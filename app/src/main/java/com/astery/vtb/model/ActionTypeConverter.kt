package com.astery.vtb.model

import androidx.room.TypeConverter


class ActionTypeConverter {
    @TypeConverter
    fun toDb(value: ActionType?): Int {
        return value?.ordinal ?: ActionType.Metal.ordinal
    }

    @TypeConverter
    fun toClass(data: Int): ActionType {
        for (e in ActionType.values()) {
            if (e.ordinal == data) return e
        }
        throw RuntimeException("ActionTypeConverter got invalid enum ordinal = $data")
    }
}
