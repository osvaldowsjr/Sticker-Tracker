package com.osvaldo.stickerstracker.ui.cameraScan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osvaldo.stickerstracker.R
import com.osvaldo.stickerstracker.data.model.Nation
import com.osvaldo.stickerstracker.data.model.NationEnum
import com.osvaldo.stickerstracker.data.model.getFlag
import com.osvaldo.stickerstracker.data.repository.NationRepository
import com.osvaldo.stickerstracker.ui.cameraScan.CameraFunctions.Companion.DESIRED_HEIGHT_CROP_PERCENT
import com.osvaldo.stickerstracker.ui.cameraScan.CameraFunctions.Companion.DESIRED_WIDTH_CROP_PERCENT
import com.osvaldo.stickerstracker.utils.camera.SmoothedMutableLiveData
import kotlinx.coroutines.launch

class CameraViewModel(private val repository: NationRepository) : ViewModel() {

    val sourceText = SmoothedMutableLiveData<String>(SMOOTHING_DURATION)
    val nationName = SmoothedMutableLiveData<String>(SMOOTHING_DURATION)
    val stickerNumber = SmoothedMutableLiveData<String>(SMOOTHING_DURATION)
    val nationEnum = SmoothedMutableLiveData<Int>(SMOOTHING_DURATION)
    val isAdded = SmoothedMutableLiveData<Boolean>(SMOOTHING_DURATION)

    val imageCropPercentages = MutableLiveData<Pair<Int, Int>>()
        .apply { value = Pair(DESIRED_HEIGHT_CROP_PERCENT, DESIRED_WIDTH_CROP_PERCENT) }

    fun addSticker(stickerId: String) {
        viewModelScope.launch {
            if (stickerId.length < 5) {
                buildNotFoundNation()
                isAdded.postValue(false)
            } else {
                addStickerToDatabase(stickerId)
                isAdded.postValue(true)
            }
        }
    }

    private fun buildNation(nation: Nation, stickerId: Int) {
        nationName.postValue(nation.nationName)
        if (nation.nationEnum == NationEnum.FWC) {
            stickerNumber.postValue(stickerId.toString())
        } else {
            stickerNumber.postValue((stickerId + 1).toString())
        }
        nationEnum.postValue(nation.nationEnum.getFlag())
    }

    private fun buildNotFoundNation() {
        nationName.postValue("Nação não encontrada, tente novamente!")
        stickerNumber.postValue(":(")
        nationEnum.postValue(R.drawable.ic_image_not_found_icon)
    }

    private suspend fun addStickerToDatabase(stickerId: String) {
        val nation = repository.selectNation(stickerId)
        if (nation.nationName != "N/A") {
            val indexOfPlayer = repository.indexPlayerToAdd(stickerId)
            buildNation(nation, indexOfPlayer)
            nation.listOfPlayers[indexOfPlayer].amount++
            repository.updateNation(nation)
        }
    }

    companion object {
        private const val SMOOTHING_DURATION = 50L
    }
}