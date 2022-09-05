package com.osvaldo.stickerstracker.ui.cameraScan

import android.view.View
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
import com.osvaldo.stickerstracker.utils.removeSpaces
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CameraViewModel(
    private val repository: NationRepository,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Main.immediate
) : ViewModel() {

    val sourceText = SmoothedMutableLiveData<String>(SMOOTHING_DURATION)
    val nationName = SmoothedMutableLiveData<String>(SMOOTHING_DURATION)
    val stickerNumber = SmoothedMutableLiveData<String>(SMOOTHING_DURATION)
    val nationEnum = SmoothedMutableLiveData<Int>(SMOOTHING_DURATION)
    val isAdded = SmoothedMutableLiveData<Int>(SMOOTHING_DURATION)
    val isRepeated = SmoothedMutableLiveData<Int>(SMOOTHING_DURATION)

    val imageCropPercentages = MutableLiveData<Pair<Int, Int>>()
        .apply { value = Pair(DESIRED_HEIGHT_CROP_PERCENT, DESIRED_WIDTH_CROP_PERCENT) }

    fun addSticker(stickerId: String) {
        viewModelScope.launch(coroutineDispatcher) {
            if (stickerId.length < 5) {
                buildNotFoundNation()
                isAdded.postValue(View.GONE)
            } else {
                addStickerToDatabase(stickerId)
                isAdded.postValue(View.VISIBLE)
            }
        }
    }

    private fun buildNation(nation: Nation, stickerId: Int) {
        nationName.postValue(nation.nationName)
        nationEnum.postValue(nation.nationEnum.getFlag())
        if (nation.nationEnum == NationEnum.FWC) {
            stickerNumber.postValue(stickerId.toString())
        } else {
            stickerNumber.postValue((stickerId + 1).toString())
        }
    }

    private fun buildNotFoundNation() {
        nationName.postValue("Nação não encontrada, tente novamente!")
        stickerNumber.postValue(":(")
        nationEnum.postValue(R.drawable.icon_not_found)
    }

    private suspend fun addStickerToDatabase(stickerId: String) {
        val id = stickerId.removeSpaces().trim().uppercase()
        val nation = repository.selectNation(id)
        if (nation.nationName != "N/A") {
            val indexOfPlayer = repository.indexPlayerToAdd(id)
            buildNation(nation, indexOfPlayer)
            nation.listOfPlayers[indexOfPlayer].amount++
            controlIsRepeated(nation, indexOfPlayer)
            repository.updateNation(nation)
        }
    }

    private fun controlIsRepeated(
        nation: Nation,
        indexOfPlayer: Int
    ) {
        if (nation.listOfPlayers[indexOfPlayer].amount > 1) {
            isRepeated.postValue(View.VISIBLE)
        } else {
            isRepeated.postValue(View.GONE)
        }
    }

    companion object {
        private const val SMOOTHING_DURATION = 50L
    }
}