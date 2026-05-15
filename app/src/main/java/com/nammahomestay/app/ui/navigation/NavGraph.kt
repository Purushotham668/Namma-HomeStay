package com.nammahomestay.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.nammahomestay.app.ui.auth.LoginScreen
import com.nammahomestay.app.ui.auth.RegisterScreen
import com.nammahomestay.app.ui.auth.RoleSelectionScreen
import com.nammahomestay.app.ui.auth.SplashScreen
import com.nammahomestay.app.ui.provider.dashboard.ProviderDashboardScreen
import com.nammahomestay.app.ui.provider.inquiry.InquiryBoxScreen
import com.nammahomestay.app.ui.provider.localguide.LocalGuideScreen
import com.nammahomestay.app.ui.provider.menu.DailyMenuScreen
import com.nammahomestay.app.ui.provider.pricing.PricingScreen
import com.nammahomestay.app.ui.provider.profile.HomeProfileScreen
import com.nammahomestay.app.ui.provider.settings.ProviderSettingsScreen
import com.nammahomestay.app.ui.traveler.dashboard.TravelerDashboardScreen
import com.nammahomestay.app.ui.traveler.explore.ExploreScreen
import com.nammahomestay.app.ui.traveler.explore.HomeStayDetailScreen
import com.nammahomestay.app.ui.traveler.inquiry.MyInquiriesScreen
import com.nammahomestay.app.ui.traveler.inquiry.SendInquiryScreen
import com.nammahomestay.app.ui.traveler.settings.TravelerSettingsScreen
import com.nammahomestay.app.ui.traveler.booking.BookingScreen
import com.nammahomestay.app.ui.traveler.booking.PaymentScreen
import com.nammahomestay.app.ui.traveler.booking.BookingConfirmationScreen
import com.nammahomestay.app.ui.traveler.booking.MyBookingsScreen
import com.nammahomestay.app.ui.traveler.booking.ReviewScreen
import com.nammahomestay.app.ui.provider.booking.ProviderBookingsScreen
import com.nammahomestay.app.ui.provider.verification.QRScannerScreen
import com.nammahomestay.app.ui.traveler.food.MealBookingScreen
import com.nammahomestay.app.ui.traveler.food.FoodTicketScreen
import com.nammahomestay.app.ui.provider.food.ProviderFoodTerminalScreen
import com.nammahomestay.app.ui.provider.food.FoodAnalyticsScreen
import com.nammahomestay.app.ui.provider.food.FoodVerificationScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        // ── Auth ──────────────────────────────────────────────────────────────
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController = navController)
        }
        composable(Screen.RoleSelection.route) {
            RoleSelectionScreen(navController = navController)
        }

        // ── Provider ──────────────────────────────────────────────────────────
        composable(Screen.ProviderDashboard.route) {
            ProviderDashboardScreen(navController = navController)
        }
        composable(Screen.HomeProfile.route) {
            HomeProfileScreen(navController = navController)
        }
        composable(Screen.DailyMenu.route) {
            DailyMenuScreen(navController = navController)
        }
        composable(Screen.Pricing.route) {
            PricingScreen(navController = navController)
        }
        composable(Screen.InquiryBox.route) {
            InquiryBoxScreen(navController = navController)
        }
        composable(Screen.LocalGuide.route) {
            LocalGuideScreen(navController = navController)
        }
        composable(Screen.ProviderSettings.route) {
            ProviderSettingsScreen(navController = navController)
        }
        composable(Screen.ProviderQRScanner.route) {
            QRScannerScreen(onBack = { navController.popBackStack() })
        }
        composable(Screen.ProviderBookings.route) {
            ProviderBookingsScreen(onBack = { navController.popBackStack() })
        }

        // ── Traveler ──────────────────────────────────────────────────────────
        composable(Screen.TravelerDashboard.route) {
            TravelerDashboardScreen(navController = navController)
        }
        composable(Screen.Explore.route) {
            ExploreScreen(navController = navController)
        }
        composable(
            route = Screen.HomeStayDetail.route,
            arguments = listOf(navArgument("homestayId") { type = NavType.StringType })
        ) { backStack ->
            val id = backStack.arguments?.getString("homestayId") ?: ""
            HomeStayDetailScreen(navController = navController, homestayId = id)
        }
        composable(
            route = Screen.SendInquiry.route,
            arguments = listOf(navArgument("homestayId") { type = NavType.StringType })
        ) { backStack ->
            val id = backStack.arguments?.getString("homestayId") ?: ""
            SendInquiryScreen(navController = navController, homestayId = id)
        }
        composable(Screen.MyInquiries.route) {
            MyInquiriesScreen(navController = navController)
        }
        composable(
            route = Screen.Chat.route,
            arguments = listOf(
                navArgument("travelerId") { type = NavType.StringType },
                navArgument("homestayId") { type = NavType.StringType }
            )
        ) { backStack ->
            val tId = backStack.arguments?.getString("travelerId") ?: ""
            val hId = backStack.arguments?.getString("homestayId") ?: ""
            com.nammahomestay.app.ui.traveler.inquiry.ChatScreen(
                navController = navController,
                travelerId = tId,
                homestayId = hId
            )
        }
        composable(Screen.MyBookings.route) {
            MyBookingsScreen(
                navController = navController,
                onBookingClick = { id ->
                    navController.navigate(Screen.BookingConfirmation.createRoute(id))
                },
                onReviewClick = { booking ->
                    navController.navigate(Screen.Review.createRoute(booking.id, booking.homestayId))
                }
            )
        }
        composable(
            route = Screen.Booking.route,
            arguments = listOf(navArgument("homestayId") { type = NavType.StringType })
        ) {
            BookingScreen(
                onBack = { navController.popBackStack() },
                onProceedToPayment = { bookingId ->
                    navController.navigate(Screen.Payment.createRoute(bookingId))
                }
            )
        }
        composable(
            route = Screen.Payment.route,
            arguments = listOf(navArgument("bookingId") { type = NavType.StringType })
        ) { backStack ->
            val id = backStack.arguments?.getString("bookingId") ?: ""
            PaymentScreen(
                bookingId = id,
                onBack = { navController.popBackStack() },
                onPaymentSuccess = { bid ->
                    navController.navigate(Screen.BookingConfirmation.createRoute(bid)) {
                        popUpTo(Screen.HomeStayDetail.route) // Clear backstack up to details
                    }
                }
            )
        }
        composable(
            route = Screen.BookingConfirmation.route,
            arguments = listOf(navArgument("bookingId") { type = NavType.StringType })
        ) { backStack ->
            val id = backStack.arguments?.getString("bookingId") ?: ""
            BookingConfirmationScreen(
                bookingId = id,
                onBackToHome = {
                    navController.navigate(Screen.TravelerDashboard.route) {
                        popUpTo(Screen.TravelerDashboard.route) { inclusive = true }
                    }
                },
                onViewBookings = { navController.navigate(Screen.MyBookings.route) }
            )
        }
        composable(
            route = Screen.Review.route,
            arguments = listOf(
                navArgument("bookingId") { type = NavType.StringType },
                navArgument("homestayId") { type = NavType.StringType }
            )
        ) { backStack ->
            val bId = backStack.arguments?.getString("bookingId") ?: ""
            val hId = backStack.arguments?.getString("homestayId") ?: ""
            ReviewScreen(
                bookingId = bId,
                homestayId = hId,
                onBack = { navController.popBackStack() },
                onReviewSubmitted = { navController.popBackStack() }
            )
        }
        composable(Screen.TravelerSettings.route) {
            TravelerSettingsScreen(navController = navController)
        }

        // ── Food System ──────────────────────────────────────────────────────────
        composable(
            route = Screen.MealBooking.route,
            arguments = listOf(navArgument("homestayId") { type = NavType.StringType })
        ) { backStack ->
            val id = backStack.arguments?.getString("homestayId") ?: ""
            MealBookingScreen(
                navController = navController,
                homestayId = id
            )
        }
        composable(
            route = Screen.FoodTicket.route,
            arguments = listOf(navArgument("bookingId") { type = NavType.StringType })
        ) { backStack ->
            val id = backStack.arguments?.getString("bookingId") ?: ""
            FoodTicketScreen(
                navController = navController,
                bookingId = id
            )
        }
        composable(Screen.ProviderFoodTerminal.route) {
            ProviderFoodTerminalScreen(navController = navController)
        }
        composable(Screen.FoodAnalytics.route) {
            FoodAnalyticsScreen(navController = navController)
        }
        composable(Screen.FoodVerification.route) {
            FoodVerificationScreen(navController = navController)
        }
    }
}
