package com.nammahomestay.app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.nammahomestay.app.ui.theme.*

@Composable
fun NammaTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    errorMessage: String = "",
    singleLine: Boolean = true,
    maxLines: Int = 1,
    minLines: Int = 1,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    onDarkBackground: Boolean = false
) {
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            placeholder = {
                if (placeholder.isNotEmpty())
                    Text(placeholder)
            },
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            isError = isError,
            singleLine = singleLine,
            maxLines = maxLines,
            minLines = minLines,
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation,
            enabled = enabled,
            readOnly = readOnly,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (onDarkBackground) PremiumGold else DeepEmerald,
                unfocusedBorderColor = if (onDarkBackground) IvoryWhite.copy(alpha = 0.5f) else DividerThin,
                errorBorderColor = ErrorDark,
                focusedLabelColor = if (onDarkBackground) PremiumGold else DeepEmerald,
                unfocusedLabelColor = if (onDarkBackground) IvoryWhite.copy(alpha = 0.7f) else SlateGray,
                cursorColor = if (onDarkBackground) PremiumGold else DeepEmerald,
                focusedContainerColor = if (onDarkBackground) IvoryWhite.copy(alpha = 0.1f) else IvoryWhite,
                unfocusedContainerColor = if (onDarkBackground) IvoryWhite.copy(alpha = 0.05f) else SurfaceSoft,
                focusedTextColor = if (onDarkBackground) IvoryWhite else MidnightBlue,
                unfocusedTextColor = if (onDarkBackground) IvoryWhite else MidnightBlue,
                focusedPlaceholderColor = if (onDarkBackground) IvoryWhite.copy(alpha = 0.5f) else MutedText,
                unfocusedPlaceholderColor = if (onDarkBackground) IvoryWhite.copy(alpha = 0.5f) else MutedText
            ),
            modifier = Modifier.fillMaxWidth()
        )
        if (isError && errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodySmall,
                color = ErrorDark
            )
        }
    }
}
