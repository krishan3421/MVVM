package com.mvvm.data.networks.responses

import com.mvvm.data.db.entities.Quote

data class QuotesResponse(
        val isSuccessful:Boolean,
        val quotes:List<Quote>
)