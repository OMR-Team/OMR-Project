package com.lok.dev.omrchecker.subject.result.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.lok.dev.commonbase.BaseFragment
import com.lok.dev.commonutil.color
import com.lok.dev.commonutil.onUiState
import com.lok.dev.coredatabase.entity.HistoryTable
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.custom.ChartMarkerView
import com.lok.dev.omrchecker.databinding.FragmentResultChartBinding
import com.lok.dev.omrchecker.subject.result.ResultViewModel

class ResultChartFragment : BaseFragment<FragmentResultChartBinding>() {

    private val viewModel: ResultViewModel by activityViewModels()

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentResultChartBinding.inflate(inflater, container, false)

    override fun initFragment() {
        setUpLineChart()
        collectViewModel()
    }

    private fun collectViewModel() = with(viewModel) {
        historyState.onUiState(lifecycleScope,
            success = { list ->
                val order = list.sortedByDescending { it.cnt }.chunked(5).first().reversed()
                val entry = order.mapIndexed { index, historyTable ->
                    Entry(index.toFloat(), historyTable.score.toFloat())
                }
                binding.chart.xAxis.valueFormatter = MyAxisFormatter(order)
                setDataToLineChart(entry)
            }
        )
    }

    private fun setUpLineChart() {
        with(binding.chart) {
            val markerView = ChartMarkerView(requireContext(), R.layout.custom_chart_marker)
            marker = markerView

            animateY(1000)
            setExtraOffsets(20f, 50f, 20f, 0f)
            description.isEnabled = false
            extraBottomOffset = 15f
            setScaleEnabled(false)
            setPinchZoom(false)

            axisLeft.isEnabled = false
            axisLeft.setDrawGridLines(false)
            axisLeft.axisMinimum = 0f
            axisLeft.axisMaximum = 100f

            xAxis.setDrawGridLines(true)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.granularity = 1f  // 축 레이블 표시 간격
            xAxis.setDrawAxisLine(false)
            xAxis.yOffset = 20f
            xAxis.textColor = requireContext().color(R.color.theme_black_2)
            xAxis.textSize = 13F
            xAxis.axisLineColor = requireContext().color(R.color.theme_black_6)
            xAxis.axisMinimum = 0f
            xAxis.axisMaximum = 4f
            xAxis.spaceMax = 1f
            xAxis.spaceMin = 1f

            axisRight.isEnabled = false

            legend.orientation = Legend.LegendOrientation.VERTICAL
            legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            legend.form = Legend.LegendForm.CIRCLE
            legend.isEnabled = false
        }
    }

    private fun setDataToLineChart(data: List<Entry>) {

        val weekOneSales = LineDataSet(data, "Week 1").apply {
            lineWidth = 1f
            valueTextSize = 15f
            mode = LineDataSet.Mode.LINEAR
            color = requireContext().color(R.color.theme_blue_primary)
            setDrawValues(false)
            setDrawCircles(true)
            setDrawCircleHole(true)
            setCircleColor(requireContext().color(R.color.theme_blue_primary))
            circleHoleColor = requireContext().color(R.color.white)
            circleRadius = 10f
            circleHoleRadius = 9f
            setDrawHighlightIndicators(false)
        }


        val dataSet = ArrayList<ILineDataSet>()
        dataSet.add(weekOneSales)

        val lineData = LineData(dataSet)
        binding.chart.data = lineData
        binding.chart.highlightValue(data.last().x, data.last().y, dataSet.lastIndex)

        binding.chart.invalidate()
    }

    inner class MyAxisFormatter(val list: List<HistoryTable>) : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            return if(index < list.size) "${list[index].cnt}회차" else ""
        }
    }

}