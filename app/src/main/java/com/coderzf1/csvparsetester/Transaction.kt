package com.coderzf1.csvparsetester

import java.util.Calendar
import java.util.Date
import java.util.UUID

data class Transaction(
    var accountName:String? = null,
    var processedDate: Date? = null,
    var description:String? = null,
    var checkNumber:String? = null,
    var creditOrDebit: CreditDebit? = null,
    var amount: Double? = null
){
    val uniqueKey:String = UUID.randomUUID().toString()
}
