package com.example.guestlog_eldroid.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.guestlog_eldroid.R
import androidx.appcompat.app.AppCompatActivity
import com.example.guestlog_eldroid.viewmodel.GuestViewModel
import com.example.guestlog_eldroid.utils.RetrofitClient
import com.example.guestlog_eldroid.data.ApiService
import com.example.guestlog_eldroid.data.LoginRequest
import com.example.guestlog_eldroid.data.ApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        val usernameEditText1 = findViewById<EditText>(R.id.usernameEditText1)
        val passwordEditText2 = findViewById<EditText>(R.id.passwordEditText2)
        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            val email = usernameEditText1.text.toString()
            val password = passwordEditText2.text.toString()

            val request = LoginRequest(email, password)
            val apiService = RetrofitClient.instance.create(ApiService::class.java)

            apiService.login(request).enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        val apiResponse = response.body()
                        if (apiResponse?.success == true) {
                            Toast.makeText(this@LogIn, "Login Successful", Toast.LENGTH_SHORT).show()

                            // Save the user's email in SharedPreferences
                            val sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                            with(sharedPref.edit()) {
                                putString("user_email", email)
                                apply()
                            }

                            // Pass the email to GuestViewModel after successful login
                            GuestViewModel().setCurrentUserEmail(email) // Set email in ViewModel

                            startActivity(Intent(this@LogIn, Dashboard::class.java))
                            finish()
                        } else {
                            Toast.makeText(this@LogIn, apiResponse?.message ?: "Login Failed", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        val errorMessage = response.errorBody()?.string() ?: "Unknown Error"
                        Toast.makeText(this@LogIn, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Toast.makeText(this@LogIn, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
