package com.coderzf1.csvparsetester

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.coderzf1.csvparsetester.ui.theme.CSVParseTesterTheme
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collectLatest
import java.util.UUID

class MainActivity : ComponentActivity() {
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

                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Column {
                            Button(
                                onClick = {
                                    filePicker.launch(arrayOf("text/comma-separated-values"))
                                }) {
                                Text(text = "Load CSV File")
                            }
                            if(transactionList.isNotEmpty()) {
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.spacedBy(16.dp),
                                    contentPadding = PaddingValues(16.dp)
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
        modifier = Modifier.fillMaxWidth(),

    ) {
        Text(text = transaction.description?:"",modifier = Modifier.padding(16.dp))
    }
}