package com.example.recipefinderapp.category.service

import com.example.recipefinderapp.category.model.CategoryResponse
import retrofit2.http.GET

interface ICategoryService {
    @GET("categories.php")
    suspend fun getAllCategories () : CategoryResponse
}



