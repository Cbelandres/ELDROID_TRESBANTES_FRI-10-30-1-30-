package com.example.guestlogapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

@Suppress("DEPRECATION")
class Dashboard : AppCompatActivity() {

    private lateinit var guestAdapter: GuestAdapter
    private lateinit var rvGuestList: RecyclerView
    private lateinit var btnAddGuest: Button
    private lateinit var btnGuestInfo: Button
    private lateinit var btnBookHotel: Button

    private val ADD_GUEST_REQUEST = 1
    private val EDIT_GUEST_REQUEST = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Initialize views
        rvGuestList = findViewById(R.id.rvGuestList)
        btnAddGuest = findViewById(R.id.btnAddGuest)
        btnGuestInfo = findViewById(R.id.GuestInfo)
        btnBookHotel = findViewById(R.id.BookHotel)

        // Set up RecyclerView
        rvGuestList.layoutManager = LinearLayoutManager(this)
        guestAdapter = GuestAdapter(
            onEditClick = { guest -> editGuest(guest) },
            onDeleteClick = { guest -> deleteGuest(guest) }
        )
        rvGuestList.adapter = guestAdapter

        // Set click listener for the Add Guest button
        btnAddGuest.setOnClickListener {
            startActivityForResult(Intent(this, GuestForm::class.java), ADD_GUEST_REQUEST)
        }

        // Set click listener for the Guest Info button
        btnGuestInfo.setOnClickListener {
            startActivity(Intent(this, GuestInfo::class.java))
        }

        // Set click listener for the Book Hotel button
        btnBookHotel.setOnClickListener {
            startActivity(Intent(this, BookHotel::class.java))
        }

        // Load guest profiles from Firebase or any other data source
        loadGuests()
    }



    private fun loadGuests() {
        // Fetch guests from Firebase or any other data source
        val guests = listOf(
            Guest("Jerry Tolentino", "Jerry@gmail.com", "10:00 AM", "2024-06-01", "09669011936", "30"),
            Guest("Janice Cayon", "Janice@gmail.com", "11:00 AM", "2024-06-01", "09625692817", "25"),
            Guest("Crisy Belandres", "crizeldabelandres@gmail.com", "11:00 AM", "2024-06-01", "09625692817", "25")
        )
        guestAdapter.submitList(guests)
    }

    private fun editGuest(guest: Guest) {
        // Launch GuestForm activity with the guest details for editing
        val intent = Intent(this, GuestForm::class.java).apply {
            putExtra("guest", guest)
        }
        startActivityForResult(intent, EDIT_GUEST_REQUEST)
    }

    private fun deleteGuest(guest: Guest) {
        // Remove the guest from the data source and update the adapter
        val updatedGuests = guestAdapter.guestList.filter { it != guest }
        guestAdapter.submitList(updatedGuests)
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            val guest = data.getSerializableExtra("guest") as Guest
            when (requestCode) {
                ADD_GUEST_REQUEST -> {
                    val updatedGuests = guestAdapter.guestList.toMutableList()
                    updatedGuests.add(guest)
                    guestAdapter.submitList(updatedGuests)
                }
                EDIT_GUEST_REQUEST -> {
                    val editedGuestName = data.getStringExtra("editedGuestName") ?: ""
                    val editedGuestEmail = data.getStringExtra("editedGuestEmail") ?: ""
                    val updatedGuests = guestAdapter.guestList.map { existingGuest ->
                        if (existingGuest.name == editedGuestName) {
                            guest.copy(name = editedGuestName, email = editedGuestEmail)
                        } else {
                            existingGuest
                        }
                    }
                    guestAdapter.submitList(updatedGuests)
                }
            }
        }
    }
}

