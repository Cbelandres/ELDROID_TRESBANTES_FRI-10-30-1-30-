package com.example.guestlog_eldroid.data.models
import com.google.gson.annotations.SerializedName

data class Guest(
    val id: String? = null,
    val name: String,
    val email: String,
    val cost: Double,
    val date: String,
    val phoneNumber: String,
    val age: Int,
    val createdBy: String
)

