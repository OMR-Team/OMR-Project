package com.lok.dev.omrchecker.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lok.dev.commonbase.BaseAdapter
import com.lok.dev.commonbase.BaseViewHolder
import com.lok.dev.coredatabase.entity.TagTable
import com.lok.dev.omrchecker.databinding.ItemTagBinding

class TagListAdapter(
    private val context: Context
) : BaseAdapter<ItemTagBinding, TagTable>() {

    override fun getBinding(parent: ViewGroup, viewType: Int): BaseViewHolder<ItemTagBinding> =
        TagViewHolder(
            ItemTagBinding.inflate(LayoutInflater.from(context), parent, false)
        )


    inner class TagViewHolder(
        binding: ItemTagBinding
    ) : BaseViewHolder<ItemTagBinding>(binding) {

        override fun bind(position: Int) = with(binding) {
            tagChip.text = adapterList[position].name
        }
    }
}