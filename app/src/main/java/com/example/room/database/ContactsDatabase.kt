package com.example.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.room.database.ContactsDatabase.Companion.DATABASE_VERSION

@Database(entities = [Contact::class], version = DATABASE_VERSION)
abstract class ContactsDatabase : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "contacts"
        const val DATABASE_VERSION = 1

        fun buildDatabase(context: Context): ContactsDatabase {
            return Room.databaseBuilder(context, ContactsDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }

    abstract fun contactDao(): ContactDao
}