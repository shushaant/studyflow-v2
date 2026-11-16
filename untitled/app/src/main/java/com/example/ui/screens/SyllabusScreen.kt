package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ChapterEntity
import com.example.data.model.InfinityStoneType
import com.example.data.model.SubjectWithStats
import com.example.ui.components.AddCustomChapterDialog
import com.example.ui.components.CurriculumProgressBanner
import com.example.ui.components.HudCornerBox
import com.example.ui.components.ScreenTab
import com.example.ui.theme.ArcReactorCyan
import com.example.ui.theme.BerserkerCrimson
import com.example.ui.theme.CapCobalt
import com.example.ui.theme.CrimsonArc
import com.example.ui.theme.DoomEmerald
import com.example.ui.theme.ElectricCyanPrimary
import com.example.ui.theme.EmeraldTertiary
import com.example.ui.theme.GammaGreen
import com.example.ui.theme.GunmetalBorder
import com.example.ui.theme.GunmetalCard
import com.example.ui.theme.GunmetalCardHigh
import com.example.ui.theme.GunmetalGrey
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.SentrySun
import com.example.ui.theme.SoftBlueSecondary
import com.example.ui.theme.StarkGold
import com.example.ui.theme.StreakFlameOrange
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerHighest
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.TextHighContrastWhite
import com.example.ui.theme.TextMutedGrey
import com.example.ui.theme.TextOutline
import com.example.ui.theme.VibraniumPurple
import com.example.ui.viewmodel.StudyFlowViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SyllabusScreen(
    viewModel: StudyFlowViewModel,
    onNavigateToTab: (ScreenTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val subjectsWithStats by viewModel.subjectsWithStats.collectAsState()
    val selectedFilter by viewModel.selectedFilter.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedChapterForSheet by viewModel.selectedChapterForSheet.collectAsState()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    var showAddChapterDialog by remember { mutableStateOf(false) }
    var showWeightageDialog by remember { mutableStateOf(false) }

    val totalChapters = subjectsWithStats.sumOf { it.totalCount }
    val completedChapters = subjectsWithStats.sumOf { it.completedCount }
    val overallPercentage = if (totalChapters > 0) (completedChapters * 100) / totalChapters else 0

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(2.dp))

                // Doctor Doom's Latverian Master Control Room Header
                HudCornerBox(
                    cornerColor = DoomEmerald,
                    fillColor = GunmetalCard,
                    borderColor = DoomEmerald.copy(alpha = 0.4f)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(DoomEmerald.copy(alpha = 0.25f))
                                        .border(1.dp, DoomEmerald, RoundedCornerShape(8.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "VD",
                                        style = MaterialTheme.typography.labelMedium,
                                        color = DoomEmerald,
                                        fontWeight = FontWeight.Black
                                    )
                                }
                                Column {
                                    Text(
                                        text = "LATVERIAN MASTER SYLLABUS CONTROL",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = TextHighContrastWhite,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 0.5.sp
                                    )
                                    Text(
                                        text = "\"None shall surpass Doom in CBSE examinations!\"",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = StarkGold
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(GunmetalCardHigh)
                                    .border(1.dp, DoomEmerald.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "470 MARKS",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = DoomEmerald,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 9.sp
                                )
                            }
                        }
                    }
                }
            }

            // Search Bar & Filter Controls
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.setSearchQuery(it) },
                    placeholder = {
                        Text(
                            text = "Search chapters, subtopics, or formulas...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextMutedGrey
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search",
                            tint = ArcReactorCyan,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    trailingIcon = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier.padding(end = 6.dp)
                        ) {
                            IconButton(onClick = { showWeightageDialog = true }) {
                                Icon(
                                    imageVector = Icons.Filled.Tune,
                                    contentDescription = "Filter",
                                    tint = TextMutedGrey,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextHighContrastWhite,
                        unfocusedTextColor = TextHighContrastWhite,
                        focusedContainerColor = GunmetalCard,
                        unfocusedContainerColor = GunmetalCard,
                        focusedBorderColor = ArcReactorCyan,
                        unfocusedBorderColor = GunmetalBorder
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Quick Scrollable Subject Filter Chips
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val filterChips = listOf(
                        "all" to "All 7 Subjects",
                        "math" to "Mathematics",
                        "science" to "Science",
                        "sst" to "Social Science",
                        "english" to "English",
                        "hindi" to "Hindi",
                        "sanskrit" to "Sanskrit",
                        "it" to "IT"
                    )
                    filterChips.forEach { (key, label) ->
                        val isSelected = selectedFilter == key
                        val chipColor = when (key) {
                            "math" -> ArcReactorCyan
                            "science" -> GammaGreen
                            "sst" -> SoftBlueSecondary
                            "english" -> StarkGold
                            "hindi" -> StreakFlameOrange
                            "sanskrit" -> VibraniumPurple
                            "it" -> Color(0xFF38BDF8)
                            else -> ArcReactorCyan
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) chipColor.copy(alpha = 0.2f) else GunmetalCard)
                                .border(
                                    1.dp,
                                    if (isSelected) chipColor else GunmetalBorder,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { viewModel.setFilter(key) }
                                .padding(horizontal = 14.dp, vertical = 7.dp)
                        ) {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelMedium,
                                color = if (isSelected) chipColor else TextMutedGrey,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }
            }

            // Overall Syllabus Progress Banner
            item {
                CurriculumProgressBanner(
                    completedChapters = completedChapters,
                    totalChapters = totalChapters,
                    percentage = overallPercentage
                )
            }

            // Six Infinity Stones Cosmic Syllabus Matrix
            item {
                InfinityGauntletMatrixCard(
                    subjectsWithStats = subjectsWithStats,
                    selectedFilter = selectedFilter,
                    onSelectFilter = { viewModel.setFilter(it) }
                )
            }

            // Subjects list with detailed chapter rows
            val displayedSubjects = subjectsWithStats.filter { stats ->
                val matchesFilter = selectedFilter == "all" || stats.subject.id == selectedFilter
                val matchesSearch = searchQuery.isBlank() ||
                        stats.subject.name.contains(searchQuery, ignoreCase = true) ||
                        stats.chapters.any { it.title.contains(searchQuery, ignoreCase = true) }
                matchesFilter && matchesSearch
            }

            if (displayedSubjects.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(GunmetalCard)
                            .border(1.dp, GunmetalBorder, RoundedCornerShape(12.dp))
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.MenuBook,
                                contentDescription = null,
                                tint = ArcReactorCyan,
                                modifier = Modifier.size(36.dp)
                            )
                            Text(
                                text = if (searchQuery.isNotBlank()) "No chapters found for \"$searchQuery\"" else "Loading CBSE Curriculum...",
                                style = MaterialTheme.typography.titleMedium,
                                color = TextHighContrastWhite,
                                fontWeight = FontWeight.SemiBold
                            )
                            if (searchQuery.isBlank()) {
                                Button(
                                    onClick = { viewModel.ensureDataPopulated() },
                                    colors = ButtonDefaults.buttonColors(containerColor = ArcReactorCyan),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text("Load Curriculum", color = ObsidianBlack, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }

            items(displayedSubjects) { subjectStats ->
                SubjectAccordionCard(
                    subjectStats = subjectStats,
                    searchQuery = searchQuery,
                    viewModel = viewModel,
                    onToggleChapter = { chapter ->
                        viewModel.toggleChapterCompletion(chapter)
                    },
                    onChapterClick = { chapter ->
                        viewModel.openChapterSheet(chapter)
                    },
                    onResumeChapter = {
                        viewModel.startTimer()
                        onNavigateToTab(ScreenTab.FOCUS)
                    },
                    onTestChapter = {
                        onNavigateToTab(ScreenTab.AI_TUTOR)
                    }
                )
            }

            // Bottom Actions Bar
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { showAddChapterDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = GunmetalCard),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .border(1.dp, GunmetalBorder, RoundedCornerShape(10.dp))
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = null,
                            tint = ArcReactorCyan,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Add Custom Chapter",
                            style = MaterialTheme.typography.labelLarge,
                            color = TextHighContrastWhite,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Button(
                        onClick = { showWeightageDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = GunmetalCard),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .height(48.dp)
                            .border(1.dp, GunmetalBorder, RoundedCornerShape(10.dp))
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Tune,
                            contentDescription = null,
                            tint = StarkGold,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Weightage",
                            style = MaterialTheme.typography.labelMedium,
                            color = StarkGold,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(28.dp))
            }
        }

        // Chapter Detail Modal Sheet
        selectedChapterForSheet?.let { chapter ->
            ModalBottomSheet(
                onDismissRequest = { viewModel.closeChapterSheet() },
                sheetState = sheetState,
                containerColor = GunmetalCard,
                scrimColor = Color.Black.copy(alpha = 0.75f),
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            ) {
                ChapterDetailSheetContent(
                    chapter = chapter,
                    viewModel = viewModel,
                    onClose = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            viewModel.closeChapterSheet()
                        }
                    },
                    onResumeStudy = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            viewModel.closeChapterSheet()
                            viewModel.startTimer()
                            onNavigateToTab(ScreenTab.FOCUS)
                        }
                    },
                    onTestKnowledge = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            viewModel.closeChapterSheet()
                            onNavigateToTab(ScreenTab.AI_TUTOR)
                        }
                    }
                )
            }
        }

        if (showAddChapterDialog) {
            AddCustomChapterDialog(
                onDismiss = { showAddChapterDialog = false },
                onAdd = { subjectId, title, marks ->
                    viewModel.addCustomChapter(subjectId, title, marks)
                }
            )
        }

        if (showWeightageDialog) {
            WeightageOverviewDialog(onDismiss = { showWeightageDialog = false })
        }
    }
}

