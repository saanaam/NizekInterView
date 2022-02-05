package com.sanam.nizekinterview.common.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.sanam.nizekinterview.R
import com.sanam.nizekinterview.common.Const.FOREGROUNDTIMEAUTOLOGOUT
import com.sanam.nizekinterview.common.state.ViewState
import com.sanam.nizekinterview.common.utils.*
import kotlinx.coroutines.Job

abstract class AbstractFragment<V : ViewState, T : AbstractViewModel<V>, VB : ViewBinding> :
    Fragment() {

    public var _vBinding: ViewBinding? = null
    protected var loadingDialog: AlertDialog? = null

    lateinit var job: Job

    @Suppress("UNCHECKED_CAST")
    public val vBinding: VB
        get() = requireNotNull(_vBinding) as VB

    public abstract fun getViewBinding(): VB

    override fun onDestroyView() {
        super.onDestroyView()
        _vBinding = null
    }

    protected abstract val viewModel: T
    protected lateinit var navController: NavController

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _vBinding = getViewBinding()
        return requireNotNull(_vBinding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        initViews(view, savedInstanceState)
        initObservers()
    }

    protected open fun initViews(view: View, savedInstanceState: Bundle?) {

    }


    open fun initObservers() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            it.error?.let { errorState ->
                showError(errorState)
                hideKeyboard()
            }
            it.loading?.let { loading ->
                if (loading)
                    loadingDialog = this.showLoading()
                else
                    hideLoading(loadingDialog)
            }
            renderView(it)

            it.navigation?.let { nav ->
                lifecycleScope.launchWhenResumed {
                    navController.navigate(nav)
                }
            }
        })
    }


    private fun onAuthenticationError() {
        clearToken()
        findNavController().navigate(R.id.action_global_login_fragment)
    }

    abstract fun renderView(state: V)
    open fun hasToken(): Boolean = false
    open fun clearToken() {}
    override fun onResume() {
        super.onResume()
        lifecycleScope.launchWhenResumed {
            if (hasToken()) {
                job = this.launchPeriodicAsync(FOREGROUNDTIMEAUTOLOGOUT) {
                    onAuthenticationError()
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (::job.isInitialized)
            job.cancel()
    }


}