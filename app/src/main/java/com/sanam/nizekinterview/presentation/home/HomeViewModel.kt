package com.sanam.nizekinterview.presentation.home

import androidx.lifecycle.viewModelScope
import com.sanam.nizekinterview.common.base.AbstractViewModel
import com.sanam.nizekinterview.common.utils.AppDispatchers
import com.sanam.nizekinterview.common.utils.isNotNullOrEmpty
import com.sanam.nizekinterview.domain.userAcount.repository.UserAccountRepository
import com.sanam.nizekinterview.domain.userAcount.useCase.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dispatchers: AppDispatchers,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val repository: UserAccountRepository
) : AbstractViewModel<HomeViewState>(
    HomeViewState()
) {

    fun showLoading(loading: Boolean) {
        _viewState.postValue(
            HomeViewState(
                loading = loading
            )
        )
    }

    fun checkToken() {
        _viewState.postValue(
            HomeViewState(
                loading = false,
                token = repository.getToken()
            )
        )
    }

    fun hasToken() = repository.getToken().isNotNullOrEmpty()

    fun clearToken() {
        repository.clearToken()
    }

    private fun navigateToLogin() {
        _viewState.postValue(
            HomeViewState(
                navigation =
                HomeFragmentDirections.actionHomeToLogin(
                    null, null
                )
            )
        )
    }

    fun logOut() {
        clearToken()
        navigateToLogin()
    }

    fun navigateToRegister() {
        _viewState.postValue(
            HomeViewState(
                navigation =
                HomeFragmentDirections.actionHomeToRegister(
                )
            )
        )
    }

    fun getUserInfo(token: String) {
        viewModelScope.launch(dispatchers.io) {
            getUserInfoUseCase(token)?.let { userInfoEns ->
                _viewState.postValue(
                    HomeViewState(
                        userInfo = userInfoEns
                    )
                )
            }
        }
    }

}