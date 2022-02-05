package com.sanam.nizekinterview.common.state

import androidx.navigation.NavDirections
import com.sanam.nizekinterview.common.state.ErrorState

interface ViewState {
    val error: ErrorState?
    val navigation: NavDirections?
    val loading : Boolean?
}