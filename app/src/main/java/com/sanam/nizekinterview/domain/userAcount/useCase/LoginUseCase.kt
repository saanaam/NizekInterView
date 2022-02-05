package com.sanam.nizekinterview.domain.userAcount.useCase

import com.sanam.nizekinterview.domain.userAcount.model.UserInfoEns
import com.sanam.nizekinterview.domain.userAcount.repository.UserAccountRepository

class LoginUseCase(
    private val UserAccountRepository: UserAccountRepository
) {
    suspend operator fun invoke(userAccountEns: UserInfoEns): UserInfoEns? {
        return UserAccountRepository.login(userAccountEns)
    }
}

