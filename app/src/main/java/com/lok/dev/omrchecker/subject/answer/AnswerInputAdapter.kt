package com.lok.dev.omrchecker.subject.answer

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.GridLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleCoroutineScope
import com.lok.dev.commonbase.BaseAdapter
import com.lok.dev.commonbase.BaseViewHolder
import com.lok.dev.coredatabase.entity.AnswerTable
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.databinding.ItemAnswerInputBinding

class AnswerInputAdapter(
    private val context: Context,
    lifecycleCoroutineScope: LifecycleCoroutineScope,
    private val onClick: (Pair<Boolean, Int>) -> Unit
) : BaseAdapter<ItemAnswerInputBinding, AnswerTable>(lifecycleCoroutineScope) {

    override fun getBinding(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemAnswerInputBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
    )

    inner class ViewHolder(
        override val binding: ItemAnswerInputBinding
    ) : BaseViewHolder<ItemAnswerInputBinding>(binding) {
        override fun bind(position: Int) = with(binding) {
            val data = adapterList[position]

            omrNum.text = data.no.toString()

            omrAnswerContainer.removeAllViews()
            for (i in data.answer.indices) {
                val selectNum = i.plus(1)
                val view = makeCheckBox(selectNum)
                if (data.answer[i] == 0) {
                    view.isChecked = false
                    view.buttonDrawable =
                        AppCompatResources.getDrawable(context, getImageType(selectNum))
                } else {
                    view.isChecked = true
                    view.buttonDrawable =
                        AppCompatResources.getDrawable(context, R.drawable.answer_selected)
                }
                view.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) changeData(data, i, selectNum)
                    else changeData(data, i, 0)
                    onClick.invoke(Pair(isChecked, data.no))
                }
                omrAnswerContainer.addView(view)
            }

            if (position % 10 < 5) container.background =
                AppCompatResources.getDrawable(context, R.color.theme_red_1)
            else container.background = AppCompatResources.getDrawable(context, R.color.white)

            divider.isVisible = position % 5 == 4
        }

        private fun changeData(data: AnswerTable, problemNum: Int, selectNum: Int) {
            val modAnswer = data.answer.toMutableList()
            modAnswer[problemNum] = selectNum
            adapterList[adapterPosition] = data.copy(answer = modAnswer)
            notifyItemChanged(adapterPosition)
        }

        private fun makeCheckBox(position: Int): CheckBox {
            val checkBox = CheckBox(context)
            val param = GridLayout.LayoutParams().apply {
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f)
                height = ViewGroup.LayoutParams.WRAP_CONTENT
                width = 0
            }
            checkBox.layoutParams = param
            checkBox.buttonDrawable =
                AppCompatResources.getDrawable(context, getImageType(position))
            return checkBox
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

}