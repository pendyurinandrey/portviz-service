package com.portviz.portvizservice.services.statistics.impl

import com.portviz.portvizservice.dto.statistics.IncomeOutcomeEntry
import com.portviz.portvizservice.services.statistics.PortvizStatisticsService
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.LocalDate

@Component("demoPortvizStatisticsService")
class DemoPortvizStatisticsService: PortvizStatisticsService {
    override fun getIncomeOutcomeStatistics(): List<IncomeOutcomeEntry> {
        return listOf(
            IncomeOutcomeEntry(LocalDate.now(), BigDecimal("20"), BigDecimal("10"))
        )
    }
}