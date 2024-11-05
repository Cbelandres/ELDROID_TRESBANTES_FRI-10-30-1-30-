package com.example.guestlogapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditGuestActivity : AppCompatActivity() {

    private lateinit var etGuestName: EditText
    private lateinit var etGuestEmail: EditText
    // Add other EditText fields as needed

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_guest)

        // Initialize views and retrieve the guest information to be edited

        val btnSubmit: Button = findViewById(R.id.btnSubmit)
        btnSubmit.setOnClickListener {
            // Retrieve edited data
            val editedGuestName = etGuestName.text.toString()
            val editedGuestEmail = etGuestEmail.text.toString()
            // Retrieve other edited data

            // Pass the edited data back to the guest info activity
            val resultIntent = Intent()
            resultIntent.putExtra("editedGuestName", editedGuestName)
            resultIntent.putExtra("editedGuestEmail", editedGuestEmail)
            // Add other edited data as extras

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}

