package com.sanam.nizekinterview.data.cache.db.userInfo

import javax.inject.Inject

class UserInfoCacheDataSource @Inject constructor(private val userDao: UserInfoDao) {
    fun insertUseName(userName: UserTable) {
        userDao.insertUseName(userName)
    }
    fun isRowIsExist(userName: String) =
        userDao.isRowIsExist(userName)

    fun isPasswordCorrect(userName: String, password: String) =
        userDao.isPasswordCorrect(userName, password)

    fun getUserInfo(userName: String): UserTable? =
        userDao.getUserInfo(userName)
}