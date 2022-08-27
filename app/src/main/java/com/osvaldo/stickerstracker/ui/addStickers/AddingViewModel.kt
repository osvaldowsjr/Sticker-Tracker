package com.osvaldo.stickerstracker.ui.addStickers

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.osvaldo.stickerstracker.data.model.Nation
import com.osvaldo.stickerstracker.data.repository.NationRepository
import kotlinx.coroutines.launch

class AddingViewModel(private val repository: NationRepository) : ViewModel() {
    val allNations: LiveData<List<Nation>> = repository.allNation.asLiveData()

    fun updateNation() = viewModelScope.launch {
        allNations.value?.forEach {
            repository.updateNation(it)
        }
    }
}