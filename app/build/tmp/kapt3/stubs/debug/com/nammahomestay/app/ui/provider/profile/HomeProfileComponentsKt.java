package com.nammahomestay.app.ui.provider.profile;

import android.net.Uri;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.window.DialogProperties;
import com.nammahomestay.app.data.model.HomeStay;
import com.nammahomestay.app.ui.components.*;
import com.nammahomestay.app.ui.theme.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000^\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0007\u001a\u0018\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0007\u001a\u0018\u0010\u0007\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0007\u001aH\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00010\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u000e2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00010\u0011H\u0007\u001a\u0018\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0007\u001a@\u0010\u0014\u001a\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u000e2\u0018\u0010\u0019\u001a\u0014\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00010\u001aH\u0007\u001a\u0018\u0010\u001b\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0007\u001a.\u0010\u001c\u001a\u00020\u00012\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00010\u000e2\u0006\u0010\u001e\u001a\u00020\f2\u0006\u0010\u001f\u001a\u00020\f2\u0006\u0010 \u001a\u00020!H\u0007\u001aF\u0010\"\u001a\u00020\u00012\u0006\u0010#\u001a\u00020$2\u0012\u0010%\u001a\u000e\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020\u00010\u00112\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u00010\u000e2\u0012\u0010\'\u001a\u000e\u0012\u0004\u0012\u00020(\u0012\u0004\u0012\u00020\u00010\u0011H\u0007\u001a\u001c\u0010)\u001a\u00020\u00012\u0006\u0010*\u001a\u00020!2\n\b\u0002\u0010+\u001a\u0004\u0018\u00010!H\u0007\u001a\u000e\u0010,\u001a\u00020\f2\u0006\u0010\u0002\u001a\u00020\u0003\u00a8\u0006-"}, d2 = {"ChecklistSection", "", "uiState", "Lcom/nammahomestay/app/ui/provider/profile/HomeProfileUiState;", "viewModel", "Lcom/nammahomestay/app/ui/provider/profile/HomeProfileViewModel;", "FloatingIdentityCard", "GallerySection", "HomeStaySummaryCard", "homeStay", "Lcom/nammahomestay/app/data/model/HomeStay;", "inquiryCount", "", "onClick", "Lkotlin/Function0;", "onDelete", "onToggleAvailability", "Lkotlin/Function1;", "", "LocationSection", "MapDialog", "latitude", "", "longitude", "onDismiss", "onConfirm", "Lkotlin/Function2;", "PricingSection", "ProfileImmersiveHeader", "onBack", "completedFields", "totalFields", "hostName", "", "RoomEditCard", "room", "Lcom/nammahomestay/app/data/model/Room;", "onUpdate", "onRemove", "onPhotoSelected", "Landroid/net/Uri;", "SectionTitle", "title", "subtitle", "calculateCompletion", "app_debug"})
public final class HomeProfileComponentsKt {
    
    @androidx.compose.runtime.Composable()
    public static final void SectionTitle(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.Nullable()
    java.lang.String subtitle) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ProfileImmersiveHeader(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, int completedFields, int totalFields, @org.jetbrains.annotations.NotNull()
    java.lang.String hostName) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void HomeStaySummaryCard(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.model.HomeStay homeStay, int inquiryCount, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDelete, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onToggleAvailability) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void FloatingIdentityCard(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.ui.provider.profile.HomeProfileUiState uiState, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.ui.provider.profile.HomeProfileViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void LocationSection(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.ui.provider.profile.HomeProfileUiState uiState, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.ui.provider.profile.HomeProfileViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void ChecklistSection(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.ui.provider.profile.HomeProfileUiState uiState, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.ui.provider.profile.HomeProfileViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void PricingSection(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.ui.provider.profile.HomeProfileUiState uiState, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.ui.provider.profile.HomeProfileViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void GallerySection(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.ui.provider.profile.HomeProfileUiState uiState, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.ui.provider.profile.HomeProfileViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void RoomEditCard(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.model.Room room, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.nammahomestay.app.data.model.Room, kotlin.Unit> onUpdate, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRemove, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super android.net.Uri, kotlin.Unit> onPhotoSelected) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void MapDialog(double latitude, double longitude, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function2<? super java.lang.Double, ? super java.lang.Double, kotlin.Unit> onConfirm) {
    }
    
    public static final int calculateCompletion(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.ui.provider.profile.HomeProfileUiState uiState) {
        return 0;
    }
}