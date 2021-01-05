package com.example.room.repository

import com.example.room.database.Contact
import com.example.room.enums.SortType
import kotlinx.coroutines.flow.Flow

interface ContactsRepository {

    suspend fun addContact(contact: Contact)

    suspend fun prepopulate(contactList: List<Contact>)

    suspend fun getAll(): List<Contact>

    suspend fun updateContact(contact: Contact)

    suspend fun deleteContact(contact: Contact)

    fun search(searchQuery: String, sortType: SortType): Flow<List<Contact>>
}