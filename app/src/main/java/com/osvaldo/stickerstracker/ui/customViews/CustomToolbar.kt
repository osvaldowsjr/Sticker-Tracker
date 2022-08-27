package com.osvaldo.stickerstracker.ui.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.osvaldo.stickerstracker.R
import kotlinx.android.synthetic.main.custom_toolbar.view.*

class CustomToolbar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.custom_toolbar,this,true)
    }

    fun setStartIconVisibility(visibility : Int){
        swap_stickers.visibility = visibility
    }

    fun setEndIconVisibility(visibility: Int){
        add_stickers.visibility = visibility
    }

    fun setEndIconOnClickListener(listener: OnClickListener){
        add_stickers.setOnClickListener(listener)
    }

    fun setStartIconOnClickListener(listener: OnClickListener){
        swap_stickers.setOnClickListener(listener)
    }

    fun setEndIconDrawable(id: Int){
        add_stickers.setImageResource(id)
    }

    fun setStartIconDrawable(id: Int){
        swap_stickers.setImageResource(id)
    }
}