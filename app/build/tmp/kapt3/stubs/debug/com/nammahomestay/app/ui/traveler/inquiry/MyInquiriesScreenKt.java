package com.nammahomestay.app.ui.traveler.inquiry;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.navigation.NavHostController;
import com.nammahomestay.app.data.model.Inquiry;
import com.nammahomestay.app.data.repository.AuthRepository;
import com.nammahomestay.app.data.repository.InquiryRepository;
import com.nammahomestay.app.util.Resource;
import com.nammahomestay.app.ui.navigation.Screen;
import com.nammahomestay.app.ui.theme.*;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001a\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u0007\u001a\u001e\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0007\u00a8\u0006\u000b"}, d2 = {"MyInquiriesScreen", "", "navController", "Landroidx/navigation/NavHostController;", "viewModel", "Lcom/nammahomestay/app/ui/traveler/inquiry/MyInquiriesViewModel;", "TravelerInquiryItem", "inquiry", "Lcom/nammahomestay/app/data/model/Inquiry;", "onClick", "Lkotlin/Function0;", "app_debug"})
public final class MyInquiriesScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void MyInquiriesScreen(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavHostController navController, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.ui.traveler.inquiry.MyInquiriesViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    public static final void TravelerInquiryItem(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.model.Inquiry inquiry, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
}