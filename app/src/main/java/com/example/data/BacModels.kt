package com.example.data

enum class SubjectType(val titleAr: String) {
    HISTORY("التاريخ"),
    GEOGRAPHY("الجغرافيا")
}

data class LessonSection(
    val title: String,
    val items: List<String>
)

data class LessonItem(
    val id: String,
    val unitNumber: Int,
    val unitTitle: String,
    val situationNumber: Int,
    val lessonTitle: String,
    val subject: SubjectType,
    val competency: String = "",
    val introduction: String = "",
    val sections: List<LessonSection> = emptyList(),
    val keyPoints: List<String> = emptyList(),
    val datesOrFacts: List<String> = emptyList(),
    val examQuestion: String = "",
    val examAnswerHints: String = "",
    val examTips: String = ""
)

data class TermItem(
    val id: String,
    val term: String,
    val subject: SubjectType,
    val definition: String,
    val unit: String,
    val isFavorite: Boolean = false
)

data class FigureItem(
    val id: String,
    val name: String,
    val roleTitle: String,
    val nationality: String,
    val period: String,
    val biography: String,
    val keyAchievements: List<String>,
    val isFavorite: Boolean = false
)

data class SummaryItem(
    val id: String,
    val title: String,
    val subject: SubjectType,
    val category: String,
    val summaryText: String,
    val comparisonTable: List<Pair<String, String>> = emptyList()
)

data class QuizQuestion(
    val id: Int,
    val question: String,
    val subject: SubjectType,
    val options: List<String>,
    val correctOptionIndex: Int,
    val explanation: String
)

data class QuizResultRecord(
    val id: Long = System.currentTimeMillis(),
    val score: Int,
    val total: Int,
    val percentage: Int,
    val dateStr: String,
    val motivationalFeedback: String
)

data class ChatMessage(
    val id: String,
    val senderIsUser: Boolean,
    val text: String,
    val timestamp: String
)

data class TeacherAnnouncement(
    val id: String,
    val title: String,
    val content: String,
    val date: String,
    val tag: String
)

data class BacExamPaper(
    val id: String,
    val title: String,
    val unitTitle: String,
    val subject: SubjectType,
    val duration: String,
    val coefficient: String,
    val termsToDefine: List<String>,
    val figuresToDefine: List<String>,
    val datesAndEvents: List<Pair<String, String>>,
    val mapTask: String,
    val essayTopicContext: String,
    val essayQuestions: List<String>,
    val modelAnswerHints: String,
    val scoringGuide: List<Pair<String, String>>
)
