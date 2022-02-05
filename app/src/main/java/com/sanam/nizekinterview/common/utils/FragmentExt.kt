package com.sanam.nizekinterview.common.utils

import android.app.Activity
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.sanam.nizekinterview.common.state.ErrorState
import com.sanam.nizekinterview.databinding.ProgressLodingBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import java.time.Duration


fun Fragment.showToast(text: String?, duration: Int = Toast.LENGTH_SHORT) {
    context?.showToast(text, duration)
}

fun Fragment.showToast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    showToast(getString(resId), duration)
}

fun Fragment.showSuccessSnackbar(message: String) {
    this.view?.let {
        val snackbar = Snackbar.make(it, message, Snackbar.LENGTH_LONG)
        snackbar.setBackgroundTint(Color.parseColor("#00c496"))
        snackbar.show()
    }
}

fun BottomSheetDialogFragment.showSuccessBottomSheetSnackbar(
    message: String
) {
    dialog?.window?.decorView?.let {
        val snackbar = Snackbar.make(it, message, Snackbar.LENGTH_LONG)
        snackbar.setBackgroundTint(Color.parseColor("#00c496"))
        snackbar.show()
    }
}

fun BottomSheetDialogFragment.showErrorBottomSheetSnackbar(message: String) {
    dialog?.window?.decorView?.let {
        val snackbar = Snackbar.make(it, message, Snackbar.LENGTH_LONG)
        snackbar.setBackgroundTint(Color.parseColor("#ff0030"))
        snackbar.show()
    }
}

fun Fragment.showErrorSnackbar(message: String) {
    this.view?.let {
        val snackbar = Snackbar.make(it, message, Snackbar.LENGTH_LONG)
        snackbar.setBackgroundTint(Color.parseColor("#ff0030"))
        snackbar.show()
    }
}

fun Fragment.hideKeyboard() {
    val imm =
        requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
    var view = view
    if (view == null) {
        view = View(context)
    }
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Fragment.checkPermission(
    permission: Array<String>,
    requestMultiplePermissions: ActivityResultLauncher<String>
): Boolean {
    permission.forEach {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                it
            ) !=
            PERMISSION_GRANTED
        ) {
            requestMultiplePermissions.launch(it)
            return false
        }
    }
    return true
}


fun Fragment.showError(error: ErrorState) {
    showErrorSnackbar(error.message)
}

fun hideLoading(loadingDialog: AlertDialog?) {
    loadingDialog?.apply {
        this.dismiss()
    }
}

fun Fragment.showLoading(): AlertDialog {
    val builder = AlertDialog.Builder(
        requireActivity()
    )
    val inflater = LayoutInflater.from(requireActivity())
    val binding = ProgressLodingBinding.inflate(inflater)
    builder.setView(binding.root)
    builder.setCancelable(false)
    val dialog = builder.create()
    dialog.setOnKeyListener { dialogInterface, i, keyEvent ->
        if (i == KeyEvent.KEYCODE_BACK &&
            keyEvent.action == KeyEvent.ACTION_UP &&
            !keyEvent.isCanceled
        ) {
            dialog.dismiss()
            if (!NavHostFragment.findNavController(this).popBackStack())
                activity?.finish()
            return@setOnKeyListener true
        }
        return@setOnKeyListener false
    }
    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.show()
    return dialog
}





