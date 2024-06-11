package com.example.recipefinderapp.favorites

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.recipefinderapp.R
import com.example.recipefinderapp.detail.model.SmallerMeal
import kotlin.math.max
import kotlin.math.min

fun Modifier.`if`(
    condition: Boolean,
    then: Modifier.() -> Modifier
): Modifier = if (condition) {
    then()
} else {
    this
}

@Composable
fun InstructionText(instructions: String) {
    var showMore by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .`if`(!showMore) { height(100.dp) }
                .padding(bottom = 8.dp)
        ) {
            Text(instructions)
        }
        Button(
            onClick = { showMore = !showMore },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(if (showMore) "Show less" else "Show more")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FavoriteDetailScreen(
    navController: NavController,
    viewModel: FavoriteDetailViewModel = hiltViewModel(),
    mealId: String?
) {
    DisposableEffect(Unit) {
        if (mealId != null) {
            viewModel.getFavoriteMealById(mealId)
        }
        onDispose { }
    }

    val singleDish by remember { viewModel.favorite }

    val scrollState = rememberLazyListState()

    singleDish?.let {

        Box {
            Content(it, scrollState)
            ParallaxToolbar(it, scrollState, viewModel, navController)
        }

    }
}

@Composable
fun ParallaxToolbar(smallerMeal: SmallerMeal, scrollState: LazyListState, viewModel: FavoriteDetailViewModel, navController: NavController) {
    val imageHeight = 344.dp
    val maxOffset = with(LocalDensity.current) { imageHeight.roundToPx() }
    val offset = min(scrollState.firstVisibleItemScrollOffset, maxOffset)
    val offsetProgress = max(0f, offset * 3f - 2f * maxOffset) / maxOffset

    var isFavorited by remember { mutableStateOf(false) }
    val favoriteIcon = if (isFavorited) R.drawable.ic_favorite else R.drawable.ic_favorite_unfilled

    Box(
        modifier = Modifier
            .height(400.dp)
            .offset { IntOffset(x = 0, y = -offset) }
            .background(Color.White)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .height(imageHeight)
                    .graphicsLayer { alpha = 1f - offsetProgress }
            ) {
                Image(
                    painter = rememberAsyncImagePainter(smallerMeal.strMealThumb),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = smallerMeal.title,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = (16 + 28 * offsetProgress).dp)
                        .scale(1f - 0.25f * offsetProgress)
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(56.dp)
                .padding(horizontal = 16.dp)
        ) {
            CircularButton(R.drawable.ic_arrow_back) {
                navController.popBackStack()
            }
            CircularButton(
                iconResource = favoriteIcon,
                onClick = {
                    isFavorited = !isFavorited
                   // viewModel.saveToFavourites(meal)
                }
            )
        }
    }
}

@Composable
fun Content(meal: SmallerMeal, scrollState: LazyListState) {
    LazyColumn(contentPadding = PaddingValues(top = 400.dp), state = scrollState) {
        item {
            Steps(meal)
        }
    }
}

@Composable
fun Steps(meal: SmallerMeal) {
    val uriHandler = LocalUriHandler.current
    val annotatedString = buildAnnotatedString {
        append(meal.strYoutube)
        addStyle(
            style = SpanStyle(
                color = Color.Blue,
                textDecoration = TextDecoration.Underline
            ),
            start = 0,
            end = meal.strYoutube.length
        )
    }
    Column(
        modifier = Modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        ClickableText(
            text = annotatedString,
            onClick = { _ -> uriHandler.openUri(meal.strYoutube) }
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Text(
                text = "Steps",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            InstructionText(meal.strInstructions)
        }
    }
}

@Composable
fun CircularButton(
    @DrawableRes iconResource: Int,
    color: Color = Color.White,
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        contentPadding = PaddingValues(),
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(contentColor = Color.Red, containerColor = color),
        elevation = elevation,
        modifier = Modifier
            .width(38.dp)
            .height(38.dp)
    ) {
        Icon(painterResource(id = iconResource), contentDescription = null)
    }
}
