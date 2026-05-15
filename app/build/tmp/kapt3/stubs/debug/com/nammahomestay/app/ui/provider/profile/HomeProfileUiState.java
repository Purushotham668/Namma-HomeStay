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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\b\u001c\b\u0087\b\u0018\u00002\u00020\u0001By\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0005\u0012\u0014\b\u0002\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00100\u000f\u0012\b\b\u0002\u0010\u0011\u001a\u00020\t\u00a2\u0006\u0002\u0010\u0012J\t\u0010\u001e\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\t\u0010 \u001a\u00020\u0006H\u00c6\u0003J\u000b\u0010!\u001a\u0004\u0018\u00010\tH\u00c6\u0003J\t\u0010\"\u001a\u00020\u0003H\u00c6\u0003J\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010$\u001a\b\u0012\u0004\u0012\u00020\r0\u0005H\u00c6\u0003J\u0015\u0010%\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00100\u000fH\u00c6\u0003J\t\u0010&\u001a\u00020\tH\u00c6\u0003J}\u0010\'\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00062\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t2\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\u000e\b\u0002\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u00052\u0014\b\u0002\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00100\u000f2\b\b\u0002\u0010\u0011\u001a\u00020\tH\u00c6\u0001J\u0013\u0010(\u001a\u00020\u00032\b\u0010)\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010*\u001a\u00020\u0010H\u00d6\u0001J\t\u0010+\u001a\u00020\tH\u00d6\u0001R\u0011\u0010\u0011\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\b\u001a\u0004\u0018\u00010\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014R\u0011\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u001d\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00100\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u001cR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u001cR\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u001cR\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\r0\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0019\u00a8\u0006,"}, d2 = {"Lcom/nammahomestay/app/ui/provider/profile/HomeProfileUiState;", "", "isLoading", "", "homeStays", "", "Lcom/nammahomestay/app/data/model/HomeStay;", "homeStay", "error", "", "isSaved", "isEditing", "selectedPhotos", "Landroid/net/Uri;", "inquiryCounts", "", "", "accountName", "(ZLjava/util/List;Lcom/nammahomestay/app/data/model/HomeStay;Ljava/lang/String;ZZLjava/util/List;Ljava/util/Map;Ljava/lang/String;)V", "getAccountName", "()Ljava/lang/String;", "getError", "getHomeStay", "()Lcom/nammahomestay/app/data/model/HomeStay;", "getHomeStays", "()Ljava/util/List;", "getInquiryCounts", "()Ljava/util/Map;", "()Z", "getSelectedPhotos", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class HomeProfileUiState {
    private final boolean isLoading = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.nammahomestay.app.data.model.HomeStay> homeStays = null;
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.model.HomeStay homeStay = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String error = null;
    private final boolean isSaved = false;
    private final boolean isEditing = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<android.net.Uri> selectedPhotos = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, java.lang.Integer> inquiryCounts = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String accountName = null;
    
    public HomeProfileUiState(boolean isLoading, @org.jetbrains.annotations.NotNull()
    java.util.List<com.nammahomestay.app.data.model.HomeStay> homeStays, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.model.HomeStay homeStay, @org.jetbrains.annotations.Nullable()
    java.lang.String error, boolean isSaved, boolean isEditing, @org.jetbrains.annotations.NotNull()
    java.util.List<? extends android.net.Uri> selectedPhotos, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.Integer> inquiryCounts, @org.jetbrains.annotations.NotNull()
    java.lang.String accountName) {
        super();
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.nammahomestay.app.data.model.HomeStay> getHomeStays() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.nammahomestay.app.data.model.HomeStay getHomeStay() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getError() {
        return null;
    }
    
    public final boolean isSaved() {
        return false;
    }
    
    public final boolean isEditing() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<android.net.Uri> getSelectedPhotos() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, java.lang.Integer> getInquiryCounts() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAccountName() {
        return null;
    }
    
    public HomeProfileUiState() {
        super();
    }
    
    public final boolean component1() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.nammahomestay.app.data.model.HomeStay> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.nammahomestay.app.data.model.HomeStay component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component4() {
        return null;
    }
    
    public final boolean component5() {
        return false;
    }
    
    public final boolean component6() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<android.net.Uri> component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, java.lang.Integer> component8() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.nammahomestay.app.ui.provider.profile.HomeProfileUiState copy(boolean isLoading, @org.jetbrains.annotations.NotNull()
    java.util.List<com.nammahomestay.app.data.model.HomeStay> homeStays, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.model.HomeStay homeStay, @org.jetbrains.annotations.Nullable()
    java.lang.String error, boolean isSaved, boolean isEditing, @org.jetbrains.annotations.NotNull()
    java.util.List<? extends android.net.Uri> selectedPhotos, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.Integer> inquiryCounts, @org.jetbrains.annotations.NotNull()
    java.lang.String accountName) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}