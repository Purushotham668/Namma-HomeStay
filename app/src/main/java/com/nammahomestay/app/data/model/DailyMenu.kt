package com.nammahomestay.app.data.model

data class DailyMenu(
    // Legacy String Fields (Kept for backward compatibility with existing Firebase data)
    val breakfast: String = "",
    val breakfastPrice: String = "",
    val lunch: String = "",
    val lunchPrice: String = "",
    val dinner: String = "",
    val dinnerPrice: String = "",
    
    // New Granular List Fields
    val breakfastItems: List<FoodItem> = emptyList(),
    val lunchItems: List<FoodItem> = emptyList(),
    val dinnerItems: List<FoodItem> = emptyList(),
    val specials: List<FoodItem> = emptyList(),
    val updatedAt: Long = System.currentTimeMillis()
)
