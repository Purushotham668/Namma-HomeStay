package com.nammahomestay.app.ui.traveler.explore;

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
import android.content.Intent;
import android.net.Uri;
import androidx.navigation.NavHostController;
import com.nammahomestay.app.data.model.HomeStay;
import com.nammahomestay.app.ui.navigation.Screen;
import com.nammahomestay.app.ui.theme.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000D\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\u001aC\u0010\u0000\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\u0011\u0010\u0007\u001a\r\u0012\u0004\u0012\u00020\u00010\b\u00a2\u0006\u0002\b\tH\u0007\u00f8\u0001\u0000\u00a2\u0006\u0004\b\n\u0010\u000b\u001a\u0010\u0010\f\u001a\u00020\u00012\u0006\u0010\r\u001a\u00020\u000eH\u0007\u001a\"\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u0015H\u0007\u001a\u001e\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u00132\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0019H\u0007\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u001a"}, d2 = {"FlowRow", "", "modifier", "Landroidx/compose/ui/Modifier;", "mainAxisSpacing", "Landroidx/compose/ui/unit/Dp;", "crossAxisSpacing", "content", "Lkotlin/Function0;", "Landroidx/compose/runtime/Composable;", "FlowRow-ghNngFA", "(Landroidx/compose/ui/Modifier;FFLkotlin/jvm/functions/Function0;)V", "FoodItemRow", "item", "Lcom/nammahomestay/app/data/model/FoodItem;", "HomeStayDetailScreen", "navController", "Landroidx/navigation/NavHostController;", "homestayId", "", "viewModel", "Lcom/nammahomestay/app/ui/traveler/explore/ExploreViewModel;", "MealCategory", "title", "items", "", "app_debug"})
public final class HomeStayDetailScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class, androidx.compose.foundation.ExperimentalFoundationApi.class})
    @androidx.compose.runtime.Composable()
    public static final void HomeStayDetailScreen(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavHostController navController, @org.jetbrains.annotations.NotNull()
    java.lang.String homestayId, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.ui.traveler.explore.ExploreViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void MealCategory(@org.jetbrains.annotations.NotNull()
    java.lang.String title, @org.jetbrains.annotations.NotNull()
    java.util.List<com.nammahomestay.app.data.model.FoodItem> items) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void FoodItemRow(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.model.FoodItem item) {
    }
}