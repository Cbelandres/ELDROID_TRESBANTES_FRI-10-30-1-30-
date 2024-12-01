package com.example.guestlog_eldroid.data.models

data class History(
    val name: String,
    val createdBy: String,
    val date: String,
    val checkoutDate: String,
    val totalCost: Double
)
