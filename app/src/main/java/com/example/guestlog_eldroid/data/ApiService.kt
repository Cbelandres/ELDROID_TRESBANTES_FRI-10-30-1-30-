package com.example.guestlog_eldroid.data

import com.example.guestlog_eldroid.data.models.Guest
import com.example.guestlog_eldroid.data.models.History
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

data class User(val fullName: String?, val userName: String?, val email: String, val password: String, val phone: String)
data class LoginRequest(val email: String, val password: String)
data class ApiResponse(val success: Boolean, val message: String)
data class GuestResponse(val success: Boolean, val guests: List<Guest>)
data class GuestIdResponse(val success: Boolean, val id: String?)
data class HistoryResponse(val success: Boolean, val history: List<History>)


interface ApiService {
    @POST("users/register")
    fun register(@Body user: com.example.guestlog_eldroid.data.User): Call<ApiResponse>

    @POST("users/login")
    fun login(@Body request: com.example.guestlog_eldroid.data.LoginRequest): Call<ApiResponse>

    @POST("guests/guests")
    fun addGuest(@Body guest: Guest): Call<ApiResponse> // Adds a guest

    @GET("guests/guests")
    fun getGuests(@Query("userEmail") userEmail: String): Call<GuestResponse>

    @GET("guests/guests/id")
    fun getGuestId(@Query("email") email: String): Call<GuestIdResponse>

    @DELETE("guests/guests/{id}")
    fun deleteGuestById(@Path("id") id: String): Call<ApiResponse>

    @PUT("guests/guests/{id}")
    fun updateGuest(@Path("id") id: String, @Body guest: Guest): Call<ApiResponse>

    @POST("history/history")
    fun saveHistory(@Body history: History): Call<ApiResponse> // Adds history entry

    @GET("history/history")
    fun getHistory(@Query("createdBy") email: String): Call<List<History>>
}

