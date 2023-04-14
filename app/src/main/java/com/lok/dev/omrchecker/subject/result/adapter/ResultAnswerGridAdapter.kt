package com.lok.dev.omrchecker.subject.result.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
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
        val imageView = ImageView(context)
        val item = adapterList[position]

        imageView.setImageResource(getImageType(item))
        return imageView
    }

    fun set(list: List<Int>) {
        adapterList.clear()
        adapterList.addAll(list.map { it })
        notifyDataSetChanged()
    }

    private fun getImageType(num: Int): Int {
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
            else -> R.drawable.omr_result_1
        }
    }
}