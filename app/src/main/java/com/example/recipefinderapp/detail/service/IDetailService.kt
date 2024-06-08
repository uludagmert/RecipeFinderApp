package com.example.recipefinderapp.detail.service

import com.example.recipefinderapp.detail.model.DetailResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface IDetailService {
    @GET("lookup.php")
    suspend fun GetDetailsForDish(
    @Query("i") mealId : String
    ):DetailResponse

}