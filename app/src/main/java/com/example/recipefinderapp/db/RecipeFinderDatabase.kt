package com.example.recipefinderapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recipefinderapp.category.model.Category
import com.example.recipefinderapp.detail.model.SmallerMeal
import com.example.recipefinderapp.dishes.model.Meal

@Database(
    entities = [SmallerMeal::class, Category::class, Meal::class],
    version = 1,
    exportSchema = false
)
abstract  class RecipeFinderDatabase : RoomDatabase() {
    abstract fun provideDao () : DishesDao
}