package com.sanam.nizekinterview.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.sanam.customizablegenericbutton.utils.hide
import com.sanam.customizablegenericbutton.utils.show
import com.sanam.nizekinterview.R
import com.sanam.nizekinterview.common.base.AbstractFragment
import com.sanam.nizekinterview.common.utils.showLoading
import com.sanam.nizekinterview.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment :
    AbstractFragment<HomeViewState, HomeViewModel, FragmentHomeBinding>() {
    override fun getViewBinding() = FragmentHomeBinding.inflate(layoutInflater)
    private val homeViewModel by viewModels<HomeViewModel>()
    override val viewModel: HomeViewModel get() = homeViewModel

    override fun initViews(view: View, savedInstanceState: Bundle?) {
        super.initViews(view, savedInstanceState)
        viewModel.showLoading(true)
        initButton()
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkToken()
    }

    private fun initButton() {
        vBinding.buttonEnter.setButtonText(getString(R.string.logout))
        vBinding.buttonEnter.setButtonIcon(R.drawable.button_drawable_navigate)
        vBinding.buttonEnter.enable(true)
        vBinding.buttonEnter.setOnClickListener {
            viewModel.logOut()
        }
    }

    private fun setUserInfo(fullName: String) {
        vBinding.textViewUserName.text = String.format(
            "%s %s",
            getString(R.string.welcome),
            fullName
        )
    }

    override fun renderView(state: HomeViewState) {
        state.token?.let {
            if (it.isNotEmpty()) {
                viewModel.getUserInfo(it)
            } else {
                viewModel.navigateToRegister()
            }
        }

        state.userInfo?.let {
            it.fullName?.let { name ->
                vBinding.rootLayout.show()
                setUserInfo(name)
            }
        }
    }

    override fun clearToken() {
        viewModel.clearToken()
    }

    override fun hasToken(): Boolean {
        return viewModel.hasToken()
    }

}