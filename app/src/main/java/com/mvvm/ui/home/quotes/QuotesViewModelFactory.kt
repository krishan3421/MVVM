package com.mvvm.ui.home.quotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mvvm.data.repositories.QuotesRepository
import com.mvvm.data.repositories.UserRepository

class QuotesViewModelFactory(private val repository: QuotesRepository): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return QuotesViewModel(repository) as T
    }
}