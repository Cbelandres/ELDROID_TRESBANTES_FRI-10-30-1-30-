package com.example.guestlogapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GuestAdapter(
    private val onEditClick: (Guest) -> Unit,
    private val onDeleteClick: (Guest) -> Unit
) : RecyclerView.Adapter<GuestAdapter.GuestViewHolder>() {

    val guestList = mutableListOf<Guest>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_guest, parent, false)
        return GuestViewHolder(view)
    }

    override fun onBindViewHolder(holder: GuestViewHolder, position: Int) {
        val guest = guestList[position]
        holder.bind(guest, onEditClick, onDeleteClick)
    }

    override fun getItemCount(): Int = guestList.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(guests: List<Guest>) {
        guestList.clear()
        guestList.addAll(guests)
        notifyDataSetChanged()
    }

    class GuestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvGuestName: TextView = itemView.findViewById(R.id.tvGuestName)
        private val tvGuestEmail: TextView = itemView.findViewById(R.id.tvGuestEmail)
        private val tvTimeIn: TextView = itemView.findViewById(R.id.tvTimeIn)
        private val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        private val tvNumber: TextView = itemView.findViewById(R.id.tvNumber)
        private val tvAge: TextView = itemView.findViewById(R.id.tvAge)
        private val btnEdit: Button = itemView.findViewById(R.id.btnEdit)
        private val btnDelete: Button = itemView.findViewById(R.id.btnDelete)

        fun bind(guest: Guest, onEditClick: (Guest) -> Unit, onDeleteClick: (Guest) -> Unit) {
            tvGuestName.text = guest.name
            tvGuestEmail.text = guest.email
            tvTimeIn.text = guest.timeIn
            tvDate.text = guest.date
            tvNumber.text = guest.number
            tvAge.text = guest.age

            btnEdit.setOnClickListener { onEditClick(guest) }
            btnDelete.setOnClickListener { onDeleteClick(guest) }
        }
    }
}
