package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.ArticleStatsEntity
import com.example.ui.theme.*
import java.text.NumberFormat
import java.util.Locale

data class BlogArticle(
    val id: String,
    val title: String,
    val summary: String,
    val category: String,
    val readTime: String,
    val date: String,
    val author: String,
    val fullContent: String
)

val sampleArticles = listOf(
    BlogArticle(
        id = "article-1",
        title = "دليل تملك الأجانب للعقارات في السعودية 2026: الشروط والمناطق المتاحة",
        summary = "شرح شامل للنظام المحدث لتملك غير السعوديين للعقارات السكنية والاستثمارية، الشروط والتراخيص الرسمية.",
        category = "الأنظمة واللوائح",
        readTime = "6 دقائق قراءة",
        date = "15 أكتوبر 2026",
        author = "المستشار القانوني العقاري",
        fullContent = """
            يشهد القطاع العقاري في المملكة العربية السعودية تحولاً تاريخياً مع التحديثات الشاملة للوائح تملك غير السعوديين للعقارات، مواكبةً لمستهدفات رؤية 2030 لتعزيز الاستثمار الأجنبي وتسهيل العيش والعمل في المملكة.

            أبرز ملامح النظام الجديد:
            1. تملك العقارات السكنية داخل النطاق العمراني في كافة المدن الرئيسية (الرياض، جدة، الدمام، الخبر).
            2. إمكانية تملك الشركات الأجنبية والمستثمرين للأصول التجارية والأبراج والمجمعات الصناعية.
            3. تراخيص فورية عبر منصة "إحكام" والهيئة العامة للعقار مع ربط إلكتروني بالسجل العقاري.
            4. حوافز خاصة لحاملي الإقامة المميزة تشمل التملك الحر والاستثمار المباشر.

            الخطوات الإجرائية للشراء:
            • التحقق من الصك الإلكتروني عبر البورصة العقارية.
            • سداد ضريبة التصرفات العقارية (5%) إلكترونياً.
            • توثيق العقد والمبايعة بحضور كاتب العدل أو عبر الإفراغ الإلكتروني المعتمد.
        """.trimIndent()
    ),
    BlogArticle(
        id = "article-2",
        title = "دليل التمويل العقاري وسكني 2026: كيف تحصل على أفضل نسبة فائدة؟",
        summary = "مقارنة شاملة بين برامج البنوك السعودية، الدعم السكني، شروط التمويل المدعوم وكيفية تقليل القسط الشهري.",
        category = "التمويل البنكي",
        readTime = "8 دقائق قراءة",
        date = "12 أكتوبر 2026",
        author = "خبير التمويل الشخصي",
        fullContent = """
            يعد التمويل العقاري الخطوة الأساسية لمعظم مشتري العقارات. مع التغيرات في معدلات الفائدة (السايبور SAIBOR)، يصبح اختيار البرنامج الأنسب أمراً بالغ الأهمية لتوفير مئات الآلاف من الريالات على مدار فترة السداد.

            أهم ركائز اختيار التمويل العقاري:
            1. المقارنة بين المرابحة والإجارة ومزايا التمويل بهامش ربح ثابت لتجنب تقلبات السوق.
            2. الاستفادة من مبادرات صندوق التنمية العقارية (برنامج مصفوفة الدعم المحدث ومبادرة القسط الميسر).
            3. خفض نسبة عبء المديونية (DBR) لرفع الحد الأقصى لمبلغ التمويل الممنوح.
            4. التمويل المشترك (الزوج والزوجة أو الأقارب من الدرجة الأولى) لشراء العقارات الفاخرة.

            نصيحة ذهبية:
            احرص دائماً على سداد دفعة أولى لا تقل عن 15% - 20% لتقليل إجمالي الأرباح المستحقة وتأمين شروط تفاوضية أفضل مع المصارف.
        """.trimIndent()
    ),
    BlogArticle(
        id = "article-3",
        title = "استراتيجيات الاستثمار العقاري الذكي: كيف تختار عقارك الأعلى عائداً؟",
        summary = "تحليل اتجاهات السوق، العوائد الإيجارية المتوقعة في شمال الرياض وجدة، والفرص الواعدة للمستثمرين.",
        category = "استثمار وتطوير",
        readTime = "5 دقائق قراءة",
        date = "08 أكتوبر 2026",
        author = "محلل الأسواق العقارية",
        fullContent = """
            الاستثمار العقاري الناجح لا يعتمد على الصدفة، بل على دراسة عميقة لنمو البنية التحتية، وقرب المشاريع الكبرى (Giga Projects)، ومعدلات الطلب السكني والتجاري.

            أبرز مناطق الجذب الاستثماري لعام 2026:
            • شمال الرياض (طريق المطار، حطين، النرجس، الملقا): طلب قياسي على المجمعات السكنية المغلقة (Compounds) والفلل الحديثة.
            • أبحر الشمالية والشاطئ في جدة: نمو قوي في قطاع الضيافة والشقق الفندقية المطلة على البحر.
            • العقارات المدرة للدخل (الشقق المفروشة والمكاتب الفاخرة): تحقق عوائد إيجارية تتراوح بين 8% إلى 12% سنوياً.

            كيف تضاعف أرباح محفظتك؟
            نوع استثماراتك بين الأراضي الخام الواعدة والتطوير السريع، واستفد من خدمات إعادة البيع والتسويق الرقمي الاحترافي عبر منصات متخصصة.
        """.trimIndent()
    )
)

