package com.sanam.nizekinterview.presentation.home

import androidx.navigation.NavDirections
import com.sanam.nizekinterview.common.state.ErrorState
import com.sanam.nizekinterview.common.state.ViewState
import com.sanam.nizekinterview.domain.userAcount.model.UserInfoEns

data class HomeViewState(
    val token : String? = null,
    val userInfo : UserInfoEns? = null,
    override val error: ErrorState? = null,
    override val navigation: NavDirections? = null,
    override val loading: Boolean? = null
) : ViewState