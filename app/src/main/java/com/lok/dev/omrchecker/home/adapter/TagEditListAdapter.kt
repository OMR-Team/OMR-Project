package com.lok.dev.omrchecker.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lok.dev.commonbase.BaseAdapter
import com.lok.dev.commonbase.BaseViewHolder
import com.lok.dev.coredatabase.entity.TagTable
import com.lok.dev.omrchecker.databinding.ItemEditTagBinding

class TagEditListAdapter(
    private val context: Context,
    private val onChecked: (Boolean, Int) -> Unit,
    private val onLongClicked: (Int) -> Unit
) : BaseAdapter<ItemEditTagBinding, TagTable>() {

    override fun getBinding(parent: ViewGroup, viewType: Int): BaseViewHolder<ItemEditTagBinding> =
        EditTagViewHolder(
            ItemEditTagBinding.inflate(LayoutInflater.from(context), parent, false)
        )

    inner class EditTagViewHolder(
        binding: ItemEditTagBinding
    ) : BaseViewHolder<ItemEditTagBinding>(binding) {

        override fun bind(position: Int): Unit = with(binding) {
            val data = adapterList[position]
            tagChip.apply {
                isChecked = false
                text = data.name
                setOnCheckedChangeListener { _, isChecked ->
                    onChecked.invoke(isChecked, data.id)
                }
                setOnLongClickListener {
                    onLongClicked.invoke(data.id)
                    true
                }
            }
        }
    }
}