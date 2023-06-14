package com.lok.dev.omrchecker.subject.answer

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
import com.lok.dev.omrchecker.databinding.ItemAnswerInputBinding

class AnswerInputAdapter(
    private val context: Context,
    private val lifecycleCoroutineScope: LifecycleCoroutineScope,
    private val selectNum: Int,
    private val onClick: (Triple<Int, Int, Boolean>) -> Unit
) : BaseAdapter<ItemAnswerInputBinding, List<Pair<Int, Boolean>>>(lifecycleCoroutineScope) {

    override fun getBinding(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemAnswerInputBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
    )

    private val itemAdapterList = mutableListOf<AnswerItemInputAdapter>()

    fun setList(list: List<List<Pair<Int, Boolean>>>) {
        itemAdapterList.clear()
        list.forEachIndexed { index, _ ->
            val itemAdapter = AnswerItemInputAdapter(this@AnswerInputAdapter.context, lifecycleCoroutineScope, index) {
                onClick.invoke(it)
            }
            itemAdapterList.add(itemAdapter)
        }
        set(list)
    }

    inner class ViewHolder(
        override val binding: ItemAnswerInputBinding
    ) : BaseViewHolder<ItemAnswerInputBinding>(binding) {
        override fun bind(position: Int) = with(binding) {

            omrNum.text = position.plus(1).toString()

            val spanCnt = if(selectNum < 5) selectNum else 5
            answerList.layoutManager = GridLayoutManager(context, spanCnt, GridLayoutManager.VERTICAL, false)
            answerList.adapter = itemAdapterList[position]

            itemAdapterList[position].set(adapterList[position])

            divider.isVisible = position % 5 == 4

            setBackgroundUI(position)
        }

        private fun setBackgroundUI(position: Int) = with(binding) {
            when {
                position == 0 -> {
                    container.background = AppCompatResources.getDrawable(context, R.drawable.bg_theme_red_1_top_radius_16)
                }
                position == adapterList.lastIndex && (position % 10 < 5) -> {
                    container.background = AppCompatResources.getDrawable(context, R.drawable.bg_theme_red_1_bottom_radius_16)
                    setDividerHorizontalMargin()
                }
                position == adapterList.lastIndex && (position % 10 >= 5) -> {
                    container.background = AppCompatResources.getDrawable(context, R.drawable.bg_theme_white_bottom_radius_16)
                    setDividerHorizontalMargin()
                }
                position % 10 < 5 -> {
                    container.background = AppCompatResources.getDrawable(context, R.color.theme_red_1)
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