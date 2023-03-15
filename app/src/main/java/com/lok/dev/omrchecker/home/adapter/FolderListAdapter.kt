package com.lok.dev.omrchecker.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleCoroutineScope
import com.lok.dev.commonbase.BaseAdapter
import com.lok.dev.commonbase.BaseViewHolder
import com.lok.dev.commonmodel.state.FolderState
import com.lok.dev.commonutil.AppConfig
import com.lok.dev.commonutil.getDrawableString
import com.lok.dev.coredatabase.entity.SubjectTable
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.databinding.ItemFolderListGrid2Binding
import com.lok.dev.omrchecker.databinding.ItemFolderListGrid3Binding
import com.lok.dev.omrchecker.databinding.ItemFolderListLinearBinding

class FolderListAdapter(
    private val lifecycleScope: LifecycleCoroutineScope,
    private val context: Context
) : BaseAdapter<ViewDataBinding, SubjectTable>(lifecycleScope) {

    private var folderStateOrdinal = FolderState.GRID_2.ordinal

    fun updateFolderState(state: Int) {
        folderStateOrdinal = state
        notifyDataSetChanged()
    }

    override fun getBinding(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewDataBinding> {
        return when (folderStateOrdinal) {
            FolderState.GRID_2.ordinal -> {
                ItemFolderGrid2ViewHolder(
                    ItemFolderListGrid2Binding.inflate(LayoutInflater.from(context), parent, false)
                )
            }
            else -> {
                ItemFolderLinearViewHolder(
                    ItemFolderListLinearBinding.inflate(LayoutInflater.from(context), parent, false)
                )
            }
        }
    }

    abstract inner class BaseFolderViewHolder<T : ViewDataBinding>(
        override val binding: T
    ) : BaseViewHolder<ViewDataBinding>(binding) {

        lateinit var data: SubjectTable
        private var fileName = "black"
        var resId = R.drawable.folder_black

        override fun bind(position: Int) {
            data = adapterList[position]
            fileName =
                AppConfig.folderData.firstOrNull { it.id == data.folderId }?.fileName ?: "black"
            resId = context.getDrawableString("folder_${fileName}") ?: R.drawable.folder_black
            initViewHolder()
        }

        abstract fun initViewHolder()
    }

    inner class ItemFolderGrid2ViewHolder(
        binding: ItemFolderListGrid2Binding
    ) : BaseFolderViewHolder<ItemFolderListGrid2Binding>(binding) {
        override fun initViewHolder() = with(binding) {
            ivFolder.setImageDrawable(ResourcesCompat.getDrawable(context.resources, resId, null))
            tvFolder.text = data.name
        }
    }

    inner class ItemFolderGrid3ViewHolder(
        binding: ItemFolderListGrid3Binding
    ) : BaseFolderViewHolder<ItemFolderListGrid3Binding>(binding) {
        override fun initViewHolder() = with(binding) {
            ivFolder.setImageDrawable(ResourcesCompat.getDrawable(context.resources, resId, null))
            tvFolder.text = data.name
        }
    }

    inner class ItemFolderLinearViewHolder(
        binding: ItemFolderListLinearBinding
    ) : BaseFolderViewHolder<ItemFolderListLinearBinding>(binding) {
        override fun initViewHolder() = with(binding) {
            ivFolder.setImageDrawable(ResourcesCompat.getDrawable(context.resources, resId, null))
            tvFolder.text = data.name
        }
    }
}