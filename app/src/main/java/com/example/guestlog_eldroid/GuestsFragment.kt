package com.example.guestlog_eldroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GuestsFragment : Fragment() {

    private lateinit var guestsAdapter: GuestsAdapter

    // Access shared ViewModel
    private val guestViewModel: GuestViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_guests, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvGuestList)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        // Initialize the adapter with an empty list
        guestsAdapter = GuestsAdapter(mutableListOf())
        recyclerView.adapter = guestsAdapter

        // Observe guest list changes and update the RecyclerView
        guestViewModel.guestList.observe(viewLifecycleOwner) { guests ->
            guestsAdapter = GuestsAdapter(guests)
            recyclerView.adapter = guestsAdapter
        }

        return view
    }
}