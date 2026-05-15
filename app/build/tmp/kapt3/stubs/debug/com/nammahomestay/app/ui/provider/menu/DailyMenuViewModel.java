package com.nammahomestay.app.ui.provider.menu;

import androidx.lifecycle.ViewModel;
import com.nammahomestay.app.data.model.DailyMenu;
import com.nammahomestay.app.data.repository.AuthRepository;
import com.nammahomestay.app.data.repository.MenuRepository;
import com.nammahomestay.app.util.Resource;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import com.nammahomestay.app.data.model.HomeStay;
import com.nammahomestay.app.data.repository.HomeStayRepository;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0011\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0006\u0010\u0010\u001a\u00020\u0011J\u0006\u0010\u0012\u001a\u00020\u0011J\u0006\u0010\u0013\u001a\u00020\u0011J\u0006\u0010\u0014\u001a\u00020\u0011J\u0006\u0010\u0015\u001a\u00020\u0011J\u0006\u0010\u0016\u001a\u00020\u0011J\b\u0010\u0017\u001a\u00020\u0011H\u0002J\u000e\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001aJ\u000e\u0010\u001b\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001aJ\u000e\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001aJ\u000e\u0010\u001d\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001aJ\u000e\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001aJ\u0006\u0010\u001f\u001a\u00020\u0011J\u000e\u0010 \u001a\u00020\u00112\u0006\u0010!\u001a\u00020\"J\u000e\u0010#\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001aJ\u000e\u0010$\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001aJ\u000e\u0010%\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001aJ\u000e\u0010&\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001aJ\u000e\u0010\'\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001aJ\u001e\u0010(\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010)\u001a\u00020\u001a2\u0006\u0010*\u001a\u00020\u001aJ\u0016\u0010+\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010,\u001a\u00020\u001aJ\u001e\u0010-\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010)\u001a\u00020\u001a2\u0006\u0010*\u001a\u00020\u001aJ\u0016\u0010.\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010,\u001a\u00020\u001aJ\u001e\u0010/\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010)\u001a\u00020\u001a2\u0006\u0010*\u001a\u00020\u001aJ\u0016\u00100\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010,\u001a\u00020\u001aJ\u001e\u00101\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010)\u001a\u00020\u001a2\u0006\u0010*\u001a\u00020\u001aJ\u0016\u00102\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010,\u001a\u00020\u001aR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u00063"}, d2 = {"Lcom/nammahomestay/app/ui/provider/menu/DailyMenuViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/nammahomestay/app/data/repository/MenuRepository;", "homeStayRepository", "Lcom/nammahomestay/app/data/repository/HomeStayRepository;", "authRepository", "Lcom/nammahomestay/app/data/repository/AuthRepository;", "(Lcom/nammahomestay/app/data/repository/MenuRepository;Lcom/nammahomestay/app/data/repository/HomeStayRepository;Lcom/nammahomestay/app/data/repository/AuthRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/nammahomestay/app/ui/provider/menu/DailyMenuUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "addBreakfastItem", "", "addDinnerItem", "addLunchItem", "addSpecial", "clearError", "clearUpdatedState", "loadProperties", "removeBreakfastItem", "id", "", "removeDinnerItem", "removeLunchItem", "removeSpecial", "saveItemAndMinimize", "saveMenu", "selectProperty", "homeStay", "Lcom/nammahomestay/app/data/model/HomeStay;", "toggleBreakfastItemVegStatus", "toggleDinnerItemVegStatus", "toggleItemExpansion", "toggleLunchItemVegStatus", "toggleSpecialVegStatus", "updateBreakfastItem", "name", "description", "updateBreakfastItemPrice", "price", "updateDinnerItem", "updateDinnerItemPrice", "updateLunchItem", "updateLunchItemPrice", "updateSpecial", "updateSpecialPrice", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class DailyMenuViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.MenuRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.HomeStayRepository homeStayRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.nammahomestay.app.ui.provider.menu.DailyMenuUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.nammahomestay.app.ui.provider.menu.DailyMenuUiState> uiState = null;
    
    @javax.inject.Inject()
    public DailyMenuViewModel(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.MenuRepository repository, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.HomeStayRepository homeStayRepository, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.AuthRepository authRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.nammahomestay.app.ui.provider.menu.DailyMenuUiState> getUiState() {
        return null;
    }
    
    private final void loadProperties() {
    }
    
    public final void selectProperty(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.model.HomeStay homeStay) {
    }
    
    public final void toggleItemExpansion(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
    }
    
    public final void addBreakfastItem() {
    }
    
    public final void updateBreakfastItem(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String description) {
    }
    
    public final void updateBreakfastItemPrice(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String price) {
    }
    
    public final void toggleBreakfastItemVegStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
    }
    
    public final void removeBreakfastItem(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
    }
    
    public final void addLunchItem() {
    }
    
    public final void updateLunchItem(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String description) {
    }
    
    public final void updateLunchItemPrice(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String price) {
    }
    
    public final void toggleLunchItemVegStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
    }
    
    public final void removeLunchItem(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
    }
    
    public final void addDinnerItem() {
    }
    
    public final void updateDinnerItem(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String description) {
    }
    
    public final void updateDinnerItemPrice(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String price) {
    }
    
    public final void toggleDinnerItemVegStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
    }
    
    public final void removeDinnerItem(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
    }
    
    public final void addSpecial() {
    }
    
    public final void updateSpecial(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String name, @org.jetbrains.annotations.NotNull()
    java.lang.String description) {
    }
    
    public final void updateSpecialPrice(@org.jetbrains.annotations.NotNull()
    java.lang.String id, @org.jetbrains.annotations.NotNull()
    java.lang.String price) {
    }
    
    public final void toggleSpecialVegStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
    }
    
    public final void removeSpecial(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
    }
    
    public final void saveMenu() {
    }
    
    public final void saveItemAndMinimize(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
    }
    
    public final void clearUpdatedState() {
    }
    
    public final void clearError() {
    }
}