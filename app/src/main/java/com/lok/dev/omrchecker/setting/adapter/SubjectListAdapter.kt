package com.lok.dev.omrchecker.setting.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import com.lok.dev.commonbase.BaseAdapter
import com.lok.dev.commonbase.BaseViewHolder
import com.lok.dev.commonutil.AppConfig
import com.lok.dev.commonutil.getDrawableString
import com.lok.dev.coredatabase.entity.SubjectTable
import com.lok.dev.omrchecker.databinding.ItemSubjectBinding

class SubjectListAdapter(
    val context: Context,
    lifecycleCoroutineScope: LifecycleCoroutineScope,
    val clickCallback: (SubjectTable) -> Unit
) : BaseAdapter<ItemSubjectBinding, SubjectTable>(lifecycleCoroutineScope) {

    override fun getBinding(parent: ViewGroup, viewType: Int): BaseViewHolder<ItemSubjectBinding> =
        ItemSubjectListViewHolder(
            ItemSubjectBinding.inflate(
                LayoutInflater.from(context),
                parent,
                false
            )
        )

    inner class ItemSubjectListViewHolder(
        binding: ItemSubjectBinding
    ) : BaseViewHolder<ItemSubjectBinding>(binding) {
        override fun bind(position: Int): Unit = with(binding) {
            val data = adapterList[position]

            AppConfig.folderData.firstOrNull { data.folderId == it.id }?.let {
                binding.imgFolder.setImageResource(context.getDrawableString("folder_${it.fileName}"))
            }
            tvSubjectTitle.text = data.name
            rootLayout.setOnClickListener {
                clickCallback.invoke(data)
            }

            rootLayout.setOnLongClickListener {
                false
            }
        }
    }
}