package com.mvvm.ui.auth

import androidx.lifecycle.LiveData
import com.mvvm.data.db.entities.User

interface AutListener {
    fun onStarted()
    fun onSuccess(user: User)
    fun onFailure(message:String)
}