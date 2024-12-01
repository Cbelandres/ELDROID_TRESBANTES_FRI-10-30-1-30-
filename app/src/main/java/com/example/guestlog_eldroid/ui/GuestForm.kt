package com.example.guestlog_eldroid.ui

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.guestlog_eldroid.data.models.Guest
import com.example.guestlog_eldroid.data.GuestsFragment
import com.example.guestlog_eldroid.R
import com.example.guestlog_eldroid.viewmodel.GuestViewModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class GuestForm : Fragment() {

    private val guestViewModel: GuestViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_guest_form, container, false)

        val etGuestName = view.findViewById<EditText>(R.id.etGuestName)
        val etGuestEmail = view.findViewById<EditText>(R.id.etGuestEmail)
        val etCost = view.findViewById<EditText>(R.id.etCost)
        val etDate = view.findViewById<EditText>(R.id.etDate)
        val etNumber = view.findViewById<EditText>(R.id.etNumber)
        val etAge = view.findViewById<EditText>(R.id.etAge)
        val btnSubmit = view.findViewById<MaterialButton>(R.id.btnSubmit)

        // Retrieve the logged-in user's email from SharedPreferences
        val sharedPref = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val createdBy = sharedPref.getString("user_email", null)

        if (createdBy == null) {
            Toast.makeText(activity, "Error: User not logged in", Toast.LENGTH_SHORT).show()
            activity?.finish() // Redirect to login if email is not found
            return null
        }

        // Date Picker for Date Input
        etDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    val selectedDate = "$year-${month + 1}-$dayOfMonth"
                    etDate.setText(selectedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }

        btnSubmit.setOnClickListener {
            val name = etGuestName.text.toString()
            val email = etGuestEmail.text.toString()
            val cost = etCost.text.toString()
            val date = etDate.text.toString()
            val phoneNumber = etNumber.text.toString()
            val age = etAge.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && date.isNotEmpty()) {
                val guest = Guest(
                    id = null, // Explicitly set to null for new guests
                    name = name,
                    email = email,
                    cost = cost.toDouble(),
                    date = date,
                    phoneNumber = phoneNumber,
                    age = age.toInt(),
                    createdBy = createdBy
                )

                // Observe the result of addGuest
                guestViewModel.addGuest(guest).observe(viewLifecycleOwner) { response ->
                    if (response.success) {
                        Toast.makeText(activity, "Guest added successfully", Toast.LENGTH_SHORT).show()

                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.frameLayout, GuestsFragment())
                            ?.commit()

                        val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
                        bottomNavigationView?.selectedItemId = R.id.nav_guests
                    } else {
                        Toast.makeText(activity, response.message ?: "Failed to add guest", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(activity, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
            }
        }


        return view
    }
}
