package com.example.room

import android.app.Application
import com.example.room.database.ContactsDatabase
import com.example.room.repository.ContactsRepository
import com.example.room.repository.ContactsRepositoryImpl

class App : Application() {

    companion object {
        private lateinit var instance: App

        private val database: ContactsDatabase by lazy {
            ContactsDatabase.buildDatabase(instance)
        }

        val repository: ContactsRepository by lazy {
            ContactsRepositoryImpl(
                database.contactDao()
            )
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}