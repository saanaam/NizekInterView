package com.sanam.nizekinterview.domain.userAcount.useCase

import com.sanam.nizekinterview.domain.userAcount.model.UserInfoEns
import com.sanam.nizekinterview.domain.userAcount.repository.UserAccountRepository

class InsertUserAccountUseCase(
    private val UserAccountRepository: UserAccountRepository
) {
    suspend operator fun invoke(userAccountEns: UserInfoEns) =
        UserAccountRepository.insertUserAccount(userAccountEns)
}