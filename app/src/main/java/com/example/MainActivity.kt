package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.ui.MainApp
import com.example.ui.screens.WebViewScreen
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                // Primary mode gives the user the full luxury website design with all pages:
                // Home, Blog, Store, About, Contact, Privacy Policy, Terms, and Articles.
                var showNativeViewOnly by remember { mutableStateOf(false) }

                if (showNativeViewOnly) {
                    MainApp(onSwitchToWebView = { showNativeViewOnly = false })
                } else {
                    WebViewScreen(
                        initialUrl = "file:///android_asset/web/index.html",
                        onSwitchToNativeApp = { showNativeViewOnly = true }
                    )
                }
            }
        }
    }
}
