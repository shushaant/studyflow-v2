package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ArcReactorCyan
import com.example.ui.theme.ArcReactorGlow
import com.example.ui.theme.BerserkerCrimson
import com.example.ui.theme.CrimsonArc
import com.example.ui.theme.DeepMatteBackground
import com.example.ui.theme.DoomEmerald
import com.example.ui.theme.ElectricCyanPrimary
import com.example.ui.theme.EmeraldTertiary
import com.example.ui.theme.GammaGreen
import com.example.ui.theme.GunmetalBorder
import com.example.ui.theme.GunmetalBorderGlow
import com.example.ui.theme.GunmetalCard
import com.example.ui.theme.GunmetalCardHigh
import com.example.ui.theme.GunmetalGrey
import com.example.ui.theme.LocalThemeIsLight
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.SentrySun
import com.example.ui.theme.StarkGold
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerHighest
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.TextDarkGrey
import com.example.ui.theme.TextHighContrastWhite
import com.example.ui.theme.TextMutedGrey
import com.example.ui.theme.VibraniumPurple
import kotlin.math.cos
import kotlin.math.sin

enum class ScreenTab(val title: String, val heroAlias: String, val icon: ImageVector) {
    DASHBOARD("Stark HUD", "Iron Man", Icons.Outlined.Dashboard),
    SYLLABUS("Doom Matrix", "Dr. Doom & Hulk", Icons.Filled.MenuBook),
    FOCUS("Sentry Core", "Sentry & Moon Knight", Icons.Filled.Timer),
    MEMORY("Ultron Vault", "Ultron & Vision", Icons.Filled.Bookmark),
    AI_TUTOR("Wakanda AI", "Shuri & F.R.I.D.A.Y.", Icons.Filled.Psychology)
}

/**
 * Tactical MCU HUD Top Bar with Arc Reactor Pulse and Logan Streak
 */
