package com.nammahomestay.app.data.repository;

import com.google.firebase.firestore.FirebaseFirestore;
import com.nammahomestay.app.data.model.FoodBooking;
import com.nammahomestay.app.util.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001c\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000bH\u0086@\u00a2\u0006\u0002\u0010\fJ\u001e\u0010\r\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0086@\u00a2\u0006\u0002\u0010\u0010J\"\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00120\b2\u0006\u0010\u0013\u001a\u00020\u000fH\u0086@\u00a2\u0006\u0002\u0010\u0010J\"\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00120\b2\u0006\u0010\u0015\u001a\u00020\u000fH\u0086@\u00a2\u0006\u0002\u0010\u0010J\u001c\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\u000e\u001a\u00020\u000fH\u0086@\u00a2\u0006\u0002\u0010\u0010R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/nammahomestay/app/data/repository/FoodRepository;", "", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "(Lcom/google/firebase/firestore/FirebaseFirestore;)V", "collection", "Lcom/google/firebase/firestore/CollectionReference;", "createFoodBooking", "Lcom/nammahomestay/app/util/Resource;", "", "booking", "Lcom/nammahomestay/app/data/model/FoodBooking;", "(Lcom/nammahomestay/app/data/model/FoodBooking;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBookingById", "bookingId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getProviderFoodBookings", "", "providerId", "getTravelerFoodBookings", "travelerId", "markAsServed", "app_debug"})
public final class FoodRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.FirebaseFirestore firestore = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.CollectionReference collection = null;
    
    @javax.inject.Inject()
    public FoodRepository(@org.jetbrains.annotations.NotNull()
    com.google.firebase.firestore.FirebaseFirestore firestore) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object createFoodBooking(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.model.FoodBooking booking, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.nammahomestay.app.util.Resource<kotlin.Unit>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getTravelerFoodBookings(@org.jetbrains.annotations.NotNull()
    java.lang.String travelerId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.nammahomestay.app.util.Resource<? extends java.util.List<com.nammahomestay.app.data.model.FoodBooking>>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getProviderFoodBookings(@org.jetbrains.annotations.NotNull()
    java.lang.String providerId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.nammahomestay.app.util.Resource<? extends java.util.List<com.nammahomestay.app.data.model.FoodBooking>>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object markAsServed(@org.jetbrains.annotations.NotNull()
    java.lang.String bookingId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.nammahomestay.app.util.Resource<kotlin.Unit>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getBookingById(@org.jetbrains.annotations.NotNull()
    java.lang.String bookingId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.nammahomestay.app.util.Resource<com.nammahomestay.app.data.model.FoodBooking>> $completion) {
        return null;
    }
}