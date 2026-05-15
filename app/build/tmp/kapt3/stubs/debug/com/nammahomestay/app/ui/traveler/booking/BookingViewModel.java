package com.nammahomestay.app.ui.traveler.booking;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.nammahomestay.app.data.model.Booking;
import com.nammahomestay.app.data.model.HomeStay;
import com.nammahomestay.app.data.repository.AuthRepository;
import com.nammahomestay.app.data.repository.BookingRepository;
import com.nammahomestay.app.data.repository.HomeStayRepository;
import com.nammahomestay.app.util.Resource;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;
import java.util.concurrent.TimeUnit;
import android.content.Context;
import dagger.hilt.android.qualifiers.ApplicationContext;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.nammahomestay.app.MainActivity;
import com.nammahomestay.app.R;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\f\n\u0002\u0010\t\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B1\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\b\u0010\u0017\u001a\u00020\u0018H\u0002J\u0006\u0010\u0019\u001a\u00020\u0018J\u0006\u0010\u001a\u001a\u00020\u0018J\u000e\u0010\u001b\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\u0011J\u0010\u0010\u001d\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\u0011H\u0002J\u000e\u0010\u001e\u001a\u00020\u00182\u0006\u0010\u001f\u001a\u00020\u0011J\u0018\u0010 \u001a\u00020\u00182\u0006\u0010!\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u0011H\u0002J\u001f\u0010#\u001a\u00020\u00182\b\u0010$\u001a\u0004\u0018\u00010%2\b\u0010&\u001a\u0004\u0018\u00010%\u00a2\u0006\u0002\u0010\'J\u000e\u0010(\u001a\u00020\u00182\u0006\u0010)\u001a\u00020*J\u000e\u0010+\u001a\u00020\u00182\u0006\u0010,\u001a\u00020\u0011R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006-"}, d2 = {"Lcom/nammahomestay/app/ui/traveler/booking/BookingViewModel;", "Landroidx/lifecycle/ViewModel;", "context", "Landroid/content/Context;", "authRepository", "Lcom/nammahomestay/app/data/repository/AuthRepository;", "homeStayRepository", "Lcom/nammahomestay/app/data/repository/HomeStayRepository;", "bookingRepository", "Lcom/nammahomestay/app/data/repository/BookingRepository;", "savedStateHandle", "Landroidx/lifecycle/SavedStateHandle;", "(Landroid/content/Context;Lcom/nammahomestay/app/data/repository/AuthRepository;Lcom/nammahomestay/app/data/repository/HomeStayRepository;Lcom/nammahomestay/app/data/repository/BookingRepository;Landroidx/lifecycle/SavedStateHandle;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/nammahomestay/app/ui/traveler/booking/BookingUiState;", "bookingIdFromNav", "", "homestayId", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "calculateTotal", "", "clearError", "createBooking", "loadBooking", "id", "loadHomeStay", "processPayment", "bookingId", "simulateProviderNotification", "title", "body", "updateDates", "checkIn", "", "checkOut", "(Ljava/lang/Long;Ljava/lang/Long;)V", "updateGuests", "count", "", "updatePaymentMethod", "method", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class BookingViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.HomeStayRepository homeStayRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.BookingRepository bookingRepository = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String homestayId = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String bookingIdFromNav = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.nammahomestay.app.ui.traveler.booking.BookingUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.nammahomestay.app.ui.traveler.booking.BookingUiState> uiState = null;
    
    @javax.inject.Inject()
    public BookingViewModel(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.AuthRepository authRepository, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.HomeStayRepository homeStayRepository, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.BookingRepository bookingRepository, @org.jetbrains.annotations.NotNull()
    androidx.lifecycle.SavedStateHandle savedStateHandle) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.nammahomestay.app.ui.traveler.booking.BookingUiState> getUiState() {
        return null;
    }
    
    private final void loadHomeStay(java.lang.String id) {
    }
    
    public final void updateDates(@org.jetbrains.annotations.Nullable()
    java.lang.Long checkIn, @org.jetbrains.annotations.Nullable()
    java.lang.Long checkOut) {
    }
    
    public final void updateGuests(int count) {
    }
    
    private final void calculateTotal() {
    }
    
    public final void updatePaymentMethod(@org.jetbrains.annotations.NotNull()
    java.lang.String method) {
    }
    
    public final void loadBooking(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
    }
    
    public final void createBooking() {
    }
    
    public final void processPayment(@org.jetbrains.annotations.NotNull()
    java.lang.String bookingId) {
    }
    
    private final void simulateProviderNotification(java.lang.String title, java.lang.String body) {
    }
    
    public final void clearError() {
    }
}