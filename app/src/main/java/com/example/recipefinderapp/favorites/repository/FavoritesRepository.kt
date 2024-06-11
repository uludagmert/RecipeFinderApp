package com.example.recipefinderapp.favorites.repository


import com.example.recipefinderapp.db.DishesDao
import com.example.recipefinderapp.detail.model.SmallerMeal
import javax.inject.Inject

class FavoritesRepository @Inject constructor(
    private val mealDetailDao: DishesDao
) {
    suspend fun addMealToFavorites(mealDetail: SmallerMeal) {
        mealDetailDao.saveMeal(mealDetail)
    }

    suspend fun getFavoriteMeals(): List<SmallerMeal> {
        return mealDetailDao.getListOfString()
    }

    suspend fun getMealById(id: String): SmallerMeal {
        return mealDetailDao.getMealById(id)
    }

    suspend fun removeMealFromFavorites(id: String) {
        mealDetailDao.deleteById(id)
    }
}
