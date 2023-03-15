package com.lok.dev.omrchecker.custom

import android.content.Context
import android.graphics.Canvas
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.lok.dev.omrchecker.R

class ChartMarkerView : MarkerView {

    private var txtViewData: TextView = findViewById(R.id.txtMarker)

    constructor(context: Context?, layoutResource: Int) : super(context, layoutResource)

    override fun draw(canvas: Canvas?) {
        canvas?.translate(-(width / 2).toFloat(), (-height +(height / 6)).toFloat() )

        super.draw(canvas)
    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        txtViewData.text = e?.y?.toInt().toString()
    }
}