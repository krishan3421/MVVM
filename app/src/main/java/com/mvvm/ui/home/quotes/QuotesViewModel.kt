package com.mvvm.ui.home.quotes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mvvm.data.repositories.QuotesRepository
import com.mvvm.util.lazyDeferred

class QuotesViewModel(repository: QuotesRepository): ViewModel() {

    val quotes by lazyDeferred {
        repository.getQuotes()
    }
}