package com.portviz.portvizservice.views.statistics

import com.portviz.portvizservice.dto.statistics.IncomeOutcomeEntry
import com.portviz.portvizservice.services.statistics.PortvizStatisticsService
import com.portviz.portvizservice.security.SecurityUtils
import jakarta.enterprise.context.RequestScoped
import jakarta.inject.Named
import org.primefaces.model.charts.ChartData
import org.primefaces.model.charts.bar.BarChartDataSet
import org.primefaces.model.charts.bar.BarChartModel
import org.primefaces.model.charts.bar.BarChartOptions
import org.primefaces.model.charts.optionconfig.title.Title
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier


@Named
@RequestScoped
class SummaryView @Autowired constructor(
    @Qualifier("demoPortvizStatisticsService") private val demoPortvizStatisticsService: PortvizStatisticsService
) {

    val incomeOutcomeBarChart
        get(): BarChartModel {
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
            incomeDataSet.label = "Income, \$"
            incomeDataSet.backgroundColor = "#097969"
            data.addChartDataSet(incomeDataSet)

            val outcomeDataSet = BarChartDataSet()
            val outcomeValues = statEntries?.map(IncomeOutcomeEntry::outcome)
            outcomeDataSet.data = outcomeValues
            outcomeDataSet.label = "Outcome, \$"
            outcomeDataSet.backgroundColor = "#AA4A44"
            data.addChartDataSet(outcomeDataSet)

            val opts = BarChartOptions()
            val title = Title()
            title.isDisplay = true
            title.text = "Income and outcome, \$/year"
            opts.title = title

            barChart.data = data
            barChart.options = opts

            return barChart
        }
}