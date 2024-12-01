package com.example.secondssince.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.secondssince.model.Love
import com.example.secondssince.model.LoveTypeConverters

@Database(
    entities = [Love::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(LoveTypeConverters::class)
abstract class AppDatabase: RoomDatabase() {
     companion object {
         @Volatile
         private var INSTANCE: AppDatabase? = null

         fun getDatabase(context: Context): AppDatabase {
             return INSTANCE ?: synchronized(this) {
                 val instance = Room.databaseBuilder(
                     context.applicationContext,
                     AppDatabase::class.java,
                     "seconds_since_database"
                 ).build()

                 INSTANCE = instance

                 instance
             }
         }
     }
}