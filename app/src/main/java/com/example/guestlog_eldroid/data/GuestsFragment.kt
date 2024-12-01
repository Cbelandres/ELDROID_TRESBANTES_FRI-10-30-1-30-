package com.example.guestlog_eldroid.data

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guestlog_eldroid.R
import com.example.guestlog_eldroid.data.models.Guest
import com.example.guestlog_eldroid.data.models.History
import com.example.guestlog_eldroid.ui.GuestsAdapter
import com.example.guestlog_eldroid.viewmodel.GuestViewModel
import com.example.guestlog_eldroid.viewmodel.HistoryViewModel
import java.text.SimpleDateFormat
import java.util.*

class GuestsFragment : Fragment() {

    private lateinit var guestsAdapter: GuestsAdapter
    private val guestViewModel: GuestViewModel by activityViewModels()
    private val historyViewModel: HistoryViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_guests, container, false)

        // Initialize the RecyclerView and Adapter
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvGuestList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        guestsAdapter = GuestsAdapter(
            guests = mutableListOf(),
            onDeleteClick = { guest -> handleDeleteGuest(guest) },
            onEditClick = { guest -> showEditGuestModal(guest) },
            onCheckoutClick = { guest -> handleCheckout(guest) } // Handle checkout
        )
        recyclerView.adapter = guestsAdapter

        // Fetch the email from SharedPreferences and set it in ViewModel
        val sharedPref = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val email = sharedPref.getString("user_email", null)

        if (email != null) {
            guestViewModel.setCurrentUserEmail(email)
        } else {
            Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show()
        }

        // Observe guest list changes and update adapter
        guestViewModel.guestList.observe(viewLifecycleOwner) { guests ->
            if (guests.isEmpty()) {
                Toast.makeText(requireContext(), "No guests found", Toast.LENGTH_SHORT).show()
            } else {
                guestsAdapter.updateGuests(guests)
            }
        }

        // Fetch guests for the current user
        guestViewModel.fetchGuests()

        return view
    }

    private fun handleDeleteGuest(guest: Guest) {
        val id = guest.id
        if (id != null) {
            AlertDialog.Builder(requireContext())
                .setTitle("Delete Guest")
                .setMessage("Are you sure you want to delete ${guest.name}?")
                .setPositiveButton("Yes") { _, _ ->
                    guestViewModel.deleteGuestById(id) { success ->
                        if (success) {
                            Toast.makeText(requireContext(), "Guest deleted successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), "Failed to delete guest", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                .setNegativeButton("No", null)
                .show()
        } else {
            Toast.makeText(requireContext(), "Failed to delete guest: Missing ID", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleCheckout(guest: Guest) {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = Date()

        // Calculate the number of days stayed
        val startDate = dateFormatter.parse(guest.date)
        val daysStayed = ((currentDate.time - startDate.time) / (1000 * 60 * 60 * 24)).toInt().coerceAtLeast(1) // Minimum of 1 day
        val totalPayment = guest.cost * daysStayed

        // Show checkout modal
        AlertDialog.Builder(requireContext())
            .setTitle("Checkout")
            .setMessage("Total Payment: $totalPayment\nDays Stayed: $daysStayed")
            .setPositiveButton("OK") { _, _ ->
                val checkoutDate = dateFormatter.format(currentDate)

                // Prepare data for history collection
                val history = History(
                    name = guest.name,
                    createdBy = guest.createdBy,
                    date = guest.date,
                    checkoutDate = checkoutDate,
                    totalCost = totalPayment
                )

                // Save history and remove guest
                historyViewModel.saveHistory(history) { success ->
                    if (success) {
                        Toast.makeText(requireContext(), "Checkout completed", Toast.LENGTH_SHORT).show()
                        guestViewModel.deleteGuestById(guest.id!!) { success ->
                            if (success) guestViewModel.fetchGuests() // Refresh guest list
                        }
                    } else {
                        Toast.makeText(requireContext(), "Failed to save history", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showEditGuestModal(guest: Guest) {
        val dialogView = layoutInflater.inflate(R.layout.edit_guest_modal, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setCancelable(false)
            .create()

        // Initialize fields with the guest's details
        val etGuestName = dialogView.findViewById<EditText>(R.id.etGuestName)
        val etGuestEmail = dialogView.findViewById<EditText>(R.id.etGuestEmail)
        val etGuestCost = dialogView.findViewById<EditText>(R.id.etGuestCost)
        val etGuestPhone = dialogView.findViewById<EditText>(R.id.etGuestPhone)
        val etGuestAge = dialogView.findViewById<EditText>(R.id.etGuestAge)

        etGuestName.setText(guest.name)
        etGuestEmail.setText(guest.email)
        etGuestCost.setText(guest.cost.toString())
        etGuestPhone.setText(guest.phoneNumber)
        etGuestAge.setText(guest.age.toString())

        // Handle buttons
        dialogView.findViewById<View>(R.id.btnCancelEdit).setOnClickListener {
            dialog.dismiss()
        }

        dialogView.findViewById<View>(R.id.btnEditGuest).setOnClickListener {
            val updatedGuest = guest.copy(
                name = etGuestName.text.toString(),
                email = etGuestEmail.text.toString(),
                cost = etGuestCost.text.toString().toDouble(),
                phoneNumber = etGuestPhone.text.toString(),
                age = etGuestAge.text.toString().toInt()
            )

            guestViewModel.updateGuest(updatedGuest) { success ->
                if (success) {
                    Toast.makeText(requireContext(), "Guest updated successfully", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                } else {
                    Toast.makeText(requireContext(), "Failed to update guest", Toast.LENGTH_SHORT).show()
                }
            }
        }

        dialog.show()
    }
}
