package com.example.recipefinderapp.dishes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipefinderapp.category.SingleItem
import com.example.recipefinderapp.dishes.model.DishesResponse
import com.example.recipefinderapp.dishes.model.Meal
import com.example.recipefinderapp.dishes.viewmodel.DishesViewModel
import com.example.recipefinderapp.dishes.viewmodel.ViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DishesScreen(
    viewModel: DishesViewModel = hiltViewModel(),
    category: String?,
    onDishClick: (String) -> Unit
) {
    DisposableEffect(Unit) {
        if (category != null) {
            viewModel.getDishesForCategory(category)
        }
        onDispose { }
    }
    val viewState by remember { viewModel.viewState }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(category ?: "Dishes") },

            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (val state = viewState) {
                is ViewState.Success -> {
                    DishesList(state.data, onDishClick)
                }
                is ViewState.Error -> {
                    Text(
                        text = "Error: ${state.message}",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                else -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
fun DishesList(dishes: DishesResponse, onDishClick: (String) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(dishes.meals) { item ->
            SingleItem(item.strMeal, item.strMealThumb) {
                onDishClick(item.idMeal)
            }
        }
    }
}
