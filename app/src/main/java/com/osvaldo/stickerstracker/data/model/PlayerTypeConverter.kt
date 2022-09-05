package com.osvaldo.stickerstracker.data.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PlayerTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun stringToPlayersList(data: String): List<Player> {
        val listType = object : TypeToken<List<Player?>?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun playerListToString(list: List<Player>): String {
        return gson.toJson(list)
    }
}