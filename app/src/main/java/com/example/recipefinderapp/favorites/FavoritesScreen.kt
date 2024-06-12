package com.example.recipefinderapp.favorites

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipefinderapp.R
import com.example.recipefinderapp.detail.model.SmallerMeal
import com.example.recipefinderapp.favorites.viewmodel.FavoritesViewModel
import com.example.recipefinderapp.ui.theme.MainColor

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel(),
    onDishClick: (String) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.loadFavorites()
    }

    val favorites = viewModel.favorites.collectAsState().value

    Scaffold(
            content = {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(favorites) { meal ->
                    FavoriteMealItem(meal = meal,
                        onItemClick = {
                            onDishClick(it)
                        }, onRemoveClick = {
                            viewModel.removeFavorite(meal.idMeal)
                        })
                }
            }
        }
    )
}

@Composable
fun FavoriteMealItem(
    meal: SmallerMeal,
    onItemClick: (id: String) -> Unit,
    onRemoveClick: () -> Unit
) {
    Card(colors = CardDefaults.cardColors(MainColor),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable
        {
                onItemClick(meal.idMeal)
            }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(meal.title, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))

            }
            IconButton(onClick = onRemoveClick) {
                Icon (
                    painterResource(id = R.drawable.ic_favorite),
                    contentDescription = "Remove from favorites"
                )
            }
        }
    }
}
