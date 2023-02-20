package com.lok.dev.commonutil

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.setTextWatcher(beforeChanged: (String) -> Unit = {}, afterChanged: (String) -> Unit) = addTextChangedListener(
    object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = beforeChanged.invoke(s?.toString().orEmpty())
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
        override fun afterTextChanged(s: Editable?) = afterChanged.invoke(s?.toString().orEmpty())
    }
)