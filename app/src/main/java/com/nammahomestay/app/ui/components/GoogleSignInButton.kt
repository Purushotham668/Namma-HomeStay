package com.nammahomestay.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nammahomestay.app.ui.theme.DeepEmerald
import com.nammahomestay.app.ui.theme.IvoryWhite
import com.nammahomestay.app.ui.theme.SlateGray

@Composable
fun GoogleSignInButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = "Continue with Google",
    isLoading: Boolean = false
) {
    NammaCard(
        modifier = modifier.height(56.dp),
        onClick = if (!isLoading) onClick else null,
        elevation = 2.dp,
        containerColor = IvoryWhite
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = DeepEmerald,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    "G", 
                    style = MaterialTheme.typography.titleLarge, 
                    fontWeight = FontWeight.Bold,
                    color = DeepEmerald,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = text,
                    style = MaterialTheme.typography.labelLarge,
                    color = SlateGray,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
