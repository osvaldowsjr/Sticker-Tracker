package com.osvaldo.stickerstracker.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.osvaldo.stickerstracker.data.dataSource.NationDataSource
import com.osvaldo.stickerstracker.data.model.Nation
import com.osvaldo.stickerstracker.data.model.NationEnum
import com.osvaldo.stickerstracker.data.model.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NationRepositoryImpl(private val dataSource: NationDataSource) : NationRepository {
    override val allNation: LiveData<List<Nation>>
        get() = dataSource.allNations.asLiveData()

    override suspend fun updateNationsFlag(nations: List<Nation>) = withContext(Dispatchers.IO) {
        nations.forEach {
            updateNation(it)
        }
    }

    override suspend fun getAlbumCompletion(nations: List<Nation>): Pair<Int, Int> =
        withContext(Dispatchers.IO) {
            var total = 0
            var obtained = 0
            nations.forEach {
                total += it.listOfPlayers.size
                obtained += it.listOfPlayers.filter { player -> player.amount > 0 }.size
            }
            Pair(obtained, total)
        }

    override suspend fun getMostCompletedNation(nations: List<Nation>): List<Nation> =
        withContext(Dispatchers.IO) {
            val listOfNations = mutableListOf<Nation>()
            var max = 0
            nations.forEach { nation ->
                var current = 0
                nation.listOfPlayers.forEach { player ->
                    if (player.amount > 0) current++
                }
                if (current == max)
                    listOfNations.add(nation)
                else if (current > max) {
                    listOfNations.clear()
                    listOfNations.add(nation)
                    max = current
                }
            }
            listOfNations
        }

    override suspend fun getLeastCompleatedNation(nations: List<Nation>): List<Nation> =
        withContext(Dispatchers.IO) {
            val listOfNations = mutableListOf<Nation>()
            var maxValue = 0
            nations.forEach { nation ->
                if (nation.nationEnum != NationEnum.FWC) {
                    var zeroCount = 0
                    nation.listOfPlayers.forEach { player ->
                        if (player.amount == 0) zeroCount++
                    }
                    if (zeroCount > maxValue) {
                        maxValue = zeroCount
                        listOfNations.clear()
                        Log.d("TESTE", nation.nationName)
                        Log.d("TESTE", "maior")
                        listOfNations.add(nation)
                    } else if (zeroCount == maxValue) {
                        Log.d("TESTE", nation.nationName)
                        Log.d("TESTE", "igual")
                        listOfNations.add(nation)
                    }
                }
            }
            listOfNations
        }

    override suspend fun getRepeatedPlayers(nations: List<Nation>): MutableList<Player> =
        withContext(Dispatchers.IO) {
            val listOfPlayers = mutableListOf<Player>()
            nations.forEach { nation ->
                nation.listOfPlayers.forEach { player ->
                    if (player.amount > 1)
                        listOfPlayers.add(player)
                }
            }
            listOfPlayers
        }

    override suspend fun getMissingPlayers(nations: List<Nation>): MutableList<Player> =
        withContext(Dispatchers.IO) {
            val listOfPlayers = mutableListOf<Player>()
            nations.forEach { nation ->
                nation.listOfPlayers.forEach { player ->
                    if (player.amount == 0)
                        listOfPlayers.add(player)
                }
            }
            listOfPlayers
        }

    override suspend fun swapStickers(stickerToGive: String, stickerToReceive: String) =
        withContext(Dispatchers.IO) {
            updateGive(stickerToGive)
            updateReceive(stickerToReceive)
        }

    private suspend fun updateGive(stickerToGive: String) {
        val result = fixStickerStructure(stickerToGive)
        val index = indexPlayerToAdd(result)
        val nation = selectNation(result)
        nation.listOfPlayers[index].amount--
        updateNation(nation)
    }

    private fun fixStickerStructure(sticker: String): String {
        val nationString = sticker.substring(0, 3).trim()
        val numberString = Integer.parseInt(sticker.substring(3).trim())
        return "$nationString $numberString"
    }

    private suspend fun updateReceive(stickerToReceive: String) {
        val result = fixStickerStructure(stickerToReceive)
        val index = indexPlayerToAdd(result)
        val nation = selectNation(result)
        nation.listOfPlayers[index].amount++
        updateNation(nation)
    }

    override suspend fun updateNation(nation: Nation) = withContext(Dispatchers.IO) {
        dataSource.updateNation(nation)
    }

    override suspend fun selectNation(nationString: String): Nation = withContext(Dispatchers.IO) {
        val nation = dataSource.selectNation(nationString.substring(0, 3).trim())
        nation ?: Nation(
            nationName = "N/A",
            nationEnum = NationEnum.FWC,
            listOfPlayers = listOf()
        )
    }

    override suspend fun indexPlayerToAdd(nationString: String): Int = withContext(Dispatchers.IO) {
        if (nationString.contains("FWC")) {
            nationString.substring(3).toIntOrNull() ?: Int.MIN_VALUE
        } else {
            nationString.substring(3).toIntOrNull()?.minus(1) ?: Int.MIN_VALUE
        }
    }

    override suspend fun updateListOfPlayers(listOfPlayer: List<Player>): Unit =
        withContext(Dispatchers.IO) {
            val nation = dataSource.selectNation(getNationByPlayer(listOfPlayer[0]))
            nation?.let {
                nation.listOfPlayers = listOfPlayer
                updateNation(it)
            }
        }

    private fun getNationByPlayer(player: Player): String {
        return player.number.substring(0, 3)
    }
}