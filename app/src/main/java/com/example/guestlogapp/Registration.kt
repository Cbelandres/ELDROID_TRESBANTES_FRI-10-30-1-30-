package com.example.guestlogapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Registration : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        // Initialize Firebase Auth
        auth = Firebase.auth

        val fullNameEditText = findViewById<EditText>(R.id.editTextFullName)
        val userNameEditText = findViewById<EditText>(R.id.editTextUserName)
        val passwordEditText = findViewById<EditText>(R.id.editTextPassword)
        val confirmPasswordEditText = findViewById<EditText>(R.id.editTextConfirmPassword)
        val emailEditText = findViewById<EditText>(R.id.editTextEmail)
        val phoneNumberEditText = findViewById<EditText>(R.id.editTextPhoneNumber)
        val registerButton = findViewById<Button>(R.id.buttonRegister)
        val cancelButton = findViewById<Button>(R.id.buttonCancel)

        registerButton.setOnClickListener {
            val fullName = fullNameEditText.text.toString().trim()
            val userName = userNameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val phoneNumber = phoneNumberEditText.text.toString().trim()

            if (fullName.isEmpty() || userName.isEmpty() || password.isEmpty() ||
                confirmPassword.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()
            ) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                // Create user with email and password
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            val user = auth.currentUser
                            Toast.makeText(
                                this, "Registration successful.",
                                Toast.LENGTH_SHORT
                            ).show()
                            // You can navigate to another activity or perform other actions here
                            startActivity(Intent(this, LogIn::class.java))
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(
                                baseContext, "Registration failed. ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
        cancelButton.setOnClickListener {
            // Navigate back to the Main
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

}
