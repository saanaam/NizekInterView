package com.sanam.nizekinterview.common.exception

import androidx.annotation.StringRes

open class PresentationException(
    message: String?
) : RuntimeException(message)


const val INVALID_VIEW_TYPE_EXCEPTION =
    "data list contains one or more view with view type that is not handled by this adapter"

class InvalidViewTypeException() : PresentationException(INVALID_VIEW_TYPE_EXCEPTION)


const val UNSUPPORTED_STRATEGY_EXCEPTION_MESSAGE =
    "there is not a defined strategy for selected input type"

class UnSupportedStrategyException : PresentationException(
    UNSUPPORTED_STRATEGY_EXCEPTION_MESSAGE
)


open class PresentationRuntimeException(
    @StringRes val messageResource: Int,
    @StringRes val errorTitle: Int? = null
) :
    PresentationException(null)




