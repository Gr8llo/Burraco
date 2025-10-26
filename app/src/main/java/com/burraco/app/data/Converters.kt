package com.burraco.app.data

import androidx.room.TypeConverter
import com.burraco.app.ui.theme.PlayerAvatar

class Converters {
    @TypeConverter
    fun fromAvatar(avatar: PlayerAvatar): String{      // Converte da ProfileAvatar a String (per salvare)
        return avatar.name
    }
    @TypeConverter
    fun toAvatar(name: String): PlayerAvatar{
        return try {
            PlayerAvatar.valueOf(name)
        } catch (e: IllegalArgumentException) {
            PlayerAvatar.SPADE_MUSIC
        }
    }
}