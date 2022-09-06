package com.osvaldo.stickerstracker.ui.sharing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.osvaldo.stickerstracker.data.model.Nation
import com.osvaldo.stickerstracker.data.model.Player
import com.osvaldo.stickerstracker.data.repository.NationRepository
import com.osvaldo.stickerstracker.utils.Constants.Companion.SMOOTHING_DURATION
import com.osvaldo.stickerstracker.utils.camera.SmoothedMutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.text.Charsets.UTF_8

class SharingViewModel(
    private val repository: NationRepository,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Main.immediate
) : ViewModel() {

    val allNations = repository.allNation
    val listOfPlayers = SmoothedMutableLiveData<List<Player>>(SMOOTHING_DURATION)

    fun getMessageToSend() : ByteArray{
        return Gson().toJson(listOfPlayers.value).toByteArray(UTF_8)
    }

    fun getRepeatedPlayers(listOfNations : List<Nation>) {
        viewModelScope.launch(coroutineDispatcher) {
            listOfPlayers.postValue(repository.getRepeatedPlayers(listOfNations) )
        }
    }
}