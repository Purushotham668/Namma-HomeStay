package com.nammahomestay.app.data.repository;

import com.google.firebase.firestore.FirebaseFirestore;
import com.nammahomestay.app.data.model.User;
import com.nammahomestay.app.util.Resource;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010$\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001c\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000bH\u0086@\u00a2\u0006\u0002\u0010\fJ\u001a\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u000e2\u0006\u0010\n\u001a\u00020\u000bJ\u001c\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\b2\u0006\u0010\u0011\u001a\u00020\tH\u0086@\u00a2\u0006\u0002\u0010\u0012J$\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00100\b2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u000bH\u0086@\u00a2\u0006\u0002\u0010\u0015J2\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00100\b2\u0006\u0010\n\u001a\u00020\u000b2\u0014\u0010\u0017\u001a\u0010\u0012\u0004\u0012\u00020\u000b\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0018H\u0086@\u00a2\u0006\u0002\u0010\u0019R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/nammahomestay/app/data/repository/UserRepository;", "", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "(Lcom/google/firebase/firestore/FirebaseFirestore;)V", "collection", "Lcom/google/firebase/firestore/CollectionReference;", "getUserProfile", "Lcom/nammahomestay/app/util/Resource;", "Lcom/nammahomestay/app/data/model/User;", "uid", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUserProfileStream", "Lkotlinx/coroutines/flow/Flow;", "saveUserProfile", "", "user", "(Lcom/nammahomestay/app/data/model/User;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateFcmToken", "token", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateProfileFields", "fields", "", "(Ljava/lang/String;Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class UserRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.FirebaseFirestore firestore = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.CollectionReference collection = null;
    
    @javax.inject.Inject()
    public UserRepository(@org.jetbrains.annotations.NotNull()
    com.google.firebase.firestore.FirebaseFirestore firestore) {
        super();
    }
    
    /**
     * Real-time listener stream for the user's Firestore profile document.
     * Any changes made by other devices or sources are reflected immediately.
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.nammahomestay.app.util.Resource<com.nammahomestay.app.data.model.User>> getUserProfileStream(@org.jetbrains.annotations.NotNull()
    java.lang.String uid) {
        return null;
    }
    
    /**
     * One-shot fetch — kept for non-streaming use cases (e.g. auth role check).
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getUserProfile(@org.jetbrains.annotations.NotNull()
    java.lang.String uid, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.nammahomestay.app.util.Resource<com.nammahomestay.app.data.model.User>> $completion) {
        return null;
    }
    
    /**
     * Persists the full user object to Firestore.
     * Uses merge=false (set) so all fields are always in sync.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object saveUserProfile(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.model.User user, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.nammahomestay.app.util.Resource<kotlin.Unit>> $completion) {
        return null;
    }
    
    /**
     * Partial field update — writes only the provided map of fields to Firestore.
     * Useful for updating a single field without overwriting the full document.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateProfileFields(@org.jetbrains.annotations.NotNull()
    java.lang.String uid, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, ? extends java.lang.Object> fields, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.nammahomestay.app.util.Resource<kotlin.Unit>> $completion) {
        return null;
    }
    
    /**
     * Updates the user's FCM device token.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateFcmToken(@org.jetbrains.annotations.NotNull()
    java.lang.String uid, @org.jetbrains.annotations.NotNull()
    java.lang.String token, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.nammahomestay.app.util.Resource<kotlin.Unit>> $completion) {
        return null;
    }
}