package com.nammahomestay.app.ui.provider.inquiry;

import androidx.lifecycle.ViewModel;
import com.nammahomestay.app.data.model.Inquiry;
import com.nammahomestay.app.data.repository.AuthRepository;
import com.nammahomestay.app.data.repository.InquiryRepository;
import com.nammahomestay.app.data.repository.HomeStayRepository;
import com.nammahomestay.app.util.Resource;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\b\u0010\u0010\u001a\u00020\u0011H\u0002J\u0006\u0010\u0012\u001a\u00020\u0011J\u0006\u0010\u0013\u001a\u00020\u0011R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0014"}, d2 = {"Lcom/nammahomestay/app/ui/provider/inquiry/InquiryBoxViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/nammahomestay/app/data/repository/InquiryRepository;", "homeStayRepository", "Lcom/nammahomestay/app/data/repository/HomeStayRepository;", "authRepository", "Lcom/nammahomestay/app/data/repository/AuthRepository;", "(Lcom/nammahomestay/app/data/repository/InquiryRepository;Lcom/nammahomestay/app/data/repository/HomeStayRepository;Lcom/nammahomestay/app/data/repository/AuthRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/nammahomestay/app/ui/provider/inquiry/InquiryBoxUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "cleanupAndLoad", "", "clearError", "loadInquiries", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class InquiryBoxViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.InquiryRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.HomeStayRepository homeStayRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.nammahomestay.app.ui.provider.inquiry.InquiryBoxUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.nammahomestay.app.ui.provider.inquiry.InquiryBoxUiState> uiState = null;
    
    @javax.inject.Inject()
    public InquiryBoxViewModel(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.InquiryRepository repository, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.HomeStayRepository homeStayRepository, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.AuthRepository authRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.nammahomestay.app.ui.provider.inquiry.InquiryBoxUiState> getUiState() {
        return null;
    }
    
    private final void cleanupAndLoad() {
    }
    
    public final void loadInquiries() {
    }
    
    public final void clearError() {
    }
}