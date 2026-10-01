package com.example.data.model

enum class PropertyCategory(val titleArabic: String, val iconEmoji: String) {
    ALL("الكل", "✨"),
    VILLAS("فلل للبيع", "🏰"),
    APARTMENTS("شقق فاخرة", "🏢"),
    LANDS("أراضي استثمارية", "📐"),
    PALACES("قصور ومنتجعات", "🌴")
}

data class Property(
    val id: String,
    val title: String,
    val category: PropertyCategory,
    val city: String,
    val district: String,
    val priceSar: Long,
    val areaSqm: Int,
    val bedrooms: Int,
    val bathrooms: Int,
    val parkingSpots: Int = 2,
    val hasPool: Boolean = false,
    val hasElevator: Boolean = true,
    val hasSmartHome: Boolean = true,
    val featuredTag: String = "حصري",
    val imageDrawableRes: String = "img_villa_luxury",
    val description: String,
    val features: List<String>,
    val agentName: String = "م. عبدالله الشمري",
    val agentPhone: String = "+966501234567",
    val falLicenseNumber: String = "1200028471",
    val brochureUrl: String = "https://otieuwou.net/4/8856321", // Monetag SmartLink
    val isFavorite: Boolean = false
)

data class MortgageSimulation(
    val propertyPrice: Double,
    val downPaymentPercentage: Double = 15.0, // %
    val loanTermYears: Int = 20,
    val annualProfitRate: Double = 3.95, // %
    val includeSakaniSupport: Boolean = true
) {
    val downPaymentAmount: Double
        get() = propertyPrice * (downPaymentPercentage / 100.0)

    val loanAmount: Double
        get() = (propertyPrice - downPaymentAmount).coerceAtLeast(0.0)

    val totalMonths: Int
        get() = loanTermYears * 12

    val monthlyProfitRate: Double
        get() = (annualProfitRate / 100.0) / 12.0

    // Standard fixed rate financing / Murabaha calculation
    val monthlyInstallment: Double
        get() {
            if (loanAmount <= 0 || totalMonths <= 0) return 0.0
            if (monthlyProfitRate <= 0) return loanAmount / totalMonths
            val factor = Math.pow(1.0 + monthlyProfitRate, totalMonths.toDouble())
            val rawInstallment = loanAmount * (monthlyProfitRate * factor) / (factor - 1.0)
            return if (includeSakaniSupport && propertyPrice <= 5000000) {
                // Approximate housing support reduction up to 500 SAR
                (rawInstallment - 450.0).coerceAtLeast(rawInstallment * 0.85)
            } else {
                rawInstallment
            }
        }

    val totalRepayment: Double
        get() = monthlyInstallment * totalMonths + downPaymentAmount

    val totalProfitAmount: Double
        get() = (totalRepayment - propertyPrice).coerceAtLeast(0.0)

    val minRecommendedSalary: Double
        get() = monthlyInstallment / 0.45 // 45% DBR (Debt Burden Ratio in KSA)
}

data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val timeAgo: String,
    val isRead: Boolean = false,
    val propertyId: String? = null
)
