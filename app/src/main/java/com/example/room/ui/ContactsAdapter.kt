package com.example.room.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.room.R
import com.example.room.database.Contact
import kotlinx.android.synthetic.main.item_contact.view.*

class ContactsAdapter(
    private val selectContact: (Contact) -> Unit,
    private val deleteContact: (Contact) -> Unit
) :
    RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    private var contacts: List<Contact> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val contact = contacts[position]

        holder.itemView.apply {

            fullNameTV.text = "${contact.firstName} ${contact.lastName}"
            numberTV.text = contact.number.toString()
            emailTV.text = contact.email

            setOnClickListener {
                selectContact.invoke(contact)
            }

            setOnLongClickListener {
                deleteContact.invoke(contact)
                true
            }
        }
    }

    override fun getItemCount() = contacts.size

    fun submitList(list: List<Contact>) {
        contacts = list
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}