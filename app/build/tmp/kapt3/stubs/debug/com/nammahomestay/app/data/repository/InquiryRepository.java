package com.nammahomestay.app.data.repository;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.nammahomestay.app.data.model.Inquiry;
import com.nammahomestay.app.util.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0086@\u00a2\u0006\u0002\u0010\nJ\"\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0086@\u00a2\u0006\u0002\u0010\u0010J\"\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\b2\u0006\u0010\u0012\u001a\u00020\u000fH\u0086@\u00a2\u0006\u0002\u0010\u0010J&\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u000fH\u0086@\u00a2\u0006\u0002\u0010\u0016J\"\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\f0\u00182\u0006\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u000fJ\u0014\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u00182\u0006\u0010\u0014\u001a\u00020\u000fJ\u001e\u0010\u001b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u00182\u0006\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u000fJ\u001c\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\u001d\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010\u001eJ.\u0010\u001f\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u000f2\u0006\u0010 \u001a\u00020\u000f2\u0006\u0010!\u001a\u00020\"H\u0086@\u00a2\u0006\u0002\u0010#R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006$"}, d2 = {"Lcom/nammahomestay/app/data/repository/InquiryRepository;", "", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "(Lcom/google/firebase/firestore/FirebaseFirestore;)V", "collection", "Lcom/google/firebase/firestore/CollectionReference;", "cleanupOldInquiries", "Lcom/nammahomestay/app/util/Resource;", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getConversationsForProvider", "", "Lcom/nammahomestay/app/data/model/Inquiry;", "providerId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getConversationsForTraveler", "travelerId", "markMessagesAsSeen", "homestayId", "currentUserId", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "observeChatMessages", "Lkotlinx/coroutines/flow/Flow;", "observeInquiryCount", "", "observeTypingStatus", "sendMessage", "message", "(Lcom/nammahomestay/app/data/model/Inquiry;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setTypingStatus", "userId", "isTyping", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class InquiryRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.FirebaseFirestore firestore = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.CollectionReference collection = null;
    
    @javax.inject.Inject()
    public InquiryRepository(@org.jetbrains.annotations.NotNull()
    com.google.firebase.firestore.FirebaseFirestore firestore) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.Integer> observeInquiryCount(@org.jetbrains.annotations.NotNull()
    java.lang.String homestayId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.nammahomestay.app.data.model.Inquiry>> observeChatMessages(@org.jetbrains.annotations.NotNull()
    java.lang.String travelerId, @org.jetbrains.annotations.NotNull()
    java.lang.String homestayId) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getConversationsForProvider(@org.jetbrains.annotations.NotNull()
    java.lang.String providerId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.nammahomestay.app.util.Resource<? extends java.util.List<com.nammahomestay.app.data.model.Inquiry>>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getConversationsForTraveler(@org.jetbrains.annotations.NotNull()
    java.lang.String travelerId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.nammahomestay.app.util.Resource<? extends java.util.List<com.nammahomestay.app.data.model.Inquiry>>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object sendMessage(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.model.Inquiry message, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.nammahomestay.app.util.Resource<kotlin.Unit>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object markMessagesAsSeen(@org.jetbrains.annotations.NotNull()
    java.lang.String travelerId, @org.jetbrains.annotations.NotNull()
    java.lang.String homestayId, @org.jetbrains.annotations.NotNull()
    java.lang.String currentUserId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.lang.String> observeTypingStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String travelerId, @org.jetbrains.annotations.NotNull()
    java.lang.String homestayId) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setTypingStatus(@org.jetbrains.annotations.NotNull()
    java.lang.String travelerId, @org.jetbrains.annotations.NotNull()
    java.lang.String homestayId, @org.jetbrains.annotations.NotNull()
    java.lang.String userId, boolean isTyping, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object cleanupOldInquiries(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.nammahomestay.app.util.Resource<kotlin.Unit>> $completion) {
        return null;
    }
}