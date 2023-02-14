package com.lok.dev.omrchecker.subject.score

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lok.dev.commonbase.BaseAdapter
import com.lok.dev.commonbase.BaseViewHolder
import com.lok.dev.commonutil.setTextWatcher
import com.lok.dev.coredatabase.entity.AnswerTable
import com.lok.dev.omrchecker.databinding.ItemScoreInputBinding

class ScoreInputAdapter(
    private val context: Context
) : BaseAdapter<ItemScoreInputBinding, AnswerTable>() {

    override fun getBinding(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemScoreInputBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
    )

    inner class ViewHolder(
        override val binding: ItemScoreInputBinding
    ) : BaseViewHolder<ItemScoreInputBinding>(binding) {
        override fun bind(position: Int) = with(binding) {

            val data = adapterList[position]

            numScore.text = data.no.toString()

            editScore.setTextWatcher {
                Log.d("123123123", "${data.no} 번째 입력 텍스트 $it")
            }

        }
    }
}