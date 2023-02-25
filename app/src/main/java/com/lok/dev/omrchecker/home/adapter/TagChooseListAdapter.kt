package com.lok.dev.omrchecker.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.lok.dev.commonbase.BaseAdapter
import com.lok.dev.commonbase.BaseViewHolder
import com.lok.dev.coredatabase.entity.TagTable
import com.lok.dev.omrchecker.databinding.ItemEditTagBinding
import com.lok.dev.omrchecker.databinding.ItemTagBinding

class TagChooseListAdapter(
    private val context: Context,
    private val onChecked: (Boolean, Int) -> Unit
) : BaseAdapter<ItemTagBinding, TagTable>() {

    override fun getBinding(parent: ViewGroup, viewType: Int): BaseViewHolder<ItemTagBinding> =
        ChooseTagViewHolder(
            ItemTagBinding.inflate(LayoutInflater.from(context), parent, false)
        )

    inner class ChooseTagViewHolder(
        binding: ItemTagBinding
    ) : BaseViewHolder<ItemTagBinding>(binding) {

        override fun bind(position: Int) = with(binding) {
            val data = adapterList[position]
            tagChip.text = data.name
            tagChip.setOnCheckedChangeListener { _, isChecked ->
                onChecked.invoke(isChecked, data.id)
            }
        }
    }
}