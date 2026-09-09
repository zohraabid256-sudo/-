package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.BacRepository
import com.example.data.QuizQuestion
import com.example.data.QuizResultRecord
import com.example.data.SubjectType
import com.example.ui.theme.CardBackground
import com.example.ui.theme.CardBorder
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldBright
import com.example.ui.theme.GreenSuccess
import com.example.ui.theme.PinkBright
import com.example.ui.theme.PinkGlossy
import com.example.ui.theme.PinkSoft
import com.example.ui.theme.RedIncorrect
import com.example.ui.theme.RoyalBlueDeep
import com.example.ui.theme.RoyalBluePrimary
import com.example.ui.theme.RoyalBlueSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    onSaveResult: (QuizResultRecord) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val questions = BacRepository.quizQuestions
    val totalQuestions = questions.size

    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }
    var isAnswerSubmitted by remember { mutableStateOf(false) }
    var currentScore by remember { mutableIntStateOf(0) }
    var isQuizCompleted by remember { mutableStateOf(false) }

    val currentQuestion = questions[currentQuestionIndex]

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (isQuizCompleted) "🏆 النتيجة النهائية" else "🧠 اختبار البكالوريا التفاعلي",
                        color = GoldBright,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("quiz_back_button")) {
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
        if (isQuizCompleted) {
            QuizSummaryView(
                score = currentScore,
                total = totalQuestions,
                onRestart = {
                    currentQuestionIndex = 0
                    selectedOptionIndex = null
                    isAnswerSubmitted = false
                    currentScore = 0
                    isQuizCompleted = false
                },
                onBackToHome = onBack,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Progress and Score Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "السؤال ${currentQuestionIndex + 1} من $totalQuestions",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Surface(
                        color = Color(0x33FFC72C),
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GoldAccent)
                    ) {
                        Text(
                            text = "النقاط: $currentScore / $totalQuestions",
                            color = GoldBright,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Progress Indicator
                LinearProgressIndicator(
                    progress = { (currentQuestionIndex + 1).toFloat() / totalQuestions },
                    color = GoldBright,
                    trackColor = Color(0xFF163259),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Question Card
                Card(
                    colors = CardDefaults.cardColors(containerColor = CardBackground),
                    shape = RoundedCornerShape(18.dp),
                    border = androidx.compose.foundation.BorderStroke(1.5.dp, CardBorder),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Surface(
                            color = if (currentQuestion.subject == SubjectType.HISTORY) Color(0x33FFC72C) else Color(0x33FF2A6D),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = if (currentQuestion.subject == SubjectType.HISTORY) "🏛️ مادة التاريخ" else "🌍 مادة الجغرافيا",
                                color = if (currentQuestion.subject == SubjectType.HISTORY) GoldBright else PinkSoft,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = currentQuestion.question,
                            color = Color.White,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 24.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Options List (Multiple Choice)
                currentQuestion.options.forEachIndexed { index, optionText ->
                    val isSelected = selectedOptionIndex == index
                    val isCorrect = index == currentQuestion.correctOptionIndex

                    val containerColor = when {
                        !isAnswerSubmitted && isSelected -> Color(0xFF1E3A6D)
                        isAnswerSubmitted && isCorrect -> Color(0x4010B981)
                        isAnswerSubmitted && isSelected && !isCorrect -> Color(0x40EF4444)
                        else -> CardBackground
                    }

                    val borderColor = when {
                        !isAnswerSubmitted && isSelected -> GoldBright
                        isAnswerSubmitted && isCorrect -> GreenSuccess
                        isAnswerSubmitted && isSelected && !isCorrect -> RedIncorrect
                        else -> CardBorder
                    }

                    Card(
                        colors = CardDefaults.cardColors(containerColor = containerColor),
                        shape = RoundedCornerShape(14.dp),
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, borderColor),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable(enabled = !isAnswerSubmitted) {
                                selectedOptionIndex = index
                            }
                            .testTag("quiz_option_$index")
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
                                    .size(30.dp)
                                    .background(
                                        color = when {
                                            isAnswerSubmitted && isCorrect -> GreenSuccess
                                            isAnswerSubmitted && isSelected && !isCorrect -> RedIncorrect
                                            isSelected -> GoldBright
                                            else -> Color(0xFF102747)
                                        },
                                        shape = CircleShape
                                    )
                            ) {
                                when {
                                    isAnswerSubmitted && isCorrect -> {
                                        Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                                    }
                                    isAnswerSubmitted && isSelected && !isCorrect -> {
                                        Icon(Icons.Default.Close, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                                    }
                                    else -> {
                                        Text(
                                            text = "${index + 1}",
                                            color = if (isSelected) RoyalBlueDeep else Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = optionText,
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                // Instant pedagogical explanation
                AnimatedVisibility(visible = isAnswerSubmitted) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F264A)),
                        shape = RoundedCornerShape(14.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, GoldAccent),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = GoldBright,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "التصحيح والشرح المنهجي للأستاذة عبيد زهرة:",
                                    color = GoldBright,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = currentQuestion.explanation,
                                color = Color(0xFFE2E8F0),
                                fontSize = 13.sp,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Bottom Action Button
                if (!isAnswerSubmitted) {
                    Button(
                        onClick = {
                            if (selectedOptionIndex != null) {
                                isAnswerSubmitted = true
                                if (selectedOptionIndex == currentQuestion.correctOptionIndex) {
                                    currentScore++
                                }
                            }
                        },
                        enabled = selectedOptionIndex != null,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GoldBright,
                            contentColor = RoyalBlueDeep,
                            disabledContainerColor = Color(0xFF475569),
                            disabledContentColor = Color(0xFF94A3B8)
                        ),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("submit_answer_button")
                    ) {
                        Text(
                            text = "تأكيد الإجابة والتصحيح الفوري ✨",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                } else {
                    Button(
                        onClick = {
                            if (currentQuestionIndex + 1 < totalQuestions) {
                                currentQuestionIndex++
                                selectedOptionIndex = null
                                isAnswerSubmitted = false
                            } else {
                                // Save Record
                                val percentage = (currentScore * 100) / totalQuestions
                                val feedback = getMotivationalMessage(currentScore, totalQuestions)
                                onSaveResult(
                                    QuizResultRecord(
                                        score = currentScore,
                                        total = totalQuestions,
                                        percentage = percentage,
                                        dateStr = "اليوم",
                                        motivationalFeedback = feedback
                                    )
                                )
                                isQuizCompleted = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PinkBright,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("next_question_button")
                    ) {
                        Text(
                            text = if (currentQuestionIndex + 1 < totalQuestions) "السؤال التالي 👈" else "عرض النتيجة النهائية 🏆",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun QuizSummaryView(
    score: Int,
    total: Int,
    onRestart: () -> Unit,
    onBackToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val percentage = (score * 100) / total
    val motivationalFeedback = getMotivationalMessage(score, total)

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(110.dp)
                .background(Color(0x33FFC72C), CircleShape)
                .border(3.dp, GoldBright, CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.EmojiEvents,
                contentDescription = null,
                tint = GoldBright,
                modifier = Modifier.size(60.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "تهانينا على إتمام الاختبار!",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "نتيجتك: $score من $total (نسبة $percentage%)",
            color = GoldBright,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Motivational Feedback Card
        Card(
            colors = CardDefaults.cardColors(containerColor = CardBackground),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "رسالة الأستاذة عبيد زهرة لك:",
                    color = PinkBright,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = motivationalFeedback,
                    color = Color(0xFFE2E8F0),
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = onRestart,
            colors = ButtonDefaults.buttonColors(
                containerColor = GoldBright,
                contentColor = RoyalBlueDeep
            ),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .testTag("quiz_restart_button")
        ) {
            Icon(Icons.Default.Refresh, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "إعادة الاختبار من جديد 🔄", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onBackToHome,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1A365D),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .testTag("quiz_home_button")
        ) {
            Text(text = "العودة للرئيسية 🏠", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
    }
}

fun getMotivationalMessage(score: Int, total: Int): String {
    val pct = (score * 100) / total
    return when {
        pct >= 85 -> "🌟 أداء ممتاز ومبهر! لديك إلمام قوي جدًا ومتقن بمفاهيم التاريخ والجغرافيا. استمر بهذه العزيمة وسيكون الامتياز حليفك في شهادة البكالوريا بإذن الله تعالى!"
        pct >= 70 -> "👏 أداء جيد جدًا! إجاباتك دقيقة وتدل على فهم واعي للمنهاج. راجع بعض التواريخ والمصطلحات الدقيقة لتصل إلى العلامة الكاملة 20/20!"
        pct >= 50 -> "👍 مستوى حسن وبداية مشجعة! تمكنت من تجاوز نصف الأسئلة بنجاح. أعد قراءة الملخصات واستخدم قاموس المصطلحات لترفع معدلك بثقة."
        else -> "💪 لا تقلق، البكالوريا رحلة تدريب وتكرار! استفد من أخطائك اليوم، واطلع على قسم الدروس والشخصيات، وكرر الاختبار حتى تحقق النتيجة التي تطمح إليها!"
    }
}
