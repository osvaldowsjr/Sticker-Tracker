package com.osvaldo.stickerstracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "NationsTable")
data class Nation(
    @PrimaryKey(autoGenerate = true)
    val nationId : Int? = null,
    val nationName : String,
    val nationFlag : Int,
    val nationEnum: NationEnum,
    val listOfPlayers : List<Player>
)