package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.Property
import com.example.data.model.PropertyCategory
import com.example.ui.components.CategorySelector
import com.example.ui.components.MonetagBannerCard
import com.example.ui.components.PropertyCard
import com.example.ui.theme.*

@Composable
fun HomeScreen(
    properties: List<Property>,
    selectedCategory: PropertyCategory,
    onSelectCategory: (PropertyCategory) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    selectedCity: String,
    onSelectCity: (String) -> Unit,
    onPropertyClick: (Property) -> Unit,
    onWhatsAppClick: (Property) -> Unit,
    onBrochureClick: (Property) -> Unit,
    onMortgageClick: (Property) -> Unit,
    onToggleFavorite: (String) -> Unit,
    onOpenCalculatorClick: () -> Unit,
    adUrl: String = "https://omg10.com/4/11930497",
    adClickCount: Int = 0,
    onPreviewInAppAd: () -> Unit = {},
    onOpenExternalAd: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val cities = listOf("الكل", "الرياض", "جدة", "الدمام", "الخبر")
    val cityScrollState = rememberScrollState()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen"),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Welcome Hero Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .border(1.dp, LuxuryGoldDark.copy(alpha = 0.4f), RoundedCornerShape(20.dp)),
                colors = CardDefaults.cardColors(containerColor = LuxuryNavyMedium)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(LuxuryNavyDark, LuxuryNavyMedium, LuxuryNavySurface)
                            )
                        )
                        .padding(18.dp)
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(text = "👑", fontSize = 16.sp)
                            Text(
                                text = "مرحباً بك في عقارات النخبة",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = LuxuryGoldLight,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "اكتشف أرقى الفلل والشقق والأراضي في أرقى أحياء المملكة",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = TextPrimary,
                                fontWeight = FontWeight.Normal,
                                lineHeight = 20.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        // Search Input Field
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = onSearchQueryChange,
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("home_search_field"),
                            placeholder = {
                                Text(
                                    text = "ابحث بالاسم، المدينة أو الحي (مثال: حطين، النرجس)...",
                                    style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null,
                                    tint = LuxuryGold
                                )
                            },
                            trailingIcon = {
                                if (searchQuery.isNotEmpty()) {
                                    IconButton(onClick = { onSearchQueryChange("") }) {
                                        Icon(
                                            imageVector = Icons.Default.Clear,
                                            contentDescription = "مسح",
                                            tint = TextSecondary
                                        )
                                    }
                                }
                            },
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = LuxuryGold,
                                unfocusedBorderColor = LuxuryNavyBorder,
                                focusedContainerColor = LuxuryNavyDark,
                                unfocusedContainerColor = LuxuryNavyDark,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary
                            )
                        )
                    }
                }
            }
        }

        // City Quick Selector Pills
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(cityScrollState)
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "المدينة:",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = LuxuryGoldLight,
                        fontWeight = FontWeight.Bold
                    )
                )
                cities.forEach { city ->
                    val isSelected = city == selectedCity
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (isSelected) LuxuryGold else LuxuryNavyMedium)
                            .border(
                                1.dp,
                                if (isSelected) LuxuryGoldLight else LuxuryNavyBorder,
                                RoundedCornerShape(16.dp)
                            )
                            .clickable { onSelectCity(city) }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = city,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = if (isSelected) Color.Black else TextPrimary,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        )
                    }
                }
            }
        }

        // Categories Horizontal Bar
        item {
            Spacer(modifier = Modifier.height(6.dp))
            CategorySelector(
                selectedCategory = selectedCategory,
                onSelectCategory = onSelectCategory,
                onOpenCalculatorClick = onOpenCalculatorClick
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Sponsored Monetag Ad Banner Card
        item {
            MonetagBannerCard(
                adUrl = adUrl,
                clickCount = adClickCount,
                onPreviewInAppClick = onPreviewInAppAd,
                onOpenExternalClick = onOpenExternalAd,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
            )
        }

        // Section Title
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "عقارات مختارة وحصرية (${properties.size})",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "معتمدة من إيجار وفال",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = EmeraldGreen,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }

        // Property Cards List
        if (properties.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "🔍", fontSize = 36.sp)
                        Text(
                            text = "لا توجد عقارات مطابقة للبحث",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "جرب تغيير فلاتر المدينة أو التصنيف للوصول للمزيد",
                            style = MaterialTheme.typography.bodySmall.copy(color = TextSecondary)
                        )
                    }
                }
            }
        } else {
            items(properties, key = { it.id }) { property ->
                PropertyCard(
                    property = property,
                    onCardClick = { onPropertyClick(property) },
                    onWhatsAppClick = { onWhatsAppClick(property) },
                    onBrochureClick = { onBrochureClick(property) },
                    onMortgageClick = { onMortgageClick(property) },
                    onToggleFavorite = { onToggleFavorite(property.id) }
                )
            }
        }
    }
}