@Composable
fun AppTopHeader(
    currentTab: ScreenTab,
    streakDays: Int = 14,
    onOpenSettings: () -> Unit = {},
    onToggleTheme: () -> Unit = {},
    onOpenFriday: () -> Unit = {},
    onTriggerDeadpool: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val isLight = LocalThemeIsLight.current
    val infiniteTransition = rememberInfiniteTransition(label = "arc_pulse")
    val pulseGlow by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseGlow"
    )

    Surface(
        color = if (isLight) Color.White.copy(alpha = 0.98f) else ObsidianBlack.copy(alpha = 0.95f),
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .border(width = 0.5.dp, color = if (isLight) Color(0xFFCBD5E1) else GunmetalBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(62.dp)
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left: Arc Reactor Emblem & Header Title
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isLight) Color(0xFFF1F5F9) else GunmetalCard)
                        .border(1.dp, (if (isLight) Color(0xFF0284C7) else ArcReactorCyan).copy(alpha = pulseGlow), RoundedCornerShape(10.dp))
                        .clickable { onOpenFriday() },
                    contentAlignment = Alignment.Center
                ) {
                    MiniArcReactorCanvas(modifier = Modifier.size(26.dp))
                }

                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text(
                            text = "STARK TACTICAL",
                            style = MaterialTheme.typography.titleMedium,
                            color = if (isLight) Color(0xFF0F172A) else TextHighContrastWhite,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(3.dp))
                                .background((if (isLight) Color(0xFF0284C7) else ArcReactorCyan).copy(alpha = 0.15f))
                                .border(0.5.dp, (if (isLight) Color(0xFF0284C7) else ArcReactorCyan).copy(alpha = 0.6f), RoundedCornerShape(3.dp))
                                .padding(horizontal = 4.dp, vertical = 1.dp)
                        ) {
                            Text(
                                text = "MK-85",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isLight) Color(0xFF0284C7) else ArcReactorCyan,
                                fontWeight = FontWeight.Bold,
                                fontSize = 9.sp
                            )
                        }
                    }
                    Text(
                        text = "${currentTab.title} • ${currentTab.heroAlias}",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isLight) Color(0xFFB45309) else StarkGold,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Right: Theme Toggle + Settings + Wade + Streak + FRIDAY Mic
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Quick Theme Toggle Switch Button
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isLight) Color(0xFFF1F5F9) else GunmetalCard)
                        .border(
                            1.dp,
                            if (isLight) Color(0xFFF59E0B).copy(alpha = 0.7f) else ArcReactorCyan.copy(alpha = 0.6f),
                            RoundedCornerShape(8.dp)
                        )
                        .clickable { onToggleTheme() }
                        .testTag("quick_theme_toggle_btn"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isLight) Icons.Filled.DarkMode else Icons.Filled.LightMode,
                        contentDescription = "Toggle Theme (Dark Developer / High-Contrast Light)",
                        tint = if (isLight) Color(0xFF334155) else Color(0xFFF59E0B),
                        modifier = Modifier.size(17.dp)
                    )
                }

                // Settings & Storage Dialog Button
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isLight) Color(0xFFF1F5F9) else GunmetalCard)
                        .border(1.dp, if (isLight) Color(0xFFCBD5E1) else GunmetalBorder, RoundedCornerShape(8.dp))
                        .clickable { onOpenSettings() }
                        .testTag("top_settings_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = "Settings & Appearance",
                        tint = if (isLight) Color(0xFF0F172A) else ArcReactorCyan,
                        modifier = Modifier.size(17.dp)
                    )
                }

                // Wolverine Adamantium Streak Pill
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isLight) Color(0xFFF1F5F9) else GunmetalCard)
                        .border(1.dp, (if (isLight) Color(0xFFB45309) else StarkGold).copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 6.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LocalFireDepartment,
                        contentDescription = "Streak",
                        tint = if (isLight) Color(0xFFB45309) else StarkGold,
                        modifier = Modifier.size(13.dp)
                    )
                    Text(
                        text = "${streakDays}d",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isLight) Color(0xFFB45309) else StarkGold,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Shuri / FRIDAY Voice Assistant Trigger
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(VibraniumPurple.copy(alpha = 0.2f))
                        .border(1.dp, VibraniumPurple.copy(alpha = pulseGlow), CircleShape)
                        .clickable { onOpenFriday() }
                        .testTag("friday_voice_trigger_top"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Mic,
                        contentDescription = "F.R.I.D.A.Y. Voice Assistant",
                        tint = if (isLight) Color(0xFF0284C7) else ArcReactorCyan,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

/**
 * Tactical MCU Bottom Navigation Bar with glowing telemetry accents
 */
@Composable
fun StudyFlowBottomBar(
    currentTab: ScreenTab,
    onTabSelected: (ScreenTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val isLight = LocalThemeIsLight.current
    Surface(
        color = if (isLight) Color.White.copy(alpha = 0.98f) else ObsidianBlack.copy(alpha = 0.98f),
        modifier = modifier
            .fillMaxWidth()
            .border(
                1.dp,
                if (isLight) Color(0xFFCBD5E1) else GunmetalBorderGlow.copy(alpha = 0.6f),
                RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
            .navigationBarsPadding(),
        tonalElevation = if (isLight) 6.dp else 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ScreenTab.entries.forEach { tab ->
                val isSelected = currentTab == tab
                val tint = if (isSelected) {
                    if (isLight) Color(0xFF0284C7) else ArcReactorCyan
                } else {
                    if (isLight) Color(0xFF64748B) else TextMutedGrey
                }

                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onTabSelected(tab) }
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                        .testTag("nav_${tab.name.lowercase()}"),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(contentAlignment = Alignment.TopEnd) {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = tab.title,
                            tint = tint,
                            modifier = Modifier.size(22.dp)
                        )
                        if (isSelected) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(if (isLight) Color(0xFFB45309) else StarkGold)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = tab.title,
                        style = MaterialTheme.typography.labelSmall,
                        color = tint,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}

/**
 * Custom Canvas drawing an Iron Man Arc Reactor
 */
@Composable
fun ArcReactorCoreCanvas(
    modifier: Modifier = Modifier,
    size: androidx.compose.ui.unit.Dp = 80.dp,
    percentage: Int = 0,
    isPulsing: Boolean = true
) {
    val infiniteTransition = rememberInfiniteTransition(label = "arc_reactor")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        val outerRadius = this.size.minDimension / 2f - 4.dp.toPx()
        val innerRadius = outerRadius * 0.72f
        val coreRadius = outerRadius * 0.45f

        // Outer Glow Ring
        drawCircle(
            color = ArcReactorCyan.copy(alpha = 0.08f),
            radius = outerRadius + 6.dp.toPx(),
            center = center
        )

        // Outer Metallic Rim
        drawCircle(
            color = GunmetalBorderGlow,
            radius = outerRadius,
            center = center,
            style = Stroke(width = 3.dp.toPx())
        )

        // Arc ticks around outer ring
        val tickCount = 18
        for (i in 0 until tickCount) {
            val angle = (i * (360f / tickCount)) * (Math.PI / 180.0)
            val startX = center.x + (outerRadius - 6.dp.toPx()) * cos(angle).toFloat()
            val startY = center.y + (outerRadius - 6.dp.toPx()) * sin(angle).toFloat()
            val endX = center.x + outerRadius * cos(angle).toFloat()
            val endY = center.y + outerRadius * sin(angle).toFloat()
            drawLine(
                color = ArcReactorCyan.copy(alpha = 0.45f),
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                strokeWidth = 2.dp.toPx(),
                cap = StrokeCap.Round
            )
        }

        // Rotating Middle Ring with Coils
        rotate(rotation, center) {
            drawCircle(
                color = ArcReactorCyan.copy(alpha = 0.6f),
                radius = innerRadius,
                center = center,
                style = Stroke(width = 2.dp.toPx())
            )

            val coilCount = 10
            for (i in 0 until coilCount) {
                val angle = (i * (360f / coilCount)) * (Math.PI / 180.0)
                val coilX = center.x + innerRadius * cos(angle).toFloat()
                val coilY = center.y + innerRadius * sin(angle).toFloat()
                drawCircle(
                    color = StarkGold,
                    radius = 3.5.dp.toPx(),
                    center = Offset(coilX, coilY)
                )
            }
        }

        // Inner Power Core (Triangle)
        rotate(-rotation * 0.6f, center) {
            val trianglePath = Path().apply {
                val r = coreRadius
                val p1 = Offset(center.x, center.y - r)
                val p2 = Offset(center.x + r * 0.866f, center.y + r * 0.5f)
                val p3 = Offset(center.x - r * 0.866f, center.y + r * 0.5f)
                moveTo(p1.x, p1.y)
                lineTo(p2.x, p2.y)
                lineTo(p3.x, p3.y)
                close()
            }
            drawPath(
                path = trianglePath,
                color = ArcReactorCyan.copy(alpha = 0.25f)
            )
            drawPath(
                path = trianglePath,
                color = ArcReactorCyan,
                style = Stroke(width = 2.dp.toPx())
            )
        }

        // Central Bright Glow Core
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(Color.White, ArcReactorCyan, Color.Transparent),
                center = center,
                radius = coreRadius * 0.8f
            ),
            radius = coreRadius * 0.7f,
            center = center
        )
    }
}

@Composable
fun MiniArcReactorCanvas(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val center = Offset(size.width / 2f, size.height / 2f)
        val r = size.minDimension / 2f - 2.dp.toPx()
        drawCircle(color = ArcReactorCyan.copy(alpha = 0.3f), radius = r, center = center)
        drawCircle(color = ArcReactorCyan, radius = r, center = center, style = Stroke(width = 1.5.dp.toPx()))
        drawCircle(color = Color.White, radius = r * 0.4f, center = center)
    }
}

