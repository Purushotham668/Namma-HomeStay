package com.nammahomestay.app.data.model

data class Inquiry(
    val id: String = "",
    val travelerId: String = "",
    val providerId: String = "",
    val homestayId: String = "",
    val homestayName: String = "",
    val senderId: String = "", // UID of the message sender
    val message: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val seen: Boolean = false
)
