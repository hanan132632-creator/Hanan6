package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.PropertyCategory
import com.example.ui.components.*
import com.example.ui.screens.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.AppTab
import com.example.ui.viewmodel.RealEstateViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(
    viewModel: RealEstateViewModel = viewModel(),
    onSwitchToWebView: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val selectedCity by viewModel.selectedCity.collectAsStateWithLifecycle()
    val maxPrice by viewModel.maxPriceFilter.collectAsStateWithLifecycle()

    val filteredProperties by viewModel.filteredProperties.collectAsStateWithLifecycle()
    val favoriteProperties by viewModel.favoriteProperties.collectAsStateWithLifecycle()
    val notifications by viewModel.notifications.collectAsStateWithLifecycle()
    val unreadNotifsCount = notifications.count { !it.isRead }

    val selectedPropertyForDetail by viewModel.selectedPropertyForDetail.collectAsStateWithLifecycle()
    val showMortgagePopup by viewModel.showMortgagePopup.collectAsStateWithLifecycle()
    val showNotificationsDialog by viewModel.showNotificationsDialog.collectAsStateWithLifecycle()
    val showSettingsDialog by viewModel.showSettingsDialog.collectAsStateWithLifecycle()
    val showInAppAdDialog by viewModel.showInAppAdDialog.collectAsStateWithLifecycle()
    val downloadingPropertyBrochure by viewModel.downloadingPropertyBrochure.collectAsStateWithLifecycle()

    val currentMortgageSimulation by viewModel.currentMortgageSimulation.collectAsStateWithLifecycle()
    val savedMortgages by viewModel.savedMortgages.collectAsStateWithLifecycle()
    val monetagSmartLink by viewModel.monetagSmartLink.collectAsStateWithLifecycle()
    val smartLinkClicks by viewModel.smartLinkClicks.collectAsStateWithLifecycle()

    // Handle back button: return to Home if on another tab
    if (selectedTab != AppTab.HOME) {
        BackHandler {
            viewModel.selectTab(AppTab.HOME)
        }
    }

    // Force RTL layout direction for Arabic language support
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = LuxuryNavyDark,
            topBar = {
                Column {
                    if (onSwitchToWebView != null) {
                        Surface(
                            color = Color(0xFF0D172E),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .statusBarsPadding()
                                    .padding(horizontal = 16.dp, vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "📱 أنت في وضع تطبيق الأندرويد",
                                    color = Color(0xFF94A3B8),
                                    fontSize = 11.sp
                                )
                                Button(
                                    onClick = onSwitchToWebView,
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = LuxuryGold,
                                        contentColor = Color.Black
                                    ),
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                                    shape = RoundedCornerShape(8.dp),
                                    modifier = Modifier.height(28.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Language,
                                        contentDescription = null,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("عرض الموقع الإلكتروني", fontSize = 11.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
                                }
                            }
                        }
                    }
                    TopHeader(
                        unreadNotificationsCount = unreadNotifsCount,
                        onNotificationClick = { viewModel.setShowNotificationsDialog(true) },
                        onSettingsClick = { viewModel.setShowSettingsDialog(true) },
                        onAdClick = { viewModel.openInAppAdPreview() }
                    )
                }
            },
            bottomBar = {
                NavigationBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .windowInsetsPadding(WindowInsets.navigationBars)
                        .testTag("bottom_nav_bar"),
                    containerColor = LuxuryNavyMedium,
                    tonalElevation = 10.dp
                ) {
                    NavigationBarItem(
                        selected = selectedTab == AppTab.HOME,
                        onClick = { viewModel.selectTab(AppTab.HOME) },
                        icon = {
                            Icon(
                                imageVector = if (selectedTab == AppTab.HOME) Icons.Filled.Home else Icons.Outlined.Home,
                                contentDescription = "الرئيسية"
                            )
                        },
                        label = {
                            Text(
                                text = "الرئيسية",
                                fontSize = 11.sp
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            selectedTextColor = LuxuryGold,
                            indicatorColor = LuxuryGold,
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextSecondary
                        ),
                        modifier = Modifier.testTag("tab_home")
                    )

                    NavigationBarItem(
                        selected = selectedTab == AppTab.SEARCH,
                        onClick = { viewModel.selectTab(AppTab.SEARCH) },
                        icon = {
                            Icon(
                                imageVector = if (selectedTab == AppTab.SEARCH) Icons.Filled.Search else Icons.Outlined.Search,
                                contentDescription = "بحث"
                            )
                        },
                        label = {
                            Text(
                                text = "بحث",
                                fontSize = 11.sp
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            selectedTextColor = LuxuryGold,
                            indicatorColor = LuxuryGold,
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextSecondary
                        ),
                        modifier = Modifier.testTag("tab_search")
                    )

                    NavigationBarItem(
                        selected = selectedTab == AppTab.CALCULATOR,
                        onClick = { viewModel.selectTab(AppTab.CALCULATOR) },
                        icon = {
                            Icon(
                                imageVector = if (selectedTab == AppTab.CALCULATOR) Icons.Filled.Calculate else Icons.Outlined.Calculate,
                                contentDescription = "الحاسبة"
                            )
                        },
                        label = {
                            Text(
                                text = "الحاسبة",
                                fontSize = 11.sp
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            selectedTextColor = LuxuryGold,
                            indicatorColor = LuxuryGold,
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextSecondary
                        ),
                        modifier = Modifier.testTag("tab_calculator")
                    )

                    NavigationBarItem(
                        selected = selectedTab == AppTab.FAVORITES,
                        onClick = { viewModel.selectTab(AppTab.FAVORITES) },
                        icon = {
                            Icon(
                                imageVector = if (selectedTab == AppTab.FAVORITES) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = "المفضلة"
                            )
                        },
                        label = {
                            Text(
                                text = "المفضلة",
                                fontSize = 11.sp
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            selectedTextColor = LuxuryGold,
                            indicatorColor = LuxuryGold,
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextSecondary
                        ),
                        modifier = Modifier.testTag("tab_favorites")
                    )

                    NavigationBarItem(
                        selected = selectedTab == AppTab.CONTACT,
                        onClick = { viewModel.selectTab(AppTab.CONTACT) },
                        icon = {
                            Icon(
                                imageVector = if (selectedTab == AppTab.CONTACT) Icons.Filled.Phone else Icons.Outlined.Phone,
                                contentDescription = "اتصل بنا"
                            )
                        },
                        label = {
                            Text(
                                text = "اتصل بنا",
                                fontSize = 11.sp
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            selectedTextColor = LuxuryGold,
                            indicatorColor = LuxuryGold,
                            unselectedIconColor = TextSecondary,
                            unselectedTextColor = TextSecondary
                        ),
                        modifier = Modifier.testTag("tab_contact")
                    )
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (selectedTab) {
                    AppTab.HOME -> {
                        HomeScreen(
                            properties = filteredProperties,
                            selectedCategory = selectedCategory,
                            onSelectCategory = { viewModel.selectCategory(it) },
                            searchQuery = searchQuery,
                            onSearchQueryChange = { viewModel.setSearchQuery(it) },
                            selectedCity = selectedCity,
                            onSelectCity = { viewModel.selectCity(it) },
                            onPropertyClick = { viewModel.openPropertyDetail(it) },
                            onWhatsAppClick = { viewModel.openWhatsApp(context, it) },
                            onBrochureClick = { viewModel.startBrochureDownloadFlow(it) },
                            onMortgageClick = { viewModel.openMortgageCalculatorForProperty(it) },
                            onToggleFavorite = { viewModel.toggleFavorite(it) },
                            onOpenCalculatorClick = { viewModel.setShowMortgagePopup(true) },
                            adUrl = monetagSmartLink,
                            adClickCount = smartLinkClicks,
                            onPreviewInAppAd = { viewModel.openInAppAdPreview() },
                            onOpenExternalAd = { viewModel.openExternalAd(context) }
                        )
                    }

                    AppTab.SEARCH -> {
                        SearchScreen(
                            searchQuery = searchQuery,
                            onSearchQueryChange = { viewModel.setSearchQuery(it) },
                            selectedCategory = selectedCategory,
                            onSelectCategory = { viewModel.selectCategory(it) },
                            selectedCity = selectedCity,
                            onSelectCity = { viewModel.selectCity(it) },
                            maxPrice = maxPrice,
                            onMaxPriceChange = { viewModel.setMaxPrice(it) },
                            properties = filteredProperties,
                            onPropertyClick = { viewModel.openPropertyDetail(it) },
                            onWhatsAppClick = { viewModel.openWhatsApp(context, it) },
                            onBrochureClick = { viewModel.startBrochureDownloadFlow(it) },
                            onMortgageClick = { viewModel.openMortgageCalculatorForProperty(it) },
                            onToggleFavorite = { viewModel.toggleFavorite(it) }
                        )
                    }

                    AppTab.CALCULATOR -> {
                        CalculatorScreen(
                            simulation = currentMortgageSimulation,
                            onPriceChange = { viewModel.setMortgagePrice(it) },
                            onDownPaymentChange = { viewModel.setDownPaymentPercent(it) },
                            onLoanTermChange = { viewModel.setLoanTermYears(it) },
                            onProfitRateChange = { viewModel.setAnnualProfitRate(it) },
                            onSakaniToggle = { viewModel.setIncludeSakani(it) },
                            onSaveClick = { viewModel.saveCurrentMortgage() },
                            onWhatsAppConsultClick = {
                                val msg = "طلب استشارة تمويلية:\nسعر العقار: ${currentMortgageSimulation.propertyPrice.toLong()} ر.س\nالدفعة: ${currentMortgageSimulation.downPaymentPercentage}%\nالمدة: ${currentMortgageSimulation.loanTermYears} سنة\nالقسط التقديري: ${currentMortgageSimulation.monthlyInstallment.toLong()} ر.س"
                                viewModel.openWhatsApp(context, null, msg)
                            },
                            savedMortgages = savedMortgages,
                            onDeleteSavedMortgage = { viewModel.deleteSavedMortgage(it) }
                        )
                    }

                    AppTab.FAVORITES -> {
                        FavoritesScreen(
                            favorites = favoriteProperties,
                            onPropertyClick = { viewModel.openPropertyDetail(it) },
                            onWhatsAppClick = { viewModel.openWhatsApp(context, it) },
                            onBrochureClick = { viewModel.startBrochureDownloadFlow(it) },
                            onMortgageClick = { viewModel.openMortgageCalculatorForProperty(it) },
                            onToggleFavorite = { viewModel.toggleFavorite(it) },
                            onExploreClick = { viewModel.selectTab(AppTab.HOME) }
                        )
                    }

                    AppTab.CONTACT -> {
                        ContactScreen(
                            onWhatsAppClick = { msg -> viewModel.openWhatsApp(context, null, msg) },
                            onCallClick = {
                                try {
                                    val intent = android.content.Intent(
                                        android.content.Intent.ACTION_DIAL,
                                        android.net.Uri.parse("tel:8001234567")
                                    ).apply { flags = android.content.Intent.FLAG_ACTIVITY_NEW_TASK }
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    // ignore
                                }
                            },
                            onMonetagSettingsClick = { viewModel.setShowSettingsDialog(true) },
                            monetagSmartLink = monetagSmartLink,
                            smartLinkClicks = smartLinkClicks
                        )
                    }
                }
            }

            // Popups and Overlays
            if (showMortgagePopup) {
                MortgageCalculatorSheet(
                    simulation = currentMortgageSimulation,
                    onPriceChange = { viewModel.setMortgagePrice(it) },
                    onDownPaymentChange = { viewModel.setDownPaymentPercent(it) },
                    onLoanTermChange = { viewModel.setLoanTermYears(it) },
                    onProfitRateChange = { viewModel.setAnnualProfitRate(it) },
                    onSakaniToggle = { viewModel.setIncludeSakani(it) },
                    onSaveClick = { viewModel.saveCurrentMortgage() },
                    onWhatsAppConsultClick = {
                        val msg = "طلب استشارة تمويلية:\nسعر العقار: ${currentMortgageSimulation.propertyPrice.toLong()} ر.س\nالقسط: ${currentMortgageSimulation.monthlyInstallment.toLong()} ر.س"
                        viewModel.openWhatsApp(context, null, msg)
                    },
                    onDismiss = { viewModel.setShowMortgagePopup(false) }
                )
            }

            selectedPropertyForDetail?.let { property ->
                PropertyDetailDialog(
                    property = property,
                    onDismiss = { viewModel.openPropertyDetail(null) },
                    onToggleFavorite = { viewModel.toggleFavorite(property.id) },
                    onWhatsAppClick = { viewModel.openWhatsApp(context, property) },
                    onCallClick = { viewModel.callAgent(context, property) },
                    onBrochureClick = { viewModel.startBrochureDownloadFlow(property) },
                    onMortgageCalculateClick = {
                        viewModel.openMortgageCalculatorForProperty(property)
                    }
                )
            }

            downloadingPropertyBrochure?.let { property ->
                BrochureDownloadDialog(
                    property = property,
                    onConfirmDownload = {
                        viewModel.triggerSmartLinkDownload(context, property)
                    },
                    onDismiss = { viewModel.dismissBrochureDownload() }
                )
            }

            if (showNotificationsDialog) {
                NotificationsDialog(
                    notifications = notifications,
                    onNotificationClick = { notif ->
                        viewModel.markNotificationRead(notif.id)
                        notif.propertyId?.let { pid ->
                            val prop = filteredProperties.find { it.id == pid }
                            if (prop != null) {
                                viewModel.openPropertyDetail(prop)
                                viewModel.setShowNotificationsDialog(false)
                            }
                        }
                    },
                    onDismiss = { viewModel.setShowNotificationsDialog(false) }
                )
            }

            if (showSettingsDialog) {
                MonetagSettingsDialog(
                    currentSmartLink = monetagSmartLink,
                    clickCount = smartLinkClicks,
                    onSaveLink = { viewModel.updateMonetagSmartLink(it) },
                    onDismiss = { viewModel.setShowSettingsDialog(false) }
                )
            }

            if (showInAppAdDialog) {
                MonetagInAppAdViewer(
                    adUrl = monetagSmartLink,
                    onDismiss = { viewModel.setShowInAppAdDialog(false) }
                )
            }
        }
    }
}
