package com.nammahomestay.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nammahomestay.app.ui.theme.*

@Composable
fun NammaButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    enabled: Boolean = true,
    containerColor: Color? = null,
    contentColor: Color? = null
) {
    val gradient = Brush.linearGradient(listOf(DeepEmerald, EmeraldMedium))
    val useGradient = containerColor == null

    Button(
        onClick = onClick,
        enabled = enabled && !isLoading,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(28.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor ?: Color.Transparent,
            contentColor = contentColor ?: IvoryWhite
        ),
        contentPadding = PaddingValues(0.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp,
            disabledElevation = 0.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(
                    brush = if (useGradient) {
                        if (enabled && !isLoading) gradient
                        else Brush.linearGradient(listOf(MutedText, MutedText))
                    } else Brush.linearGradient(listOf(Color.Transparent, Color.Transparent)),
                    shape = RoundedCornerShape(28.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = contentColor ?: IvoryWhite,
                    strokeWidth = 3.dp,
                    modifier = Modifier.height(24.dp)
                )
            } else {
                Text(
                    text = text.uppercase(),
                    style = MaterialTheme.typography.labelLarge,
                    color = contentColor ?: IvoryWhite,
                    letterSpacing = 1.2.sp
                )
            }
        }
    }
}

@Composable
fun NammaOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    OutlinedButton(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(28.dp),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, DeepEmerald),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = DeepEmerald
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = DeepEmerald
        )
    }
}
