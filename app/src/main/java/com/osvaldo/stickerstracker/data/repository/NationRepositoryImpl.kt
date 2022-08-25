package com.osvaldo.stickerstracker.data.repository

import com.osvaldo.stickerstracker.data.dataSource.NationDataSource
import com.osvaldo.stickerstracker.data.model.Nation
import kotlinx.coroutines.flow.Flow

class NationRepositoryImpl(private val dataSource: NationDataSource) : NationRepository {
    override val allNation: Flow<List<Nation>>
        get() = dataSource.allNations
}