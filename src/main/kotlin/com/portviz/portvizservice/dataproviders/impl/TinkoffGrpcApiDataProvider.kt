package com.portviz.portvizservice.dataproviders.impl

import com.portviz.portvizservice.dataproviders.ReadOnlyBrokerDataProvider
import com.portviz.portvizservice.models.BrokerOperation
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.tinkoff.piapi.contract.v1.MoneyValue
import ru.tinkoff.piapi.contract.v1.Operation
import ru.tinkoff.piapi.core.InvestApi
import java.lang.StringBuilder
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import kotlin.math.abs

class TinkoffGrpcApiDataProvider(apiToken: String) : ReadOnlyBrokerDataProvider {
    private val investApi = InvestApi.create(apiToken);

    override fun loadOperations(
        accountId: String,
        dateFrom: LocalDateTime,
        dateTo: LocalDateTime
    ): Flux<BrokerOperation> {
        val ops = investApi.operationsService.getExecutedOperations(
            accountId, Instant.from(dateFrom), Instant.from(dateTo)
        )
        return Mono.fromFuture(ops)
            .flatMapMany { Flux.fromIterable(it) }
            .map(TinkoffGrpcToBrokerOperationMapper::mapGrpcOperationToInternal)
    }

    object TinkoffGrpcToBrokerOperationMapper {
        fun mapGrpcOperationToInternal(op: Operation): BrokerOperation {
            return BrokerOperation(
                externalOperationId = op.id,
                externalParentOperationId = op.parentOperationId,
                quantity = extractQuantity(op)
            )
        }

        private fun toBigDecimal(money: MoneyValue): BigDecimal {
            if (money.units == 0L && money.nano == 0) return BigDecimal.ZERO
            val builder = StringBuilder()
            // Based on Tinkoff docs, both values can be negative
            if (money.units < 0 || money.nano < 0) builder.append("-")
            builder.append(abs(money.units))
                .append(".")
                .append(abs(money.nano))
                .toString()
            return BigDecimal(builder.toString())
        }

        private fun extractQuantity(op: Operation): Long {
            /*
                Previous Tinkoff API had a bug when it produced incorrect 'quantity' value in case if an order was partially
                complete. So, it's better to calculate quantity by trades (if they provided)
                rather than rely on 'quantity' field.
             */
            return if (op.tradesCount > 0) {
                op.tradesList
                    .map { it.quantity }
                    .reduce { acc, q -> acc + q }
            } else {
                op.quantity - op.quantityRest
            }
        }

        private fun extractCompletionDate(op: Operation): ZonedDateTime {
            val maxDate = op.tradesList
                .map { it.dateTime.seconds }
                .plus(op.date.seconds)
                .max()
            return ZonedDateTime.ofInstant(Instant.ofEpochSecond(maxDate), ZoneOffset.UTC)
        }
    }
}