package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.SavedMortgageEntity
import com.example.data.model.MortgageSimulation
import com.example.ui.theme.*
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CalculatorScreen(
    simulation: MortgageSimulation,
    onPriceChange: (Double) -> Unit,
    onDownPaymentChange: (Double) -> Unit,
    onLoanTermChange: (Int) -> Unit,
    onProfitRateChange: (Double) -> Unit,
    onSakaniToggle: (Boolean) -> Unit,
    onSaveClick: () -> Unit,
    onWhatsAppConsultClick: () -> Unit,
    savedMortgages: List<SavedMortgageEntity>,
    onDeleteSavedMortgage: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val numberFormat = NumberFormat.getNumberInstance(Locale.US)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("calculator_screen"),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // Title
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "🧮", fontSize = 24.sp)
                Column {
                    Text(
                        text = "حاسبة التمويل العقاري الذكية",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "محاكاة فورية للأقساط الشهرية وهوامش المرابحة",
                        style = MaterialTheme.typography.bodySmall.copy(color = LuxuryGold)
                    )
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
        }

        // Highlight Result Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .border(1.5.dp, LuxuryGoldDark, RoundedCornerShape(18.dp)),
                colors = CardDefaults.cardColors(containerColor = LuxuryNavyMedium)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "القسط الشهري المتوقع",
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = LuxuryGoldLight,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = numberFormat.format(simulation.monthlyInstallment.toInt()),
                            style = MaterialTheme.typography.headlineLarge.copy(
                                color = EmeraldGreen,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 34.sp
                            )
                        )
                        Text(
                            text = "ر.س / شهر",
                            style = MaterialTheme.typography.titleSmall.copy(
                                color = EmeraldGreenLight,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            ),
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(LuxuryNavySurface)
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        CalcStat(
                            title = "الدفعة الأولى",
                            value = "${numberFormat.format(simulation.downPaymentAmount.toInt())} ر.س"
                        )
                        CalcStat(
                            title = "أصل التمويل",
                            value = "${numberFormat.format(simulation.loanAmount.toInt())} ر.س"
                        )
                        CalcStat(
                            title = "إجمالي الأرباح",
                            value = "${numberFormat.format(simulation.totalProfitAmount.toInt())} ر.س"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))
        }

        // Sliders & Controls
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = LuxuryNavyMedium),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, LuxuryNavyBorder)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    // Property Price
                    Text(
                        text = "سعر العقار: ${numberFormat.format(simulation.propertyPrice.toInt())} ر.س",
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Slider(
                        value = simulation.propertyPrice.toFloat(),
                        onValueChange = { onPriceChange(it.toDouble()) },
                        valueRange = 500000f..15000000f,
                        steps = 58,
                        colors = SliderDefaults.colors(
                            thumbColor = LuxuryGold,
                            activeTrackColor = LuxuryGold,
                            inactiveTrackColor = LuxuryNavyLight
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Down Payment %
                    Text(
                        text = "نسبة الدفعة الأولى: ${simulation.downPaymentPercentage.toInt()}% (${numberFormat.format(simulation.downPaymentAmount.toInt())} ر.س)",
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Slider(
                        value = simulation.downPaymentPercentage.toFloat(),
                        onValueChange = { onDownPaymentChange(it.toDouble()) },
                        valueRange = 10f..50f,
                        steps = 8,
                        colors = SliderDefaults.colors(
                            thumbColor = LuxuryGold,
                            activeTrackColor = LuxuryGold,
                            inactiveTrackColor = LuxuryNavyLight
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Loan Term Years
                    Text(
                        text = "مدة التمويل: ${simulation.loanTermYears} سنة (${simulation.totalMonths} شهر)",
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Slider(
                        value = simulation.loanTermYears.toFloat(),
                        onValueChange = { onLoanTermChange(it.toInt()) },
                        valueRange = 5f..30f,
                        steps = 25,
                        colors = SliderDefaults.colors(
                            thumbColor = LuxuryGold,
                            activeTrackColor = LuxuryGold,
                            inactiveTrackColor = LuxuryNavyLight
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Annual Rate
                    Text(
                        text = "هامش الربح السنوي: ${String.format(Locale.US, "%.2f", simulation.annualProfitRate)}%",
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = TextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Slider(
                        value = simulation.annualProfitRate.toFloat(),
                        onValueChange = { onProfitRateChange(it.toDouble()) },
                        valueRange = 2.5f..7.0f,
                        steps = 18,
                        colors = SliderDefaults.colors(
                            thumbColor = LuxuryGold,
                            activeTrackColor = LuxuryGold,
                            inactiveTrackColor = LuxuryNavyLight
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Sakani Program
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(LuxuryNavySurface)
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "دعم برنامج سكني للمواطنين",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    color = TextPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                text = "احتساب تخفيض هامش الربح",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = TextSecondary,
                                    fontSize = 11.sp
                                )
                            )
                        }
                        Switch(
                            checked = simulation.includeSakaniSupport,
                            onCheckedChange = onSakaniToggle,
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = LuxuryGold,
                                checkedTrackColor = LuxuryGoldGlow,
                                uncheckedTrackColor = LuxuryNavyLight
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Action Buttons Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onSaveClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("calculator_screen_save_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = LuxuryNavyMedium,
                        contentColor = LuxuryGoldLight
                    ),
                    border = androidx.compose.foundation.BorderStroke(1.dp, LuxuryGoldDark)
                ) {
                    Icon(
                        imageVector = Icons.Default.Bookmark,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "حفظ الحسبة", fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = onWhatsAppConsultClick,
                    modifier = Modifier
                        .weight(1.3f)
                        .height(48.dp)
                        .testTag("calculator_screen_whatsapp_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WhatsAppGreen,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "💬", fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "استشارة عبر واتساب", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Saved Mortgages Section
        if (savedMortgages.isNotEmpty()) {
            item {
                Text(
                    text = "الحسبات المحفوظة (${savedMortgages.size})",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(savedMortgages, key = { it.id }) { saved ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = LuxuryNavyMedium),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, LuxuryNavyBorder)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = saved.propertyTitle,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    color = TextPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                text = "السعر: ${numberFormat.format(saved.propertyPrice.toInt())} ر.س | ${saved.loanTermYears} سنة",
                                style = MaterialTheme.typography.bodySmall.copy(color = TextSecondary)
                            )
                            Text(
                                text = "القسط: ${numberFormat.format(saved.monthlyInstallment.toInt())} ر.س/شهر",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    color = EmeraldGreen,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        IconButton(
                            onClick = { onDeleteSavedMortgage(saved.id) },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "حذف",
                                tint = DangerRed.copy(alpha = 0.8f)
                            )
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
private fun CalcStat(title: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall.copy(
                color = TextSecondary,
                fontSize = 10.sp
            )
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall.copy(
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        )
    }
}
