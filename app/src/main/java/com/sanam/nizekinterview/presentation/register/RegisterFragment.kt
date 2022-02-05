package com.sanam.nizekinterview.presentation.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.sanam.nizekinterview.R
import com.sanam.nizekinterview.common.base.AbstractFragment
import com.sanam.nizekinterview.common.utils.*
import com.sanam.nizekinterview.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlin.text.matches

@AndroidEntryPoint
class RegisterFragment :
    AbstractFragment<RegisterViewState, RegisterViewModel, FragmentRegisterBinding>() {

    private val registerViewModel by viewModels<RegisterViewModel>()
    override val viewModel: RegisterViewModel get() = registerViewModel
    override fun getViewBinding() = FragmentRegisterBinding.inflate(layoutInflater)

    override fun initViews(view: View, savedInstanceState: Bundle?) {
        super.initViews(view, savedInstanceState)
        initButton()
        doAfterTextChange()
        initObserver()
    }

    private fun initButton() {
        vBinding.buttonEnter.setButtonText(getString(R.string.register))
        vBinding.buttonEnter.setButtonIcon(R.drawable.button_drawable_navigate)
        vBinding.buttonEnter.setOnClickListener {
            viewModel.insertUserAccount()
        }
    }

    private fun doAfterTextChange() {
        vBinding.outlinedFamilyFeild.editText?.doAfterTextChanged {
            if (it?.length!! > 2 && (it.toString().containsEnglish(false) &&
                        !it.toString().isPersianString())
            ) {
                viewModel.setName(it.toString())
                vBinding.outlinedFamilyFeild.error = null
            } else {
                vBinding.outlinedFamilyFeild.error = getString(R.string.name_validation)
                viewModel.setName("")
            }
        }

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


    override fun renderView(state: RegisterViewState) {

    }

}