package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.BacRepository
import com.example.ui.theme.CardBackground
import com.example.ui.theme.CardBorder
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldBright
import com.example.ui.theme.GreenSuccess
import com.example.ui.theme.PinkBright
import com.example.ui.theme.PinkGlossy
import com.example.ui.theme.PinkSoft
import com.example.ui.theme.RoyalBlueDeep
import com.example.ui.theme.RoyalBlueSecondary
import kotlinx.coroutines.launch

data class ControlModuleItem(
    val id: String,
    val title: String,
    val description: String,
    val icon: ImageVector,
    val countTag: String,
    val color: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ControlPanelScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val managementModules = listOf(
        ControlModuleItem(
            id = "lessons",
            title = "تنظيم الدروس والوحدات",
            description = "إدارة محتوى المنهاج الدراسي، إضافة وتعديل وحدات التاريخ والجغرافيا",
            icon = Icons.Default.MenuBook,
            countTag = "${BacRepository.lessonsList.size} دروس مبرمجة",
            color = GoldBright
        ),
        ControlModuleItem(
            id = "terms",
            title = "إدارة المصطلحات والمفاهيم",
            description = "تحديث وتنسيق شروحات المصطلحات الرسمية للبكالوريا",
            icon = Icons.Default.Translate,
            countTag = "${BacRepository.termsList.size} مصطلحاً",
            color = PinkBright
        ),
        ControlModuleItem(
            id = "figures",
            title = "تنظيم الشخصيات التاريخية",
            description = "إدارة بطاقات الشخصيات وجنسياتها وأهم إنجازاتها",
            icon = Icons.Default.Person,
            countTag = "${BacRepository.figuresList.size} شخصيات",
            color = GoldBright
        ),
        ControlModuleItem(
            id = "summaries",
            title = "الملخصات وجداول المقارنة",
            description = "تنسيق وتحديث المخططات الذهنية وجداول المقارنة السريعة",
            icon = Icons.Default.Description,
            countTag = "${BacRepository.summariesList.size} ملخصات",
            color = PinkGlossy
        ),
        ControlModuleItem(
            id = "tests",
            title = "بنك الاختبارات والأسئلة",
            description = "إعداد وتعديل أسئلة الاختيار من متعدد والتصحيح النموذجي",
            icon = Icons.Default.Psychology,
            countTag = "${BacRepository.quizQuestions.size} سؤالاً تفاعلياً",
            color = GoldBright
        ),
        ControlModuleItem(
            id = "qa",
            title = "الأسئلة والأجوبة للروبوت الذكي",
            description = "تحديث المعجم المعرفي للروبوت وإجابات البكالوريا الفورية",
            icon = Icons.Default.QuestionAnswer,
            countTag = "إجابات فورية مدمجة",
            color = PinkBright
        ),
        ControlModuleItem(
            id = "results",
            title = "نتائج ومتابعة التلاميذ",
            description = "استعراض مستويات الإنجاز ومعدلات نتائج الاختبارات للتلاميذ",
            icon = Icons.Default.People,
            countTag = "مزامنة محلية",
            color = Color(0xFF38BDF8)
        ),
        ControlModuleItem(
            id = "alerts",
            title = "الإعلانات والتنبيهات",
            description = "نشر نصائح وتوجيهات الأستاذة عبيد زهرة الدورية",
            icon = Icons.Default.Campaign,
            countTag = "${BacRepository.teacherAnnouncements.size} تنبيهات",
            color = GreenSuccess
        )
    )

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "🌟 معًا في طريق النجاح",
                        color = GoldBright,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("control_panel_back_button")) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "رجوع",
                            tint = GoldBright
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = RoyalBlueDeep)
            )
        },
        containerColor = RoyalBlueDeep,
        modifier = modifier
    ) { innerPadding ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Control Hub Welcome Header
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    shape = RoundedCornerShape(18.dp),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, GoldAccent),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(44.dp)
                                    .background(Color(0x33FFC72C), CircleShape)
                                    .border(1.dp, GoldBright, CircleShape)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = null,
                                    tint = GoldBright,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "لوحة تنظيم وإدارة المحتوى",
                                    color = Color.White,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "إشراف: ${BacRepository.teacherName}",
                                    color = PinkBright,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "من خلال هذه اللوحة، يُنظّم محتوى المنهاج كاملاً ويمكن تحديثه وتوسيعه لاحقاً دون إعادة بناء فكرة التطبيق.",
                            color = Color(0xFFCBD5E1),
                            fontSize = 12.sp,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            // Section: Teacher Announcements
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = null,
                        tint = PinkBright,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "توجيهات وإعلانات الأستاذة عبيد زهرة:",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            items(BacRepository.teacherAnnouncements, key = { it.id }) { ann ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF102747)),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x44E91E63)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = ann.title,
                                color = GoldBright,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                            Surface(
                                color = Color(0x33FF4081),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    text = ann.tag,
                                    color = PinkSoft,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = ann.content,
                            color = Color(0xFFE2E8F0),
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            // Section: Management Modules
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "وحدات التنظيم المتاحة:",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            items(managementModules, key = { it.id }) { module ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    shape = RoundedCornerShape(14.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "تم فتح وحدة: ${module.title} (الحالة: نشطة ومحدثة لدفعة 2026)"
                                )
                            }
                        }
                        .testTag("control_module_${module.id}")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(42.dp)
                                .background(Color(0x33000000), CircleShape)
                                .border(1.dp, module.color, CircleShape)
                        ) {
                            Icon(
                                imageVector = module.icon,
                                contentDescription = null,
                                tint = module.color,
                                modifier = Modifier.size(22.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = module.title,
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = module.description,
                                color = Color(0xFF94A3B8),
                                fontSize = 11.sp,
                                lineHeight = 15.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Surface(
                            color = Color(0x33FFC72C),
                            shape = RoundedCornerShape(8.dp),
                            border = androidx.compose.foundation.BorderStroke(0.8.dp, GoldAccent)
                        ) {
                            Text(
                                text = module.countTag,
                                color = GoldBright,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
