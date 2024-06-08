package com.example.recipefinderapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.recipefinderapp.category.CategoryScreen
import com.example.recipefinderapp.detail.DetailScreen
import com.example.recipefinderapp.dishes.DishesScreen
import com.example.recipefinderapp.ui.theme.RecipeFinderAppTheme
import dagger.hilt.android.AndroidEntryPoint

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
        NavHost(navController = navController, startDestination = "category") {
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
        }
    }
}