/**
 * Tactical Corner Bracket Box wrapper for authentic Mark LXXXV HUD aesthetic
 */
@Composable
fun HudCornerBox(
    modifier: Modifier = Modifier,
    bracketColor: Color = if (LocalThemeIsLight.current) Color(0xFF0284C7) else ArcReactorCyan.copy(alpha = 0.6f),
    cornerColor: Color = bracketColor,
    fillColor: Color = if (LocalThemeIsLight.current) Color.White else GunmetalCard,
    borderColor: Color = if (LocalThemeIsLight.current) Color(0xFFCBD5E1) else GunmetalBorder,
    content: @Composable () -> Unit
) {
    val activeCornerColor = if (cornerColor != bracketColor) cornerColor else bracketColor
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(fillColor)
            .border(1.dp, borderColor, RoundedCornerShape(14.dp))
    ) {
        // Draw HUD Corner Brackets
        Canvas(modifier = Modifier.matchParentSize()) {
            val bracketLength = 12.dp.toPx()
            val strokeWidth = 2.dp.toPx()
            val pad = 4.dp.toPx()

            // Top-Left
            drawLine(activeCornerColor, Offset(pad, pad), Offset(pad + bracketLength, pad), strokeWidth)
            drawLine(activeCornerColor, Offset(pad, pad), Offset(pad, pad + bracketLength), strokeWidth)

            // Top-Right
            drawLine(activeCornerColor, Offset(size.width - pad, pad), Offset(size.width - pad - bracketLength, pad), strokeWidth)
            drawLine(activeCornerColor, Offset(size.width - pad, pad), Offset(size.width - pad, pad + bracketLength), strokeWidth)

            // Bottom-Left
            drawLine(activeCornerColor, Offset(pad, size.height - pad), Offset(pad + bracketLength, size.height - pad), strokeWidth)
            drawLine(activeCornerColor, Offset(pad, size.height - pad), Offset(pad, size.height - pad - bracketLength), strokeWidth)

            // Bottom-Right
            drawLine(activeCornerColor, Offset(size.width - pad, size.height - pad), Offset(size.width - pad - bracketLength, size.height - pad), strokeWidth)
            drawLine(activeCornerColor, Offset(size.width - pad, size.height - pad), Offset(size.width - pad, size.height - pad - bracketLength), strokeWidth)
        }

        content()
    }
}

