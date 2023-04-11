package com.lok.dev.omrchecker.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.lok.dev.commonbase.BaseAdapter
import com.lok.dev.commonbase.BaseViewHolder
import com.lok.dev.coredatabase.entity.OMRTable
import com.lok.dev.omrchecker.databinding.ItemOmrOngoingBinding

class OmrOngoingListAdapter(
    lifecycleCoroutineScope: LifecycleCoroutineScope,
    private val context: Context,
    private val onClick: (OMRTable) -> Unit
) : BaseAdapter<ItemOmrOngoingBinding, OMRTable>(lifecycleCoroutineScope) {

    private val asyncDiffer = AsyncListDiffer(this, DiffUtilCallback())

    fun updateList(items: List<OMRTable>) {
        asyncDiffer.submitList(items)
    }

    override fun getItemCount(): Int = asyncDiffer.currentList.size

    override fun getBinding(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ItemOmrOngoingBinding> = ItemOmrOngoingViewHolder(
        ItemOmrOngoingBinding.inflate(LayoutInflater.from(context), parent, false)
    )

    inner class ItemOmrOngoingViewHolder(
        binding: ItemOmrOngoingBinding
    ) : BaseViewHolder<ItemOmrOngoingBinding>(binding) {
        override fun bind(position: Int): Unit = with(binding) {
            val data = asyncDiffer.currentList[position]
            omrData = data

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
            return oldItem == newItem
        }
    }
}