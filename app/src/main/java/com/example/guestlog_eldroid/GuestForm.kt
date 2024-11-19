package com.example.guestlog_eldroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton

class GuestForm : Fragment() {

    // Access shared ViewModel
    private val guestViewModel: GuestViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_guest_form, container, false)

        val etGuestName = view.findViewById<EditText>(R.id.etGuestName)
        val etGuestEmail = view.findViewById<EditText>(R.id.etGuestEmail)
        val etTimeIn = view.findViewById<EditText>(R.id.etTimeIn)
        val etDate = view.findViewById<EditText>(R.id.etDate)
        val etNumber = view.findViewById<EditText>(R.id.etNumber)
        val etAge = view.findViewById<EditText>(R.id.etAge)
        val btnSubmit = view.findViewById<MaterialButton>(R.id.btnSubmit)

        btnSubmit.setOnClickListener {
            val name = etGuestName.text.toString()
            val email = etGuestEmail.text.toString()
            val timeIn = etTimeIn.text.toString()
            val date = etDate.text.toString()
            val phoneNumber = etNumber.text.toString()
            val age = etAge.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty()) {
                val guest = Guest(name, email, timeIn, date, phoneNumber, age)
                guestViewModel.addGuest(guest)

                // Navigate to GuestsFragment
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.frameLayout, GuestsFragment())
                    ?.commit()

                val bottomNavigationView = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
                bottomNavigationView?.selectedItemId = R.id.nav_guests
            } else {
                Toast.makeText(activity, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}
