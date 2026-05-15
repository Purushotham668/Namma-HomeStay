package com.nammahomestay.app.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.nammahomestay.app.ui.theme.*

@Composable
fun PhotoPickerGrid(
    label: String,
    existingUrls: List<String> = emptyList(),
    selectedPhotos: List<Uri> = emptyList(),
    onImagesSelected: (List<Uri>) -> Unit,
    onRemoveSelected: (Uri) -> Unit,
    onRemoveExisting: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    maxImages: Int = 10
) {
    val totalCount = existingUrls.size + selectedPhotos.size
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxImages - existingUrls.size)
    ) { uris ->
        if (uris.isNotEmpty()) onImagesSelected(uris)
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            color = DeepEmerald,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        // We replace LazyVerticalGrid with a manual Column/Row grid because
        // this component is nested inside a scrollable Column in HomeProfileScreen.
        // Lazy layouts cannot be nested in scrollable containers in the same orientation.
        val totalItems = if (totalCount < maxImages) totalCount + 1 else totalCount
        val columns = 3
        val rows = (totalItems + columns - 1) / columns

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (rowIndex in 0 until rows) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (columnIndex in 0 until columns) {
                        val itemIndex = rowIndex * columns + columnIndex
                        Box(modifier = Modifier.weight(1f)) {
                            if (itemIndex < existingUrls.size) {
                                // Existing Remote Images
                                val url = existingUrls[itemIndex]
                                Box(
                                    modifier = Modifier
                                        .aspectRatio(1f)
                                        .clip(RoundedCornerShape(12.dp))
                                        .border(1.dp, DividerThin, RoundedCornerShape(12.dp))
                                ) {
                                    NammaImage(
                                        url = url,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                    // Remove button
                                    RemoveIcon { onRemoveExisting(url) }
                                }
                            } else if (itemIndex < totalCount) {
                                // Newly Selected URIs
                                val uri = selectedPhotos[itemIndex - existingUrls.size]
                                Box(
                                    modifier = Modifier
                                        .aspectRatio(1f)
                                        .clip(RoundedCornerShape(12.dp))
                                        .border(1.dp, DividerThin, RoundedCornerShape(12.dp))
                                ) {
                                    AsyncImage(
                                        model = uri,
                                        contentDescription = "Selected photo",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                    RemoveIcon { onRemoveSelected(uri) }
                                }
                            } else if (itemIndex == totalCount && totalCount < maxImages) {
                                // Add tile
                                Surface(
                                    modifier = Modifier
                                        .aspectRatio(1f)
                                        .clickable {
                                            launcher.launch(
                                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                            )
                                        },
                                    shape = RoundedCornerShape(12.dp),
                                    color = SurfaceSoft,
                                    border = androidx.compose.foundation.BorderStroke(1.5.dp, LeafAccent)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = "Add photo",
                                            tint = LeafAccent,
                                            modifier = Modifier.size(32.dp)
                                        )
                                    }
                                }
                            } else {
                                Spacer(modifier = Modifier.aspectRatio(1f))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BoxScope.RemoveIcon(onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(4.dp)
            .size(24.dp)
            .clickable { onClick() },
        shape = CircleShape,
        color = Color.Black.copy(alpha = 0.6f)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Remove",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
