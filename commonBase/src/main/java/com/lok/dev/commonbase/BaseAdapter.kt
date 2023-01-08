package com.lok.dev.commonbase

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<B : ViewDataBinding, T> : RecyclerView.Adapter<BaseViewHolder<B>>() {

    protected open val adapterList = mutableListOf<T>()

    protected abstract fun getBinding(parent : ViewGroup, viewType : Int) : BaseViewHolder<B>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = getBinding(parent, viewType)

    override fun onBindViewHolder(holder: BaseViewHolder<B>, position: Int) = holder.bind(position)

    override fun getItemCount(): Int {
        return adapterList.size
    }

    open fun set(list : List<T>) {
        adapterList.clear()
        adapterList.addAll(list)
        notifyDataSetChanged()
    }

}

abstract class BaseViewHolder<out B : ViewDataBinding>(open val binding : B) : RecyclerView.ViewHolder(binding.root) {
    open fun bind(position : Int) = Unit
}