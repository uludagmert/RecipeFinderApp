package com.example.recipefinderapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.recipefinderapp.ai.AiScreen
import com.example.recipefinderapp.category.CategoryScreen
import com.example.recipefinderapp.detail.DetailScreen
import com.example.recipefinderapp.dishes.DishesScreen
import com.example.recipefinderapp.favorites.FavoriteDetailScreen
import com.example.recipefinderapp.favorites.FavoritesScreen
import com.example.recipefinderapp.ui.theme.RecipeFinderAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipeFinderAppTheme {
                DisherApp()
            }
        }
    }

    @Composable
    fun DisherApp() {
        val navController = rememberNavController()
        var showSplash by remember { mutableStateOf(true) }
        val uriState = remember { MutableStateFlow("") }

        val imagePicker = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri -> uriState.value = uri.toString() }
        )


        if (showSplash) {
            SplashScreen {
                showSplash = false
            }
        } else {
            Scaffold(
                bottomBar = {
                    BottomAppBar(navController)
                }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = "category",
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable("category") {
                        CategoryScreen { category ->
                            navController.navigate("dishes/${category}")
                        }
                    }

                    composable("dishes/{category}", arguments = listOf(
                        navArgument("category") {
                            type = NavType.StringType
                        }
                    )) {
                        val categoryString = remember {
                            it.arguments?.getString("category")
                        }
                        DishesScreen(category = categoryString) { dishId ->
                            navController.navigate("detail/${dishId}")
                        }
                    }

                    composable("detail/{mealId}", arguments = listOf(
                        navArgument("mealId") {
                            type = NavType.StringType
                        }
                    )) {
                        val mealStringId = remember {
                            it.arguments?.getString("mealId")
                        }
                        DetailScreen(navController = navController, mealId = mealStringId)
                    }

                    composable(route = "favorites") {
                        FavoritesScreen { dishId ->
                            navController.navigate("favorite_detail/${dishId}")
                        }
                    }

                    composable(route = "favorite_detail/{mealId}") {
                        val mealStringId = remember {
                            it.arguments?.getString("mealId")
                        }
                        FavoriteDetailScreen(navController = navController, mealId = mealStringId)
                    }

                    composable(route = "ai") {
                        AiScreen(navController, imagePicker, uriState)
                    }
                }
            }
        }
    }

    @Composable
    fun BottomAppBar(navController: NavController) {
        BottomAppBar {
            IconButton(onClick = { navController.navigate("favorites") }) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorites")
            }
            Spacer(modifier = Modifier.weight(1f, true))
            IconButton(onClick = { navController.navigate("category") }) {
                Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
            }
            Spacer(modifier = Modifier.weight(1f, true))
            IconButton(onClick = { navController.navigate("ai") }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "AI Search")
            }
        }
    }
}
