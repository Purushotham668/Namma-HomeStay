package com.nammahomestay.app;

import android.app.Application;
import com.nammahomestay.app.util.FirestoreInitializer;
import dagger.hilt.android.HiltAndroidApp;
import kotlinx.coroutines.Dispatchers;
import javax.inject.Inject;

@dagger.hilt.android.HiltAndroidApp()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0005\u001a\u00020\u00068\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2 = {"Lcom/nammahomestay/app/NammaHomeStayApp;", "Landroid/app/Application;", "()V", "appScope", "Lkotlinx/coroutines/CoroutineScope;", "firestoreInitializer", "Lcom/nammahomestay/app/util/FirestoreInitializer;", "getFirestoreInitializer", "()Lcom/nammahomestay/app/util/FirestoreInitializer;", "setFirestoreInitializer", "(Lcom/nammahomestay/app/util/FirestoreInitializer;)V", "onCreate", "", "app_debug"})
public final class NammaHomeStayApp extends android.app.Application {
    @javax.inject.Inject()
    public com.nammahomestay.app.util.FirestoreInitializer firestoreInitializer;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope appScope = null;
    
    public NammaHomeStayApp() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.nammahomestay.app.util.FirestoreInitializer getFirestoreInitializer() {
        return null;
    }
    
    public final void setFirestoreInitializer(@org.jetbrains.annotations.NotNull()
    com.nammahomestay.app.util.FirestoreInitializer p0) {
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
}