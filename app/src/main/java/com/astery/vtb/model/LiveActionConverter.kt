package com.astery.vtb.model

import androidx.room.TypeConverter


class LiveActionConverter {
    @TypeConverter
    fun toDb(value: LiveAction?): String {
        return ""
    }

    @TypeConverter
    fun toClass(data: String): LiveAction? {
        return null
    }
}
