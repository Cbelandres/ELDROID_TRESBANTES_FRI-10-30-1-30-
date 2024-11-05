package com.example.guestlogapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import android.widget.Button
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var logoImageView2: ImageView
    private lateinit var buttonLogin: Button
    private lateinit var buttonRegister: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Ensure this points to the correct layout

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()



        // Initialize the views
       // logoImageView2 = findViewById(R.id.logoImageView2)


        val buttonLogin: Button = findViewById(R.id.buttonLogin)
        buttonLogin.setOnClickListener{
            startActivity(Intent(this@MainActivity,LogIn::class.java))
        }

        val buttonRegister:Button = findViewById(R.id.buttonRegister)
        buttonRegister.setOnClickListener{
            startActivity(Intent(this@MainActivity, Registration::class.java))
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