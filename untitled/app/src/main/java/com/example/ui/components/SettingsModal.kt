package com.example.ui.components

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.ArcReactorCyan
import com.example.ui.theme.GunmetalBorder
import com.example.ui.theme.GunmetalCard
import com.example.ui.theme.GunmetalCardHigh
import com.example.ui.theme.LocalThemeIsLight
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.StarkGold
import com.example.ui.theme.TextDarkGrey
import com.example.ui.theme.TextHighContrastWhite
import com.example.ui.theme.TextMutedGrey

/**
 * Interactive Settings & Appearance Modal Dialog
 * Allows switching between 'Dark Developer' (Obsidian HUD) and 'High-Contrast Light' mode,
 * configuring daily Pomodoro study goals, and reviewing persistent data telemetry.
 */
@Composable
fun SettingsModal(
    isOpen: Boolean,
    isLightMode: Boolean,
    onToggleTheme: (Boolean) -> Unit,
    onClose: () -> Unit,
    totalChaptersCompleted: Int,
    totalChapters: Int,
    dailyPomodoroTarget: Int,
    onSetDailyPomodoroTarget: (Int) -> Unit,
    streakDays: Int,
    modifier: Modifier = Modifier
) {
    if (!isOpen) return

    val isLight = LocalThemeIsLight.current

    val dialogBackground = if (isLight) Color.White else GunmetalCard
    val dialogBorder = if (isLight) Color(0xFFCBD5E1) else GunmetalBorder
    val surfaceBoxColor = if (isLight) Color(0xFFF8FAFC) else GunmetalCardHigh
    val textPrimary = if (isLight) Color(0xFF0F172A) else TextHighContrastWhite
    val textSecondary = if (isLight) Color(0xFF475569) else TextMutedGrey

    Dialog(onDismissRequest = onClose) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .border(1.5.dp, if (isLight) Color(0xFF0284C7).copy(alpha = 0.5f) else ArcReactorCyan.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                .testTag("settings_modal_dialog"),
            color = dialogBackground,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isLight) Color(0xFF0284C7).copy(alpha = 0.15f) else ArcReactorCyan.copy(alpha = 0.15f))
                                .border(1.dp, if (isLight) Color(0xFF0284C7).copy(alpha = 0.4f) else ArcReactorCyan.copy(alpha = 0.4f), RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = "Settings",
                                tint = if (isLight) Color(0xFF0284C7) else ArcReactorCyan,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "Settings & Appearance",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = textPrimary
                            )
                            Text(
                                text = "Tactical Mark LXXXV System Preferences",
                                style = MaterialTheme.typography.labelSmall,
                                color = textSecondary
                            )
                        }
                    }

                    IconButton(
                        onClick = onClose,
                        modifier = Modifier
                            .size(32.dp)
                            .testTag("settings_close_button")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close Settings",
                            tint = textSecondary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                // ==========================================
                // SECTION 1: THEME TOGGLE (USER CONTROLLED)
                // ==========================================
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(surfaceBoxColor)
                        .border(1.dp, dialogBorder, RoundedCornerShape(14.dp))
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = if (isLightMode) Icons.Filled.LightMode else Icons.Filled.DarkMode,
                                contentDescription = "Theme Icon",
                                tint = if (isLightMode) Color(0xFFF59E0B) else ArcReactorCyan,
                                modifier = Modifier.size(20.dp)
                            )
                            Column {
                                Text(
                                    text = "Interface Theme",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = textPrimary
                                )
                                Text(
                                    text = if (isLightMode) "High-Contrast Light Mode Active" else "Dark Developer (Obsidian HUD) Active",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontFamily = FontFamily.Monospace,
                                    color = if (isLightMode) Color(0xFF0284C7) else ArcReactorCyan
                                )
                            }
                        }

                        // M3 Switch Toggle
                        Switch(
                            checked = isLightMode,
                            onCheckedChange = { onToggleTheme(it) },
                            modifier = Modifier.testTag("theme_toggle_switch"),
                            thumbContent = {
                                Icon(
                                    imageVector = if (isLightMode) Icons.Filled.LightMode else Icons.Filled.DarkMode,
                                    contentDescription = null,
                                    modifier = Modifier.size(SwitchDefaults.IconSize),
                                    tint = if (isLightMode) Color(0xFFB45309) else Color(0xFF0F172A)
                                )
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color(0xFF0284C7),
                                uncheckedThumbColor = ArcReactorCyan,
                                uncheckedTrackColor = ObsidianBlack,
                                uncheckedBorderColor = GunmetalBorder
                            )
                        )
                    }

                    // Visual Theme Selector Cards
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Card 1: Dark Developer
                        val darkSelected = !isLightMode
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (darkSelected) ObsidianBlack else dialogBackground)
                                .border(
                                    1.5.dp,
                                    if (darkSelected) ArcReactorCyan else dialogBorder,
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable { onToggleTheme(false) }
                                .padding(10.dp)
                                .testTag("theme_card_dark")
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Dark Developer",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = if (darkSelected) ArcReactorCyan else textPrimary
                                    )
                                    if (darkSelected) {
                                        Box(
                                            modifier = Modifier
                                                .size(8.dp)
                                                .clip(CircleShape)
                                                .background(ArcReactorCyan)
                                        )
                                    }
                                }
                                Text(
                                    text = "Obsidian black & gunmetal HUD with glowing cyan accents",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontSize = 10.sp,
                                    color = textSecondary
                                )
                            }
                        }

                        // Card 2: High-Contrast Light
                        val lightSelected = isLightMode
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (lightSelected) Color(0xFFF1F5F9) else dialogBackground)
                                .border(
                                    1.5.dp,
                                    if (lightSelected) Color(0xFF0284C7) else dialogBorder,
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable { onToggleTheme(true) }
                                .padding(10.dp)
                                .testTag("theme_card_light")
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "High-Contrast Light",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = if (lightSelected) Color(0xFF0284C7) else textPrimary
                                    )
                                    if (lightSelected) {
                                        Box(
                                            modifier = Modifier
                                                .size(8.dp)
                                                .clip(CircleShape)
                                                .background(Color(0xFF0284C7))
                                        )
                                    }
                                }
                                Text(
                                    text = "Pure white surfaces with deep high-contrast text & slate borders",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontSize = 10.sp,
                                    color = textSecondary
                                )
                            }
                        }
                    }
                }

                // ==========================================
                // SECTION 2: DAILY POMODORO TARGET CONFIGURATION
                // ==========================================
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(surfaceBoxColor)
                        .border(1.dp, dialogBorder, RoundedCornerShape(14.dp))
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Timer,
                            contentDescription = "Timer Goal",
                            tint = StarkGold,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Daily Pomodoro Target",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = textPrimary
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(4, 6, 8, 10).forEach { target ->
                            val isSelected = dailyPomodoroTarget == target
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        if (isSelected) {
                                            if (isLight) Color(0xFF0284C7).copy(alpha = 0.15f) else StarkGold.copy(alpha = 0.2f)
                                        } else dialogBackground
                                    )
                                    .border(
                                        1.dp,
                                        if (isSelected) {
                                            if (isLight) Color(0xFF0284C7) else StarkGold
                                        } else dialogBorder,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .clickable { onSetDailyPomodoroTarget(target) }
                                    .padding(vertical = 6.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "${target}P",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily.Monospace,
                                    color = if (isSelected) {
                                        if (isLight) Color(0xFF0284C7) else StarkGold
                                    } else textSecondary
                                )
                            }
                        }
                    }
                }

                // ==========================================
                // SECTION 3: STORAGE ENGINE TELEMETRY
                // ==========================================
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(surfaceBoxColor)
                        .border(1.dp, dialogBorder, RoundedCornerShape(14.dp))
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Storage,
                            contentDescription = "Storage",
                            tint = if (isLight) Color(0xFF059669) else Color(0xFF10B981),
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Permanent Storage & Telemetry",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = textPrimary
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Completed Chapters",
                            style = MaterialTheme.typography.labelSmall,
                            color = textSecondary
                        )
                        Text(
                            text = "$totalChaptersCompleted of $totalChapters conquered",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            color = if (isLight) Color(0xFF059669) else Color(0xFF10B981)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Wolverine Streak",
                            style = MaterialTheme.typography.labelSmall,
                            color = textSecondary
                        )
                        Text(
                            text = "$streakDays Days Adamantium Record",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace,
                            color = StarkGold
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Local Storage Engine",
                            style = MaterialTheme.typography.labelSmall,
                            color = textSecondary
                        )
                        Text(
                            text = "SQLite Room DB + SharedPreferences",
                            style = MaterialTheme.typography.labelSmall,
                            fontFamily = FontFamily.Monospace,
                            color = textSecondary
                        )
                    }
                }

                // Close & Apply Button
                Button(
                    onClick = onClose,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("settings_done_button"),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isLight) Color(0xFF0284C7) else ArcReactorCyan,
                        contentColor = if (isLight) Color.White else ObsidianBlack
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Done & Apply Preferences",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
