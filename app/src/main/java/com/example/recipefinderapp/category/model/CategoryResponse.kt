package com.example.recipefinderapp.category.model

import androidx.room.TypeConverter


data class CategoryResponse(
    val categories: List<Category>
)

class CategoryConverter {
    @TypeConverter
    fun listOfCategoryToString(list: List<Category>) : String {
        list.map {
            "${it.idCategory}---${it.strCategory}---${it.strCategoryDescription}---${it.strCategoryThumb}"
        }
        return ""
    }
}