package com.mvvm.data.networks.responses

import com.mvvm.data.db.entities.User

data class AutResponse(
    val isSuccessful:Boolean?,
    val message:String?,
    val user: User?
)