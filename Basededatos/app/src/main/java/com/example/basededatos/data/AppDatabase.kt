package com.example.basededatos.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Articulo::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun articuloDao(): ArticuloDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "articulos_db"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}