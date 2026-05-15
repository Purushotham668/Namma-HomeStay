package com.nammahomestay.app.data.repository;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.nammahomestay.app.data.model.HomeStay;
import com.nammahomestay.app.data.model.Pricing;
import com.nammahomestay.app.data.model.Review;
import com.nammahomestay.app.util.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010$\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u001c\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010\u000eJ\u001a\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\nH\u0086@\u00a2\u0006\u0002\u0010\u0012J\u001c\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00110\n2\u0006\u0010\f\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010\u000eJ\"\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\n2\u0006\u0010\u0015\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010\u000eJ\u001e\u0010\u0016\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00170\n2\u0006\u0010\u0018\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010\u000eJ\"\u0010\u0019\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u00100\n2\u0006\u0010\u001a\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010\u000eJ\u001a\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\u001c2\u0006\u0010\u0015\u001a\u00020\rJ\u001a\u0010\u001d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\u00100\u001c2\u0006\u0010\u001a\u001a\u00020\rJ\u001c\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\u001f\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u0010 J\"\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\n2\u0006\u0010\"\u001a\u00020\rH\u0086@\u00a2\u0006\u0002\u0010\u000eJ\u001c\u0010#\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010$\u001a\u00020\u0017H\u0086@\u00a2\u0006\u0002\u0010%J0\u0010&\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\r2\u0012\u0010\'\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020)0(H\u0086@\u00a2\u0006\u0002\u0010*J$\u0010+\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010,\u001a\u00020)H\u0086@\u00a2\u0006\u0002\u0010-J$\u0010.\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010/\u001a\u000200H\u0086@\u00a2\u0006\u0002\u00101J*\u00102\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\f\u001a\u00020\r2\f\u00103\u001a\b\u0012\u0004\u0012\u0002040\u0010H\u0086@\u00a2\u0006\u0002\u00105J$\u00106\u001a\b\u0012\u0004\u0012\u00020\r0\n2\u0006\u0010\u0015\u001a\u00020\r2\u0006\u00107\u001a\u000208H\u0086@\u00a2\u0006\u0002\u00109R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006:"}, d2 = {"Lcom/nammahomestay/app/data/repository/HomeStayRepository;", "", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "storage", "Lcom/google/firebase/storage/FirebaseStorage;", "(Lcom/google/firebase/firestore/FirebaseFirestore;Lcom/google/firebase/storage/FirebaseStorage;)V", "collection", "Lcom/google/firebase/firestore/CollectionReference;", "deleteHomeStay", "Lcom/nammahomestay/app/util/Resource;", "", "homeStayId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllHomeStays", "", "Lcom/nammahomestay/app/data/model/HomeStay;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getHomeProfile", "getProviderHomeStays", "providerId", "getReviewByBookingId", "Lcom/nammahomestay/app/data/model/Review;", "reviewId", "getReviewsForHomestay", "homestayId", "observeProviderHomeStays", "Lkotlinx/coroutines/flow/Flow;", "observeReviewsForHomestay", "saveHomeProfile", "homeStay", "(Lcom/nammahomestay/app/data/model/HomeStay;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "searchHomeStays", "query", "submitReview", "review", "(Lcom/nammahomestay/app/data/model/Review;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateChecklist", "checklist", "", "", "(Ljava/lang/String;Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateOccupancy", "isOccupied", "(Ljava/lang/String;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updatePricing", "pricing", "Lcom/nammahomestay/app/data/model/Pricing;", "(Ljava/lang/String;Lcom/nammahomestay/app/data/model/Pricing;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateRooms", "rooms", "Lcom/nammahomestay/app/data/model/Room;", "(Ljava/lang/String;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "uploadPhoto", "localUri", "Landroid/net/Uri;", "(Ljava/lang/String;Landroid/net/Uri;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class HomeStayRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.FirebaseFirestore firestore = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.storage.FirebaseStorage storage = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.CollectionReference collection = null;
    
    @javax.inject.Inject()
    public HomeStayRepository(@org.jetbrains.annotations.NotNull()
    com.google.firebase.firestore.FirebaseFirestore firestore, @org.jetbrains.annotations.NotNull()
    com.google.firebase.storage.FirebaseStorage storage) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object saveHomeProfile(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.model.HomeStay homeStay, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.nammahomestay.app.util.Resource<kotlin.Unit>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getProviderHomeStays(@org.jetbrains.annotations.NotNull()
    java.lang.String providerId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.nammahomestay.app.util.Resource<? extends java.util.List<com.nammahomestay.app.data.model.HomeStay>>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.nammahomestay.app.data.model.HomeStay>> observeProviderHomeStays(@org.jetbrains.annotations.NotNull()
    java.lang.String providerId) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getHomeProfile(@org.jetbrains.annotations.NotNull()
    java.lang.String homeStayId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.nammahomestay.app.util.Resource<com.nammahomestay.app.data.model.HomeStay>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updatePricing(@org.jetbrains.annotations.NotNull()
    java.lang.String homeStayId, @org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.model.Pricing pricing, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.nammahomestay.app.util.Resource<kotlin.Unit>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateChecklist(@org.jetbrains.annotations.NotNull()
    java.lang.String homeStayId, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, java.lang.Boolean> checklist, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.nammahomestay.app.util.Resource<kotlin.Unit>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateOccupancy(@org.jetbrains.annotations.NotNull()
    java.lang.String homeStayId, boolean isOccupied, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.nammahomestay.app.util.Resource<kotlin.Unit>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object updateRooms(@org.jetbrains.annotations.NotNull()
    java.lang.String homeStayId, @org.jetbrains.annotations.NotNull()
    java.util.List<com.nammahomestay.app.data.model.Room> rooms, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.nammahomestay.app.util.Resource<kotlin.Unit>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getAllHomeStays(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.nammahomestay.app.util.Resource<? extends java.util.List<com.nammahomestay.app.data.model.HomeStay>>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object deleteHomeStay(@org.jetbrains.annotations.NotNull()
    java.lang.String homeStayId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.nammahomestay.app.util.Resource<kotlin.Unit>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object searchHomeStays(@org.jetbrains.annotations.NotNull()
    java.lang.String query, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.nammahomestay.app.util.Resource<? extends java.util.List<com.nammahomestay.app.data.model.HomeStay>>> $completion) {
        return null;
    }
    
    /**
     * Uploads a local image URI to Firebase Storage and returns the download URL.
     * Requires Firebase Storage enabled on Blaze plan.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object uploadPhoto(@org.jetbrains.annotations.NotNull()
    java.lang.String providerId, @org.jetbrains.annotations.NotNull()
    android.net.Uri localUri, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.nammahomestay.app.util.Resource<java.lang.String>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getReviewsForHomestay(@org.jetbrains.annotations.NotNull()
    java.lang.String homestayId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.nammahomestay.app.util.Resource<? extends java.util.List<com.nammahomestay.app.data.model.Review>>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.nammahomestay.app.data.model.Review>> observeReviewsForHomestay(@org.jetbrains.annotations.NotNull()
    java.lang.String homestayId) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object submitReview(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.data.model.Review review, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.nammahomestay.app.util.Resource<kotlin.Unit>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getReviewByBookingId(@org.jetbrains.annotations.NotNull()
    java.lang.String reviewId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.nammahomestay.app.util.Resource<com.nammahomestay.app.data.model.Review>> $completion) {
        return null;
    }
}