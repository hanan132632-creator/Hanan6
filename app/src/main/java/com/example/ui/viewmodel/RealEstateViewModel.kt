package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.SavedMortgageEntity
import com.example.data.model.MortgageSimulation
import com.example.data.model.NotificationItem
import com.example.data.model.Property
import com.example.data.model.PropertyCategory
import com.example.data.repository.RealEstateRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.net.URLEncoder

enum class AppTab {
    HOME,
    SEARCH,
    BLOG,
    STORE,
    CALCULATOR,
    FAVORITES,
    MORE,
    CONTACT
}

class RealEstateViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RealEstateRepository

    init {
        val db = AppDatabase.getDatabase(application)
        repository = RealEstateRepository(db.propertyDao())
    }

    private val _selectedTab = MutableStateFlow(AppTab.HOME)
    val selectedTab = _selectedTab.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow(PropertyCategory.ALL)
    val selectedCategory = _selectedCategory.asStateFlow()

    private val _selectedCity = MutableStateFlow("الكل")
    val selectedCity = _selectedCity.asStateFlow()

    private val _maxPriceFilter = MutableStateFlow(15000000.0)
    val maxPriceFilter = _maxPriceFilter.asStateFlow()

    // Property Details Sheet State
    private val _selectedPropertyForDetail = MutableStateFlow<Property?>(null)
    val selectedPropertyForDetail = _selectedPropertyForDetail.asStateFlow()

    // Mortgage Popup State
    private val _showMortgagePopup = MutableStateFlow(false)
    val showMortgagePopup = _showMortgagePopup.asStateFlow()

    // Notifications Dialog State
    private val _showNotificationsDialog = MutableStateFlow(false)
    val showNotificationsDialog = _showNotificationsDialog.asStateFlow()

    // Monetag In-App Live Ad Viewer State
    private val _showInAppAdDialog = MutableStateFlow(false)
    val showInAppAdDialog = _showInAppAdDialog.asStateFlow()

    // Monetag & App Settings Dialog State
    private val _showSettingsDialog = MutableStateFlow(false)
    val showSettingsDialog = _showSettingsDialog.asStateFlow()

    // Brochure Download Action Dialog State
    private val _downloadingPropertyBrochure = MutableStateFlow<Property?>(null)
    val downloadingPropertyBrochure = _downloadingPropertyBrochure.asStateFlow()

    // Mortgage Simulator State
    private val _mortgagePrice = MutableStateFlow(5000000.0)
    val mortgagePrice = _mortgagePrice.asStateFlow()

    private val _downPaymentPercent = MutableStateFlow(15.0)
    val downPaymentPercent = _downPaymentPercent.asStateFlow()

    private val _loanTermYears = MutableStateFlow(20)
    val loanTermYears = _loanTermYears.asStateFlow()

    private val _annualProfitRate = MutableStateFlow(3.95)
    val annualProfitRate = _annualProfitRate.asStateFlow()

    private val _includeSakani = MutableStateFlow(true)
    val includeSakani = _includeSakani.asStateFlow()

    val currentMortgageSimulation: StateFlow<MortgageSimulation> = combine(
        _mortgagePrice,
        _downPaymentPercent,
        _loanTermYears,
        _annualProfitRate,
        _includeSakani
    ) { price, dp, years, rate, sakani ->
        MortgageSimulation(
            propertyPrice = price,
            downPaymentPercentage = dp,
            loanTermYears = years,
            annualProfitRate = rate,
            includeSakaniSupport = sakani
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MortgageSimulation(5000000.0))

    val allProperties: StateFlow<List<Property>> = repository.getPropertiesFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val filteredProperties: StateFlow<List<Property>> = combine(
        allProperties,
        _searchQuery,
        _selectedCategory,
        _selectedCity,
        _maxPriceFilter
    ) { properties, query, category, city, maxPrice ->
        properties.filter { prop ->
            val matchesCategory = category == PropertyCategory.ALL || prop.category == category
            val matchesCity = city == "الكل" || prop.city.contains(city)
            val matchesPrice = prop.priceSar <= maxPrice
            val matchesQuery = query.isBlank() ||
                    prop.title.contains(query, ignoreCase = true) ||
                    prop.district.contains(query, ignoreCase = true) ||
                    prop.city.contains(query, ignoreCase = true) ||
                    prop.description.contains(query, ignoreCase = true)
            matchesCategory && matchesCity && matchesPrice && matchesQuery
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favoriteProperties: StateFlow<List<Property>> = combine(allProperties) { propsList ->
        propsList[0].filter { it.isFavorite }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val notifications: StateFlow<List<NotificationItem>> = repository.notifications
    val monetagSmartLink: StateFlow<String> = repository.monetagSmartLink.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        "https://otieuwou.net/4/8856321"
    )
    val smartLinkClicks: StateFlow<Int> = repository.smartLinkClicks
    val savedMortgages = repository.getSavedMortgages().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun selectTab(tab: AppTab) {
        _selectedTab.value = tab
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectCategory(category: PropertyCategory) {
        _selectedCategory.value = category
    }

    fun selectCity(city: String) {
        _selectedCity.value = city
    }

    fun setMaxPrice(price: Double) {
        _maxPriceFilter.value = price
    }

    fun openPropertyDetail(property: Property?) {
        _selectedPropertyForDetail.value = property
    }

    fun openMortgageCalculatorForProperty(property: Property) {
        _mortgagePrice.value = property.priceSar.toDouble()
        _showMortgagePopup.value = true
    }

    fun setMortgagePrice(price: Double) {
        _mortgagePrice.value = price
    }

    fun setDownPaymentPercent(dp: Double) {
        _downPaymentPercent.value = dp
    }

    fun setLoanTermYears(years: Int) {
        _loanTermYears.value = years
    }

    fun setAnnualProfitRate(rate: Double) {
        _annualProfitRate.value = rate
    }

    fun setIncludeSakani(enabled: Boolean) {
        _includeSakani.value = enabled
    }

    fun setShowMortgagePopup(show: Boolean) {
        _showMortgagePopup.value = show
    }

    fun setShowNotificationsDialog(show: Boolean) {
        _showNotificationsDialog.value = show
    }

    fun setShowSettingsDialog(show: Boolean) {
        _showSettingsDialog.value = show
    }

    fun setShowInAppAdDialog(show: Boolean) {
        _showInAppAdDialog.value = show
    }

    fun openInAppAdPreview() {
        repository.registerSmartLinkClick()
        _showInAppAdDialog.value = true
    }

    fun openExternalAd(context: Context) {
        repository.registerSmartLinkClick()
        val url = monetagSmartLink.value
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "تعذر فتح المتصفح", Toast.LENGTH_SHORT).show()
        }
    }

    fun startBrochureDownloadFlow(property: Property) {
        _downloadingPropertyBrochure.value = property
    }

    fun dismissBrochureDownload() {
        _downloadingPropertyBrochure.value = null
    }

    fun toggleFavorite(propertyId: String) {
        viewModelScope.launch {
            repository.toggleFavorite(propertyId)
        }
    }

    fun saveCurrentMortgage(propertyTitle: String = "حسبة مخصصة") {
        viewModelScope.launch {
            val sim = currentMortgageSimulation.value
            repository.saveMortgageCalculation(
                SavedMortgageEntity(
                    propertyTitle = propertyTitle,
                    propertyPrice = sim.propertyPrice,
                    downPaymentPercentage = sim.downPaymentPercentage,
                    loanTermYears = sim.loanTermYears,
                    annualProfitRate = sim.annualProfitRate,
                    monthlyInstallment = sim.monthlyInstallment
                )
            )
            Toast.makeText(getApplication(), "تم حفظ الحسبة في السجل بنجاح", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteSavedMortgage(id: Int) {
        viewModelScope.launch {
            repository.deleteSavedMortgage(id)
        }
    }

    fun updateMonetagSmartLink(newUrl: String) {
        repository.setCustomSmartLink(newUrl)
        Toast.makeText(getApplication(), "تم تحديث رابط Monetag الذكي", Toast.LENGTH_SHORT).show()
    }

    fun openWhatsApp(context: Context, property: Property? = null, customMessage: String? = null) {
        viewModelScope.launch {
            val phone = property?.agentPhone ?: "+966509876543"
            val message = customMessage ?: if (property != null) {
                "السلام عليكم ورحمة الله، أود الاستفسار والتفاوض حول: (${property.title}) كود [${property.id}] في ${property.city} - ${property.district} المعلن عنه في تطبيق عقارات النخبة."
            } else {
                "السلام عليكم ورحمة الله، أود الاستفسار عن الخدمات العقارية وفرص الاستثمار الفاخرة المتاحة عبر تطبيق عقارات النخبة."
            }

            if (property != null) {
                repository.recordInquiry(
                    propertyId = property.id,
                    propertyTitle = property.title,
                    agentName = property.agentName,
                    channel = "WHATSAPP"
                )
            }

            try {
                val encoded = URLEncoder.encode(message, "UTF-8")
                val cleanPhone = phone.replace("+", "").replace(" ", "")
                val uri = Uri.parse("https://api.whatsapp.com/send?phone=$cleanPhone&text=$encoded")
                val intent = Intent(Intent.ACTION_VIEW, uri).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(context, "لم يتم العثور على تطبيق واتساب، سيتم فتح الرابط", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun callAgent(context: Context, property: Property) {
        viewModelScope.launch {
            repository.recordInquiry(
                propertyId = property.id,
                propertyTitle = property.title,
                agentName = property.agentName,
                channel = "PHONE"
            )
            try {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${property.agentPhone}")).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(context, "تعذر فتح لوحة الاتصال", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun triggerSmartLinkDownload(context: Context, property: Property) {
        repository.registerSmartLinkClick()
        val url = monetagSmartLink.value
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
            Toast.makeText(
                context,
                "جاري تحميل بروشور (${property.title}) فائق الدقة وتفعيل العرض...",
                Toast.LENGTH_LONG
            ).show()
        } catch (e: Exception) {
            Toast.makeText(context, "جاري تجهيز البروشور للتحميل", Toast.LENGTH_SHORT).show()
        }
        _downloadingPropertyBrochure.value = null
    }

    fun markNotificationRead(id: String) {
        repository.markNotificationRead(id)
    }
}
