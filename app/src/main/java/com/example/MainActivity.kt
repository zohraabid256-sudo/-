package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.example.data.QuizResultRecord
import com.example.ui.screens.AppDestination
import com.example.ui.screens.ControlPanelScreen
import com.example.ui.screens.FavoritesScreen
import com.example.ui.screens.FiguresScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LessonsScreen
import com.example.ui.screens.PhonePreviewScreen
import com.example.ui.screens.ProgressScreen
import com.example.ui.screens.QuizScreen
import com.example.ui.screens.SmartBotScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.screens.SummariesScreen
import com.example.ui.screens.TermsScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.RoyalBlueDeep

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme(darkTheme = true) {
                // Enforce right-to-left layout for authentic Arabic experience
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = RoyalBlueDeep
                    ) {
                        SanadFinalApp()
                    }
                }
            }
        }
    }
}

@Composable
fun SanadFinalApp() {
    var currentScreen by remember { mutableStateOf(AppDestination.SPLASH) }

    // Persistent in-memory state for favorites and quiz achievements
    var favoriteLessonIds by remember { mutableStateOf(setOf("his_u1_l1", "his_u2_l1")) }
    var favoriteTermIds by remember { mutableStateOf(setOf("t1", "t2", "t5", "t11")) }
    var favoriteFigureIds by remember { mutableStateOf(setOf("f1", "f5")) }
    val quizResults = remember {
        mutableStateListOf(
            QuizResultRecord(
                score = 18,
                total = 20,
                percentage = 90,
                dateStr = "تجربة سابقة",
                motivationalFeedback = "🌟 أداء ممتاز ومبهر! لديك إلمام قوي جدًا ومتقن بمفاهيم التاريخ والجغرافيا."
            )
        )
    }

    // Handle system back navigation
    BackHandler(enabled = currentScreen != AppDestination.HOME && currentScreen != AppDestination.SPLASH) {
        currentScreen = AppDestination.HOME
    }

    AnimatedContent(
        targetState = currentScreen,
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        label = "screen_transition"
    ) { screen ->
        when (screen) {
            AppDestination.SPLASH -> {
                SplashScreen(
                    onEnterApp = { currentScreen = AppDestination.HOME }
                )
            }

            AppDestination.HOME -> {
                HomeScreen(
                    onNavigate = { destination -> currentScreen = destination }
                )
            }

            AppDestination.LESSONS -> {
                LessonsScreen(
                    favoriteLessonIds = favoriteLessonIds,
                    onToggleFavorite = { id ->
                        favoriteLessonIds = if (favoriteLessonIds.contains(id)) {
                            favoriteLessonIds - id
                        } else {
                            favoriteLessonIds + id
                        }
                    },
                    onBack = { currentScreen = AppDestination.HOME }
                )
            }

            AppDestination.SUMMARIES -> {
                SummariesScreen(
                    onBack = { currentScreen = AppDestination.HOME }
                )
            }

            AppDestination.TERMS -> {
                TermsScreen(
                    favoriteTermIds = favoriteTermIds,
                    onToggleFavorite = { id ->
                        favoriteTermIds = if (favoriteTermIds.contains(id)) {
                            favoriteTermIds - id
                        } else {
                            favoriteTermIds + id
                        }
                    },
                    onBack = { currentScreen = AppDestination.HOME }
                )
            }

            AppDestination.FIGURES -> {
                FiguresScreen(
                    favoriteFigureIds = favoriteFigureIds,
                    onToggleFavorite = { id ->
                        favoriteFigureIds = if (favoriteFigureIds.contains(id)) {
                            favoriteFigureIds - id
                        } else {
                            favoriteFigureIds + id
                        }
                    },
                    onBack = { currentScreen = AppDestination.HOME }
                )
            }

            AppDestination.QUIZ -> {
                QuizScreen(
                    onSaveResult = { result ->
                        quizResults.add(0, result)
                    },
                    onBack = { currentScreen = AppDestination.HOME }
                )
            }

            AppDestination.SMART_BOT -> {
                SmartBotScreen(
                    onBack = { currentScreen = AppDestination.HOME }
                )
            }

            AppDestination.FAVORITES -> {
                FavoritesScreen(
                    favoriteLessonIds = favoriteLessonIds,
                    favoriteTermIds = favoriteTermIds,
                    favoriteFigureIds = favoriteFigureIds,
                    onToggleLessonFav = { id ->
                        favoriteLessonIds = if (favoriteLessonIds.contains(id)) favoriteLessonIds - id else favoriteLessonIds + id
                    },
                    onToggleTermFav = { id ->
                        favoriteTermIds = if (favoriteTermIds.contains(id)) favoriteTermIds - id else favoriteTermIds + id
                    },
                    onToggleFigureFav = { id ->
                        favoriteFigureIds = if (favoriteFigureIds.contains(id)) favoriteFigureIds - id else favoriteFigureIds + id
                    },
                    onBack = { currentScreen = AppDestination.HOME }
                )
            }

            AppDestination.PROGRESS -> {
                ProgressScreen(
                    quizResults = quizResults,
                    favoriteCount = favoriteLessonIds.size + favoriteTermIds.size + favoriteFigureIds.size,
                    onBack = { currentScreen = AppDestination.HOME }
                )
            }

            AppDestination.CONTROL_PANEL -> {
                ControlPanelScreen(
                    onBack = { currentScreen = AppDestination.HOME }
                )
            }

            AppDestination.PHONE_PREVIEW -> {
                PhonePreviewScreen(
                    onBack = { currentScreen = AppDestination.HOME }
                )
            }
        }
    }
}
