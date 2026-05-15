package com.nammahomestay.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.nammahomestay.app.data.model.LocalSpot
import com.nammahomestay.app.util.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalSpotRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val collection = firestore.collection("homestays")

    suspend fun addSpot(providerId: String, spots: List<LocalSpot>): Resource<Unit> {
        return try {
            collection.document(providerId).update("localSpots", spots).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to save spots")
        }
    }

    suspend fun getSpots(providerId: String): Resource<List<LocalSpot>> {
        return try {
            val doc = collection.document(providerId).get().await()
            val stay = doc.toObject(com.nammahomestay.app.data.model.HomeStay::class.java)
            Resource.Success(stay?.localSpots ?: emptyList())
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to load spots")
        }
    }
}
