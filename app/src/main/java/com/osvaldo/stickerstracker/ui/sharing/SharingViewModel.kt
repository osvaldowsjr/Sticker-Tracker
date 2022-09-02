package com.osvaldo.stickerstracker.ui.sharing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osvaldo.stickerstracker.data.model.Player
import com.osvaldo.stickerstracker.data.repository.NationRepository
import com.osvaldo.stickerstracker.utils.camera.SmoothedMutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SharingViewModel(
    private val repository: NationRepository,
    internal val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Main.immediate
) : ViewModel() {

    val allNations = repository.allNation
    val listOfPlayers = SmoothedMutableLiveData<List<Player>>(50L)

    fun getRepeatedPlayers() {
        viewModelScope.launch(coroutineDispatcher) {
            listOfPlayers.postValue(allNations.value?.let { repository.getRepeatedPlayers(it) })
        }
    }

    fun getMissingPlayers(){
        viewModelScope.launch(coroutineDispatcher) {
            listOfPlayers.postValue(allNations.value?.let { repository.getMissingPlayers(it) })
        }
    }

}