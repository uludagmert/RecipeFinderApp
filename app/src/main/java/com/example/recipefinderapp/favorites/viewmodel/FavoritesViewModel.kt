package com.example.recipefinderapp.favorites.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipefinderapp.detail.model.SmallerMeal
import com.example.recipefinderapp.favorites.repository.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _favorites = MutableStateFlow<List<SmallerMeal>>(emptyList())
    val favorites: StateFlow<List<SmallerMeal>> = _favorites

    fun loadFavorites() {
        viewModelScope.launch {
            _favorites.value = favoritesRepository.getFavoriteMeals()
        }
    }

    fun removeFavorite(id: String) {
        viewModelScope.launch {
            favoritesRepository.removeMealFromFavorites(id)
            _favorites.value = favoritesRepository.getFavoriteMeals()
        }
    }
}
