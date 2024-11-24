package com.example.guestlog_eldroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.UUID

class GuestForm : Fragment() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_guest_form, container, false)

        dbHelper = DatabaseHelper(requireContext())

        val etGuestName = view.findViewById<EditText>(R.id.etGuestName)
        val etGuestEmail = view.findViewById<EditText>(R.id.etGuestEmail)
        val etTimeIn = view.findViewById<EditText>(R.id.etTimeIn)
        val etDate = view.findViewById<EditText>(R.id.etDate)
        val etNumber = view.findViewById<EditText>(R.id.etNumber)
        val etAge = view.findViewById<EditText>(R.id.etAge)
        val btnSubmit = view.findViewById<MaterialButton>(R.id.btnSubmit)

        btnSubmit.setOnClickListener {
            val name = etGuestName.text.toString().trim()
            val email = etGuestEmail.text.toString().trim()
            val timeIn = etTimeIn.text.toString().trim().ifEmpty { null }
            val date = etDate.text.toString().trim().ifEmpty { null }
            val phoneNumber = etNumber.text.toString().trim().ifEmpty { null }
            val age = etAge.text.toString().trim()

            if (name.isNotEmpty() && email.isNotEmpty() && age.isNotEmpty()) {
                val guest = Guest(
                    id = UUID.randomUUID().toString(),
                    name = name,
                    email = email,
                    timeIn = timeIn,
                    date = date,
                    phoneNumber = phoneNumber,
                    age = age
                )

                addGuestToAPIAndDatabase(guest)
            } else {
                Toast.makeText(requireContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun addGuestToAPIAndDatabase(guest: Guest) {
        ApiClient.guestApiService.addGuest(guest).enqueue(object : Callback<Guest> {
            override fun onResponse(call: Call<Guest>, response: Response<Guest>) {
                if (response.isSuccessful) {
                    // Add guest to SQLite
                    dbHelper.addGuest(
                        guest.name, guest.email, guest.timeIn, guest.date,
                        guest.phoneNumber, guest.age
                    )

                    Toast.makeText(requireContext(), "Guest added successfully!", Toast.LENGTH_SHORT).show()

                    // Navigate to GuestsFragment
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, GuestsFragment())
                        .commit()
                } else {
                    Toast.makeText(requireContext(), "Failed to add guest: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Guest>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}

