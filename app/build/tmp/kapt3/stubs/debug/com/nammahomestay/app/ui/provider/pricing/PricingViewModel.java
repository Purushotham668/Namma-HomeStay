package com.nammahomestay.app.ui.provider.pricing;

import androidx.lifecycle.ViewModel;
import com.nammahomestay.app.data.model.Pricing;
import com.nammahomestay.app.data.model.HomeStay;
import com.nammahomestay.app.data.repository.AuthRepository;
import com.nammahomestay.app.data.repository.HomeStayRepository;
import com.nammahomestay.app.util.Resource;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0010\u001a\u00020\u000fJ\u0006\u0010\u0011\u001a\u00020\u000fJ\u0006\u0010\u0012\u001a\u00020\u000fJ\u000e\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0015J\u000e\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0017\u001a\u00020\u0018J\u000e\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u001bJ\u000e\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u001bJ\u000e\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u001a\u001a\u00020\u001bR\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u001e"}, d2 = {"Lcom/nammahomestay/app/ui/provider/pricing/PricingViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/nammahomestay/app/data/repository/HomeStayRepository;", "authRepository", "Lcom/nammahomestay/app/data/repository/AuthRepository;", "(Lcom/nammahomestay/app/data/repository/HomeStayRepository;Lcom/nammahomestay/app/data/repository/AuthRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/nammahomestay/app/ui/provider/pricing/PricingUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "clearError", "", "clearSavedState", "loadProperties", "savePricing", "selectProperty", "homeStay", "Lcom/nammahomestay/app/data/model/HomeStay;", "updateAvailability", "isAvailable", "", "updatePricePerDay", "text", "", "updatePricePerPerson", "updateRoomsAvailable", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class PricingViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.HomeStayRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.nammahomestay.app.ui.provider.pricing.PricingUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.nammahomestay.app.ui.provider.pricing.PricingUiState> uiState = null;
    
    @javax.inject.Inject()
    public PricingViewModel(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.HomeStayRepository repository, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.AuthRepository authRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.nammahomestay.app.ui.provider.pricing.PricingUiState> getUiState() {
        return null;
    }
    
    public final void loadProperties() {
    }
    
    public final void selectProperty(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.model.HomeStay homeStay) {
    }
    
    public final void updatePricePerDay(@org.jetbrains.annotations.NotNull()
    java.lang.String text) {
    }
    
    public final void updatePricePerPerson(@org.jetbrains.annotations.NotNull()
    java.lang.String text) {
    }
    
    public final void updateRoomsAvailable(@org.jetbrains.annotations.NotNull()
    java.lang.String text) {
    }
    
    public final void updateAvailability(boolean isAvailable) {
    }
    
    public final void savePricing() {
    }
    
    public final void clearSavedState() {
    }
    
    public final void clearError() {
    }
}