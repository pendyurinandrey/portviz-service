package com.portviz.portvizservice.services.statistics

import com.portviz.portvizservice.dto.statistics.IncomeOutcomeEntry

interface PortvizStatisticsService {

    fun getIncomeOutcomeStatistics(): List<IncomeOutcomeEntry>
}