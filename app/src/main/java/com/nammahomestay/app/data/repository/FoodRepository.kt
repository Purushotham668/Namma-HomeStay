package com.nammahomestay.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.nammahomestay.app.data.model.FoodBooking
import com.nammahomestay.app.util.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val collection = firestore.collection("food_bookings")

    suspend fun createFoodBooking(booking: FoodBooking): Resource<Unit> {
        return try {
            collection.document(booking.id).set(booking).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to create food booking")
        }
    }

    suspend fun getTravelerFoodBookings(travelerId: String): Resource<List<FoodBooking>> {
        return try {
            val snapshot = collection.whereEqualTo("travelerId", travelerId).get().await()
            val bookings = snapshot.documents.mapNotNull { it.toObject(FoodBooking::class.java) }
                .sortedByDescending { it.bookingDate }
            Resource.Success(bookings)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to load bookings")
        }
    }

    suspend fun getProviderFoodBookings(providerId: String): Resource<List<FoodBooking>> {
        return try {
            val snapshot = collection.whereEqualTo("providerId", providerId).get().await()
            val bookings = snapshot.documents.mapNotNull { it.toObject(FoodBooking::class.java) }
                .sortedByDescending { it.bookingDate }
            Resource.Success(bookings)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to load bookings")
        }
    }

    suspend fun markAsServed(bookingId: String): Resource<Unit> {
        return try {
            collection.document(bookingId).update("status", "Served").await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to update status")
        }
    }

    suspend fun getBookingById(bookingId: String): Resource<FoodBooking?> {
        return try {
            val doc = collection.document(bookingId).get().await()
            Resource.Success(doc.toObject(FoodBooking::class.java))
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to fetch booking")
        }
    }
}
