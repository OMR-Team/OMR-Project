package com.lok.dev.omrchecker.subject.result

import android.view.LayoutInflater
import android.view.ViewGroup
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
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.custom.ChartMarkerView
import com.lok.dev.omrchecker.databinding.FragmentResultChartBinding

class ResultChartFragment : BaseFragment<FragmentResultChartBinding>() {

    override fun createFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentResultChartBinding.inflate(inflater, container, false)

    override fun initFragment() {
        setUpLineChart()
        setDataToLineChart()
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
            xAxis.valueFormatter = MyAxisFormatter()
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

    private fun setDataToLineChart() {

        val data = week1()
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

    private fun week1(): ArrayList<Entry> {
        val sales = ArrayList<Entry>()
        sales.add(Entry(0f, 90f))
        sales.add(Entry(1f, 80f))
        sales.add(Entry(2f, 100f))
        sales.add(Entry(3f, 70f))
        sales.add(Entry(4f, 100f))
        return sales
    }

    inner class MyAxisFormatter : IndexAxisValueFormatter() {

        private var items = arrayListOf("1회차", "2회차", "3회차", "4회차", "5회차")

        override fun getAxisLabel(value: Float, axis: AxisBase?): String? {
            val index = value.toInt()
            return if (index < items.size) {
                items[index]
            } else {
                null
            }
        }
    }

}