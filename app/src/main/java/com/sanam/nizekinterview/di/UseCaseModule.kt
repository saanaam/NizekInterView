package com.sanam.nizekinterview.di

import com.sanam.nizekinterview.domain.userAcount.repository.UserAccountRepository
import com.sanam.nizekinterview.domain.userAcount.useCase.GetUserInfoUseCase
import com.sanam.nizekinterview.domain.userAcount.useCase.InsertUserAccountUseCase
import com.sanam.nizekinterview.domain.userAcount.useCase.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideInsertUserAccountUseCase(
        userAccountRepository: UserAccountRepository
    ): InsertUserAccountUseCase =
        InsertUserAccountUseCase(userAccountRepository)

    @Provides
    fun provideLoginUseCase(
        userAccountRepository: UserAccountRepository
    ): LoginUseCase =
        LoginUseCase(userAccountRepository)

    @Provides
    fun provideGetUserInfoUseCase(
        userAccountRepository: UserAccountRepository
    ): GetUserInfoUseCase =
        GetUserInfoUseCase(userAccountRepository)


}