package com.example.aviatickets.model.network

import android.telecom.Call
import com.example.aviatickets.model.entity.Flight
import com.example.aviatickets.model.entity.Offer
import retrofit2.http.GET

interface ApiService {
    @GET("offer_list")
    suspend fun getOffers(): List<Offer>
}