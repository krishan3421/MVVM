package com.mvvm.ui.home.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mvvm.data.repositories.UserRepository

class ProfileViewModel(repository: UserRepository) : ViewModel() {
  val user =repository.getUser()
}