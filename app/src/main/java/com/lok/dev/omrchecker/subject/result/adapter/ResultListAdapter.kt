package com.lok.dev.omrchecker.subject.result.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import com.lok.dev.commonbase.BaseAdapter
import com.lok.dev.commonbase.BaseViewHolder
import com.lok.dev.commonmodel.ResultJoinData
import com.lok.dev.commonutil.color
import com.lok.dev.commonutil.visible
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.databinding.ItemOmrResultBinding

class ResultListAdapter(
    private val context: Context,
    lifecycleCoroutineScope: LifecycleCoroutineScope,
    private val onClick: (ResultJoinData) -> Unit
): BaseAdapter<ItemOmrResultBinding, ResultJoinData>(lifecycleCoroutineScope) {

    override fun getBinding(
        parent: ViewGroup,
        viewType: Int
    ) = ViewHolder(
        ItemOmrResultBinding.inflate(LayoutInflater.from(context), parent, false)
    )

    inner class ViewHolder(override val binding: ItemOmrResultBinding): BaseViewHolder<ItemOmrResultBinding>(binding) {

        override fun bind(position: Int) {
            val item = adapterList[position]
            binding.numResult.text = (item.no + 1).toString()

            val resultImage = getImageType(item.answer)
            binding.myResultImage.visible(resultImage != null)
            binding.myResultText.visible(resultImage == null)
            resultImage?.let { binding.myResultImage.setImageResource(it) }

            if(item.answer.isEmpty() || item.problem.isEmpty() || item.answer != item.problem) {
                binding.root.setBackgroundColor(context.color(R.color.theme_red_1))
                binding.numResult.setBackgroundColor(context.color(R.color.theme_red))
            }

            binding.root.setOnClickListener {
                onClick.invoke(item)
            }
        }

        private fun getImageType(position: List<Int>): Int? {
            return if(position.size > 1){
                R.drawable.omr_result_m
            }
            else if(position.isEmpty()) {
                null
            }
            else {
                    when (position[0]) {
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
    }
}