@Composable
fun BlogScreen(
    articleStats: Map<String, ArticleStatsEntity> = emptyMap(),
    onArticleViewed: (String) -> Unit = {},
    onArticleLiked: (String) -> Unit = {},
    onWhatsAppShare: (String) -> Unit = {}
) {
    val numberFormat = remember { NumberFormat.getNumberInstance(Locale.US) }
    var selectedArticleForRead by remember { mutableStateOf<BlogArticle?>(null) }

    if (selectedArticleForRead != null) {
        val article = selectedArticleForRead!!
        val stat = articleStats[article.id]
        val isLiked = stat?.isLikedByUser == true
        val likesCount = stat?.likesCount ?: 0
        val viewsCount = stat?.viewsCount ?: 0

        // Scale animation for like button
        val scale by animateFloatAsState(
            targetValue = if (isLiked) 1.25f else 1.0f,
            animationSpec = spring(dampingRatio = 0.4f, stiffness = 400f),
            label = "likeScale"
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(LuxuryNavyDark)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            onClick = { selectedArticleForRead = null },
                            colors = IconButtonDefaults.iconButtonColors(
                                containerColor = LuxuryNavyMedium,
                                contentColor = LuxuryGold
                            )
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "رجوع")
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "تفاصيل المقال",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Interactive Like Action in header
                    FilledTonalButton(
                        onClick = { onArticleLiked(article.id) },
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = if (isLiked) DangerRed.copy(alpha = 0.25f) else LuxuryNavyMedium,
                            contentColor = if (isLiked) DangerRed else TextSecondary
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "إعجاب",
                            tint = if (isLiked) DangerRed else TextSecondary,
                            modifier = Modifier
                                .size(18.dp)
                                .scale(scale)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = numberFormat.format(likesCount),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = LuxuryNavyMedium),
                    border = androidx.compose.foundation.BorderStroke(1.dp, LuxuryGold.copy(alpha = 0.3f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = LuxuryGold.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = article.category,
                                    color = LuxuryGold,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                            Text(
                                text = article.date,
                                color = TextSecondary,
                                fontSize = 11.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = article.title,
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black,
                            lineHeight = 26.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Stats Bar with live views, likes and read time
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF0C1322), RoundedCornerShape(10.dp))
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Visibility, contentDescription = null, tint = LuxuryGold, modifier = Modifier.size(15.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${numberFormat.format(viewsCount)} مشاهدة",
                                    color = Color(0xFFE2E8F0),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Favorite, contentDescription = null, tint = DangerRed, modifier = Modifier.size(15.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${numberFormat.format(likesCount)} إعجاب",
                                    color = Color(0xFFE2E8F0),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.AccessTime, contentDescription = null, tint = LuxuryGold, modifier = Modifier.size(15.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = article.readTime,
                                    color = TextSecondary,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 16.dp),
                            color = Color(0xFF1E293B)
                        )

                        Text(
                            text = article.fullContent,
                            color = Color(0xFFE2E8F0),
                            fontSize = 14.sp,
                            lineHeight = 24.sp
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Interaction Row: Like + Share WhatsApp
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = { onArticleLiked(article.id) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isLiked) DangerRed else Color(0xFF334155),
                                    contentColor = Color.White
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (isLiked) "أعجبك (${numberFormat.format(likesCount)})" else "إعجاب (${numberFormat.format(likesCount)})",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }

                            Button(
                                onClick = {
                                    onWhatsAppShare("مقال مميز من عقارات النخبة:\n*${article.title}*\n\nقراءة المزيد عبر: https://hanan.pro/${article.id}")
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = LuxuryGold,
                                    contentColor = Color.Black
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Share, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("مشاركة واتساب", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(LuxuryNavyDark)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = LuxuryNavyMedium),
                    border = androidx.compose.foundation.BorderStroke(1.dp, LuxuryGold.copy(alpha = 0.3f))
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "📰 مدونة وتقارير عقارات النخبة",
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Black,
                            color = LuxuryGold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "تحليلات السوق، أدلة التمويل وسكني وتملك الأجانب 2026",
                            fontSize = 12.sp,
                            color = TextSecondary,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            items(sampleArticles) { article ->
                val stat = articleStats[article.id]
                val views = stat?.viewsCount ?: 0
                val likes = stat?.likesCount ?: 0
                val isLiked = stat?.isLikedByUser == true

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onArticleViewed(article.id)
                            selectedArticleForRead = article
                        },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = LuxuryNavyMedium),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1E293B))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = LuxuryGold.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = article.category,
                                    color = LuxuryGold,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                )
                            }

                            // Live Views & Likes Counters Badge on the card
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Default.Visibility, contentDescription = null, tint = LuxuryGold, modifier = Modifier.size(13.dp))
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(numberFormat.format(views), color = TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.clickable { onArticleLiked(article.id) }
                                ) {
                                    Icon(
                                        imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                        contentDescription = null,
                                        tint = if (isLiked) DangerRed else TextSecondary,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(
                                        numberFormat.format(likes),
                                        color = if (isLiked) DangerRed else TextSecondary,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = article.title,
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 22.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = article.summary,
                            color = TextSecondary,
                            fontSize = 12.sp,
                            maxLines = 2,
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "بقلم: ${article.author}",
                                color = Color(0xFF94A3B8),
                                fontSize = 11.sp
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "اقرأ المقال",
                                    color = LuxuryGold,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = null,
                                    tint = LuxuryGold,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
