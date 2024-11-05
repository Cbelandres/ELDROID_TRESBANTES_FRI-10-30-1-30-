package com.example.guestlogapp

import java.io.Serializable

data class Guest(
    val name: String,
    val email: String?,
    val timeIn: String,
    val date: String,
    val number: String,
    val age: String
) : Serializable {
    val contact: String
        get() = "Contact: $number"

    val time: Int
        get() = timeIn.substringBefore(":").toInt()
}
