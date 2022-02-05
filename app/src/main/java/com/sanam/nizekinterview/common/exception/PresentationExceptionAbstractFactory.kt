package com.sanam.nizekinterview.common.exception

import androidx.annotation.StringRes
import com.sanam.nizekinterview.common.state.ErrorState

interface PresentationExceptionAbstractFactory {
    fun create(exception: Throwable, @StringRes title: Int? = null): ErrorState
}