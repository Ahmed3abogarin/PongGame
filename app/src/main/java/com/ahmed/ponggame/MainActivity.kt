package com.ahmed.ponggame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.ahmed.ponggame.ui.theme.PongGameTheme
import kotlinx.coroutines.delay
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PongGameTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    InfiniteMovingImage()

                }
            }
        }
    }
}

@Composable
fun GameDefault() {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val imageSize = 100.dp

    val imageOffsetX = remember { Animatable(0f) }
    val imageOffsetY = remember { Animatable(0f) } // Starting at a vertical offset of 300.dp

    val maxOffsetXPx = with(LocalDensity.current) {
        (screenWidth - imageSize).toPx()
    }
    val maxOffsetYPx = with(LocalDensity.current) {
        (screenHeight - imageSize).toPx()
    }

    LaunchedEffect(Unit) {
        while (true) {
            // Move right and down
            imageOffsetX.animateTo(
                targetValue = maxOffsetXPx,
                animationSpec = tween(durationMillis = 2000, easing = LinearEasing)
            )
            imageOffsetY.animateTo(
                targetValue = maxOffsetYPx,
                animationSpec = tween(durationMillis = 2000, easing = LinearEasing)
            )

            // Move left and up
            imageOffsetX.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 2000, easing = LinearEasing)
            )
            imageOffsetY.animateTo(
                targetValue = 300f, // Back to initial vertical position
                animationSpec = tween(durationMillis = 2000, easing = LinearEasing)
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.ufo), // Replace with your image
            contentDescription = null,
            modifier = Modifier
                .offset { IntOffset(imageOffsetX.value.toInt(), imageOffsetY.value.toInt()) }
                .size(imageSize)
        )
    }
}


@Composable
fun Games() {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val imageSize = 100.dp

    val imageOffsetX = remember { Animatable(0f) }
    val imageOffsetY = remember { Animatable(0f) }

    val maxOffsetXPx = with(LocalDensity.current) {
        (screenWidth - imageSize).toPx()
    }
    val maxOffsetYPx = with(LocalDensity.current) {
        (screenHeight - imageSize).toPx()
    }

    LaunchedEffect(Unit) {
        while (true) {
            // Move the image until it reaches the max width or max height
            if (imageOffsetX.value >= maxOffsetXPx || imageOffsetY.value >= maxOffsetYPx) {
                // Randomly decide the new position only when boundaries are hit
                val randomX = -imageOffsetX.value// Random X position
                val randomY = -imageOffsetY.value// Random Y position (upper half of the screen)

                // Animate to random position (left and upper)
                imageOffsetX.animateTo(
                    targetValue = randomX,
                    animationSpec = tween(durationMillis = 2000, easing = LinearEasing)
                )
                imageOffsetY.animateTo(
                    targetValue = randomY,
                    animationSpec = tween(durationMillis = 2000, easing = LinearEasing)
                )
            }else{
                imageOffsetX.animateTo(
                    targetValue = maxOffsetXPx,
                    animationSpec = tween(durationMillis = 2000, easing = LinearEasing)
                )
                imageOffsetY.animateTo(
                    targetValue = maxOffsetYPx,
                    animationSpec = tween(durationMillis = 2000, easing = LinearEasing)
                )
            }

            // Continue moving until boundary is hit



            // Optional: Delay between boundary hits to prevent too fast of a loop
            delay(100)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.ufo), // Replace with your image
            contentDescription = null,
            modifier = Modifier
                .offset { IntOffset(imageOffsetX.value.toInt(), imageOffsetY.value.toInt()) }
                .size(imageSize),
            contentScale = ContentScale.Crop
        )
    }
}


@Composable
fun InfiniteMovingImage() {
    var xVelocity by remember { mutableStateOf(16f) }
    var yVelocity by remember { mutableStateOf(10f) }

    // Getting the screen dimensions
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val imageSize = 100.dp // Size of the image

    // Animatable positions for the image
    val imageOffsetX = remember { Animatable(0f) }
    val imageOffsetY = remember { Animatable(0f) }

    // Convert screen size from dp to px for boundary calculations
    val maxOffsetXPx = with(LocalDensity.current) {
        (screenWidth - imageSize).toPx()
    }
    val maxOffsetYPx = with(LocalDensity.current) {
        (screenHeight - imageSize).toPx()
    }

    var isPlaying = true

    // Launch an effect to control continuous movement
    LaunchedEffect(Unit) {
        while (isPlaying) {
            // Update positions based on current velocity
            imageOffsetX.snapTo(imageOffsetX.value + xVelocity) // Update position directly
            imageOffsetY.snapTo(imageOffsetY.value + yVelocity) // Update position directly

            // Check for boundary conditions and reverse the direction when hitting edges
            if (imageOffsetX.value >= maxOffsetXPx || imageOffsetX.value <= 0) {
                xVelocity = -xVelocity

            }
            if (imageOffsetY.value >= maxOffsetYPx || imageOffsetY.value <= 0) {
                yVelocity = -yVelocity
            }


            // Add a small delay to create smooth movement
            delay(16) // Delay for roughly 60 FPS
        }
    }

    Box(
        modifier = Modifier.size(500.dp), // Box size to match the canvas size
        contentAlignment = Alignment.TopStart
    ) {
        // Draw the image at the updated position
        Image(
            painter = painterResource(id = R.drawable.ufo), // Replace with your image resource
            contentDescription = "UFO",
            modifier = Modifier
                .size(imageSize) // Set image size
                .offset { IntOffset(imageOffsetX.value.toInt(), imageOffsetY.value.toInt()) }
        )
    }
}



























