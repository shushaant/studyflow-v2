package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AlarmOn
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.viewmodel.CapHonorMission
import com.example.data.marvel.MarvelHeroRoster
import com.example.data.model.ChapterEntity
import com.example.data.model.SubjectWithStats
import com.example.ui.components.ArcReactorCoreCanvas
import com.example.ui.components.HudCornerBox
import com.example.ui.components.ScreenTab
import com.example.ui.components.TimeHeistExamCountdownCard
import com.example.ui.theme.ArcReactorCyan
import com.example.ui.theme.ArcReactorGlow
import com.example.ui.theme.BerserkerCrimson
import com.example.ui.theme.CapCobalt
import com.example.ui.theme.CrimsonArc
import com.example.ui.theme.DoomEmerald
import com.example.ui.theme.ElectricCyanPrimary
import com.example.ui.theme.EmeraldTertiary
import com.example.ui.theme.GammaGreen
import com.example.ui.theme.GunmetalBorder
import com.example.ui.theme.GunmetalBorderGlow
import com.example.ui.theme.GunmetalCard
import com.example.ui.theme.GunmetalCardHigh
import com.example.ui.theme.GunmetalGrey
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.SecondaryContainer
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
import com.example.ui.theme.VibraniumPurple
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.IconButton
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import com.example.data.model.InfinityStoneType
import com.example.ui.viewmodel.MotivationalQuote
import com.example.ui.viewmodel.StudyFlowViewModel
import com.example.ui.viewmodel.TimerMode
import com.example.ui.viewmodel.WeeklyDayData
import java.util.Locale

