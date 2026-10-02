package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

data class StoreProduct(
    val id: String,
    val title: String,
    val description: String,
    val priceSar: String,
    val badge: String,
    val features: List<String>,
    val icon: ImageVector
)

val sampleStoreProducts = listOf(
    StoreProduct(
        id = "srv-1",
        title = "باقة التسويق العقاري الملكية VIP",
        description = "حملة إعلانية ممولة مكثفة تشمل تصوير فيديو سنمائي وتغطية لمشاهير العقار للوصول لنخبة المشترين.",
        priceSar = "4,999 ر.س",
        badge = "الأكثر طلباً ⭐",
        features = listOf(
            "تصوير درون 4K وجولة افتراضية تفاعلية 360",
            "إبراز العقار في صدارة المنصة والتطبيق لمدة 30 يوماً",
            "حملات ممولة على Google و Instagram و X",
            "إعداد بروشور رقمي فائق الفخامة"
        ),
        icon = Icons.Default.Diamond
    ),
    StoreProduct(
        id = "srv-2",
        title = "طلب تقييم عقاري معتمد (مقيّم)",
        description = "تقرير تقييم رسمي معتمد من الهيئة السعودية للمقيمين المعتمدين لتحديد القيمة السوقية الدقيقة للعقار.",
        priceSar = "1,850 ر.س",
        badge = "معتمد رسمياً",
        features = listOf(
            "معاينة ميدانية من مقيمين مرخصين",
            "تحليل مقارنات السوق ومعدلات الصفقات المماثلة",
            "تقرير مفصل مقبول لدى كافة البنوك والجهات التمويلية",
            "تسليم التقرير خلال 48 ساعة عمل"
        ),
        icon = Icons.Default.Verified
    ),
    StoreProduct(
        id = "srv-3",
        title = "جلسة استشارة استثمارية وتمويلية خاصة",
        description = "جلسة مباشرة لمدة 60 دقيقة مع كبير مستشاري النخبة لتحليل محفظتك العقارية واختيار العقار الأعلى عائداً.",
        priceSar = "750 ر.س",
        badge = "استشارة VIP",
        features = listOf(
            "دراسة جدوى سريعة ومقارنة بدائل التمويل",
            "تحليل العوائد الإيجارية والتوقعات المستقبلية للمنطقة",
            "التأكد من سلامة الصكوك والاشتراطات البلدية",
            "خطة استثمارية مخصصة لأهدافك"
        ),
        icon = Icons.Default.SupportAgent
    ),
    StoreProduct(
        id = "srv-4",
        title = "تصوير درون فوتوغرافي وجولات VR 360",
        description = "إنتاج محتوى بصري فاخر يبرز كافة تفاصيل القصر أو الفيلا أو المجمع التجاري بأحدث التقنيات العالمية.",
        priceSar = "1,200 ر.س",
        badge = "تقنية حديثة",
        features = listOf(
            "تصوير جوي بدقة Ultra HD 4K",
            "تصوير داخلي بزوايا واسعة احترافية",
            "رابط جولة افتراضية تفاعلي قابل للمشاركة الفورية",
            "تعديل وتلوين سينمائي جاهز للنشر"
        ),
        icon = Icons.Default.CameraAlt
    )
)

@Composable
fun StoreScreen(
    onOrderServiceClick: (StoreProduct) -> Unit
) {
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
                        text = "🛍️ متجر الخدمات العقارية الفاخرة",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = LuxuryGold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "حلول تسويقية وتقييم واستشارات بإشراف نخبة من الخبراء المرخصين",
                        fontSize = 12.sp,
                        color = TextSecondary,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        items(sampleStoreProducts) { product ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = LuxuryNavyMedium),
                border = androidx.compose.foundation.BorderStroke(1.dp, LuxuryGold.copy(alpha = 0.25f))
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(LuxuryGold.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = product.icon,
                                    contentDescription = null,
                                    tint = LuxuryGold,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Surface(
                                color = LuxuryGold.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = product.badge,
                                    color = LuxuryGold,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }

                        Text(
                            text = product.priceSar,
                            color = LuxuryGoldLight,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = product.title,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = product.description,
                        color = TextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFF070C18), RoundedCornerShape(10.dp))
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        product.features.forEach { feature ->
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = EmeraldGreen,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = feature,
                                    color = Color(0xFFCBD5E1),
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = { onOrderServiceClick(product) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LuxuryGold,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.ShoppingBag, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("طلب الخدمة وتحديد الموعد عبر واتساب", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}
