package com.osvaldo.stickerstracker.ui.customViews.spinner

import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class OnItemSelectedListener : AdapterView.OnItemSelectedListener {

    private val _position = MutableLiveData(0)
    val position : LiveData<Int> = _position

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, positionSelected: Int, p3: Long) {
        _position.postValue(positionSelected)
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        // do nothing
    }
}