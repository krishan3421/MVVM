package com.mvvm.ui.auth

import android.content.Intent
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mvvm.data.db.entities.User
import com.mvvm.data.repositories.UserRepository
import com.mvvm.util.ApiException
import com.mvvm.util.Coroutines
import com.mvvm.util.NoInternetException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthViewModel(
       private val repository: UserRepository
) :ViewModel(){
    fun getLoggedInUser()=repository.getUser()

    suspend fun userLogin(
            email: String,
            password: String
    ) = withContext(Dispatchers.IO) { repository.userLogin(email, password) }

    suspend fun userSignup(
            name: String,
            email: String,
            password: String
    ) = withContext(Dispatchers.IO) { repository.userSignUp(name, email, password) }

    suspend fun saveLoggedInUser(user: User) = repository.saveUser(user)

}