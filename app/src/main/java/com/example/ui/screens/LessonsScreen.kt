package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.BacExamPaper
import com.example.data.BacRepository
import com.example.data.LessonItem
import com.example.data.SubjectType
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonsScreen(
    favoriteLessonIds: Set<String>,
    onToggleFavorite: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedSubject by remember { mutableStateOf(SubjectType.HISTORY) }
    var selectedUnitFilter by remember { mutableStateOf(0) } // 0 = all, 1, 2, 3
    var showExamsSheet by remember { mutableStateOf(false) }

    val filteredLessons = remember(selectedSubject, selectedUnitFilter) {
        BacRepository.lessonsList.filter { lesson ->
            lesson.subject == selectedSubject &&
                (selectedUnitFilter == 0 || lesson.unitNumber == selectedUnitFilter)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "📚 منهاج الأستاذة عبيد زهرة",
                            color = GoldBright,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "الوحدات التعليمية الثلاث كاملة (3AS)",
                            color = Color(0xFFCBD5E1),
                            fontSize = 11.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("lessons_back_button")) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "رجوع",
                            tint = GoldBright
                        )
                    }
                },
                actions = {
                    Surface(
                        color = PinkGlossy,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable { showExamsSheet = !showExamsSheet }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Assignment,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (showExamsSheet) "الدروس" else "امتحانات مقترحة",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = RoyalBlueDeep)
            )
        },
        containerColor = RoyalBlueDeep,
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (showExamsSheet) {
                // Official Baccalaureate Mock Exams View
                BaccalaureateExamsView(
                    exams = BacRepository.bacExams,
                    onDismiss = { showExamsSheet = false }
                )
            } else {
                // Subject Switcher: التاريخ / الجغرافيا
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .background(Color(0xFF102747), RoundedCornerShape(12.dp))
                        .padding(4.dp)
                ) {
                    SubjectType.values().forEach { subject ->
                        val isSelected = selectedSubject == subject
                        Surface(
                            color = if (isSelected) GoldBright else Color.Transparent,
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    selectedSubject = subject
                                    selectedUnitFilter = 0
                                }
                                .testTag("subject_tab_${subject.name}")
                        ) {
                            Text(
                                text = if (subject == SubjectType.HISTORY) "🏛️ مادة التاريخ (3 وحدات)" else "🌍 مادة الجغرافيا (3 وحدات)",
                                color = if (isSelected) RoyalBlueDeep else Color(0xFF94A3B8),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(vertical = 10.dp),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
                    }
                }

                // Unit Filter Chips: جميع الوحدات، الوحدة 1، الوحدة 2، الوحدة 3
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        UnitFilterChip(
                            title = "جميع الوحدات (كامل المقرر)",
                            isSelected = selectedUnitFilter == 0,
                            onClick = { selectedUnitFilter = 0 }
                        )
                    }
                    item {
                        UnitFilterChip(
                            title = if (selectedSubject == SubjectType.HISTORY) "الوحدة 1: العالم والثنائية" else "الوحدة 1: الاقتصاد العالمي",
                            isSelected = selectedUnitFilter == 1,
                            onClick = { selectedUnitFilter = 1 }
                        )
                    }
                    item {
                        UnitFilterChip(
                            title = if (selectedSubject == SubjectType.HISTORY) "الوحدة 2: ثورة الجزائر" else "الوحدة 2: القوى الكبرى",
                            isSelected = selectedUnitFilter == 2,
                            onClick = { selectedUnitFilter = 2 }
                        )
                    }
                    item {
                        UnitFilterChip(
                            title = if (selectedSubject == SubjectType.HISTORY) "الوحدة 3: العالم الثالث وفلسطين" else "الوحدة 3: اقتصاد الجنوب",
                            isSelected = selectedUnitFilter == 3,
                            onClick = { selectedUnitFilter = 3 }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Lessons List
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredLessons, key = { it.id }) { lesson ->
                        DetailedLessonCard(
                            lesson = lesson,
                            isFavorite = favoriteLessonIds.contains(lesson.id),
                            onToggleFavorite = { onToggleFavorite(lesson.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun UnitFilterChip(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        color = if (isSelected) PinkGlossy else Color(0xFF132D52),
        shape = RoundedCornerShape(8.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) PinkBright else CardBorder),
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Text(
            text = title,
            color = if (isSelected) Color.White else Color(0xFFCBD5E1),
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

@Composable
fun LessonCardItem(
    lesson: LessonItem,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit
) {
    DetailedLessonCard(
        lesson = lesson,
        isFavorite = isFavorite,
        onToggleFavorite = onToggleFavorite
    )
}

@Composable
fun DetailedLessonCard(
    lesson: LessonItem,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, if (isExpanded) GoldAccent else CardBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("lesson_card_${lesson.id}")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = lesson.unitTitle,
                        color = PinkBright,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = lesson.lessonTitle,
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 22.sp
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onToggleFavorite) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "حفظ في المفضلة",
                            tint = if (isFavorite) GoldBright else Color(0xFF94A3B8)
                        )
                    }

                    IconButton(onClick = { isExpanded = !isExpanded }) {
                        Icon(
                            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = "عرض عناصر الوضعية",
                            tint = GoldBright
                        )
                    }
                }
            }

            // Competency summary tag
            if (lesson.competency.isNotBlank() && !isExpanded) {
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    color = Color(0x221E3A8A),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "🎯 الكفاءة: ${lesson.competency}",
                        color = Color(0xFF93C5FD),
                        fontSize = 11.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        maxLines = 2
                    )
                }
            }

            // Collapsed preview hint
            if (!isExpanded) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "اضغط للتفاصيل الكاملة: المعايير، الأسباب، الاستراتيجيات، والأسئلة الإدماجية ⬇️",
                    color = Color(0xFF94A3B8),
                    fontSize = 11.sp
                )
            }

            // Expanded content
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column(modifier = Modifier.padding(top = 10.dp)) {
                    // Full Competency Box
                    if (lesson.competency.isNotBlank()) {
                        Surface(
                            color = Color(0x2E2563EB),
                            shape = RoundedCornerShape(8.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF3B82F6)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = "🎯 الكفاءة المستهدفة للوضعية:",
                                    color = Color(0xFF93C5FD),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = lesson.competency,
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    lineHeight = 17.sp
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    // Introduction
                    if (lesson.introduction.isNotBlank()) {
                        Surface(
                            color = Color(0x22FFFFFF),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = "📝 مقدمة وإشكالية الوضعية:",
                                    color = GoldBright,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = lesson.introduction,
                                    color = Color(0xFFE2E8F0),
                                    fontSize = 12.sp,
                                    lineHeight = 18.sp
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    // Rich Sections (Situation Breakdown)
                    if (lesson.sections.isNotEmpty()) {
                        lesson.sections.forEach { section ->
                            Surface(
                                color = Color(0xFF0F2647),
                                shape = RoundedCornerShape(10.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text(
                                        text = section.title,
                                        color = GoldBright,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    section.items.forEach { item ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 2.dp),
                                            verticalAlignment = Alignment.Top
                                        ) {
                                            Text(text = "🔹 ", fontSize = 12.sp)
                                            Text(
                                                text = item,
                                                color = Color(0xFFE2E8F0),
                                                fontSize = 12.sp,
                                                lineHeight = 18.sp
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Dates & Chronology
                    if (lesson.datesOrFacts.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "🗓️ محطات وتواريخ مهمة للحفظ:",
                            color = PinkBright,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        lesson.datesOrFacts.forEach { dateItem ->
                            Surface(
                                color = Color(0x33000000),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 2.dp)
                            ) {
                                Text(
                                    text = dateItem,
                                    color = Color(0xFFCBD5E1),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }

                    // Exam Question & Model Answer Hints (سؤال تقويمي ونقاش للتلاميذ)
                    if (lesson.examQuestion.isNotBlank()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Surface(
                            color = Color(0x22EC4899),
                            shape = RoundedCornerShape(10.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, PinkBright),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = "❓ سؤال تقويمي / إدماجي للتلاميذ (نموذج بكالوريا):",
                                    color = PinkBright,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(3.dp))
                                Text(
                                    text = lesson.examQuestion,
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    lineHeight = 17.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                if (lesson.examAnswerHints.isNotBlank()) {
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "💡 عناصر الإجابة والتلميحات للمصحح:",
                                        color = GoldBright,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = lesson.examAnswerHints,
                                        color = Color(0xFFFDE68A),
                                        fontSize = 11.sp,
                                        lineHeight = 16.sp
                                    )
                                }
                            }
                        }
                    }

                    // Exam Tips
                    if (lesson.examTips.isNotBlank()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Surface(
                            color = Color(0x33FFC72C),
                            shape = RoundedCornerShape(10.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, GoldAccent)
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lightbulb,
                                    contentDescription = null,
                                    tint = GoldBright,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(
                                        text = "توجيه الأستاذة عبيد زهرة:",
                                        color = GoldBright,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = lesson.examTips,
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        lineHeight = 17.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BaccalaureateExamsView(
    exams: List<BacExamPaper>,
    onDismiss: () -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Surface(
                color = Color(0x33FFC72C),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, GoldAccent),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "🎓 امتحانات بكالوريا تجريبية مقترحة وشبكات التقييم",
                        color = GoldBright,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "نماذج امتحانات كاملة وفق الطرح الرسمي لوزارة التربية الوطنية من إعداد الأستاذة عبيد زهرة، تتضمن الجزء الأول (المصطلحات، الشخصيات، التواريخ، والخريطة) والجزء الثاني (المقال التاريخي وسلم التنقيط بالتفصيل).",
                        color = Color(0xFFE2E8F0),
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        items(exams, key = { it.id }) { exam ->
            ExamPaperCard(exam = exam)
        }
    }
}

@Composable
fun ExamPaperCard(exam: BacExamPaper) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(containerColor = CardBackground),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, if (isExpanded) GoldAccent else CardBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = exam.title,
                color = GoldBright,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "${exam.unitTitle} | المدة: ${exam.duration} | المعامل: ${exam.coefficient}",
                color = Color(0xFF94A3B8),
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                color = if (isExpanded) GoldBright else PinkGlossy,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded }
            ) {
                Text(
                    text = if (isExpanded) "إخفاء تفاصيل الامتحان ⬆️" else "عرض نص الامتحان وسلّم التنقيط النموذجي ⬇️",
                    color = if (isExpanded) RoyalBlueDeep else Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(vertical = 8.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    // Part 1: 12 Points
                    Surface(
                        color = Color(0xFF0F2647),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "الجزء الأول (12 نقطة):",
                                color = GoldBright,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "أ) المصطلحات المطلوب تعريفها:",
                                color = PinkBright,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            exam.termsToDefine.forEach { term ->
                                Text(
                                    text = "• $term",
                                    color = Color(0xFFE2E8F0),
                                    fontSize = 12.sp,
                                    lineHeight = 17.sp,
                                    modifier = Modifier.padding(vertical = 2.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "ب) الشخصيات التاريخية:",
                                color = PinkBright,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            exam.figuresToDefine.forEach { figure ->
                                Text(
                                    text = "• $figure",
                                    color = Color(0xFFE2E8F0),
                                    fontSize = 12.sp,
                                    lineHeight = 17.sp,
                                    modifier = Modifier.padding(vertical = 2.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "ج) التواريخ والأحداث المعلمية:",
                                color = PinkBright,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            exam.datesAndEvents.forEach { (date, event) ->
                                Text(
                                    text = "• $date: $event",
                                    color = Color(0xFFCBD5E1),
                                    fontSize = 12.sp,
                                    modifier = Modifier.padding(vertical = 1.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "د) التوقيع على الخريطة:",
                                color = PinkBright,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = exam.mapTask,
                                color = Color(0xFFE2E8F0),
                                fontSize = 12.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Part 2: Essay 8 Points
                    Surface(
                        color = Color(0xFF0F2647),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "الجزء الثاني: المقال التاريخي (08 نقاط):",
                                color = GoldBright,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = exam.essayTopicContext,
                                color = Color(0xFFCBD5E1),
                                fontSize = 12.sp,
                                lineHeight = 17.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "المطلوب:",
                                color = PinkBright,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            exam.essayQuestions.forEach { q ->
                                Text(
                                    text = q,
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(vertical = 2.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "عناصر الإجابة النموذجية وسلم التنقيط:",
                                color = GoldBright,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = exam.modelAnswerHints,
                                color = Color(0xFFFDE68A),
                                fontSize = 11.sp,
                                lineHeight = 16.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Scoring Breakdown
                    Surface(
                        color = Color(0x33000000),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(
                                text = "📊 سلم التنقيط الرسمي (20/20):",
                                color = Color(0xFF93C5FD),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                            exam.scoringGuide.forEach { (part, pts) ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = part, color = Color(0xFFCBD5E1), fontSize = 11.sp)
                                    Text(text = pts, color = GoldBright, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
