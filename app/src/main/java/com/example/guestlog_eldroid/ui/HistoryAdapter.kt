package com.example.guestlog_eldroid.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.guestlog_eldroid.R
import com.example.guestlog_eldroid.data.models.History

class HistoryAdapter(private var historyList: List<History>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: TextView = itemView.findViewById(R.id.tvHistoryName)
        private val tvDate: TextView = itemView.findViewById(R.id.tvHistoryDate)
        private val tvCheckoutDate: TextView = itemView.findViewById(R.id.tvCheckoutDate)
        private val tvTotalCost: TextView = itemView.findViewById(R.id.tvTotalCost)

        fun bind(history: History) {
            tvName.text = history.name
            tvDate.text = "Date: ${history.date}"
            tvCheckoutDate.text = "Checked Out: ${history.checkoutDate}"
            tvTotalCost.text = "Total Cost: $${history.totalCost}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_item, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = historyList[position]
        holder.bind(history)
    }

    override fun getItemCount(): Int = historyList.size

    fun updateHistory(newHistoryList: List<History>) {
        historyList = newHistoryList
        println("Updating history list in adapter: $historyList") // Debugging
        notifyDataSetChanged()
    }
}
