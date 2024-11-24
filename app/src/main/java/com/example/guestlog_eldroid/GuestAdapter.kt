package com.example.guestlog_eldroid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GuestsAdapter(
    private val guests: MutableList<Guest>,
    private val context: Context,
    private val dbHelper: DatabaseHelper
) : RecyclerView.Adapter<GuestsAdapter.GuestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_guest, parent, false)
        return GuestViewHolder(view)
    }

    override fun onBindViewHolder(holder: GuestViewHolder, position: Int) {
        val guest = guests[position]
        holder.nameTextView.text = guest.name
        holder.emailTextView.text = guest.email
        holder.timeInTextView.text = guest.timeIn
        holder.dateTextView.text = guest.date
        holder.phoneTextView.text = guest.phoneNumber ?: "N/A"
        holder.ageTextView.text = guest.age

        holder.deleteButton.setOnClickListener {
            deleteGuest(guest, position)
        }
    }


    override fun getItemCount(): Int = guests.size

    class GuestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.tvGuestName) // Matches XML
        val emailTextView: TextView = view.findViewById(R.id.tvGuestEmail) // Matches XML
        val phoneTextView: TextView = view.findViewById(R.id.tvNumber) // Matches XML
        val timeInTextView: TextView = view.findViewById(R.id.tvTimeIn) // Correct ID
        val dateTextView: TextView = view.findViewById(R.id.tvDate) // Correct ID
        val ageTextView: TextView = view.findViewById(R.id.tvAge) // Matches XML
        val deleteButton: Button = view.findViewById(R.id.btnDeleteGuest) // Matches XML
    }
    fun updateGuests(newGuests: List<Guest>) {
        guests.clear() // Clear the current list
        guests.addAll(newGuests) // Add all new guests
        notifyDataSetChanged() // Notify RecyclerView to refresh
    }
    private fun deleteGuest(guest: Guest, position: Int) {
        ApiClient.guestApiService.deleteGuest(guest.id ?: "").enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // Delete from SQLite
                    dbHelper.deleteGuest(guest.id ?: "")

                    // Remove from RecyclerView
                    guests.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, guests.size)

                    Toast.makeText(context, "Guest deleted successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Failed to delete guest from API", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
