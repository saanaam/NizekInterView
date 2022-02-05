package com.sanam.nizekinterview.common.state

import androidx.annotation.StringRes

data class ErrorState(
    val message: String = "",
    @StringRes
    val extraInfo: Int? = null,
    @StringRes
    val errorTitle: Int? = null,
    val isAuthorizationError: Boolean = false
)