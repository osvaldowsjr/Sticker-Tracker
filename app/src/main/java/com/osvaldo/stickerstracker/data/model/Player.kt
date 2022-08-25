package com.osvaldo.stickerstracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Player(
    @PrimaryKey
    val number: String,
    var amount : Int = 0
    )