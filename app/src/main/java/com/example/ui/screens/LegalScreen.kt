package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

enum class LegalContentType {
    PRIVACY_POLICY,
    TERMS_OF_SERVICE,
    ABOUT_US
}

@Composable
fun LegalScreen(
    contentType: LegalContentType,
    onBackClick: () -> Unit
) {
    val title = when (contentType) {
        LegalContentType.PRIVACY_POLICY -> "سياسة الخصوصية وحماية البيانات"
        LegalContentType.TERMS_OF_SERVICE -> "شروط الاستخدام والاتفاقية"
        LegalContentType.ABOUT_US -> "عن منصة وتطبيق عقارات النخبة"
    }

    val content = when (contentType) {
        LegalContentType.PRIVACY_POLICY -> """
            تاريخ آخر تحديث: أكتوبر 2026

            نحن في منصة وتطبيق «عقارات النخبة» نلتزم بأعلى معايير حماية وخصوصية بيانات عملائنا وزوارنا، وفقاً للأنظمة واللوائح الصادرة عن الهيئة الوطنية للأمن السيبراني ونظام حماية البيانات الشخصية في المملكة العربية السعودية ومتطلبات متجر Google Play.

            1. البيانات التي نجمعها:
            • بيانات الاتصال الاختيارية (الاسم، رقم الهاتف، البريد الإلكتروني) عند التواصل أو طلب معاينة عقار أو حاسبة تمويل.
            • المعلومات التقنية العامة (نوع الجهاز، إصدار النظام) لتحسين أداء واستقرار التطبيق.

            2. استخدام البيانات:
            • تسهيل التواصل المباشر بين المشتري والوسيط العقاري المعتمد.
            • حفظ العقارات المفضلة وسجل الحسابات التمويلية محلياً على جهازك.
            • تقديم إعلانات مخصصة وعروض ترويجية ذات صلة عبر شركائنا الإعلانيين (مثل Monetag / AdSense).

            3. عدم مشاركة البيانات:
            • لا نقوم إطلاقاً ببيع أو تأجير بياناتك الشخصية لأي طرف ثالث غير معتمد.

            4. التواصل مع مسؤول حماية البيانات:
            لأي استفسار بخصوص بياناتك، يمكنك التواصل عبر البريد: support@hanan.pro أو info@hanan.pro.
        """.trimIndent()

        LegalContentType.TERMS_OF_SERVICE -> """
            تاريخ السريان: أكتوبر 2026

            مرحباً بكم في منصة وتطبيق «عقارات النخبة». باستخدامك للتطبيق فإنك توافق على الالتزام بالشروط والأحكام التالية:

            1. طبيعة الخدمة:
            • تعد المنصة وسيطاً تقنياً وإعلانياً لعرض الفلل والقصور والشقق الفاخرة والخدمات العقارية المصاحبة.
            • جميع الأسعار والمواصفات المعروضة يتم تحديثها دورياً بالتعاون مع المطورين والملاك والوسطاء المرخصين من الهيئة العامة للعقار.

            2. حسابات التمويل وسكني:
            • النتائج المعروضة في حاسبة التمويل العقاري هي نتائج تقديرية وتقريبية استرشادية، وتخضع للشروط الائتمانية المعتمدة لدى البنوك والجهات التمويلية المرخصة من البنك المركزي السعودي (ساما).

            3. الملكية الفكرية:
            • كافة التصاميم والشعارات والمحتوى النصي والمرئي في هذا التطبيق مملوكة لـ «عقارات النخبة» ومحمية بموجب أنظمة حماية حقوق المؤلف والعلامات التجارية.
        """.trimIndent()

        LegalContentType.ABOUT_US -> """
            «عقارات النخبة» هي المنصة الرائدة المتخصصة في تسويق وإدارة وتملك العقارات الفاخرة والاستثمارية الاستثنائية في المملكة العربية السعودية.

            رؤيتنا:
            أن نكون الوجهة الأولى والموثوقة للمستثمرين ونخبة الباحثين عن الفخامة والتميز في السوق العقاري السعودي، من خلال تقديم تجربة رقمية متكاملة تجمع بين السرعة والشفافية وأعلى معايير الجودة.

            قيمنا:
            • الشفافية المطلقة والالتزام بالتراخيص الرسمية المعتمدة (فال / إيجار / سكني).
            • الابتكار الرقمي والجولات الافتراضية التفاعلية 360.
            • تقديم استشارات مالية وقانونية وهندسية مدعومة بالبيانات الدقيقة.
        """.trimIndent()
    }

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
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = onBackClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = LuxuryNavyMedium,
                        contentColor = LuxuryGold
                    )
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "رجوع")
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = LuxuryNavyMedium),
                border = androidx.compose.foundation.BorderStroke(1.dp, LuxuryGold.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = title,
                        color = LuxuryGold,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 12.dp),
                        color = Color(0xFF1E293B)
                    )

                    Text(
                        text = content,
                        color = Color(0xFFE2E8F0),
                        fontSize = 13.sp,
                        lineHeight = 22.sp
                    )
                }
            }
        }
    }
}
