package com.nammahomestay.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.nammahomestay.app.data.model.User
import com.nammahomestay.app.util.Resource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val collection = firestore.collection("users")

    /**
     * Real-time listener stream for the user's Firestore profile document.
     * Any changes made by other devices or sources are reflected immediately.
     */
    fun getUserProfileStream(uid: String): Flow<Resource<User>> = callbackFlow {
        trySend(Resource.Loading)

        val listenerRegistration = collection.document(uid)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(Resource.Error(error.localizedMessage ?: "Failed to listen to profile"))
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    val user = snapshot.toObject(User::class.java) ?: User(uid = uid)
                    trySend(Resource.Success(user))
                } else {
                    // Document doesn't exist yet — emit an empty user so the UI can still render
                    trySend(Resource.Success(User(uid = uid)))
                }
            }

        // Clean up the Firestore listener when the flow collector is cancelled
        awaitClose { listenerRegistration.remove() }
    }

    /**
     * One-shot fetch — kept for non-streaming use cases (e.g. auth role check).
     */
    suspend fun getUserProfile(uid: String): Resource<User> {
        return try {
            val doc = collection.document(uid).get().await()
            val user = doc.toObject(User::class.java) ?: User(uid = uid)
            Resource.Success(user)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to load user profile")
        }
    }

    /**
     * Persists the full user object to Firestore.
     * Uses merge=false (set) so all fields are always in sync.
     */
    suspend fun saveUserProfile(user: User): Resource<Unit> {
        return try {
            collection.document(user.uid).set(user).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to save user profile")
        }
    }

    /**
     * Partial field update — writes only the provided map of fields to Firestore.
     * Useful for updating a single field without overwriting the full document.
     */
    suspend fun updateProfileFields(uid: String, fields: Map<String, Any?>): Resource<Unit> {
        return try {
            collection.document(uid).update(fields).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            // If document doesn't exist yet, fall back to set()
            try {
                collection.document(uid).set(fields).await()
                Resource.Success(Unit)
            } catch (e2: Exception) {
                Resource.Error(e2.localizedMessage ?: "Failed to update profile")
            }
        }
    }

    /**
     * Updates the user's FCM device token.
     */
    suspend fun updateFcmToken(uid: String, token: String): Resource<Unit> {
        return updateProfileFields(uid, mapOf("fcmToken" to token))
    }
}
