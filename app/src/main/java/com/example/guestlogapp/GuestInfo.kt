package com.example.guestlogapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GuestInfo : AppCompatActivity() {

    private lateinit var headerTextView: TextView
    private lateinit var guestInfoListView: ListView
    private lateinit var guestDetailsLayout: View
    private lateinit var guestImageView: ImageView
    private lateinit var guestNumberTextView: TextView
    private lateinit var guestNameTextView: TextView
    private lateinit var guestTypeTextView: TextView
    private lateinit var transactionHistoryTextView: TextView
    private lateinit var transactionListView: ListView

    private var guests = mutableListOf(
        Guest("Jerry Tolentino", "Jerry@gmail.com", "10:00 AM", "2024-06-01", "09669011936", "30"),
        Guest("Janice Cayon", "Janice@gmail.com", "11:00 AM", "2024-06-01", "09625692817", "25"),
        Guest("Crisy Belandres", "crizeldabelandres@gmail.com", "11:00 AM", "2024-06-01", "09625692817", "25")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_info)

        // Initialize views
        headerTextView = findViewById(R.id.headerTextView)
        guestInfoListView = findViewById(R.id.GuestInfoListView)
        guestDetailsLayout = findViewById(R.id.GuestDetailsLayout)
        guestImageView = findViewById(R.id.GuestImageView)
        guestNumberTextView = findViewById(R.id.GuestNumberTextView)
        guestNameTextView = findViewById(R.id.GuestNameTextView)
        guestTypeTextView = findViewById(R.id.GuestTypeTextView)
        transactionHistoryTextView = findViewById(R.id.transactionHistoryTextView)
        transactionListView = findViewById(R.id.transactionListView)

        // Load guests and set adapter
        loadGuests()

        guestInfoListView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                guestDetailsLayout.visibility = View.VISIBLE
                val selectedGuest = guests[position]
                updateGuestDetails(selectedGuest)
            }
    }

    private fun loadGuests() {
        // Display guests in ListView
        val guestNames = guests.map { it.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, guestNames)
        guestInfoListView.adapter = adapter
    }

    @SuppressLint("SetTextI18n")
    private fun updateGuestDetails(guest: Guest) {
        guestImageView.setImageResource(R.drawable.guest_icon) // Placeholder, update with actual logic if needed
        guestNumberTextView.text = "Guest Number: ${guest.number}"
        guestNameTextView.text = "Guest Name: ${guest.name}"
        guestTypeTextView.text = "Guest Type: Regular" // Update with actual type if available

        // Mock data for transaction history
        val transactions = arrayOf("January - $100", "February - $150", "March - $200")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, transactions)
        transactionListView.adapter = adapter
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            val editedGuestName = data.getStringExtra("editedGuestName")
            val editedGuestEmail = data.getStringExtra("editedGuestEmail")
            // Handle other edited data if needed

            // Update the guest details if the edited guest matches the selected guest
            val selectedGuestName = guestNameTextView.text.toString().substringAfter("Guest Name: ").trim()
            if (selectedGuestName == editedGuestName) {
                // Update guest details
                guestNameTextView.text = "Guest Name: $editedGuestName"
                // Update other guest details as needed

                // Find the index of the edited guest in the guests list
                val index = guests.indexOfFirst { it.name == selectedGuestName }
                if (index != -1) {
                    // Update the guest in the list
                    guests[index] = Guest(editedGuestName!!, editedGuestEmail, guests[index].timeIn, guests[index].date, guests[index].number, guests[index].age)
                    // Reload the guest details
                    updateGuestDetails(guests[index])
                }
            }
        }
    }

}
