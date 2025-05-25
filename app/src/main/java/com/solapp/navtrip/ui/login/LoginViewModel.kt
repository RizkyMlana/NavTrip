package com.solapp.navtrip.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solapp.navtrip.data.pref.UserModel
import com.solapp.navtrip.data.remote.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class LoginViewModel (private val repository: UserRepository) : ViewModel() {
    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
    fun getSession(): Flow<UserModel> {
        return repository.getSession()
    }
}