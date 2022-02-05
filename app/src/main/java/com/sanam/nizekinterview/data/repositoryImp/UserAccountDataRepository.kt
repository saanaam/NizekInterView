package com.sanam.nizekinterview.data.repositoryImp

import com.sanam.nizekinterview.common.utils.isNotNullOrEmpty
import com.sanam.nizekinterview.data.cache.db.userInfo.UserInfoCacheDataSource
import com.sanam.nizekinterview.data.cache.sharepreference.token.TokenDataSource
import com.sanam.nizekinterview.data.repositoryImp.mapper.toUserAccountEns
import com.sanam.nizekinterview.data.repositoryImp.mapper.toUserAccountTable
import com.sanam.nizekinterview.domain.userAcount.model.UserInfoEns
import com.sanam.nizekinterview.domain.userAcount.repository.UserAccountRepository
import javax.inject.Inject

class UserAccountDataRepository @Inject constructor(
    private val tokenDataSource: TokenDataSource,
    private val userCacheDataSource: UserInfoCacheDataSource,
) : UserAccountRepository {

    override suspend fun insertUserAccount(userName: UserInfoEns): Boolean {
        //return false if user with same userName  exist
        if (userCacheDataSource.isRowIsExist(userName = userName.userName)) {
            return false
        } else {
            userCacheDataSource.insertUseName(
                userName = userName.toUserAccountTable()
            ).apply {
                val token = userName.userName;
                setToken(token)
                return true
            }
        }
    }

    override suspend fun login(userName: UserInfoEns): UserInfoEns? {
        userCacheDataSource.isPasswordCorrect(
            userName = userName.userName,
            password = userName.password
        ).apply {
            //if password is correct, return userInfo
            if (this) {
                setToken(userName.userName)
                return getUserInfo(userName.userName)
            }
        }
        return null
    }

    override suspend fun getUserInfo(userName: String): UserInfoEns? {
        return userCacheDataSource.getUserInfo(userName)?.toUserAccountEns()
    }

    override fun getToken(): String? {
        return tokenDataSource.getToken()
    }

    override fun clearToken() {
        tokenDataSource.clearToken()
    }

    override fun hasToken(): Boolean {
        return tokenDataSource.getToken().isNotNullOrEmpty()
    }

    private fun setToken(userName: String) {
        tokenDataSource.setToken(userName)
    }
}