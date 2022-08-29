package com.osvaldo.stickerstracker.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osvaldo.stickerstracker.data.model.Nation
import com.osvaldo.stickerstracker.data.repository.NationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: NationRepository,
    internal val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Main.immediate
) : ViewModel() {
    val allNations: LiveData<List<Nation>> = repository.allNation

    fun updateFlags(nations: List<Nation>) = viewModelScope.launch(coroutineDispatcher) {
        repository.updateNationsFlag(nations)
    }

}