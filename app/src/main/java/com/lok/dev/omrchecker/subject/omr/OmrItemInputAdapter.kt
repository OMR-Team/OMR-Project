package com.lok.dev.omrchecker.subject.omr

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import com.lok.dev.commonbase.BaseAdapter
import com.lok.dev.commonbase.BaseViewHolder
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.databinding.ItemOmrItemInputBinding

class OmrItemInputAdapter(
    private val context: Context,
    lifecycleCoroutineScope: LifecycleCoroutineScope,
    private val problemNum: Int,
    private val onClick: (Triple<Int, Int, Boolean>) -> Unit
) : BaseAdapter<ItemOmrItemInputBinding, Pair<Int, Boolean>>(lifecycleCoroutineScope) {


    override fun getBinding(
        parent: ViewGroup,
        viewType: Int
    ) = ViewHolder(
        ItemOmrItemInputBinding.inflate(LayoutInflater.from(context), parent, false)
    )


    inner class ViewHolder(
        override val binding: ItemOmrItemInputBinding
    ) : BaseViewHolder<ItemOmrItemInputBinding>(binding) {

        override fun bind(position: Int) = with(binding) {
            val data = adapterList[position]

            val answerNum = data.first      // 답안 번호
            val isSelected = data.second    // 선택 여부

            if (isSelected) {
                omrItem.isChecked = true
                omrItem.setBackgroundResource(R.drawable.omr_selected)
            }
            else {
                omrItem.isChecked = false
                omrItem.setBackgroundResource(getImageType(answerNum))
            }

            omrItem.setOnCheckedChangeListener { _, isChecked ->
                onClick.invoke(Triple(problemNum, answerNum, isChecked))
                if (isChecked) omrItem.setBackgroundResource(R.drawable.omr_selected)
                else omrItem.setBackgroundResource(getImageType(answerNum))
            }
        }

        private fun getImageType(position: Int): Int {
            return when (position) {
                1 -> R.drawable.omr_num_1
                2 -> R.drawable.omr_num_2
                3 -> R.drawable.omr_num_3
                4 -> R.drawable.omr_num_4
                5 -> R.drawable.omr_num_5
                6 -> R.drawable.omr_num_6
                7 -> R.drawable.omr_num_7
                8 -> R.drawable.omr_num_8
                9 -> R.drawable.omr_num_9
                10 -> R.drawable.omr_num_10
                else -> R.drawable.omr_num_1
            }
        }
    }

}