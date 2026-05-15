package com.nammahomestay.app.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nammahomestay.app.ui.theme.*

@Composable
fun ChecklistItem(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth().padding(vertical = 6.dp)
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = LeafAccent,
                uncheckedColor = DividerThin,
                checkmarkColor = IvoryWhite
            )
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = label, 
            style = MaterialTheme.typography.bodyMedium,
            color = if (checked) DeepEmerald else SlateGray
        )
    }
}
