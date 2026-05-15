package com.nammahomestay.app.ui.traveler.booking;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.nammahomestay.app.data.model.Review;
import com.nammahomestay.app.data.repository.AuthRepository;
import com.nammahomestay.app.data.repository.BookingRepository;
import com.nammahomestay.app.data.repository.HomeStayRepository;
import com.nammahomestay.app.util.Resource;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018J\b\u0010\u001a\u001a\u00020\u0016H\u0002J\u000e\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u001c\u001a\u00020\u0019J\u0006\u0010\u001d\u001a\u00020\u0016J\u000e\u0010\u001e\u001a\u00020\u00162\u0006\u0010\u001f\u001a\u00020\u000fJ\u000e\u0010 \u001a\u00020\u00162\u0006\u0010!\u001a\u00020\"R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\r0\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006#"}, d2 = {"Lcom/nammahomestay/app/ui/traveler/booking/ReviewViewModel;", "Landroidx/lifecycle/ViewModel;", "savedStateHandle", "Landroidx/lifecycle/SavedStateHandle;", "authRepository", "Lcom/nammahomestay/app/data/repository/AuthRepository;", "bookingRepository", "Lcom/nammahomestay/app/data/repository/BookingRepository;", "homeStayRepository", "Lcom/nammahomestay/app/data/repository/HomeStayRepository;", "(Landroidx/lifecycle/SavedStateHandle;Lcom/nammahomestay/app/data/repository/AuthRepository;Lcom/nammahomestay/app/data/repository/BookingRepository;Lcom/nammahomestay/app/data/repository/HomeStayRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/nammahomestay/app/ui/traveler/booking/ReviewUiState;", "bookingId", "", "homestayId", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "addPhotos", "", "uris", "", "Landroid/net/Uri;", "loadExistingReview", "removePhoto", "uri", "submitReview", "updateComment", "comment", "updateRating", "rating", "", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ReviewViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.BookingRepository bookingRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.HomeStayRepository homeStayRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String bookingId = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String homestayId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.nammahomestay.app.ui.traveler.booking.ReviewUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.nammahomestay.app.ui.traveler.booking.ReviewUiState> uiState = null;
    
    @javax.inject.Inject()
    public ReviewViewModel(@org.jetbrains.annotations.NotNull()
    androidx.lifecycle.SavedStateHandle savedStateHandle, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.AuthRepository authRepository, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.BookingRepository bookingRepository, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.HomeStayRepository homeStayRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.nammahomestay.app.ui.traveler.booking.ReviewUiState> getUiState() {
        return null;
    }
    
    private final void loadExistingReview() {
    }
    
    public final void updateRating(int rating) {
    }
    
    public final void updateComment(@org.jetbrains.annotations.NotNull()
    java.lang.String comment) {
    }
    
    public final void addPhotos(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends android.net.Uri> uris) {
    }
    
    public final void removePhoto(@org.jetbrains.annotations.NotNull()
    android.net.Uri uri) {
    }
    
    public final void submitReview() {
    }
}