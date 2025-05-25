package com.solapp.navtrip.di

import android.content.Context
import com.solapp.navtrip.data.pref.UserPreference
import com.solapp.navtrip.data.pref.dataStore
import com.solapp.navtrip.data.remote.UserRepository

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}