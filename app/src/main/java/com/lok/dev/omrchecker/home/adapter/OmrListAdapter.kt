package com.lok.dev.omrchecker.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.lok.dev.commonbase.BaseAdapter
import com.lok.dev.commonbase.BaseViewHolder
import com.lok.dev.commonutil.visible
import com.lok.dev.coredatabase.entity.OMRTable
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.databinding.ItemOmrListBinding

class OmrListAdapter(
    lifecycleCoroutineScope: LifecycleCoroutineScope,
    private val context: Context,
    private val onClick: (OMRTable) -> Unit
) : BaseAdapter<ItemOmrListBinding, OMRTable>(lifecycleCoroutineScope) {

    private val asyncDiffer = AsyncListDiffer(this, DiffUtilCallback())

    fun updateList(items: List<OMRTable>) {
        asyncDiffer.submitList(items)
    }

    override fun getItemCount(): Int = asyncDiffer.currentList.size

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
            val data = asyncDiffer.currentList[position]

            tvTitle.text = data.title
            tvDate.text = data.getDate()
            tvCorrectEa.apply {
                text = HtmlCompat.fromHtml(
                    context.getString(
                        R.string.omr_list_correct_ea,
                        data.correctCnt,
                        data.problemNum
                    ), HtmlCompat.FROM_HTML_MODE_LEGACY
                )
                visible(true)
            }

            tvCnt.apply {
                text = context.getString(R.string.text_omr_cnt, data.cnt)
                visible(data.cnt > 1)
            }

            container.throttleFirstClick {
                onClick.invoke(data)
            }
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<OMRTable>() {

        override fun areItemsTheSame(oldItem: OMRTable, newItem: OMRTable): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: OMRTable, newItem: OMRTable): Boolean {
            return oldItem.getDate() == newItem.getDate()
        }
    }
}