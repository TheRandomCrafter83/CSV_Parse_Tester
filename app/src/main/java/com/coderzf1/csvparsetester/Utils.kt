package com.coderzf1.csvparsetester

import androidx.compose.ui.text.intl.Locale
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date

class Utils {
    companion object {
        fun formatDate(date: Date): String {
            val formatter = SimpleDateFormat("MMMM dd, yyyy", java.util.Locale(Locale.current.language))
            return formatter.format(date).toString()
        }


        fun formatCurrency(amount:Double):String{
            val formatter = NumberFormat.getCurrencyInstance()
            formatter.roundingMode = RoundingMode.HALF_UP
            return formatter.format(amount)
        }


        fun formatCurrency(amount: BigDecimal):String{
            val formatter = NumberFormat.getCurrencyInstance()
            formatter.roundingMode = RoundingMode.HALF_UP
            return formatter.format(amount)
        }
    }
}