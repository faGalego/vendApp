package com.galego.fabricio.vendapp.data.common

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.NumberFormat

class MoneyTextWatcher(
    private val editText: EditText
) : TextWatcher {

    private var current = ""

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

        if (s.toString() != current) {
            editText.removeTextChangedListener(this)
            val cleanString: String = s.toString()
                .replace("[R$,.]".toRegex(), "")
                .replace("\\s+".toRegex(), "")

            if (cleanString.isNotEmpty()) {
                try {
                    val parsed: Double = cleanString.toDouble()
                    val formatted = NumberFormat.getCurrencyInstance().format(parsed / 100)

                    current = formatted

                    editText.setText(formatted)
                    editText.setSelection(formatted.length)

                } catch (e: NumberFormatException) {
                }
            }
            editText.addTextChangedListener(this)
        }
    }

    override fun afterTextChanged(p0: Editable?) {}
}