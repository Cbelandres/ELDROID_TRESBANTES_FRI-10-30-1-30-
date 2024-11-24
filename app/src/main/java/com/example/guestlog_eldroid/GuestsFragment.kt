package com.example.guestlog_eldroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GuestsFragment : Fragment() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var guestsAdapter: GuestsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_guests, container, false)

        dbHelper = DatabaseHelper(requireContext())

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvGuestList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize adapter with empty list
        guestsAdapter = GuestsAdapter(mutableListOf(), requireContext(), dbHelper)
        recyclerView.adapter = guestsAdapter

        fetchGuestsFromAPI()

        return view
    }

    private fun fetchGuestsFromAPI() {
        ApiClient.guestApiService.getGuests().enqueue(object : Callback<List<Guest>> {
            override fun onResponse(call: Call<List<Guest>>, response: Response<List<Guest>>) {
                if (response.isSuccessful) {
                    val guests = response.body() ?: emptyList()

                    // Save guests to SQLite
                    dbHelper.clearGuests() // Clear local database before syncing
                    guests.forEach { guest ->
                        dbHelper.addGuest(
                            guest.name,
                            guest.email,
                            guest.timeIn,
                            guest.date,
                            guest.phoneNumber,
                            guest.age
                        )
                    }

                    // Update the adapter with new data
                    guestsAdapter.updateGuests(guests)
                } else {
                    Toast.makeText(requireContext(), "Failed to fetch guests from API", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Guest>>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
