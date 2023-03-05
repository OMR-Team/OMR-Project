package com.lok.dev.commonbase

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.lok.dev.commonutil.collect
import com.lok.dev.commonutil.throttleFirst

abstract class BaseAdapter<B : ViewDataBinding, T>(
    private val lifecycleScope: LifecycleCoroutineScope
) : RecyclerView.Adapter<BaseViewHolder<B>>() {

    val adapterList = mutableListOf<T>()

    protected abstract fun getBinding(parent: ViewGroup, viewType: Int): BaseViewHolder<B>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = getBinding(parent, viewType)

    override fun onBindViewHolder(holder: BaseViewHolder<B>, position: Int) = holder.bind(position)

    override fun getItemCount(): Int {
        return adapterList.size
    }

    open fun set(list: List<T>) {
        adapterList.clear()
        adapterList.addAll(list)
        notifyDataSetChanged()
    }

    protected fun View.throttleFirstClick(period: Long = 1500, action: (View) -> Unit) {
        throttleFirst(period).collect(lifecycleScope, action)
    }

}

abstract class BaseViewHolder<out B : ViewDataBinding>(open val binding: B) :
    RecyclerView.ViewHolder(binding.root) {
    open fun bind(position: Int) = Unit
}