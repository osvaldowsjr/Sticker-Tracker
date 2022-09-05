package com.osvaldo.stickerstracker.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.osvaldo.stickerstracker.data.model.Nation
import com.osvaldo.stickerstracker.data.repository.NationRepository

class MainViewModel(
    repository: NationRepository
) : ViewModel() {
    val allNations: LiveData<List<Nation>> = repository.allNation
}