package com.lok.dev.omrchecker.subject.omr

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.GridLayout
import android.widget.GridLayout.spec
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleCoroutineScope
import com.lok.dev.commonbase.BaseAdapter
import com.lok.dev.commonbase.BaseViewHolder
import com.lok.dev.commonutil.convertDpToPx
import com.lok.dev.coredatabase.entity.ProblemTable
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.databinding.ItemOmrInputBinding


class OmrInputAdapter(
    private val context: Context,
    lifecycleCoroutineScope: LifecycleCoroutineScope,
    private val selectNum: Int,
    private val onClick: (Pair<Boolean, Int>) -> Unit
) : BaseAdapter<ItemOmrInputBinding, ProblemTable>(lifecycleCoroutineScope) {

    override fun getBinding(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemOmrInputBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
    )

    inner class ViewHolder(
        override val binding: ItemOmrInputBinding
    ) : BaseViewHolder<ItemOmrInputBinding>(binding) {
        override fun bind(position: Int) = with(binding) {
            val data = adapterList[position]

            omrNum.text = data.no.toString()

            omrInputContainer.removeAllViews()

            for(num in 1..selectNum) {
                val view = makeCheckBox(num)
                if(data.answer.contains(num)) {
                    view.isChecked = true
                    view.buttonDrawable = AppCompatResources.getDrawable(context, R.drawable.omr_selected)
                }else {
                    view.isChecked = false
                    view.buttonDrawable = AppCompatResources.getDrawable(context, getImageType(num))
                }
                view.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) addData(data, num)
                    else removeData(data, num)
                    onClick.invoke(Pair(isChecked, data.no))
                }
                omrInputContainer.addView(view)
            }

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

        private fun addData(data: ProblemTable, num: Int) {
            val modAnswer = data.answer.toMutableList()
            modAnswer.add(num)
            adapterList[adapterPosition] = data.copy(answer = modAnswer)
            notifyItemChanged(adapterPosition)
        }

        private fun removeData(data: ProblemTable, num: Int) {
            val modAnswer = data.answer.toMutableList()
            modAnswer.remove(num)
            adapterList[adapterPosition] = data.copy(answer = modAnswer)
            notifyItemChanged(adapterPosition)
        }

        private fun makeCheckBox(position: Int): CheckBox {
            val checkBox = CheckBox(context)
            val param = GridLayout.LayoutParams().apply {
                columnSpec = spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f)
                height = ViewGroup.LayoutParams.WRAP_CONTENT
                width = 0
            }
            checkBox.layoutParams = param
            checkBox.buttonDrawable = AppCompatResources.getDrawable(context, getImageType(position))
            return checkBox
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