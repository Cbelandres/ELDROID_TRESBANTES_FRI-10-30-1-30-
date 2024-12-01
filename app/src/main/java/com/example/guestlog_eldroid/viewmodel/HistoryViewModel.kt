package com.example.guestlog_eldroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.guestlog_eldroid.data.ApiResponse
import com.example.guestlog_eldroid.data.ApiService
import com.example.guestlog_eldroid.data.models.History
import com.example.guestlog_eldroid.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryViewModel : ViewModel() {

    // Retrofit API Service
    private val apiService: ApiService = RetrofitClient.instance.create(ApiService::class.java)

    // LiveData to hold the fetched history list
    private val _historyList = MutableLiveData<List<History>>()
    val historyList: LiveData<List<History>> get() = _historyList

    // LiveData to hold the current user's email
    private val _currentUserEmail = MutableLiveData<String?>()
    val currentUserEmail: LiveData<String?> get() = _currentUserEmail

    /**
     * Sets the current user's email and fetches the history for that email.
     * @param email The email of the current user.
     */
    fun setCurrentUserEmail(email: String) {
        _currentUserEmail.value = email // Save email to LiveData
        fetchHistory(email) // Trigger fetching history immediately
    }

    /**
     * Fetches the history from the backend using the user's email.
     * @param email The email to fetch history for.
     */
    private fun fetchHistory(email: String) {
        println("Fetching histories for: $email") // Debugging

        // Make a network call to fetch history
        apiService.getHistory(email).enqueue(object : Callback<List<History>> {
            override fun onResponse(call: Call<List<History>>, response: Response<List<History>>) {
                if (response.isSuccessful) {
                    // Update the history list LiveData
                    val fetchedHistory = response.body() ?: emptyList()
                    _historyList.postValue(fetchedHistory)
                    println("Fetched histories: $fetchedHistory") // Debugging
                } else {
                    // Handle API error response
                    println("Error fetching histories: ${response.errorBody()?.string()}")
                    _historyList.postValue(emptyList())
                }
            }

            override fun onFailure(call: Call<List<History>>, t: Throwable) {
                // Handle network or server failure
                println("Error fetching histories: ${t.message}")
                _historyList.postValue(emptyList())
            }
        })
    }

    /**
     * Saves a new history entry to the backend.
     * @param history The history object to save.
     * @param onResult Callback to notify success or failure of the save operation.
     */
    fun saveHistory(history: History, onResult: (Boolean) -> Unit) {
        println("Saving history: $history") // Debugging

        apiService.saveHistory(history).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    onResult(true)
                    println("History saved successfully.") // Debugging
                } else {
                    onResult(false)
                    println("Failed to save history: ${response.errorBody()?.string()}") // Debugging
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                onResult(false)
                println("Error saving history: ${t.message}") // Debugging
            }
        })
    }
}
