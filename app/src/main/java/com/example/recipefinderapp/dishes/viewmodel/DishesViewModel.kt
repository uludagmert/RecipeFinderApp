package com.example.recipefinderapp.dishes.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefinderapp.dishes.model.DishesResponse
import com.example.recipefinderapp.dishes.model.Meal
import com.example.recipefinderapp.dishes.usecase.IGetDishesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ViewState {
    object Loading : ViewState()
    data class Success(val data: DishesResponse) : ViewState()
    data class Error(val message: String) : ViewState()
}

@HiltViewModel
class DishesViewModel @Inject constructor(
    val useCase : IGetDishesUseCase
): ViewModel() {

    private val _viewState : MutableState<ViewState> = mutableStateOf(ViewState.Loading)
    val viewState : State<ViewState> = _viewState

    fun getDishesForCategory(categoryName: String) {
        _viewState.value = ViewState.Loading
        viewModelScope.launch {
            try {
                val listDishes = useCase(categoryName)
                _viewState.value = ViewState.Success(listDishes)
            }
            catch (e: Exception) {
                Log.d("DishesViewModel", "getDishesForCategory: ${e.message}")
                _viewState.value = ViewState.Error(e.message.toString())
            }

        }
    }
}