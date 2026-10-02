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
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView

enum class WebPageDestination(val title: String, val fileName: String) {
    HOME("الرئيسية", "index.html"),
    BLOG("المدونة", "blog.html"),
    STORE("المتجر", "store.html"),
    ABOUT("من نحن", "about.html"),
    CONTACT("تواصل", "contact.html"),
    PRIVACY("الخصوصية", "privacy.html"),
    TERMS("الشروط", "terms.html")
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(
    initialUrl: String = "file:///android_asset/web/index.html",
    onSwitchToNativeApp: (() -> Unit)? = null
) {
    var webViewInstance by remember { mutableStateOf<WebView?>(null) }
    var canGoBack by remember { mutableStateOf(false) }
    var canGoForward by remember { mutableStateOf(false) }
    var currentTitle by remember { mutableStateOf("عقارات النخبة") }
    var currentUrl by remember { mutableStateOf(initialUrl) }
    var isLoading by remember { mutableStateOf(true) }
    var progress by remember { mutableStateOf(0) }
    var hasError by remember { mutableStateOf(false) }
    var showMoreMenuSheet by remember { mutableStateOf(false) }

    val baseAssetUrl = "file:///android_asset/web/"

    fun navigateToPage(fileName: String) {
        val fullUrl = if (fileName.startsWith("http")) fileName else "$baseAssetUrl$fileName"
        hasError = false
        webViewInstance?.loadUrl(fullUrl)
    }

    // Determine current active section for bottom navigation highlights
    val activeDestination = remember(currentUrl) {
        when {
            currentUrl.contains("blog.html") || currentUrl.contains("article-") -> WebPageDestination.BLOG
            currentUrl.contains("store.html") -> WebPageDestination.STORE
            currentUrl.contains("contact.html") -> WebPageDestination.CONTACT
            currentUrl.contains("about.html") -> WebPageDestination.ABOUT
            currentUrl.contains("privacy.html") -> WebPageDestination.PRIVACY
            currentUrl.contains("terms.html") -> WebPageDestination.TERMS
            else -> WebPageDestination.HOME
        }
    }

    // Intercept hardware / gesture back navigation inside the WebView
    BackHandler(enabled = canGoBack) {
        webViewInstance?.goBack()
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            topBar = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF060A14))
                        .statusBarsPadding()
                ) {
                    // Header Bar with Luxury Styling
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Brand Logo and Title
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { navigateToPage("index.html") }
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(
                                        Brush.linearGradient(
                                            listOf(Color(0xFFD4AF37), Color(0xFFF5E6AB), Color(0xFF997B1E))
                                        )
                                    )
                                    .padding(1.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color(0xFF060A14), RoundedCornerShape(9.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Apartment,
                                        contentDescription = null,
                                        tint = Color(0xFFD4AF37),
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "عقارات النخبة",
                                    color = Color.White,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Black
                                )
                                Text(
                                    text = "ELITE REAL ESTATE",
                                    color = Color(0xFFD4AF37),
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp
                                )
                            }
                        }

                        // Navigation Quick Actions
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            IconButton(
                                onClick = { webViewInstance?.goBack() },
                                enabled = canGoBack,
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "للخلف",
                                    tint = if (canGoBack) Color(0xFFD4AF37) else Color.Gray.copy(alpha = 0.4f),
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            IconButton(
                                onClick = { webViewInstance?.reload() },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "تحديث",
                                    tint = Color(0xFFD4AF37),
                                    modifier = Modifier.size(18.dp)
                                )
                            }

                            IconButton(
                                onClick = { showMoreMenuSheet = true },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MenuOpen,
                                    contentDescription = "القائمة الكاملة",
                                    tint = Color(0xFFD4AF37),
                                    modifier = Modifier.size(22.dp)
                                )
                            }

                            if (onSwitchToNativeApp != null) {
                                Surface(
                                    color = Color(0xFF131F3D),
                                    shape = RoundedCornerShape(8.dp),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFD4AF37).copy(alpha = 0.5f)),
                                    modifier = Modifier.clickable { onSwitchToNativeApp() }
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Widgets,
                                            contentDescription = null,
                                            tint = Color(0xFFD4AF37),
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "الواجهة الأصلية",
                                            color = Color(0xFFFDE047),
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
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
            bottomBar = {
                NavigationBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .windowInsetsPadding(WindowInsets.navigationBars),
                    containerColor = Color(0xFF080E1E),
                    tonalElevation = 10.dp
                ) {
                    NavigationBarItem(
                        selected = activeDestination == WebPageDestination.HOME,
                        onClick = { navigateToPage("index.html") },
                        icon = {
                            Icon(
                                imageVector = if (activeDestination == WebPageDestination.HOME) Icons.Filled.Home else Icons.Outlined.Home,
                                contentDescription = "الرئيسية"
                            )
                        },
                        label = { Text("الرئيسية", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            selectedTextColor = Color(0xFFD4AF37),
                            indicatorColor = Color(0xFFD4AF37),
                            unselectedIconColor = Color(0xFF94A3B8),
                            unselectedTextColor = Color(0xFF94A3B8)
                        )
                    )

                    NavigationBarItem(
                        selected = activeDestination == WebPageDestination.BLOG,
                        onClick = { navigateToPage("blog.html") },
                        icon = {
                            Icon(
                                imageVector = if (activeDestination == WebPageDestination.BLOG) Icons.Filled.MenuBook else Icons.Outlined.MenuBook,
                                contentDescription = "المدونة"
                            )
                        },
                        label = { Text("المدونة", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            selectedTextColor = Color(0xFFD4AF37),
                            indicatorColor = Color(0xFFD4AF37),
                            unselectedIconColor = Color(0xFF94A3B8),
                            unselectedTextColor = Color(0xFF94A3B8)
                        )
                    )

                    NavigationBarItem(
                        selected = activeDestination == WebPageDestination.STORE,
                        onClick = { navigateToPage("store.html") },
                        icon = {
                            Icon(
                                imageVector = if (activeDestination == WebPageDestination.STORE) Icons.Filled.ShoppingBag else Icons.Outlined.ShoppingBag,
                                contentDescription = "المتجر"
                            )
                        },
                        label = { Text("المتجر", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            selectedTextColor = Color(0xFFD4AF37),
                            indicatorColor = Color(0xFFD4AF37),
                            unselectedIconColor = Color(0xFF94A3B8),
                            unselectedTextColor = Color(0xFF94A3B8)
                        )
                    )

                    NavigationBarItem(
                        selected = activeDestination == WebPageDestination.CONTACT,
                        onClick = { navigateToPage("contact.html") },
                        icon = {
                            Icon(
                                imageVector = if (activeDestination == WebPageDestination.CONTACT) Icons.Filled.Phone else Icons.Outlined.Phone,
                                contentDescription = "تواصل"
                            )
                        },
                        label = { Text("تواصل", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            selectedTextColor = Color(0xFFD4AF37),
                            indicatorColor = Color(0xFFD4AF37),
                            unselectedIconColor = Color(0xFF94A3B8),
                            unselectedTextColor = Color(0xFF94A3B8)
                        )
                    )

                    NavigationBarItem(
                        selected = showMoreMenuSheet || activeDestination == WebPageDestination.PRIVACY || activeDestination == WebPageDestination.TERMS || activeDestination == WebPageDestination.ABOUT,
                        onClick = { showMoreMenuSheet = true },
                        icon = {
                            Icon(
                                imageVector = Icons.Default.MoreHoriz,
                                contentDescription = "المزيد"
                            )
                        },
                        label = { Text("المزيد", fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            selectedTextColor = Color(0xFFD4AF37),
                            indicatorColor = Color(0xFFD4AF37),
                            unselectedIconColor = Color(0xFF94A3B8),
                            unselectedTextColor = Color(0xFF94A3B8)
                        )
                    )
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
                                @Suppress("DEPRECATION")
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
                                    canGoForward = view?.canGoForward() == true
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
                                    if (url.startsWith("tel:") || url.startsWith("whatsapp:") || url.startsWith("https://wa.me/") || url.startsWith("mailto:")) {
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

                // Error Fallback
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
                                Text(
                                    text = "إعادة تحميل الصفحة",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    textAlign = TextAlign.Center
                                )
                                Button(
                                    onClick = {
                                        hasError = false
                                        webViewInstance?.loadUrl(initialUrl)
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFD4AF37),
                                        contentColor = Color.Black
                                    ),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Text("العودة للرئيسية", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }

            // More Menu Bottom Sheet
            if (showMoreMenuSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showMoreMenuSheet = false },
                    containerColor = Color(0xFF0D1527),
                    dragHandle = {
                        Box(
                            modifier = Modifier
                                .padding(vertical = 12.dp)
                                .width(40.dp)
                                .height(4.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFD4AF37).copy(alpha = 0.5f))
                        )
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 8.dp)
                            .navigationBarsPadding(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "جميع صفحات وتطبيقات عقارات النخبة",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFFD4AF37),
                            modifier = Modifier.padding(bottom = 6.dp)
                        )

                        // Grid of Menu Items
                        val menuItems = listOf(
                            Triple("الرئيسية والعقارات", "index.html", Icons.Default.Home),
                            Triple("المدونة والمقالات", "blog.html", Icons.Default.MenuBook),
                            Triple("متجر الخدمات العقارية", "store.html", Icons.Default.ShoppingBag),
                            Triple("عن عقارات النخبة", "about.html", Icons.Default.Info),
                            Triple("اتصل بنا واستشر خبيراً", "contact.html", Icons.Default.Phone),
                            Triple("سياسة الخصوصية", "privacy.html", Icons.Default.Security),
                            Triple("شروط الاستخدام والاتفاقية", "terms.html", Icons.Default.Gavel),
                            Triple("دليل تملك الأجانب 2026", "article-1.html", Icons.Default.Article),
                            Triple("التمويل العقاري وسكني", "article-2.html", Icons.Default.AccountBalance),
                            Triple("استراتيجيات التمويل الذكي", "article-3.html", Icons.Default.TrendingUp)
                        )

                        menuItems.forEach { (title, url, icon) ->
                            Surface(
                                color = Color(0xFF131F3D),
                                shape = RoundedCornerShape(14.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFD4AF37).copy(alpha = 0.2f)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        showMoreMenuSheet = false
                                        navigateToPage(url)
                                    }
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = null,
                                        tint = Color(0xFFD4AF37),
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = title,
                                        color = Color.White,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = null,
                                        tint = Color(0xFF64748B),
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}
