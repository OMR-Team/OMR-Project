package com.lok.dev.omrchecker.subject.omr

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.GridLayout
import android.widget.GridLayout.spec
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.children
import com.lok.dev.commonbase.BaseDiffUtilAdapter
import com.lok.dev.commonbase.BaseViewHolder
import com.lok.dev.coredatabase.entity.OMRTable
import com.lok.dev.coredatabase.entity.ProblemTable
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.databinding.ItemOmrInputBinding

class OmrInputAdapter(
    private val context : Context,
    private val test: List<List<Int>>,
    private val onClick: (Pair<Boolean, List<Int>>) -> Unit
) : BaseDiffUtilAdapter<ItemOmrInputBinding, ProblemTable>() {

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

            omrNum.text = (position.plus(1)).toString()

            omrInputContainer.removeAllViews()
            for (i in test[position].indices) {
                val problemNum = i.plus(1)
                val view = makeCheckBox(problemNum)
                view.setOnCheckedChangeListener { _, checked ->
                    if (checked) {
                        view.buttonDrawable = AppCompatResources.getDrawable(context, R.drawable.omr_selected)
                        onClick.invoke(Pair(true, listOf(position, i)))
                    } else{
                        view.buttonDrawable = AppCompatResources.getDrawable(context, getImageType(i))
                        onClick.invoke(Pair(false, listOf(position, i)))
                    }
                }
                omrInputContainer.addView(view)
            }

            data.answer.forEach {
                (omrInputContainer.getChildAt(it.minus(1)) as CheckBox).isChecked = true
            }

            if(position % 10 < 5) container.background = AppCompatResources.getDrawable(context, R.color.omr_input_container)
            else container.background = AppCompatResources.getDrawable(context, R.color.white)

            if(position % 5 == 4) divider.visibility = View.VISIBLE
            else divider.visibility = View.GONE
        }

        private fun makeCheckBox(position: Int) : CheckBox {
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
            return when(position) {
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

    override fun areItemsTheSame(oldItem: ProblemTable, newItem: ProblemTable): Boolean =
        oldItem.id == newItem.id && oldItem.no == newItem.no && oldItem.answer == newItem.answer

    override fun areContentsTheSame(oldItem: ProblemTable, newItem: ProblemTable): Boolean =
        oldItem.id == newItem.id && oldItem.no == newItem.no && oldItem.answer == newItem.answer
}