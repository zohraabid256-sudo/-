package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.BacRepository
import com.example.ui.theme.CardBackground
import com.example.ui.theme.CardBorder
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldBright
import com.example.ui.theme.GoldLight
import com.example.ui.theme.PinkBright
import com.example.ui.theme.PinkGlossy
import com.example.ui.theme.PinkSoft
import com.example.ui.theme.RoyalBlueDeep
import com.example.ui.theme.RoyalBlueLight
import com.example.ui.theme.RoyalBluePrimary
import com.example.ui.theme.RoyalBlueSecondary
import com.example.ui.theme.RoyalBlueSurface

enum class AppDestination {
    SPLASH,
    HOME,
    LESSONS,
    SUMMARIES,
    TERMS,
    FIGURES,
    QUIZ,
    SMART_BOT,
    FAVORITES,
    PROGRESS,
    CONTROL_PANEL,
    PHONE_PREVIEW
}

data class MainCardItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val badge: String? = null,
    val primaryColor: Color,
    val destination: AppDestination
)

@Composable
fun HomeScreen(
    onNavigate: (AppDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    val cards = listOf(
        MainCardItem(
            id = "lessons",
            title = "📖 الدروس",
            subtitle = "تاريخ وجغرافيا • الوحدات والدروس",
            icon = Icons.Default.MenuBook,
            badge = "المنهاج كامل",
            primaryColor = GoldBright,
            destination = AppDestination.LESSONS
        ),
        MainCardItem(
            id = "summaries",
            title = "📝 الملخصات",
            subtitle = "جداول مقارنة وخرائط مراجعة سريعة",
            icon = Icons.Default.Description,
            badge = "مكثف",
            primaryColor = PinkBright,
            destination = AppDestination.SUMMARIES
        ),
        MainCardItem(
            id = "terms",
            title = "📚 المصطلحات",
            subtitle = "قاموس شامل مع البحث والمفضلة",
            icon = Icons.Default.Translate,
            badge = "بكالوريا",
            primaryColor = GoldBright,
            destination = AppDestination.TERMS
        ),
        MainCardItem(
            id = "figures",
            title = "👤 الشخصيات",
            subtitle = "أبرز الشخصيات وجنسياتها وإنجازاتها",
            icon = Icons.Default.Person,
            badge = "شامل",
            primaryColor = PinkGlossy,
            destination = AppDestination.FIGURES
        ),
        MainCardItem(
            id = "quiz",
            title = "🧠 اختبارات تفاعلية",
            subtitle = "20 سؤالاً مع التصحيح الفوري والنقاط",
            icon = Icons.Default.Psychology,
            badge = "تحدي 20/20",
            primaryColor = GoldBright,
            destination = AppDestination.QUIZ
        ),
        MainCardItem(
            id = "bot",
            title = "🤖 اسأل الروبوت الذكي",
            subtitle = "مساعد ذكي خاص بأسئلة البكالوريا",
            icon = Icons.Default.SmartToy,
            badge = "AI ذكي",
            primaryColor = PinkBright,
            destination = AppDestination.SMART_BOT
        ),
        MainCardItem(
            id = "favorites",
            title = "⭐ المفضلة",
            subtitle = "المصطلحات والشخصيات المحفوظة",
            icon = Icons.Default.Star,
            badge = null,
            primaryColor = GoldBright,
            destination = AppDestination.FAVORITES
        ),
        MainCardItem(
            id = "progress",
            title = "📊 تقدمي ونتائجي",
            subtitle = "إحصائيات الإنجاز ومعدل الاختبارات",
            icon = Icons.Default.BarChart,
            badge = "متابعة",
            primaryColor = PinkGlossy,
            destination = AppDestination.PROGRESS
        )
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(RoyalBlueDeep, Color(0xFF091B33), RoyalBlueDeep)
                )
            )
    ) {
        // Hero Header
        item(span = { GridItemSpan(2) }) {
            HomeHeaderSection(onNavigate = onNavigate)
        }

        // Action banner: Phone Mockup Showcase ("أعطني صورة للتطبيق عند تنزيله في الهاتف")
        item(span = { GridItemSpan(2) }) {
            PhoneMockupBanner(onClick = { onNavigate(AppDestination.PHONE_PREVIEW) })
        }

        // Control Panel quick access banner
        item(span = { GridItemSpan(2) }) {
            ControlPanelBanner(onClick = { onNavigate(AppDestination.CONTROL_PANEL) })
        }

        // Section Title
        item(span = { GridItemSpan(2) }) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(4.dp, 20.dp)
                        .background(GoldBright, RoundedCornerShape(2.dp))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "الأقسام الرئيسية للدراسة والتحضير",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // 8 Grid Cards
        items(cards) { card ->
            CardGridItem(card = card, onClick = { onNavigate(card.destination) })
        }

        // Motivational Footer
        item(span = { GridItemSpan(2) }) {
            Spacer(modifier = Modifier.height(12.dp))
            MotivationalFooter()
        }
    }
}

