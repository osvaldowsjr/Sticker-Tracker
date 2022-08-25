package com.osvaldo.stickerstracker.data.repository

import com.osvaldo.stickerstracker.data.model.Nation
import kotlinx.coroutines.flow.Flow

interface NationRepository {
    val allNation : Flow<List<Nation>>
}