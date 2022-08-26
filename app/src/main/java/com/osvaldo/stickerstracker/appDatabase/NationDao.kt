package com.osvaldo.stickerstracker.appDatabase

import androidx.room.*
import com.osvaldo.stickerstracker.data.model.Nation
import kotlinx.coroutines.flow.Flow

@Dao
interface NationDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(nation: Nation)

    @Update
    suspend fun updateNation(nation: Nation)

    @Query("SELECT * FROM NationsTable")
    fun getAllNations() : Flow<List<Nation>>

    @Query("SELECT * FROM NationsTable WHERE nationEnum is :nation")
    fun selectNation(nation:String) : Nation

    @Query("DELETE FROM NationsTable")
    fun nukeTable()
}