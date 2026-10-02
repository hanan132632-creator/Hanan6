package com.example.data.repository

import com.example.data.local.ArticleStatsEntity
import com.example.data.local.FavoriteEntity
import com.example.data.local.PropertyDao
import com.example.data.local.PropertyInquiryEntity
import com.example.data.local.SavedMortgageEntity
import com.example.data.model.NotificationItem
import com.example.data.model.Property
import com.example.data.model.PropertyCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class RealEstateRepository(private val propertyDao: PropertyDao) {

    private val _monetagSmartLink = MutableStateFlow("https://omg10.com/4/11930497")
    val monetagSmartLink: Flow<String> = _monetagSmartLink.asStateFlow()

    private val _smartLinkClicks = MutableStateFlow(0)
    val smartLinkClicks = _smartLinkClicks.asStateFlow()

    private val _notifications = MutableStateFlow(
        listOf(
            NotificationItem(
                id = "n1",
                title = "🏰 عرض حصري جديد في حي حطين",
                message = "تم إدراج قصر المها الملكي بمساحة 850 م² مع خصم 5% للدفع النقدي.",
                timeAgo = "منذ 15 دقيقة",
                propertyId = "prop-1"
            ),
            NotificationItem(
                id = "n2",
                title = "📉 انخفاض نسبي في هوامش التمويل العقاري",
                message = "بنك الرياض والراجحي يقدمان هوامش ربح تبدأ من 3.49% عبر عقارات النخبة.",
                timeAgo = "منذ ساعتين"
            ),
            NotificationItem(
                id = "n3",
                title = "📥 تم تحديث بروشورات الربع السنوي",
                message = "احصل على التحليل الاستثماري لحي النرجس والشاطئ بصيغة PDF فوراً.",
                timeAgo = "منذ يوم"
            )
        )
    )
    val notifications = _notifications.asStateFlow()

    private val catalogProperties = listOf(
        Property(
            id = "prop-1",
            title = "قصر المها الملكي - مودرن فاخر",
            category = PropertyCategory.VILLAS,
            city = "الرياض",
            district = "حي حطين الفاخر",
            priceSar = 6850000,
            areaSqm = 750,
            bedrooms = 6,
            bathrooms = 8,
            parkingSpots = 4,
            hasPool = true,
            hasElevator = true,
            hasSmartHome = true,
            featuredTag = "حصري VIP",
            imageDrawableRes = "img_villa_luxury",
            description = "فيلا ملكية بتصميم معماري فندقي فائق الفخامة مع مسبح إنفينيتي خارجي وتدفئة، مصعد إيطالي بانورامي، شلالات مائية، وتكييف مركزي مخفي لكامل الفيلا. موقع استراتيجي قرب بوليفارد وادي حنيفة.",
            features = listOf(
                "مسبح إنفينيتي خاص بتدفئة",
                "مصعد بانورامي إيطالي",
                "نظام سمارت هوم كلي KNX",
                "غرفة سينما منزلية 4K",
                "جناح نوم ماستر ملكي 90م²",
                "أجنحة مستقلة للسائق والخادمة",
                "مطابخ مجهزة بالكامل من المانيا"
            ),
            agentName = "م. عبدالله الشمري",
            agentPhone = "+966509876543",
            falLicenseNumber = "1200049210"
        ),
        Property(
            id = "prop-2",
            title = "بنتهاوس الأبراج العاجية بإطلالة بانورامية",
            category = PropertyCategory.APARTMENTS,
            city = "الرياض",
            district = "حي النرجس (شمال الرياض)",
            priceSar = 2450000,
            areaSqm = 310,
            bedrooms = 4,
            bathrooms = 5,
            parkingSpots = 2,
            hasPool = true,
            hasElevator = true,
            hasSmartHome = true,
            featuredTag = "جديد معتمد",
            imageDrawableRes = "img_apartment_penthouse",
            description = "شقة روف بنتهاوس فاخرة بإطلالة مفتوحة على أفق مدينة الرياض. تشطيبات رخامية إسبانية، تراس واسع مع جلسة شواء ومسبح جاكوزي معلق، ونظام دخول ذكي بالأبواب الرقمية.",
            features = listOf(
                "تراس خاص مع جاكوزي معلق",
                "واجهات زجاجية ممتدة بالكامل",
                "رخام ستاتواريو إسباني فاخر",
                "موقفين سيارة خاصة بالقبو",
                "نادي صحي ولاونج برج سكني",
                "خدمات كونسيرج وأمن 24/7"
            ),
            agentName = "أ. سارة القحطاني",
            agentPhone = "+966551234567",
            falLicenseNumber = "1200088319"
        ),
        Property(
            id = "prop-3",
            title = "أرض استثمارية تجارية وسكنية كبرى",
            category = PropertyCategory.LANDS,
            city = "الرياض",
            district = "طريق الملك سلمان (العارض)",
            priceSar = 9200000,
            areaSqm = 1450,
            bedrooms = 0,
            bathrooms = 0,
            parkingSpots = 0,
            hasPool = false,
            hasElevator = false,
            hasSmartHome = false,
            featuredTag = "فرصة استثمارية",
            imageDrawableRes = "img_land_investment",
            description = "أرض زاوية على شارعين رئيسيين بعرض 36م و20م في موقع حيوي واستراتيجي قرب المترو ومطار الملك خالد. مصرحة لبناء مجمع تجاري مكتبي أو مجمع شقق فندقية عالي العائد.",
            features = listOf(
                "زاوية على شارعين 36م و20م",
                "عائد استثماري متوقع يفوق 11%",
                "صك إلكتروني محدث معتمد",
                "قريبة من محطة المترو والجامعة",
                "كافة الخدمات والشبكات واصلة"
            ),
            agentName = "فهد التميمي",
            agentPhone = "+966548765432",
            falLicenseNumber = "1200017462"
        ),
        Property(
            id = "prop-4",
            title = "فيلا إيوان الفاخرة - أبحر الشمالية",
            category = PropertyCategory.VILLAS,
            city = "جدة",
            district = "حي الشاطئ / أبحر",
            priceSar = 5400000,
            areaSqm = 620,
            bedrooms = 5,
            bathrooms = 7,
            parkingSpots = 3,
            hasPool = true,
            hasElevator = true,
            hasSmartHome = true,
            featuredTag = "إطلالة بحرية",
            imageDrawableRes = "img_villa_luxury",
            description = "فيلا بحرية راقية تجمع بين الحداثة والهدوء الساحلي. حديقة استوائية مع جلسات عائمة، شلالات مائية، وتصميم داخلي بنظام الأسقف المزدوجة (Double Height Ceiling).",
            features = listOf(
                "قريبة 3 دقائق من كورنيش أبحر",
                "مسبح خاص مع نظام تدفئة وتبريد",
                "أسقف مرتفعة 6 أمتار",
                "أرضيات باركيه ألماني ورخام",
                "ضمانات إنشائية تصل إلى 25 سنة"
            ),
            agentName = "م. عبدالله الشمري",
            agentPhone = "+966509876543",
            falLicenseNumber = "1200049210"
        ),
        Property(
            id = "prop-5",
            title = "شقة ريفيرا الفندقية بإطلالة الكورنيش",
            category = PropertyCategory.APARTMENTS,
            city = "الخبر",
            district = "حي الكورنيش الشمالي",
            priceSar = 1850000,
            areaSqm = 240,
            bedrooms = 3,
            bathrooms = 4,
            parkingSpots = 2,
            hasPool = true,
            hasElevator = true,
            hasSmartHome = true,
            featuredTag = "سياحي استثماري",
            imageDrawableRes = "img_apartment_penthouse",
            description = "شقة مفروشة بالكامل بأعلى المعايير الفندقية الخمس نجوم. إمكانية التأجير اليومي (Airbnb) بعائد سنوي مجزي يتجاوز 14%.",
            features = listOf(
                "مفروشة بالكامل من أشهر الماركات",
                "إطلالة مباشرة على الخليج العربي",
                "إدارة فندقية وتشغيل متكامل",
                "مسبح وصالة ألعاب رياضية خاصة"
            ),
            agentName = "أ. سارة القحطاني",
            agentPhone = "+966551234567",
            falLicenseNumber = "1200088319"
        ),
        Property(
            id = "prop-6",
            title = "أرض خام تجارية لتطوير مجمع تجاري",
            category = PropertyCategory.LANDS,
            city = "الدمام",
            district = "حي الشاطئ الغربي",
            priceSar = 7600000,
            areaSqm = 2200,
            bedrooms = 0,
            bathrooms = 0,
            parkingSpots = 0,
            hasPool = false,
            hasElevator = false,
            hasSmartHome = false,
            featuredTag = "سعر لقطة",
            imageDrawableRes = "img_land_investment",
            description = "فرصة استثمارية نادرة على واجهة بحرية وتجارية مباشرة لتطوير مجمع كافيهات ومطاعم مفتوحة (Drive-thru / Strip Mall).",
            features = listOf(
                "واجهة تجارية 55 متراً",
                "كافة التراخيص والموافقات جاهزة",
                "موقع مرتفع الكثافة والحركة"
            ),
            agentName = "فهد التميمي",
            agentPhone = "+966548765432",
            falLicenseNumber = "1200017462"
        )
    )

    fun getPropertiesFlow(): Flow<List<Property>> {
        return combine(propertyDao.getAllFavoriteIds()) { favoriteIdsArray ->
            val favList = favoriteIdsArray[0]
            val favSet = if (favList.isEmpty()) {
                // If user hasn't added any yet, pre-populate default luxury favorites
                setOf("prop-1", "prop-2", "prop-4")
            } else {
                favList.toSet()
            }
            catalogProperties.map { prop ->
                prop.copy(
                    isFavorite = favSet.contains(prop.id),
                    brochureUrl = _monetagSmartLink.value
                )
            }
        }
    }

    suspend fun toggleFavorite(propertyId: String) {
        val isFav = propertyDao.isFavorite(propertyId)
        if (isFav) {
            propertyDao.removeFavorite(propertyId)
        } else {
            propertyDao.addFavorite(FavoriteEntity(propertyId))
        }
    }

    fun getSavedMortgages(): Flow<List<SavedMortgageEntity>> {
        return propertyDao.getAllSavedMortgages()
    }

    suspend fun saveMortgageCalculation(entity: SavedMortgageEntity) {
        propertyDao.saveMortgage(entity)
    }

    suspend fun deleteSavedMortgage(id: Int) {
        propertyDao.deleteSavedMortgage(id)
    }

    suspend fun recordInquiry(propertyId: String, propertyTitle: String, agentName: String, channel: String) {
        propertyDao.insertInquiry(
            PropertyInquiryEntity(
                propertyId = propertyId,
                propertyTitle = propertyTitle,
                agentName = agentName,
                channel = channel
            )
        )
    }

    fun setCustomSmartLink(url: String) {
        _monetagSmartLink.value = url
    }

    fun registerSmartLinkClick() {
        _smartLinkClicks.value += 1
    }

    fun markNotificationRead(id: String) {
        _notifications.value = _notifications.value.map {
            if (it.id == id) it.copy(isRead = true) else it
        }
    }

    private val defaultArticleStats = mapOf(
        "article-1" to Pair(1248, 142),
        "article-2" to Pair(980, 89),
        "article-3" to Pair(1650, 214)
    )

    fun getArticleStatsFlow(): Flow<Map<String, ArticleStatsEntity>> {
        return propertyDao.getAllArticleStats().map { list: List<ArticleStatsEntity> ->
            val map = list.associateBy { it.articleId }.toMutableMap()
            // Ensure default base numbers for articles
            defaultArticleStats.forEach { (id, pair) ->
                if (!map.containsKey(id)) {
                    map[id] = ArticleStatsEntity(
                        articleId = id,
                        viewsCount = pair.first,
                        likesCount = pair.second,
                        isLikedByUser = false
                    )
                }
            }
            map
        }
    }

    suspend fun incrementArticleView(articleId: String) {
        val existing = propertyDao.getArticleStat(articleId)
        val defaultBase = defaultArticleStats[articleId] ?: Pair(100, 10)
        val currentViews = existing?.viewsCount ?: defaultBase.first
        val currentLikes = existing?.likesCount ?: defaultBase.second
        val isLiked = existing?.isLikedByUser ?: false

        propertyDao.saveArticleStat(
            com.example.data.local.ArticleStatsEntity(
                articleId = articleId,
                viewsCount = currentViews + 1,
                likesCount = currentLikes,
                isLikedByUser = isLiked
            )
        )
    }

    suspend fun toggleArticleLike(articleId: String) {
        val existing = propertyDao.getArticleStat(articleId)
        val defaultBase = defaultArticleStats[articleId] ?: Pair(100, 10)
        val currentViews = existing?.viewsCount ?: defaultBase.first
        val currentLikes = existing?.likesCount ?: defaultBase.second
        val currentLiked = existing?.isLikedByUser ?: false

        val newLiked = !currentLiked
        val newLikesCount = if (newLiked) currentLikes + 1 else (currentLikes - 1).coerceAtLeast(0)

        propertyDao.saveArticleStat(
            com.example.data.local.ArticleStatsEntity(
                articleId = articleId,
                viewsCount = currentViews,
                likesCount = newLikesCount,
                isLikedByUser = newLiked
            )
        )
    }
}
