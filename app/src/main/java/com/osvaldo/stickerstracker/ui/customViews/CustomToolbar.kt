package com.osvaldo.stickerstracker.ui.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.osvaldo.stickerstracker.R

class CustomToolbar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.custom_toolbar,this,true)
    }
}