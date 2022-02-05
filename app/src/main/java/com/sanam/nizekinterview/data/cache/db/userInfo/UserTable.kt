package com.sanam.nizekinterview.data.cache.db.userInfo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserTable(
    @PrimaryKey(autoGenerate = true) var id: Long? = null,
    var userName: String,
    var fullName: String?,
    val password: String
)