package com.portviz.portvizservice.dataproviders

import com.portviz.portvizservice.models.BrokerOperation
import reactor.core.publisher.Flux
import java.time.LocalDateTime

interface ReadOnlyBrokerDataProvider {
    fun loadOperations(accountId: String, dateFrom: LocalDateTime, dateTo: LocalDateTime): Flux<BrokerOperation>
}