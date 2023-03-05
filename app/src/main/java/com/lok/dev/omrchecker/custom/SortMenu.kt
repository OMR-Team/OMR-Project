package com.lok.dev.omrchecker.custom

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.PopupWindow
import com.lok.dev.omrchecker.databinding.MenuSortBinding

enum class SortStandard {
    NEWEST, ALPHABET, ADD
}

class SortMenu(
    context: Context,
    private val view: View,
    private val onClick: (SortStandard) -> Unit,
    private val onDismiss: () -> Unit,
) : PopupWindow() {

    private val binding = MenuSortBinding.inflate(LayoutInflater.from(context))
    private val window = PopupWindow(binding.root, WRAP_CONTENT, WRAP_CONTENT, true)

    init {
        addListeners()
    }

    fun addListeners() {
        binding.btnSortNewest.setOnClickListener {
            onClick.invoke(SortStandard.NEWEST)
            window.dismiss()
        }

        binding.btnSortAlphabet.setOnClickListener {
            onClick.invoke(SortStandard.ALPHABET)
            window.dismiss()
        }

        binding.btnSortAdd.setOnClickListener {
            onClick.invoke(SortStandard.ADD)
            window.dismiss()
        }

        window.setOnDismissListener {
            onDismiss.invoke()
        }
    }

    fun show() {
        window.showAsDropDown(view)
    }

}