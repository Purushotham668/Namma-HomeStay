package com.nammahomestay.app.ui.provider.pricing;

import androidx.compose.foundation.layout.*;
import androidx.compose.foundation.text.KeyboardOptions;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.input.KeyboardType;
import androidx.navigation.NavHostController;
import com.nammahomestay.app.ui.navigation.Screen;
import com.nammahomestay.app.data.model.HomeStay;
import com.nammahomestay.app.ui.theme.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u00004\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a$\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\u0010\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\bH\u0003\u001a,\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\u0018\u0010\f\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH\u0003\u001a\u001a\u0010\u000f\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\r\u001a\u00020\u000eH\u0007\u001a$\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u00a8\u0006\u0013"}, d2 = {"AvailabilityToggleCard", "", "isAvailable", "", "onToggle", "Lkotlin/Function1;", "PortfolioSummaryCard", "uiState", "Lcom/nammahomestay/app/ui/provider/pricing/PricingUiState;", "PricingImmersiveHeader", "navController", "Landroidx/navigation/NavHostController;", "PricingInputForm", "viewModel", "Lcom/nammahomestay/app/ui/provider/pricing/PricingViewModel;", "PricingScreen", "PropertySelector", "onSelect", "Lcom/nammahomestay/app/data/model/HomeStay;", "app_debug"})
public final class PricingScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void PricingScreen(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavHostController navController, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.ui.provider.pricing.PricingViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PricingImmersiveHeader(androidx.navigation.NavHostController navController, boolean isAvailable, kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onToggle) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void AvailabilityToggleCard(boolean isAvailable, kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> onToggle) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PortfolioSummaryCard(com.nammahomestay.app.ui.provider.pricing.PricingUiState uiState) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PropertySelector(com.nammahomestay.app.ui.provider.pricing.PricingUiState uiState, kotlin.jvm.functions.Function1<? super com.nammahomestay.app.data.model.HomeStay, kotlin.Unit> onSelect) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PricingInputForm(com.nammahomestay.app.ui.provider.pricing.PricingUiState uiState, com.nammahomestay.app.ui.provider.pricing.PricingViewModel viewModel) {
    }
}