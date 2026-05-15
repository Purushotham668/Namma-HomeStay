package com.nammahomestay.app.ui.provider.food;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.text.font.FontWeight;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavHostController;
import com.nammahomestay.app.data.model.FoodBooking;
import com.nammahomestay.app.data.model.HomeStay;
import com.nammahomestay.app.data.model.OrderItem;
import com.nammahomestay.app.data.repository.AuthRepository;
import com.nammahomestay.app.data.repository.FoodRepository;
import com.nammahomestay.app.data.repository.HomeStayRepository;
import com.nammahomestay.app.ui.navigation.Screen;
import com.nammahomestay.app.ui.theme.*;
import com.nammahomestay.app.util.Resource;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000B\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0018\u0002\n\u0000\u001aL\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\t0\b2\u0018\u0010\n\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010\u000bH\u0007\u001a\u001a\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0007\u001a\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0002\u00a8\u0006\u0015"}, d2 = {"MealTerminalSection", "", "title", "", "items", "", "Lcom/nammahomestay/app/data/model/FoodItem;", "cart", "", "", "onUpdate", "Lkotlin/Function2;", "ProviderFoodTerminalScreen", "navController", "Landroidx/navigation/NavHostController;", "viewModel", "Lcom/nammahomestay/app/ui/provider/food/ProviderFoodTerminalViewModel;", "calculateTotal", "", "state", "Lcom/nammahomestay/app/ui/provider/food/ProviderFoodTerminalUiState;", "app_debug"})
public final class ProviderFoodTerminalScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void ProviderFoodTerminalScreen(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavHostController navController, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.ui.provider.food.ProviderFoodTerminalViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void MealTerminalSection(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.util.List<com.nammahomestay.app.data.model.FoodItem> items, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.Integer> cart, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.Integer, kotlin.Unit> onUpdate) {
    }
    
    private static final double calculateTotal(com.nammahomestay.app.ui.provider.food.ProviderFoodTerminalUiState state) {
        return 0.0;
    }
}