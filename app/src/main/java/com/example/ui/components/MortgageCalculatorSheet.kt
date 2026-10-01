package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.MortgageSimulation
import com.example.ui.theme.*
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MortgageCalculatorSheet(
    simulation: MortgageSimulation,
    onPriceChange: (Double) -> Unit,
    onDownPaymentChange: (Double) -> Unit,
    onLoanTermChange: (Int) -> Unit,
    onProfitRateChange: (Double) -> Unit,
    onSakaniToggle: (Boolean) -> Unit,
    onSaveClick: () -> Unit,
    onWhatsAppConsultClick: () -> Unit,
    onDismiss: () -> Unit
) {
    val numberFormat = NumberFormat.getNumberInstance(Locale.US)
    val scrollState = rememberScrollState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = LuxuryNavyDark,
        tonalElevation = 16.dp,
        dragHandle = {
            BottomSheetDefaults.DragHandle(color = LuxuryGold)
        },
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 32.dp)
                .verticalScroll(scrollState)
                .testTag("mortgage_calculator_sheet")
        ) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "🧮", fontSize = 24.sp)
                    Column {
                        Text(
                            text = "حاسبة التمويل والأقساط",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextPrimary
                            )
                        )
                        Text(
                            text = "حساب الأقساط الشهرية للمرابحة العقارية",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = LuxuryGold,
                                fontSize = 11.sp
                            )
                        )
                    }
                }

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(LuxuryNavyMedium)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "إغلاق",
                        tint = TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Highlighted Monthly Installment Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.5.dp, LuxuryGoldDark.copy(alpha = 0.6f), RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = LuxuryNavyMedium)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "القسط الشهري التقديري",
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = LuxuryGoldLight,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = numberFormat.format(simulation.monthlyInstallment.toInt()),
                            style = MaterialTheme.typography.headlineLarge.copy(
                                color = EmeraldGreen,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 32.sp
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

                    Spacer(modifier = Modifier.height(12.dp))

                    // Key stats row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(LuxuryNavySurface)
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        StatItem(
                            label = "الدفعة الأولى",
                            value = "${numberFormat.format(simulation.downPaymentAmount.toInt())} ر.س"
                        )
                        StatItem(
                            label = "مبلغ التمويل",
                            value = "${numberFormat.format(simulation.loanAmount.toInt())} ر.س"
                        )
                        StatItem(
                            label = "الراتب المقترح",
                            value = "${numberFormat.format(simulation.minRecommendedSalary.toInt())} ر.س"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Property Price Selector
            Text(
                text = "سعر العقار: ${numberFormat.format(simulation.propertyPrice.toInt())} ر.س",
                style = MaterialTheme.typography.titleSmall.copy(color = TextPrimary, fontWeight = FontWeight.Bold)
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

            // Down Payment %
            Text(
                text = "نسبة الدفعة الأولى: ${simulation.downPaymentPercentage.toInt()}% (${numberFormat.format(simulation.downPaymentAmount.toInt())} ر.س)",
                style = MaterialTheme.typography.titleSmall.copy(color = TextPrimary, fontWeight = FontWeight.Bold)
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

            // Loan Term Years
            Text(
                text = "مدة التمويل: ${simulation.loanTermYears} سنة (${simulation.totalMonths} شهر)",
                style = MaterialTheme.typography.titleSmall.copy(color = TextPrimary, fontWeight = FontWeight.Bold)
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

            // Annual Profit Rate
            Text(
                text = "هامش الربح السنوي التقديري: ${String.format(Locale.US, "%.2f", simulation.annualProfitRate)}%",
                style = MaterialTheme.typography.titleSmall.copy(color = TextPrimary, fontWeight = FontWeight.Bold)
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

            // Sakani Program Switch
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(LuxuryNavyMedium)
                    .padding(horizontal = 14.dp, vertical = 10.dp),
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
                        text = "خصم الدعم المباشر للأرباح الشهرية",
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

            Spacer(modifier = Modifier.height(20.dp))

            // Action Buttons Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Save Calculation Button
                OutlinedButton(
                    onClick = onSaveClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("save_mortgage_button"),
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

                // WhatsApp Financing Consultant Button
                Button(
                    onClick = onWhatsAppConsultClick,
                    modifier = Modifier
                        .weight(1.3f)
                        .height(48.dp)
                        .testTag("consult_mortgage_whatsapp_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WhatsAppGreen,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "💬", fontSize = 16.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "استشارة تمويلية", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun StatItem(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
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
