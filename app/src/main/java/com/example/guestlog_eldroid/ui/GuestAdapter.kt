package com.example.guestlog_eldroid.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.guestlog_eldroid.R
import com.example.guestlog_eldroid.data.models.Guest
import com.google.android.material.button.MaterialButton

class GuestsAdapter(
    private var guests: MutableList<Guest>,
    private val onDeleteClick: (Guest) -> Unit, // Callback for delete click
    private val onEditClick: (Guest) -> Unit,   // Callback for edit click
    private val onCheckoutClick: (Guest) -> Unit // Callback for checkout click
) : RecyclerView.Adapter<GuestsAdapter.GuestViewHolder>() {

    class GuestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvGuestName: TextView = itemView.findViewById(R.id.tvGuestName)
        private val tvGuestEmail: TextView = itemView.findViewById(R.id.tvGuestEmail)
        private val tvCost: TextView = itemView.findViewById(R.id.tvCost)
        private val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        private val tvNumber: TextView = itemView.findViewById(R.id.tvNumber)
        private val tvAge: TextView = itemView.findViewById(R.id.tvAge)
        private val btnDeleteGuest: MaterialButton = itemView.findViewById(R.id.btnDeleteGuest)
        private val btnEditGuest: MaterialButton = itemView.findViewById(R.id.btnEditGuest)
        private val btnCheckout: MaterialButton = itemView.findViewById(R.id.btnCheckout)

        fun bind(
            guest: Guest,
            onDeleteClick: (Guest) -> Unit,
            onEditClick: (Guest) -> Unit,
            onCheckoutClick: (Guest) -> Unit
        ) {
            // Set guest details
            tvGuestName.text = guest.name
            tvGuestEmail.text = guest.email
            tvCost.text = guest.cost.toString()
            tvDate.text = guest.date
            tvNumber.text = guest.phoneNumber
            tvAge.text = guest.age.toString()

            // Set delete button click listener
            btnDeleteGuest.setOnClickListener {
                onDeleteClick(guest) // Pass entire guest object, including id
            }

            // Set edit button click listener
            btnEditGuest.setOnClickListener {
                onEditClick(guest) // Trigger edit callback
            }
            // Set checkout button click listener
            btnCheckout.setOnClickListener {
                onCheckoutClick(guest) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_guest, parent, false)
        return GuestViewHolder(view)
    }

    override fun onBindViewHolder(holder: GuestViewHolder, position: Int) {
        holder.bind(guests[position], onDeleteClick, onEditClick, onCheckoutClick)
    }

    override fun getItemCount(): Int = guests.size

    fun updateGuests(newGuests: List<Guest>) {
        guests.clear()
        guests.addAll(newGuests)
        notifyDataSetChanged()
    }
}
