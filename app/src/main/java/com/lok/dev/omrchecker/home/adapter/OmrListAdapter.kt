package com.lok.dev.omrchecker.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import com.lok.dev.commonbase.BaseAdapter
import com.lok.dev.commonbase.BaseViewHolder
import com.lok.dev.commonutil.convertToDate
import com.lok.dev.coredatabase.entity.OMRTable
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.databinding.ItemOmrListBinding

class OmrListAdapter(
    private val context: Context
) : BaseAdapter<ItemOmrListBinding, OMRTable>() {

    override fun getBinding(parent: ViewGroup, viewType: Int): BaseViewHolder<ItemOmrListBinding> =
        ItemOmrListViewHolder(
            ItemOmrListBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )

    inner class ItemOmrListViewHolder(
        binding: ItemOmrListBinding
    ) : BaseViewHolder<ItemOmrListBinding>(binding) {
        override fun bind(position: Int): Unit = with(binding) {
            val data = adapterList[position]
            tvTitle.text = data.title
            tvDate.apply {
                text = data.updateDate.convertToDate()
                setTextColor(
                    if (data.isTemp) resources.getColor(R.color.theme_orange, null)
                    else resources.getColor(R.color.theme_black_5, null)
                )
            }
            tvCnt.text = context.getString(R.string.omr_list_cnt, data.cnt)
            tvCorrectEa.text = HtmlCompat.fromHtml(
                context.getString(
                    R.string.omr_list_correct_ea,
                    data.correctCnt,
                    data.problemNum
                ), HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        }
    }
}