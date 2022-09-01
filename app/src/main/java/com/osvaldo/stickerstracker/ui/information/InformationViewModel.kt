package com.osvaldo.stickerstracker.ui.information

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osvaldo.stickerstracker.data.model.Nation
import com.osvaldo.stickerstracker.data.repository.NationRepository
import com.osvaldo.stickerstracker.utils.camera.SmoothedMutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.text.DecimalFormat

class InformationViewModel(
    private val repository: NationRepository,
    internal val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Main.immediate
) : ViewModel() {

    val allNations = repository.allNation
    val albumCompletion = SmoothedMutableLiveData<Pair<Int,Int>>(SMOOTHING_DURATION)
    val nationMostObtained = SmoothedMutableLiveData<List<Nation>>(SMOOTHING_DURATION)
    val nationLeastObtained = SmoothedMutableLiveData<List<Nation>>(SMOOTHING_DURATION)

    fun getAlbumCompletion() {
        viewModelScope.launch(coroutineDispatcher) {
            albumCompletion.postValue(allNations.value?.let { repository.getAlbumCompletion(it) })
        }
    }

    fun getLeastObtained(){
        viewModelScope.launch(coroutineDispatcher) {
            nationLeastObtained.postValue(allNations.value?.let { repository.getLeastCompleatedNation(it) })
        }
    }

    fun getMostObtained(){
        viewModelScope.launch(coroutineDispatcher) {
            nationMostObtained.postValue(allNations.value?.let { repository.getMostCompletedNation(it) })
        }
    }

    fun providePercentage(pair: Pair<Int,Int>) : String{
        val value = (pair.first.toDouble()/pair.second) * 100.0
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.FLOOR
        return "${df.format(value)}%"
    }

    companion object {
        private const val SMOOTHING_DURATION = 50L
    }
}