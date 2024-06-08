package com.example.recipefinderapp.category.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("categories")
data class Category(
    @PrimaryKey
    val idCategory: String,
    val strCategory: String,
    val strCategoryDescription: String,
    val strCategoryThumb: String
)