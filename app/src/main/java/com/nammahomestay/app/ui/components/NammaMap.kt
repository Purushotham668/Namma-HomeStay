package com.nammahomestay.app.ui.components

import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

/**
 * A zero-config interactive map component that doesn't require an API key.
 * Uses Leaflet.js and OpenStreetMap-based tiles (CartoDB Voyager).
 */
@Composable
fun NammaMap(
    latitude: Double,
    longitude: Double,
    onLocationConfirm: (Double, Double) -> Unit,
    modifier: Modifier = Modifier,
    showSearch: Boolean = true
) {
    // We use CartoDB Voyager tiles because they have a clean "Google Maps-like" aesthetic 
    // while being entirely open-source and free to use without an API key.
    val html = MapConstants.getMapHtml(latitude, longitude, showSearch)

    var webViewInstance: WebView? by remember { mutableStateOf(null) }

    LaunchedEffect(latitude, longitude) {
        webViewInstance?.evaluateJavascript("updateMarker($latitude, $longitude)", null)
    }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                layoutParams = android.view.ViewGroup.LayoutParams(
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = WebViewClient()
                webChromeClient = object : android.webkit.WebChromeClient() {
                    override fun onGeolocationPermissionsShowPrompt(
                        origin: String?,
                        callback: android.webkit.GeolocationPermissions.Callback?
                    ) {
                        // Grant permission to the WebView for geolocation
                        callback?.invoke(origin, true, false)
                    }
                }
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                settings.userAgentString = "NammaHomeStayApp/1.0"
                setLayerType(android.view.View.LAYER_TYPE_HARDWARE, null)
                
                addJavascriptInterface(object {
                    @JavascriptInterface
                    fun onMapClick(lat: Double, lng: Double) {
                        post {
                            onLocationConfirm(lat, lng)
                        }
                    }
                }, "Android")
                loadDataWithBaseURL("https://carto.com", html, "text/html", "UTF-8", null)
                webViewInstance = this
            }
        },
        update = {
            webViewInstance = it
        }
    )
}
