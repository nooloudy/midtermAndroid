package com.example.aviatickets.model.network

import android.telecom.Call
import com.example.aviatickets.model.entity.Flight
import com.example.aviatickets.model.entity.Offer
import retrofit2.http.GET

interface ApiService {
    @GET("offers")
    suspend fun getOffers(): List<Offer>
}