package com.nammahomestay.app.ui.traveler.food;

import androidx.compose.animation.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavHostController;
import com.nammahomestay.app.data.model.DailyMenu;
import com.nammahomestay.app.data.model.FoodBooking;
import com.nammahomestay.app.data.model.FoodItem;
import com.nammahomestay.app.data.model.OrderItem;
import com.nammahomestay.app.data.repository.AuthRepository;
import com.nammahomestay.app.data.repository.BookingRepository;
import com.nammahomestay.app.data.repository.FoodRepository;
import com.nammahomestay.app.data.repository.HomeStayRepository;
import com.nammahomestay.app.ui.navigation.Screen;
import com.nammahomestay.app.ui.theme.*;
import com.nammahomestay.app.util.Resource;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\\\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\u001a \u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0007\u001a\u0010\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\nH\u0007\u001a2\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0018\u0010\u0010\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u0011H\u0007\u001a\"\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0007\u001aL\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u00072\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\r0\u00182\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u000f0\u001a2\u0018\u0010\u0010\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u0011H\u0007\u001a\u0016\u0010\u001b\u001a\u00020\u00012\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00010\u001dH\u0007\u001a\u0010\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u0003H\u0002\u00a8\u0006!"}, d2 = {"CheckoutBar", "", "uiState", "Lcom/nammahomestay/app/ui/traveler/food/MealBookingUiState;", "viewModel", "Lcom/nammahomestay/app/ui/traveler/food/MealBookingViewModel;", "homestayId", "", "DietaryIcon", "isVeg", "", "FoodOrderCard", "item", "Lcom/nammahomestay/app/data/model/FoodItem;", "quantity", "", "onUpdate", "Lkotlin/Function2;", "MealBookingScreen", "navController", "Landroidx/navigation/NavHostController;", "MealCategorySection", "title", "items", "", "cart", "", "NoStayBookingView", "onBookStay", "Lkotlin/Function0;", "calculateTotal", "", "state", "app_debug"})
public final class MealBookingScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void MealBookingScreen(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavHostController navController, @org.jetbrains.annotations.NotNull()
    java.lang.String homestayId, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.ui.traveler.food.MealBookingViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void NoStayBookingView(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBookStay) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void MealCategorySection(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.util.List<com.nammahomestay.app.data.model.FoodItem> items, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.Integer> cart, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.Integer, kotlin.Unit> onUpdate) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void FoodOrderCard(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.model.FoodItem item, int quantity, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.Integer, kotlin.Unit> onUpdate) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void DietaryIcon(boolean isVeg) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void CheckoutBar(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.ui.traveler.food.MealBookingUiState uiState, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.ui.traveler.food.MealBookingViewModel viewModel, @org.jetbrains.annotations.NotNull()
    java.lang.String homestayId) {
    }
    
    private static final double calculateTotal(com.nammahomestay.app.ui.traveler.food.MealBookingUiState state) {
        return 0.0;
    }
}