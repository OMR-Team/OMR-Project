package com.lok.dev.omrchecker.subject.result.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.lok.dev.omrchecker.R

class ResultAnswerGridAdapter (
val context: Context
): BaseAdapter() {

    val adapterList = mutableListOf<Int>()

    override fun getCount() = adapterList.size

    override fun getItem(position: Int) = adapterList[position]

    override fun getItemId(position: Int) = position.toLong()

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val item = adapterList[position]
        val answerImage = getImageType(item)
        return if(answerImage == null) {
            TextView(context).apply {
                text = "-"
                typeface = Typeface.DEFAULT_BOLD
                setPadding(15, 0, 0, 0)
            }
        }
        else {
            ImageView(context).apply {
                setImageResource(answerImage)
            }
        }
    }

    fun set(list: List<Int>) {
        adapterList.clear()
        adapterList.addAll(if(list.isEmpty()) listOf(-1) else list.map { it })
        notifyDataSetChanged()
    }

    private fun getImageType(num: Int): Int? {
        return when (num) {
            1 -> R.drawable.omr_result_1
            2 -> R.drawable.omr_result_2
            3 -> R.drawable.omr_result_3
            4 -> R.drawable.omr_result_4
            5 -> R.drawable.omr_result_5
            6 -> R.drawable.omr_result_6
            7 -> R.drawable.omr_result_7
            8 -> R.drawable.omr_result_8
            9 -> R.drawable.omr_result_9
            10 -> R.drawable.omr_result_10
            else -> null
        }
    }
}