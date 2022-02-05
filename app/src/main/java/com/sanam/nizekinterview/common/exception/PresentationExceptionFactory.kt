package com.sanam.nizekinterview.common.exception

import androidx.annotation.StringRes
import com.nar.bimito.domain.exception.AuthorizationException
import com.nar.bimito.domain.exception.IllegalDataException
import com.nar.bimito.domain.exception.NetworkException
import com.sanam.nizekinterview.R
import com.sanam.nizekinterview.common.state.ErrorState
import com.sanam.nizekinterview.common.utils.containsEnglish
import java.net.UnknownHostException

class PresentationExceptionFactory : PresentationExceptionAbstractFactory {

    override fun create(exception: Throwable, @StringRes title: Int?): ErrorState {
        if (exception is UnknownHostException) {
            return ErrorState(
                extraInfo = R.string.exception_network_unavailable_message,
                errorTitle = R.string.exception_network_unavailable_title
            )
        } else if (exception is NetworkException) {
            return checkNetworkException(exception, title)
        } else if (exception is IllegalDataException) {
            return ErrorState(
                extraInfo = R.string.exception_illegal_data_message,
                errorTitle = title
            )
        } else if (exception is AuthorizationException) {
            return ErrorState(
                extraInfo = R.string.exception_authorization_message,
                errorTitle = R.string.exception_authorization_title,
                isAuthorizationError = true
            )
        } else if (exception.message.isNullOrEmpty() || exception.message.containsEnglish()) {
            return ErrorState(
                extraInfo = title,
                errorTitle = R.string.exception_unknown_title
            )
        } else if (exception is PresentationRuntimeException) {
            return ErrorState(
                extraInfo = exception.messageResource,
                errorTitle = exception.errorTitle
            )
        }
        return ErrorState(
            message = exception.message!!
        )

    }

    private fun checkNetworkException(
        exception: NetworkException,
        @StringRes resourceId: Int?
    ): ErrorState {
        return if (exception.message.isNullOrEmpty() || exception.message.containsEnglish()) {
            ErrorState(
                extraInfo = resourceId,
                errorTitle = R.string.exception_unknown_title
            )
        } else {
            ErrorState(
                message = exception.message,
                errorTitle = resourceId
            )
        }
    }

}