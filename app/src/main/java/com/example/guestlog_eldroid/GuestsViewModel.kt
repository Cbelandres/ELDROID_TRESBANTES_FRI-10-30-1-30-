package com.example.guestlog_eldroid

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GuestViewModel : ViewModel() {

    private val _guestList = MutableLiveData<MutableList<Guest>>(mutableListOf())
    val guestList: LiveData<MutableList<Guest>> get() = _guestList

    // Function to add a guest
    fun addGuest(guest: Guest) {
        _guestList.value?.add(guest)
        _guestList.value = _guestList.value
    }
}

