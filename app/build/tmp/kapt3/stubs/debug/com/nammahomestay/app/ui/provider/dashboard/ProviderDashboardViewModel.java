package com.nammahomestay.app.ui.provider.dashboard;

import androidx.lifecycle.ViewModel;
import com.nammahomestay.app.data.repository.AuthRepository;
import com.nammahomestay.app.data.repository.HomeStayRepository;
import com.nammahomestay.app.data.repository.InquiryRepository;
import com.nammahomestay.app.util.Resource;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\b\u0010\u0018\u001a\u00020\u0019H\u0002J\b\u0010\u001a\u001a\u00020\u0019H\u0002J\u0010\u0010\u001b\u001a\u00020\u00192\u0006\u0010\u001c\u001a\u00020\u0010H\u0002R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00100\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\r0\u0015\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2 = {"Lcom/nammahomestay/app/ui/provider/dashboard/ProviderDashboardViewModel;", "Landroidx/lifecycle/ViewModel;", "homeStayRepository", "Lcom/nammahomestay/app/data/repository/HomeStayRepository;", "userRepository", "Lcom/nammahomestay/app/data/repository/UserRepository;", "inquiryRepository", "Lcom/nammahomestay/app/data/repository/InquiryRepository;", "authRepository", "Lcom/nammahomestay/app/data/repository/AuthRepository;", "(Lcom/nammahomestay/app/data/repository/HomeStayRepository;Lcom/nammahomestay/app/data/repository/UserRepository;Lcom/nammahomestay/app/data/repository/InquiryRepository;Lcom/nammahomestay/app/data/repository/AuthRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/nammahomestay/app/ui/provider/dashboard/DashboardStatsUiState;", "inquiryCountsMap", "", "", "", "observedInquiryIds", "", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "loadStats", "", "observeAccountName", "observeInquiriesForProperty", "homestayId", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ProviderDashboardViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.HomeStayRepository homeStayRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.UserRepository userRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.InquiryRepository inquiryRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.nammahomestay.app.ui.provider.dashboard.DashboardStatsUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.nammahomestay.app.ui.provider.dashboard.DashboardStatsUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Set<java.lang.String> observedInquiryIds = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, java.lang.Integer> inquiryCountsMap = null;
    
    @javax.inject.Inject()
    public ProviderDashboardViewModel(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.HomeStayRepository homeStayRepository, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.UserRepository userRepository, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.InquiryRepository inquiryRepository, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.AuthRepository authRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.nammahomestay.app.ui.provider.dashboard.DashboardStatsUiState> getUiState() {
        return null;
    }
    
    private final void observeAccountName() {
    }
    
    private final void loadStats() {
    }
    
    private final void observeInquiriesForProperty(java.lang.String homestayId) {
    }
}