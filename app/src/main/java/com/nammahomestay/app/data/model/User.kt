package com.nammahomestay.app.data.model

data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val role: String = "", // "provider" or "traveler"
    val dob: String = "",
    val language: String = "English",
    val notificationsEnabled: Boolean = true
)
