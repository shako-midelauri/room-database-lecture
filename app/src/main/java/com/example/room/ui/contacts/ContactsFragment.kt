package com.example.room.ui.contacts

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log.d
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.room.R
import com.example.room.database.Contact
import com.example.room.ui.ContactsAdapter
import com.example.room.enums.SortType
import com.example.room.enums.TaskType
import kotlinx.android.synthetic.main.contacts_fragment.*
import kotlinx.android.synthetic.main.item_contact.*

class ContactsFragment : Fragment() {

    private lateinit var viewModel: ContactsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ContactsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.contacts_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ContactsAdapter(::selectContact, viewModel::deleteContact)

        recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                RecyclerView.VERTICAL
            )
        )
        recyclerView.adapter = adapter

        viewModel.contacts.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        addButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_contactsFragment_to_editContactFragment,
                bundleOf("task_type" to TaskType.ADD_CONTACT)
            )
        }

        setHasOptionsMenu(true)
    }

    private fun selectContact(contact: Contact) {
        val bundle = Bundle()
        bundle.apply {
            putParcelable("contact", contact)
            putParcelable("task_type", TaskType.EDIT_CONTACT)
        }
        findNavController().navigate(
            R.id.action_contactsFragment_to_editContactFragment,
            bundle
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.contacts_menu, menu)

        val searchItem = menu.findItem(R.id.searchItem)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.search(newText.orEmpty())
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {

            R.id.sortByFirstName -> {
                viewModel.sort(SortType.FIRST_NAME)
                return true
            }
            R.id.sortByLastName -> {
                viewModel.sort(SortType.LAST_NAME)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}