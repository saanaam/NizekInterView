package com.sanam.nizekinterview.common.exception

import com.sanam.nizekinterview.common.state.ErrorState

abstract class PresentationExceptionDecorator :
    PresentationExceptionAbstractFactory {

    private val exceptionFactoryException: PresentationExceptionAbstractFactory by lazy {
        PresentationExceptionFactory()
    }

    override fun create(exception: Throwable, title: Int?): ErrorState {
        val createException = createException(exception, title)
        return createException?.copy() ?: exceptionFactoryException.create(exception, title)
    }

    protected abstract fun createException(exception: Throwable, title: Int?): ErrorState?
}


