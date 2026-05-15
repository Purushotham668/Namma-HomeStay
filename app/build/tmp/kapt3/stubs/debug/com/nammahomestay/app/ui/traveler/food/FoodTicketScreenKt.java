package com.nammahomestay.app.ui.traveler.food;

import android.graphics.Bitmap;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavHostController;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.nammahomestay.app.data.model.FoodBooking;
import com.nammahomestay.app.data.repository.FoodRepository;
import com.nammahomestay.app.ui.navigation.Screen;
import com.nammahomestay.app.ui.theme.*;
import com.nammahomestay.app.util.Resource;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000(\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\u001a\"\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0007\u001a\u001a\u0010\b\u001a\u0004\u0018\u00010\t2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\fH\u0002\u00a8\u0006\r"}, d2 = {"FoodTicketScreen", "", "navController", "Landroidx/navigation/NavHostController;", "bookingId", "", "viewModel", "Lcom/nammahomestay/app/ui/traveler/food/FoodTicketViewModel;", "generateQrCode", "Landroid/graphics/Bitmap;", "text", "size", "", "app_debug"})
public final class FoodTicketScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void FoodTicketScreen(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavHostController navController, @org.jetbrains.annotations.NotNull()
    java.lang.String bookingId, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.ui.traveler.food.FoodTicketViewModel viewModel) {
    }
    
    private static final android.graphics.Bitmap generateQrCode(java.lang.String text, int size) {
        return null;
    }
}