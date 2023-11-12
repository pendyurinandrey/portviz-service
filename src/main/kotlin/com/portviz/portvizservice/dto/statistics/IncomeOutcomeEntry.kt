package com.portviz.portvizservice.dto.statistics

import java.math.BigDecimal
import java.time.LocalDate

data class IncomeOutcomeEntry(
    val date: LocalDate,
    val income: BigDecimal,
    val outcome: BigDecimal
)