/**
 * Iron Man & Doctor Doom Curriculum Progress Banner
 */
@Composable
fun CurriculumProgressBanner(
    completedChapters: Int,
    totalChapters: Int,
    percentage: Int,
    finalsInDays: Int = 42,
    modifier: Modifier = Modifier
) {
    HudCornerBox(
        modifier = modifier.fillMaxWidth(),
        bracketColor = ArcReactorCyan
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
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
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(ArcReactorCyan)
                        )
                        Text(
                            text = "STARK ARC TELEMETRY // CB-10",
                            style = MaterialTheme.typography.labelSmall,
                            color = ArcReactorCyan,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }
                    Text(
                        text = "$completedChapters of $totalChapters Chapters Conquered",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextHighContrastWhite,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Doom's Latverian Exam Conquest Target: 470/470 Marks",
                        style = MaterialTheme.typography.bodySmall,
                        color = DoomEmerald
                    )
                }

                // Power Level % Gauge
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "$percentage%",
                        style = MaterialTheme.typography.headlineLarge,
                        color = if (percentage >= 70) GammaGreen else if (percentage >= 30) StarkGold else ArcReactorCyan,
                        fontWeight = FontWeight.Black
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(GunmetalCardHigh)
                            .border(0.5.dp, ArcReactorCyan.copy(alpha = 0.3f), RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "D-$finalsInDays Board Exam",
                            style = MaterialTheme.typography.labelSmall,
                            color = StarkGold,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            // Material 3 Linear Progress Bar styled as Arc Reactor Energy Conduit
            LinearProgressIndicator(
                progress = { (percentage / 100f).coerceIn(0f, 1f) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = ArcReactorCyan,
                trackColor = GunmetalGrey,
                strokeCap = StrokeCap.Round
            )

            // Telemetry Status Badges
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(GammaGreen))
                        Text(
                            text = "Mastered ($completedChapters)",
                            style = MaterialTheme.typography.labelSmall,
                            color = GammaGreen
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(StarkGold))
                        Text(
                            text = "In Review (5)",
                            style = MaterialTheme.typography.labelSmall,
                            color = StarkGold
                        )
                    }
                }
                Text(
                    text = "Remaining: ${totalChapters - completedChapters}",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMutedGrey
                )
            }
        }
    }
}

