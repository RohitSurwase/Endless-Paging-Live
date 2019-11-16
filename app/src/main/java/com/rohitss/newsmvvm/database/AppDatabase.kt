package com.rohitss.newsmvvm.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rohitss.newsmvvm.model.NewsDAO
import com.rohitss.newsmvvm.model.NewsItem
import com.rohitss.newsmvvm.utils.DATABASE_NAME
import com.rohitss.newsmvvm.utils.DATABASE_VERSION

@Database(entities = [NewsItem::class], version = DATABASE_VERSION, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getNewsDAO(): NewsDAO

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context)
                    .also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, DATABASE_NAME
            ).build()
        }
    }
}