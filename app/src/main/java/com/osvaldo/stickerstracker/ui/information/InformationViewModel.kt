package com.osvaldo.stickerstracker.ui.information

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osvaldo.stickerstracker.data.model.Nation
import com.osvaldo.stickerstracker.data.repository.NationRepository
import com.osvaldo.stickerstracker.utils.camera.SmoothedMutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InformationViewModel(
    private val repository: NationRepository,
    internal val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Main.immediate
) : ViewModel() {

    val allNations = repository.allNation
    val albumCompletion = SmoothedMutableLiveData<Pair<Int,Int>>(SMOOTHING_DURATION)
    val nationMostObtained = SmoothedMutableLiveData<Nation>(SMOOTHING_DURATION)

    fun getAlbumCompletion() {
        viewModelScope.launch {
            albumCompletion.postValue(allNations.value?.let { repository.getAlbumCompletion(it) })
        }
    }

    fun getMostObtained(){
        viewModelScope.launch {
            nationMostObtained.postValue(allNations.value?.let { repository.getMostCompletedNation(it) })
        }
    }

    companion object {
        private const val SMOOTHING_DURATION = 50L
    }
}