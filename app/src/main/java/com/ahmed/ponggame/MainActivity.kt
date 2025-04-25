package com.ahmed.ponggame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmed.ponggame.ui.theme.PaddleColor
import com.ahmed.ponggame.ui.theme.PongGameTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PongGameTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    PongGame()

                }
            }
        }
    }
}

@Composable
fun PongGame() {

    val infiniteTransition = rememberInfiniteTransition()
    val buttonColor by infiniteTransition.animateColor(
        initialValue = Color.Yellow,
        targetValue = Color.Blue,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    var xVelocity by remember { mutableFloatStateOf(16f) }
    var yVelocity by remember { mutableFloatStateOf(10f) }


    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val imageSize = 100.dp

    val imageOffsetX = remember { Animatable(0f) }
    val imageOffsetY = remember { Animatable(0f) }

    val density = LocalDensity.current
    val maxOffsetXPx = with(density) { (screenWidth - imageSize).toPx() }
//    val maxOffsetYPx = with(density) { (screenHeight - imageSize).toPx() }

    val paddleWidth = 100.dp
    val paddleHeight = 20.dp

    var paddleOffsetX by remember { mutableFloatStateOf(0f) }
    var isPlaying by remember { mutableStateOf(true) }

    var score by remember { mutableIntStateOf(0) }

    // loop
    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            imageOffsetX.snapTo(0f)
            imageOffsetY.snapTo(0f)
        }

        while (isPlaying) {
            imageOffsetX.snapTo(imageOffsetX.value + xVelocity)
            imageOffsetY.snapTo(imageOffsetY.value + yVelocity)

            // Reflect from screen edges
            if (imageOffsetX.value <= 0 || imageOffsetX.value >= maxOffsetXPx) {
                xVelocity = -xVelocity
            }

            if (imageOffsetY.value <= 0) {
                yVelocity = -yVelocity
            }

            // Check collision with paddle
            val paddleTop = with(density) { screenHeight.toPx() - paddleHeight.toPx() }
            val paddleBottom = paddleTop + with(density) { paddleHeight.toPx() }
            val paddleRight = paddleOffsetX + with(density) { paddleWidth.toPx() }
            val paddleLeft = paddleOffsetX

            val ballBottom = imageOffsetY.value + with(density) { imageSize.toPx() }
            val ballCenterX = imageOffsetX.value + with(density) { imageSize.toPx() / 2 }

            if (ballBottom >= paddleTop && ballCenterX in paddleLeft..paddleRight) {
                score += 10
                yVelocity = -yVelocity

//                val newXVelocity = Random.nextFloat() * 12f + 4f // range: 4 to 16
                xVelocity += 4
            }

            if (ballBottom > paddleBottom + 60) {
                isPlaying = false
            }

            delay(16)
        }
    }

    fun resetGame() {
        score = 0
        isPlaying = true
        xVelocity = 10f
        yVelocity = 10f

    }

    Box(modifier = Modifier.fillMaxSize()) {

        // Background
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.background_image),
            contentScale = ContentScale.FillBounds,
            contentDescription = null
        )

        // ufo image
        Image(
            painter = painterResource(R.drawable.ufo),
            contentDescription = "UFO",
            modifier = Modifier
                .size(imageSize)
                .offset { IntOffset(imageOffsetX.value.toInt(), imageOffsetY.value.toInt()) }
        )

        // Paddle
        Box(
            modifier = Modifier
                .offset {
                    val y = screenHeight - paddleHeight
                    IntOffset(paddleOffsetX.toInt(), with(density) { y.toPx().toInt() })
                }
                .clip(CircleShape)
                .size(paddleWidth, paddleHeight)
                .background(PaddleColor)
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        val newOffset = (paddleOffsetX + delta).coerceIn(
                            0f, with(density) { screenWidth.toPx() - paddleWidth.toPx() }
                        )
                        paddleOffsetX = newOffset
                    }
                )
        )
        if (!isPlaying) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f))
            )
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Game Over",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineLarge.copy(color = Color.White)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                    onClick = {
                        resetGame()
                    }) {
                    Text(text = "Restart Game", color = Color.Black, fontSize = 18.sp)
                }
            }
        }

        // score ( each hit == 10 points)
        Text(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(12.dp),
            text = "Score: $score",
            style = MaterialTheme.typography.headlineMedium.copy(color = Color.White)
        )
    }
}





























