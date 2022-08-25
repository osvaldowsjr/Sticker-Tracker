package com.osvaldo.stickerstracker.data.dataSource

import com.osvaldo.stickerstracker.appDatabase.NationDao
import com.osvaldo.stickerstracker.data.model.Nation
import kotlinx.coroutines.flow.Flow

class NationDataSourceImpl(private val nationDao: NationDao) : NationDataSource {
    override val allNations: Flow<List<Nation>>
        get() = nationDao.getAllNations()

    override suspend fun updateNation(nation: Nation) {
        nationDao.updateNation(nation)
    }
}