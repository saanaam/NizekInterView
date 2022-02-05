package com.sanam.nizekinterview.data.cache.sharepreference.token

import javax.inject.Inject

class TokenDataSource @Inject constructor(
    private val tokenSharedPreferences: TokenSharedPreferences
) {

    fun getToken(): String? {
        return tokenSharedPreferences.accessToken
    }

    fun setToken(token: String) {
        tokenSharedPreferences.accessToken = token
    }

    fun clearToken() {
        tokenSharedPreferences.clearToken()
    }

}