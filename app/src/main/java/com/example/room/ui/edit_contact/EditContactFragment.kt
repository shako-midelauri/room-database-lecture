package com.example.room.ui.edit_contact

import android.content.Context
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.room.R
import com.example.room.database.Contact
import com.example.room.enums.TaskType
import kotlinx.android.synthetic.main.fragment_edit_contact.*

class EditContactFragment : Fragment() {

    private lateinit var viewModel: EditContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EditContactViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val taskType = arguments?.getParcelable<TaskType>("task_type")!!

        if (taskType == TaskType.EDIT_CONTACT) {
            val contact = arguments?.getParcelable<Contact>("contact")
            if (contact != null) {
                viewModel.getContact(contact)

                viewModel.contact.observe(viewLifecycleOwner, {
                    firstNameET.setText(it.firstName)
                    lastNameET.setText(it.lastName)
                    numberET.setText(it.number.toString())
                    emailET.setText(it.email)
                })
            }
        }

        saveButton.setOnClickListener {
            val inputMethod =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethod.hideSoftInputFromWindow(view.windowToken, 0)

            val newContact = Contact(
                firstNameET.text.toString(),
                lastNameET.text.toString(),
                numberET.text.toString().toLong(),
                emailET.text.toString()
            )
            viewModel.performTask(newContact, taskType)
            findNavController().popBackStack()
        }

        setHasOptionsMenu(true)
    }
}