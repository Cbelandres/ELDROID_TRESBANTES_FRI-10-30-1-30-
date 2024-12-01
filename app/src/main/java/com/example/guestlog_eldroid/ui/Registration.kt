package com.example.guestlog_eldroid.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.guestlog_eldroid.data.ApiService
import com.example.guestlog_eldroid.data.User
import com.example.guestlog_eldroid.data.ApiResponse
import com.example.guestlog_eldroid.utils.RetrofitClient
import com.example.guestlog_eldroid.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Registration : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val fullNameEditText = findViewById<EditText>(R.id.editTextFullName)
        val userNameEditText = findViewById<EditText>(R.id.editTextUserName)
        val passwordEditText = findViewById<EditText>(R.id.editTextPassword)
        val confirmPasswordEditText = findViewById<EditText>(R.id.editTextConfirmPassword)
        val emailEditText = findViewById<EditText>(R.id.editTextEmail)
        val phoneNumberEditText = findViewById<EditText>(R.id.editTextPhoneNumber)
        val registerButton = findViewById<Button>(R.id.buttonRegister)

        registerButton.setOnClickListener {
            val fullName = fullNameEditText.text.toString()
            val userName = userNameEditText.text.toString()
            val email = emailEditText.text.toString()
            val phone = phoneNumberEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (password.length < 8) {
                Toast.makeText(this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = User(fullName, userName, email, password, phone)
            val apiService = RetrofitClient.instance.create(ApiService::class.java)

            apiService.register(user).enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        Toast.makeText(this@Registration, "Registered Successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Toast.makeText(
                            this@Registration,
                            "Error: ${response.code()} - ${response.message()}\n$errorBody",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Toast.makeText(this@Registration, "Network Error: ${t.message}", Toast.LENGTH_LONG).show()
                    t.printStackTrace()
                }

            })
        }
    }
}
