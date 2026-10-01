package com.example.ui.screens

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(
    initialUrl: String = "file:///android_asset/web/index.html",
    onSwitchToNativeApp: (() -> Unit)? = null
) {
    var webViewInstance by remember { mutableStateOf<WebView?>(null) }
    var canGoBack by remember { mutableStateOf(false) }
    var currentTitle by remember { mutableStateOf("عقارات النخبة") }
    var currentUrl by remember { mutableStateOf(initialUrl) }
    var isLoading by remember { mutableStateOf(true) }
    var progress by remember { mutableStateOf(0) }
    var hasError by remember { mutableStateOf(false) }

    // Intercept hardware / gesture back navigation inside the WebView
    BackHandler(enabled = canGoBack) {
        webViewInstance?.goBack()
    }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF080E1E))
                    .statusBarsPadding()
            ) {
                // Top control bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Left controls: Back, Forward, Refresh
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        IconButton(
                            onClick = { 
                                hasError = false
                                webViewInstance?.goBack() 
                            },
                            enabled = canGoBack,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowForward, // RTL Forward is Back
                                contentDescription = "السابق",
                                tint = if (canGoBack) Color(0xFFD4AF37) else Color.Gray
                            )
                        }

                        IconButton(
                            onClick = { 
                                hasError = false
                                webViewInstance?.reload() 
                            },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "تحديث",
                                tint = Color(0xFFD4AF37)
                            )
                        }

                        IconButton(
                            onClick = { 
                                hasError = false
                                webViewInstance?.loadUrl(initialUrl) 
                            },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Home,
                                contentDescription = "الرئيسية",
                                tint = Color(0xFFD4AF37)
                            )
                        }
                    }

                    // Center: Website Live Indicator
                    Surface(
                        color = Color(0xFF131F3D),
                        shape = RoundedCornerShape(16.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFD4AF37).copy(alpha = 0.4f))
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(if (hasError) Color(0xFFEF4444) else Color(0xFF10B981))
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "شاشة الموقع المباشر",
                                color = Color(0xFFFDE047),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Right: Switch to Native App view if available
                    if (onSwitchToNativeApp != null) {
                        Button(
                            onClick = onSwitchToNativeApp,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFD4AF37),
                                contentColor = Color.Black
                            ),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.PhoneAndroid,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("وضع التطبيق", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                // Progress Bar while loading
                if (isLoading) {
                    LinearProgressIndicator(
                        progress = { progress / 100f },
                        modifier = Modifier.fillMaxWidth().height(2.dp),
                        color = Color(0xFFD4AF37),
                        trackColor = Color(0xFF080E1E),
                    )
                }
            }
        },
        containerColor = Color(0xFF060A14)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { ctx ->
                    WebView(ctx).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        settings.apply {
                            javaScriptEnabled = true
                            domStorageEnabled = true
                            databaseEnabled = true
                            loadWithOverviewMode = true
                            useWideViewPort = true
                            builtInZoomControls = true
                            displayZoomControls = false
                            allowFileAccess = true
                            allowContentAccess = true
                            @Suppress("DEPRECATION")
                            allowFileAccessFromFileURLs = true
                            @Suppress("DEPRECATION")
                            allowUniversalAccessFromFileURLs = true
                            cacheMode = WebSettings.LOAD_DEFAULT
                            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                        }

                        webViewClient = object : WebViewClient() {
                            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                                super.onPageStarted(view, url, favicon)
                                isLoading = true
                                hasError = false
                                url?.let { currentUrl = it }
                            }

                            override fun onPageFinished(view: WebView?, url: String?) {
                                super.onPageFinished(view, url)
                                isLoading = false
                                canGoBack = view?.canGoBack() == true
                                view?.title?.let { currentTitle = it }
                            }

                            override fun onReceivedError(
                                view: WebView?,
                                request: WebResourceRequest?,
                                error: WebResourceError?
                            ) {
                                super.onReceivedError(view, request, error)
                                if (request?.isForMainFrame == true) {
                                    hasError = true
                                    isLoading = false
                                }
                            }

                            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                                val url = request?.url?.toString() ?: return false
                                if (url.startsWith("tel:") || url.startsWith("whatsapp:") || url.startsWith("https://wa.me/")) {
                                    try {
                                        val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, request.url)
                                        ctx.startActivity(intent)
                                        return true
                                    } catch (e: Exception) {
                                        return false
                                    }
                                }
                                return false
                            }
                        }

                        webChromeClient = object : WebChromeClient() {
                            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                                super.onProgressChanged(view, newProgress)
                                progress = newProgress
                                if (newProgress >= 100) {
                                    isLoading = false
                                }
                            }

                            override fun onReceivedTitle(view: WebView?, title: String?) {
                                super.onReceivedTitle(view, title)
                                title?.let { currentTitle = it }
                            }
                        }

                        loadUrl(initialUrl)
                        webViewInstance = this
                    }
                },
                update = { webView ->
                    webViewInstance = webView
                }
            )

            // Luxury Error Fallback Screen
            if (hasError) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF060A14))
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF0B132B)),
                        shape = RoundedCornerShape(20.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFD4AF37).copy(alpha = 0.3f)),
                        modifier = Modifier.fillMaxWidth().padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFD4AF37).copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = null,
                                    tint = Color(0xFFD4AF37),
                                    modifier = Modifier.size(32.dp)
                                )
                            }

                            Text(
                                text = "تحديث الصفحة",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )

                            Text(
                                text = "جارٍ تحميل بيانات الموقع. اضغطي على زر إعادة المحاولة للتحميل الفوري.",
                                fontSize = 13.sp,
                                color = Color(0xFFCBD5E1),
                                textAlign = TextAlign.Center
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Button(
                                    onClick = {
                                        hasError = false
                                        webViewInstance?.loadUrl(initialUrl)
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFD4AF37),
                                        contentColor = Color.Black
                                    ),
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text("إعادة المحاولة", fontWeight = FontWeight.Bold)
                                }

                                if (onSwitchToNativeApp != null) {
                                    OutlinedButton(
                                        onClick = onSwitchToNativeApp,
                                        colors = ButtonDefaults.outlinedButtonColors(
                                            contentColor = Color(0xFFD4AF37)
                                        ),
                                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFD4AF37)),
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Text("وضع التطبيق", fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
