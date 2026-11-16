package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Headphones
import androidx.compose.material.icons.filled.MoreTime
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Nature
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.ScreenLockPortrait
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.Waves
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.HudCornerBox
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
import com.example.ui.theme.SoftBlueSecondary
import com.example.ui.theme.StarkGold
import com.example.ui.theme.StreakFlameOrange
import com.example.ui.theme.TextHighContrastWhite
import com.example.ui.theme.TextMutedGrey
import com.example.ui.theme.VibraniumPurple
import com.example.ui.viewmodel.AmbientSound
import com.example.ui.viewmodel.StudyFlowViewModel
import com.example.ui.viewmodel.TimerMode

@Composable
fun FocusTimerScreen(
    viewModel: StudyFlowViewModel,
    modifier: Modifier = Modifier
) {
    val timerState by viewModel.timerState.collectAsState()
    val activeAmbientSound by viewModel.activeAmbientSound.collectAsState()
    val ambientVolume by viewModel.ambientVolume.collectAsState()
    val isMoonKnightMode by viewModel.isMoonKnightMode.collectAsState()
    val moonKnightPersona by viewModel.moonKnightPersona.collectAsState()
    val pomodorosDoneToday by viewModel.pomodorosCompletedToday.collectAsState()
    val dailyPomodoroTarget by viewModel.dailyPomodoroTarget.collectAsState()
    val quickNotesText by viewModel.quickNotesText.collectAsState()
    val autoPlaySoundscapes by viewModel.autoPlaySoundscapes.collectAsState()

    // Screen-Wake Lock: Automatically keep mobile screen awake while Pomodoro focus timer is running
    val currentView = LocalView.current
    DisposableEffect(timerState.isRunning) {
        currentView.keepScreenOn = timerState.isRunning
        onDispose {
            currentView.keepScreenOn = false
        }
    }

    val progress = if (timerState.totalSeconds > 0) {
        (timerState.totalSeconds - timerState.remainingSeconds).toFloat() / timerState.totalSeconds
    } else 0f

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing),
        label = "timer_sweep"
    )

    val minutes = timerState.remainingSeconds / 60
    val seconds = timerState.remainingSeconds % 60
    val timeFormatted = String.format("%02d:%02d", minutes, seconds)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(2.dp))

            // Sentry (Robert Reynolds) Golden Guardian Header
            HudCornerBox(
                cornerColor = if (isMoonKnightMode) VibraniumPurple else SentrySun,
                fillColor = GunmetalCard,
                borderColor = if (isMoonKnightMode) VibraniumPurple.copy(alpha = 0.4f) else SentrySun.copy(alpha = 0.4f)
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
                                    .background((if (isMoonKnightMode) VibraniumPurple else SentrySun).copy(alpha = 0.25f))
                                    .border(1.dp, if (isMoonKnightMode) VibraniumPurple else SentrySun, RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (isMoonKnightMode) "🌙" else "☀️",
                                    fontSize = 16.sp
                                )
                            }
                            Column {
                                Text(
                                    text = if (isMoonKnightMode) "MOON KNIGHT NOCTURNAL FOCUS" else "SENTRY SOLAR FOCUS CORE",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = TextHighContrastWhite,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp
                                )
                                Text(
                                    text = if (isMoonKnightMode) "Khonshu's Vigilance (+25% Focus Retention)" else "Power of 1 Million Exploding Suns",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (isMoonKnightMode) VibraniumPurple else SentrySun
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(GunmetalCardHigh)
                                .border(1.dp, (if (isMoonKnightMode) VibraniumPurple else SentrySun).copy(alpha = 0.5f), RoundedCornerShape(4.dp))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = if (timerState.isRunning) "BURNING HOT" else "STANDBY",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (timerState.isRunning) SentrySun else TextMutedGrey,
                                fontWeight = FontWeight.Bold,
                                fontSize = 9.sp
                            )
                        }
                    }
                }
            }
        }

        // Timer Mode Selectors
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TimerMode.entries.forEach { mode ->
                    val isSelected = timerState.mode == mode
                    val icon: ImageVector = when (mode) {
                        TimerMode.POMODORO -> Icons.Filled.Timer
                        TimerMode.SHORT_BREAK -> Icons.Filled.Coffee
                        TimerMode.LONG_BREAK -> Icons.Filled.Bedtime
                        TimerMode.DEEP_FLOW -> Icons.Filled.Bolt
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) SentrySun.copy(alpha = 0.25f) else GunmetalCard)
                            .border(
                                1.dp,
                                if (isSelected) SentrySun else GunmetalBorder,
                                RoundedCornerShape(8.dp)
                            )
                            .clickable { viewModel.setTimerMode(mode) }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                tint = if (isSelected) SentrySun else TextMutedGrey,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = mode.title,
                                style = MaterialTheme.typography.labelMedium,
                                color = if (isSelected) SentrySun else TextMutedGrey,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }

        // Linked Chapter Focus Card
        item {
            HudCornerBox(
                cornerColor = ArcReactorCyan,
                fillColor = GunmetalCard,
                borderColor = GunmetalBorder
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
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(ArcReactorCyan.copy(alpha = 0.2f))
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = timerState.linkedSubject,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = ArcReactorCyan,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = timerState.linkedChapter,
                                style = MaterialTheme.typography.titleMedium,
                                color = TextHighContrastWhite,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(GunmetalCardHigh)
                                .border(1.dp, GunmetalBorder, RoundedCornerShape(6.dp))
                                .clickable { viewModel.showToast("Linked to Chapter 8 Trigonometry") },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.SwapHoriz,
                                contentDescription = "Switch chapter",
                                tint = ArcReactorCyan,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Target: 2 Solar Cycles (50m total)",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextMutedGrey
                        )
                        Text(
                            text = "Cycle ${timerState.currentSession} of ${timerState.totalSessions}",
                            style = MaterialTheme.typography.labelSmall,
                            color = EmeraldTertiary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Central Countdown Timer Dial (Sentry Solar Flare Ring)
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                // Background aura glow
                Box(
                    modifier = Modifier
                        .size(240.dp)
                        .clip(CircleShape)
                        .background((if (isMoonKnightMode) VibraniumPurple else SentrySun).copy(alpha = 0.06f))
                )

                // Timer Canvas Ring
                Canvas(modifier = Modifier.size(220.dp)) {
                    val strokeWidth = 14.dp.toPx()
                    val diameter = size.minDimension - strokeWidth
                    val topLeft = Offset(strokeWidth / 2, strokeWidth / 2)
                    val arcSize = Size(diameter, diameter)

                    // Track
                    drawArc(
                        color = GunmetalCardHigh,
                        startAngle = -90f,
                        sweepAngle = 360f,
                        useCenter = false,
                        topLeft = topLeft,
                        size = arcSize,
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )

                    // Progress Arc (Sentry Sun Gold / Arc Cyan)
                    drawArc(
                        color = if (isMoonKnightMode) VibraniumPurple else SentrySun,
                        startAngle = -90f,
                        sweepAngle = animatedProgress * 360f,
                        useCenter = false,
                        topLeft = topLeft,
                        size = arcSize,
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )
                }

                // Inner Time Display
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = timeFormatted,
                        style = MaterialTheme.typography.displayLarge,
                        color = TextHighContrastWhite,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )

                    Text(
                        text = if (timerState.isRunning) "SOLAR FLARE ACTIVE" else "TAP PLAY TO IGNITE",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isMoonKnightMode) VibraniumPurple else SentrySun,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }
            }
        }

        // Void Overload Warning Indicator (>90m study time warning)
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(GunmetalCard)
                    .border(1.dp, BerserkerCrimson.copy(alpha = 0.35f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = null,
                    tint = BerserkerCrimson,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "VOID OVERLOAD SAFEGUARD: Energy threshold stable. Max 90m continuous study before mandatory Bob Reynolds breather.",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMutedGrey,
                    fontSize = 11.sp
                )
            }
        }

        // Screen-Wake Lock & Display Power Telemetry Indicator
        item {
            HudCornerBox(
                cornerColor = if (timerState.isRunning) EmeraldTertiary else GunmetalBorder,
                fillColor = GunmetalCard,
                borderColor = if (timerState.isRunning) EmeraldTertiary.copy(alpha = 0.5f) else GunmetalBorder,
                modifier = Modifier.testTag("screen_wake_lock_indicator")
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(if (timerState.isRunning) EmeraldTertiary.copy(alpha = 0.2f) else GunmetalCardHigh)
                                .border(1.dp, if (timerState.isRunning) EmeraldTertiary else GunmetalBorder, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (timerState.isRunning) Icons.Filled.ScreenLockPortrait else Icons.Filled.Bedtime,
                                contentDescription = null,
                                tint = if (timerState.isRunning) EmeraldTertiary else TextMutedGrey,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                        Column {
                            Text(
                                text = if (timerState.isRunning) "SCREEN-WAKE LOCK: ACTIVE" else "SCREEN-WAKE LOCK: STANDBY",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (timerState.isRunning) EmeraldTertiary else TextHighContrastWhite,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = if (timerState.isRunning)
                                    "Display will stay awake during this focus session"
                                else
                                    "Display will follow standard device sleep timeout",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextMutedGrey,
                                fontSize = 9.sp
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(if (timerState.isRunning) EmeraldTertiary.copy(alpha = 0.15f) else GunmetalCardHigh)
                            .border(1.dp, if (timerState.isRunning) EmeraldTertiary.copy(alpha = 0.6f) else GunmetalBorder, RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = if (timerState.isRunning) "AWAKE ⚡" else "IDLE 💤",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (timerState.isRunning) EmeraldTertiary else TextMutedGrey,
                            fontWeight = FontWeight.Bold,
                            fontSize = 9.sp
                        )
                    }
                }
            }
        }

        // Primary Control Buttons (Play/Pause, Reset, -5m, +5m)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Play / Pause Button
                Button(
                    onClick = { viewModel.toggleTimer() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isMoonKnightMode) VibraniumPurple else SentrySun,
                        contentColor = ObsidianBlack
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .weight(1.3f)
                        .height(52.dp)
                        .testTag("toggle_timer_btn")
                ) {
                    Icon(
                        imageVector = if (timerState.isRunning) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                        contentDescription = null,
                        tint = ObsidianBlack,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = if (timerState.isRunning) "PAUSE" else "IGNITE",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                // Reset Button
                Button(
                    onClick = { viewModel.resetTimer() },
                    colors = ButtonDefaults.buttonColors(containerColor = GunmetalCard),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .size(52.dp)
                        .border(1.dp, GunmetalBorder, RoundedCornerShape(10.dp))
                        .testTag("reset_timer_btn")
                ) {
                    Icon(
                        imageVector = Icons.Filled.RestartAlt,
                        contentDescription = "Reset",
                        tint = TextHighContrastWhite,
                        modifier = Modifier.size(22.dp)
                    )
                }

                // Decrease -5m Button
                Button(
                    onClick = { viewModel.decreaseFiveMinutes() },
                    colors = ButtonDefaults.buttonColors(containerColor = GunmetalCard),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .weight(0.85f)
                        .height(52.dp)
                        .border(1.dp, BerserkerCrimson.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                        .testTag("decrease_time_btn")
                ) {
                    Text(
                        text = "-5m",
                        style = MaterialTheme.typography.titleMedium,
                        color = BerserkerCrimson,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Increase +5m Button
                Button(
                    onClick = { viewModel.addFiveMinutes() },
                    colors = ButtonDefaults.buttonColors(containerColor = GunmetalCard),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .weight(0.85f)
                        .height(52.dp)
                        .border(1.dp, ArcReactorCyan.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                        .testTag("add_time_btn")
                ) {
                    Text(
                        text = "+5m",
                        style = MaterialTheme.typography.titleMedium,
                        color = ArcReactorCyan,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Quick Notes Concept Jotter (auto-saved to Saved Memory section at session end)
        item {
            FocusQuickNotesCard(
                notes = quickNotesText,
                onNotesChange = { viewModel.updateQuickNotes(it) },
                onSaveNow = {
                    viewModel.saveCurrentQuickNotes(
                        subjectName = timerState.linkedSubject.ifBlank { "Mathematics" },
                        chapterTitle = timerState.linkedChapter.ifBlank { "Introduction to Trigonometry" }
                    )
                }
            )
        }

        // Visual Daily Goal Progress Indicator (Dynamically updates when session finishes)
        item {
            FocusDailyGoalProgressCard(
                completedCount = pomodorosDoneToday,
                targetCount = dailyPomodoroTarget,
                onSetTarget = { viewModel.setDailyPomodoroTarget(it) },
                onIncrement = { viewModel.incrementPomodoroCount() },
                onDecrement = { viewModel.decrementPomodoroCount() }
            )
        }

        // Moon Knight (Marc Spector) Late-Night Focus & Night Study Mode
        item {
            HudCornerBox(
                cornerColor = VibraniumPurple,
                fillColor = GunmetalCard,
                borderColor = if (isMoonKnightMode) VibraniumPurple.copy(alpha = 0.5f) else GunmetalBorder
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
                            Text(text = "🌙", fontSize = 18.sp)
                            Column {
                                Text(
                                    text = "MOON KNIGHT NIGHT STUDY MODE",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = TextHighContrastWhite,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp
                                )
                                Text(
                                    text = "Active Persona: $moonKnightPersona",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = VibraniumPurple
                                )
                            }
                        }

                        Switch(
                            checked = isMoonKnightMode,
                            onCheckedChange = { viewModel.toggleMoonKnightMode() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = VibraniumPurple,
                                checkedTrackColor = VibraniumPurple.copy(alpha = 0.3f),
                                uncheckedThumbColor = TextMutedGrey,
                                uncheckedTrackColor = GunmetalCardHigh
                            )
                        )
                    }

                    if (isMoonKnightMode) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listOf(
                                "Marc (Tactical Strategy)",
                                "Steven (Historical Recall)",
                                "Jake (Unrelenting Focus)"
                            ).forEach { persona ->
                                val isSelected = moonKnightPersona == persona
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(if (isSelected) VibraniumPurple.copy(alpha = 0.25f) else GunmetalCardHigh)
                                        .border(
                                            1.dp,
                                            if (isSelected) VibraniumPurple else GunmetalBorder,
                                            RoundedCornerShape(6.dp)
                                        )
                                        .clickable { viewModel.setMoonKnightPersona(persona) }
                                        .padding(horizontal = 10.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = persona,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (isSelected) VibraniumPurple else TextMutedGrey,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Focus Soundscapes & Ambient Audio Controls
        item {
            HudCornerBox(
                cornerColor = ArcReactorCyan,
                fillColor = GunmetalCard,
                borderColor = GunmetalBorder
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Header Row with Title & Active Status
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
                                    .size(28.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(ArcReactorCyan.copy(alpha = 0.15f))
                                    .border(1.dp, ArcReactorCyan.copy(alpha = 0.4f), RoundedCornerShape(6.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Headphones,
                                    contentDescription = null,
                                    tint = ArcReactorCyan,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = "FOCUS SOUNDSCAPES",
                                    style = MaterialTheme.typography.titleSmall,
                                    color = TextHighContrastWhite,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp
                                )
                                Text(
                                    text = "Procedural audio to mask external distractions",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = TextMutedGrey,
                                    fontSize = 11.sp
                                )
                            }
                        }

                        // Status Badge with Animated Visualizer Bars when active
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(if (activeAmbientSound != null) ArcReactorCyan.copy(alpha = 0.15f) else GunmetalCardHigh)
                                .border(
                                    1.dp,
                                    if (activeAmbientSound != null) ArcReactorCyan.copy(alpha = 0.5f) else GunmetalBorder,
                                    RoundedCornerShape(20.dp)
                                )
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            if (activeAmbientSound != null) {
                                // Mini visualizer bars
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                                    verticalAlignment = Alignment.Bottom,
                                    modifier = Modifier.height(10.dp)
                                ) {
                                    Box(modifier = Modifier.width(2.dp).height(8.dp).background(ArcReactorCyan, RoundedCornerShape(1.dp)))
                                    Box(modifier = Modifier.width(2.dp).height(10.dp).background(ArcReactorCyan, RoundedCornerShape(1.dp)))
                                    Box(modifier = Modifier.width(2.dp).height(6.dp).background(ArcReactorCyan, RoundedCornerShape(1.dp)))
                                }
                                Text(
                                    text = activeAmbientSound?.title?.uppercase() ?: "PLAYING",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = ArcReactorCyan,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                            } else {
                                Text(
                                    text = "MUTED",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = TextMutedGrey,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }

                    // Soundscape Selection Chips (Horizontal Carousel)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AmbientSound.entries.forEach { sound ->
                            val isSelected = activeAmbientSound == sound
                            val soundIcon: ImageVector = when (sound) {
                                AmbientSound.RAIN -> Icons.Filled.WaterDrop
                                AmbientSound.WHITE_NOISE -> Icons.Filled.Air
                                AmbientSound.LO_FI_BEATS -> Icons.Filled.Headphones
                                AmbientSound.BINAURAL -> Icons.Filled.GraphicEq
                                AmbientSound.BROWN_NOISE -> Icons.Filled.Waves
                            }

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isSelected) ArcReactorCyan.copy(alpha = 0.2f) else GunmetalCardHigh)
                                    .border(
                                        1.dp,
                                        if (isSelected) ArcReactorCyan else GunmetalBorder,
                                        RoundedCornerShape(10.dp)
                                    )
                                    .clickable { viewModel.toggleAmbientSound(sound) }
                                    .padding(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        imageVector = soundIcon,
                                        contentDescription = null,
                                        tint = if (isSelected) ArcReactorCyan else TextMutedGrey,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Column {
                                        Text(
                                            text = sound.title,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = if (isSelected) ArcReactorCyan else TextHighContrastWhite,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                        )
                                        Text(
                                            text = sound.subtitle,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = TextMutedGrey,
                                            fontSize = 9.sp
                                        )
                                    }
                                }
                            }
                        }

                        // Mute / Off Chip
                        val isMuted = activeAmbientSound == null
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isMuted) BerserkerCrimson.copy(alpha = 0.15f) else GunmetalCardHigh)
                                .border(
                                    1.dp,
                                    if (isMuted) BerserkerCrimson.copy(alpha = 0.6f) else GunmetalBorder,
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable { viewModel.setAmbientSound(null) }
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.VolumeOff,
                                    contentDescription = null,
                                    tint = if (isMuted) BerserkerCrimson else TextMutedGrey,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "Mute",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (isMuted) BerserkerCrimson else TextMutedGrey,
                                    fontWeight = if (isMuted) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }

                    // Volume Slider Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(GunmetalCardHigh, RoundedCornerShape(8.dp))
                            .border(1.dp, GunmetalBorder, RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = if (ambientVolume > 0f && activeAmbientSound != null) Icons.Filled.VolumeUp else Icons.Filled.VolumeOff,
                            contentDescription = "Volume",
                            tint = if (activeAmbientSound != null) ArcReactorCyan else TextMutedGrey,
                            modifier = Modifier.size(18.dp)
                        )
                        Slider(
                            value = ambientVolume,
                            onValueChange = { viewModel.setAmbientVolume(it) },
                            modifier = Modifier.weight(1f),
                            colors = SliderDefaults.colors(
                                thumbColor = ArcReactorCyan,
                                activeTrackColor = ArcReactorCyan,
                                inactiveTrackColor = GunmetalBorder
                            )
                        )
                        Text(
                            text = "${(ambientVolume * 100).toInt()}%",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (activeAmbientSound != null) ArcReactorCyan else TextMutedGrey,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    }

                    // Auto-Play during Active Focus Toggle
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Auto-Play During Focus Rounds",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextHighContrastWhite,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "Streams soundscape automatically while timer runs",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextMutedGrey,
                                fontSize = 10.sp
                            )
                        }
                        Switch(
                            checked = autoPlaySoundscapes,
                            onCheckedChange = { viewModel.toggleAutoPlaySoundscapes() },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = ArcReactorCyan,
                                uncheckedThumbColor = TextMutedGrey,
                                uncheckedTrackColor = GunmetalCardHigh
                            )
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

/**
 * Visual Daily Goal Progress Indicator for Pomodoro completion
 * Updates dynamically with smooth animations when a session finishes.
 */
@Composable
private fun FocusDailyGoalProgressCard(
    completedCount: Int,
    targetCount: Int,
    onSetTarget: (Int) -> Unit,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    val progress = if (targetCount > 0) (completedCount.toFloat() / targetCount.toFloat()).coerceIn(0f, 1f) else 0f
    val percentage = if (targetCount > 0) ((completedCount * 100) / targetCount).coerceAtLeast(0) else 0
    val isGoalReached = completedCount >= targetCount

    val animatedGoalProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 650, easing = FastOutSlowInEasing),
        label = "timer_daily_goal_progress"
    )

    HudCornerBox(
        cornerColor = if (isGoalReached) EmeraldTertiary else SentrySun,
        fillColor = GunmetalCard,
        borderColor = if (isGoalReached) EmeraldTertiary.copy(alpha = 0.5f) else SentrySun.copy(alpha = 0.35f),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
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
                            .background((if (isGoalReached) EmeraldTertiary else SentrySun).copy(alpha = 0.2f))
                            .border(1.dp, if (isGoalReached) EmeraldTertiary else SentrySun, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = if (isGoalReached) "🏆" else "🎯", fontSize = 16.sp)
                    }
                    Column {
                        Text(
                            text = "DAILY POMODORO GOAL",
                            style = MaterialTheme.typography.titleSmall,
                            color = TextHighContrastWhite,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = if (isGoalReached) "Target Conquered! Solar Core Primed" else "Dynamic Real-Time Quota",
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
                            if (isGoalReached) EmeraldTertiary.copy(alpha = 0.6f) else SentrySun.copy(alpha = 0.4f),
                            RoundedCornerShape(6.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "$completedCount / $targetCount SESSIONS",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isGoalReached) EmeraldTertiary else SentrySun,
                        fontWeight = FontWeight.Bold,
                        fontSize = 9.sp
                    )
                }
            }

            // Visual Progress Ring & Diagnostics Area
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
                // Circular Progress Ring
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(76.dp)
                ) {
                    CircularProgressIndicator(
                        progress = { animatedGoalProgress },
                        modifier = Modifier.fillMaxSize(),
                        color = if (isGoalReached) EmeraldTertiary else SentrySun,
                        trackColor = GunmetalGrey,
                        strokeWidth = 7.dp,
                        strokeCap = StrokeCap.Round
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "$completedCount/$targetCount",
                            style = MaterialTheme.typography.titleSmall,
                            color = TextHighContrastWhite,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            text = "$percentage%",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isGoalReached) EmeraldTertiary else SentrySun,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    }
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = if (isGoalReached) "Daily Target Achieved! (+250 XP)" else "${(targetCount - completedCount).coerceAtLeast(0)} Pomodoros remaining today",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextHighContrastWhite,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Total Focus Time: ${completedCount * 25}m / ${targetCount * 25}m target",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMutedGrey
                    )

                    // Stepper + / -
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(GunmetalCard)
                                .border(1.dp, GunmetalBorder, RoundedCornerShape(6.dp))
                                .clickable { onDecrement() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Remove,
                                contentDescription = "Decrement",
                                tint = TextMutedGrey,
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        Text(
                            text = "$completedCount Done",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextHighContrastWhite,
                            fontWeight = FontWeight.Bold
                        )

                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(GunmetalCard)
                                .border(1.dp, GunmetalBorder, RoundedCornerShape(6.dp))
                                .clickable { onIncrement() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Increment",
                                tint = EmeraldTertiary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }

            // Target selector presets
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "TARGET:",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMutedGrey,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
                )
                listOf(2, 4, 6, 8, 10).forEach { preset ->
                    val isSelected = targetCount == preset
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (isSelected) SentrySun.copy(alpha = 0.25f) else GunmetalCardHigh)
                            .border(
                                1.dp,
                                if (isSelected) SentrySun else GunmetalBorder,
                                RoundedCornerShape(6.dp)
                            )
                            .clickable { onSetTarget(preset) }
                            .padding(vertical = 5.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${preset}P",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isSelected) SentrySun else TextMutedGrey,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FocusQuickNotesCard(
    notes: String,
    onNotesChange: (String) -> Unit,
    onSaveNow: () -> Unit,
    modifier: Modifier = Modifier
) {
    val wordCount = remember(notes) {
        if (notes.isBlank()) 0 else notes.trim().split(Regex("\\s+")).filter { it.isNotBlank() }.size
    }
    val charCount = notes.length

    HudCornerBox(
        cornerColor = DoomEmerald,
        fillColor = GunmetalCard,
        borderColor = DoomEmerald.copy(alpha = 0.4f),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
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
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(DoomEmerald.copy(alpha = 0.2f))
                            .border(1.dp, DoomEmerald.copy(alpha = 0.6f), RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.EditNote,
                            contentDescription = "Quick Notes",
                            tint = DoomEmerald,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Column {
                        Text(
                            text = "QUICK STUDY NOTES",
                            style = MaterialTheme.typography.titleSmall,
                            color = TextHighContrastWhite,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            letterSpacing = 0.8.sp
                        )
                        Text(
                            text = "JOT FORMULAS & CONCEPTS DURING FOCUS",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextMutedGrey,
                            fontSize = 10.sp
                        )
                    }
                }

                // Auto-save badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(DoomEmerald.copy(alpha = 0.15f))
                        .border(1.dp, DoomEmerald.copy(alpha = 0.4f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(DoomEmerald)
                        )
                        Text(
                            text = "AUTO-SAVES ON FINISH",
                            style = MaterialTheme.typography.labelSmall,
                            color = DoomEmerald,
                            fontWeight = FontWeight.Bold,
                            fontSize = 9.sp
                        )
                    }
                }
            }

            // Text input area
            OutlinedTextField(
                value = notes,
                onValueChange = onNotesChange,
                placeholder = {
                    Text(
                        text = "Jot down key formulas, definitions, mnemonics, or steps here while timer runs...\n(e.g., sin²θ + cos²θ = 1, Redox reaction balance)",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMutedGrey.copy(alpha = 0.7f),
                        fontSize = 12.sp
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("focus_quick_notes_input"),
                minLines = 3,
                maxLines = 6,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = TextHighContrastWhite,
                    unfocusedTextColor = TextHighContrastWhite,
                    focusedContainerColor = GunmetalCardHigh,
                    unfocusedContainerColor = GunmetalCardHigh,
                    cursorColor = DoomEmerald,
                    focusedBorderColor = DoomEmerald,
                    unfocusedBorderColor = GunmetalBorder
                ),
                shape = RoundedCornerShape(8.dp)
            )

            // Bottom Bar: Word Count & Manual Save Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$wordCount words · $charCount chars",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMutedGrey,
                    fontSize = 11.sp
                )

                Button(
                    onClick = onSaveNow,
                    enabled = notes.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = DoomEmerald,
                        disabledContainerColor = GunmetalCardHigh
                    ),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier
                        .height(34.dp)
                        .testTag("save_quick_notes_now_btn")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Bookmark,
                            contentDescription = "Save Note",
                            tint = if (notes.isNotBlank()) ObsidianBlack else TextMutedGrey,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "SAVE TO MEMORY",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (notes.isNotBlank()) ObsidianBlack else TextMutedGrey,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    }
}
