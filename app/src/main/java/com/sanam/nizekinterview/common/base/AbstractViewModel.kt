package com.sanam.nizekinterview.common.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.nar.bimito.domain.exception.AuthorizationException
import com.sanam.nizekinterview.common.SingleLiveEvent
import com.sanam.nizekinterview.common.exception.PresentationExceptionAbstractFactory
import com.sanam.nizekinterview.common.exception.PresentationExceptionDecorator
import com.sanam.nizekinterview.common.exception.PresentationExceptionFactory
import com.sanam.nizekinterview.common.state.ViewState
import java.net.UnknownHostException


abstract class AbstractViewModel<V : ViewState>(
    viewState: V,
) : ViewModel(){

    val exceptionFactory: PresentationExceptionAbstractFactory = PresentationExceptionFactory()

    open var exceptionFactoryDecorator: PresentationExceptionDecorator? = null

    protected val _viewState = SingleLiveEvent<V>()
    val viewState: LiveData<V> = _viewState

    init {
        _viewState.value = viewState
        _viewState.postValue(viewState)
    }

    protected fun isAuthenticationError(throwable: Throwable): Boolean {
        return (throwable is AuthorizationException)
    }

    protected fun isNetworkConnectionError(throwable: Throwable): Boolean {
        return (throwable is UnknownHostException)
    }

    open fun clearViewModel() {

    }


}

