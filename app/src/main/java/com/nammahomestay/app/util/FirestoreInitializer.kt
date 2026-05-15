package com.nammahomestay.app.util

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * FirestoreInitializer — runs once on first app launch to bootstrap all required
 * Firestore collections with placeholder documents so they appear in the Firebase console.
 *
 * Firestore is "lazy" — collections only appear in the console after at least
 * one document has been written. This class creates the scaffolding structure.
 */
@Singleton
class FirestoreInitializer @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    companion object {
        private const val TAG = "FirestoreInitializer"
        private const val META_COLLECTION = "_app_meta"
        private const val INIT_DOC_ID = "bootstrap_v2"
    }

    /**
     * Call this from Application.onCreate() or a ViewModel init.
     * Checks a sentinel document first so the seeding only happens ONCE per project.
     */
    suspend fun initializeIfNeeded() {
        try {
            val metaRef = firestore.collection(META_COLLECTION).document(INIT_DOC_ID)
            val existing = metaRef.get().await()

            if (existing.exists()) {
                Log.d(TAG, "Firestore already initialized — skipping bootstrap.")
                return
            }

            Log.d(TAG, "First launch — bootstrapping Firestore collections…")
            seedCollections()

            // Write sentinel so we never seed again
            metaRef.set(
                mapOf(
                    "initialized" to true,
                    "timestamp" to com.google.firebase.Timestamp.now(),
                    "appVersion" to "1.0.0"
                )
            ).await()

            Log.d(TAG, "Firestore bootstrap complete.")
        } catch (e: Exception) {
            Log.e(TAG, "Firestore bootstrap failed: ${e.localizedMessage}")
        }
    }

    private suspend fun seedCollections() {
        val batch = firestore.batch()

        // ─── 1. 'users' collection ──────────────────────────────────────────
        // Placeholder document — real user docs are created on registration.
        val usersPlaceholder = firestore.collection("users").document("_placeholder")
        batch.set(
            usersPlaceholder,
            mapOf(
                "_note" to "This is a placeholder to make the collection visible in the console. Do not delete.",
                "uid" to "",
                "name" to "",
                "email" to "",
                "phone" to "",
                "role" to "",          // "traveler" or "provider"
                "dob" to "",
                "language" to "English",
                "notificationsEnabled" to true
            )
        )

        // ─── 2. 'homestays' collection ──────────────────────────────────────
        // Placeholder document — real docs are created when a provider saves their profile.
        val homeStaysPlaceholder = firestore.collection("homestays").document("_placeholder")
        batch.set(
            homeStaysPlaceholder,
            mapOf(
                "_note" to "This is a placeholder. Real homestay docs are created by providers.",
                "providerId" to "",
                "name" to "Sample Homestay",
                "ownerName" to "",
                "contact" to "",
                "address" to "Chikmagalur, Karnataka",
                "description" to "",
                "photoUrls" to emptyList<String>(),
                "categories" to listOf("Forest"),
                "latitude" to 13.3161,
                "longitude" to 75.7720,
                "rating" to 0.0f,
                "reviewCount" to 0,
                "checklist" to mapOf(
                    "Clean Rooms" to false,
                    "Hygienic Food" to false,
                    "Safe Drinking Water" to false,
                    "Parking Available" to false,
                    "Family Friendly" to false
                ),
                "menu" to mapOf(
                    "breakfast" to "",
                    "lunch" to "",
                    "dinner" to "",
                    "todaySpecial" to ""
                ),
                "pricing" to mapOf(
                    "pricePerDay" to "0",
                    "isAvailable" to false,
                    "guestLimit" to 0,
                    "extraGuestCharge" to "0"
                )
            )
        )

        // ─── 3. 'inquiries' collection ──────────────────────────────────────
        val inquiriesPlaceholder = firestore.collection("inquiries").document("_placeholder")
        batch.set(
            inquiriesPlaceholder,
            mapOf(
                "_note" to "This is a placeholder. Real inquiry docs are created when travelers contact providers.",
                "inquiryId" to "",
                "travelerId" to "",
                "travelerName" to "",
                "providerId" to "",
                "homeStayName" to "",
                "message" to "",
                "checkIn" to "",
                "checkOut" to "",
                "guests" to 1,
                "status" to "pending",
                "timestamp" to System.currentTimeMillis()
            )
        )

        // ─── 4. 'app_config' collection (optional — for dynamic config) ─────
        val configDoc = firestore.collection("app_config").document("categories")
        batch.set(
            configDoc,
            mapOf(
                "_note" to "Global app configuration — categories shown across the app.",
                "available" to listOf(
                    "Forest", "Mountain", "Coffee", "Heritage", "Estate", "River", "Valley"
                )
            )
        )

        batch.commit().await()
        Log.d(TAG, "All collections seeded successfully.")
    }
}
