package com.nammahomestay.app.ui.traveler.booking;

import androidx.compose.animation.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.navigation.NavHostController;
import com.nammahomestay.app.ui.navigation.Screen;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.layout.ContentScale;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextOverflow;
import com.nammahomestay.app.data.model.Booking;
import com.nammahomestay.app.ui.theme.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000B\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a$\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001a$\u0010\u0005\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001a\u0010\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\bH\u0007\u001aP\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0007\u001aB\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00142\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u00162\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00010\u00162\b\b\u0002\u0010\u0017\u001a\u00020\u0018H\u0007\u001a\u001a\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\b2\b\b\u0002\u0010\u001b\u001a\u00020\u001cH\u0007\u001a\u0018\u0010\u001d\u001a\u00020\b2\u0006\u0010\u001e\u001a\u00020\r2\u0006\u0010\u001f\u001a\u00020\rH\u0002\u00a8\u0006 "}, d2 = {"CancelConfirmationDialog", "", "onConfirm", "Lkotlin/Function0;", "onDismiss", "CheckoutConfirmationDialog", "EmptyBookingsState", "tab", "", "ModernBookingCard", "booking", "Lcom/nammahomestay/app/data/model/Booking;", "currentTime", "", "onClick", "onReviewClick", "onCancelClick", "onCheckoutClick", "MyBookingsScreen", "navController", "Landroidx/navigation/NavHostController;", "onBookingClick", "Lkotlin/Function1;", "viewModel", "Lcom/nammahomestay/app/ui/traveler/booking/MyBookingsViewModel;", "StatusBadge", "status", "modifier", "Landroidx/compose/ui/Modifier;", "formatDateRange", "start", "end", "app_debug"})
public final class MyBookingsScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void MyBookingsScreen(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavHostController navController, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onBookingClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.nammahomestay.app.data.model.Booking, kotlin.Unit> onReviewClick, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.ui.traveler.booking.MyBookingsViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ModernBookingCard(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.model.Booking booking, long currentTime, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onReviewClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onCancelClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onCheckoutClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void CancelConfirmationDialog(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onConfirm, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void CheckoutConfirmationDialog(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onConfirm, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void StatusBadge(@org.jetbrains.annotations.NotNull()
    java.lang.String status, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void EmptyBookingsState(@org.jetbrains.annotations.NotNull()
    java.lang.String tab) {
    }
    
    private static final java.lang.String formatDateRange(long start, long end) {
        return null;
    }
}