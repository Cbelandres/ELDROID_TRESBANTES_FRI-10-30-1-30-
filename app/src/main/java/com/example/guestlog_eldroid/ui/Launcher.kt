package com.example.guestlog_eldroid.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.guestlog_eldroid.R

class Launcher : AppCompatActivity() {

    private lateinit var buttonLogin: Button
    private lateinit var buttonRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher) // Ensure this points to the correct layout

        // Initialize the views
        // logoImageView2 = findViewById(R.id.logoImageView2)


        val buttonLogin: Button = findViewById(R.id.buttonLogin)
        buttonLogin.setOnClickListener{
            startActivity(Intent(this@Launcher,LogIn::class.java))
        }

        val buttonRegister:Button = findViewById(R.id.buttonRegister)
        buttonRegister.setOnClickListener{
            startActivity(Intent(this@Launcher, Registration::class.java))
        }

        buttonLogin.setOnClickListener {
            // Change background color when clicked
            buttonLogin.setBackgroundColor(Color.RED)
        }
        // Load GIF using Glide
        //Glide.with(this).load(R.drawable.giphy_1).into(logoImageView2)

        buttonLogin.setOnClickListener {
            val intent = Intent(this, LogIn::class.java) // Ensure correct activity name
            startActivity(intent)
        }


        buttonRegister.setOnClickListener {
            val intent = Intent(this, Registration::class.java) // Ensure correct activity name
            startActivity(intent)
        }

    }

}