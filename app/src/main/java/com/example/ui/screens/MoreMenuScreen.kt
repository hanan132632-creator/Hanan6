package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

data class MoreMenuItem(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val badge: String? = null,
    val onClick: () -> Unit
)

@Composable
fun MoreMenuScreen(
    onNavigateToBlog: () -> Unit,
    onNavigateToStore: () -> Unit,
    onNavigateToLegal: (LegalContentType) -> Unit,
    onNavigateToContact: () -> Unit,
    onOpenWebBrowser: () -> Unit,
    onOpenMonetagSettings: () -> Unit
) {
    val items = listOf(
        MoreMenuItem(
            title = "📰 المدونة والمقالات العقارية",
            subtitle = "أدلة تملك الأجانب، التمويل وسكني، ودراسات السوق",
            icon = Icons.Default.MenuBook,
            badge = "جديد",
            onClick = onNavigateToBlog
        ),
        MoreMenuItem(
            title = "🛍️ متجر الخدمات العقارية الفاخرة",
            subtitle = "باقات التسويق VIP، التقييم المعتمد، وجولات VR 360",
            icon = Icons.Default.ShoppingBag,
            badge = "VIP",
            onClick = onNavigateToStore
        ),
        MoreMenuItem(
            title = "🏢 عن عقارات النخبة (من نحن)",
            subtitle = "رؤيتنا، رسالتنا، والتراخيص المعتمدة",
            icon = Icons.Default.Info,
            onClick = { onNavigateToLegal(LegalContentType.ABOUT_US) }
        ),
        MoreMenuItem(
            title = "🛡️ سياسة الخصوصية وحماية البيانات",
            subtitle = "بنود الخصوصية المعتمدة لمتجر Google Play",
            icon = Icons.Default.Security,
            onClick = { onNavigateToLegal(LegalContentType.PRIVACY_POLICY) }
        ),
        MoreMenuItem(
            title = "⚖️ شروط الاستخدام والاتفاقية",
            subtitle = "حقوق الاستخدام والضوابط القانونية",
            icon = Icons.Default.Gavel,
            onClick = { onNavigateToLegal(LegalContentType.TERMS_OF_SERVICE) }
        ),
        MoreMenuItem(
            title = "📞 تواصل معنا واستشر خبيراً",
            subtitle = "خدمة العملاء، الواتساب، والاتصال المباشر",
            icon = Icons.Default.Phone,
            onClick = onNavigateToContact
        ),
        MoreMenuItem(
            title = "🌐 فتح الموقع الإلكتروني المباشر",
            subtitle = "تصفح https://hanan.pro بالكامل داخل المتصفح",
            icon = Icons.Default.Language,
            badge = "LIVE",
            onClick = onOpenWebBrowser
        ),
        MoreMenuItem(
            title = "⚙️ إعدادات الإعلانات الذكية Monetag",
            subtitle = "تخصيص رابط SmartLink ومتابعة الإحصائيات",
            icon = Icons.Default.Settings,
            onClick = onOpenMonetagSettings
        )
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(LuxuryNavyDark)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = LuxuryNavyMedium),
                border = androidx.compose.foundation.BorderStroke(1.dp, LuxuryGold.copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(LuxuryGold.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Apartment,
                            contentDescription = null,
                            tint = LuxuryGold,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = "منصة عقارات النخبة",
                            color = Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Black
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "الإصدار 2.0 (Google Play Verified)",
                            color = LuxuryGold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        items(items.size) { index ->
            val item = items[index]
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { item.onClick() },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = LuxuryNavyMedium),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF1E293B))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(LuxuryNavyLight),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = null,
                            tint = LuxuryGold,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = item.title,
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            if (item.badge != null) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Surface(
                                    color = LuxuryGold.copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = item.badge,
                                        color = LuxuryGold,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = item.subtitle,
                            color = TextSecondary,
                            fontSize = 11.sp
                        )
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Color(0xFF64748B),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
