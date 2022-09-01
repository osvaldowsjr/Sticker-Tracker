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

    fun setBackIconVisibility(visibility: Int) {
        binding.backIcon.visibility = visibility
    }

    fun setBackIconDrawable(id:Int){
        binding.backIcon.setImageResource(id)
    }

    fun setBackIconOnClickListener(listener: OnClickListener) {
        binding.backIcon.setOnClickListener(listener)
    }

    fun setLastIconVisibility(visibility: Int) {
        binding.lastIcon.visibility = visibility
    }

    fun setLastIconOnClickListener(listener: OnClickListener) {
        binding.lastIcon.setOnClickListener(listener)
    }

    fun setLastIconDrawable(id: Int) {
        binding.lastIcon.setImageResource(id)
    }

    fun setFirstIconVisibility(visibility: Int) {
        binding.firstIcon.visibility = visibility
    }

    fun setFirstIconListener(listener: OnClickListener) {
        binding.firstIcon.setOnClickListener(listener)
    }

    fun setFirstIconDrawable(id: Int) {
        binding.firstIcon.setImageResource(id)
    }
}