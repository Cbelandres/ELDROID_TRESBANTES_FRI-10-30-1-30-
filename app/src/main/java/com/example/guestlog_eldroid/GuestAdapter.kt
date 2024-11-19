package com.example.guestlog_eldroid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GuestsAdapter(private val guests: List<Guest>) : RecyclerView.Adapter<GuestsAdapter.GuestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_guest, parent, false)
        return GuestViewHolder(view)
    }

    override fun onBindViewHolder(holder: GuestViewHolder, position: Int) {
        val guest = guests[position]
        holder.nameTextView.text = guest.name
        holder.emailTextView.text = guest.email
        holder.timeInTextView.text = guest.timeIn
        holder.dateTextView.text = guest.date
        holder.phoneTextView.text = guest.phoneNumber
        holder.ageTextView.text = guest.age
    }

    override fun getItemCount() = guests.size

    class GuestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.tvGuestName)
        val emailTextView: TextView = view.findViewById(R.id.tvGuestEmail)
        val timeInTextView: TextView = view.findViewById(R.id.tvTimeIn)
        val dateTextView: TextView = view.findViewById(R.id.tvDate)
        val phoneTextView: TextView = view.findViewById(R.id.tvNumber)
        val ageTextView: TextView = view.findViewById(R.id.tvAge)
    }
}