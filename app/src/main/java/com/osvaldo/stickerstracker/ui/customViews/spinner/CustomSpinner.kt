package com.osvaldo.stickerstracker.ui.customViews.spinner

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import com.osvaldo.stickerstracker.data.model.NationEnum
import com.osvaldo.stickerstracker.databinding.CustomSpinnerBinding

class CustomSpinner @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    val binding = CustomSpinnerBinding.inflate(LayoutInflater.from(context), this, true)
    private val listener = OnItemSelectedListener()

    private val _position = listener.position
    val position: LiveData<Int> = _position

    init {
        initView()
    }

    private fun initView() {
        binding.spinnerNation.apply {
            adapter = NationSpinnerAdapter(
                context,
                NationEnum.values().toMutableList()
            )
            onItemSelectedListener = listener
        }
    }
}