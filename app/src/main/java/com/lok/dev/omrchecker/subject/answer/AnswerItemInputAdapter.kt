package com.lok.dev.omrchecker.subject.answer

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import com.lok.dev.commonbase.BaseAdapter
import com.lok.dev.commonbase.BaseDiffUtilAdapter
import com.lok.dev.commonbase.BaseViewHolder
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.databinding.ItemAnswerItemInputBinding

class AnswerItemInputAdapter(
    private val context: Context,
    lifecycleCoroutineScope: LifecycleCoroutineScope,
    private val problemNum: Int,
    private val onClick: (Triple<Int, Int, Boolean>) -> Unit
) : BaseDiffUtilAdapter<ItemAnswerItemInputBinding, Pair<Int, Boolean>>(lifecycleCoroutineScope) {

    override fun getBinding(
        parent: ViewGroup,
        viewType: Int
    ) = ViewHolder(
        ItemAnswerItemInputBinding.inflate(LayoutInflater.from(context), parent, false)
    )

    inner class ViewHolder(
        override val binding: ItemAnswerItemInputBinding
    ) : BaseViewHolder<ItemAnswerItemInputBinding>(binding) {

        override fun bind(position: Int) = with(binding) {
            val data = adapterList[position]

            val answerNum = data.first      // 답안 번호
            val isSelected = data.second    // 선택 여부

            if(isSelected) {
                answerItem.isChecked = true
                answerItem.setBackgroundResource(R.drawable.answer_selected)
            }
            else {
                answerItem.isChecked = false
                answerItem.setBackgroundResource(getImageType(answerNum))
            }

            answerItem.setOnCheckedChangeListener { _, isChecked ->
                onClick.invoke(Triple(problemNum, answerNum, isChecked))
            }

        }

        private fun getImageType(position: Int): Int {
            return when (position) {
                1 -> R.drawable.answer_num_1
                2 -> R.drawable.answer_num_2
                3 -> R.drawable.answer_num_3
                4 -> R.drawable.answer_num_4
                5 -> R.drawable.answer_num_5
                6 -> R.drawable.answer_num_6
                7 -> R.drawable.answer_num_7
                8 -> R.drawable.answer_num_8
                9 -> R.drawable.answer_num_9
                10 -> R.drawable.answer_num_10
                else -> R.drawable.answer_num_1
            }
        }

    }

    override fun areItemsTheSame(oldItem: Pair<Int, Boolean>, newItem: Pair<Int, Boolean>): Boolean =
        oldItem.second == newItem.second

    override fun areContentsTheSame(oldItem: Pair<Int, Boolean>, newItem: Pair<Int, Boolean>): Boolean =
        oldItem.second == newItem.second

}