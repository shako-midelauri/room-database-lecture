package com.example.room.ui.edit_contact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.room.App
import com.example.room.database.Contact
import com.example.room.enums.TaskType
import kotlinx.coroutines.launch

class EditContactViewModel : ViewModel() {

    private val repository by lazy {
        App.repository
    }

    private val _contact = MutableLiveData<Contact>()
    val contact: LiveData<Contact>
        get() = _contact

    fun getContact(contact: Contact) {
        _contact.value = contact
    }

    fun performTask(contact: Contact, taskType: TaskType) = viewModelScope.launch {
        if (taskType == TaskType.ADD_CONTACT) {
            repository.addContact(contact)
        } else {
            repository.updateContact(contact)
        }
    }
}