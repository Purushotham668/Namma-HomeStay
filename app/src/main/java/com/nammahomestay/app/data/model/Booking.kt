package com.nammahomestay.app.data.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Booking(
    val id: String = java.util.UUID.randomUUID().toString(),
    val travelerId: String = "",
    val providerId: String = "",
    val homestayId: String = "",
    val homestayName: String = "",
    val homestayImageUrl: String = "",
    val homestayAddress: String = "",
    val checkInDate: Long = 0L,
    val checkOutDate: Long = 0L,
    val guests: Int = 1,
    val totalPrice: Double = 0.0,
    val status: String = "Pending", // Pending, Confirmed, CheckedIn, Cancelled
    val paymentMethod: String = "", // UPI, Credit Card
    val qrCodeData: String = "", // e.g. "booking_id"
    @ServerTimestamp
    val createdAt: Date? = null
)
