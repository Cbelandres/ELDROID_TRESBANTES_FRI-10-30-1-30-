package com.example.guestlogapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LogIn : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        // Initialize Firebase Auth
        auth = Firebase.auth

        val usernameEditText = findViewById<EditText>(R.id.usernameEditText1)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText2)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val registerTextView = findViewById<TextView>(R.id.registerTextView)
        val forgotPasswordTextView = findViewById<TextView>(R.id.forgotPasswordTextView)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Sign in user with email and password
                auth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            val user = auth.currentUser
                            Toast.makeText(
                                this, "Login successful.",
                                Toast.LENGTH_SHORT
                            ).show()
                            // Add a log to verify the user and navigation
                            if (user != null) {
                                // Ensure that the user is not null before navigating
                                startActivity(Intent(this, Dashboard::class.java))
                                finish()
                            } else {
                                Toast.makeText(this, "User is null", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(
                                baseContext, "Authentication failed. ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }

        // Navigate to RegistrationActivity when Register TextView is clicked
        registerTextView.setOnClickListener {
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }

        // Navigate to ForgotPassword activity when forgot password TextView is clicked
        forgotPasswordTextView.setOnClickListener {
            // Add your forgot password functionality here
            startActivity(Intent(this, ForgotPassword::class.java))
            Toast.makeText(this, "Forgot Password clicked", Toast.LENGTH_SHORT).show()
        }

    }
}


