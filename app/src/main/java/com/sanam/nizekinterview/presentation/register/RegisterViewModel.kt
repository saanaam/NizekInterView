package com.sanam.nizekinterview.presentation.register

import androidx.lifecycle.viewModelScope
import com.sanam.nizekinterview.common.base.AbstractViewModel
import com.sanam.nizekinterview.common.state.ErrorState
import com.sanam.nizekinterview.common.utils.AppDispatchers
import com.sanam.nizekinterview.domain.userAcount.model.UserInfoEns
import com.sanam.nizekinterview.domain.userAcount.useCase.InsertUserAccountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val dispatchers: AppDispatchers,
    private val insertUserAccountUseCase: InsertUserAccountUseCase
) :
    AbstractViewModel<RegisterViewState>(RegisterViewState()) {
    private val _name = MutableStateFlow("")
    private val _userName = MutableStateFlow("")
    private val _password = MutableStateFlow("")

    fun setName(name: String?) {
        name?.let {
            _name.value = name
        }
    }

    private fun getName(): String {
        var name = ""
        viewModelScope.launch {
            _name.collect { it ->
                name = it
            }
        }
        return name
    }

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

    val isSubmitEnabled: Flow<Boolean> = combine(
        _name,
        _userName,
        _password
    ) { name, userName, password ->
        return@combine name.isNotEmpty() and userName.isNotEmpty() and password.isNotEmpty()

    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        false
    )

    fun insertUserAccount(
        userAccountEns: UserInfoEns = UserInfoEns(
            userName = getUserName(),
            fullName = getName(),
            password = getPassword()
        )
    ) {
        viewModelScope.launch(dispatchers.io) {
            insertUserAccountUseCase(userAccountEns).also { isSuccess ->
                if (isSuccess) {
                    _viewState.postValue(
                        RegisterViewState(
                            navigation =
                            RegisterFragmentDirections.actionRegisterToHome(
                                userAccountEns
                            )
                        )
                    )
                } else {
                    _viewState.postValue(
                        RegisterViewState(
                            error = ErrorState("this userName already exist"),
                            navigation =
                            RegisterFragmentDirections.actionRegisterToLogin(
                                getUserName(),
                                getPassword()
                            )

                        )
                    )
                }
            }
        }
    }
}