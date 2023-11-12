package com.portviz.portvizservice.views.statistics

import com.portviz.portvizservice.dto.statistics.IncomeOutcomeEntry
import com.portviz.portvizservice.services.statistics.PortvizStatisticsService
import com.portviz.portvizservice.security.SecurityUtils
import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Named
import org.primefaces.model.charts.ChartData
import org.primefaces.model.charts.bar.BarChartDataSet
import org.primefaces.model.charts.bar.BarChartModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier


@Named
@RequestScoped
class SummaryView @Autowired constructor(
    @Qualifier("demoPortvizStatisticsService") private val demoPortvizStatisticsService: PortvizStatisticsService
) {

    val incomeOutcomeBarChart get(): BarChartModel {
        var statEntries: List<IncomeOutcomeEntry>? = null
        if (SecurityUtils.isDemoAccount()) {
            statEntries = demoPortvizStatisticsService.getIncomeOutcomeStatistics()
        }
        val barChart = BarChartModel()
        val data = ChartData()
        val labels = statEntries?.map { it.date.toString() }
        data.labels = labels

        val incomeDataSet = BarChartDataSet()
        val incomeValues = statEntries?.map(IncomeOutcomeEntry::income)
        incomeDataSet.data = incomeValues
        data.addChartDataSet(incomeDataSet)

        val outcomeDataSet = BarChartDataSet()
        val outcomeValues = statEntries?.map(IncomeOutcomeEntry::outcome)
        outcomeDataSet.data = outcomeValues
        data.addChartDataSet(outcomeDataSet)

        barChart.data = data

        return barChart
    }
}