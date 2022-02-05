package com.sanam.nizekinterview.di

import com.sanam.nizekinterview.data.cache.db.userInfo.UserInfoCacheDataSource
import com.sanam.nizekinterview.data.cache.sharepreference.token.TokenDataSource
import com.sanam.nizekinterview.data.repositoryImp.UserAccountDataRepository
import com.sanam.nizekinterview.domain.userAcount.repository.UserAccountRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideUserAccountDataRepository(
        tokenDataSource: TokenDataSource,
        userCacheDataSource: UserInfoCacheDataSource
    ): UserAccountRepository =
        UserAccountDataRepository(tokenDataSource, userCacheDataSource)


}