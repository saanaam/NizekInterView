package com.sanam.nizekinterview.presentation.login

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.sanam.nizekinterview.R
import com.sanam.nizekinterview.common.base.AbstractFragment
import com.sanam.nizekinterview.common.utils.english
import com.sanam.nizekinterview.common.utils.persianString
import com.sanam.nizekinterview.common.utils.regexString
import com.sanam.nizekinterview.databinding.FragmentLoginBinding
import com.sanam.nizekinterview.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class LoginFragment :
    AbstractFragment<LoginViewState, LoginViewModel, FragmentLoginBinding>() {
    private val args: LoginFragmentArgs by navArgs()
    override fun getViewBinding() = FragmentLoginBinding.inflate(layoutInflater)
    private val logOutViewModel by viewModels<LoginViewModel>()
    override val viewModel: LoginViewModel get() = logOutViewModel
    override fun initViews(view: View, savedInstanceState: Bundle?) {
        super.initViews(view, savedInstanceState)
        setDefault()
        initButton()
        doAfterTextChange()
        initObserver()

    }

    private fun initButton() {
        vBinding.buttonEnter.setButtonText(getString(R.string.login))
        vBinding.buttonEnter.setButtonIcon(R.drawable.button_drawable_navigate)
        vBinding.buttonEnter.setOnClickListener {
            viewModel.login()
        }
    }

    private fun doAfterTextChange() {
        vBinding.outlinedUserNameFeild.editText?.doAfterTextChanged {
            if (it?.length!! > 5) {
                vBinding.outlinedUserNameFeild.error = null
                viewModel.setUserName(it.toString())
            } else {
                vBinding.outlinedUserNameFeild.error = getString(R.string.user_name_validation)
                viewModel.setUserName("")
            }
        }

        vBinding.outlinedPasswordFeild.editText?.doAfterTextChanged {
            if (it?.length!! > 3) {
                viewModel.setPassword(password = it.toString().english)
                vBinding.outlinedPasswordFeild.error = null
            } else {
                vBinding.outlinedPasswordFeild.error =
                    getString(R.string.password_validation)
                viewModel.setPassword("")
            }
        }

    }

    private fun initObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.isSubmitEnabled.collect { isEnable ->
                vBinding.buttonEnter.enable(isEnable)
            }
        }
    }

    private fun setDefault() {
        args.password?.let {
            if (it.isNotBlank())
            vBinding.outlinedPasswordFeild.editText?.setText(it)
        }
        args.userName?.let {
            if (it.isNotBlank())
            vBinding.outlinedUserNameFeild.editText?.setText(it)
        }
    }

    override fun renderView(state: LoginViewState) {

    }

}