@Composable
private fun SubjectAccordionCard(
    subjectStats: SubjectWithStats,
    searchQuery: String,
    viewModel: StudyFlowViewModel,
    onToggleChapter: (ChapterEntity) -> Unit,
    onChapterClick: (ChapterEntity) -> Unit,
    onResumeChapter: () -> Unit,
    onTestChapter: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(true) }

    val icon: ImageVector = when (subjectStats.subject.id) {
        "math" -> Icons.Filled.Calculate
        "science" -> Icons.Filled.Science
        "sst" -> Icons.Filled.Public
        "english" -> Icons.Filled.MenuBook
        "hindi" -> Icons.Filled.Translate
        "sanskrit" -> Icons.Filled.AutoStories
        "it" -> Icons.Filled.Computer
        else -> Icons.Filled.Calculate
    }

    val stone = InfinityStoneType.fromSubjectId(subjectStats.subject.id)
    val subjectColor: Color = stone.color

    HudCornerBox(
        cornerColor = subjectColor,
        fillColor = GunmetalCard,
        borderColor = if (subjectStats.completionPercentage == 100) EmeraldTertiary.copy(alpha = 0.5f) else GunmetalBorder,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            // Header Row (Clickable Accordion)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded }
                    .padding(14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(subjectColor.copy(alpha = 0.2f))
                            .border(1.dp, subjectColor, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = subjectColor,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = subjectStats.subject.name,
                                style = MaterialTheme.typography.titleMedium,
                                color = TextHighContrastWhite,
                                fontWeight = FontWeight.Bold
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(stone.color.copy(alpha = 0.2f))
                                    .border(0.5.dp, stone.color.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    if (stone.drawableResId != null) {
                                        Image(
                                            painter = painterResource(id = stone.drawableResId),
                                            contentDescription = stone.stoneName,
                                            modifier = Modifier
                                                .size(14.dp)
                                                .clip(RoundedCornerShape(2.dp))
                                        )
                                    } else {
                                        Text(text = stone.emblem, fontSize = 9.sp)
                                    }
                                    Text(
                                        text = stone.stoneName.uppercase(),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = stone.color,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 9.sp
                                    )
                                }
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "${subjectStats.completedCount} of ${subjectStats.totalCount} Conquered",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextMutedGrey,
                                fontSize = 11.sp
                            )
                            Text(text = "•", color = TextMutedGrey.copy(alpha = 0.4f), fontSize = 10.sp)
                            Text(
                                text = "HERO: ${stone.guardianHero.uppercase()}",
                                style = MaterialTheme.typography.labelSmall,
                                color = stone.color.copy(alpha = 0.9f),
                                fontSize = 9.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(GunmetalCardHigh)
                            .border(1.dp, subjectColor.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "${subjectStats.completionPercentage}%",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (subjectStats.completionPercentage == 100) EmeraldTertiary else subjectColor,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Icon(
                        imageVector = if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        contentDescription = if (isExpanded) "Collapse" else "Expand",
                        tint = TextMutedGrey,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Subject Progress Indicator
            LinearProgressIndicator(
                progress = { (subjectStats.completionPercentage / 100f).coerceIn(0f, 1f) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp),
                color = if (subjectStats.completionPercentage == 100) EmeraldTertiary else subjectColor,
                trackColor = GunmetalGrey,
                strokeCap = StrokeCap.Square
            )

            // Infinity Stone Cosmic Lore Bar
            if (isExpanded) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(stone.color.copy(alpha = 0.08f))
                        .border(0.5.dp, stone.color.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(text = stone.emblem, fontSize = 12.sp)
                        Text(
                            text = "${stone.cosmicTitle}: ${stone.cosmicPower}",
                            style = MaterialTheme.typography.labelSmall,
                            color = stone.color,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Chapters List (Accordion body)
            var expandAllChapters by remember { mutableStateOf<Boolean?>(null) }

            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val filteredChapters = if (searchQuery.isBlank()) {
                        subjectStats.chapters
                    } else {
                        subjectStats.chapters.filter { it.title.contains(searchQuery, ignoreCase = true) }
                    }

                    // Quick Toggle for all chapter checklists in this subject
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp, vertical = 2.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${filteredChapters.size} Chapters • Marvel HUD Indexed",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextMutedGrey
                        )
                        Text(
                            text = if (expandAllChapters == true) "Collapse All Topics" else "Expand All Topics",
                            style = MaterialTheme.typography.labelSmall,
                            color = ArcReactorCyan,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .clickable {
                                    expandAllChapters = !(expandAllChapters ?: false)
                                }
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }

                    filteredChapters.forEach { chapter ->
                        ChapterItemRow(
                            chapter = chapter,
                            isScience = subjectStats.subject.id == "science",
                            viewModel = viewModel,
                            expandAllOverride = expandAllChapters,
                            onToggle = { onToggleChapter(chapter) },
                            onClick = { onChapterClick(chapter) },
                            onResume = onResumeChapter,
                            onTest = onTestChapter
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ChapterItemRow(
    chapter: ChapterEntity,
    isScience: Boolean,
    viewModel: StudyFlowViewModel,
    expandAllOverride: Boolean? = null,
    onToggle: () -> Unit,
    onClick: () -> Unit,
    onResume: () -> Unit,
    onTest: () -> Unit
) {
    var isSubtopicsExpanded by remember { mutableStateOf(false) }
    val effectiveExpanded = expandAllOverride ?: isSubtopicsExpanded
    val isMastered = chapter.status == "MASTERED"
    val isInReview = chapter.status == "IN_REVIEW"
    val subtopics = viewModel.getSubtopicsForChapter(chapter)
    val completedSubtopicsCount = subtopics.count { it.isCompleted }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(if (isMastered) GunmetalCard else GunmetalCardHigh)
            .border(
                1.dp,
                if (isMastered) EmeraldTertiary.copy(alpha = 0.35f) else GunmetalBorder,
                RoundedCornerShape(10.dp)
            )
    ) {
        // Main Chapter Card Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 48dp Dedicated Chapter Checkbox
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clickable(role = Role.Checkbox, onClick = onToggle)
                    .testTag("toggle_chapter_${chapter.id}"),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isMastered) EmeraldTertiary else if (isInReview) ArcReactorCyan else GunmetalGrey)
                        .border(
                            width = 1.dp,
                            color = if (isMastered) EmeraldTertiary else if (isInReview) ArcReactorCyan else TextMutedGrey.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(6.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (isMastered) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Completed",
                            tint = ObsidianBlack,
                            modifier = Modifier.size(16.dp)
                        )
                    } else if (isInReview) {
                        Icon(
                            imageVector = Icons.Filled.Timelapse,
                            contentDescription = "In Review",
                            tint = ObsidianBlack,
                            modifier = Modifier.size(14.dp)
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.RadioButtonUnchecked,
                            contentDescription = "Not started",
                            tint = TextMutedGrey,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }

            // Clickable details text area (toggles subtopics or opens sheet)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable(onClick = { isSubtopicsExpanded = !effectiveExpanded })
                    .padding(vertical = 10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = chapter.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = if (isMastered) TextHighContrastWhite.copy(alpha = 0.85f) else TextHighContrastWhite,
                        textDecoration = if (isMastered) TextDecoration.LineThrough else TextDecoration.None,
                        fontWeight = if (isMastered) FontWeight.Normal else FontWeight.SemiBold,
                        modifier = Modifier.weight(1f, fill = false)
                    )

                    // Spider-Man Peter Parker web formula sense indicator for high-weightage chapters
                    if (chapter.weightageMarks >= 6) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(BerserkerCrimson.copy(alpha = 0.2f))
                                .border(1.dp, BerserkerCrimson.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
                                .padding(horizontal = 5.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "🕷️ Spider-Sense",
                                style = MaterialTheme.typography.labelSmall,
                                color = BerserkerCrimson,
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.padding(top = 2.dp)
                ) {
                    if (isMastered) {
                        Text(
                            text = "Conquered ${chapter.completedDate ?: "recently"}",
                            style = MaterialTheme.typography.labelSmall,
                            color = EmeraldTertiary
                        )
                        Text(text = "•", style = MaterialTheme.typography.labelSmall, color = TextMutedGrey)
                        Icon(
                            imageVector = Icons.Filled.CloudDone,
                            contentDescription = null,
                            tint = TextMutedGrey,
                            modifier = Modifier.size(12.dp)
                        )
                    } else {
                        Text(
                            text = "$completedSubtopicsCount of ${subtopics.size} Topics Done • ${chapter.weightageMarks}M Weightage",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (completedSubtopicsCount > 0) ArcReactorCyan else TextMutedGrey
                        )
                    }
                }

                // Dedicated Quick-Tap Topic Checklist Pill
                Row(
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (effectiveExpanded) ArcReactorCyan.copy(alpha = 0.2f) else GunmetalGrey)
                        .clickable { isSubtopicsExpanded = !effectiveExpanded }
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Icon(
                        imageVector = if (effectiveExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        contentDescription = null,
                        tint = ArcReactorCyan,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = if (effectiveExpanded) "Hide Topics Checklist" else "Checklist (${completedSubtopicsCount}/${subtopics.size} Topics)",
                        style = MaterialTheme.typography.labelSmall,
                        color = ArcReactorCyan,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // Action: The Hulk (Bruce Banner) Smash-Through Chapter Checklist Button
            if (isScience || chapter.weightageMarks >= 5) {
                Box(
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(GammaGreen.copy(alpha = 0.2f))
                        .border(1.dp, GammaGreen, RoundedCornerShape(6.dp))
                        .clickable { viewModel.hulkSmashChapter(chapter) }
                        .padding(horizontal = 6.dp, vertical = 4.dp)
                        .testTag("hulk_smash_${chapter.id}")
                ) {
                    Text(
                        text = "💥 SMASH",
                        style = MaterialTheme.typography.labelSmall,
                        color = GammaGreen,
                        fontWeight = FontWeight.Black,
                        fontSize = 9.sp
                    )
                }
            }

            // Expand Arrow Button
            IconButton(
                onClick = { isSubtopicsExpanded = !effectiveExpanded },
                modifier = Modifier.size(44.dp)
            ) {
                Icon(
                    imageVector = if (effectiveExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if (effectiveExpanded) "Collapse Topics" else "Expand Topics",
                    tint = TextMutedGrey,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // Expandable Nested Main Topics List
        AnimatedVisibility(
            visible = effectiveExpanded,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(GunmetalCard.copy(alpha = 0.7f))
                    .padding(start = 14.dp, end = 12.dp, top = 8.dp, bottom = 12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Mission Arsenal 4-Core Checkboxes (Permanent Room DB Storage)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(GunmetalCardHigh)
                        .border(1.dp, StarkGold.copy(alpha = 0.35f), RoundedCornerShape(8.dp))
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "🎯 CHAPTER ARSENAL CHECKLIST (ROOM DB)",
                        style = MaterialTheme.typography.labelSmall,
                        color = StarkGold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 9.sp,
                        letterSpacing = 0.5.sp
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        ArsenalCheckChip(
                            label = "📖 NCERT",
                            isDone = chapter.isNcertDone,
                            onToggle = { viewModel.toggleChapterNcert(chapter) },
                            testTag = "check_ncert_${chapter.id}",
                            modifier = Modifier.weight(1f)
                        )
                        ArsenalCheckChip(
                            label = "📚 Q-Bank",
                            isDone = chapter.isQuestionBankDone,
                            onToggle = { viewModel.toggleChapterQuestionBank(chapter) },
                            testTag = "check_qb_${chapter.id}",
                            modifier = Modifier.weight(1f)
                        )
                        ArsenalCheckChip(
                            label = "📝 Sample",
                            isDone = chapter.isSamplePaperDone,
                            onToggle = { viewModel.toggleChapterSamplePaper(chapter) },
                            testTag = "check_sp_${chapter.id}",
                            modifier = Modifier.weight(1f)
                        )
                        ArsenalCheckChip(
                            label = "🎯 10Y PYQ",
                            isDone = chapter.isPyqDone,
                            onToggle = { viewModel.toggleChapterPyq(chapter) },
                            testTag = "check_pyq_${chapter.id}",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "CBSE MAIN TOPICS ($completedSubtopicsCount/${subtopics.size})",
                        style = MaterialTheme.typography.labelSmall,
                        color = ArcReactorCyan,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Tap box to toggle",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMutedGrey
                    )
                }

                subtopics.forEach { subtopic ->
                    val isChecked = subtopic.isCompleted
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (isChecked) GunmetalCardHigh else GunmetalCard)
                            .border(
                                1.dp,
                                if (isChecked) EmeraldTertiary.copy(alpha = 0.3f) else GunmetalBorder,
                                RoundedCornerShape(6.dp)
                            )
                            .clickable { viewModel.toggleSubtopic(subtopic, chapter) }
                            .padding(end = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // 44dp Interactive Subtopic Checkbox
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clickable(
                                    role = Role.Checkbox,
                                    onClick = { viewModel.toggleSubtopic(subtopic, chapter) }
                                )
                                .testTag("subtopic_check_${subtopic.id}"),
                            contentAlignment = Alignment.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(RoundedCornerShape(5.dp))
                                    .background(if (isChecked) EmeraldTertiary else GunmetalGrey)
                                    .border(
                                        1.dp,
                                        if (isChecked) EmeraldTertiary else TextMutedGrey.copy(alpha = 0.5f),
                                        RoundedCornerShape(5.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isChecked) {
                                    Icon(
                                        imageVector = Icons.Filled.Check,
                                        contentDescription = "Done",
                                        tint = ObsidianBlack,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        }

                        Text(
                            text = subtopic.title,
                            style = MaterialTheme.typography.bodySmall,
                            color = if (isChecked) TextHighContrastWhite.copy(alpha = 0.6f) else TextHighContrastWhite,
                            textDecoration = if (isChecked) TextDecoration.LineThrough else TextDecoration.None,
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 8.dp)
                        )
                    }
                }

                // Subtopic action bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = onClick,
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.EditNote,
                            contentDescription = null,
                            tint = StarkGold,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Flashcards & Notes",
                            style = MaterialTheme.typography.labelSmall,
                            color = StarkGold
                        )
                    }

                    Button(
                        onClick = onResume,
                        colors = ButtonDefaults.buttonColors(containerColor = ArcReactorCyan),
                        shape = RoundedCornerShape(6.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                        modifier = Modifier.height(28.dp)
                    ) {
                        Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = null, tint = ObsidianBlack, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Sentry Focus", style = MaterialTheme.typography.labelSmall, color = ObsidianBlack, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun ChapterDetailSheetContent(
    chapter: ChapterEntity,
    viewModel: StudyFlowViewModel,
    onClose: () -> Unit,
    onResumeStudy: () -> Unit,
    onTestKnowledge: () -> Unit
) {
    val flashcards by viewModel.flashcards.collectAsState()
    val currentCardIndex by viewModel.currentFlashcardIndex.collectAsState()
    val isFlipped by viewModel.isFlashcardFlipped.collectAsState()

    var activeTab by remember { mutableStateOf("checklist") } // "checklist", "flashcards", "notes"

    val subtopics = viewModel.getSubtopicsForChapter(chapter)
    val isMastered = chapter.status == "MASTERED"
    val checkedCount = subtopics.count { it.isCompleted }
    val calculatedPercent = if (isMastered) 100 else if (subtopics.isNotEmpty()) (checkedCount * 100) / subtopics.size else 0

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Grab Bar
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(42.dp)
                .height(4.dp)
                .clip(CircleShape)
                .background(GunmetalBorder)
        )

        // Chapter Header & Weightage
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Class 10 CBSE • ${chapter.subjectId.uppercase()} • MARVEL TACTICAL HUD",
                    style = MaterialTheme.typography.labelSmall,
                    color = ArcReactorCyan
                )
                Text(
                    text = chapter.title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = TextHighContrastWhite,
                    fontWeight = FontWeight.Bold
                )
            }
            IconButton(onClick = onClose) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Close",
                    tint = TextMutedGrey
                )
            }
        }

        // Badges Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(GunmetalCardHigh)
                    .border(1.dp, StarkGold.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(
                    text = "★ ${chapter.weightageMarks} Marks Weightage",
                    style = MaterialTheme.typography.labelSmall,
                    color = StarkGold,
                    fontWeight = FontWeight.Bold
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(GunmetalCardHigh)
                    .border(1.dp, EmeraldTertiary.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(
                    text = "$calculatedPercent% Mastered",
                    style = MaterialTheme.typography.labelSmall,
                    color = EmeraldTertiary,
                    fontWeight = FontWeight.Bold
                )
            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(GunmetalCardHigh)
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(
                    text = "NCERT Focus",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMutedGrey
                )
            }
        }

        // Chapter Arsenal Checklist in Bottom Sheet (Room DB Persistence)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(GunmetalCardHigh)
                .border(1.dp, StarkGold.copy(alpha = 0.35f), RoundedCornerShape(8.dp))
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "🎯 CHAPTER ARSENAL CHECKLIST (PERMANENT ROOM DB)",
                style = MaterialTheme.typography.labelSmall,
                color = StarkGold,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                letterSpacing = 0.5.sp
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                ArsenalCheckChip(
                    label = "📖 NCERT",
                    isDone = chapter.isNcertDone,
                    onToggle = { viewModel.toggleChapterNcert(chapter) },
                    testTag = "sheet_check_ncert_${chapter.id}",
                    modifier = Modifier.weight(1f)
                )
                ArsenalCheckChip(
                    label = "📚 Q-Bank",
                    isDone = chapter.isQuestionBankDone,
                    onToggle = { viewModel.toggleChapterQuestionBank(chapter) },
                    testTag = "sheet_check_qb_${chapter.id}",
                    modifier = Modifier.weight(1f)
                )
                ArsenalCheckChip(
                    label = "📝 Sample",
                    isDone = chapter.isSamplePaperDone,
                    onToggle = { viewModel.toggleChapterSamplePaper(chapter) },
                    testTag = "sheet_check_sp_${chapter.id}",
                    modifier = Modifier.weight(1f)
                )
                ArsenalCheckChip(
                    label = "🎯 PYQ 10Y",
                    isDone = chapter.isPyqDone,
                    onToggle = { viewModel.toggleChapterPyq(chapter) },
                    testTag = "sheet_check_pyq_${chapter.id}",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Action Buttons Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = onResumeStudy,
                colors = ButtonDefaults.buttonColors(containerColor = ArcReactorCyan),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
            ) {
                Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = null, tint = ObsidianBlack)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Sentry Focus Timer", color = ObsidianBlack, fontWeight = FontWeight.Bold)
            }

            Button(
                onClick = onTestKnowledge,
                colors = ButtonDefaults.buttonColors(containerColor = GunmetalCardHigh),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .border(1.dp, GunmetalBorder, RoundedCornerShape(10.dp))
            ) {
                Icon(imageVector = Icons.Filled.Quiz, contentDescription = null, tint = StarkGold)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Test Knowledge", color = TextHighContrastWhite, fontWeight = FontWeight.SemiBold)
            }
        }

        // Tabs: Checklist, Flashcards, Notes
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(
                "checklist" to "Checklist ($checkedCount/${subtopics.size})",
                "flashcards" to "Flashcards (${flashcards.size})",
                "notes" to "Notes & Formulas"
            ).forEach { (tabKey, label) ->
                val isSelected = activeTab == tabKey
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isSelected) ArcReactorCyan.copy(alpha = 0.2f) else GunmetalCardHigh)
                        .border(
                            1.dp,
                            if (isSelected) ArcReactorCyan else GunmetalBorder,
                            RoundedCornerShape(8.dp)
                        )
                        .clickable { activeTab = tabKey }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelMedium,
                        color = if (isSelected) ArcReactorCyan else TextMutedGrey,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        // Tab Content
        when (activeTab) {
            "checklist" -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(subtopics) { subtopic ->
                        val isChecked = subtopic.isCompleted
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isChecked) GunmetalCardHigh else GunmetalCard)
                                .border(
                                    1.dp,
                                    if (isChecked) EmeraldTertiary.copy(alpha = 0.3f) else GunmetalBorder,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { viewModel.toggleSubtopic(subtopic, chapter) }
                                .padding(end = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clickable(role = Role.Checkbox) { viewModel.toggleSubtopic(subtopic, chapter) },
                                contentAlignment = Alignment.Center
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clip(RoundedCornerShape(5.dp))
                                        .background(if (isChecked) EmeraldTertiary else GunmetalGrey)
                                        .border(
                                            1.dp,
                                            if (isChecked) EmeraldTertiary else TextMutedGrey.copy(alpha = 0.5f),
                                            RoundedCornerShape(5.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (isChecked) {
                                        Icon(
                                            imageVector = Icons.Filled.Check,
                                            contentDescription = "Done",
                                            tint = ObsidianBlack,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                }
                            }

                            Text(
                                text = subtopic.title,
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (isChecked) TextHighContrastWhite.copy(alpha = 0.6f) else TextHighContrastWhite,
                                textDecoration = if (isChecked) TextDecoration.LineThrough else TextDecoration.None,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            "flashcards" -> {
                if (flashcards.isNotEmpty()) {
                    val card = flashcards[currentCardIndex % flashcards.size]
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(GunmetalCardHigh)
                            .border(1.dp, GunmetalBorder, RoundedCornerShape(12.dp))
                            .clickable { viewModel.flipFlashcard() }
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Card ${currentCardIndex + 1} of ${flashcards.size}",
                                style = MaterialTheme.typography.labelSmall,
                                color = ArcReactorCyan
                            )
                            Text(
                                text = if (isFlipped) "Tap to see question" else "Tap to flip",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextMutedGrey
                            )
                        }

                        Text(
                            text = if (isFlipped) card.explanationBack else card.formulaFront,
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextHighContrastWhite,
                            fontWeight = FontWeight.SemiBold
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextButton(onClick = { viewModel.prevFlashcard(flashcards.size) }) {
                                Text("← Prev", color = TextMutedGrey)
                            }
                            TextButton(onClick = { viewModel.nextFlashcard(flashcards.size) }) {
                                Text("Next →", color = ArcReactorCyan)
                            }
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No flashcards generated yet for this chapter.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextMutedGrey
                        )
                    }
                }
            }

            "notes" -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(GunmetalCardHigh)
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Key Formulas & Board Mnemonics",
                        style = MaterialTheme.typography.titleSmall,
                        color = ArcReactorCyan,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "• Chapter weightage: ${chapter.weightageMarks} marks in CBSE Board exams.\n• Practice all NCERT in-text examples and previous 5 years' question patterns.\n• Stored in Ultron Room Database Core for offline review.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextHighContrastWhite
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun WeightageOverviewDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = ArcReactorCyan)
            ) {
                Text("Dismiss", color = ObsidianBlack, fontWeight = FontWeight.Bold)
            }
        },
        title = {
            Text(
                text = "CBSE Class 10 Weightage Matrix",
                style = MaterialTheme.typography.titleMedium,
                color = TextHighContrastWhite,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Total Syllabus Weightage: 470 Marks across 7 subjects.",
                    style = MaterialTheme.typography.bodySmall,
                    color = ArcReactorCyan
                )
                Text(
                    text = "• Mathematics: 80 Theory + 20 Internal = 100M\n• Science: 80 Theory + 20 Internal = 100M\n• Social Science: 80 Theory + 20 Internal = 100M\n• English Language & Lit: 80M\n• Hindi / Sanskrit / IT: 50-80M",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextHighContrastWhite
                )
            }
        },
        containerColor = GunmetalCard,
        shape = RoundedCornerShape(14.dp)
    )
}

@Composable
private fun InfinityGauntletMatrixCard(
    subjectsWithStats: List<SubjectWithStats>,
    selectedFilter: String,
    onSelectFilter: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    HudCornerBox(
        cornerColor = StarkGold,
        fillColor = GunmetalCard,
        borderColor = StarkGold.copy(alpha = 0.5f),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(StarkGold.copy(alpha = 0.2f))
                            .border(1.dp, StarkGold.copy(alpha = 0.7f), RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "💎", fontSize = 16.sp)
                    }

                    Column {
                        Text(
                            text = "SIX INFINITY STONES // SYLLABUS MATRIX",
                            style = MaterialTheme.typography.titleSmall,
                            color = TextHighContrastWhite,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            letterSpacing = 0.8.sp
                        )
                        Text(
                            text = "CBSE CLASS 10 SUBJECTS CLASSIFIED AS COSMIC ARTIFACTS",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextMutedGrey,
                            fontSize = 9.sp
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(StarkGold.copy(alpha = 0.15f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "MCU LORE",
                        style = MaterialTheme.typography.labelSmall,
                        color = StarkGold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 9.sp
                    )
                }
            }

            // Scrollable Stones Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val stoneMappings = listOf(
                    "math" to "Mathematics",
                    "science" to "Science",
                    "sst" to "Social Science",
                    "sanskrit" to "Sanskrit",
                    "english" to "English",
                    "hindi" to "Hindi",
                    "it" to "IT"
                )

                stoneMappings.forEach { (subId, defaultName) ->
                    val stone = InfinityStoneType.fromSubjectId(subId)
                    val stats = subjectsWithStats.firstOrNull { it.subject.id == subId }
                    val completed = stats?.completedCount ?: 0
                    val total = stats?.totalCount ?: 0
                    val isSelected = selectedFilter == subId

                    Box(
                        modifier = Modifier
                            .width(138.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) stone.color.copy(alpha = 0.22f) else GunmetalCardHigh)
                            .border(
                                1.dp,
                                if (isSelected) stone.color else stone.color.copy(alpha = 0.35f),
                                RoundedCornerShape(10.dp)
                            )
                            .clickable { onSelectFilter(if (isSelected) "all" else subId) }
                            .padding(10.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (stone.drawableResId != null) {
                                    Image(
                                        painter = painterResource(id = stone.drawableResId),
                                        contentDescription = stone.stoneName,
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clip(RoundedCornerShape(4.dp))
                                    )
                                } else {
                                    Text(text = stone.emblem, fontSize = 16.sp)
                                }
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(stone.color.copy(alpha = 0.2f))
                                        .padding(horizontal = 4.dp, vertical = 1.dp)
                                ) {
                                    Text(
                                        text = "$completed/$total",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = stone.color,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 9.sp
                                    )
                                }
                            }
                            Text(
                                text = stone.stoneName,
                                style = MaterialTheme.typography.titleSmall,
                                color = TextHighContrastWhite,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                            Text(
                                text = defaultName,
                                style = MaterialTheme.typography.labelSmall,
                                color = stone.color,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 10.sp
                            )
                            Text(
                                text = stone.guardianHero,
                                style = MaterialTheme.typography.labelSmall,
                                color = TextMutedGrey,
                                fontSize = 9.sp,
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ArsenalCheckChip(
    label: String,
    isDone: Boolean,
    onToggle: () -> Unit,
    testTag: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(34.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(if (isDone) EmeraldTertiary.copy(alpha = 0.2f) else GunmetalCard)
            .border(
                1.dp,
                if (isDone) EmeraldTertiary else GunmetalBorder,
                RoundedCornerShape(6.dp)
            )
            .clickable(role = Role.Checkbox, onClick = onToggle)
            .testTag(testTag)
            .padding(horizontal = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = if (isDone) Icons.Filled.Check else Icons.Outlined.RadioButtonUnchecked,
                contentDescription = label,
                tint = if (isDone) EmeraldTertiary else TextMutedGrey,
                modifier = Modifier.size(12.dp)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = if (isDone) EmeraldTertiary else TextHighContrastWhite,
                fontWeight = if (isDone) FontWeight.Bold else FontWeight.Medium,
                fontSize = 10.sp,
                maxLines = 1
            )
        }
    }
}

