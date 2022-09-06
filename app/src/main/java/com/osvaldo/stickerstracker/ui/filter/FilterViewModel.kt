package com.osvaldo.stickerstracker.ui.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osvaldo.stickerstracker.data.model.Player
import com.osvaldo.stickerstracker.data.repository.NationRepository
import com.osvaldo.stickerstracker.utils.Constants.Companion.SMOOTHING_DURATION
import com.osvaldo.stickerstracker.utils.camera.SmoothedMutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FilterViewModel(
    private val repository: NationRepository,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Main.immediate
) : ViewModel() {

    val allNations = repository.allNation
    val listOfPlayers = SmoothedMutableLiveData<List<Player>>(SMOOTHING_DURATION)

    fun getRepeatedPlayers() {
        viewModelScope.launch(coroutineDispatcher) {
            listOfPlayers.postValue(allNations.value?.let { repository.getRepeatedPlayers(it) })
        }
    }

    fun getMissingPlayers() {
        viewModelScope.launch(coroutineDispatcher) {
            listOfPlayers.postValue(allNations.value?.let { repository.getMissingPlayers(it) })
        }
    }

}