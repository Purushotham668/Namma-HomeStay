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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/nammahomestay/app/ui/traveler/explore/PriceSort;", "", "(Ljava/lang/String;I)V", "NONE", "LOW_TO_HIGH", "HIGH_TO_LOW", "app_debug"})
public enum PriceSort {
    /*public static final*/ NONE /* = new NONE() */,
    /*public static final*/ LOW_TO_HIGH /* = new LOW_TO_HIGH() */,
    /*public static final*/ HIGH_TO_LOW /* = new HIGH_TO_LOW() */;
    
    PriceSort() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.nammahomestay.app.ui.traveler.explore.PriceSort> getEntries() {
        return null;
    }
}