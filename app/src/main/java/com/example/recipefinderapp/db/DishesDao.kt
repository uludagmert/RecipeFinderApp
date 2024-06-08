package com.example.recipefinderapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipefinderapp.category.model.Category
import com.example.recipefinderapp.category.model.CategoryResponse
import com.example.recipefinderapp.detail.model.SmallerMeal
import com.example.recipefinderapp.dishes.model.DishesResponse
import com.example.recipefinderapp.dishes.model.Meal

@Dao
interface DishesDao {

    @Query("SELECT * FROM meal_details")
    suspend fun getListOfString () : List<SmallerMeal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMeal (meal : SmallerMeal)

    @Query("SELECT * FROM categories")
    suspend fun getAllCategories() : List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCategoryResponse (list : List<Category>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDishesResponse (dishesResponse: List<Meal>)

    @Query("SELECT * FROM dishes WHERE categoryId = :categoryId")
    suspend fun getDishesForCategory (categoryId: String) : List<Meal>

}