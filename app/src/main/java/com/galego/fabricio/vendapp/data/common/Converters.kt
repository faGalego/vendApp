package com.galego.fabricio.vendapp.data.common

import java.text.NumberFormat

class Converters {

    companion object {

        fun doubleToMoneyString(value: Double) = NumberFormat.getCurrencyInstance().format(value)

        fun moneyStringToDouble(str: String) =
            if (str.isBlank())
                0.0
            else
                str.replace("R$", "")
                    .replace(".", "")
                    .replace(",", ".")
                    .trim()
                    .toDouble()
    }
}