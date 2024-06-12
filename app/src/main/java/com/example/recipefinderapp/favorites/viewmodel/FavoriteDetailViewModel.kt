package com.example.recipefinderapp.favorites.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefinderapp.detail.model.SmallerMeal
import com.example.recipefinderapp.favorites.repository.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteDetailViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _favorite : MutableState<SmallerMeal?> = mutableStateOf(null)
    val favorite: State<SmallerMeal?> = _favorite

    fun getFavoriteMealById(id: String) {
        viewModelScope.launch {
            val favoriteMeal = favoritesRepository.getMealById(id)
            _favorite.value = favoriteMeal
        }
    }
    fun removeFromFavorites(id: String) {
        viewModelScope.launch {
            favoritesRepository.removeMealFromFavorites(id)
        }
    }
}
