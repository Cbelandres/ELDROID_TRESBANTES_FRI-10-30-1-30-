package com.example.guestlog_eldroid.data

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guestlog_eldroid.R
import com.example.guestlog_eldroid.ui.HistoryAdapter
import com.example.guestlog_eldroid.viewmodel.HistoryViewModel

class HistoryFragment : Fragment() {

    private val historyViewModel: HistoryViewModel by activityViewModels()
    private lateinit var historyAdapter: HistoryAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvHistory)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        historyAdapter = HistoryAdapter(emptyList())
        recyclerView.adapter = historyAdapter

        val sharedPref = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val email = sharedPref.getString("user_email", null)

        if (email != null) {
            historyViewModel.setCurrentUserEmail(email)
        } else {
            Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show()
        }

        historyViewModel.historyList.observe(viewLifecycleOwner) { historyList ->
            if (historyList.isEmpty()) {
                Toast.makeText(requireContext(), "No history records found", Toast.LENGTH_SHORT).show()
            } else {
                historyAdapter.updateHistory(historyList)
                println("Observed history list: $historyList") // Debugging
            }
        }

        return view
    }
}

