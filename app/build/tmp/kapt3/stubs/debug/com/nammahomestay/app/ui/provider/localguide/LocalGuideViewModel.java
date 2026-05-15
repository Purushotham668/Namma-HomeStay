package com.nammahomestay.app.ui.provider.localguide;

import androidx.lifecycle.ViewModel;
import com.nammahomestay.app.data.model.LocalSpot;
import com.nammahomestay.app.data.repository.AuthRepository;
import com.nammahomestay.app.data.repository.LocalSpotRepository;
import com.nammahomestay.app.util.Resource;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import java.util.UUID;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u001e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0011J\u0006\u0010\u0014\u001a\u00020\u000fJ\u0006\u0010\u0015\u001a\u00020\u000fJ\b\u0010\u0016\u001a\u00020\u000fH\u0002J\u000e\u0010\u0017\u001a\u00020\u000f2\u0006\u0010\u0018\u001a\u00020\u0011J\u0016\u0010\u0019\u001a\u00020\u000f2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001bH\u0002R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r\u00a8\u0006\u001d"}, d2 = {"Lcom/nammahomestay/app/ui/provider/localguide/LocalGuideViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/nammahomestay/app/data/repository/LocalSpotRepository;", "authRepository", "Lcom/nammahomestay/app/data/repository/AuthRepository;", "(Lcom/nammahomestay/app/data/repository/LocalSpotRepository;Lcom/nammahomestay/app/data/repository/AuthRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/nammahomestay/app/ui/provider/localguide/LocalGuideUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "addSpot", "", "name", "", "distance", "description", "clearError", "clearSavedState", "loadSpots", "removeSpot", "id", "saveSpots", "spots", "", "Lcom/nammahomestay/app/data/model/LocalSpot;", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class LocalGuideViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.LocalSpotRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.nammahomestay.app.ui.provider.localguide.LocalGuideUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.nammahomestay.app.ui.provider.localguide.LocalGuideUiState> uiState = null;
    
    @javax.inject.Inject()
    public LocalGuideViewModel(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.LocalSpotRepository repository, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.AuthRepository authRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.nammahomestay.app.ui.provider.localguide.LocalGuideUiState> getUiState() {
        return null;
    }
    
    private final void loadSpots() {
    }
    
    public final void addSpot(@org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String distance, @org.jetbrains.annotations.NotNull()
    java.lang.String description) {
    }
    
    public final void removeSpot(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
    }
    
    private final void saveSpots(java.util.List<com.nammahomestay.app.data.model.LocalSpot> spots) {
    }
    
    public final void clearSavedState() {
    }
    
    public final void clearError() {
    }
}