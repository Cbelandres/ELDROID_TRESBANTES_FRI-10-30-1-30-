package com.example.guestlogapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class ForgotPassword : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val sendResetEmailButton = findViewById<Button>(R.id.sendResetEmailButton)

        sendResetEmailButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()

            if (email.isNotEmpty()) {
                // Send password reset email
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Email sent successfully
                            Toast.makeText(
                                this,
                                "Password reset email sent to $email",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        } else {
                            // Failed to send email
                            Toast.makeText(
                                this,
                                "Failed to send password reset email. Please try again.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                // Email field is empty
                Toast.makeText(
                    this,
                    "Please enter your email address",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}