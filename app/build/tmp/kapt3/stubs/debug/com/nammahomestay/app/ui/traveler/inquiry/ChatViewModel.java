package com.nammahomestay.app.ui.traveler.inquiry;

import androidx.compose.animation.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavHostController;
import com.nammahomestay.app.data.model.Inquiry;
import com.nammahomestay.app.data.repository.AuthRepository;
import com.nammahomestay.app.data.repository.InquiryRepository;
import com.nammahomestay.app.ui.theme.*;
import com.nammahomestay.app.util.Resource;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u00122\u0006\u0010%\u001a\u00020\u0012J\u0010\u0010&\u001a\u00020#2\u0006\u0010\'\u001a\u00020\u000eH\u0002J\u0010\u0010(\u001a\u00020#2\u0006\u0010)\u001a\u00020\u0012H\u0002J\u001e\u0010*\u001a\u00020#2\u0006\u0010$\u001a\u00020\u00122\u0006\u0010%\u001a\u00020\u00122\u0006\u0010+\u001a\u00020\u0012J\u000e\u0010,\u001a\u00020#2\u0006\u0010-\u001a\u00020.R\u0016\u0010\t\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0011\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u0013\u001a\u00020\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0019\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001d\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001aR\u0019\u0010\u001d\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001aR\u000e\u0010\u001f\u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0019\u0010 \u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120\u0018\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001a\u00a8\u0006/"}, d2 = {"Lcom/nammahomestay/app/ui/traveler/inquiry/ChatViewModel;", "Landroidx/lifecycle/ViewModel;", "inquiryRepository", "Lcom/nammahomestay/app/data/repository/InquiryRepository;", "authRepository", "Lcom/nammahomestay/app/data/repository/AuthRepository;", "homestayRepository", "Lcom/nammahomestay/app/data/repository/HomeStayRepository;", "(Lcom/nammahomestay/app/data/repository/InquiryRepository;Lcom/nammahomestay/app/data/repository/AuthRepository;Lcom/nammahomestay/app/data/repository/HomeStayRepository;)V", "_homestay", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/nammahomestay/app/data/model/HomeStay;", "_messages", "", "Lcom/nammahomestay/app/data/model/Inquiry;", "_provider", "Lcom/nammahomestay/app/data/model/User;", "_typingId", "", "currentUserId", "getCurrentUserId", "()Ljava/lang/String;", "hId", "homestay", "Lkotlinx/coroutines/flow/StateFlow;", "getHomestay", "()Lkotlinx/coroutines/flow/StateFlow;", "messages", "getMessages", "provider", "getProvider", "tId", "typingId", "getTypingId", "init", "", "travelerId", "homestayId", "loadDetails", "message", "loadProvider", "providerId", "sendMessage", "messageText", "setTyping", "isTyping", "", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class ChatViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.InquiryRepository inquiryRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.AuthRepository authRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final com.nammahomestay.app.data.repository.HomeStayRepository homestayRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.util.List<com.nammahomestay.app.data.model.Inquiry>> _messages = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.nammahomestay.app.data.model.Inquiry>> messages = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.nammahomestay.app.data.model.HomeStay> _homestay = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.nammahomestay.app.data.model.HomeStay> homestay = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.nammahomestay.app.data.model.User> _provider = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.nammahomestay.app.data.model.User> provider = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _typingId = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> typingId = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String currentUserId = null;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String tId = "";
    @org.jetbrains.annotations.NotNull()
    private java.lang.String hId = "";
    
    @javax.inject.Inject()
    public ChatViewModel(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.InquiryRepository inquiryRepository, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.AuthRepository authRepository, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.repository.HomeStayRepository homestayRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.nammahomestay.app.data.model.Inquiry>> getMessages() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.nammahomestay.app.data.model.HomeStay> getHomestay() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.nammahomestay.app.data.model.User> getProvider() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getTypingId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCurrentUserId() {
        return null;
    }
    
    public final void init(@org.jetbrains.annotations.NotNull()
    java.lang.String travelerId, @org.jetbrains.annotations.NotNull()
    java.lang.String homestayId) {
    }
    
    private final void loadDetails(com.nammahomestay.app.data.model.Inquiry message) {
    }
    
    private final void loadProvider(java.lang.String providerId) {
    }
    
    public final void setTyping(boolean isTyping) {
    }
    
    public final void sendMessage(@org.jetbrains.annotations.NotNull()
    java.lang.String travelerId, @org.jetbrains.annotations.NotNull()
    java.lang.String homestayId, @org.jetbrains.annotations.NotNull()
    java.lang.String messageText) {
    }
}