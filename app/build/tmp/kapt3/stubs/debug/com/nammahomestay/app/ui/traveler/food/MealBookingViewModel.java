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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0016\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0015J\u000e\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015J\u0016\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u001bR\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u001c"}, d2 = {"Lcom/nammahomestay/app/ui/traveler/food/MealBookingViewModel;", "Landroidx/lifecycle/ViewModel;", "foodRepository", "Lcom/nammahomestay/app/data/repository/FoodRepository;", "homestayRepository", "Lcom/nammahomestay/app/data/repository/HomeStayRepository;", "bookingRepository", "Lcom/nammahomestay/app/data/repository/BookingRepository;", "authRepository", "Lcom/nammahomestay/app/data/repository/AuthRepository;", "(Lcom/nammahomestay/app/data/repository/FoodRepository;Lcom/nammahomestay/app/data/repository/HomeStayRepository;Lcom/nammahomestay/app/data/repository/BookingRepository;Lcom/nammahomestay/app/data/repository/AuthRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/nammahomestay/app/ui/traveler/food/MealBookingUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "bookMeals", "", "homestayId", "", "paymentMethod", "loadMenu", "updateCart", "itemId", "delta", "", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class MealBookingViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.FoodRepository foodRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.HomeStayRepository homestayRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.BookingRepository bookingRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.nammahomestay.app.ui.traveler.food.MealBookingUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.nammahomestay.app.ui.traveler.food.MealBookingUiState> uiState = null;
    
    @javax.inject.Inject()
    public MealBookingViewModel(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.FoodRepository foodRepository, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.HomeStayRepository homestayRepository, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.BookingRepository bookingRepository, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.AuthRepository authRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.nammahomestay.app.ui.traveler.food.MealBookingUiState> getUiState() {
        return null;
    }
    
    public final void loadMenu(@org.jetbrains.annotations.NotNull()
    java.lang.String homestayId) {
    }
    
    public final void updateCart(@org.jetbrains.annotations.NotNull()
    java.lang.String itemId, int delta) {
    }
    
    public final void bookMeals(@org.jetbrains.annotations.NotNull()
    java.lang.String homestayId, @org.jetbrains.annotations.NotNull()
    java.lang.String paymentMethod) {
    }
}