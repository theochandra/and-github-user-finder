package com.android.githubuserfinder.presentation.wrapper

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

abstract class WrapperTextWatcher(
    private val editText: EditText
) : TextWatcher {

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // default implementation ignored
    }

    override fun afterTextChanged(s: Editable?) { }

}