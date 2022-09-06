package com.osvaldo.stickerstracker.ui.swap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osvaldo.stickerstracker.data.model.Player
import com.osvaldo.stickerstracker.data.repository.NationRepository
import com.osvaldo.stickerstracker.utils.Constants.Companion.SMOOTHING_DURATION
import com.osvaldo.stickerstracker.utils.camera.SmoothedMutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SwapViewModel(
    private val repository: NationRepository,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Main.immediate
) : ViewModel() {

    private lateinit var friendList: MutableList<Player>

    val resultList = SmoothedMutableLiveData<List<Player>>(SMOOTHING_DURATION)
    val repeatedList = SmoothedMutableLiveData<List<Player>>(SMOOTHING_DURATION)
    val allNations = repository.allNation

    fun startFriendList(list: MutableList<Player>) {
        friendList = list
    }

    fun getMissingPlayers() {
        viewModelScope.launch(coroutineDispatcher) {
            allNations.value?.let { mergeLists(repository.getMissingPlayers(it)) }
        }
    }

    fun getRepeatedPlayers() {
        viewModelScope.launch(coroutineDispatcher) {
            allNations.value?.let { repeatedList.postValue(repository.getRepeatedPlayers(it)) }
        }
    }

    fun swapStickers(stickerToGive: String, stickerToReceive: String) {
        viewModelScope.launch(coroutineDispatcher) {
            repository.swapStickers(stickerToGive, stickerToReceive)
        }
    }

    private fun mergeLists(missingPlayers: MutableList<Player>) {
        val result = mutableListOf<Player>()
        friendList.forEach {
            if (missingPlayers.contains(it))
                result.add(it)
        }
        resultList.postValue(result)
    }
}