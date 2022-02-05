package com.sanam.nizekinterview.data.cache.db.userInfo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserInfoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUseName(userName: UserTable)

    @Query("SELECT EXISTS(SELECT * FROM UserTable WHERE userName = :userName)")
    fun isRowIsExist(userName: String): Boolean

    @Query("SELECT EXISTS(SELECT * FROM UserTable WHERE userName = :userName and password = :password)")
    fun isPasswordCorrect(userName: String, password: String): Boolean

    @Query("SELECT * FROM UserTable WHERE userName = :userName")
    fun getUserInfo(userName: String): UserTable?

    @Query("DELETE FROM UserTable")
    fun nukeTable()

}