package com.example.room.repository

import androidx.lifecycle.LiveData
import com.example.room.database.Contact
import com.example.room.database.ContactDao
import com.example.room.enums.SortType
import kotlinx.coroutines.flow.Flow

class ContactsRepositoryImpl(
    private val contactDao: ContactDao
) : ContactsRepository {

    override suspend fun addContact(contact: Contact) = contactDao.addContact(contact)

    override suspend fun prepopulate(contactList: List<Contact>) = contactDao.prepopulate(contactList)

    override suspend fun getAll(): List<Contact> = contactDao.getAll()

    override suspend fun updateContact(contact: Contact) = contactDao.updateContact(contact)

    override suspend fun deleteContact(contact: Contact) = contactDao.deleteContact(contact)

    override fun search(searchQuery: String, sortType: SortType): Flow<List<Contact>> = contactDao.getContacts(searchQuery, sortType)
}