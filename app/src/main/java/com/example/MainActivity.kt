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
                // Native Jetpack Compose App loads instantly with offline support
                var showNativeApp by remember { mutableStateOf(true) }

                if (showNativeApp) {
                    MainApp(onSwitchToWebView = { showNativeApp = false })
                } else {
                    WebViewScreen(
                        initialUrl = "file:///android_asset/web/index.html",
                        onSwitchToNativeApp = { showNativeApp = true }
                    )
                }
            }
        }
    }
}
