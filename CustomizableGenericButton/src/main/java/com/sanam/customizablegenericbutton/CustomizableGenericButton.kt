package com.sanam.customizablegenericbutton

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import com.google.android.material.imageview.ShapeableImageView
import com.sanam.customizablegenericbutton.utils.*
import java.util.concurrent.atomic.AtomicBoolean

class CustomizableGenericButton @JvmOverloads
constructor(
    ctx: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(ctx, attributeSet, defStyleAttr) {
    private var scaleOutAnim: Animation
    private var scaleInAnim: Animation
    var button: ConstraintLayout
    var title: AppCompatTextView
    var subTitle: AppCompatTextView
    var icon: ShapeableImageView
    private var loadingGroup: Group
    var dot1: ImageView
    var dot2: ImageView
    var dot3: ImageView

    init {
        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.custom_button, this)
        button = view.findViewById(R.id.button)
        title = view.findViewById(R.id.text_view_title)
        subTitle = view.findViewById(R.id.text_view_sub_title)
        icon = view.findViewById(R.id.image_view_icon)
        loadingGroup = view.findViewById(R.id.loadingGroup)
        dot1 = view.findViewById(R.id.dot1)
        dot2 = view.findViewById(R.id.dot2)
        dot3 = view.findViewById(R.id.dot3)

        scaleOutAnim = AnimationUtils.loadAnimation(ctx, R.anim.scale_out)
        scaleOutAnim.repeatCount = Animation.INFINITE
        scaleInAnim = AnimationUtils.loadAnimation(ctx, R.anim.scale_in)
        scaleInAnim.repeatCount = Animation.INFINITE
        enable(false)
    }

    fun loading(visibility: Boolean, buttonText: String = "", buttonSubTitle: String = "") {

        if (visibility) {
            button.disable()
            icon.hide()
            setButtonText("")
            loadingGroup.visibility = View.VISIBLE
            dot1.startAnimation(scaleOutAnim)
            dot3.startAnimation(scaleOutAnim)
            dot2.startAnimation(scaleInAnim)
        } else {
            button.enable()
            icon.show()
            setButtonText(buttonText)
            loadingGroup.visibility = View.GONE
            scaleOutAnim.cancel()
            scaleInAnim.cancel()
            dot1.clearAnimation()
            dot2.clearAnimation()
            dot3.clearAnimation()
        }
    }

    fun enable(
        flag: Boolean,
        @ColorRes backgroundColor: Int = R.color.colorPrimary
    ) {
        loading(visibility = false, title.text.toString(), subTitle.text.toString())
        if (loadingGroup.visibility == View.GONE) {
            if (flag) {
                button.isEnabled = true
                button.backgroundTintList =
                    (ContextCompat.getColorStateList(this.context, backgroundColor))
                title.setTextColor(ContextCompat.getColor(this.context, R.color.white))
                subTitle.setTextColor(ContextCompat.getColor(this.context, R.color.white))

            } else {
                button.isEnabled = false
                button.backgroundTintList =
                    (ContextCompat.getColorStateList(this.context, R.color.color_f2f2f2))
                title.setTextColor(ContextCompat.getColor(this.context, R.color.color_848484))
                subTitle.setTextColor(ContextCompat.getColor(this.context, R.color.color_848484))
            }
        }
    }

    fun setButtonText(@StringRes buttonTitle: Int, @StringRes buttonSubTitle: Int? = null) {
        title.text = string(buttonTitle)
        buttonSubTitle?.let { text ->
            subTitle.show()
            subTitle.text = string(text)

        }
    }

    fun setButtonText(buttonTitle: String, buttonSubTitle: String? = null) {
        title.text = buttonTitle
        buttonSubTitle?.let { text ->
            subTitle.show()
            subTitle.text = text
        } ?: run {
            subTitle.hide()
        }
    }

    fun setButtonIcon(@DrawableRes buttonIconDrawable: Int) {
        setImageResourceCompat(icon, buttonIconDrawable)
    }

    fun setTextStyle(@StyleRes resId: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            title.setTextAppearance(resId)
            subTitle.setTextAppearance(resId)
        } else {
            @Suppress("DEPRECATION")
            title.setTextAppearance(context, resId)
            subTitle.setTextAppearance(context, resId)
        }
    }

    fun setBackground(@DrawableRes background: Int) {
        button.setBackgroundResource(background)
    }

    private fun setTitleTextViewDrawableColor(context: Context, color: Int) {
        for (drawable in title.compoundDrawables) {
            drawable?.setTintList(ContextCompat.getColorStateList(context, color))
        }
    }

    private fun setSubTitleTextViewDrawableColor(context: Context, color: Int) {
        for (drawable in subTitle.compoundDrawables) {
            drawable?.setTintList(ContextCompat.getColorStateList(context, color))
        }
    }

    fun setOnClickListener(clickListener: (() -> Unit)?) {
        button.setOnClickListener {
            clickListener?.invoke()
        }
    }


    fun freeResources() {
        scaleOutAnim.cancel()
        scaleInAnim.cancel()
        dot1.clearAnimation()
        dot2.clearAnimation()
        dot3.clearAnimation()
    }

}