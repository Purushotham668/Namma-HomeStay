package com.nammahomestay.app.ui.provider.verification;

import androidx.lifecycle.ViewModel;
import com.nammahomestay.app.data.repository.BookingRepository;
import com.nammahomestay.app.data.repository.FoodRepository;
import com.nammahomestay.app.util.Resource;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u0010\u001a\u00020\u0011J\u0010\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u000e\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u0014J\u0010\u0010\u0017\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0006\u0010\u0018\u001a\u00020\u0011R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\t0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0019"}, d2 = {"Lcom/nammahomestay/app/ui/provider/verification/QRScannerViewModel;", "Landroidx/lifecycle/ViewModel;", "bookingRepository", "Lcom/nammahomestay/app/data/repository/BookingRepository;", "foodRepository", "Lcom/nammahomestay/app/data/repository/FoodRepository;", "(Lcom/nammahomestay/app/data/repository/BookingRepository;Lcom/nammahomestay/app/data/repository/FoodRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/nammahomestay/app/ui/provider/verification/QRScannerUiState;", "hasProcessed", "", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "markFoodAsServed", "", "processFoodQR", "bookingId", "", "processQRCode", "qrData", "processStayQR", "resumeScanning", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class QRScannerViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.BookingRepository bookingRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.FoodRepository foodRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.nammahomestay.app.ui.provider.verification.QRScannerUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.nammahomestay.app.ui.provider.verification.QRScannerUiState> uiState = null;
    private boolean hasProcessed = false;
    
    @javax.inject.Inject()
    public QRScannerViewModel(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.BookingRepository bookingRepository, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.FoodRepository foodRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.nammahomestay.app.ui.provider.verification.QRScannerUiState> getUiState() {
        return null;
    }
    
    public final void processQRCode(@org.jetbrains.annotations.NotNull()
    java.lang.String qrData) {
    }
    
    private final void processStayQR(java.lang.String bookingId) {
    }
    
    private final void processFoodQR(java.lang.String bookingId) {
    }
    
    public final void markFoodAsServed() {
    }
    
    public final void resumeScanning() {
    }
}