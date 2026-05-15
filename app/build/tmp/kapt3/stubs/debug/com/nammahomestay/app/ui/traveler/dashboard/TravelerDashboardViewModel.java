package com.nammahomestay.app.ui.traveler.dashboard;

import androidx.lifecycle.ViewModel;
import com.nammahomestay.app.data.model.HomeStay;
import com.nammahomestay.app.data.model.Booking;
import com.nammahomestay.app.data.repository.AuthRepository;
import com.nammahomestay.app.data.repository.BookingRepository;
import com.nammahomestay.app.data.repository.HomeStayRepository;
import com.nammahomestay.app.util.Resource;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\b\u0010\u0013\u001a\u00020\u0014H\u0002J\u000e\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0017J\u000e\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u0017J\b\u0010\u001a\u001a\u00020\u0014H\u0002J\u0010\u0010\u001b\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u0017H\u0002R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u001c"}, d2 = {"Lcom/nammahomestay/app/ui/traveler/dashboard/TravelerDashboardViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/nammahomestay/app/data/repository/HomeStayRepository;", "bookingRepository", "Lcom/nammahomestay/app/data/repository/BookingRepository;", "authRepository", "Lcom/nammahomestay/app/data/repository/AuthRepository;", "(Lcom/nammahomestay/app/data/repository/HomeStayRepository;Lcom/nammahomestay/app/data/repository/BookingRepository;Lcom/nammahomestay/app/data/repository/AuthRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/nammahomestay/app/ui/traveler/dashboard/DashboardUiState;", "allHomeStays", "", "Lcom/nammahomestay/app/data/model/HomeStay;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "loadData", "", "onCategorySelected", "category", "", "onSearchQueryChanged", "query", "updateFeaturedStays", "updateSuggestions", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class TravelerDashboardViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.HomeStayRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.BookingRepository bookingRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.nammahomestay.app.ui.traveler.dashboard.DashboardUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.nammahomestay.app.ui.traveler.dashboard.DashboardUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.nammahomestay.app.data.model.HomeStay> allHomeStays;
    
    @javax.inject.Inject()
    public TravelerDashboardViewModel(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.HomeStayRepository repository, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.BookingRepository bookingRepository, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.AuthRepository authRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.nammahomestay.app.ui.traveler.dashboard.DashboardUiState> getUiState() {
        return null;
    }
    
    private final void loadData() {
    }
    
    public final void onCategorySelected(@org.jetbrains.annotations.NotNull()
    java.lang.String category) {
    }
    
    private final void updateFeaturedStays() {
    }
    
    public final void onSearchQueryChanged(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    private final void updateSuggestions(java.lang.String query) {
    }
}