package com.lok.dev.omrchecker.subject.omr

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.GridLayout
import android.widget.GridLayout.spec
import androidx.appcompat.content.res.AppCompatResources
import com.lok.dev.commonbase.BaseDiffUtilAdapter
import com.lok.dev.commonbase.BaseViewHolder
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.databinding.ItemOmrInputBinding

class OmrInputAdapter(
    private val context : Context,
    private val test: List<List<Int>>
) : BaseDiffUtilAdapter<ItemOmrInputBinding, List<Int>>() {

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

            if(omrInputContainer.childCount == 0) {
                for (i in test[position].indices) {
                    val view = makeCheckBox(i)
                    view.setOnCheckedChangeListener { _, checked ->
                        if (checked) view.buttonDrawable =
                            AppCompatResources.getDrawable(context, R.drawable.omr_selected)
                        else view.buttonDrawable =
                            AppCompatResources.getDrawable(context, getImageType(i))
                    }
                    view.isChecked = data[i] != 0
                    omrInputContainer.addView(view)
                }
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
                0 -> R.drawable.omr_num_1
                1 -> R.drawable.omr_num_2
                2 -> R.drawable.omr_num_3
                3 -> R.drawable.omr_num_4
                4 -> R.drawable.omr_num_5
                5 -> R.drawable.omr_num_6
                6 -> R.drawable.omr_num_7
                7 -> R.drawable.omr_num_8
                8 -> R.drawable.omr_num_9
                9 -> R.drawable.omr_num_10
                else -> R.drawable.omr_num_1
            }
        }
    }

    override fun areItemsTheSame(oldItem: List<Int>, newItem: List<Int>): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: List<Int>, newItem: List<Int>): Boolean =
        oldItem == newItem

}