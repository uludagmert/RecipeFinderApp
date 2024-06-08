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
import com.example.recipefinderapp.dishes.viewmodel.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val useCase: IGetDetailsUseCase,
    val dao : DishesDao
): ViewModel()  {
    private val _meal : MutableState<MealDetail?> = mutableStateOf(null)
    val meal : State<MealDetail?> = _meal

    fun getDetailsForDishId (id : String){
        viewModelScope.launch {
            try {
                val mealDetailResponse = useCase (id)
                val meal = mealDetailResponse.meals[0]
                _meal.value = meal
            }catch (e: Exception){
                Log.d("DetailsViewModel", "getDetailsForDishes: ${e.message}")
            }
        }
    }
    fun saveToFavourites (mealDetail : MealDetail) {
    viewModelScope.launch {

            dao.saveMeal(mealDetail.convertToSmaller())


    }
    }
}