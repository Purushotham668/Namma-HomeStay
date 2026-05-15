package com.nammahomestay.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.nammahomestay.app.data.model.HomeStay
import com.nammahomestay.app.data.model.Pricing
import com.nammahomestay.app.data.model.Review
import com.nammahomestay.app.util.Resource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeStayRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) {
    private val collection = firestore.collection("homestays")

    suspend fun saveHomeProfile(homeStay: HomeStay): Resource<Unit> {
        return try {
            val docId = if (homeStay.id.isNotEmpty()) homeStay.id else collection.document().id
            val updatedStay = if (homeStay.id.isEmpty()) homeStay.copy(id = docId) else homeStay
            collection.document(docId).set(updatedStay).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to save profile")
        }
    }

    suspend fun getProviderHomeStays(providerId: String): Resource<List<HomeStay>> {
        return try {
            val snapshot = collection.whereEqualTo("providerId", providerId).get().await()
            val list = snapshot.documents.mapNotNull { doc ->
                doc.toObject(HomeStay::class.java)?.copy(id = doc.id)
            }
            Resource.Success(list)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to load profiles")
        }
    }

    fun observeProviderHomeStays(providerId: String) = callbackFlow {
        val listener = collection.whereEqualTo("providerId", providerId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val list = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(HomeStay::class.java)?.copy(id = doc.id)
                } ?: emptyList()
                trySend(list)
            }
        awaitClose { listener.remove() }
    }

    suspend fun getHomeProfile(homeStayId: String): Resource<HomeStay> {
        return try {
            val doc = collection.document(homeStayId).get().await()
            val stay = doc.toObject(HomeStay::class.java) ?: throw Exception("Not found")
            Resource.Success(stay)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to load profile")
        }
    }

    suspend fun updatePricing(homeStayId: String, pricing: Pricing): Resource<Unit> {
        return try {
            if (homeStayId.isEmpty()) throw Exception("Invalid property ID")
            collection.document(homeStayId).update("pricing", pricing).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to update pricing")
        }
    }

    suspend fun updateChecklist(
        homeStayId: String,
        checklist: Map<String, Boolean>
    ): Resource<Unit> {
        return try {
            if (homeStayId.isEmpty()) throw Exception("Invalid property ID")
            collection.document(homeStayId).update("checklist", checklist).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to update checklist")
        }
    }

    suspend fun updateOccupancy(homeStayId: String, isOccupied: Boolean): Resource<Unit> {
        return try {
            if (homeStayId.isEmpty()) throw Exception("Invalid property ID")
            collection.document(homeStayId).update("isOccupied", isOccupied).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to update occupancy")
        }
    }

    suspend fun updateRooms(homeStayId: String, rooms: List<com.nammahomestay.app.data.model.Room>): Resource<Unit> {
        return try {
            if (homeStayId.isEmpty()) throw Exception("Invalid property ID")
            collection.document(homeStayId).update("rooms", rooms).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to update rooms")
        }
    }

    suspend fun getAllHomeStays(): Resource<List<HomeStay>> {
        return try {
            val snapshot = collection.get().await()
            val list = snapshot.documents.mapNotNull { it.toObject(HomeStay::class.java) }
            Resource.Success(list)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to fetch stays")
        }
    }

    suspend fun deleteHomeStay(homeStayId: String): Resource<Unit> {
        return try {
            if (homeStayId.isEmpty()) throw Exception("Property ID is missing. Cannot delete.")
            collection.document(homeStayId).delete().await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to delete property")
        }
    }

    suspend fun searchHomeStays(query: String): Resource<List<HomeStay>> {
        return try {
            val snapshot = collection.get().await()
            val list = snapshot.documents
                .mapNotNull { it.toObject(HomeStay::class.java) }
                .filter {
                    it.name.contains(query, ignoreCase = true) ||
                    it.address.contains(query, ignoreCase = true)
                }
            Resource.Success(list)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Search failed")
        }
    }

    /**
     * Uploads a local image URI to Firebase Storage and returns the download URL.
     * Requires Firebase Storage enabled on Blaze plan.
     */
    suspend fun uploadPhoto(providerId: String, localUri: android.net.Uri): Resource<String> {
        return try {
            val ref = storage.reference
                .child("homestays/$providerId/${System.currentTimeMillis()}.jpg")
            ref.putFile(localUri).await()
            val url = ref.downloadUrl.await().toString()
            Resource.Success(url)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Upload failed")
        }
    }

    suspend fun getReviewsForHomestay(homestayId: String): Resource<List<Review>> {
        return try {
            val snapshot = firestore.collection("reviews")
                .whereEqualTo("homestayId", homestayId)
                .get()
                .await()
            val reviews = snapshot.documents.mapNotNull { it.toObject(Review::class.java) }
                .sortedByDescending { it.timestamp }
            Resource.Success(reviews)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to fetch reviews")
        }
    }

    fun observeReviewsForHomestay(homestayId: String): kotlinx.coroutines.flow.Flow<List<Review>> = callbackFlow {
        val listener = firestore.collection("reviews")
            .whereEqualTo("homestayId", homestayId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val reviews = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(Review::class.java)
                }?.sortedByDescending { it.timestamp } ?: emptyList()
                trySend(reviews)
            }
        awaitClose { listener.remove() }
    }

    suspend fun submitReview(review: Review): Resource<Unit> {
        return try {
            firestore.runTransaction { transaction ->
                val homestayRef = collection.document(review.homestayId)
                val homestaySnapshot = transaction.get(homestayRef)
                val reviewRef = firestore.collection("reviews").document(review.id)
                val existingReviewSnapshot = transaction.get(reviewRef)
                
                val currentRating = homestaySnapshot.getDouble("rating") ?: 0.0
                val currentCount = homestaySnapshot.getLong("reviewCount") ?: 0L
                
                val isEdit = existingReviewSnapshot.exists()
                
                if (isEdit) {
                    val oldRating = existingReviewSnapshot.getLong("rating") ?: 0L
                    val newRating = ((currentRating * currentCount) - oldRating + review.rating) / currentCount
                    transaction.update(homestayRef, "rating", newRating.toFloat())
                } else {
                    val newCount = currentCount + 1
                    val newRating = ((currentRating * currentCount) + review.rating) / newCount
                    transaction.update(homestayRef, "rating", newRating.toFloat())
                    transaction.update(homestayRef, "reviewCount", newCount.toInt())
                }
                
                transaction.set(reviewRef, review)
            }.await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to submit review")
        }
    }

    suspend fun getReviewByBookingId(reviewId: String): Resource<Review?> {
        return try {
            val snapshot = firestore.collection("reviews").document(reviewId).get().await()
            val review = snapshot.toObject(Review::class.java)
            Resource.Success(review)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to find review")
        }
    }
}
