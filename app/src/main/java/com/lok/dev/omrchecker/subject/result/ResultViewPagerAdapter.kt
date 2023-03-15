package com.lok.dev.omrchecker.subject.result

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ResultViewPagerAdapter(
    val context: Context,
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    private val adapterList = mutableListOf<String>()

    override fun getItemCount(): Int = adapterList.size

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return ResultScoreFragment()
            1 -> return ResultChartFragment()
        }
        return ResultScoreFragment()
    }

    fun set(list: List<String>) {
        adapterList.clear()
        adapterList.addAll(list)
        notifyDataSetChanged()
    }
}