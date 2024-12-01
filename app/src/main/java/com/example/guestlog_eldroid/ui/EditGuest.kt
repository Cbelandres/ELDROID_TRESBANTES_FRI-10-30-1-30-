package com.example.guestlog_eldroid.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.guestlog_eldroid.R
import com.google.android.material.button.MaterialButton

class EditGuestFragment : Fragment() {

    // Declare UI elements
    private lateinit var etGuestName: EditText
    private lateinit var etGuestEmail: EditText
    private lateinit var etNumber: EditText
    private lateinit var etAge: EditText
    private lateinit var btnSaveChanges: MaterialButton
    private lateinit var btnCancel: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_guest, container, false)

        // Bind UI components
        etGuestName = view.findViewById(R.id.etGuestName)
        etGuestEmail = view.findViewById(R.id.etGuestEmail)
        etNumber = view.findViewById(R.id.etNumber)
        etAge = view.findViewById(R.id.etAge)
        btnSaveChanges = view.findViewById(R.id.btnSaveChanges)
        btnCancel = view.findViewById(R.id.btnCancel)

        // Set click listeners for buttons
        btnSaveChanges.setOnClickListener {
            saveChanges()
        }

        btnCancel.setOnClickListener {
            cancelChanges()
        }

        return view
    }

    // Save Changes Logic
    private fun saveChanges() {
        val name = etGuestName.text.toString()
        val email = etGuestEmail.text.toString()
        val phone = etNumber.text.toString()
        val age = etAge.text.toString()

        if (name.isBlank() || email.isBlank() || phone.isBlank() || age.isBlank()) {
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Here, update your database or send data to the parent activity
        Toast.makeText(requireContext(), "Changes Saved", Toast.LENGTH_SHORT).show()
    }

    // Cancel Changes Logic
    private fun cancelChanges() {
        // Clear the fields or navigate back
        etGuestName.text.clear()
        etGuestEmail.text.clear()
        etNumber.text.clear()
        etAge.text.clear()
        Toast.makeText(requireContext(), "Changes Cancelled", Toast.LENGTH_SHORT).show()
    }
}

