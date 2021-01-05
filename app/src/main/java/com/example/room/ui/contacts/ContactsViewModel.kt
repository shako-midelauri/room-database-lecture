package com.example.room.ui.contacts

import android.util.Log.d
import androidx.lifecycle.*
import com.example.room.App
import com.example.room.database.Contact
import com.example.room.enums.SortType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class ContactsViewModel : ViewModel() {

    private val repository by lazy {
        App.repository
    }

    private val _searchQuery = MutableStateFlow("")
    private val _sortType = MutableStateFlow(SortType.FIRST_NAME)

    private val contactsFlow = combine(_sortType, _searchQuery) { sortType, searchQuery ->
        Pair(searchQuery, sortType)
    }.flatMapLatest { (searchQuery, sortType) ->
        repository.search(searchQuery, sortType)
    }
    var contacts = contactsFlow.asLiveData()

    init {
        viewModelScope.launch {

            if (repository.getAll().isEmpty()) {
                repository.prepopulate(
                    listOf(
                        Contact("Nathaniel", "Layton", 903416575, "nLayton@mail.com"),
                        Contact("Ahmad", "Wolff", 611641824, "mkassulke@aprilmovo.com"),
                        Contact("Loma", "Gutmann", 933808141, "hpfeffer@life-recipes.gq"),
                        Contact("Alfreda", "Balistreri ", 443845114, "vokon@vipmail.com"),
                        Contact("John", "Doe", 574394055, "jdoe@mail.com"),
                        Contact("Norman", "Coy", 209827776, "ncoy@gmail.com"),
                        Contact("Kendall", "McClure", 516245031, "christophe15@ntdx.pw")
                    )
                )
            }
        }
    }

    fun search(query: String) {
        _searchQuery.value = query
    }

    fun sort(sortType: SortType) {
        _sortType.value = sortType
    }

    fun deleteContact(contact: Contact) = viewModelScope.launch {
        repository.deleteContact(contact)
    }
}