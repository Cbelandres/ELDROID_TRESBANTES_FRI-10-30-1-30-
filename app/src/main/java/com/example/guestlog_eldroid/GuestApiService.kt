package com.example.guestlog_eldroid

import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.POST
import retrofit2.http.Body

interface GuestApiService {

    // 1. Fetch all guests (GET request)
    @GET("api/guests")
    fun getGuests(): Call<List<Guest>>

    // 2. Add a new guest (POST request)
    @POST("api/guests")
    fun addGuest(@Body guest: Guest): Call<Guest>

    // 3. Fetch a guest by ID (GET request with ID parameter)
    @GET("api/guests/{id}")
    fun getGuestById(@Path("id") id: Int): Call<Guest>

    // 4. Update a guest (PUT request with ID parameter)
    @PUT("api/guests/{id}")
    fun updateGuest(@Path("id") id: String?, @Body guest: Guest): Call<Guest>

    // 5. Delete a guest (DELETE request with ID parameter)
    @DELETE("api/guests/{id}")
    fun deleteGuest(@Path("id") id: String): Call<Void>
}
