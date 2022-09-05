package com.osvaldo.stickerstracker.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Player(
    @PrimaryKey
    val number: String,
    var amount: Int = 0
){
    override fun equals(other: Any?): Boolean {
        return this.number == (other as Player).number
    }

    override fun hashCode(): Int {
        var result = number.hashCode()
        result = 31 * result + amount
        return result
    }
}