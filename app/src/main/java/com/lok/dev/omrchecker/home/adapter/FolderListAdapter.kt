package com.lok.dev.omrchecker.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.lok.dev.commonbase.BaseAdapter
import com.lok.dev.commonbase.BaseViewHolder
import com.lok.dev.commonmodel.state.FolderState
import com.lok.dev.coredatabase.entity.SubjectTable
import com.lok.dev.omrchecker.databinding.ItemFolderListGrid2Binding
import com.lok.dev.omrchecker.databinding.ItemFolderListGrid3Binding
import com.lok.dev.omrchecker.databinding.ItemFolderListLinearBinding

class FolderListAdapter(
    private val context: Context
) : BaseAdapter<ViewDataBinding, SubjectTable>() {

    private var folderStateOrdinal = FolderState.GRID_2.ordinal

    fun updateFolderState(state: Int) {
        folderStateOrdinal = state
    }

    override fun getBinding(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewDataBinding> {
        return when (folderStateOrdinal) {
            FolderState.GRID_2.ordinal -> {
                ItemFolderGrid2ViewHolder(
                    ItemFolderListGrid2Binding.inflate(LayoutInflater.from(context), parent, false)
                )
            }
            FolderState.GRID_3.ordinal -> {
                ItemFolderGrid3ViewHolder(
                    ItemFolderListGrid3Binding.inflate(LayoutInflater.from(context), parent, false)
                )
            }
            else -> {
                ItemFolderLinearViewHolder(
                    ItemFolderListLinearBinding.inflate(LayoutInflater.from(context), parent, false)
                )
            }
        }
    }

    inner class ItemFolderGrid2ViewHolder(
        binding: ItemFolderListGrid2Binding
    ) : BaseViewHolder<ItemFolderListGrid2Binding>(binding) {
        override fun bind(position: Int) = with(binding) {
            val data = adapterList[position]
            tvFolder.text = data.name
        }
    }

    inner class ItemFolderGrid3ViewHolder(
        binding: ItemFolderListGrid3Binding
    ) : BaseViewHolder<ItemFolderListGrid3Binding>(binding) {
        override fun bind(position: Int) = with(binding) {
            val data = adapterList[position]
            tvFolder.text = data.name
        }
    }

    inner class ItemFolderLinearViewHolder(
        binding: ItemFolderListLinearBinding
    ) : BaseViewHolder<ItemFolderListLinearBinding>(binding) {
        override fun bind(position: Int) = with(binding) {
            val data = adapterList[position]
            tvFolder.text = data.name
        }
    }
}