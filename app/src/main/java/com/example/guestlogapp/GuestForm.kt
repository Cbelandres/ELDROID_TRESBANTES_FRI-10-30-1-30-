package com.example.guestlogapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class GuestForm : AppCompatActivity() {
    private lateinit var etGuestName: EditText
    private lateinit var etGuestEmail: EditText
    private lateinit var etTimeIn: EditText
    private lateinit var etDate: EditText
    private lateinit var etNumber: EditText
    private lateinit var etAge: EditText
    private lateinit var btnSubmit: Button

    private var guest: Guest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_form)

        // Initialize views
        etGuestName = findViewById(R.id.etGuestName)
        etGuestEmail = findViewById(R.id.etGuestEmail)
        etTimeIn = findViewById(R.id.etTimeIn)
        etDate = findViewById(R.id.etDate)
        etNumber = findViewById(R.id.etNumber)
        etAge = findViewById(R.id.etAge)
        btnSubmit = findViewById(R.id.btnSubmit)

        guest = intent.getSerializableExtra("guest") as Guest?

        guest?.let {
            etGuestName.setText(it.name)
            etGuestEmail.setText(it.email)
            etTimeIn.setText(it.timeIn)
            etDate.setText(it.date)
            etNumber.setText(it.number)
            etAge.setText(it.age)
        }

        // Set click listener for the button
        btnSubmit.setOnClickListener {
            val name = etGuestName.text.toString()
            val email = etGuestEmail.text.toString()
            val timeIn = etTimeIn.text.toString()
            val date = etDate.text.toString()
            val number = etNumber.text.toString()
            val age = etAge.text.toString()

            // Check if all fields are filled
            if (name.isNotEmpty() && email.isNotEmpty() && timeIn.isNotEmpty() && date.isNotEmpty() && number.isNotEmpty() && age.isNotEmpty()) {
                val newGuest = Guest(name, email, timeIn, date, number, age)

                val resultIntent = intent
                resultIntent.putExtra("guest", newGuest)
                setResult(RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
