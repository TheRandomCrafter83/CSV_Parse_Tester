package com.coderzf1.csvparsetester

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


/* SAMPLE DATA: copy/paste this into a csv file

"Account Name","Processed Date","Description","Check Number","Credit or Debit","Amount"
"CHECKING",2024-02-29,"WITHDRAWAL CHECK CARD FIVE STAR FOOD",,"Debit",0.85
"CHECKING",2024-02-29,"WITHDRAWAL CHECK CARD GOOGLE ",,"Debit",5.99
"CHECKING",2024-02-29,"DEPOSIT PayCheck",,"Credit",663.87
"CHECKING",2024-02-29,"WITHDRAWAL POS #1",,"Debit",4.70
"CHECKING",2024-02-29,"WITHDRAWAL CHECK CARD GOOGLE",,"Debit",5.99
"CHECKING",2024-02-07,"WITHDRAWAL POS #2",,"Debit",0.50
"CHECKING",2024-02-06,"WITHDRAWAL POS #3",,"Debit",10.00
"CHECKING",2024-02-06,"WITHDRAWAL POS #4",,"Debit",5.85

*/