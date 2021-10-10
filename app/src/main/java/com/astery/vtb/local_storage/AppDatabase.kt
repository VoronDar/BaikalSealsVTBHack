package com.astery.vtb.local_storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.astery.vtb.model.ActionEntity
import com.astery.vtb.model.ChoresHistory
import com.astery.vtb.model.InvestHistory

@Database(
    entities = [ActionEntity::class, ChoresHistory::class, InvestHistory::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun actionDao(): ActionDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "databas")
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}