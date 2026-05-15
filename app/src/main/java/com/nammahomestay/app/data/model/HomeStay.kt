package com.nammahomestay.app.data.model

import com.google.firebase.firestore.PropertyName

data class HomeStay(
    val id: String = "",
    val providerId: String = "",
    val name: String = "",
    val ownerName: String = "",
    val contact: String = "",
    val address: String = "",
    val description: String = "",
    val photoUrls: List<String> = emptyList(),
    val checklist: Map<String, Boolean> = mapOf(
        "Clean Rooms" to false,
        "Hygienic Food" to false,
        "Safe Drinking Water" to false,
        "Parking Available" to false,
        "Family Friendly" to false
    ),
    val menu: DailyMenu = DailyMenu(),
    val pricing: Pricing = Pricing(),
    val localSpots: List<LocalSpot> = emptyList(),
    val categories: List<String> = listOf("Heritage"),
    val rooms: List<Room> = emptyList(),
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val rating: Float = 0.0f,
    val reviewCount: Int = 0,
    @get:PropertyName("isOccupied")
    @PropertyName("isOccupied")
    val isOccupied: Boolean = false
)

data class Room(
    val id: String = java.util.UUID.randomUUID().toString(),
    val name: String = "",
    val price: String = "",
    val description: String = "",
    val capacity: String = "2 Adults",
    val photoUrl: String? = null
)
