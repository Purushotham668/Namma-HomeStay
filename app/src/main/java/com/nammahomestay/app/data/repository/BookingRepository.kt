package com.nammahomestay.app.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.nammahomestay.app.data.model.Booking
import com.nammahomestay.app.util.Resource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookingRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val collection = firestore.collection("bookings")

    suspend fun createBooking(booking: Booking): Resource<String> {
        return try {
            collection.document(booking.id).set(booking).await()
            Resource.Success(booking.id)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to create booking")
        }
    }

    suspend fun getTravelerBookingsOnce(travelerId: String): Resource<List<Booking>> {
        return try {
            val snapshot = collection.whereEqualTo("travelerId", travelerId).get().await()
            val bookings = snapshot.documents.mapNotNull { it.toObject(Booking::class.java) }
            Resource.Success(bookings)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to fetch bookings")
        }
    }

    fun observeTravelerBookings(travelerId: String): Flow<List<Booking>> = callbackFlow {
        val listener = collection
            .whereEqualTo("travelerId", travelerId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val bookings = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(Booking::class.java)
                }?.sortedByDescending { it.checkInDate } ?: emptyList()
                trySend(bookings)
            }
        awaitClose { listener.remove() }
    }

    fun observeProviderBookings(providerId: String): Flow<List<Booking>> = callbackFlow {
        val listener = collection
            .whereEqualTo("providerId", providerId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                val bookings = snapshot?.documents?.mapNotNull { doc ->
                    doc.toObject(Booking::class.java)
                }?.sortedByDescending { it.checkInDate } ?: emptyList()
                trySend(bookings)
            }
        awaitClose { listener.remove() }
    }

    suspend fun getBookingById(bookingId: String): Resource<Booking> {
        return try {
            val snapshot = collection.document(bookingId).get().await()
            val booking = snapshot.toObject(Booking::class.java)
            if (booking != null) {
                Resource.Success(booking)
            } else {
                Resource.Error("Booking not found")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to fetch booking")
        }
    }

    suspend fun updateBookingStatus(bookingId: String, status: String): Resource<Unit> {
        return try {
            collection.document(bookingId).update("status", status).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to update booking status")
        }
    }

    suspend fun updateBookingQrCode(bookingId: String, qrData: String): Resource<Unit> {
        return try {
            collection.document(bookingId).update("qrCodeData", qrData).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to update booking QR code")
        }
    }
}
