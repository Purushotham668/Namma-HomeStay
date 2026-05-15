package com.nammahomestay.app.ui.provider.menu;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.text.font.FontWeight;
import androidx.navigation.NavHostController;
import com.nammahomestay.app.data.model.HomeStay;
import com.nammahomestay.app.ui.navigation.Screen;
import com.nammahomestay.app.ui.theme.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001a\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0007\u001a\u0098\u0001\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\u0018\u0010\u0011\u001a\u0014\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\u00122\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\b\b\u0002\u0010\u0018\u001a\u00020\nH\u0003\u001a\u00d4\u0001\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\b0\u001c2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\n0\u001e2\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\u00142\u0006\u0010\u0018\u001a\u00020\n2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00102\u001e\u0010\u0011\u001a\u001a\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010 2\u0018\u0010\u0013\u001a\u0014\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\u00122\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\u00142\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\u00142\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\u0014H\u0003\u001a\u0010\u0010\"\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u001a$\u0010#\u001a\u00020\u00012\u0006\u0010$\u001a\u00020%2\u0012\u0010&\u001a\u000e\u0012\u0004\u0012\u00020\'\u0012\u0004\u0012\u00020\u00010\u0014H\u0003\u00a8\u0006("}, d2 = {"DailyMenuScreen", "", "navController", "Landroidx/navigation/NavHostController;", "viewModel", "Lcom/nammahomestay/app/ui/provider/menu/DailyMenuViewModel;", "FoodItemCard", "item", "Lcom/nammahomestay/app/data/model/FoodItem;", "categoryLabel", "", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "isExpanded", "", "onToggleExpand", "Lkotlin/Function0;", "onUpdate", "Lkotlin/Function2;", "onPriceChange", "Lkotlin/Function1;", "onVegToggle", "onSave", "onRemove", "placeholder", "MealListSection", "title", "items", "", "expandedItemIds", "", "onAdd", "Lkotlin/Function3;", "onToggleVeg", "MenuImmersiveHeader", "PropertySelector", "uiState", "Lcom/nammahomestay/app/ui/provider/menu/DailyMenuUiState;", "onSelect", "Lcom/nammahomestay/app/data/model/HomeStay;", "app_debug"})
public final class DailyMenuScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void DailyMenuScreen(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavHostController navController, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.ui.provider.menu.DailyMenuViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void MenuImmersiveHeader(androidx.navigation.NavHostController navController) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PropertySelector(com.nammahomestay.app.ui.provider.menu.DailyMenuUiState uiState, kotlin.jvm.functions.Function1<? super com.nammahomestay.app.data.model.HomeStay, kotlin.Unit> onSelect) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void MealListSection(java.lang.String title, androidx.compose.ui.graphics.vector.ImageVector icon, java.util.List<com.nammahomestay.app.data.model.FoodItem> items, java.util.Set<java.lang.String> expandedItemIds, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onToggleExpand, java.lang.String placeholder, kotlin.jvm.functions.Function0<kotlin.Unit> onAdd, kotlin.jvm.functions.Function3<? super java.lang.String, ? super java.lang.String, ? super java.lang.String, kotlin.Unit> onUpdate, kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.String, kotlin.Unit> onPriceChange, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onToggleVeg, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSave, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onRemove) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void FoodItemCard(com.nammahomestay.app.data.model.FoodItem item, java.lang.String categoryLabel, androidx.compose.ui.graphics.vector.ImageVector icon, boolean isExpanded, kotlin.jvm.functions.Function0<kotlin.Unit> onToggleExpand, kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.String, kotlin.Unit> onUpdate, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onPriceChange, kotlin.jvm.functions.Function0<kotlin.Unit> onVegToggle, kotlin.jvm.functions.Function0<kotlin.Unit> onSave, kotlin.jvm.functions.Function0<kotlin.Unit> onRemove, java.lang.String placeholder) {
    }
}