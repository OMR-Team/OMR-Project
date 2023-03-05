package com.lok.dev.omrchecker.setting.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.lok.dev.commonmodel.FolderData
import com.lok.dev.commonutil.getDrawableString
import com.lok.dev.commonutil.visible
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.databinding.ItemFolderImageBinding

class FolderAdapter(
    val context: Context
): BaseAdapter() {

    val adapterList = mutableListOf<FolderData>()

    override fun getCount() = adapterList.size

    override fun getItem(position: Int) = adapterList[position]

    override fun getItemId(position: Int) = position.toLong()

    @SuppressLint("ViewHolder", "UseCompatLoadingForDrawables")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = ItemFolderImageBinding.inflate(LayoutInflater.from(context), parent, false)
        val item = adapterList[position]

        if(item.color.isNotEmpty()) {
            binding.viewFolder.setBackgroundResource(R.drawable.bg_oval_white)
            binding.viewFolder.backgroundTintList = ColorStateList.valueOf(Color.parseColor(item.color))
        }
        else {
            val resId = context.getDrawableString("folder_${item.fileName}") ?: R.drawable.folder_black
            binding.viewFolder.setBackgroundResource(resId)
        }

        binding.viewSelect.visible(item.isSelect)
        binding.imgSelect.visible(item.isSelect)

        binding.root.setOnClickListener {
            if(!item.isSelect) {
                selectItem(position)
            }
        }

        return binding.root
    }

    fun set(list: List<FolderData>) {
        adapterList.clear()
        adapterList.addAll(list.map { it.copy() })
        notifyDataSetChanged()
    }

    private fun selectItem(position: Int) {
        val oldItemIndex = adapterList.indexOfFirst { it.isSelect }
        adapterList[oldItemIndex].isSelect = false
        adapterList[position].isSelect = true
        notifyDataSetChanged()
    }
}