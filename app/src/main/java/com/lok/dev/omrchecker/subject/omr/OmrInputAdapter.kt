package com.lok.dev.omrchecker.subject.omr

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.GridLayoutManager
import com.lok.dev.commonbase.BaseAdapter
import com.lok.dev.commonbase.BaseViewHolder
import com.lok.dev.commonutil.convertDpToPx
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.databinding.ItemOmrInputBinding


class OmrInputAdapter(
    private val context: Context,
    private val lifecycleCoroutineScope: LifecycleCoroutineScope,
    private val selectNum: Int,
    private val onClick: (Pair<Boolean, Int>) -> Unit
) : BaseAdapter<ItemOmrInputBinding, MutableList<Pair<Int, Boolean>>>(lifecycleCoroutineScope) {

    override fun getBinding(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemOmrInputBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
    )

    private val itemAdapterList = mutableListOf<OmrItemInputAdapter>()

    fun setList(list: List<MutableList<Pair<Int, Boolean>>>) {
        itemAdapterList.clear()
        list.forEachIndexed { index, _ ->
            val itemAdapter = OmrItemInputAdapter(this@OmrInputAdapter.context, lifecycleCoroutineScope, index) {
                val problemNum = it.first   // 문제 번호
                val answerNum = it.second   // 답안 번호
                val isSelected = it.third   // 선택 여부
                adapterList[problemNum][answerNum.minus(1)] = Pair(answerNum, isSelected)
                onClick.invoke(Pair(isSelected, problemNum))
            }
            itemAdapterList.add(itemAdapter)
        }
        set(list)
    }

    inner class ViewHolder(
        override val binding: ItemOmrInputBinding
    ) : BaseViewHolder<ItemOmrInputBinding>(binding) {
        override fun bind(position: Int) = with(binding) {

            omrNum.text = position.plus(1).toString()

            val spanCnt = if(selectNum < 5) selectNum else 5
            omrInputList.layoutManager = GridLayoutManager(context, spanCnt, GridLayoutManager.VERTICAL, false)
            omrInputList.adapter = itemAdapterList[position]

            itemAdapterList[position].set(adapterList[position])

            divider.isVisible = position % 5 == 4
            setBackgroundUI(position)
        }

        private fun setBackgroundUI(position: Int) = with(binding) {
            when {
                position == 0 -> {
                    container.background = AppCompatResources.getDrawable(context, R.drawable.bg_theme_blue_4_top_radius_16)
                }
                position == adapterList.lastIndex && (position % 10 < 5) -> {
                    container.background = AppCompatResources.getDrawable(context, R.drawable.bg_theme_blue_4_bottom_radius_16)
                    setDividerHorizontalMargin()
                }
                position == adapterList.lastIndex && (position % 10 >= 5) -> {
                    container.background = AppCompatResources.getDrawable(context, R.drawable.bg_theme_white_bottom_radius_16)
                    setDividerHorizontalMargin()
                }
                position % 10 < 5 -> {
                    container.background = AppCompatResources.getDrawable(context, R.color.theme_blue_4)
                }
                else -> {
                    container.background = AppCompatResources.getDrawable(context, R.color.white)
                }
            }
        }

        private fun setDividerHorizontalMargin() = with(binding) {
            val params = divider.layoutParams as ViewGroup.MarginLayoutParams
            params.leftMargin = context.convertDpToPx(8F).toInt()
            params.rightMargin = context.convertDpToPx(8F).toInt()
            divider.layoutParams = params
        }

    }
}