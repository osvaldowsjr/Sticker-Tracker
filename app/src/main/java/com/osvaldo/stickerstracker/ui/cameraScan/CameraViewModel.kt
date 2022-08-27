package com.osvaldo.stickerstracker.ui.cameraScan

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.osvaldo.stickerstracker.ui.cameraScan.CameraFunctions.Companion.DESIRED_HEIGHT_CROP_PERCENT
import com.osvaldo.stickerstracker.ui.cameraScan.CameraFunctions.Companion.DESIRED_WIDTH_CROP_PERCENT
import com.osvaldo.stickerstracker.utils.SmoothedMutableLiveData

class CameraViewModel(application: Application) : AndroidViewModel(application) {

    val sourceText = SmoothedMutableLiveData<String>(SMOOTHING_DURATION)

    val imageCropPercentages = MutableLiveData<Pair<Int, Int>>()
        .apply { value = Pair(DESIRED_HEIGHT_CROP_PERCENT, DESIRED_WIDTH_CROP_PERCENT) }

    companion object {
        private const val SMOOTHING_DURATION = 50L
    }
}