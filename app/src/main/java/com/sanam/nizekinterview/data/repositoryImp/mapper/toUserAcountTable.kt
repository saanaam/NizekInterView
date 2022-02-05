package com.sanam.nizekinterview.data.repositoryImp.mapper

import com.sanam.nizekinterview.data.cache.db.userInfo.UserTable
import com.sanam.nizekinterview.domain.userAcount.model.UserInfoEns

internal fun UserInfoEns.toUserAccountTable(): UserTable {
    return UserTable(userName = this.userName, fullName = this.fullName, password = this.password)
}

internal fun UserTable.toUserAccountEns(): UserInfoEns {
    return UserInfoEns(userName = this.userName, fullName = this.fullName, password = this.password)
}