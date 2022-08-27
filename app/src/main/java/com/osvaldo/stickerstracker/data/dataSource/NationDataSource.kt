package com.osvaldo.stickerstracker.data.dataSource

import com.osvaldo.stickerstracker.data.model.Nation
import com.osvaldo.stickerstracker.data.model.NationEnum
import kotlinx.coroutines.flow.Flow

interface NationDataSource {
    val allNations : Flow<List<Nation>>
    suspend fun updateNation(nation: Nation)
    suspend fun selectNation(nationEnum: String) : Nation
}