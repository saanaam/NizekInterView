package com.sanam.nizekinterview.domain.userAcount.repository

import com.sanam.nizekinterview.domain.userAcount.model.UserInfoEns

interface UserAccountRepository {
    suspend fun insertUserAccount(userName: UserInfoEns): Boolean
    suspend fun getUserInfo(userName: String): UserInfoEns?
    suspend fun login(userName: UserInfoEns): UserInfoEns?
    fun getToken(): String?
    fun clearToken()
    fun hasToken(): Boolean
}