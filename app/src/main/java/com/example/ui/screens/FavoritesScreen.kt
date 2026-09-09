package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.BacRepository
import com.example.ui.theme.CardBackground
import com.example.ui.theme.CardBorder
import com.example.ui.theme.GoldAccent
import com.example.ui.theme.GoldBright
import com.example.ui.theme.PinkBright
import com.example.ui.theme.PinkSoft
import com.example.ui.theme.RoyalBlueDeep

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    favoriteLessonIds: Set<String>,
    favoriteTermIds: Set<String>,
    favoriteFigureIds: Set<String>,
    onToggleLessonFav: (String) -> Unit,
    onToggleTermFav: (String) -> Unit,
    onToggleFigureFav: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val favLessons = BacRepository.lessonsList.filter { favoriteLessonIds.contains(it.id) }
    val favTerms = BacRepository.termsList.filter { favoriteTermIds.contains(it.id) }
    val favFigures = BacRepository.figuresList.filter { favoriteFigureIds.contains(it.id) }

    val isEmpty = favLessons.isEmpty() && favTerms.isEmpty() && favFigures.isEmpty()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "⭐ العناصر المفضلة",
                        color = GoldBright,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("favorites_back_button")) {
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
        if (isEmpty) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(80.dp)
                        .background(Color(0x33FFC72C), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = GoldBright,
                        modifier = Modifier.size(44.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "قائمة المفضلة فارغة حالياً",
                    color = Color.White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "اضغط على أيقونة الإشارة المرجعية 🔖 بجانب أي درس أو مصطلح أو شخصية لحفظها هنا والرجوع إليها بسرعة قبل البكالوريا.",
                    color = Color(0xFF94A3B8),
                    fontSize = 13.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    lineHeight = 18.sp
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (favTerms.isNotEmpty()) {
                    item {
                        Text(
                            text = "📚 المصطلحات المحفوظة (${favTerms.size}):",
                            color = GoldBright,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    items(favTerms) { term ->
                        TermCardItem(
                            term = term,
                            isFavorite = true,
                            onToggleFavorite = { onToggleTermFav(term.id) }
                        )
                    }
                }

                if (favFigures.isNotEmpty()) {
                    item {
                        Text(
                            text = "👤 الشخصيات المحفوظة (${favFigures.size}):",
                            color = PinkBright,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                    }
                    items(favFigures) { figure ->
                        FigureCardItem(
                            figure = figure,
                            isFavorite = true,
                            onToggleFavorite = { onToggleFigureFav(figure.id) }
                        )
                    }
                }

                if (favLessons.isNotEmpty()) {
                    item {
                        Text(
                            text = "📖 الدروس المحفوظة (${favLessons.size}):",
                            color = GoldAccent,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 12.dp)
                        )
                    }
                    items(favLessons) { lesson ->
                        LessonCardItem(
                            lesson = lesson,
                            isFavorite = true,
                            onToggleFavorite = { onToggleLessonFav(lesson.id) }
                        )
                    }
                }
            }
        }
    }
}
