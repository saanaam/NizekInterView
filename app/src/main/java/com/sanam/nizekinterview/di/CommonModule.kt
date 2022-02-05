package com.sanam.nizekinterview.di

import com.sanam.nizekinterview.common.utils.AppDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers


@InstallIn(SingletonComponent::class)
@Module
class CommonModule {
    @Provides
    fun provideAppDispatcher() =
        AppDispatchers(
            Dispatchers.Main,
            Dispatchers.IO
        )
}