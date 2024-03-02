package com.coderzf1.csvparsetester

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.InputStream

class MainActivityViewModel: ViewModel() {

    var transactions:MutableStateFlow<List<Transaction>> = MutableStateFlow(emptyList())
        private set

    fun loadCsvDataFromInputStream(inputStream: InputStream){
        viewModelScope.launch {
            val rows: List<Map<String,String>> =csvReader().readAllWithHeader(ips = inputStream)
            rows.map {item ->
                transactions.update {
                    it + Transaction(
                        accountName = item["Account Name"],
                        processedDate = null,
                        description = item["Description"],
                        checkNumber = item["Check Number"],
                        creditOrDebit = if(item["Credit or Debit"].equals("Debit")) CreditDebit.DEBIT else CreditDebit.CREDIT,
                        amount = item["Amount"]?.toDoubleOrNull()
                    )
                }
            }
        }
    }
}