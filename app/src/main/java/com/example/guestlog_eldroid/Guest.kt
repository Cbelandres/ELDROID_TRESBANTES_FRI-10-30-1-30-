package com.example.guestlog_eldroid
import com.google.gson.annotations.SerializedName

data class Guest(
    val id: String? = null,
    @SerializedName("name") var name: String,
    @SerializedName("email") var email: String,
    @SerializedName("time_in") val timeIn: String? = null,
    @SerializedName("date") val date: String? = null,
    @SerializedName("phone_number") var phoneNumber: String? = null,
    @SerializedName("age") var age: String = null.toString()
)

