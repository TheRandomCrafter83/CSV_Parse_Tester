package com.coderzf1.csvparsetester

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.coderzf1.csvparsetester.ui.theme.CSVParseTesterTheme
import java.math.BigDecimal

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CSVParseTesterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel:MainActivityViewModel = viewModel()
                    val context = LocalContext.current
                    val filePicker = rememberLauncherForActivityResult(contract = ActivityResultContracts.OpenDocument()) {uri ->
                        if(uri != null){
                            context.contentResolver.openInputStream(uri)
                                ?.let { viewModel.loadCsvDataFromInputStream(it) }
                        }
                    }

                    val transactionList by viewModel.transactions.collectAsState()

                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp), contentAlignment = Alignment.Center){
                        if(transactionList.isEmpty()) {
                            Button(
                                onClick = {
                                    filePicker.launch(arrayOf("text/comma-separated-values"))
                                }) {
                                Text(text = "Load CSV File")
                            }
                        }
                        Column {
                            if(transactionList.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(16.dp))
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    items(
                                        transactionList,
                                        key = {
                                            it.uniqueKey
                                        }
                                    ){
                                        TransactionCard(transaction = it)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TransactionCard(transaction:Transaction){
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)

        ) {
            Text(
                text = transaction.description?:"",
                style = MaterialTheme.typography.headlineSmall,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(16.dp))
            val amt = transaction.amount?.let { BigDecimal.valueOf(it) }
//            val amt: BigDecimal? = BigDecimal.valueOf(120/394.40)
            val formattedDate:String =transaction.processedDate?.let { Utils.formatDate(it) }.toString()
            val formattedCurrency:String = amt?.let{Utils.formatCurrency(it)}.toString()
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = formattedDate,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = formattedCurrency,
                    color = when (transaction.creditOrDebit){
                        CreditDebit.CREDIT -> Color(0xFF640000)
                        CreditDebit.DEBIT -> Color(0xFF1B6400)
                        null -> Color.Black
                    }
                )
            }

        }
    }
}