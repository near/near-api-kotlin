package com.knear.android.user.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.knear.android.user.data.dao.UserDao
import com.knear.android.user.data.entities.UserInformationEntity

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = arrayOf(UserInformationEntity::class), version = 1, exportSchema = false)
abstract class NearDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: NearDatabase? = null

        fun getDatabase(application: Application): NearDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    application,
                    NearDatabase::class.java,
                    "near_wallet_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}