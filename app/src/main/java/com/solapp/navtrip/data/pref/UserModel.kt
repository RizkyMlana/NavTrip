package com.solapp.navtrip.data.pref

data class UserModel(
    val name: String = "",
    val email: String,
    val token: String,
    val photoUrl: String = "",
    val isLogin: Boolean = false
){
    fun isNullOrEmpty(): Boolean {
        return email.isEmpty() || token.isEmpty() || !isLogin
    }
}