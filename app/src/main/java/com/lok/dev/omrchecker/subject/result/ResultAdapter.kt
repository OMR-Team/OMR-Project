package com.lok.dev.omrchecker.subject.result

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import com.lok.dev.commonbase.BaseAdapter
import com.lok.dev.commonbase.BaseViewHolder
import com.lok.dev.coredatabase.entity.ProblemTable
import com.lok.dev.omrchecker.databinding.ItemOmrResultBinding

class ResultAdapter(
    private val context: Context,
    lifecycleCoroutineScope: LifecycleCoroutineScope
) : BaseAdapter<ItemOmrResultBinding, ProblemTable>(lifecycleCoroutineScope) {

    override fun getBinding(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemOmrResultBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
    )

    inner class ViewHolder(
        override val binding: ItemOmrResultBinding
    ) : BaseViewHolder<ItemOmrResultBinding>(binding) {
        override fun bind(position: Int) {

            val data = adapterList[position]

        }
    }


}