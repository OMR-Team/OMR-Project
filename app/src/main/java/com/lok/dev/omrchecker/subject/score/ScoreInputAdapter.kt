package com.lok.dev.omrchecker.subject.score

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.recyclerview.widget.DiffUtil
import com.lok.dev.commonbase.BaseAdapter
import com.lok.dev.commonbase.BaseViewHolder
import com.lok.dev.commonutil.setTextWatcher
import com.lok.dev.commonutil.visible
import com.lok.dev.coredatabase.entity.AnswerTable
import com.lok.dev.omrchecker.databinding.ItemScoreInputBinding

class ScoreInputAdapter(
    private val context: Context,
    private val scoreChange: (Pair<Int, Double>) -> Unit
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

            if(data.score != 0.0) editScore.setText(data.score.toString())
            else editScore.setText("")

            editScore.setTextWatcher { score ->
                val modScore = when {
                    score.isEmpty() -> 0.0
                    score.last() == '.' -> score.replace(".", "").toDouble()
                    else -> score.toDouble()
                }
                changeData(position, data, modScore.toString())
                scoreChange.invoke(Pair(data.no, modScore))
            }

            // TODO double 형으로 되어서 마지막 소수점 나오는 현상 해결 필요
        }

        private fun changeData(position: Int, data: AnswerTable, score: String) {
            adapterList[position] = data.copy(score = score.toDouble())
        }

    }
}