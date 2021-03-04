package com.mvvm.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mvvm.data.db.AppDatabase
import com.mvvm.data.db.entities.Quote
import com.mvvm.data.networks.MyApi
import com.mvvm.data.networks.SafeApiRequest
import com.mvvm.data.preferences.PreferenceProvider
import com.mvvm.util.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

private val MINIMUM_INTERVAL=6
class QuotesRepository (
private val api: MyApi,
private val db: AppDatabase,
private val pref:PreferenceProvider
) : SafeApiRequest(){

    private val quotes=MutableLiveData<List<Quote>>()
    init {
        quotes.observeForever{
            saveQuotes(it)
        }
    }

    private fun saveQuotes(quotes: List<Quote>){
         Coroutines.io {
             pref.saveLastsaveAt(LocalDateTime.now().toString())
             db.getQuoteDao().saveAllQuotes(quotes)
         }
    }

    suspend fun getQuotes():LiveData<List<Quote>>{
        return withContext(Dispatchers.IO){
            fetchQuote()
            db.getQuoteDao().getQuotes()
        }
    }
    private suspend  fun fetchQuote() {
        val lastSavedTime=pref.getLastSavedAt()
        if(lastSavedTime==null || isFetchNeeded(LocalDateTime.parse(lastSavedTime))){
            val response =  apiRequest { api.getQuotes() }
            quotes.postValue(response.quotes)
        }
        //return  apiRequest { api.getQuotes() }

    }
    private fun isFetchNeeded(savedAt: LocalDateTime):Boolean{
         return ChronoUnit.HOURS.between(savedAt,LocalDateTime.now()) > MINIMUM_INTERVAL
    }

}