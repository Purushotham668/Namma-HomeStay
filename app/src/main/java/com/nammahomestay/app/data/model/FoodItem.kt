package com.nammahomestay.app.data.model

import com.google.firebase.firestore.PropertyName

data class FoodItem(
    val id: String = java.util.UUID.randomUUID().toString(),
    val name: String = "",
    val description: String = "",
    val price: String = "",
    @get:PropertyName("isVeg")
    @PropertyName("isVeg")
    val isVeg: Boolean = true
)
