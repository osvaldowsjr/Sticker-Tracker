package com.osvaldo.stickerstracker.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.osvaldo.stickerstracker.R
import com.osvaldo.stickerstracker.data.dataSource.NationDataSource
import com.osvaldo.stickerstracker.data.model.Nation
import com.osvaldo.stickerstracker.data.model.NationEnum
import com.osvaldo.stickerstracker.data.model.Player
import com.osvaldo.stickerstracker.data.model.getFlag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NationRepositoryImpl(private val dataSource: NationDataSource) : NationRepository {
    override val allNation: LiveData<List<Nation>>
        get() = dataSource.allNations.asLiveData()

    override suspend fun updateNationsFlag(nations: List<Nation>) = withContext(Dispatchers.IO){
        nations.forEach {
            it.nationFlag = it.nationEnum.getFlag()
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

    override suspend fun getMostCompletedNation(nations: List<Nation>): Nation = withContext(Dispatchers.IO){
        var mostSelected =
            Nation(
                nationName = "Não encontrada",
                nationFlag = R.drawable.not_found,
                nationEnum = NationEnum.FWC,
                listOfPlayers = listOf()
            )
        var max = 0
        nations.forEach { nation ->
            var current = 0
            nation.listOfPlayers.forEach { player ->
                if (player.amount > 0) current++
            }
            if (current >= max) {
                max = max.coerceAtLeast(current)
                Log.d("TESTE",nation.nationName)
                mostSelected = nation
                current = 0
            }
        }
        mostSelected
    }

    override suspend fun updateNation(nation: Nation) = withContext(Dispatchers.IO){
        dataSource.updateNation(nation)
    }

    override suspend fun selectNation(nationString: String): Nation = withContext(Dispatchers.IO) {
        val nation = dataSource.selectNation(nationString.substring(0, 3).trim())
        nation ?: Nation(
            nationName = "N/A",
            nationFlag = 1,
            nationEnum = NationEnum.FWC,
            listOfPlayers = listOf()
        )
    }

    override suspend fun indexPlayerToAdd(nationString: String): Int = withContext(Dispatchers.IO) {
        if (nationString.contains("FWC")) {
            nationString.substring(4).toIntOrNull() ?: Int.MIN_VALUE
        } else {
            nationString.substring(4).toIntOrNull()?.minus(1) ?: Int.MIN_VALUE
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