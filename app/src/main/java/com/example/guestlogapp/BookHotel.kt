package com.example.guestlogapp


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

class BookHotel : AppCompatActivity() {

    private lateinit var etHotelName: EditText
    private lateinit var etHotelLocation: EditText
    private lateinit var etCheckInDate: EditText
    private lateinit var etCheckOutDate: EditText
    private lateinit var etNumOfGuests: EditText
    private lateinit var btnBookHotel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_hotel)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        // Initialize views
        etHotelName = findViewById(R.id.etHotelName)
        etHotelLocation = findViewById(R.id.etHotelLocation)
        etCheckInDate = findViewById(R.id.etCheckInDate)
        etCheckOutDate = findViewById(R.id.etCheckOutDate)
        etNumOfGuests = findViewById(R.id.etNumOfGuests)
        btnBookHotel = findViewById(R.id.btnBookHotel)

        // Set click listener for the Book Hotel button
        btnBookHotel.setOnClickListener {
            // Get user input
            val hotelName = etHotelName.text.toString()
            val location = etHotelLocation.text.toString()
            val checkInDate = etCheckInDate.text.toString()
            val checkOutDate = etCheckOutDate.text.toString()
            val numOfGuests = etNumOfGuests.text.toString()

            // Perform validation
            if (hotelName.isBlank() || location.isBlank() || checkInDate.isBlank() || checkOutDate.isBlank() || numOfGuests.isBlank()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Store booking details in Firebase
                val db = FirebaseFirestore.getInstance()
                val booking = hashMapOf(
                    "hotelName" to hotelName,
                    "location" to location,
                    "checkInDate" to checkInDate,
                    "checkOutDate" to checkOutDate,
                    "numOfGuests" to numOfGuests
                )
                db.collection("bookings")
                    .add(booking)
                    .addOnSuccessListener {
                        // Booking added successfully
                        val message = "Hotel booked successfully! Details: \n" +
                                "Hotel Name: $hotelName\n" +
                                "Location: $location\n" +
                                "Check-in Date: $checkInDate\n" +
                                "Check-out Date: $checkOutDate\n" +
                                "Number of Guests: $numOfGuests"
                        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener { e ->
                        // Failed to add booking
                        Toast.makeText(this, "Error booking hotel: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}
