package com.nammahomestay.app.ui.provider.profile;

import android.net.Uri;
import androidx.lifecycle.ViewModel;
import com.nammahomestay.app.data.model.HomeStay;
import com.nammahomestay.app.data.model.Pricing;
import com.nammahomestay.app.data.repository.AuthRepository;
import com.nammahomestay.app.data.repository.HomeStayRepository;
import com.nammahomestay.app.util.Resource;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u001e\b\u0007\u0018\u00002\u00020\u0001B\'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0014\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018J\u0006\u0010\u001a\u001a\u00020\u0016J\u0006\u0010\u001b\u001a\u00020\u0016J\u0006\u0010\u001c\u001a\u00020\u0016J\u0006\u0010\u001d\u001a\u00020\u0016J\u0006\u0010\u001e\u001a\u00020\u0016J\u000e\u0010\u001f\u001a\u00020\u00162\u0006\u0010 \u001a\u00020\u0010J\u0006\u0010!\u001a\u00020\u0016J\b\u0010\"\u001a\u00020\u0016H\u0002J\u0010\u0010#\u001a\u00020\u00162\u0006\u0010$\u001a\u00020\u0010H\u0002J\u000e\u0010%\u001a\u00020\u00162\u0006\u0010&\u001a\u00020\u0010J\u000e\u0010\'\u001a\u00020\u00162\u0006\u0010(\u001a\u00020\u0019J\u000e\u0010)\u001a\u00020\u00162\u0006\u0010*\u001a\u00020\u0010J\u0006\u0010+\u001a\u00020\u0016J\u000e\u0010,\u001a\u00020\u00162\u0006\u0010-\u001a\u00020.J\u001e\u0010/\u001a\u00020\u00162\u0006\u00100\u001a\u00020\u00102\f\u00101\u001a\b\u0012\u0004\u0012\u0002020\u0018H\u0002J\u0016\u00103\u001a\u00020\u00162\u0006\u0010 \u001a\u00020\u00102\u0006\u00104\u001a\u000205J\u0016\u00106\u001a\u00020\u00162\u0006\u0010 \u001a\u00020\u00102\u0006\u00107\u001a\u000205J\u000e\u00108\u001a\u00020\u00162\u0006\u00109\u001a\u00020\u0010J\u000e\u0010:\u001a\u00020\u00162\u0006\u0010;\u001a\u00020\u0010J\u0016\u0010<\u001a\u00020\u00162\u0006\u0010=\u001a\u00020\u00102\u0006\u0010>\u001a\u000205J\u000e\u0010?\u001a\u00020\u00162\u0006\u0010@\u001a\u00020\u0010J\u000e\u0010A\u001a\u00020\u00162\u0006\u0010B\u001a\u00020\u0010J\u000e\u0010C\u001a\u00020\u00162\u0006\u0010D\u001a\u00020\u0010J\u000e\u0010E\u001a\u00020\u00162\u0006\u0010F\u001a\u00020\u0010J\u000e\u0010G\u001a\u00020\u00162\u0006\u0010H\u001a\u00020\u0010J\u000e\u0010I\u001a\u00020\u00162\u0006\u0010J\u001a\u00020\u0010J\u000e\u0010K\u001a\u00020\u00162\u0006\u0010L\u001a\u00020\u0010J\u000e\u0010M\u001a\u00020\u00162\u0006\u0010L\u001a\u00020\u0010J\u000e\u0010N\u001a\u00020\u00162\u0006\u0010O\u001a\u000202J\u000e\u0010P\u001a\u00020\u00162\u0006\u0010Q\u001a\u00020\u0010J\u0016\u0010R\u001a\u00020\u00162\u0006\u0010*\u001a\u00020\u00102\u0006\u0010(\u001a\u00020\u0019R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\r0\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006S"}, d2 = {"Lcom/nammahomestay/app/ui/provider/profile/HomeProfileViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/nammahomestay/app/data/repository/HomeStayRepository;", "userRepository", "Lcom/nammahomestay/app/data/repository/UserRepository;", "inquiryRepository", "Lcom/nammahomestay/app/data/repository/InquiryRepository;", "authRepository", "Lcom/nammahomestay/app/data/repository/AuthRepository;", "(Lcom/nammahomestay/app/data/repository/HomeStayRepository;Lcom/nammahomestay/app/data/repository/UserRepository;Lcom/nammahomestay/app/data/repository/InquiryRepository;Lcom/nammahomestay/app/data/repository/AuthRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/nammahomestay/app/ui/provider/profile/HomeProfileUiState;", "observedInquiryIds", "", "", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "addPhotos", "", "uris", "", "Landroid/net/Uri;", "addRoom", "backToList", "clearError", "clearSavedState", "createNewProfile", "deleteProperty", "homeStayId", "loadProfiles", "observeAccountName", "observeInquiries", "homestayId", "removeExistingPhoto", "url", "removePhoto", "uri", "removeRoom", "roomId", "saveProfile", "selectForEdit", "homeStay", "Lcom/nammahomestay/app/data/model/HomeStay;", "silentSaveRooms", "id", "rooms", "Lcom/nammahomestay/app/data/model/Room;", "toggleAvailability", "isAvailable", "", "toggleOccupancy", "isOccupied", "updateAddress", "address", "updateCategory", "category", "updateChecklist", "key", "value", "updateContact", "contact", "updateDescription", "desc", "updateLatitude", "lat", "updateLongitude", "lon", "updateName", "name", "updateOwnerName", "owner", "updatePricePerDay", "price", "updatePricePerPerson", "updateRoom", "updatedRoom", "updateRoomsAvailable", "count", "uploadRoomPhoto", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class HomeProfileViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.HomeStayRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.UserRepository userRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.InquiryRepository inquiryRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.nammahomestay.app.ui.provider.profile.HomeProfileUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.nammahomestay.app.ui.provider.profile.HomeProfileUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Set<java.lang.String> observedInquiryIds = null;
    
    @javax.inject.Inject()
    public HomeProfileViewModel(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.HomeStayRepository repository, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.UserRepository userRepository, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.InquiryRepository inquiryRepository, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.AuthRepository authRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.nammahomestay.app.ui.provider.profile.HomeProfileUiState> getUiState() {
        return null;
    }
    
    private final void observeAccountName() {
    }
    
    public final void loadProfiles() {
    }
    
    private final void observeInquiries(java.lang.String homestayId) {
    }
    
    public final void selectForEdit(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.model.HomeStay homeStay) {
    }
    
    public final void createNewProfile() {
    }
    
    public final void backToList() {
    }
    
    public final void updateName(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
    }
    
    public final void updateOwnerName(@org.jetbrains.annotations.NotNull()
    java.lang.String owner) {
    }
    
    public final void updateContact(@org.jetbrains.annotations.NotNull()
    java.lang.String contact) {
    }
    
    public final void updateAddress(@org.jetbrains.annotations.NotNull()
    java.lang.String address) {
    }
    
    public final void updateDescription(@org.jetbrains.annotations.NotNull()
    java.lang.String desc) {
    }
    
    public final void updatePricePerDay(@org.jetbrains.annotations.NotNull()
    java.lang.String price) {
    }
    
    public final void updatePricePerPerson(@org.jetbrains.annotations.NotNull()
    java.lang.String price) {
    }
    
    public final void updateRoomsAvailable(@org.jetbrains.annotations.NotNull()
    java.lang.String count) {
    }
    
    public final void updateCategory(@org.jetbrains.annotations.NotNull()
    java.lang.String category) {
    }
    
    public final void updateLatitude(@org.jetbrains.annotations.NotNull()
    java.lang.String lat) {
    }
    
    public final void updateLongitude(@org.jetbrains.annotations.NotNull()
    java.lang.String lon) {
    }
    
    public final void updateChecklist(@org.jetbrains.annotations.NotNull()
    java.lang.String key, boolean value) {
    }
    
    public final void addPhotos(@org.jetbrains.annotations.NotNull()
    java.util.List<? extends android.net.Uri> uris) {
    }
    
    public final void toggleOccupancy(@org.jetbrains.annotations.NotNull()
    java.lang.String homeStayId, boolean isOccupied) {
    }
    
    public final void toggleAvailability(@org.jetbrains.annotations.NotNull()
    java.lang.String homeStayId, boolean isAvailable) {
    }
    
    public final void removePhoto(@org.jetbrains.annotations.NotNull()
    android.net.Uri uri) {
    }
    
    public final void addRoom() {
    }
    
    public final void updateRoom(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.model.Room updatedRoom) {
    }
    
    public final void removeExistingPhoto(@org.jetbrains.annotations.NotNull()
    java.lang.String url) {
    }
    
    public final void removeRoom(@org.jetbrains.annotations.NotNull()
    java.lang.String roomId) {
    }
    
    public final void uploadRoomPhoto(@org.jetbrains.annotations.NotNull()
    java.lang.String roomId, @org.jetbrains.annotations.NotNull()
    android.net.Uri uri) {
    }
    
    private final void silentSaveRooms(java.lang.String id, java.util.List<com.nammahomestay.app.data.model.Room> rooms) {
    }
    
    public final void saveProfile() {
    }
    
    public final void deleteProperty(@org.jetbrains.annotations.NotNull()
    java.lang.String homeStayId) {
    }
    
    public final void clearSavedState() {
    }
    
    public final void clearError() {
    }
}