package com.sanam.customizablegenericbutton.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
import androidx.annotation.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.util.concurrent.atomic.AtomicBoolean


fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}


fun View.enable() {
    isEnabled = true
}

fun View.disable() {
    isEnabled = false
}

fun View.string(@StringRes resId: Int): String = resources.getString(resId)

fun View.string(@StringRes resId: Int, vararg formatArgs: Any): String =
    resources.getString(resId, *formatArgs)


fun TextView.setTextStyle(@StyleRes resId: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        setTextAppearance(resId)
    } else {
        @Suppress("DEPRECATION")
        setTextAppearance(context, resId)
    }
}

fun View.setImageResourceFromAttr(@AttrRes attr: Int) {
    setImageResourceCompat(this, getResId(attr))
}

private fun View.getResId(attr: Int): Int {
    val typedValue = TypedValue()
    context.theme.resolveAttribute(attr, typedValue, true)
    return typedValue.resourceId
}

private fun getImage(mContext: Context, iconName: String): Int {
 return mContext.resources.getIdentifier(iconName, "drawable", mContext.packageName)
}

fun setImageResourceCompat(view: View, @DrawableRes drawable: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        (view as? AppCompatImageView)?.setImageResource(drawable)
    } else {
        (view as? FloatingActionButton)?.setImageResource(drawable)
    }
}


fun EditText.onNextActionClick(onAction: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_NEXT) {
            onAction.invoke()
            return@setOnEditorActionListener true
        }
        return@setOnEditorActionListener false
    }
}

fun EditText.onDoneActionClick(onAction: () -> Unit) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            onAction.invoke()
            return@setOnEditorActionListener true
        }
        return@setOnEditorActionListener false
    }
}

fun View.setOnSingleClickListener(clickListener: ((View) -> Unit)?) {
    clickListener?.also {
        setOnClickListener(OnSingleClickListener(it))
    } ?: setOnClickListener(null)
}

class OnSingleClickListener(
    private val clickListener: (View) -> Unit,
    private val intervalMs: Long = 1000
) : View.OnClickListener {
    private var canClick = AtomicBoolean(true)

    override fun onClick(v: View?) {
        if (canClick.getAndSet(false)) {
            v?.run {
                postDelayed({
                    canClick.set(true)
                }, intervalMs)
                clickListener.invoke(v)
            }
        }
    }
}