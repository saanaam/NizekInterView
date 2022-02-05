package com.sanam.nizekinterview.data.cache.sharepreference.token


import android.content.Context
import com.sanam.nizekinterview.data.cache.sharepreference.AbstractSharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenSharedPreferences @Inject constructor(@ApplicationContext context: Context) :
    AbstractSharedPreferences(context) {
    companion object {
        private const val PARAM_KEY = "token_key"
    }

    var accessToken: String?
        get() = sharedPreferences.getString(PARAM_KEY, "")
        set(value) = sharedPreferences.edit().putString(PARAM_KEY, value).apply()

    fun clearToken() {
        accessToken = null
    }

}
