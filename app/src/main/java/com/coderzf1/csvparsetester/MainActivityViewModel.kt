package com.coderzf1.csvparsetester

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.InputStream
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivityViewModel: ViewModel() {

    var transactions:MutableStateFlow<List<Transaction>> = MutableStateFlow(emptyList())
        private set

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadCsvDataFromInputStream(inputStream: InputStream){
        viewModelScope.launch {
            val rows: List<Map<String,String>> =csvReader().readAllWithHeader(ips = inputStream)
            rows.map {item ->
                transactions.update {
                    it + Transaction(
                        accountName = item["Account Name"],
                        processedDate = item["Processed Date"]?.let { it1 -> convertStringToDate(it1) },
                        description = item["Description"],
                        checkNumber = item["Check Number"],
                        creditOrDebit = if(item["Credit or Debit"].equals("Debit")) CreditDebit.DEBIT else CreditDebit.CREDIT,
                        amount = item["Amount"]?.let {amt ->
                            NumberFormat.getInstance().parse(amt)?.toDouble() ?: 0.toDouble()
                        }
                    )
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertStringToDate(dateString: String): Date? {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale(androidx.compose.ui.text.intl.Locale.current.language))
        return formatter.parse(dateString)
    }
}