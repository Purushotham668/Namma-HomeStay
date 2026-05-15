package com.nammahomestay.app.data.model

data class FoodBooking(
    val id: String = java.util.UUID.randomUUID().toString(),
    val travelerId: String = "",
    val travelerName: String = "Guest",
    val providerId: String = "",
    val homestayId: String = "",
    val homestayName: String = "",
    val items: List<OrderItem> = emptyList(),
    val totalPrice: Double = 0.0,
    val status: String = "Paid", // Paid, Served
    val paymentMethod: String = "Online", // Online, Cash
    val qrCodeData: String = "", 
    val bookingDate: Long = System.currentTimeMillis(),
    val generatedBy: String = "Traveler" // Traveler, Provider
)

data class OrderItem(
    val itemId: String = "",
    val itemName: String = "",
    val category: String = "", // Breakfast, Lunch, Dinner, Special
    val price: Double = 0.0,
    val quantity: Int = 1,
    val isVeg: Boolean = true
)
