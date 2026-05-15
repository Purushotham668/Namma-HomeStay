package com.nammahomestay.app.ui.traveler.explore;

import androidx.lifecycle.ViewModel;
import com.nammahomestay.app.data.model.HomeStay;
import com.nammahomestay.app.data.model.Review;
import com.nammahomestay.app.data.repository.AuthRepository;
import com.nammahomestay.app.data.repository.BookingRepository;
import com.nammahomestay.app.data.repository.HomeStayRepository;
import com.nammahomestay.app.util.Resource;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;
import kotlin.math.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\u0016\u001a\u00020\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0002J(\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\u001aH\u0002J\b\u0010\u001f\u001a\u00020\u0017H\u0002J\u0006\u0010 \u001a\u00020\u0017J\u000e\u0010!\u001a\u00020\u00172\u0006\u0010\"\u001a\u00020#J\u000e\u0010$\u001a\u00020\u00172\u0006\u0010%\u001a\u00020#J\u000e\u0010&\u001a\u00020\u00172\u0006\u0010\'\u001a\u00020(J\u000e\u0010)\u001a\u00020\u00172\u0006\u0010*\u001a\u00020#J\u0006\u0010+\u001a\u00020\u0017J\u0006\u0010,\u001a\u00020\u0017J\u0010\u0010-\u001a\u00020\u00172\u0006\u0010%\u001a\u00020#H\u0002J\u0016\u0010.\u001a\u00020\u00172\u0006\u0010/\u001a\u00020\u001a2\u0006\u00100\u001a\u00020\u001aR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015\u00a8\u00061"}, d2 = {"Lcom/nammahomestay/app/ui/traveler/explore/ExploreViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/nammahomestay/app/data/repository/HomeStayRepository;", "bookingRepository", "Lcom/nammahomestay/app/data/repository/BookingRepository;", "authRepository", "Lcom/nammahomestay/app/data/repository/AuthRepository;", "(Lcom/nammahomestay/app/data/repository/HomeStayRepository;Lcom/nammahomestay/app/data/repository/BookingRepository;Lcom/nammahomestay/app/data/repository/AuthRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/nammahomestay/app/ui/traveler/explore/ExploreUiState;", "bookingJob", "Lkotlinx/coroutines/Job;", "originalList", "", "Lcom/nammahomestay/app/data/model/HomeStay;", "reviewsJob", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "applySortingAndFiltering", "", "list", "calculateDistance", "", "lat1", "lon1", "lat2", "lon2", "fetchAndProcess", "loadHomeStays", "loadReviews", "homestayId", "", "onSearchQueryChanged", "query", "setPriceSort", "sort", "Lcom/nammahomestay/app/ui/traveler/explore/PriceSort;", "toggleCategory", "category", "toggleNearest", "toggleTopRated", "updateSuggestions", "updateUserLocation", "lat", "lon", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ExploreViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.HomeStayRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.BookingRepository bookingRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.nammahomestay.app.ui.traveler.explore.ExploreUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.nammahomestay.app.ui.traveler.explore.ExploreUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.nammahomestay.app.data.model.HomeStay> originalList;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job bookingJob;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job reviewsJob;
    
    @javax.inject.Inject()
    public ExploreViewModel(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.HomeStayRepository repository, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.BookingRepository bookingRepository, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.AuthRepository authRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.nammahomestay.app.ui.traveler.explore.ExploreUiState> getUiState() {
        return null;
    }
    
    public final void loadHomeStays() {
    }
    
    public final void onSearchQueryChanged(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    public final void loadReviews(@org.jetbrains.annotations.NotNull()
    java.lang.String homestayId) {
    }
    
    private final void updateSuggestions(java.lang.String query) {
    }
    
    public final void toggleNearest() {
    }
    
    public final void toggleTopRated() {
    }
    
    public final void setPriceSort(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.ui.traveler.explore.PriceSort sort) {
    }
    
    public final void updateUserLocation(double lat, double lon) {
    }
    
    private final void fetchAndProcess() {
    }
    
    public final void toggleCategory(@org.jetbrains.annotations.NotNull()
    java.lang.String category) {
    }
    
    private final void applySortingAndFiltering(java.util.List<com.nammahomestay.app.data.model.HomeStay> list) {
    }
    
    private final double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        return 0.0;
    }
}