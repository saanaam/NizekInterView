package com.sanam.nizekinterview.domain.userAcount.useCase

import com.sanam.nizekinterview.domain.userAcount.model.UserInfoEns
import com.sanam.nizekinterview.domain.userAcount.repository.UserAccountRepository

class GetUserInfoUseCase(
    private val UserAccountRepository: UserAccountRepository
) {
    suspend operator fun invoke(userName: String): UserInfoEns? {
        return UserAccountRepository.getUserInfo(userName)
    }
}
