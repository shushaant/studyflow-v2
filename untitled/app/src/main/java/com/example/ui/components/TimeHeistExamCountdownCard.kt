package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.ui.res.painterResource
import com.example.data.model.InfinityStoneType
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CbseExamItem
import com.example.data.model.CbseExamSchedule
import com.example.data.model.DeadlineStatus
import com.example.ui.theme.ArcReactorCyan
import com.example.ui.theme.BerserkerCrimson
import com.example.ui.theme.DoomEmerald
import com.example.ui.theme.EmeraldTertiary
import com.example.ui.theme.GunmetalBorder
import com.example.ui.theme.GunmetalCard
import com.example.ui.theme.GunmetalCardHigh
import com.example.ui.theme.GunmetalGrey
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.SentrySun
import com.example.ui.theme.StarkGold
import com.example.ui.theme.StreakFlameOrange
import com.example.ui.theme.TextHighContrastWhite
import com.example.ui.theme.TextMutedGrey
import com.example.ui.theme.VibraniumPurple
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.util.Locale

@Composable
fun TimeHeistExamCountdownCard(
    deadlineStatus: DeadlineStatus,
    uncompletedChapters: Int,
    completedChapters: Int,
    totalChapters: Int,
    exams: List<CbseExamItem>,
    onSelectExamSubject: (String) -> Unit,
    onOpenFriday: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isScheduleExpanded by remember { mutableStateOf(false) }
    var currentNow by remember { mutableStateOf(LocalDateTime.now()) }

    // Live tick every second to keep the countdown ticking in real-time
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000L)
            currentNow = LocalDateTime.now()
        }
    }

    val liveDeadlineStatus = remember(currentNow, uncompletedChapters) {
        CbseExamSchedule.getDeadlineStatus(uncompletedChapters, currentNow)
    }

    val completionPercent = if (totalChapters > 0) (completedChapters * 100) / totalChapters else 0

    HudCornerBox(
        cornerColor = ArcReactorCyan,
        fillColor = GunmetalCard,
        borderColor = ArcReactorCyan.copy(alpha = 0.5f),
        modifier = modifier.testTag("time_heist_exam_countdown_card")
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Mission Header
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
                            .clip(CircleShape)
                            .background(ArcReactorCyan.copy(alpha = 0.15f))
                            .border(1.dp, ArcReactorCyan, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.HourglassTop,
                            contentDescription = "Time Heist",
                            tint = ArcReactorCyan,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(
                                text = "PROJECT: CONQUER ALL SUBJECTS",
                                style = MaterialTheme.typography.titleMedium,
                                color = TextHighContrastWhite,
                                fontWeight = FontWeight.Black,
                                letterSpacing = 0.5.sp
                            )
                        }
                        Text(
                            text = "THE TIME HEIST // TARGET: 95%+ MARKS",
                            style = MaterialTheme.typography.labelSmall,
                            color = StarkGold,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(GunmetalCardHigh)
                        .border(1.dp, StarkGold.copy(alpha = 0.6f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = "TARGET: 95%+",
                        style = MaterialTheme.typography.labelSmall,
                        color = StarkGold,
                        fontWeight = FontWeight.Black,
                        fontSize = 10.sp
                    )
                }
            }

            // Thematic Quote Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(ObsidianBlack.copy(alpha = 0.6f))
                    .border(0.5.dp, ArcReactorCyan.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "\"Whatever it takes. Every chapter, question bank, and PYQ conquered is securing an Infinity Stone to rewrite your final score destiny.\"",
                    style = MaterialTheme.typography.bodySmall,
                    color = ArcReactorCyan,
                    fontSize = 11.sp,
                    lineHeight = 15.sp
                )
            }

            // The Six Infinity Stones Vault Showcase
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(ObsidianBlack.copy(alpha = 0.8f))
                    .border(1.dp, StarkGold.copy(alpha = 0.35f), RoundedCornerShape(8.dp))
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "💎 INFINITY STONES SECURED FOR 95%+",
                        style = MaterialTheme.typography.labelSmall,
                        color = StarkGold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 9.sp,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "$completedChapters/$totalChapters Chapters",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMutedGrey,
                        fontSize = 9.sp
                    )
                }

                // Row of 6 Infinity Stones
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val allStones = listOf(
                        InfinityStoneType.MIND,
                        InfinityStoneType.POWER,
                        InfinityStoneType.SPACE,
                        InfinityStoneType.TIME,
                        InfinityStoneType.REALITY,
                        InfinityStoneType.SOUL
                    )

                    allStones.forEach { stone ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(stone.color.copy(alpha = 0.18f))
                                    .border(1.dp, stone.color.copy(alpha = 0.7f), RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                if (stone.drawableResId != null) {
                                    Image(
                                        painter = painterResource(id = stone.drawableResId),
                                        contentDescription = stone.stoneName,
                                        modifier = Modifier
                                            .size(30.dp)
                                            .clip(RoundedCornerShape(6.dp))
                                    )
                                } else {
                                    Text(text = stone.emblem, fontSize = 16.sp)
                                }
                            }
                            Text(
                                text = stone.stoneName.replace(" Stone", ""),
                                style = MaterialTheme.typography.labelSmall,
                                color = stone.color,
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Master Completion Deadline: January 15, 2027 Countdown
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(GunmetalCardHigh)
                    .border(1.dp, if (liveDeadlineStatus.isPacingBehind) BerserkerCrimson else ArcReactorCyan.copy(alpha = 0.6f), RoundedCornerShape(10.dp))
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(
                            imageVector = Icons.Filled.Flag,
                            contentDescription = null,
                            tint = if (liveDeadlineStatus.isPacingBehind) BerserkerCrimson else ArcReactorCyan,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "MASTER DEADLINE: JAN 15, 2027",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextHighContrastWhite,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    }
                    Text(
                        text = "100% SYLLABUS LOCK",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMutedGrey,
                        fontSize = 9.sp
                    )
                }

                // Digital Countdown Matrix
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CountdownDigitBlock(value = liveDeadlineStatus.daysRemaining.toString(), label = "DAYS")
                    Text(text = ":", color = ArcReactorCyan, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    CountdownDigitBlock(value = String.format(Locale.US, "%02d", liveDeadlineStatus.hoursRemaining), label = "HOURS")
                    Text(text = ":", color = ArcReactorCyan, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    CountdownDigitBlock(value = String.format(Locale.US, "%02d", liveDeadlineStatus.minutesRemaining), label = "MINS")
                    Text(text = ":", color = ArcReactorCyan, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    CountdownDigitBlock(value = String.format(Locale.US, "%02d", liveDeadlineStatus.secondsRemaining), label = "SECS")
                }

                // Stark Tactical Pacing & Warning Telemetry
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (liveDeadlineStatus.isPacingBehind) BerserkerCrimson.copy(alpha = 0.15f) else EmeraldTertiary.copy(alpha = 0.1f))
                        .border(0.5.dp, if (liveDeadlineStatus.isPacingBehind) BerserkerCrimson.copy(alpha = 0.5f) else EmeraldTertiary.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = if (liveDeadlineStatus.isPacingBehind) Icons.Filled.Warning else Icons.Filled.Speed,
                            contentDescription = null,
                            tint = if (liveDeadlineStatus.isPacingBehind) BerserkerCrimson else EmeraldTertiary,
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = if (liveDeadlineStatus.isPacingBehind)
                                "⚠️ STARK HUD WARNING: Required rate: ${String.format(Locale.US, "%.1f", liveDeadlineStatus.requiredDailyVelocity)} ch/day before Jan 15"
                            else
                                "✓ Pacing Nominal: ${String.format(Locale.US, "%.1f", liveDeadlineStatus.requiredDailyVelocity)} ch/day required ($uncompletedChapters remaining)",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (liveDeadlineStatus.isPacingBehind) BerserkerCrimson else EmeraldTertiary,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 10.sp
                        )
                    }

                    IconButton(
                        onClick = onOpenFriday,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Mic,
                            contentDescription = "Ask FRIDAY",
                            tint = ArcReactorCyan,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }

            // Official CBSE Board Exam Schedule Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(GunmetalCardHigh)
                    .border(1.dp, GunmetalBorder, RoundedCornerShape(10.dp))
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isScheduleExpanded = !isScheduleExpanded },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Icon(
                            imageVector = Icons.Filled.CalendarMonth,
                            contentDescription = null,
                            tint = SentrySun,
                            modifier = Modifier.size(16.dp)
                        )
                        Column {
                            Text(
                                text = "OFFICIAL CBSE EXAM DATES (FEB 17 – MAR 8, 2027)",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextHighContrastWhite,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "10:30 AM Shift • 7 Core Papers",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextMutedGrey,
                                fontSize = 9.sp
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (isScheduleExpanded) "COLLAPSE" else "VIEW ALL 7",
                            style = MaterialTheme.typography.labelSmall,
                            color = ArcReactorCyan,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                        Icon(
                            imageVector = if (isScheduleExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                            contentDescription = null,
                            tint = ArcReactorCyan,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                // Show first upcoming exam when collapsed, or all 7 when expanded
                val displayExams = if (isScheduleExpanded) exams else exams.take(2)

                displayExams.forEach { exam ->
                    val countdownStr = remember(currentNow, exam.targetDateTime) {
                        CbseExamSchedule.getExamCountdown(exam, currentNow)
                    }
                    val isFirstExam = exam.id == "cbse_math"

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(ObsidianBlack.copy(alpha = 0.6f))
                            .border(
                                1.dp,
                                if (isFirstExam) ArcReactorCyan.copy(alpha = 0.6f) else GunmetalBorder,
                                RoundedCornerShape(8.dp)
                            )
                            .clickable { onSelectExamSubject(exam.subjectId) }
                            .padding(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .background(ArcReactorCyan.copy(alpha = 0.2f))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = exam.examDateString,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = ArcReactorCyan,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 10.sp
                                        )
                                    }
                                    Text(
                                        text = "${exam.dayOfWeek} • ${exam.examTiming}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = TextMutedGrey,
                                        fontSize = 10.sp
                                    )
                                }

                                Text(
                                    text = "${exam.subjectName} (${exam.subjectCode})",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = TextHighContrastWhite,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(top = 2.dp)
                                )

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    modifier = Modifier.padding(top = 2.dp)
                                ) {
                                    val stoneType = InfinityStoneType.fromSubjectId(exam.subjectId)
                                    if (stoneType.drawableResId != null) {
                                        Image(
                                            painter = painterResource(id = stoneType.drawableResId),
                                            contentDescription = stoneType.stoneName,
                                            modifier = Modifier
                                                .size(16.dp)
                                                .clip(RoundedCornerShape(3.dp))
                                        )
                                    } else {
                                        Text(text = "💎", fontSize = 11.sp)
                                    }
                                    Text(
                                        text = exam.infinityStone,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = StarkGold,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(text = "•", color = TextMutedGrey)
                                    Text(
                                        text = exam.quantumReadinessHero,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = TextMutedGrey,
                                        fontSize = 10.sp
                                    )
                                }
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(if (isFirstExam) ArcReactorCyan.copy(alpha = 0.25f) else GunmetalCard)
                                        .border(1.dp, if (isFirstExam) ArcReactorCyan else GunmetalBorder, RoundedCornerShape(6.dp))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = countdownStr,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (isFirstExam) ArcReactorCyan else SentrySun,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Revise →",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = ArcReactorCyan,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CountdownDigitBlock(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(ObsidianBlack)
                .border(1.dp, ArcReactorCyan.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                .padding(horizontal = 8.dp, vertical = 6.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                color = ArcReactorCyan,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.sp
            )
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = TextMutedGrey,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}
