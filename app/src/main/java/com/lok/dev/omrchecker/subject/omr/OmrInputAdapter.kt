package com.lok.dev.omrchecker.subject.omr

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.GridLayout
import android.widget.GridLayout.spec
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.GridLayoutManager
import com.lok.dev.commonbase.BaseAdapter
import com.lok.dev.commonbase.BaseViewHolder
import com.lok.dev.commonutil.convertDpToPx
import com.lok.dev.coredatabase.entity.ProblemTable
import com.lok.dev.omrchecker.R
import com.lok.dev.omrchecker.databinding.ItemOmrInputBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class OmrInputAdapter(
    private val context: Context,
    private val lifecycleCoroutineScope: LifecycleCoroutineScope,
    private val selectNum: Int,
    private val onClick: (Pair<Boolean, Int>) -> Unit
) : BaseAdapter<ItemOmrInputBinding, MutableList<Pair<Int, Boolean>>>(lifecycleCoroutineScope) {

    override fun getBinding(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemOmrInputBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
    )

    private val itemAdapterList = mutableListOf<OmrItemInputAdapter>()

    fun set(list: List<MutableList<Pair<Int, Boolean>>>, selectNum: Int) {
        itemAdapterList.clear()
        list.forEachIndexed { index, _ ->
            val itemAdapter = OmrItemInputAdapter(this@OmrInputAdapter.context, lifecycleCoroutineScope, index) {

                val problemNum = it.first
                val answerNum = it.second
                val isSelected = it.third

                adapterList[problemNum][answerNum.minus(1)] = Pair(answerNum, isSelected)

                onClick.invoke(Pair(isSelected, problemNum))
            }
            itemAdapterList.add(itemAdapter)
        }
        set(list)
    }

    inner class ViewHolder(
        override val binding: ItemOmrInputBinding
    ) : BaseViewHolder<ItemOmrInputBinding>(binding) {
        override fun bind(position: Int) = with(binding) {

            omrNum.text = position.plus(1).toString()

            val spanCnt = if(selectNum < 5) selectNum else 5
            omrInputList.layoutManager = GridLayoutManager(context, spanCnt, GridLayoutManager.VERTICAL, false)
            omrInputList.adapter = itemAdapterList[position]

            itemAdapterList[position].set(adapterList[position])
        }

    }
}