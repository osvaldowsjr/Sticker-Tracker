package com.osvaldo.stickerstracker.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "NationsTable")
data class Nation(
    @PrimaryKey(autoGenerate = true)
    val nationId: Int? = null,
    val nationName: String,
    val nationFlag: Int,
    val nationEnum: NationEnum,
    var listOfPlayers: List<Player>,

    ) {
    @Ignore
    var isPlayerListVisible: Boolean = false

    override fun toString(): String {
        return nationEnum.toString()
    }

}