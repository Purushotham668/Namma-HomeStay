package com.nammahomestay.app.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Formats a Unix timestamp to a human-readable date string.
 */
fun Long.toFormattedDate(): String {
    val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
    return sdf.format(Date(this))
}

/**
 * Formats a Unix timestamp to a short date string.
 */
fun Long.toShortDate(): String {
    val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return sdf.format(Date(this))
}

/**
 * Returns true if a string is a valid 10-digit Indian phone number.
 */
fun String.isValidPhone(): Boolean = matches(Regex("^[6-9]\\d{9}$"))

/**
 * Returns true if a string is a valid email address.
 */
fun String.isValidEmail(): Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
