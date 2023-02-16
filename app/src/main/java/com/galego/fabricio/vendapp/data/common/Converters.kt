package com.galego.fabricio.vendapp.data.common

import android.content.Context
import androidx.room.TypeConverter
import com.galego.fabricio.vendapp.R
import java.text.NumberFormat
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

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

        fun monthToString(month: Int) = String.format("%02d", month + 1)
    }

}