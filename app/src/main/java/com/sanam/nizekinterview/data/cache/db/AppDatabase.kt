package com.sanam.nizekinterview.data.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sanam.nizekinterview.data.cache.db.userInfo.UserInfoDao
import com.sanam.nizekinterview.data.cache.db.userInfo.UserTable


@Database(
    version = 1,
    entities = [UserTable::class],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    // DAO
    abstract fun requestDao(): UserInfoDao

    companion object {
        fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "nizek.db"
            ).build()
    }
}