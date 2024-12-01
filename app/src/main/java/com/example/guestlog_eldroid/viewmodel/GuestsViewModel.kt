    package com.example.guestlog_eldroid.viewmodel

    import androidx.lifecycle.LiveData
    import androidx.lifecycle.MutableLiveData
    import androidx.lifecycle.ViewModel
    import com.example.guestlog_eldroid.data.ApiResponse
    import com.example.guestlog_eldroid.data.GuestResponse
    import com.example.guestlog_eldroid.data.models.Guest
    import com.example.guestlog_eldroid.data.ApiService
    import com.example.guestlog_eldroid.utils.RetrofitClient
    import retrofit2.Call
    import retrofit2.Callback
    import retrofit2.Response

    class GuestViewModel : ViewModel() {
        private val apiService = RetrofitClient.instance.create(ApiService::class.java)

        private val _guestList = MutableLiveData<List<Guest>>()
        val guestList: LiveData<List<Guest>> get() = _guestList

        private val _currentUserEmail = MutableLiveData<String?>()
        val currentUserEmail: LiveData<String?> get() = _currentUserEmail

        fun setCurrentUserEmail(email: String) {
            _currentUserEmail.value = email
        }

        fun fetchGuests() {
            val email = _currentUserEmail.value
            if (email.isNullOrEmpty()) {
                println("Current user email not set") // Debugging
                _guestList.value = emptyList()
                return
            }
            println("Fetching guests for user: $email")
            apiService.getGuests(email).enqueue(object : Callback<GuestResponse> {
                override fun onResponse(call: Call<GuestResponse>, response: Response<GuestResponse>) {
                    if (response.isSuccessful) {
                        _guestList.value = response.body()?.guests ?: emptyList()
                        println("Guests fetched: ${_guestList.value}")
                    } else {
                        println("Error: ${response.errorBody()?.string()}")
                        _guestList.value = emptyList()
                    }
                }

                override fun onFailure(call: Call<GuestResponse>, t: Throwable) {
                    println("Error fetching guests: ${t.message}")
                    _guestList.value = emptyList()
                }
            })
        }

        fun addGuest(guest: Guest): LiveData<ApiResponse> {
            val result = MutableLiveData<ApiResponse>()

            apiService.addGuest(guest).enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        result.postValue(response.body())
                        fetchGuests() // Optionally refresh the guest list after adding
                    } else {
                        result.postValue(ApiResponse(success = false, message = "Failed to add guest"))
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    result.postValue(ApiResponse(success = false, message = "Network error: ${t.message}"))
                }
            })

            return result
        }


        fun deleteGuestById(id: String, onResult: (Boolean) -> Unit) {
            apiService.deleteGuestById(id).enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        println("Guest deleted successfully")
                        fetchGuests() // Refresh list
                        onResult(true)
                    } else {
                        println("Failed to delete guest: ${response.errorBody()?.string()}")
                        onResult(false)
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    println("Error deleting guest: ${t.message}")
                    onResult(false)
                }
            })
        }
        fun updateGuest(guest: Guest, onResult: (Boolean) -> Unit) {
            if (guest.id.isNullOrEmpty()) {
                println("Error: Guest ID is missing")
                onResult(false)
                return
            }

            apiService.updateGuest(guest.id, guest).enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        fetchGuests() // Refresh the guest list after successful update
                        println("Guest updated successfully: ${guest.id}")
                        onResult(true)
                    } else {
                        println("Failed to update guest: ${response.errorBody()?.string()}")
                        onResult(false)
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    println("Error updating guest: ${t.message}")
                    onResult(false)
                }
            })
        }
    }





