package com.nammahomestay.app.data.model

data class Review(
    val id: String = java.util.UUID.randomUUID().toString(),
    val homestayId: String = "",
    val travelerId: String = "",
    val travelerName: String = "",
    val rating: Int = 5,
    val comment: String = "",
    val reviewPhotos: List<String> = emptyList(),
    val timestamp: Long = System.currentTimeMillis()
)
