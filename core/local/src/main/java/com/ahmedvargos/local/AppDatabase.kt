package com.ahmedvargos.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ahmedvargos.local.dao.AgentsDao
import com.ahmedvargos.local.dao.FavoritesDao
import com.ahmedvargos.local.entities.AgentEntity
import com.ahmedvargos.local.entities.AgentInfoConverter

@Database(entities = [AgentEntity::class], version = 1, exportSchema = false)
@TypeConverters(AgentInfoConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun agentsDao(): AgentsDao
    abstract fun favoritesDao(): FavoritesDao

    companion object {
        fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "appdatabase.db"
            ).build()
    }
}
