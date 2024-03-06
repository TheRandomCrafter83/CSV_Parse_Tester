package com.coderzf1.csvparsetester

import androidx.compose.ui.text.intl.Locale
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
            return NumberFormat.getCurrencyInstance().format(amount)
        }
    }
}