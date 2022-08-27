package com.osvaldo.stickerstracker.data.repository

import com.osvaldo.stickerstracker.data.dataSource.NationDataSource
import com.osvaldo.stickerstracker.data.model.Nation
import com.osvaldo.stickerstracker.data.model.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class NationRepositoryImpl(private val dataSource: NationDataSource) : NationRepository {
    override val allNation: Flow<List<Nation>>
        get() = dataSource.allNations

    override suspend fun updateNation(nation: Nation) {
        dataSource.updateNation(nation)
    }

    override suspend fun selectNation(nationString: String): Nation = withContext(Dispatchers.IO){
        dataSource.selectNation(nationString.substring(0,3).trim())
    }

    override suspend fun indexPlayerToAdd(nationString: String): Int = withContext(Dispatchers.IO) {
        nationString.substring(4).toInt()-1
    }

    override suspend fun updateListOfPlayers(listOfPlayer: List<Player>): Unit = withContext(Dispatchers.IO){
        val nation = dataSource.selectNation(getNationByPlayer(listOfPlayer[0]))
        nation.let {
            nation.listOfPlayers = listOfPlayer
            updateNation(it)
        }
    }

    private fun getNationByPlayer(player: Player) : String{
        return player.number.substring(0,3)
    }
}