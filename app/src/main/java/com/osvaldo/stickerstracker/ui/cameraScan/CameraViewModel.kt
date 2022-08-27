package com.osvaldo.stickerstracker.ui.cameraScan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osvaldo.stickerstracker.data.repository.NationRepository
import com.osvaldo.stickerstracker.ui.cameraScan.CameraFunctions.Companion.DESIRED_HEIGHT_CROP_PERCENT
import com.osvaldo.stickerstracker.ui.cameraScan.CameraFunctions.Companion.DESIRED_WIDTH_CROP_PERCENT
import com.osvaldo.stickerstracker.utils.camera.SmoothedMutableLiveData
import kotlinx.coroutines.launch

class CameraViewModel(private val repository: NationRepository) : ViewModel() {

    val sourceText = SmoothedMutableLiveData<String>(SMOOTHING_DURATION)
    val nationName = SmoothedMutableLiveData<String>(SMOOTHING_DURATION)
    val stickerNumber = SmoothedMutableLiveData<String>(SMOOTHING_DURATION)
    val nationEnum = SmoothedMutableLiveData<String>(SMOOTHING_DURATION)

    val imageCropPercentages = MutableLiveData<Pair<Int, Int>>()
        .apply { value = Pair(DESIRED_HEIGHT_CROP_PERCENT, DESIRED_WIDTH_CROP_PERCENT) }

    fun addSticker(stickerId: String) = viewModelScope.launch {
        stickerId.let {
            if (it.length < 5) {
                nationName.postValue("Nação não encontrada, tente novamente!")
                stickerNumber.postValue(":(")
            } else {
                addStickerToDatabase(it)
            }
        }
    }

    private suspend fun addStickerToDatabase(stickerId: String) {
        val nation = repository.selectNation(stickerId)
        if (nation.nationName != "N/A") {
            val indexOfPlayer = repository.indexPlayerToAdd(stickerId)
            nation.listOfPlayers[indexOfPlayer].amount++
            repository.updateNation(nation)
        }
    }

    companion object {
        private const val SMOOTHING_DURATION = 50L
    }
}