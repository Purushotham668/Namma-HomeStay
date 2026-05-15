package com.nammahomestay.app

import android.app.Application
import com.nammahomestay.app.util.FirestoreInitializer
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class NammaHomeStayApp : Application() {

    @Inject
    lateinit var firestoreInitializer: FirestoreInitializer

    // Application-scoped coroutine scope — survives the full app lifecycle
    private val appScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        // Bootstrap Firestore collections on first launch (runs in background)
        appScope.launch {
            firestoreInitializer.initializeIfNeeded()
        }
    }
}
