package com.sanam.nizekinterview.common.utils

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import kotlinx.coroutines.*

fun Context.integer(@IntegerRes resId: Int) =
    resources.getInteger(resId)

fun Context.dimenInPixel(@DimenRes resId: Int) =
    resources.getDimensionPixelSize(resId)

fun Context.color(@ColorRes resId: Int) =
    ContextCompat.getColor(this, resId)

fun Context.colorAttr(@AttrRes attrId: Int): Int {

    val typedValue = TypedValue()
    theme.resolveAttribute(attrId, typedValue, true)
    val colorResId = typedValue.resourceId
    return color(colorResId)
}

fun Context.colorStateList(@ColorRes resId: Int) =
    ContextCompat.getColorStateList(this, resId)

fun Context.string(@StringRes resId: Int): String = resources.getString(resId)

fun Context.string(@StringRes resId: Int, vararg formatArgs: Any): String =
    resources.getString(resId, *formatArgs)

fun Context.fontAttr(@AttrRes attrId: Int): Typeface {

    val typedValue = TypedValue()
    theme.resolveAttribute(attrId, typedValue, true)
    val fontResId = typedValue.resourceId
    return ResourcesCompat.getFont(this, fontResId)!!
}



fun Context.showToast(text: String?, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, text.orEmpty(), duration).show()
}


fun CoroutineScope.launchPeriodicAsync(
    repeatMillis: Long,
    action: () -> Unit
) = this.async {
    if (repeatMillis > 0) {
        while (isActive) {
            delay(repeatMillis)
            action()
            this.cancel()
        }
    }
}

