package com.example.spinningwheel.presentation.screens.spinningwheel.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.example.spinningwheel.R
import com.example.spinningwheel.core.presentation.util.drawArrowPath
import com.example.spinningwheel.core.util.Constants
import com.example.spinningwheel.core.util.Constants.MIN_WHEEL_ENTRIES
import com.example.spinningwheel.core.util.toRad
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun SpinningWheel(
    wheelBoxSize: Dp = 400.dp,
    items: List<String>,
    isSpinning: Boolean,
    onWheelClick: () -> Unit,
    onAnimationFinish: (String?) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (items.size >= MIN_WHEEL_ENTRIES) {
            SpinningWheelContent(
                items = items,
                isSpinning = isSpinning,
                onWheelClick = onWheelClick,
                onAnimationFinish = onAnimationFinish
            )
        } else {
            Canvas(
                modifier = Modifier
                    .size(wheelBoxSize)
            ) {
                drawCircle(color = Color.LightGray)
            }

            Text(
                text = stringResource(
                    R.string.entries_remaining,
                    MIN_WHEEL_ENTRIES - items.size
                ),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
fun BoxScope.SpinningWheelContent(
    wheelBoxSize: Dp = 400.dp,
    items: List<String>,
    isSpinning: Boolean,
    onWheelClick: () -> Unit,
    onAnimationFinish: (String?) -> Unit
) {
    val wheelDivisions = items.size

    val sweepAngleSize by remember(wheelDivisions) {
        mutableFloatStateOf(360f / wheelDivisions)
    }

    val sweepAngleSizeRad by remember(wheelDivisions) { mutableDoubleStateOf(2 * PI / wheelDivisions) }

    var lastAnglePosition by remember {
        mutableFloatStateOf(0f)
    }

    val spinTheWheelRotation by animateFloatAsState(
        targetValue = if (isSpinning) -5000f * Random.nextFloat() else 0f,
        animationSpec = tween(1500),
        label = "",
        finishedListener = {
            if (isSpinning) {
                val divisionIndex = getDivisionIndex(
                    endRotationValue = it,
                    sweepAngleSize = sweepAngleSize,
                    wheelDivisions = wheelDivisions
                )
                onAnimationFinish(items.getOrNull(divisionIndex))

                lastAnglePosition = it
            }
        }
    )

    Box(
        modifier = Modifier
            .size(wheelBoxSize)
            .clip(CircleShape)
            .graphicsLayer {
                rotationZ = if (!isSpinning) {
                    lastAnglePosition
                } else spinTheWheelRotation
            },
        contentAlignment = Alignment.Center
    ) {

        val spinningWheelColors by rememberSpinningWheelColors(wheelDivisions = wheelDivisions)

        var wheelSize by remember {
            mutableStateOf(Size.Zero)
        }

        val sizeWidth by remember(wheelSize) {
            mutableFloatStateOf(wheelSize.width / 2)
        }

        val sizeHeight by remember(wheelSize) {
            mutableFloatStateOf(wheelSize.height / 2)
        }

        val radius by remember(wheelSize) {
            mutableFloatStateOf(wheelSize.width / 2)
        }

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged {
                    wheelSize = it.toSize()
                }
                .clip(CircleShape)
                .clickable {
                    lastAnglePosition = 0f
                    onWheelClick()
                }
        )
        {
            repeat(wheelDivisions) {
                drawArc(
                    color = spinningWheelColors.getOrNull(it) ?: Color.Black,
                    startAngle = it * sweepAngleSize,
                    sweepAngle = sweepAngleSize,
                    useCenter = true,
                    size = wheelSize
                )

                val angle = (it * sweepAngleSizeRad).toFloat()
                val x = sizeWidth + radius * cos(angle)
                val y = sizeHeight + radius * sin(angle)
                drawLine(
                    color = Color.Black,
                    start = Offset(sizeWidth, sizeHeight),
                    end = Offset(x, y),
                    strokeWidth = 4f
                )
            }
        }

        repeat(wheelDivisions) {
            val startAngle =
                it * sweepAngleSize // Počateční uhel textu, první text bude mít 0 stupnu + text Angle, další pak sweep + text Angle ,...
            val textAngle =
                startAngle + sweepAngleSize / 2 // Uhel textu by měl být polovina úhlu arcu
            val textX =
                sizeWidth / Constants.INNER_RADIUS_COEFFICIENT * cos(textAngle.toRad()) // sizeWidth / koeficient je vlastně poloměr "vnitřního kruhu" kola štěstí, poloha X a Y je pak spočítána pomocí vzorců  x = r(cos(degree)), y = r(sin(degrees))
            val textY =
                sizeHeight / Constants.INNER_RADIUS_COEFFICIENT * sin(textAngle.toRad())
            Text(
                modifier = Modifier
                    .offset(
                        x = with(LocalDensity.current) {
                            textX.toDp()
                        },
                        y = with(LocalDensity.current) {
                            textY.toDp()
                        }
                    )
                    .graphicsLayer {
                        rotationZ = textAngle
                    },
                text = items.getOrNull(it).orEmpty(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }

    Canvas(
        modifier = Modifier
            .size(30.dp)
            .align(Alignment.CenterEnd)
    )
    {
        drawPath(
            path = drawArrowPath(this.size),
            color = Color.Red
        )
    }
}

@Composable
fun rememberSpinningWheelColors(wheelDivisions: Int) = remember(wheelDivisions) {
    mutableStateOf(
        List(wheelDivisions) {
            Color(
                Random.nextInt(50, 255),
                Random.nextInt(50, 255),
                Random.nextInt(50, 255),
                alpha = 0x80
            )
        }
    )
}

private fun getDivisionIndex(
    endRotationValue: Float,
    sweepAngleSize: Float,
    wheelDivisions: Int
): Int {
    val angle = abs(endRotationValue)
    val roundsMade = floor(abs(angle) / 360f)
    val remainingAngle = angle - (roundsMade * 360f)
    return floor((remainingAngle / sweepAngleSize) % wheelDivisions).toInt()
}