@Composable
fun HomeHeaderSection(
    onNavigate: (AppDestination) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, CardBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .background(
                    Brush.linearGradient(
                        colors = listOf(RoyalBlueSecondary, CardBackground)
                    )
                )
                .padding(18.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Circular App Emblem
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .border(2.dp, GoldBright, CircleShape)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_app_icon),
                        contentDescription = "شعار التطبيق",
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = BacRepository.appTitle,
                        color = GoldBright,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 22.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "🎓 ${BacRepository.appMotto}",
                        color = Color(0xFFF1F5F9),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Sub-bar with Teacher Name and Grade
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0x33000000), RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "إعداد: ${BacRepository.teacherName}",
                    color = PinkSoft,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )

                Surface(
                    color = Color(0x33FFC72C),
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GoldAccent)
                ) {
                    Text(
                        text = "منهاج كامل 2026/2027 🇩🇿",
                        color = GoldBright,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // 3-Unit Full Curriculum Counter Strip
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0x221E3A8A), RoundedCornerShape(10.dp))
                    .border(1.dp, Color(0x443B82F6), RoundedCornerShape(10.dp))
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "🏛️ 3 وحدات تاريخ", color = GoldBright, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Text(text = "10 وضعيات تعلمية", color = Color(0xFFCBD5E1), fontSize = 10.sp)
                }
                Box(modifier = Modifier.size(1.dp, 20.dp).background(Color(0x44CBD5E1)))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "🌍 3 وحدات جغرافيا", color = PinkBright, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Text(text = "8 وضعيات تعلمية", color = Color(0xFFCBD5E1), fontSize = 10.sp)
                }
                Box(modifier = Modifier.size(1.dp, 20.dp).background(Color(0x44CBD5E1)))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "🎓 بكالوريا تجريبية", color = GoldLight, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Text(text = "مع سلم التنقيط", color = Color(0xFFCBD5E1), fontSize = 10.sp)
                }
            }
        }
    }
}

@Composable
fun PhoneMockupBanner(
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF132F5C)),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, GoldAccent),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("open_phone_mockup_button")
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(46.dp)
                    .background(Color(0x44FFC72C), CircleShape)
                    .border(1.dp, GoldBright, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.PhoneAndroid,
                    contentDescription = "معاينة الهاتف",
                    tint = GoldBright,
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "📱 صورة التطبيق عند تنزيله في الهاتف",
                        color = GoldBright,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "عرض نموذج واجهة الهاتف الفعلية وجاهزية ملف APK",
                    color = Color(0xFFCBD5E1),
                    fontSize = 12.sp
                )
            }

            Surface(
                color = PinkGlossy,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "معاينة",
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun ControlPanelBanner(
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x55E91E63)),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("open_control_panel_button")
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(14.dp)
                .fillMaxWidth()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(42.dp)
                    .background(Color(0x33E91E63), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.AdminPanelSettings,
                    contentDescription = null,
                    tint = PinkBright,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "🌟 لوحة التحكم: معًا في طريق النجاح",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "تنظيم الدروس، المصطلحات، نتائج التلاميذ والإعلانات",
                    color = Color(0xFF94A3B8),
                    fontSize = 11.sp
                )
            }

            Text(
                text = "دخول 👈",
                color = PinkBright,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun CardGridItem(
    card: MainCardItem,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable(onClick = onClick)
            .testTag("home_card_${card.id}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0x330C2340), CircleShape)
                        .border(1.dp, card.primaryColor, CircleShape)
                ) {
                    Icon(
                        imageVector = card.icon,
                        contentDescription = card.title,
                        tint = card.primaryColor,
                        modifier = Modifier.size(22.dp)
                    )
                }

                card.badge?.let { badgeText ->
                    Surface(
                        color = Color(0x33FF2A6D),
                        shape = RoundedCornerShape(6.dp),
                        border = androidx.compose.foundation.BorderStroke(0.8.dp, PinkBright)
                    ) {
                        Text(
                            text = badgeText,
                            color = PinkSoft,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }
            }

            Column {
                Text(
                    text = card.title,
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = card.subtitle,
                    color = Color(0xFF94A3B8),
                    fontSize = 11.sp,
                    lineHeight = 15.sp,
                    maxLines = 2
                )
            }
        }
    }
}

@Composable
fun MotivationalFooter() {
    Surface(
        color = Color(0x22174284),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x33D4AF37)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(14.dp)
        ) {
            Text(
                text = "✨ نصيحة اليوم للتفوق في البكالوريا:",
                color = GoldBright,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "« المراجعة المنتظمة وحفظ المصطلحات أولاً بأول يضمنان لك أعلى العلامات بإذن الله »",
                color = Color(0xFFE2E8F0),
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
