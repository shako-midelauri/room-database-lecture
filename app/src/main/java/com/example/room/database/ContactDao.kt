package com.example.room.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.room.enums.SortType
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    fun getContacts(searchQuery: String, sortType: SortType) = when (sortType) {
            SortType.FIRST_NAME -> orderByFirstName(searchQuery)
            SortType.LAST_NAME -> orderByLastName(searchQuery)
        }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addContact(contact: Contact)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun prepopulate(contactList: List<Contact>)

    @Query("SELECT * FROM contacts")
    suspend fun getAll(): List<Contact>

    @Update
    suspend fun updateContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("SELECT * FROM contacts WHERE first_name LIKE '%' || :searchQuery ||'%' OR last_name  LIKE '%' || :searchQuery ||'%' OR number LIKE '%' || :searchQuery ||'%' OR email  LIKE '%' || :searchQuery ||'%' ORDER BY first_name ASC")
    fun orderByFirstName(searchQuery: String): Flow<List<Contact>>

    @Query("SELECT * FROM contacts WHERE first_name LIKE '%' || :searchQuery ||'%' OR last_name  LIKE '%' || :searchQuery ||'%' OR number LIKE '%' || :searchQuery ||'%' OR email  LIKE '%' || :searchQuery ||'%' ORDER BY last_name ASC ")
    fun orderByLastName(searchQuery: String): Flow<List<Contact>>

    @Query("DELETE FROM contacts")
    fun deleteAll()

    @Query("DELETE FROM contacts WHERE number LIKE '599%' ")
    fun deleteMagtiUsers()
}