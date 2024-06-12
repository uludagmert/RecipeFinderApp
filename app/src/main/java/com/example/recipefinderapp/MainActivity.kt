package com.example.recipefinderapp

import android.annotation.SuppressLint
import androidx.compose.material3.DropdownMenuItem
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.recipefinderapp.ai.AiScreen
import com.example.recipefinderapp.category.CategoryScreen
import com.example.recipefinderapp.detail.DetailScreen
import com.example.recipefinderapp.dishes.DishesScreen
import com.example.recipefinderapp.favorites.FavoriteDetailScreen
import com.example.recipefinderapp.favorites.FavoritesScreen
import com.example.recipefinderapp.login.LoginScreen
import com.example.recipefinderapp.login.SignUpScreen
import com.example.recipefinderapp.ui.theme.RecipeFinderAppTheme
import com.example.recipefinderapp.ui.theme.SecondaryColor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.concurrent.TimeUnit

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
        scheduleRecipeUpdateWorker()
    }
    private fun scheduleRecipeUpdateWorker() {
        val workRequest = PeriodicWorkRequestBuilder<RecipeUpdateWorker>(1, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(this).enqueue(workRequest)
    }
}
    @Composable
    fun DisherApp() {
        val navController = rememberNavController()
        var showSplash by remember { mutableStateOf(true) }
        val uriState = remember { MutableStateFlow("") }
        var isAuthenticated by remember { mutableStateOf(false) }
        var isNavHostReady by remember { mutableStateOf(false) }

        val imagePicker = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri -> uriState.value = uri.toString() }
        )

        LaunchedEffect(isAuthenticated, isNavHostReady) {
            if (isNavHostReady) {
                navController.navigateToCorrectScreen(isAuthenticated)
            }
        }

        if (showSplash) {
            SplashScreen {
                showSplash = false
            }
        } else {
            Scaffold(
                topBar = {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route
                    if (currentRoute != "login" && currentRoute != "signup") {
                        TopAppBarWithMenu(navController) {
                            isAuthenticated = false
                            navController.navigateToCorrectScreen(isAuthenticated)
                        }
                    }
                },
                bottomBar = {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentRoute = navBackStackEntry?.destination?.route
                    if (currentRoute != "login" && currentRoute != "signup") {
                        BottomAppBar(navController)
                    }
                }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = "login",
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable("login") {
                        LoginScreen(
                            onLoginSuccess = {
                                isAuthenticated = true
                                navController.navigateToCorrectScreen(isAuthenticated)
                            },
                            onSignUpClick = {
                                navController.navigate("signup")
                            }
                        )
                    }
                    composable("signup") {
                        SignUpScreen(
                            onSignUpSuccess = {
                                navController.navigate("login") {
                                    popUpTo("signup") { inclusive = true }
                                }
                            }
                        )
                    }
                    composable("category") {
                        CategoryScreen { category ->
                            navController.navigate("dishes/$category")
                        }
                    }
                    composable(
                        "dishes/{category}",
                        arguments = listOf(navArgument("category") { type = NavType.StringType })
                    ) {
                        val categoryString = it.arguments?.getString("category")
                        DishesScreen(category = categoryString) { dishId ->
                            navController.navigate("detail/$dishId")
                        }
                    }
                    composable(
                        "detail/{mealId}",
                        arguments = listOf(navArgument("mealId") { type = NavType.StringType })
                    ) {
                        val mealStringId = it.arguments?.getString("mealId")
                        DetailScreen(navController = navController, mealId = mealStringId)
                    }
                    composable(route = "favorites") {
                        FavoritesScreen { dishId ->
                            navController.navigate("favorite_detail/$dishId")
                        }
                    }
                    composable(route = "favorite_detail/{mealId}") {
                        val mealStringId = it.arguments?.getString("mealId")
                        FavoriteDetailScreen(navController = navController, mealId = mealStringId)
                    }
                    composable(route = "ai") {
                        AiScreen(navController, imagePicker, uriState)
                    }
                }

                // Mark the NavHost as ready once it's set up
                LaunchedEffect(Unit) {
                    isNavHostReady = true
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TopAppBarWithMenu(navController: NavController, onSignOut: () -> Unit) {
        TopAppBar(
            title = { Text("Recipe Finder App") },
            actions = {
                var showMenu by remember { mutableStateOf(false) }
                IconButton(onClick = { showMenu = !showMenu }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Menu")
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Sign Out") },
                        onClick = {
                            showMenu = false
                            onSignOut()
                            navController.navigateToCorrectScreen(false)
                        }
                    )
                }
            }
        )
    }

    @Composable
    fun BottomAppBar(navController: NavController) {
        BottomAppBar(
            containerColor = Color.White,
            contentColor = SecondaryColor
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButtonWithLabel(
                    icon = Icons.Default.Favorite,
                    label = "Favorites",
                    onClick = { navController.navigate("favorites") }
                )
                IconButtonWithLabel(
                    icon = Icons.Default.Home,
                    label = "Home",
                    onClick = { navController.navigate("category") }
                )
                IconButtonWithLabel(
                    icon = Icons.Default.Create,
                    label = "Gemini",
                    onClick = { navController.navigate("ai") }
                )
            }
        }
    }

    @Composable
    fun IconButtonWithLabel(icon: ImageVector, label: String, onClick: () -> Unit) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.clickable(onClick = onClick)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = SecondaryColor,
                fontSize = 18.sp
            )
        }
    }


fun NavController.navigateToCorrectScreen(isAuthenticated: Boolean) {
    if (isAuthenticated) {
        this.navigate("category") {
            popUpTo(0) { inclusive = true }
        }
    } else {
        this.navigate("login") {
            popUpTo(0) { inclusive = true }
        }
    }
}
