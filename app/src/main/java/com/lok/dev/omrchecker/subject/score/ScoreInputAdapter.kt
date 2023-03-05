package com.lok.dev.omrchecker.subject.score

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import com.lok.dev.commonbase.BaseAdapter
import com.lok.dev.commonbase.BaseViewHolder
import com.lok.dev.coredatabase.entity.AnswerTable
import com.lok.dev.omrchecker.databinding.ItemScoreInputBinding

class ScoreInputAdapter(
    private val context: Context,
    lifecycleCoroutineScope: LifecycleCoroutineScope,
    private val scoreChange: (Pair<Int, Double>) -> Unit
) : BaseAdapter<ItemScoreInputBinding, AnswerTable>(lifecycleCoroutineScope) {

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

            if(data.score != 0.0) editScore.setText(data.score.toString())
            else editScore.setText("")

            editScore.removeTextChangedListener(textWatcher)
            editScore.addTextChangedListener(textWatcher)

            // TODO double 형으로 되어서 마지막 소수점 나오는 현상 해결 필요
        }

        private val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            override fun afterTextChanged(s: Editable?) {
                val score = s.toString()
                val modScore = when {
                    score.isEmpty() -> 0.0
                    score.last() == '.' -> score.replace(".", "").toDouble()
                    else -> score.toDouble()
                }
                changeData(adapterPosition, adapterList[adapterPosition], modScore.toString())
                scoreChange.invoke(Pair(adapterList[adapterPosition].no, modScore))
            }
        }


        private fun changeData(position: Int, data: AnswerTable, score: String) {
            adapterList[position] = data.copy(score = score.toDouble())
        }
    }

}