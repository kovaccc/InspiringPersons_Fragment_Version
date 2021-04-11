package com.example.inspiringpersons.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.inspiringpersons.model.InspiringPerson
import com.example.inspiringpersons.model.Quote
import com.example.inspiringpersons.utilities.Constants.DATABASE_NAME

@Database(entities = [Quote::class, InspiringPerson::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun inspiringPersonDao(): InspiringPersonDao
    abstract fun quoteDao(): QuoteDao

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                }
            }).build()
        }
    }
}
