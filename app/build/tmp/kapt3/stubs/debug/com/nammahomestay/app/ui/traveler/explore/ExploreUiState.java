package com.nammahomestay.app.ui.traveler.explore;

import androidx.lifecycle.ViewModel;
import com.nammahomestay.app.data.model.HomeStay;
import com.nammahomestay.app.data.model.Review;
import com.nammahomestay.app.data.repository.AuthRepository;
import com.nammahomestay.app.data.repository.BookingRepository;
import com.nammahomestay.app.data.repository.HomeStayRepository;
import com.nammahomestay.app.util.Resource;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;
import kotlin.math.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b%\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B\u00a5\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005\u0012\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\f\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0010\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\n\u0012\b\b\u0002\u0010\u0012\u001a\u00020\n\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0014\u0012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0014\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0017J\t\u0010(\u001a\u00020\u0003H\u00c6\u0003J\t\u0010)\u001a\u00020\nH\u00c6\u0003J\u0010\u0010*\u001a\u0004\u0018\u00010\u0014H\u00c6\u0003\u00a2\u0006\u0002\u0010%J\u0010\u0010+\u001a\u0004\u0018\u00010\u0014H\u00c6\u0003\u00a2\u0006\u0002\u0010%J\t\u0010,\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\u000f\u0010.\u001a\b\u0012\u0004\u0012\u00020\b0\u0005H\u00c6\u0003J\u000f\u0010/\u001a\b\u0012\u0004\u0012\u00020\n0\u0005H\u00c6\u0003J\u000f\u00100\u001a\b\u0012\u0004\u0012\u00020\n0\fH\u00c6\u0003J\t\u00101\u001a\u00020\u0003H\u00c6\u0003J\t\u00102\u001a\u00020\u0003H\u00c6\u0003J\t\u00103\u001a\u00020\u0010H\u00c6\u0003J\u000b\u00104\u001a\u0004\u0018\u00010\nH\u00c6\u0003J\u00ae\u0001\u00105\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00052\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00052\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\f2\b\b\u0002\u0010\r\u001a\u00020\u00032\b\b\u0002\u0010\u000e\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\u00102\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\n2\b\b\u0002\u0010\u0012\u001a\u00020\n2\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u00142\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u00142\b\b\u0002\u0010\u0016\u001a\u00020\u0003H\u00c6\u0001\u00a2\u0006\u0002\u00106J\u0013\u00107\u001a\u00020\u00032\b\u00108\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00109\u001a\u00020:H\u00d6\u0001J\t\u0010;\u001a\u00020\nH\u00d6\u0001R\u0013\u0010\u0011\u001a\u0004\u0018\u00010\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u0016\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u001cR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u001cR\u0011\u0010\r\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u001cR\u0011\u0010\u000e\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u001cR\u0011\u0010\u000f\u001a\u00020\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u0012\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0019R\u0017\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\f\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001bR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u001bR\u0015\u0010\u0013\u001a\u0004\u0018\u00010\u0014\u00a2\u0006\n\n\u0002\u0010&\u001a\u0004\b$\u0010%R\u0015\u0010\u0015\u001a\u0004\u0018\u00010\u0014\u00a2\u0006\n\n\u0002\u0010&\u001a\u0004\b\'\u0010%\u00a8\u0006<"}, d2 = {"Lcom/nammahomestay/app/ui/traveler/explore/ExploreUiState;", "", "isLoading", "", "homeStays", "", "Lcom/nammahomestay/app/data/model/HomeStay;", "selectedStayReviews", "Lcom/nammahomestay/app/data/model/Review;", "suggestions", "", "selectedCategories", "", "isNearestSelected", "isTopRatedSelected", "priceSort", "Lcom/nammahomestay/app/ui/traveler/explore/PriceSort;", "error", "searchQuery", "userLat", "", "userLon", "isCheckedInAtCurrent", "(ZLjava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/Set;ZZLcom/nammahomestay/app/ui/traveler/explore/PriceSort;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Z)V", "getError", "()Ljava/lang/String;", "getHomeStays", "()Ljava/util/List;", "()Z", "getPriceSort", "()Lcom/nammahomestay/app/ui/traveler/explore/PriceSort;", "getSearchQuery", "getSelectedCategories", "()Ljava/util/Set;", "getSelectedStayReviews", "getSuggestions", "getUserLat", "()Ljava/lang/Double;", "Ljava/lang/Double;", "getUserLon", "component1", "component10", "component11", "component12", "component13", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(ZLjava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/Set;ZZLcom/nammahomestay/app/ui/traveler/explore/PriceSort;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Double;Ljava/lang/Double;Z)Lcom/nammahomestay/app/ui/traveler/explore/ExploreUiState;", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class ExploreUiState {
    private final boolean isLoading = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.nammahomestay.app.data.model.HomeStay> homeStays = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.nammahomestay.app.data.model.Review> selectedStayReviews = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> suggestions = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Set<java.lang.String> selectedCategories = null;
    private final boolean isNearestSelected = false;
    private final boolean isTopRatedSelected = false;
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.ui.traveler.explore.PriceSort priceSort = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String error = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String searchQuery = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double userLat = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Double userLon = null;
    private final boolean isCheckedInAtCurrent = false;
    
    public ExploreUiState(boolean isLoading, @org.jetbrains.annotations.NotNull()
    java.util.List<com.nammahomestay.app.data.model.HomeStay> homeStays, @org.jetbrains.annotations.NotNull()
    java.util.List<com.nammahomestay.app.data.model.Review> selectedStayReviews, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> suggestions, @org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.String> selectedCategories, boolean isNearestSelected, boolean isTopRatedSelected, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.ui.traveler.explore.PriceSort priceSort, @org.jetbrains.annotations.Nullable()
    java.lang.String error, @org.jetbrains.annotations.NotNull()
    java.lang.String searchQuery, @org.jetbrains.annotations.Nullable()
    java.lang.Double userLat, @org.jetbrains.annotations.Nullable()
    java.lang.Double userLon, boolean isCheckedInAtCurrent) {
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
    public final java.util.List<com.nammahomestay.app.data.model.Review> getSelectedStayReviews() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getSuggestions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<java.lang.String> getSelectedCategories() {
        return null;
    }
    
    public final boolean isNearestSelected() {
        return false;
    }
    
    public final boolean isTopRatedSelected() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.nammahomestay.app.ui.traveler.explore.PriceSort getPriceSort() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getError() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getSearchQuery() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getUserLat() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double getUserLon() {
        return null;
    }
    
    public final boolean isCheckedInAtCurrent() {
        return false;
    }
    
    public ExploreUiState() {
        super();
    }
    
    public final boolean component1() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Double component12() {
        return null;
    }
    
    public final boolean component13() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.nammahomestay.app.data.model.HomeStay> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.nammahomestay.app.data.model.Review> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<java.lang.String> component5() {
        return null;
    }
    
    public final boolean component6() {
        return false;
    }
    
    public final boolean component7() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.nammahomestay.app.ui.traveler.explore.PriceSort component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.nammahomestay.app.ui.traveler.explore.ExploreUiState copy(boolean isLoading, @org.jetbrains.annotations.NotNull()
    java.util.List<com.nammahomestay.app.data.model.HomeStay> homeStays, @org.jetbrains.annotations.NotNull()
    java.util.List<com.nammahomestay.app.data.model.Review> selectedStayReviews, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> suggestions, @org.jetbrains.annotations.NotNull()
    java.util.Set<java.lang.String> selectedCategories, boolean isNearestSelected, boolean isTopRatedSelected, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.ui.traveler.explore.PriceSort priceSort, @org.jetbrains.annotations.Nullable()
    java.lang.String error, @org.jetbrains.annotations.NotNull()
    java.lang.String searchQuery, @org.jetbrains.annotations.Nullable()
    java.lang.Double userLat, @org.jetbrains.annotations.Nullable()
    java.lang.Double userLon, boolean isCheckedInAtCurrent) {
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