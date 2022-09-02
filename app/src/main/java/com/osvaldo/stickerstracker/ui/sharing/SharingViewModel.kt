package com.osvaldo.stickerstracker.ui.sharing

import androidx.lifecycle.ViewModel
import com.osvaldo.stickerstracker.data.repository.NationRepository
import com.osvaldo.stickerstracker.utils.camera.SmoothedMutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class SharingViewModel(
    private val repository: NationRepository,
    internal val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Main.immediate
) : ViewModel() {

    val qrCodeResult = SmoothedMutableLiveData<String>(50L)

}