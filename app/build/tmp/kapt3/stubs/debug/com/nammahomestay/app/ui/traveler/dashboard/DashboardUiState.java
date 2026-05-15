package com.nammahomestay.app.ui.traveler.dashboard;

import androidx.lifecycle.ViewModel;
import com.nammahomestay.app.data.model.HomeStay;
import com.nammahomestay.app.data.model.Booking;
import com.nammahomestay.app.data.repository.AuthRepository;
import com.nammahomestay.app.data.repository.BookingRepository;
import com.nammahomestay.app.data.repository.HomeStayRepository;
import com.nammahomestay.app.util.Resource;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001BO\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u00a2\u0006\u0002\u0010\rJ\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005H\u00c6\u0003J\u000f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00070\u0005H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\nH\u00c6\u0003J\u000b\u0010\u001c\u001a\u0004\u0018\u00010\fH\u00c6\u0003JS\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u00052\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00c6\u0001J\u0013\u0010\u001e\u001a\u00020\n2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010 \u001a\u00020!H\u00d6\u0001J\t\u0010\"\u001a\u00020\u0003H\u00d6\u0001R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0011\u00a8\u0006#"}, d2 = {"Lcom/nammahomestay/app/ui/traveler/dashboard/DashboardUiState;", "", "searchQuery", "", "suggestions", "", "featuredStays", "Lcom/nammahomestay/app/data/model/HomeStay;", "selectedCategory", "isLoading", "", "activeStay", "Lcom/nammahomestay/app/data/model/Booking;", "(Ljava/lang/String;Ljava/util/List;Ljava/util/List;Ljava/lang/String;ZLcom/nammahomestay/app/data/model/Booking;)V", "getActiveStay", "()Lcom/nammahomestay/app/data/model/Booking;", "getFeaturedStays", "()Ljava/util/List;", "()Z", "getSearchQuery", "()Ljava/lang/String;", "getSelectedCategory", "getSuggestions", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class DashboardUiState {
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String searchQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> suggestions = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.nammahomestay.app.data.model.HomeStay> featuredStays = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String selectedCategory = null;
    private final boolean isLoading = false;
    @org.jetbrains.annotations.Nullable()
    private final com.nammahomestay.app.data.model.Booking activeStay = null;
    
    public DashboardUiState(@org.jetbrains.annotations.NotNull()
    java.lang.String searchQuery, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> suggestions, @org.jetbrains.annotations.NotNull()
    java.util.List<com.nammahomestay.app.data.model.HomeStay> featuredStays, @org.jetbrains.annotations.NotNull()
    java.lang.String selectedCategory, boolean isLoading, @org.jetbrains.annotations.Nullable()
    com.nammahomestay.app.data.model.Booking activeStay) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSearchQuery() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getSuggestions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.nammahomestay.app.data.model.HomeStay> getFeaturedStays() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSelectedCategory() {
        return null;
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.nammahomestay.app.data.model.Booking getActiveStay() {
        return null;
    }
    
    public DashboardUiState() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.nammahomestay.app.data.model.HomeStay> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    public final boolean component5() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.nammahomestay.app.data.model.Booking component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.nammahomestay.app.ui.traveler.dashboard.DashboardUiState copy(@org.jetbrains.annotations.NotNull()
    java.lang.String searchQuery, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> suggestions, @org.jetbrains.annotations.NotNull()
    java.util.List<com.nammahomestay.app.data.model.HomeStay> featuredStays, @org.jetbrains.annotations.NotNull()
    java.lang.String selectedCategory, boolean isLoading, @org.jetbrains.annotations.Nullable()
    com.nammahomestay.app.data.model.Booking activeStay) {
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