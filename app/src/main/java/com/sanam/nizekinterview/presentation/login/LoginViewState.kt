package com.sanam.nizekinterview.presentation.login

import androidx.navigation.NavDirections
import com.sanam.nizekinterview.common.state.ErrorState
import com.sanam.nizekinterview.common.state.ViewState

data class LoginViewState(
    override val error: ErrorState? = null,
    override val navigation: NavDirections? = null,
    override val loading: Boolean? = null
) : ViewState