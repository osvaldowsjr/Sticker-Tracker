package com.osvaldo.stickerstracker.ui.addStickers

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osvaldo.stickerstracker.data.model.Nation
import com.osvaldo.stickerstracker.data.repository.NationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddingViewModel(
    private val repository: NationRepository,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Main.immediate
) : ViewModel() {
    val allNations: LiveData<List<Nation>> = repository.allNation

    fun updateNation() = viewModelScope.launch(coroutineDispatcher) {
        allNations.value?.forEach {
            repository.updateNation(it)
        }
    }
}