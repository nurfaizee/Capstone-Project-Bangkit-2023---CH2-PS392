package com.dicoding.bottomnavigationbar.ui.customview

import android.content.Context
import android.graphics.Rect
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.dicoding.bottomnavigationbar.R


class ETPassword : AppCompatEditText, View.OnTouchListener {

    var isPasswordValid: Boolean = false

    init {
        init()
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        // Set background with border
        background = ContextCompat.getDrawable(context, R.drawable.border)
        transformationMethod = PasswordTransformationMethod.getInstance()

        // Set text change listener
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Validate password when text changed
                validatePassword()
            }

            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }
        })
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        return false
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (!focused) {
            validatePassword()
        }
    }

    private fun validatePassword() {
        isPasswordValid = (text?.length ?: 0) >= 8
        error = if (!isPasswordValid) {
            resources.getString(R.string.passwordLess)
        } else {
            null
        }
    }
}