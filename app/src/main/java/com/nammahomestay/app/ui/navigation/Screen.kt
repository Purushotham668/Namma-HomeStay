package com.nammahomestay.app.ui.navigation

sealed class Screen(val route: String) {
    // Auth
    object Splash              : Screen("splash")
    object Login               : Screen("login")
    object Register            : Screen("register")
    object RoleSelection       : Screen("role_selection")

    // Provider
    object ProviderDashboard   : Screen("provider_dashboard")
    object HomeProfile         : Screen("home_profile")
    object DailyMenu           : Screen("daily_menu")
    object Pricing             : Screen("pricing")
    object InquiryBox          : Screen("inquiry_box")
    object LocalGuide          : Screen("local_guide")
    object ProviderSettings    : Screen("provider_settings")

    // Traveler
    object TravelerDashboard   : Screen("traveler_dashboard")
    object Explore             : Screen("explore")
    object HomeStayDetail      : Screen("homestay_detail/{homestayId}") {
        fun createRoute(id: String) = "homestay_detail/$id"
    }
    object SendInquiry         : Screen("send_inquiry/{homestayId}") {
        fun createRoute(id: String) = "send_inquiry/$id"
    }
    object Booking             : Screen("booking/{homestayId}") {
        fun createRoute(id: String) = "booking/$id"
    }
    object Payment             : Screen("payment/{bookingId}") {
        fun createRoute(id: String) = "payment/$id"
    }
    object BookingConfirmation : Screen("booking_confirmation/{bookingId}") {
        fun createRoute(id: String) = "booking_confirmation/$id"
    }
    object ProviderQRScanner   : Screen("provider_qr_scanner")
    object ProviderBookings    : Screen("provider_bookings")
    object MyInquiries         : Screen("my_inquiries")
    object Chat : Screen("chat/{travelerId}/{homestayId}") {
        fun createRoute(travelerId: String, homestayId: String) = "chat/$travelerId/$homestayId"
    }
    object MyBookings          : Screen("my_bookings")
    object Review              : Screen("review/{bookingId}/{homestayId}") {
        fun createRoute(bookingId: String, homestayId: String) = "review/$bookingId/$homestayId"
    }
    object TravelerSettings    : Screen("traveler_settings")

    // Food System
    object MealBooking         : Screen("meal_booking/{homestayId}") {
        fun createRoute(id: String) = "meal_booking/$id"
    }
    object FoodTicket          : Screen("food_ticket/{bookingId}") {
        fun createRoute(id: String) = "food_ticket/$id"
    }
    object ProviderFoodTerminal: Screen("provider_food_terminal")
    object FoodAnalytics       : Screen("food_analytics")
    object FoodVerification    : Screen("food_verification")
}