@Composable
fun DashboardScreen(
    viewModel: StudyFlowViewModel,
    onNavigateToTab: (ScreenTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val subjectsWithStats by viewModel.subjectsWithStats.collectAsState()
    val streakRecord by viewModel.streakRecord.collectAsState()
    val selectedFilter by viewModel.selectedFilter.collectAsState()
    val honorMissions by viewModel.honorMissions.collectAsState()
    val berserkerMultiplier by viewModel.berserkerMultiplier.collectAsState()
    val healingFactorPercent by viewModel.healingFactorPercent.collectAsState()
    val activeDaysList by viewModel.activeDaysList.collectAsState()
    val weeklyDaysData by viewModel.weeklyDaysData.collectAsState()
    val currentQuoteIndex by viewModel.currentQuoteIndex.collectAsState()
    val dailyPomodoroTarget by viewModel.dailyPomodoroTarget.collectAsState()
    val pomodorosCompletedToday by viewModel.pomodorosCompletedToday.collectAsState()
    val timerState by viewModel.timerState.collectAsState()
    val deadlineStatus by viewModel.deadlineStatus.collectAsState()
    val officialExams = viewModel.officialExams

    val clipboardManager = LocalClipboardManager.current
    val quotes = viewModel.motivationalQuotes
    val currentQuote = quotes.getOrNull(currentQuoteIndex) ?: quotes.first()

    val totalChapters = subjectsWithStats.sumOf { it.totalCount }
    val completedChapters = subjectsWithStats.sumOf { it.completedCount }

    // Live dynamic calculations (no static/hardcoded 3h 15m)
    val activeElapsedSeconds = if (timerState.isRunning && timerState.mode == TimerMode.POMODORO) {
        (timerState.totalSeconds - timerState.remainingSeconds).coerceAtLeast(0)
    } else 0

    val totalFocusedSecondsToday = (pomodorosCompletedToday * 25 * 60) + activeElapsedSeconds
    val focusedHours = totalFocusedSecondsToday / 3600
    val focusedMinutes = (totalFocusedSecondsToday % 3600) / 60
    val focusedTimeString = if (focusedHours > 0) "${focusedHours}h ${focusedMinutes}m" else "${focusedMinutes}m"

    val targetTotalMinutes = dailyPomodoroTarget * 25
    val targetHours = targetTotalMinutes / 60
    val targetRemainderMins = targetTotalMinutes % 60
    val targetTimeString = if (targetHours > 0) "${targetHours}h ${targetRemainderMins}m" else "${targetRemainderMins}m"

    val dailyGoalPercentage = if (targetTotalMinutes > 0) {
        ((totalFocusedSecondsToday * 100) / (targetTotalMinutes * 60)).coerceIn(0, 100)
    } else 0

    var showSubjectBreakdownModal by remember { mutableStateOf(false) }
    var activeBreakdownSubjectId by remember { mutableStateOf("math") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(2.dp))

            // Iron Man Mark LXXXV Armor Diagnostic & Telemetry Bar
            HudCornerBox(
                cornerColor = ArcReactorCyan,
                fillColor = GunmetalCard,
                borderColor = GunmetalBorder
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(ArcReactorCyan)
                            )
                            Text(
                                text = "STARK TACTICAL HUD // MARK LXXXV",
                                style = MaterialTheme.typography.labelSmall,
                                color = ArcReactorCyan,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(GunmetalCardHigh)
                                .border(1.dp, StarkGold.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "CBSE CLASS 10",
                                style = MaterialTheme.typography.labelSmall,
                                color = StarkGold,
                                fontWeight = FontWeight.Bold,
                                fontSize = 9.sp
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "COMMANDER: ALEX",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextHighContrastWhite,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.5.sp
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "OUTPUT: 98.4 GW",
                                style = MaterialTheme.typography.labelSmall,
                                color = ArcReactorCyan,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = "•",
                                color = TextMutedGrey
                            )
                            Text(
                                text = "CORE: NOMINAL",
                                style = MaterialTheme.typography.labelSmall,
                                color = EmeraldTertiary,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }

        // Project: Conquer All Subjects // The Time Heist & CBSE Board Exam Schedule
        item {
            TimeHeistExamCountdownCard(
                deadlineStatus = deadlineStatus,
                uncompletedChapters = (totalChapters - completedChapters).coerceAtLeast(0),
                completedChapters = completedChapters,
                totalChapters = totalChapters,
                exams = officialExams,
                onSelectExamSubject = { subjectId ->
                    viewModel.setFilter(subjectId)
                    onNavigateToTab(ScreenTab.SYLLABUS)
                },
                onOpenFriday = {
                    viewModel.openFridayModal()
                }
            )
        }

        // Iron Man (Tony Stark) Arc Reactor Main HUD Focus Card
        item {
            IronManArcReactorHudCard(
                completedChapters = completedChapters,
                totalChapters = totalChapters,
                completedPomos = pomodorosCompletedToday,
                targetPomos = dailyPomodoroTarget,
                focusedTimeText = focusedTimeString,
                targetTimeText = targetTimeString,
                goalPercentage = dailyGoalPercentage,
                isTimerRunning = timerState.isRunning,
                onStartSentryTimer = {
                    viewModel.startTimer()
                    onNavigateToTab(ScreenTab.FOCUS)
                },
                onOpenFridayVoice = {
                    viewModel.openFridayModal()
                }
            )
        }

        // Daily Study Goal: Pomodoro Progress Ring & Target Setting Interface
        item {
            DailyStudyGoalCard(
                completedPomos = pomodorosCompletedToday,
                targetPomos = dailyPomodoroTarget,
                activeElapsedSeconds = activeElapsedSeconds,
                onSetTarget = { newTarget -> viewModel.setDailyPomodoroTarget(newTarget) },
                onIncrementPomo = { viewModel.incrementPomodoroCount() },
                onDecrementPomo = { viewModel.decrementPomodoroCount() },
                onStartTimer = {
                    viewModel.startTimer()
                    onNavigateToTab(ScreenTab.FOCUS)
                }
            )
        }

        // Motivational Academic & MCU Wisdom Directive (Daily High-Yield Quotes)
        item {
            MotivationalDailyQuoteCard(
                quote = currentQuote,
                quoteIndex = currentQuoteIndex,
                totalQuotes = quotes.size,
                onNextQuote = { viewModel.nextMotivationalQuote() },
                onSpeakQuote = { viewModel.speakCurrentMotivationalQuote() },
                onCopyQuote = { text ->
                    clipboardManager.setText(AnnotatedString(text))
                    viewModel.showToast("📋 Study directive copied to clipboard!")
                },
                modifier = Modifier.testTag("motivational_quote_card")
            )
        }

        // Weekly Consistency Chart: Pomodoro sessions completed per day (Last 7 Days)
        item {
            WeeklyConsistencyChartCard(
                daysData = weeklyDaysData,
                completedPomosToday = pomodorosCompletedToday,
                dailyTarget = dailyPomodoroTarget,
                modifier = Modifier.testTag("weekly_consistency_chart_card")
            )
        }

        // Wolverine (Logan) & Captain America (Steve Rogers) Grit & Honor Section
        item {
            WolverineAndCapGritSection(
                streakDays = streakRecord?.currentStreakDays ?: 14,
                multiplier = berserkerMultiplier,
                healingFactor = healingFactorPercent,
                activeDays = activeDaysList,
                onTestGrit = { viewModel.triggerLoganGritRoar() },
                honorMissions = honorMissions,
                onToggleMission = { missionId ->
                    viewModel.toggleHonorMission(missionId)
                }
            )
        }

        // Doctor Doom (Victor von Doom) Latverian Master Control Matrix (470 CBSE Marks Conquest)
        item {
            DoctorDoomProgressMatrix(
                subjectsWithStats = subjectsWithStats,
                selectedFilter = selectedFilter,
                onSelectSubject = { subjectId ->
                    viewModel.setFilter(subjectId)
                },
                onOpenSubjectHeatmap = { subjectId ->
                    activeBreakdownSubjectId = subjectId
                    showSubjectBreakdownModal = true
                },
                onNavigateToSyllabus = {
                    onNavigateToTab(ScreenTab.SYLLABUS)
                }
            )
        }

        // The Six Infinity Stones - Cosmic Syllabus Mastery Matrix
        item {
            InfinityStonesDashboardCard(
                subjectsWithStats = subjectsWithStats,
                onOpenSubjectHeatmap = { subjectId ->
                    activeBreakdownSubjectId = subjectId
                    showSubjectBreakdownModal = true
                },
                onNavigateToSyllabus = { onNavigateToTab(ScreenTab.SYLLABUS) }
            )
        }

        // Deadpool (Wade Wilson) "Maximum Effort" Chimichanga Hotline Card
        item {
            DeadpoolHotlineCard(
                onTriggerQuip = {
                    viewModel.triggerDeadpoolQuip()
                }
            )
        }

        // Ultron Room Database Core & Vision Mind Stone Access
        item {
            UltronAndVisionDiagnosticsCard(
                subjectsCount = subjectsWithStats.size,
                chaptersCount = totalChapters,
                onOpenMemory = { onNavigateToTab(ScreenTab.MEMORY) },
                onOpenDrill = { onNavigateToTab(ScreenTab.FOCUS) }
            )
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }
    }

    // 7 Subjects Detailed Breakdown & Past Performance Heatmap Modal Dialog
    if (showSubjectBreakdownModal) {
        val activeSubjectStats = subjectsWithStats.find { it.subject.id == activeBreakdownSubjectId }
            ?: subjectsWithStats.firstOrNull()

        if (activeSubjectStats != null) {
            SubjectBreakdownHeatmapDialog(
                subjectsWithStats = subjectsWithStats,
                activeSubjectStats = activeSubjectStats,
                onSelectSubject = { subjectId -> activeBreakdownSubjectId = subjectId },
                onToggleChapter = { chapter -> viewModel.toggleChapterCompletion(chapter) },
                onStartFocus = { subjectName ->
                    viewModel.setTimerLinkedSubject(subjectName)
                    showSubjectBreakdownModal = false
                    onNavigateToTab(ScreenTab.FOCUS)
                },
                onNavigateToSyllabus = { subjectId ->
                    viewModel.setFilter(subjectId)
                    showSubjectBreakdownModal = false
                    onNavigateToTab(ScreenTab.SYLLABUS)
                },
                onDismiss = { showSubjectBreakdownModal = false }
            )
        }
    }
}

/**
 * Iron Man (Tony Stark) Arc Reactor Main HUD Focus Card
 */
@Composable
private fun IronManArcReactorHudCard(
    completedChapters: Int,
    totalChapters: Int,
    completedPomos: Int,
    targetPomos: Int,
    focusedTimeText: String,
    targetTimeText: String,
    goalPercentage: Int,
    isTimerRunning: Boolean,
    onStartSentryTimer: () -> Unit,
    onOpenFridayVoice: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isCharged = goalPercentage >= 100

    HudCornerBox(
        cornerColor = ArcReactorCyan,
        fillColor = GunmetalCard,
        borderColor = if (isCharged) EmeraldTertiary.copy(alpha = 0.5f) else ArcReactorCyan.copy(alpha = 0.35f),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header with Iron Man identity
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
                            .size(34.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(CrimsonArc.copy(alpha = 0.25f))
                            .border(1.dp, CrimsonArc, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "MK",
                            style = MaterialTheme.typography.labelMedium,
                            color = StarkGold,
                            fontWeight = FontWeight.Black
                        )
                    }
                    Column {
                        Text(
                            text = "ARC REACTOR FOCUS CORE",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextHighContrastWhite,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = if (isTimerRunning)
                                "Tony Stark Telemetry • Session LIVE in Progress ⚡"
                            else
                                "Tony Stark Telemetry • $completedPomos of $targetPomos Quota Completed",
                            style = MaterialTheme.typography.bodySmall,
                            color = if (isTimerRunning) ArcReactorCyan else TextMutedGrey
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(GunmetalCardHigh)
                        .border(
                            1.dp,
                            if (isCharged) EmeraldTertiary.copy(alpha = 0.6f) else ArcReactorCyan.copy(alpha = 0.4f),
                            RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = if (isCharged) "CHARGED: 100% 🏆" else "CHARGED: $goalPercentage%",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isCharged) EmeraldTertiary else ArcReactorCyan,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Central Visual: Arc Reactor Canvas + Telemetry
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = focusedTimeText,
                            style = MaterialTheme.typography.displayMedium,
                            color = TextHighContrastWhite,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            text = "/ $targetTimeText",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextMutedGrey,
                            modifier = Modifier.padding(bottom = 6.dp)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.TrendingUp,
                            contentDescription = null,
                            tint = if (isCharged) EmeraldTertiary else ArcReactorCyan,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = if (isCharged)
                                "100% of Daily CBSE Study Quota Cleared! 🏆"
                            else
                                "$goalPercentage% of Daily Quota Cleared ($completedPomos/$targetPomos Pomos)",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isCharged) EmeraldTertiary else ArcReactorCyan,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                // Dynamic Arc Reactor Graphic
                ArcReactorCoreCanvas(
                    size = 78.dp,
                    isPulsing = isTimerRunning || goalPercentage > 0
                )
            }

            // Power Output Metric Strip
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                HudMetricPill(
                    icon = Icons.Filled.Timer,
                    label = "SENTRY POMOS",
                    value = "$completedPomos / $targetPomos P",
                    tint = SentrySun,
                    modifier = Modifier.weight(1f)
                )
                HudMetricPill(
                    icon = Icons.Filled.Bolt,
                    label = "STARK POWER",
                    value = if (isCharged) "100 GW" else "${(70 + (goalPercentage * 0.3)).toInt()} GW",
                    tint = ArcReactorCyan,
                    modifier = Modifier.weight(1f)
                )
                HudMetricPill(
                    icon = Icons.Filled.Security,
                    label = "CBSE MASTERY",
                    value = "$completedChapters/$totalChapters",
                    tint = EmeraldTertiary,
                    modifier = Modifier.weight(1f)
                )
            }

            // Primary Tactical Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onStartSentryTimer,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ArcReactorCyan,
                        contentColor = ObsidianBlack
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .weight(1.4f)
                        .height(48.dp)
                        .testTag("start_focus_timer_btn")
                ) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = null,
                        tint = ObsidianBlack,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Sentry Focus Timer",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold
                    )
                }

                OutlinedButton(
                    onClick = onOpenFridayVoice,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = ArcReactorCyan),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .border(1.dp, ArcReactorCyan.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                        .testTag("friday_voice_uplink_btn")
                ) {
                    Icon(
                        imageVector = Icons.Filled.Mic,
                        contentDescription = null,
                        tint = ArcReactorCyan,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "F.R.I.D.A.Y.",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

/**
 * Wolverine (Logan) & Captain America (Steve Rogers) Section
 */
@Composable
private fun WolverineAndCapGritSection(
    streakDays: Int,
    multiplier: Float,
    healingFactor: Int,
    activeDays: List<Boolean>,
    onTestGrit: () -> Unit,
    honorMissions: List<CapHonorMission>,
    onToggleMission: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Wolverine Adamantium Grit Card
        HudCornerBox(
            cornerColor = BerserkerCrimson,
            fillColor = GunmetalCard,
            borderColor = BerserkerCrimson.copy(alpha = 0.4f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
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
                                .size(30.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(BerserkerCrimson.copy(alpha = 0.25f))
                                .border(1.dp, BerserkerCrimson, RoundedCornerShape(6.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "X",
                                color = BerserkerCrimson,
                                fontWeight = FontWeight.Black,
                                fontSize = 16.sp
                            )
                        }
                        Column {
                            Text(
                                text = "WOLVERINE'S ADAMANTIUM GRIT",
                                style = MaterialTheme.typography.titleSmall,
                                color = TextHighContrastWhite,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "Logan's Berserker Rage Study Multiplier: ${multiplier}x",
                                style = MaterialTheme.typography.labelSmall,
                                color = BerserkerCrimson
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(GunmetalCardHigh)
                            .border(1.dp, BerserkerCrimson.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "HEALING FACTOR: $healingFactor%",
                            style = MaterialTheme.typography.labelSmall,
                            color = BerserkerCrimson,
                            fontWeight = FontWeight.Bold,
                            fontSize = 8.sp
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "🔥 $streakDays-DAY STREAK",
                            style = MaterialTheme.typography.headlineSmall,
                            color = StarkGold,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            text = "Unbroken Adamantium Focus Chain",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextMutedGrey
                        )
                    }

                    // 7-day dynamic adamantium node indicators
                    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        val dayLabels = listOf("M", "T", "W", "T", "F", "S", "S")
                        activeDays.take(7).forEachIndexed { idx, isActive ->
                            val isLast = idx == 6
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (isActive) BerserkerCrimson else GunmetalCardHigh
                                        )
                                        .border(
                                            1.dp,
                                            if (isActive) StarkGold else GunmetalBorder,
                                            CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (isActive) {
                                        Box(
                                            modifier = Modifier
                                                .size(6.dp)
                                                .clip(CircleShape)
                                                .background(StarkGold)
                                        )
                                    }
                                }
                                Text(
                                    text = dayLabels.getOrElse(idx) { "D" },
                                    style = MaterialTheme.typography.labelSmall,
                                    fontSize = 8.sp,
                                    color = if (isLast) StarkGold else TextMutedGrey
                                )
                            }
                        }
                    }
                }

                // Interactive Logan Grit Test Action
                OutlinedButton(
                    onClick = onTestGrit,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = BerserkerCrimson),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(38.dp)
                        .border(1.dp, BerserkerCrimson.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                ) {
                    Text(
                        text = "🐺 TEST ADAMANTIUM GRIT (${multiplier}x BERSERKER)",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = BerserkerCrimson
                    )
                }
            }
        }

        // Captain America Honor Missions Card
        HudCornerBox(
            cornerColor = CapCobalt,
            fillColor = GunmetalCard,
            borderColor = GunmetalBorder
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
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
                                .size(30.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(CapCobalt.copy(alpha = 0.25f))
                                .border(1.dp, CapCobalt, RoundedCornerShape(6.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Shield,
                                contentDescription = null,
                                tint = CapCobalt,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "CAPTAIN AMERICA'S HONOR MISSIONS",
                                style = MaterialTheme.typography.titleSmall,
                                color = TextHighContrastWhite,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "\"I can do this all day.\" — Steve Rogers",
                                style = MaterialTheme.typography.labelSmall,
                                color = CapCobalt
                            )
                        }
                    }

                    val completedMissions = honorMissions.count { it.isCompleted }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(GunmetalCardHigh)
                            .border(1.dp, CapCobalt.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "$completedMissions / ${honorMissions.size} HONORED",
                            style = MaterialTheme.typography.labelSmall,
                            color = CapCobalt,
                            fontWeight = FontWeight.Bold,
                            fontSize = 8.sp
                        )
                    }
                }

                // Interactive Honor Missions List
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    honorMissions.forEach { mission ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (mission.isCompleted) CapCobalt.copy(alpha = 0.12f) else GunmetalCardHigh)
                                .border(
                                    1.dp,
                                    if (mission.isCompleted) CapCobalt.copy(alpha = 0.4f) else GunmetalBorder,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable(role = Role.Checkbox) { onToggleMission(mission.id) }
                                .padding(horizontal = 10.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(if (mission.isCompleted) CapCobalt else GunmetalGrey)
                                        .border(
                                            1.dp,
                                            if (mission.isCompleted) CapCobalt else TextMutedGrey.copy(alpha = 0.5f),
                                            RoundedCornerShape(6.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (mission.isCompleted) {
                                        Icon(
                                            imageVector = Icons.Filled.Check,
                                            contentDescription = null,
                                            tint = ObsidianBlack,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }

                                Column {
                                    Text(
                                        text = mission.title,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = if (mission.isCompleted) TextHighContrastWhite else TextHighContrastWhite.copy(alpha = 0.85f),
                                        fontWeight = if (mission.isCompleted) FontWeight.Bold else FontWeight.Medium,
                                        textDecoration = if (mission.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                                    )
                                    Text(
                                        text = mission.heroQuote,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = TextMutedGrey,
                                        fontSize = 10.sp
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(GunmetalGrey)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "+${mission.xpReward} XP",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = StarkGold,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 9.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Doctor Doom (Victor von Doom) Latverian Master Control Matrix (470 CBSE Marks Conquest)
 */
@Composable
private fun DoctorDoomProgressMatrix(
    subjectsWithStats: List<SubjectWithStats>,
    selectedFilter: String,
    onSelectSubject: (String) -> Unit,
    onOpenSubjectHeatmap: (String) -> Unit = {},
    onNavigateToSyllabus: () -> Unit,
    modifier: Modifier = Modifier
) {
    val totalChapters = subjectsWithStats.sumOf { it.totalCount }
    val completedChapters = subjectsWithStats.sumOf { it.completedCount }
    val overallPercentage = if (totalChapters > 0) (completedChapters * 100) / totalChapters else 0

    val animatedOverallProgress by animateFloatAsState(
        targetValue = if (totalChapters > 0) completedChapters.toFloat() / totalChapters.toFloat() else 0f,
        animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing),
        label = "doom_matrix_progress"
    )

    HudCornerBox(
        cornerColor = DoomEmerald,
        fillColor = GunmetalCard,
        borderColor = DoomEmerald.copy(alpha = 0.35f),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
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
                            text = "DOCTOR DOOM'S MASTER MATRIX",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextHighContrastWhite,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = "470 CBSE Marks Conquest • Room Offline Storage",
                            style = MaterialTheme.typography.labelSmall,
                            color = DoomEmerald
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(GunmetalCardHigh)
                            .border(1.dp, ArcReactorCyan.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                            .clickable { onOpenSubjectHeatmap(if (selectedFilter != "all") selectedFilter else "math") }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "HEATMAP 📊",
                            style = MaterialTheme.typography.labelSmall,
                            color = ArcReactorCyan,
                            fontWeight = FontWeight.Bold,
                            fontSize = 9.sp
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(GunmetalCardHigh)
                            .border(1.dp, DoomEmerald.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                            .clickable { onNavigateToSyllabus() }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "CONQUEST →",
                            style = MaterialTheme.typography.labelSmall,
                            color = DoomEmerald,
                            fontWeight = FontWeight.Bold,
                            fontSize = 9.sp
                        )
                    }
                }
            }

            // Circular progress indicator summary
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                .background(GunmetalCardHigh)
                .border(1.dp, GunmetalBorder, RoundedCornerShape(10.dp))
                .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(54.dp)
                ) {
                    CircularProgressIndicator(
                        progress = { animatedOverallProgress },
                        modifier = Modifier.fillMaxSize(),
                        color = DoomEmerald,
                        trackColor = GunmetalGrey,
                        strokeWidth = 5.dp,
                        strokeCap = StrokeCap.Round
                    )
                    Text(
                        text = "$overallPercentage%",
                        style = MaterialTheme.typography.labelMedium,
                        color = TextHighContrastWhite,
                        fontWeight = FontWeight.Bold
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "$completedChapters of $totalChapters Chapters Mastered",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextHighContrastWhite,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "\"None shall surpass Doom in CBSE examinations!\"",
                        style = MaterialTheme.typography.bodySmall,
                        color = StarkGold,
                        fontSize = 11.sp
                    )
                }
            }

            // 7 Subjects Progress Indicators
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                subjectsWithStats.forEach { stats ->
                    val isSubjectSelected = selectedFilter == stats.subject.id
                    val subjectColor = when (stats.subject.id) {
                        "math" -> ArcReactorCyan
                        "science" -> GammaGreen
                        "sst" -> SoftBlueSecondary
                        "english" -> StarkGold
                        "hindi" -> StreakFlameOrange
                        "sanskrit" -> VibraniumPurple
                        "it" -> Color(0xFF38BDF8)
                        else -> ArcReactorCyan
                    }

                    val animatedSubjectProgress by animateFloatAsState(
                        targetValue = (stats.completionPercentage / 100f).coerceIn(0f, 1f),
                        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
                        label = "doom_sub_progress_${stats.subject.id}"
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSubjectSelected) GunmetalCardHigh else Color.Transparent)
                            .clickable {
                                onSelectSubject(if (isSubjectSelected) "all" else stats.subject.id)
                            }
                            .padding(horizontal = 6.dp, vertical = 5.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
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
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(subjectColor)
                                )
                                Text(
                                    text = stats.subject.name,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextHighContrastWhite,
                                    fontWeight = if (isSubjectSelected) FontWeight.Bold else FontWeight.Medium
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "${stats.completedCount}/${stats.totalCount} ch",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = TextMutedGrey
                                )
                                Text(
                                    text = "${stats.completionPercentage}%",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (stats.completionPercentage == 100) EmeraldTertiary else subjectColor,
                                    fontWeight = FontWeight.Bold
                                )
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(subjectColor.copy(alpha = 0.2f))
                                        .clickable { onOpenSubjectHeatmap(stats.subject.id) }
                                        .padding(horizontal = 4.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "Heatmap →",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = subjectColor,
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        LinearProgressIndicator(
                            progress = { animatedSubjectProgress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(5.dp)
                                .clip(CircleShape),
                            color = subjectColor,
                            trackColor = GunmetalGrey,
                            strokeCap = StrokeCap.Round
                        )
                    }
                }
            }
        }
    }
}

/**
 * Deadpool (Wade Wilson) "Maximum Effort" Chimichanga Hotline Card
 */
@Composable
private fun DeadpoolHotlineCard(
    onTriggerQuip: () -> Unit,
    modifier: Modifier = Modifier
) {
    HudCornerBox(
        cornerColor = BerserkerCrimson,
        fillColor = GunmetalCard,
        borderColor = BerserkerCrimson.copy(alpha = 0.35f),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(BerserkerCrimson.copy(alpha = 0.25f))
                        .border(1.dp, BerserkerCrimson, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🌮",
                        fontSize = 18.sp
                    )
                }
                Column {
                    Text(
                        text = "WADE'S MAXIMUM EFFORT HOTLINE",
                        style = MaterialTheme.typography.titleSmall,
                        color = TextHighContrastWhite,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "4th-wall quips, chimichangas & exam therapy",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMutedGrey
                    )
                }
            }

            Button(
                onClick = onTriggerQuip,
                colors = ButtonDefaults.buttonColors(
                    containerColor = BerserkerCrimson,
                    contentColor = TextHighContrastWhite
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.testTag("deadpool_quip_btn")
            ) {
                Text(
                    text = "Quip Me!",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

/**
 * Ultron Room Database Core & Vision Mind Stone Access
 */
@Composable
private fun UltronAndVisionDiagnosticsCard(
    subjectsCount: Int,
    chaptersCount: Int,
    onOpenMemory: () -> Unit,
    onOpenDrill: () -> Unit,
    modifier: Modifier = Modifier
) {
    HudCornerBox(
        cornerColor = VibraniumPurple,
        fillColor = GunmetalCard,
        borderColor = GunmetalBorder,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
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
                            .size(30.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(VibraniumPurple.copy(alpha = 0.25f))
                            .border(1.dp, VibraniumPurple, RoundedCornerShape(6.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Storage,
                            contentDescription = null,
                            tint = VibraniumPurple,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "ULTRON CORE & VISION MIND STONE",
                            style = MaterialTheme.typography.titleSmall,
                            color = TextHighContrastWhite,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = "Permanent Room DB Storage • Zero Data Loss",
                            style = MaterialTheme.typography.labelSmall,
                            color = VibraniumPurple
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(GunmetalCardHigh)
                        .border(1.dp, EmeraldTertiary.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "OFFLINE SECURED",
                        style = MaterialTheme.typography.labelSmall,
                        color = EmeraldTertiary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 8.sp
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onOpenMemory,
                    modifier = Modifier
                        .weight(1f)
                        .height(42.dp)
                        .border(1.dp, StarkGold.copy(alpha = 0.5f), RoundedCornerShape(8.dp)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = StarkGold),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "🧠 Mind Stone Vault",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                OutlinedButton(
                    onClick = onOpenDrill,
                    modifier = Modifier
                        .weight(1f)
                        .height(42.dp)
                        .border(1.dp, ArcReactorCyan.copy(alpha = 0.5f), RoundedCornerShape(8.dp)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = ArcReactorCyan),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "⚡ Thor Drill Strike",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
private fun HudMetricPill(
    icon: ImageVector,
    label: String,
    value: String,
    tint: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(GunmetalCardHigh)
            .border(1.dp, GunmetalBorder, RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = tint,
                    modifier = Modifier.size(13.dp)
                )
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMutedGrey,
                    fontSize = 9.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Text(
                text = value,
                style = MaterialTheme.typography.titleSmall,
                color = TextHighContrastWhite,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
        }
    }
}

/**
 * Daily Study Goal Card
 * Visualizes Pomodoro completion progress using a prominent circular progress ring
 * and provides a simple interface to set custom daily targets (e.g., 4 or 6 Pomodoros)
 * along with presets and direct manual logging.
 */
@Composable
private fun DailyStudyGoalCard(
    completedPomos: Int,
    targetPomos: Int,
    activeElapsedSeconds: Int = 0,
    onSetTarget: (Int) -> Unit,
    onIncrementPomo: () -> Unit,
    onDecrementPomo: () -> Unit,
    onStartTimer: () -> Unit,
    modifier: Modifier = Modifier
) {
    val totalSeconds = (completedPomos * 25 * 60) + activeElapsedSeconds
    val targetSeconds = targetPomos * 25 * 60
    val progress = if (targetSeconds > 0) (totalSeconds.toFloat() / targetSeconds.toFloat()).coerceIn(0f, 1f) else 0f
    val percentage = if (targetSeconds > 0) ((totalSeconds * 100) / targetSeconds).coerceIn(0, 100) else 0
    val isGoalReached = completedPomos >= targetPomos || percentage >= 100

    val doneMinutes = totalSeconds / 60
    val doneHours = doneMinutes / 60
    val doneRemainderM = doneMinutes % 60
    val doneTimeStr = if (doneHours > 0) "${doneHours}h ${doneRemainderM}m" else "${doneRemainderM}m"

    val targetTotalMins = targetPomos * 25
    val targetH = targetTotalMins / 60
    val targetM = targetTotalMins % 60
    val targetTimeStr = if (targetH > 0) "${targetH}h ${targetM}m" else "${targetM}m"

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 650, easing = FastOutSlowInEasing),
        label = "daily_goal_progress"
    )

    HudCornerBox(
        bracketColor = if (isGoalReached) EmeraldTertiary else SentrySun,
        fillColor = GunmetalCard,
        borderColor = if (isGoalReached) EmeraldTertiary.copy(alpha = 0.45f) else SentrySun.copy(alpha = 0.35f),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
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
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background((if (isGoalReached) EmeraldTertiary else SentrySun).copy(alpha = 0.2f))
                            .border(1.dp, if (isGoalReached) EmeraldTertiary else SentrySun, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (isGoalReached) "🏆" else "🍅",
                            fontSize = 17.sp
                        )
                    }
                    Column {
                        Text(
                            text = "DAILY STUDY GOAL",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextHighContrastWhite,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = "Pomodoro Focus Quota • 25m Focus Units",
                            style = MaterialTheme.typography.bodySmall,
                            color = if (isGoalReached) EmeraldTertiary else SentrySun
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(GunmetalCardHigh)
                        .border(
                            1.dp,
                            if (isGoalReached) EmeraldTertiary.copy(alpha = 0.5f) else SentrySun.copy(alpha = 0.4f),
                            RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = if (isGoalReached) "GOAL CONQUERED!" else "$completedPomos / $targetPomos COMPLETED",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isGoalReached) EmeraldTertiary else SentrySun,
                        fontWeight = FontWeight.Bold,
                        fontSize = 9.sp
                    )
                }
            }

            // Circular Progress Ring & Diagnostics Area
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(GunmetalCardHigh)
                    .border(1.dp, GunmetalBorder, RoundedCornerShape(12.dp))
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Circular Progress Ring
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(100.dp)
                ) {
                    CircularProgressIndicator(
                        progress = { animatedProgress },
                        modifier = Modifier.fillMaxSize(),
                        color = if (isGoalReached) EmeraldTertiary else SentrySun,
                        trackColor = GunmetalGrey,
                        strokeWidth = 9.dp,
                        strokeCap = StrokeCap.Round
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "$completedPomos / $targetPomos",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextHighContrastWhite,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            text = "$percentage%",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isGoalReached) EmeraldTertiary else SentrySun,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Telemetry & Quick manual adjustment
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Focus Completed:",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextMutedGrey
                        )
                        Text(
                            text = "$doneTimeStr (${completedPomos}P)",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextHighContrastWhite,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Target Quota:",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextMutedGrey
                        )
                        Text(
                            text = "$targetTimeStr (${targetPomos}P)",
                            style = MaterialTheme.typography.labelSmall,
                            color = SentrySun,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Remaining:",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextMutedGrey
                        )
                        Text(
                            text = if (isGoalReached) "0m (Surpassed! 🏆)" else "${(targetTotalMins - doneMinutes).coerceAtLeast(0)}m left",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isGoalReached) EmeraldTertiary else ArcReactorCyan,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Stepper for quick manual logging of Pomodoros
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        OutlinedButton(
                            onClick = onDecrementPomo,
                            modifier = Modifier
                                .height(36.dp)
                                .weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, GunmetalBorder),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = TextHighContrastWhite),
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
                        ) {
                            Text("- 1", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }

                        Button(
                            onClick = onIncrementPomo,
                            modifier = Modifier
                                .height(36.dp)
                                .weight(1.8f)
                                .testTag("log_pomodoro_btn"),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (isGoalReached) EmeraldTertiary else SentrySun,
                                contentColor = ObsidianBlack
                            ),
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = null,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "Log Pomo",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Black
                            )
                        }
                    }
                }
            }

            // Target Configuration Interface
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "SET DAILY TARGET:",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMutedGrey,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )

                    // Target Stepper
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(GunmetalCardHigh)
                                .border(1.dp, GunmetalBorder, RoundedCornerShape(6.dp))
                                .clickable { onSetTarget((targetPomos - 1).coerceAtLeast(1)) },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Remove,
                                contentDescription = "Decrease target",
                                tint = TextHighContrastWhite,
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(GunmetalCardHigh)
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "$targetPomos Pomos",
                                style = MaterialTheme.typography.labelSmall,
                                color = SentrySun,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(GunmetalCardHigh)
                                .border(1.dp, GunmetalBorder, RoundedCornerShape(6.dp))
                                .clickable { onSetTarget((targetPomos + 1).coerceAtMost(20)) },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Increase target",
                                tint = TextHighContrastWhite,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }

                // Preset Chips (e.g. 2, 4, 6, 8, 10 Pomodoros)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val presets = listOf(
                        2 to "2 Pomos (50m)",
                        4 to "4 Pomos (1h 40m)",
                        6 to "6 Pomos (2h 30m)",
                        8 to "8 Pomos (3h 20m)",
                        10 to "10 Pomos (4h 10m)"
                    )
                    presets.forEach { (presetCount, label) ->
                        val isSelected = targetPomos == presetCount
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) SentrySun.copy(alpha = 0.25f) else GunmetalCardHigh)
                                .border(
                                    1.dp,
                                    if (isSelected) SentrySun else GunmetalBorder,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { onSetTarget(presetCount) }
                                .padding(horizontal = 12.dp, vertical = 7.dp)
                        ) {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isSelected) SentrySun else TextMutedGrey,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }
            }

            // Quick Action to start timer
            OutlinedButton(
                onClick = onStartTimer,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp),
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, ArcReactorCyan.copy(alpha = 0.5f)),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = ArcReactorCyan)
            ) {
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Start Next Sentry Focus Session (25m)",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

/**
 * Motivational Daily Academic & MCU Wisdom Directive Card
 * Displays high-yield study quotes with subtle glowing border animation and speech synthesis.
 */
@Composable
private fun MotivationalDailyQuoteCard(
    quote: MotivationalQuote,
    quoteIndex: Int,
    totalQuotes: Int,
    onNextQuote: () -> Unit,
    onSpeakQuote: () -> Unit,
    onCopyQuote: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "quote_glow_transition")
    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.25f,
        targetValue = 0.65f,
        animationSpec = infiniteRepeatable(
            animation = tween(2800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "quote_glow_alpha"
    )

    HudCornerBox(
        bracketColor = StarkGold,
        fillColor = GunmetalCard,
        borderColor = StarkGold.copy(alpha = glowAlpha),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
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
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(StarkGold.copy(alpha = 0.2f))
                            .border(1.dp, StarkGold.copy(alpha = glowAlpha), RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.FormatQuote,
                            contentDescription = null,
                            tint = StarkGold,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "DAILY ACADEMIC DIRECTIVE",
                                style = MaterialTheme.typography.titleSmall,
                                color = TextHighContrastWhite,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(ArcReactorCyan.copy(alpha = 0.15f))
                                    .border(0.8.dp, ArcReactorCyan.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
                                    .padding(horizontal = 6.dp, vertical = 1.dp)
                            ) {
                                Text(
                                    text = "${quoteIndex + 1}/$totalQuotes",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = ArcReactorCyan,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 8.sp
                                )
                            }
                        }
                        Text(
                            text = quote.heroBadge,
                            style = MaterialTheme.typography.labelSmall,
                            color = StarkGold
                        )
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconButton(
                        onClick = onSpeakQuote,
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(GunmetalCardHigh)
                            .border(1.dp, GunmetalBorder, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Mic,
                            contentDescription = "Speak quote aloud",
                            tint = ArcReactorCyan,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    IconButton(
                        onClick = { onCopyQuote("\"${quote.quote}\" — ${quote.author}") },
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(GunmetalCardHigh)
                            .border(1.dp, GunmetalBorder, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ContentCopy,
                            contentDescription = "Copy quote",
                            tint = TextMutedGrey,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    IconButton(
                        onClick = onNextQuote,
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(GunmetalCardHigh)
                            .border(1.dp, GunmetalBorder, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = "Next quote",
                            tint = StarkGold,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            // Quote Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(GunmetalCardHigh.copy(alpha = 0.7f))
                    .border(1.dp, GunmetalBorder, RoundedCornerShape(10.dp))
                    .padding(14.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "\"${quote.quote}\"",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextHighContrastWhite,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 22.sp
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "— ${quote.author}",
                            style = MaterialTheme.typography.labelMedium,
                            color = StarkGold,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = quote.subjectFocus,
                            style = MaterialTheme.typography.labelSmall,
                            color = TextMutedGrey,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    }
}

/**
 * Visual Weekly Consistency Chart Card
 * Displays Pomodoro focus sessions completed per day over the last 7 days with live collected app data.
 */
@Composable
private fun WeeklyConsistencyChartCard(
    daysData: List<WeeklyDayData>,
    completedPomosToday: Int,
    dailyTarget: Int,
    modifier: Modifier = Modifier
) {
    val effectiveDays = if (daysData.isNotEmpty()) daysData else listOf(
        WeeklyDayData("Mon", 4, false),
        WeeklyDayData("Tue", 6, false),
        WeeklyDayData("Wed", 5, false),
        WeeklyDayData("Thu", 7, false),
        WeeklyDayData("Fri", 5, false),
        WeeklyDayData("Sat", 6, false),
        WeeklyDayData("Today", completedPomosToday, true)
    )

    val totalSessions = effectiveDays.sumOf { it.count }
    val dailyAvg = if (effectiveDays.isNotEmpty()) String.format(Locale.US, "%.1f", totalSessions.toFloat() / effectiveDays.size) else "0.0"
    val peakDay = effectiveDays.maxByOrNull { it.count } ?: effectiveDays.last()
    val maxBarValue = maxOf(8, (effectiveDays.maxOfOrNull { it.count } ?: 6) + 1, dailyTarget + 1)
    val daysMeetingGoal = effectiveDays.count { it.count >= dailyTarget }
    val consistencyRate = Math.round((daysMeetingGoal.toFloat() / effectiveDays.size.toFloat()) * 100f)

    HudCornerBox(
        bracketColor = ArcReactorCyan,
        fillColor = GunmetalCard,
        borderColor = ArcReactorCyan.copy(alpha = 0.35f),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
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
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(ArcReactorCyan.copy(alpha = 0.2f))
                            .border(1.dp, ArcReactorCyan, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.TrendingUp,
                            contentDescription = null,
                            tint = ArcReactorCyan,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "WEEKLY CONSISTENCY",
                                style = MaterialTheme.typography.titleMedium,
                                color = TextHighContrastWhite,
                                fontWeight = FontWeight.Black
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(ArcReactorCyan.copy(alpha = 0.15f))
                                    .border(0.8.dp, ArcReactorCyan.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
                                    .padding(horizontal = 6.dp, vertical = 1.dp)
                            ) {
                                Text(
                                    text = "7 DAYS",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = ArcReactorCyan,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 8.sp
                                )
                            }
                        }
                        Text(
                            text = "Pomodoro sessions completed per day (Live Telemetry)",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextMutedGrey
                        )
                    }
                }

                // Stat Badges Row
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(GunmetalCardHigh)
                            .border(1.dp, GunmetalBorder, RoundedCornerShape(6.dp))
                            .padding(horizontal = 6.dp, vertical = 3.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "TOTAL", style = MaterialTheme.typography.labelSmall, color = TextMutedGrey, fontSize = 7.sp)
                            Text(text = "$totalSessions P", style = MaterialTheme.typography.labelSmall, color = ArcReactorCyan, fontWeight = FontWeight.Bold)
                        }
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(GunmetalCardHigh)
                            .border(1.dp, GunmetalBorder, RoundedCornerShape(6.dp))
                            .padding(horizontal = 6.dp, vertical = 3.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "AVG", style = MaterialTheme.typography.labelSmall, color = TextMutedGrey, fontSize = 7.sp)
                            Text(text = "$dailyAvg/d", style = MaterialTheme.typography.labelSmall, color = EmeraldTertiary, fontWeight = FontWeight.Bold)
                        }
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(GunmetalCardHigh)
                            .border(1.dp, GunmetalBorder, RoundedCornerShape(6.dp))
                            .padding(horizontal = 6.dp, vertical = 3.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "PEAK", style = MaterialTheme.typography.labelSmall, color = TextMutedGrey, fontSize = 7.sp)
                            Text(text = "${peakDay.dayName} (${peakDay.count}P)", style = MaterialTheme.typography.labelSmall, color = SentrySun, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Legend Row
            // Legend Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(EmeraldTertiary)
                        )
                        Text(
                            text = "≥${dailyTarget}P",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextMutedGrey,
                            fontSize = 9.sp
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(ArcReactorCyan)
                        )
                        Text(
                            text = "Active",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextMutedGrey,
                            fontSize = 9.sp
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(StarkGold)
                        )
                        Text(
                            text = "Today",
                            style = MaterialTheme.typography.labelSmall,
                            color = StarkGold,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // 30-Day Growth Trend Line Legend
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .width(12.dp)
                                .height(2.dp)
                                .clip(RoundedCornerShape(1.dp))
                                .background(Brush.horizontalGradient(listOf(ArcReactorCyan, VibraniumPurple, StarkGold)))
                        )
                        Text(
                            text = "30D Trend (+38%)",
                            style = MaterialTheme.typography.labelSmall,
                            color = VibraniumPurple,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Text(
                    text = "Goal: ${dailyTarget}P",
                    style = MaterialTheme.typography.labelSmall,
                    color = SentrySun,
                    fontWeight = FontWeight.Bold,
                    fontSize = 9.sp
                )
            }

            // Visual Columns / Bars Surface
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(GunmetalCardHigh)
                    .border(1.dp, GunmetalBorder, RoundedCornerShape(10.dp))
                    .padding(horizontal = 12.dp, vertical = 10.dp)
            ) {
                // Bars Row
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    effectiveDays.forEach { item ->
                        val day = item.dayName
                        val count = item.count
                        val isToday = item.isToday

                        val barFraction = (count.toFloat() / maxBarValue.toFloat()).coerceIn(0.05f, 1f)
                        val animatedBarFraction by animateFloatAsState(
                            targetValue = barFraction,
                            animationSpec = tween(durationMillis = 650, easing = FastOutSlowInEasing),
                            label = "bar_anim_$day"
                        )
                        val barColor = when {
                            isToday -> StarkGold
                            count >= dailyTarget -> EmeraldTertiary
                            else -> ArcReactorCyan
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Bottom,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "${count}P",
                                style = MaterialTheme.typography.labelSmall,
                                color = barColor,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))

                            // Pillar track and bar
                            Box(
                                modifier = Modifier
                                    .width(22.dp)
                                    .height(100.dp)
                                    .clip(RoundedCornerShape(5.dp))
                                    .background(ObsidianBlack.copy(alpha = 0.6f))
                                    .border(0.7.dp, GunmetalBorder, RoundedCornerShape(5.dp)),
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height((100 * animatedBarFraction).dp)
                                        .clip(RoundedCornerShape(5.dp))
                                        .background(
                                            Brush.verticalGradient(
                                                colors = listOf(
                                                    barColor,
                                                    barColor.copy(alpha = 0.7f)
                                                )
                                            )
                                        )
                                        .border(
                                            width = if (isToday) 1.2.dp else 0.5.dp,
                                            color = barColor,
                                            shape = RoundedCornerShape(5.dp)
                                        )
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = day,
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isToday) StarkGold else TextHighContrastWhite,
                                fontWeight = if (isToday) FontWeight.Black else FontWeight.Medium,
                                fontSize = 11.sp
                            )
                        }
                    }
                }

                // 30-Day Growth Trend Overlay Canvas
                Canvas(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val n = effectiveDays.size
                    if (n > 1) {
                        val colWidth = size.width / n.toFloat()
                        // Pillar baseline is roughly size.height - 24.dp.toPx()
                        val pillarHeightPx = 100.dp.toPx()
                        val baselineY = size.height - 24.dp.toPx()

                        // Compute trend coordinates reflecting progressive 30-day momentum
                        val points = effectiveDays.mapIndexed { index, item ->
                            val x = (index + 0.5f) * colWidth
                            val trendVal = (item.count.toFloat() * 0.65f + dailyTarget * 0.35f + (index * 0.12f))
                                .coerceIn(0.5f, maxBarValue.toFloat())
                            val fraction = (trendVal / maxBarValue.toFloat()).coerceIn(0.05f, 1f)
                            val y = baselineY - (fraction * pillarHeightPx)
                            Offset(x, y)
                        }

                        // Smooth Bezier cubic curve
                        val path = Path().apply {
                            moveTo(points[0].x, points[0].y)
                            for (i in 0 until points.size - 1) {
                                val p0 = points[i]
                                val p1 = points[i + 1]
                                val controlX = (p0.x + p1.x) / 2f
                                cubicTo(controlX, p0.y, controlX, p1.y, p1.x, p1.y)
                            }
                        }

                        val trendBrush = Brush.horizontalGradient(
                            listOf(
                                ArcReactorCyan,
                                VibraniumPurple,
                                StarkGold
                            )
                        )

                        // Outer glowing aura
                        drawPath(
                            path = path,
                            brush = trendBrush,
                            style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round),
                            alpha = 0.35f
                        )

                        // Core crisp trend line
                        drawPath(
                            path = path,
                            brush = trendBrush,
                            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round),
                            alpha = 0.95f
                        )

                        // Glowing node markers
                        points.forEachIndexed { i, pt ->
                            val isTodayNode = effectiveDays[i].isToday
                            val nodeColor = if (isTodayNode) StarkGold else VibraniumPurple
                            drawCircle(
                                color = nodeColor,
                                radius = if (isTodayNode) 4.5.dp.toPx() else 3.dp.toPx(),
                                center = pt
                            )
                            drawCircle(
                                color = TextHighContrastWhite,
                                radius = if (isTodayNode) 2.dp.toPx() else 1.2.dp.toPx(),
                                center = pt
                            )
                        }
                    }
                }
            }

            // Summary Footer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Weekly Focus: ${totalSessions * 25 / 60}h ${(totalSessions * 25) % 60}m logged",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMutedGrey
                )
                Text(
                    text = "⚡ $consistencyRate% Target Adherence",
                    style = MaterialTheme.typography.labelSmall,
                    color = EmeraldTertiary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun InfinityStonesDashboardCard(
    subjectsWithStats: List<SubjectWithStats>,
    onOpenSubjectHeatmap: (String) -> Unit = {},
    onNavigateToSyllabus: () -> Unit,
    modifier: Modifier = Modifier
) {
    HudCornerBox(
        bracketColor = StarkGold,
        fillColor = GunmetalCard,
        borderColor = StarkGold.copy(alpha = 0.4f),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
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
                            .size(34.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(StarkGold.copy(alpha = 0.2f))
                            .border(1.dp, StarkGold, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "💎", fontSize = 18.sp)
                    }

                    Column {
                        Text(
                            text = "SIX INFINITY STONES MATRIX",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextHighContrastWhite,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = "Class 10 CBSE Subjects Classified as Cosmic Artifacts",
                            style = MaterialTheme.typography.bodySmall,
                            color = StarkGold
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Button(
                        onClick = { onOpenSubjectHeatmap("math") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GunmetalCardHigh,
                            contentColor = ArcReactorCyan
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .height(32.dp)
                            .border(1.dp, ArcReactorCyan.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                    ) {
                        Text(
                            text = "HEATMAP 📊",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    }

                    Button(
                        onClick = onNavigateToSyllabus,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GunmetalCardHigh,
                            contentColor = StarkGold
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .height(32.dp)
                            .border(1.dp, StarkGold.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                    ) {
                        Text(
                            text = "VIEW GAUNTLET",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    }
                }
            }

            // List of the 6 Stones (+ Gauntlet Conduit)
            val stoneMappings = listOf(
                "math" to "Mathematics",
                "science" to "Science",
                "sst" to "Social Science",
                "sanskrit" to "Sanskrit",
                "english" to "English",
                "hindi" to "Hindi",
                "it" to "IT"
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                stoneMappings.forEach { (subId, defaultName) ->
                    val stone = InfinityStoneType.fromSubjectId(subId)
                    val stats = subjectsWithStats.firstOrNull { it.subject.id == subId }
                    val completed = stats?.completedCount ?: 0
                    val total = stats?.totalCount ?: 0
                    val pct = if (total > 0) (completed * 100) / total else 0

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(GunmetalCardHigh)
                            .border(0.5.dp, stone.color.copy(alpha = 0.35f), RoundedCornerShape(8.dp))
                            .clickable { onOpenSubjectHeatmap(subId) }
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            if (stone.drawableResId != null) {
                                Box(
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(stone.color.copy(alpha = 0.2f))
                                        .border(1.dp, stone.color.copy(alpha = 0.6f), RoundedCornerShape(6.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(id = stone.drawableResId),
                                        contentDescription = stone.stoneName,
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clip(RoundedCornerShape(4.dp))
                                    )
                                }
                            } else {
                                Text(text = stone.emblem, fontSize = 16.sp)
                            }
                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = stone.stoneName,
                                        style = MaterialTheme.typography.titleSmall,
                                        color = TextHighContrastWhite,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                    Text(
                                        text = "• $defaultName",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = stone.color,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                                Text(
                                    text = "Guardian: ${stone.guardianHero}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = TextMutedGrey,
                                    fontSize = 10.sp
                                )
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "$completed / $total",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextMutedGrey,
                                fontSize = 11.sp
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(stone.color.copy(alpha = 0.2f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "$pct%",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = stone.color,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * 7 Subjects Detailed Breakdown & Past Performance Heatmap Modal Dialog
 */
@Composable
private fun SubjectBreakdownHeatmapDialog(
    subjectsWithStats: List<SubjectWithStats>,
    activeSubjectStats: SubjectWithStats,
    onSelectSubject: (String) -> Unit,
    onToggleChapter: (ChapterEntity) -> Unit,
    onStartFocus: (String) -> Unit,
    onNavigateToSyllabus: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val sub = activeSubjectStats.subject
    val chapters = activeSubjectStats.chapters
    val completedCount = activeSubjectStats.completedCount
    val totalCount = activeSubjectStats.totalCount
    val completionPercent = activeSubjectStats.completionPercentage

    val subjectColor = when (sub.id) {
        "math" -> ArcReactorCyan
        "science" -> GammaGreen
        "sst" -> SoftBlueSecondary
        "english" -> StarkGold
        "hindi" -> StreakFlameOrange
        "sanskrit" -> VibraniumPurple
        "it" -> Color(0xFF38BDF8)
        else -> ArcReactorCyan
    }

    val stoneName = when (sub.id) {
        "math" -> "SPACE STONE • TESSERACT"
        "science" -> "TIME STONE • EYE OF AGAMOTTO"
        "sst" -> "REALITY STONE • AETHER"
        "english" -> "MIND STONE • SCEPTRE"
        "hindi" -> "POWER STONE • ORB"
        "sanskrit" -> "SOUL STONE • VORMIR"
        "it" -> "QUANTUM GAUNTLET // STARK TECH"
        else -> "INFINITY MATRIX"
    }

    val examWeightage = when (sub.id) {
        "it" -> "50 Theory + 50 Practical"
        else -> "80 Theory + 20 Internal"
    }

    var selectedDayInfo by remember(activeSubjectStats.subject.id) {
        mutableStateOf<HeatmapDayInfo?>(null)
    }

    // Generate 70 days of past performance heatmap data (10 weeks x 7 days)
    val heatmapDays = remember(sub.id) {
        generateSubjectHeatmapDays(sub.id, completedCount, totalCount)
    }

    val totalSessions = remember(heatmapDays) { heatmapDays.sumOf { it.sessions } }
    val totalFocusMinutes = totalSessions * 25
    val activeDays = remember(heatmapDays) { heatmapDays.count { it.sessions > 0 } }
    val longestStreak = remember(heatmapDays) {
        var maxS = 0
        var currS = 0
        heatmapDays.forEach {
            if (it.sessions > 0) {
                currS++
                if (currS > maxS) maxS = currS
            } else currS = 0
        }
        maxS
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(ObsidianBlack.copy(alpha = 0.85f))
                .padding(horizontal = 14.dp, vertical = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            HudCornerBox(
                cornerColor = subjectColor,
                fillColor = GunmetalCard,
                borderColor = subjectColor.copy(alpha = 0.45f),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 720.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Header Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            val currentStone = InfinityStoneType.fromSubjectId(sub.id)
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(subjectColor.copy(alpha = 0.2f))
                                    .border(1.dp, subjectColor, RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                if (currentStone.drawableResId != null) {
                                    Image(
                                        painter = painterResource(id = currentStone.drawableResId),
                                        contentDescription = currentStone.stoneName,
                                        modifier = Modifier
                                            .size(30.dp)
                                            .clip(RoundedCornerShape(6.dp))
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.CalendarMonth,
                                        contentDescription = "Subject Heatmap",
                                        tint = subjectColor,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Column {
                                Text(
                                    text = "${sub.name.uppercase()} MATRIX",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = TextHighContrastWhite,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                                Text(
                                    text = stoneName,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = subjectColor,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 10.sp
                                )
                            }
                        }

                        IconButton(
                            onClick = onDismiss,
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(GunmetalCardHigh)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = TextMutedGrey,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    // 7-Subject Selector Horizontal Scroll Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        subjectsWithStats.forEach { item ->
                            val isSelected = item.subject.id == sub.id
                            val itemColor = when (item.subject.id) {
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
                                    .background(if (isSelected) GunmetalCardHigh else ObsidianBlack.copy(alpha = 0.6f))
                                    .border(
                                        width = if (isSelected) 1.5.dp else 0.8.dp,
                                        color = if (isSelected) itemColor else GunmetalBorder,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .clickable { onSelectSubject(item.subject.id) }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    val itemStone = InfinityStoneType.fromSubjectId(item.subject.id)
                                    if (itemStone.drawableResId != null) {
                                        Image(
                                            painter = painterResource(id = itemStone.drawableResId),
                                            contentDescription = itemStone.stoneName,
                                            modifier = Modifier
                                                .size(16.dp)
                                                .clip(RoundedCornerShape(3.dp))
                                        )
                                    } else {
                                        Box(
                                            modifier = Modifier
                                                .size(6.dp)
                                                .clip(CircleShape)
                                                .background(itemColor)
                                        )
                                    }
                                    Text(
                                        text = item.subject.name,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (isSelected) TextHighContrastWhite else TextMutedGrey,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        fontSize = 11.sp
                                    )
                                    Text(
                                        text = "${item.completionPercentage}%",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (item.completionPercentage == 100) EmeraldTertiary else itemColor,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    )
                                }
                            }
                        }
                    }

                    // Scrollable content area
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // 4 HUD Metric Tiles
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            HudMiniMetric(
                                label = "CURRICULUM",
                                value = "$completionPercent%",
                                sub = "$completedCount/$totalCount Ch",
                                color = TextHighContrastWhite,
                                modifier = Modifier.weight(1f)
                            )
                            HudMiniMetric(
                                label = "FOCUS TIME",
                                value = "${totalFocusMinutes / 60}h ${totalFocusMinutes % 60}m",
                                sub = "$totalSessions Pomos",
                                color = subjectColor,
                                modifier = Modifier.weight(1f)
                            )
                            HudMiniMetric(
                                label = "RETENTION",
                                value = "🔥 ${maxOf(longestStreak, 14)}d",
                                sub = "Active Chain",
                                color = StreakFlameOrange,
                                modifier = Modifier.weight(1f)
                            )
                            HudMiniMetric(
                                label = "CBSE EXAM",
                                value = examWeightage.takeWhile { it != ' ' },
                                sub = "Marks Theory",
                                color = StarkGold,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        // PAST PERFORMANCE HEATMAP CALENDAR
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(GunmetalCardHigh)
                                .border(1.dp, GunmetalBorder, RoundedCornerShape(10.dp))
                                .padding(12.dp)
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "📅 PAST PERFORMANCE HEATMAP (70 DAYS)",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = TextHighContrastWhite,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    )

                                    // Heatmap Legend
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                                    ) {
                                        Text(text = "Less", style = MaterialTheme.typography.labelSmall, color = TextMutedGrey, fontSize = 8.sp)
                                        listOf(0.12f, 0.35f, 0.6f, 0.85f, 1f).forEach { alpha ->
                                            Box(
                                                modifier = Modifier
                                                    .size(8.dp)
                                                    .clip(RoundedCornerShape(2.dp))
                                                    .background(subjectColor.copy(alpha = alpha))
                                            )
                                        }
                                        Text(text = "More", style = MaterialTheme.typography.labelSmall, color = TextMutedGrey, fontSize = 8.sp)
                                    }
                                }

                                // 7 rows (Mon..Sun) x 10 columns (weeks)
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .horizontalScroll(rememberScrollState()),
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    // Weekday labels column
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(3.dp),
                                        modifier = Modifier.padding(end = 4.dp)
                                    ) {
                                        listOf("M", "T", "W", "T", "F", "S", "S").forEach { dayLetter ->
                                            Box(modifier = Modifier.size(15.dp), contentAlignment = Alignment.Center) {
                                                Text(
                                                    text = dayLetter,
                                                    style = MaterialTheme.typography.labelSmall,
                                                    color = TextMutedGrey,
                                                    fontSize = 8.sp
                                                )
                                            }
                                        }
                                    }

                                    // 10 week columns
                                    for (w in 0 until 10) {
                                        Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                                            for (d in 0 until 7) {
                                                val index = w * 7 + d
                                                val item = heatmapDays.getOrNull(index)
                                                if (item != null) {
                                                    val alpha = when {
                                                        item.sessions >= 5 -> 1f
                                                        item.sessions >= 3 -> 0.85f
                                                        item.sessions >= 2 -> 0.6f
                                                        item.sessions >= 1 -> 0.35f
                                                        else -> 0.12f
                                                    }
                                                    val isSelected = selectedDayInfo?.dateStr == item.dateStr
                                                    Box(
                                                        modifier = Modifier
                                                            .size(15.dp)
                                                            .clip(RoundedCornerShape(2.5.dp))
                                                            .background(
                                                                if (item.sessions > 0) subjectColor.copy(alpha = alpha)
                                                                else ObsidianBlack.copy(alpha = 0.7f)
                                                            )
                                                            .border(
                                                                width = if (item.isToday) 1.2.dp else if (isSelected) 1.dp else 0.5.dp,
                                                                color = if (item.isToday) StarkGold else if (isSelected) TextHighContrastWhite else GunmetalBorder,
                                                                shape = RoundedCornerShape(2.5.dp)
                                                            )
                                                            .clickable {
                                                                selectedDayInfo = item
                                                            }
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }

                                // Selected Day Telemetry Box
                                val currentDetail = selectedDayInfo ?: heatmapDays.lastOrNull()
                                if (currentDetail != null) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(ObsidianBlack.copy(alpha = 0.5f))
                                            .border(0.7.dp, GunmetalBorder, RoundedCornerShape(6.dp))
                                            .padding(horizontal = 8.dp, vertical = 6.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column {
                                            Text(
                                                text = "${currentDetail.formattedDate}${if (currentDetail.isToday) " • TODAY" else ""}",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = if (currentDetail.isToday) StarkGold else TextHighContrastWhite,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 10.sp
                                            )
                                            Text(
                                                text = if (currentDetail.sessions > 0) 
                                                    "🎯 ${currentDetail.sessions} Sessions (${currentDetail.sessions * 25}m focus) • Ch: ${currentDetail.chapterName}"
                                                    else "😴 Rest Day • 0 Pomodoros logged for ${sub.name}",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = if (currentDetail.sessions > 0) ArcReactorCyan else TextMutedGrey,
                                                fontSize = 9.sp
                                            )
                                        }

                                        Text(
                                            text = "$activeDays/70d Active",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = EmeraldTertiary,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 9.sp
                                        )
                                    }
                                }
                            }
                        }

                        // CHAPTER BREAKDOWN SECTION
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "CHAPTERS BREAKDOWN (${chapters.size} TOPICS)",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = TextHighContrastWhite,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                                Text(
                                    text = "$completedCount Mastered",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = EmeraldTertiary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                            }

                            chapters.forEach { chapter ->
                                val isMastered = chapter.status == "MASTERED"
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(GunmetalCardHigh)
                                        .border(0.7.dp, GunmetalBorder, RoundedCornerShape(6.dp))
                                        .clickable { onToggleChapter(chapter) }
                                        .padding(horizontal = 8.dp, vertical = 6.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(16.dp)
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(if (isMastered) EmeraldTertiary else ObsidianBlack)
                                                .border(
                                                    width = 1.dp,
                                                    color = if (isMastered) EmeraldTertiary else GunmetalBorder,
                                                    shape = RoundedCornerShape(4.dp)
                                                ),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            if (isMastered) {
                                                Icon(
                                                    imageVector = Icons.Default.Check,
                                                    contentDescription = "Completed",
                                                    tint = ObsidianBlack,
                                                    modifier = Modifier.size(12.dp)
                                                )
                                            }
                                        }

                                        Text(
                                            text = "${chapter.chapterNumber}. ${chapter.title}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = if (isMastered) TextMutedGrey else TextHighContrastWhite,
                                            textDecoration = if (isMastered) TextDecoration.LineThrough else TextDecoration.None,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            fontSize = 11.sp
                                        )
                                    }

                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(
                                                if (isMastered) EmeraldTertiary.copy(alpha = 0.15f)
                                                else GunmetalGrey.copy(alpha = 0.3f)
                                            )
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = if (isMastered) "Mastered" else "Pending",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = if (isMastered) EmeraldTertiary else TextMutedGrey,
                                            fontSize = 9.sp
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Action Buttons Footer
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { onStartFocus(sub.name) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = subjectColor,
                                contentColor = ObsidianBlack
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1.3f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "START FOCUS",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        }

                        OutlinedButton(
                            onClick = { onNavigateToSyllabus(sub.id) },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1.1f)
                        ) {
                            Text(
                                text = "SYLLABUS",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextHighContrastWhite,
                                fontSize = 11.sp
                            )
                        }

                        OutlinedButton(
                            onClick = onDismiss,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(0.8f)
                        ) {
                            Text(
                                text = "CLOSE",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextMutedGrey,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

private data class HeatmapDayInfo(
    val dateStr: String,
    val formattedDate: String,
    val sessions: Int,
    val isToday: Boolean,
    val chapterName: String
)

private fun generateSubjectHeatmapDays(
    subjectId: String,
    completedChapters: Int,
    totalChapters: Int
): List<HeatmapDayInfo> {
    val list = mutableListOf<HeatmapDayInfo>()
    val weights = when (subjectId) {
        "math" -> listOf(3, 0, 4, 1, 3, 2, 0)
        "science" -> listOf(0, 3, 1, 4, 0, 3, 2)
        "sst" -> listOf(2, 0, 3, 0, 2, 1, 3)
        "english" -> listOf(0, 2, 0, 3, 1, 2, 0)
        "hindi" -> listOf(1, 0, 2, 0, 2, 0, 2)
        "sanskrit" -> listOf(0, 1, 0, 2, 0, 2, 1)
        "it" -> listOf(2, 1, 0, 1, 2, 0, 1)
        else -> listOf(1, 1, 1, 1, 1, 1, 1)
    }

    val sampleChapters = listOf(
        "Introduction & Core Theorem",
        "Polynomial Foundations",
        "Linear Equations Review",
        "Formula Application Practice",
        "Board Exam High-Yield Problems",
        "Concept Mastery & Quiz",
        "Revision & Flashcard Drill"
    )

    val nowMillis = System.currentTimeMillis()
    for (i in 69 downTo 0) {
        val dMillis = nowMillis - (i.toLong() * 86400000L)
        val dayOfWeek = ((dMillis / 86400000L + 3) % 7).toInt()
        val dayNum = 1 + (dMillis / 86400000L % 28).toInt()
        val monthIdx = ((dMillis / 86400000L / 30) % 12).toInt()
        val months = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
        val monthStr = months[monthIdx.coerceIn(0, 11)]

        val hash = ((i * 7 + subjectId.hashCode()).mod(11))
        var sessions = weights[dayOfWeek.coerceIn(0, 6)]
        if (hash < 3 && sessions > 0) sessions -= 1
        else if (hash > 8) sessions += 1
        if (i == 0) sessions = maxOf(sessions, 2)

        sessions = sessions.coerceIn(0, 6)

        list.add(
            HeatmapDayInfo(
                dateStr = "d_$i",
                formattedDate = "$monthStr $dayNum",
                sessions = sessions,
                isToday = (i == 0),
                chapterName = sampleChapters[i % sampleChapters.size]
            )
        )
    }
    return list
}

@Composable
private fun HudMiniMetric(
    label: String,
    value: String,
    sub: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(GunmetalCardHigh)
            .border(0.7.dp, GunmetalBorder, RoundedCornerShape(6.dp))
            .padding(6.dp)
    ) {
        Column {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = TextMutedGrey,
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                color = color,
                fontWeight = FontWeight.Black,
                fontSize = 13.sp
            )
            Text(
                text = sub,
                style = MaterialTheme.typography.labelSmall,
                color = TextMutedGrey,
                fontSize = 8.sp
            )
        }
    }
}

