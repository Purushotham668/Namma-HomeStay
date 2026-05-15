package com.nammahomestay.app.ui.traveler.explore;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.*;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavHostController;
import com.google.android.gms.location.LocationServices;
import com.nammahomestay.app.data.model.HomeStay;
import com.nammahomestay.app.ui.navigation.Screen;
import com.nammahomestay.app.ui.theme.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000h\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\u001a\b\u0010\u0000\u001a\u00020\u0001H\u0007\u001a\u0018\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u001a\u001a\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0007\u001a&\u0010\n\u001a\u00020\u00012\u001c\u0010\u000b\u001a\u0018\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u00010\f\u00a2\u0006\u0002\b\u000e\u00a2\u0006\u0002\b\u000fH\u0007\u001a7\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u00142\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u0017H\u0007\u00a2\u0006\u0002\u0010\u0018\u001a\u0018\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001bH\u0007\u001a.\u0010\u001d\u001a\u00020\u00012\u0006\u0010\u001e\u001a\u00020\u001b2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u0017H\u0007\u001a\"\u0010#\u001a\u00020\u00012\u0006\u0010$\u001a\u00020\u001b2\u0006\u0010%\u001a\u00020&H\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\'\u0010(\u001a(\u0010)\u001a\u00020\u00142\u0006\u0010*\u001a\u00020\u00142\u0006\u0010+\u001a\u00020\u00142\u0006\u0010,\u001a\u00020\u00142\u0006\u0010-\u001a\u00020\u0014H\u0002\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006."}, d2 = {"EmptyState", "", "ExploreHeader", "viewModel", "Lcom/nammahomestay/app/ui/traveler/explore/ExploreViewModel;", "uiState", "Lcom/nammahomestay/app/ui/traveler/explore/ExploreUiState;", "ExploreScreen", "navController", "Landroidx/navigation/NavHostController;", "FadingHorizontalRow", "content", "Lkotlin/Function1;", "Landroidx/compose/foundation/layout/RowScope;", "Landroidx/compose/runtime/Composable;", "Lkotlin/ExtensionFunctionType;", "HomeStayListItem", "stay", "Lcom/nammahomestay/app/data/model/HomeStay;", "userLat", "", "userLon", "onClick", "Lkotlin/Function0;", "(Lcom/nammahomestay/app/data/model/HomeStay;Ljava/lang/Double;Ljava/lang/Double;Lkotlin/jvm/functions/Function0;)V", "SectionHeader", "title", "", "subtitle", "SortBadge", "label", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "selected", "", "StatusChip", "text", "color", "Landroidx/compose/ui/graphics/Color;", "StatusChip-4WTKRHQ", "(Ljava/lang/String;J)V", "calculateDistance", "lat1", "lon1", "lat2", "lon2", "app_debug"})
public final class ExploreScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void ExploreScreen(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavHostController navController, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.ui.traveler.explore.ExploreViewModel viewModel) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void ExploreHeader(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.ui.traveler.explore.ExploreViewModel viewModel, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.ui.traveler.explore.ExploreUiState uiState) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void FadingHorizontalRow(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super androidx.compose.foundation.layout.RowScope, kotlin.Unit> content) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SortBadge(@org.jetbrains.annotations.NotNull()
    java.lang.String label, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.graphics.vector.ImageVector icon, boolean selected, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void SectionHeader(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.lang.String subtitle) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void HomeStayListItem(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.model.HomeStay stay, @org.jetbrains.annotations.Nullable()
    java.lang.Double userLat, @org.jetbrains.annotations.Nullable()
    java.lang.Double userLon, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void EmptyState() {
    }
    
    private static final double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        return 0.0;
    }
}