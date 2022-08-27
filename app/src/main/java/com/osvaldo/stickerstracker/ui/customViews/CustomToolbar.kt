package com.osvaldo.stickerstracker.ui.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.osvaldo.stickerstracker.databinding.CustomToolbarBinding

class CustomToolbar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private var binding: CustomToolbarBinding =
        CustomToolbarBinding.inflate(LayoutInflater.from(context), this, true)

    fun setStartIconVisibility(visibility: Int) {
        binding.startIcon.visibility = visibility
    }

    fun setEndIconVisibility(visibility: Int) {
        binding.endIcon.visibility = visibility
    }

    fun setEndIconOnClickListener(listener: OnClickListener) {
        binding.endIcon.setOnClickListener(listener)
    }

    fun setStartIconOnClickListener(listener: OnClickListener) {
        binding.startIcon.setOnClickListener(listener)
    }

    fun setEndIconDrawable(id: Int) {
        binding.endIcon.setImageResource(id)
    }

    fun setStartIconDrawable(id: Int) {
        binding.startIcon.setImageResource(id)
    }
}