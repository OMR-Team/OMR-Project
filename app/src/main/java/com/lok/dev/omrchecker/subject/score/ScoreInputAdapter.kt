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
import com.lok.dev.commonutil.visible
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

            background.setOnClickListener {
                editScore.visible(false)
                showScore.visible(true)
                hideKeyboard(editScore)
            }

            editScore.setOnFocusChangeListener { v, hasFocus ->
                if(!hasFocus) {
                    val score = editScore.text.toString()
                    if(score.isNotEmpty()) {
                        val modScore = if (score.last() == '.') score.replace(".", "") else score
                        if (modScore.isNotEmpty()) {
                            changeData(position, data, modScore)
                        }
                    }
                    showScore.visible(true)
                    editScore.visible(false)
                    hideKeyboard(editScore)
                }
            }

            showScore.setOnClickListener {
                editScore.visible(true)
                showScore.visible(false)
                editScore.requestFocus()
                showKeyboard(editScore)
            }

            if(data.score != 0.0) showScore.text = data.score.toString()
            else showScore.text = ""

            // TODO double 형으로 되어서 마지막 소수점 나오는 현상 해결 필요
        }

        private fun changeData(position: Int, data: AnswerTable, score: String) {
            adapterList[position] = data.copy(score = score.toDouble())
            notifyItemChanged(position)
        }

        private fun showKeyboard(view: EditText) {
            val inputMethodManager: InputMethodManager = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }

        private fun hideKeyboard(view: EditText) {
            val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

    }
}