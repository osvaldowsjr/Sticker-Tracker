package com.osvaldo.stickerstracker.data.repository

import androidx.lifecycle.LiveData
import com.osvaldo.stickerstracker.data.model.Nation
import com.osvaldo.stickerstracker.data.model.Player

interface NationRepository {
    val allNation: LiveData<List<Nation>>
    suspend fun updateNationsFlag(nations: List<Nation>)
    suspend fun getAlbumCompletion(nations: List<Nation>): Pair<Int, Int>
    suspend fun getMostCompletedNation(nations: List<Nation>): List<Nation>
    suspend fun getLeastCompleatedNation(nations: List<Nation>): List<Nation>
    suspend fun updateListOfPlayers(listOfPlayer: List<Player>)
    suspend fun updateNation(nation: Nation)
    suspend fun selectNation(nationString: String): Nation
    suspend fun indexPlayerToAdd(nationString: String): Int
    suspend fun getRepeatedPlayers(nations: List<Nation>): MutableList<Player>
    suspend fun getMissingPlayers(nations: List<Nation>): MutableList<Player>
    suspend fun swapStickers(stickerToGive : String, stickerToReceive : String)
}