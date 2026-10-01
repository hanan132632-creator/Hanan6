package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PropertyCategory
import com.example.ui.theme.*

@Composable
fun CategorySelector(
    selectedCategory: PropertyCategory,
    onSelectCategory: (PropertyCategory) -> Unit,
    onOpenCalculatorClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .testTag("category_selector"),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PropertyCategory.values().forEach { category ->
            val isSelected = category == selectedCategory
            val bgColor by animateColorAsState(
                if (isSelected) LuxuryGold else LuxuryNavyMedium,
                label = "category_bg"
            )
            val textColor by animateColorAsState(
                if (isSelected) Color.Black else TextPrimary,
                label = "category_text"
            )

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        if (isSelected) {
                            Brush.horizontalGradient(listOf(LuxuryGold, LuxuryGoldDark))
                        } else {
                            Brush.horizontalGradient(listOf(LuxuryNavyMedium, LuxuryNavySurface))
                        }
                    )
                    .border(
                        1.dp,
                        if (isSelected) LuxuryGoldLight else LuxuryNavyBorder,
                        RoundedCornerShape(20.dp)
                    )
                    .clickable { onSelectCategory(category) }
                    .padding(horizontal = 14.dp, vertical = 8.dp)
                    .testTag("category_chip_${category.name}"),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = category.iconEmoji,
                        fontSize = 14.sp
                    )
                    Text(
                        text = category.titleArabic,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = textColor,
                            fontSize = 13.sp
                        )
                    )
                }
            }
        }

        // Quick Financing Calculator Trigger Chip
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(
                    Brush.horizontalGradient(
                        listOf(EmeraldGreenDark.copy(alpha = 0.8f), EmeraldGreen.copy(alpha = 0.8f))
                    )
                )
                .border(1.dp, EmeraldGreenLight.copy(alpha = 0.6f), RoundedCornerShape(20.dp))
                .clickable { onOpenCalculatorClick() }
                .padding(horizontal = 14.dp, vertical = 8.dp)
                .testTag("calculator_quick_chip"),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "🧮",
                    fontSize = 14.sp
                )
                Text(
                    text = "حاسبة التمويل",
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 13.sp
                    )
                )
            }
        }
    }
}
