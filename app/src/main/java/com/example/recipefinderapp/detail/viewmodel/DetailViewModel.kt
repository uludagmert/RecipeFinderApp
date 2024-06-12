package com.example.recipefinderapp.detail.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefinderapp.db.DishesDao
import com.example.recipefinderapp.detail.model.MealDetail
import com.example.recipefinderapp.detail.model.convertToSmaller
import com.example.recipefinderapp.detail.usecase.IGetDetailsUseCase
import com.example.recipefinderapp.favorites.repository.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val useCase: IGetDetailsUseCase,
    val dao: DishesDao
) : ViewModel() {
    private val _meal: MutableState<MealDetail?> = mutableStateOf(null)
    val meal: State<MealDetail?> = _meal

    private val _isFavorited: MutableState<Boolean> = mutableStateOf(false)
    val isFavorited: State<Boolean> = _isFavorited

    fun getDetailsForDishId(id: String) {
        viewModelScope.launch {
            try {
                val mealDetailResponse = useCase(id)
                val meal = mealDetailResponse.meals[0]
                _meal.value = meal

                // Check if the meal is already in favorites
                _isFavorited.value = dao.isFavorite(meal.idMeal)
            } catch (e: Exception) {
                Log.d("DetailsViewModel", "getDetailsForDishes: ${e.message}")
            }
        }
    }

    fun saveToFavourites(mealDetail: MealDetail) {
        viewModelScope.launch {
            if (_isFavorited.value) {
                dao.deleteById(mealDetail.idMeal)
            } else {
                dao.saveMeal(mealDetail.convertToSmaller())
            }
            _isFavorited.value = !_isFavorited.value
        }
    }
}
