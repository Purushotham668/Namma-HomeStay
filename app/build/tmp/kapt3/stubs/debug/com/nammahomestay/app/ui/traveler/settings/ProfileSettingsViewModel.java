package com.nammahomestay.app.ui.traveler.settings;

import androidx.lifecycle.ViewModel;
import com.nammahomestay.app.data.model.User;
import com.nammahomestay.app.data.repository.AuthRepository;
import com.nammahomestay.app.data.repository.UserRepository;
import com.nammahomestay.app.util.Resource;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.FlowPreview;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\t\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u0012\u001a\u00020\u0013J\b\u0010\u0014\u001a\u00020\u0013H\u0002J\u0006\u0010\u0015\u001a\u00020\u0013J\b\u0010\u0016\u001a\u00020\u0013H\u0002J\u000e\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u001b\u001a\u00020\rJ\u000e\u0010\u001c\u001a\u00020\u00132\u0006\u0010\u001d\u001a\u00020\rJ\u000e\u0010\u001e\u001a\u00020\u00132\u0006\u0010\u001f\u001a\u00020\rJ\u000e\u0010 \u001a\u00020\u00132\u0006\u0010!\u001a\u00020\rR\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\""}, d2 = {"Lcom/nammahomestay/app/ui/traveler/settings/ProfileSettingsViewModel;", "Landroidx/lifecycle/ViewModel;", "userRepository", "Lcom/nammahomestay/app/data/repository/UserRepository;", "authRepository", "Lcom/nammahomestay/app/data/repository/AuthRepository;", "(Lcom/nammahomestay/app/data/repository/UserRepository;Lcom/nammahomestay/app/data/repository/AuthRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/nammahomestay/app/ui/traveler/settings/ProfileSettingsUiState;", "autoSaveJob", "Lkotlinx/coroutines/Job;", "currentUid", "", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "clearStatus", "", "listenToProfile", "saveProfile", "scheduleAutoSave", "toggleNotifications", "enabled", "", "updateDob", "dob", "updateFullName", "name", "updateLanguage", "lang", "updatePhoneNumber", "phone", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ProfileSettingsViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.UserRepository userRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.nammahomestay.app.ui.traveler.settings.ProfileSettingsUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.nammahomestay.app.ui.traveler.settings.ProfileSettingsUiState> uiState = null;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job autoSaveJob;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String currentUid = "";
    
    @javax.inject.Inject()
    public ProfileSettingsViewModel(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.UserRepository userRepository, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.AuthRepository authRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.nammahomestay.app.ui.traveler.settings.ProfileSettingsUiState> getUiState() {
        return null;
    }
    
    /**
     * Opens a real-time Firestore snapshot listener.
     * UI will reflect changes from ANY device/source immediately.
     */
    private final void listenToProfile() {
    }
    
    public final void updateFullName(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
    }
    
    public final void updatePhoneNumber(@org.jetbrains.annotations.NotNull()
    java.lang.String phone) {
    }
    
    public final void updateDob(@org.jetbrains.annotations.NotNull()
    java.lang.String dob) {
    }
    
    public final void updateLanguage(@org.jetbrains.annotations.NotNull()
    java.lang.String lang) {
    }
    
    public final void toggleNotifications(boolean enabled) {
    }
    
    /**
     * Schedules a Firebase save 1.5 seconds after the last keystroke.
     * Any subsequent change cancels the previous save and reschedules.
     */
    private final void scheduleAutoSave() {
    }
    
    public final void saveProfile() {
    }
    
    public final void clearStatus() {
    }
}