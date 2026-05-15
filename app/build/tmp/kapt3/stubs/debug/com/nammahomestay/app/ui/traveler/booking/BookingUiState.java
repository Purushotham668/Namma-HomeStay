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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b%\b\u0087\b\u0018\u00002\u00020\u0001Bs\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u000e\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u000e\u0012\b\b\u0002\u0010\u0011\u001a\u00020\f\u00a2\u0006\u0002\u0010\u0012J\u000b\u0010#\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010$\u001a\u00020\fH\u00c6\u0003J\u0010\u0010%\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0016J\u0010\u0010&\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0016J\t\u0010\'\u001a\u00020\bH\u00c6\u0003J\t\u0010(\u001a\u00020\nH\u00c6\u0003J\t\u0010)\u001a\u00020\fH\u00c6\u0003J\u000b\u0010*\u001a\u0004\u0018\u00010\u000eH\u00c6\u0003J\u000b\u0010+\u001a\u0004\u0018\u00010\u000eH\u00c6\u0003J\t\u0010,\u001a\u00020\u000eH\u00c6\u0003J|\u0010-\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u000e2\b\b\u0002\u0010\u0010\u001a\u00020\u000e2\b\b\u0002\u0010\u0011\u001a\u00020\fH\u00c6\u0001\u00a2\u0006\u0002\u0010.J\u0013\u0010/\u001a\u00020\f2\b\u00100\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00101\u001a\u00020\bH\u00d6\u0001J\t\u00102\u001a\u00020\u000eH\u00d6\u0001R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u0015\u0010\u0016R\u0015\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\n\n\u0002\u0010\u0017\u001a\u0004\b\u0018\u0010\u0016R\u0013\u0010\r\u001a\u0004\u0018\u00010\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0014R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u001eR\u0011\u0010\u0010\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0014R\u0011\u0010\u0011\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001eR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\"\u00a8\u00063"}, d2 = {"Lcom/nammahomestay/app/ui/traveler/booking/BookingUiState;", "", "homeStay", "Lcom/nammahomestay/app/data/model/HomeStay;", "checkInDate", "", "checkOutDate", "guests", "", "totalPrice", "", "isLoading", "", "error", "", "bookingId", "paymentMethod", "paymentSuccess", "(Lcom/nammahomestay/app/data/model/HomeStay;Ljava/lang/Long;Ljava/lang/Long;IDZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)V", "getBookingId", "()Ljava/lang/String;", "getCheckInDate", "()Ljava/lang/Long;", "Ljava/lang/Long;", "getCheckOutDate", "getError", "getGuests", "()I", "getHomeStay", "()Lcom/nammahomestay/app/data/model/HomeStay;", "()Z", "getPaymentMethod", "getPaymentSuccess", "getTotalPrice", "()D", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(Lcom/nammahomestay/app/data/model/HomeStay;Ljava/lang/Long;Ljava/lang/Long;IDZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)Lcom/nammahomestay/app/ui/traveler/booking/BookingUiState;", "equals", "other", "hashCode", "toString", "app_debug"})
public final class BookingUiState {
    @org.jetbrains.annotations.Nullable()
    private final com.nammahomestay.app.data.model.HomeStay homeStay = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long checkInDate = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Long checkOutDate = null;
    private final int guests = 0;
    private final double totalPrice = 0.0;
    private final boolean isLoading = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String error = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String bookingId = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String paymentMethod = null;
    private final boolean paymentSuccess = false;
    
    public BookingUiState(@org.jetbrains.annotations.Nullable()
    com.nammahomestay.app.data.model.HomeStay homeStay, @org.jetbrains.annotations.Nullable()
    java.lang.Long checkInDate, @org.jetbrains.annotations.Nullable()
    java.lang.Long checkOutDate, int guests, double totalPrice, boolean isLoading, @org.jetbrains.annotations.Nullable()
    java.lang.String error, @org.jetbrains.annotations.Nullable()
    java.lang.String bookingId, @org.jetbrains.annotations.NotNull()
    java.lang.String paymentMethod, boolean paymentSuccess) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.nammahomestay.app.data.model.HomeStay getHomeStay() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getCheckInDate() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getCheckOutDate() {
        return null;
    }
    
    public final int getGuests() {
        return 0;
    }
    
    public final double getTotalPrice() {
        return 0.0;
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getError() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getBookingId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPaymentMethod() {
        return null;
    }
    
    public final boolean getPaymentSuccess() {
        return false;
    }
    
    public BookingUiState() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.nammahomestay.app.data.model.HomeStay component1() {
        return null;
    }
    
    public final boolean component10() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long component3() {
        return null;
    }
    
    public final int component4() {
        return 0;
    }
    
    public final double component5() {
        return 0.0;
    }
    
    public final boolean component6() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.nammahomestay.app.ui.traveler.booking.BookingUiState copy(@org.jetbrains.annotations.Nullable()
    com.nammahomestay.app.data.model.HomeStay homeStay, @org.jetbrains.annotations.Nullable()
    java.lang.Long checkInDate, @org.jetbrains.annotations.Nullable()
    java.lang.Long checkOutDate, int guests, double totalPrice, boolean isLoading, @org.jetbrains.annotations.Nullable()
    java.lang.String error, @org.jetbrains.annotations.Nullable()
    java.lang.String bookingId, @org.jetbrains.annotations.NotNull()
    java.lang.String paymentMethod, boolean paymentSuccess) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}