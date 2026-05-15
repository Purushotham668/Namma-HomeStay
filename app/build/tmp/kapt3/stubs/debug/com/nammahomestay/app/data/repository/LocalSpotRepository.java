package com.nammahomestay.app.data.repository;

import com.google.firebase.firestore.FirebaseFirestore;
import com.nammahomestay.app.data.model.LocalSpot;
import com.nammahomestay.app.util.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J*\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH\u0086@\u00a2\u0006\u0002\u0010\u000fJ\"\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\b2\u0006\u0010\n\u001a\u00020\u000bH\u0086@\u00a2\u0006\u0002\u0010\u0011R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/nammahomestay/app/data/repository/LocalSpotRepository;", "", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "(Lcom/google/firebase/firestore/FirebaseFirestore;)V", "collection", "Lcom/google/firebase/firestore/CollectionReference;", "addSpot", "Lcom/nammahomestay/app/util/Resource;", "", "providerId", "", "spots", "", "Lcom/nammahomestay/app/data/model/LocalSpot;", "(Ljava/lang/String;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getSpots", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class LocalSpotRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.FirebaseFirestore firestore = null;
    @org.jetbrains.annotations.NotNull()
    private final com.google.firebase.firestore.CollectionReference collection = null;
    
    @javax.inject.Inject()
    public LocalSpotRepository(@org.jetbrains.annotations.NotNull()
    com.google.firebase.firestore.FirebaseFirestore firestore) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object addSpot(@org.jetbrains.annotations.NotNull()
    java.lang.String providerId, @org.jetbrains.annotations.NotNull()
    java.util.List<com.nammahomestay.app.data.model.LocalSpot> spots, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.nammahomestay.app.util.Resource<kotlin.Unit>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getSpots(@org.jetbrains.annotations.NotNull()
    java.lang.String providerId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.nammahomestay.app.util.Resource<? extends java.util.List<com.nammahomestay.app.data.model.LocalSpot>>> $completion) {
        return null;
    }
}