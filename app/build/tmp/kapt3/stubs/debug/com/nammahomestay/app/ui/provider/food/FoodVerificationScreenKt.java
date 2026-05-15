package com.nammahomestay.app.ui.provider.food;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavHostController;
import com.nammahomestay.app.data.model.FoodBooking;
import com.nammahomestay.app.data.repository.FoodRepository;
import com.nammahomestay.app.ui.theme.*;
import com.nammahomestay.app.util.Resource;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001a\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0007\u001a \u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u0003H\u0007\u001a\b\u0010\t\u001a\u00020\u0001H\u0007\u00a8\u0006\n"}, d2 = {"FoodVerificationScreen", "", "navController", "Landroidx/navigation/NavHostController;", "viewModel", "Lcom/nammahomestay/app/ui/provider/food/FoodVerificationViewModel;", "ResultContent", "uiState", "Lcom/nammahomestay/app/ui/provider/food/FoodVerificationUiState;", "ScannerOverlay", "app_debug"})
public final class FoodVerificationScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void FoodVerificationScreen(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavHostController navController, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.ui.provider.food.FoodVerificationViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ScannerOverlay() {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ResultContent(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.ui.provider.food.FoodVerificationUiState uiState, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.ui.provider.food.FoodVerificationViewModel viewModel, @org.jetbrains.annotations.NotNull()
    androidx.navigation.NavHostController navController) {
    }
}