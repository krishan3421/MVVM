package com.mvvm.data.repositories

import com.mvvm.data.db.AppDatabase
import com.mvvm.data.db.entities.User
import com.mvvm.data.networks.MyApi
import com.mvvm.data.networks.SafeApiRequest
import com.mvvm.data.networks.responses.AutResponse
import retrofit2.Response

class UserRepository(
        private val api:MyApi,
        private val db:AppDatabase
) :SafeApiRequest(){
  suspend  fun userLogin(email:String,password:String):AutResponse{
        return  apiRequest { api.userLogin(email,password) }

    }
    suspend  fun userSignUp(name:String,email:String,password:String):AutResponse{
        return  apiRequest { api.userSignup(name,email,password) }

    }

    suspend fun saveUser(user: User)=db.getUserDao().upsert(user)
     fun getUser()=db.getUserDao().getuser()
}