/**
 * Deadpool 4th-Wall Breaking Motivation Overlay Modal
 */
@Composable
fun DeadpoolQuipOverlay(
    quip: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(CrimsonArc),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "🌮", fontSize = 16.sp)
                }
                Column {
                    Text(
                        text = "WADE WILSON (DEADPOOL)",
                        style = MaterialTheme.typography.titleMedium,
                        color = CrimsonArc,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = "4th-Wall Breaking Motivation Hotline",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMutedGrey
                    )
                }
            }
        },
        text = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(GunmetalCardHigh)
                    .border(1.dp, CrimsonArc.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                    .padding(14.dp)
            ) {
                Text(
                    text = "\"$quip\"",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextHighContrastWhite,
                    lineHeight = 22.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = CrimsonArc),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Maximum Effort! 👍", color = Color.White, fontWeight = FontWeight.Bold)
            }
        },
        containerColor = GunmetalGrey,
        shape = RoundedCornerShape(16.dp)
    )
}

/**
 * Shuri & F.R.I.D.A.Y. AI Tactical Voice Assistant & Command Terminal Modal
 */
@Composable
fun FridayAssistantModal(
    speechText: String,
    isSpeaking: Boolean,
    onClose: () -> Unit,
    onSendCommand: (String) -> Unit,
    onReplayVoice: () -> Unit = {}
) {
    var commandInput by remember { mutableStateOf("") }
    val quickCommands = listOf("Tactical Status", "Deadpool Quip", "Hulk Smash", "Logan Grit Check", "Doom Decree", "Sentry Focus")

    AlertDialog(
        onDismissRequest = onClose,
        title = {
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
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(VibraniumPurple.copy(alpha = 0.2f))
                            .border(1.dp, ArcReactorCyan, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Mic,
                            contentDescription = null,
                            tint = ArcReactorCyan,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "F.R.I.D.A.Y. // WAKANDA AI",
                            style = MaterialTheme.typography.titleMedium,
                            color = ArcReactorCyan,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            text = "Tactical Voice & Terminal System",
                            style = MaterialTheme.typography.labelSmall,
                            color = VibraniumPurple
                        )
                    }
                }
                IconButton(onClick = onClose) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = "Close", tint = TextMutedGrey)
                }
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                // Speech Bubble with Audio Waveform
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(GunmetalCard)
                        .border(1.dp, ArcReactorCyan.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                        .padding(14.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.VolumeUp,
                                    contentDescription = null,
                                    tint = if (isSpeaking) ArcReactorCyan else TextMutedGrey,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = if (isSpeaking) "AUDIO STREAM TRANSMITTING..." else "F.R.I.D.A.Y. VOICE ACTIVE",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (isSpeaking) ArcReactorCyan else TextMutedGrey,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            // Replay / Speak Aloud button
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(ArcReactorCyan.copy(alpha = 0.15f))
                                    .border(1.dp, ArcReactorCyan.copy(alpha = 0.6f), RoundedCornerShape(6.dp))
                                    .clickable { onReplayVoice() }
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.VolumeUp,
                                        contentDescription = "Speak Aloud",
                                        tint = ArcReactorCyan,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Text(
                                        text = "SPEAK ALOUD",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = ArcReactorCyan,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 9.sp
                                    )
                                }
                            }
                        }

                        Text(
                            text = speechText,
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextHighContrastWhite,
                            lineHeight = 20.sp
                        )

                        // Animated Visualizer Bar
                        if (isSpeaking) {
                            Row(
                                modifier = Modifier.fillMaxWidth().height(16.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                repeat(16) { index ->
                                    val heightFactor = if (index % 2 == 0) 12.dp else 6.dp
                                    Box(
                                        modifier = Modifier
                                            .width(3.dp)
                                            .height(heightFactor)
                                            .clip(RoundedCornerShape(2.dp))
                                            .background(ArcReactorCyan)
                                    )
                                }
                            }
                        }
                    }
                }

                // Quick Tactical Chips
                Text(
                    text = "TACTICAL QUICK COMMANDS",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMutedGrey,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    quickCommands.take(3).forEach { cmd ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(6.dp))
                                .background(GunmetalCardHigh)
                                .border(0.5.dp, GunmetalBorderGlow, RoundedCornerShape(6.dp))
                                .clickable { onSendCommand(cmd) }
                                .padding(vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = cmd,
                                style = MaterialTheme.typography.labelSmall,
                                color = ArcReactorCyan,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    quickCommands.drop(3).take(3).forEach { cmd ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(6.dp))
                                .background(GunmetalCardHigh)
                                .border(0.5.dp, GunmetalBorderGlow, RoundedCornerShape(6.dp))
                                .clickable { onSendCommand(cmd) }
                                .padding(vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = cmd,
                                style = MaterialTheme.typography.labelSmall,
                                color = StarkGold,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                // Custom Command Input
                OutlinedTextField(
                    value = commandInput,
                    onValueChange = { commandInput = it },
                    placeholder = { Text("Enter command (e.g. status, smash, doom)...", color = TextDarkGrey, fontSize = 12.sp) },
                    trailingIcon = {
                        IconButton(onClick = {
                            if (commandInput.isNotBlank()) {
                                onSendCommand(commandInput)
                                commandInput = ""
                            }
                        }) {
                            Icon(imageVector = Icons.Filled.Send, contentDescription = "Send", tint = ArcReactorCyan)
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextHighContrastWhite,
                        unfocusedTextColor = TextHighContrastWhite,
                        focusedBorderColor = ArcReactorCyan,
                        unfocusedBorderColor = GunmetalBorderGlow,
                        focusedContainerColor = GunmetalCard,
                        unfocusedContainerColor = GunmetalCard
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onClose,
                colors = ButtonDefaults.buttonColors(containerColor = GunmetalCardHigh),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Stand Down", color = TextMutedGrey)
            }
        },
        containerColor = GunmetalGrey,
        shape = RoundedCornerShape(18.dp)
    )
}

/**
 * Toast Notification with Tactical Arc Border
 */
@Composable
fun ToastNotification(
    message: String?,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = message != null,
        enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
        modifier = modifier
    ) {
        if (message != null) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(ObsidianBlack.copy(alpha = 0.94f))
                    .border(1.dp, ArcReactorCyan.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
                    .padding(horizontal = 16.dp, vertical = 10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Bolt,
                        contentDescription = null,
                        tint = ArcReactorCyan,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = message,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextHighContrastWhite,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

/**
 * Custom Chapter Dialog with Tactical Styling
 */
@Composable
fun AddCustomChapterDialog(
    onDismiss: () -> Unit,
    onAdd: (subjectId: String, title: String, marks: Int) -> Unit
) {
    var subjectId by remember { mutableStateOf("math") }
    var title by remember { mutableStateOf("") }
    var marks by remember { mutableStateOf("4") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Add Tactical Unit / Chapter",
                style = MaterialTheme.typography.headlineSmall,
                color = TextHighContrastWhite,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Chapter Title (e.g. Vectors & Coordinate Geometry)") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextHighContrastWhite,
                        unfocusedTextColor = TextHighContrastWhite,
                        focusedBorderColor = ArcReactorCyan,
                        unfocusedBorderColor = GunmetalBorder
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = marks,
                    onValueChange = { marks = it },
                    label = { Text("Board Exam Weightage Marks (e.g. 6)") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextHighContrastWhite,
                        unfocusedTextColor = TextHighContrastWhite,
                        focusedBorderColor = ArcReactorCyan,
                        unfocusedBorderColor = GunmetalBorder
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        onAdd(subjectId, title, marks.toIntOrNull() ?: 4)
                        onDismiss()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = ArcReactorCyan)
            ) {
                Text("Deploy to Matrix", color = ObsidianBlack, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Abort", color = TextMutedGrey)
            }
        },
        containerColor = GunmetalCard,
        shape = RoundedCornerShape(16.dp)
    )
}
