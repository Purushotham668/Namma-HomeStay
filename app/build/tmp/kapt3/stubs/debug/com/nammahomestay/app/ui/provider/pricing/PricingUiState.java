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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0015\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001BK\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\rJ\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\u000b\u0010\u0019\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\tH\u00c6\u0003J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\u000bH\u00c6\u0003J\t\u0010\u001c\u001a\u00020\u0003H\u00c6\u0003JO\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\b\u001a\u00020\t2\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u000b2\b\b\u0002\u0010\f\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u001e\u001a\u00020\u00032\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010 \u001a\u00020!H\u00d6\u0001J\t\u0010\"\u001a\u00020\u000bH\u00d6\u0001R\u0013\u0010\n\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0012R\u0011\u0010\f\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0012R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006#"}, d2 = {"Lcom/nammahomestay/app/ui/provider/pricing/PricingUiState;", "", "isLoading", "", "homeStays", "", "Lcom/nammahomestay/app/data/model/HomeStay;", "selectedHomeStay", "pricing", "Lcom/nammahomestay/app/data/model/Pricing;", "error", "", "isSaved", "(ZLjava/util/List;Lcom/nammahomestay/app/data/model/HomeStay;Lcom/nammahomestay/app/data/model/Pricing;Ljava/lang/String;Z)V", "getError", "()Ljava/lang/String;", "getHomeStays", "()Ljava/util/List;", "()Z", "getPricing", "()Lcom/nammahomestay/app/data/model/Pricing;", "getSelectedHomeStay", "()Lcom/nammahomestay/app/data/model/HomeStay;", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class PricingUiState {
    private final boolean isLoading = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.nammahomestay.app.data.model.HomeStay> homeStays = null;
    @org.jetbrains.annotations.Nullable()
    private final com.nammahomestay.app.data.model.HomeStay selectedHomeStay = null;
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.model.Pricing pricing = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String error = null;
    private final boolean isSaved = false;
    
    public PricingUiState(boolean isLoading, @org.jetbrains.annotations.NotNull()
    java.util.List<com.nammahomestay.app.data.model.HomeStay> homeStays, @org.jetbrains.annotations.Nullable()
    com.nammahomestay.app.data.model.HomeStay selectedHomeStay, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.model.Pricing pricing, @org.jetbrains.annotations.Nullable()
    java.lang.String error, boolean isSaved) {
        super();
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.nammahomestay.app.data.model.HomeStay> getHomeStays() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.nammahomestay.app.data.model.HomeStay getSelectedHomeStay() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.nammahomestay.app.data.model.Pricing getPricing() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getError() {
        return null;
    }
    
    public final boolean isSaved() {
        return false;
    }
    
    public PricingUiState() {
        super();
    }
    
    public final boolean component1() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.nammahomestay.app.data.model.HomeStay> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.nammahomestay.app.data.model.HomeStay component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.nammahomestay.app.data.model.Pricing component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component5() {
        return null;
    }
    
    public final boolean component6() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.nammahomestay.app.ui.provider.pricing.PricingUiState copy(boolean isLoading, @org.jetbrains.annotations.NotNull()
    java.util.List<com.nammahomestay.app.data.model.HomeStay> homeStays, @org.jetbrains.annotations.Nullable()
    com.nammahomestay.app.data.model.HomeStay selectedHomeStay, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.model.Pricing pricing, @org.jetbrains.annotations.Nullable()
    java.lang.String error, boolean isSaved) {
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