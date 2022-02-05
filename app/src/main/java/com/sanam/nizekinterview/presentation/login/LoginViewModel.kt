package com.sanam.nizekinterview.presentation.login

import androidx.lifecycle.viewModelScope
import com.sanam.nizekinterview.common.base.AbstractViewModel
import com.sanam.nizekinterview.common.state.ErrorState
import com.sanam.nizekinterview.common.utils.AppDispatchers
import com.sanam.nizekinterview.domain.userAcount.model.UserInfoEns
import com.sanam.nizekinterview.domain.userAcount.useCase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val dispatchers: AppDispatchers,
    private val loginUseCase: LoginUseCase
) : AbstractViewModel<LoginViewState>(
    LoginViewState()
) {
    private val _userName = MutableStateFlow("")
    private val _password = MutableStateFlow("")

    fun setUserName(userName: String?) {
        userName?.let {
            _userName.value = userName
        }
    }

    private fun getUserName(): String {
        var userName = ""
        viewModelScope.launch {
            _userName.collect {
                userName = it
            }
        }
        return userName
    }

    fun setPassword(password: String?) {
        password?.let {
            _password.value = it
        }
    }

    private fun getPassword(): String {
        var password = ""
        viewModelScope.launch {
            _password.collect {
                password = it
            }
        }
        return password
    }


    val isSubmitEnabled: Flow<Boolean> by lazy {
        combine(
            _userName,
            _password
        ) { name, pass ->
            return@combine name.isNotEmpty() and pass.isNotEmpty()
        }
    }

    fun login(
        userAccountEns: UserInfoEns = UserInfoEns(
            userName = getUserName(),
            password = getPassword()
        )
    ) {
        viewModelScope.launch(dispatchers.io) {
            loginUseCase(userAccountEns).also { userInfoEns ->
                userInfoEns?.let {
                    _viewState.postValue(
                        LoginViewState(
                            navigation =
                            LoginFragmentDirections.actionLoginToHome(
                                it
                            )
                        )
                    )
                } ?: run {
                    _viewState.postValue(
                        LoginViewState(
                            error =
                            ErrorState("userName or password is incorrect")
                        )
                    )
                }
            }
        }
    }

}