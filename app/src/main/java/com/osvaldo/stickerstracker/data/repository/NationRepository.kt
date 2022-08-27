package com.osvaldo.stickerstracker.data.repository

import com.osvaldo.stickerstracker.data.model.Nation
import com.osvaldo.stickerstracker.data.model.Player
import kotlinx.coroutines.flow.Flow

interface NationRepository {
    val allNation: Flow<List<Nation>>
    suspend fun updateListOfPlayers(listOfPlayer: List<Player>)
    suspend fun updateNation(nation: Nation)
    suspend fun selectNation(nationString: String): Nation
    suspend fun indexPlayerToAdd(nationString: String): Int
}