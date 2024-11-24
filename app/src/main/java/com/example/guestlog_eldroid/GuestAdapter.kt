package com.example.guestlog_eldroid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GuestsAdapter(
    private val guests: MutableList<Guest>,
    private val context: Context
) : RecyclerView.Adapter<GuestsAdapter.GuestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_guest, parent, false)
        return GuestViewHolder(view)
    }

    override fun onBindViewHolder(holder: GuestViewHolder, position: Int) {
        val guest = guests[position]
        holder.nameTextView.text = guest.name
        holder.emailTextView.text = guest.email
        holder.phoneTextView.text = guest.phoneNumber ?: "N/A"
        holder.ageTextView.text = guest.age
    }

    override fun getItemCount(): Int = guests.size

    class GuestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.tvGuestName)
        val emailTextView: TextView = view.findViewById(R.id.tvGuestEmail)
        val phoneTextView: TextView = view.findViewById(R.id.tvNumber)
        val ageTextView: TextView = view.findViewById(R.id.tvAge)
    }
    fun updateGuests(newGuests: List<Guest>) {
        guests.clear() // Clear the current list
        guests.addAll(newGuests) // Add all new guests
        notifyDataSetChanged() // Notify RecyclerView to refresh
    }
}
