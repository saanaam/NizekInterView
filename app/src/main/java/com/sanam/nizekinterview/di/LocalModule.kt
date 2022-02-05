package com.sanam.nizekinterview.di

import android.content.Context
import com.sanam.nizekinterview.data.cache.db.AppDatabase
import com.sanam.nizekinterview.data.cache.db.userInfo.UserInfoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {


    //APP DATABASE
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.buildDatabase(context)


    @Provides
    fun provideRequestDao(database: AppDatabase): UserInfoDao =
        database.requestDao()


}




