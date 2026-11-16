package com.example.ui.screens

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.example.data.model.SavedMemoryEntity
import com.example.ui.components.HudCornerBox
import com.example.ui.components.ScreenTab
import com.example.ui.theme.ArcReactorCyan
import com.example.ui.theme.BerserkerCrimson
import com.example.ui.theme.DoomEmerald
import com.example.ui.theme.EmeraldTertiary
import com.example.ui.theme.GammaGreen
import com.example.ui.theme.GunmetalBorder
import com.example.ui.theme.GunmetalCard
import com.example.ui.theme.GunmetalCardHigh
import com.example.ui.theme.GunmetalGrey
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.SoftBlueSecondary
import com.example.ui.theme.StarkGold
import com.example.ui.theme.StreakFlameOrange
import com.example.ui.theme.TextHighContrastWhite
import com.example.ui.theme.TextMutedGrey
import com.example.ui.theme.VibraniumPurple
import com.example.ui.viewmodel.StudyFlowViewModel

@Composable
fun SavedMemoryScreen(
    viewModel: StudyFlowViewModel,
    onNavigateToTab: (ScreenTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val memories by viewModel.savedMemories.collectAsState()
    val subjectsWithStats by viewModel.subjectsWithStats.collectAsState()
    var selectedFilter by remember { mutableStateOf("all") }
    var searchQuery by remember { mutableStateOf("") }
    var showAddDialog by remember { mutableStateOf(false) }
    var showResetConfirmDialog by remember { mutableStateOf(false) }

    val totalChapters = subjectsWithStats.sumOf { it.totalCount }

    val filteredMemories = memories.filter { memory ->
        val matchesSubject = selectedFilter == "all" || memory.subjectId == selectedFilter
        val matchesQuery = searchQuery.isBlank() ||
                memory.conceptTitle.contains(searchQuery, ignoreCase = true) ||
                memory.memoryContent.contains(searchQuery, ignoreCase = true) ||
                memory.chapterTitle.contains(searchQuery, ignoreCase = true)
        matchesSubject && matchesQuery
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(2.dp))

            // Ultron Room Database Core & Integrity Diagnostics
            HudCornerBox(
                cornerColor = VibraniumPurple,
                fillColor = GunmetalCard,
                borderColor = VibraniumPurple.copy(alpha = 0.4f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
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
                                    .background(VibraniumPurple.copy(alpha = 0.25f))
                                    .border(1.dp, VibraniumPurple, RoundedCornerShape(8.dp)),
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
                                    text = "ULTRON ROOM DATA STORAGE CORE",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = TextHighContrastWhite,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp
                                )
                                Text(
                                    text = "Permanent Local Storage • Zero Cloud Leakage",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = VibraniumPurple
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(GunmetalCardHigh)
                                .border(1.dp, EmeraldTertiary.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "CORE 100% HEALTHY",
                                style = MaterialTheme.typography.labelSmall,
                                color = EmeraldTertiary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 8.sp
                            )
                        }
                    }

                    // Entity count diagnostic telemetry strip
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        DiagnosticNode(label = "SUBJECTS", value = "7", tint = ArcReactorCyan, modifier = Modifier.weight(1f))
                        DiagnosticNode(label = "CHAPTERS", value = "$totalChapters", tint = StarkGold, modifier = Modifier.weight(1f))
                        DiagnosticNode(label = "MEMORIES", value = "${memories.size}", tint = VibraniumPurple, modifier = Modifier.weight(1f))
                    }
                }
            }
        }

        // Vision Mind Stone Concept Synthesizer Banner
        item {
            HudCornerBox(
                cornerColor = StarkGold,
                fillColor = GunmetalCard,
                borderColor = StarkGold.copy(alpha = 0.35f)
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
                                .background(StarkGold.copy(alpha = 0.25f))
                                .border(1.dp, StarkGold, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "🧠", fontSize = 18.sp)
                        }
                        Column {
                            Text(
                                text = "VISION'S MIND STONE SYNTHESIZER",
                                style = MaterialTheme.typography.titleSmall,
                                color = TextHighContrastWhite,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "Distill complex NCERT derivations into pure mental nodes",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextMutedGrey
                            )
                        }
                    }

                    Button(
                        onClick = { showAddDialog = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = StarkGold,
                            contentColor = ObsidianBlack
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "+ Store",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Search Bar
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = {
                    Text("Search synthesized concepts, mnemonics...", color = TextMutedGrey)
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = ArcReactorCyan,
                        modifier = Modifier.size(20.dp)
                    )
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

        // Filter Pills
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(
                    "all" to "All Syntheses",
                    "math" to "Mathematics",
                    "science" to "Science",
                    "sst" to "Social Science",
                    "english" to "English"
                ).forEach { (id, label) ->
                    val isSelected = selectedFilter == id
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) ArcReactorCyan.copy(alpha = 0.2f) else GunmetalCard)
                            .border(
                                1.dp,
                                if (isSelected) ArcReactorCyan else GunmetalBorder,
                                RoundedCornerShape(8.dp)
                            )
                            .clickable { selectedFilter = id }
                            .padding(horizontal = 14.dp, vertical = 7.dp)
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelMedium,
                            color = if (isSelected) ArcReactorCyan else TextMutedGrey,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }
        }

        // Memories List or Empty State
        if (filteredMemories.isEmpty()) {
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
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.BookmarkBorder,
                            contentDescription = null,
                            tint = StarkGold,
                            modifier = Modifier.size(36.dp)
                        )
                        Text(
                            text = "No saved memories in this domain",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextHighContrastWhite,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Tap '+ Store' above to synthesize your first formula or key concept.",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextMutedGrey
                        )
                    }
                }
            }
        } else {
            items(filteredMemories, key = { it.id }) { memory ->
                MemoryCard(
                    memory = memory,
                    onTogglePin = { viewModel.togglePinMemory(memory.id) },
                    onDelete = { viewModel.deleteMemory(memory) }
                )
            }
        }

        // Thor Mjolnir Lightning Review Banner
        item {
            HudCornerBox(
                cornerColor = ArcReactorCyan,
                fillColor = GunmetalCard,
                borderColor = GunmetalBorder
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
                                .size(34.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(ArcReactorCyan.copy(alpha = 0.2f))
                                .border(1.dp, ArcReactorCyan, RoundedCornerShape(6.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "⚡", fontSize = 16.sp)
                        }
                        Column {
                            Text(
                                text = "THOR'S MJOLNIR DRILL STRIKE",
                                style = MaterialTheme.typography.titleSmall,
                                color = TextHighContrastWhite,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "Rapid board exam drill with lightning-fast recall",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextMutedGrey
                            )
                        }
                    }

                    Button(
                        onClick = { onNavigateToTab(ScreenTab.FOCUS) },
                        colors = ButtonDefaults.buttonColors(containerColor = ArcReactorCyan),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Strike Drill",
                            color = ObsidianBlack,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Reset All Chapters Button
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(GunmetalCard)
                    .border(1.dp, BerserkerCrimson.copy(alpha = 0.35f), RoundedCornerShape(10.dp))
                    .clickable { showResetConfirmDialog = true }
                    .padding(14.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.RestartAlt,
                        contentDescription = null,
                        tint = BerserkerCrimson,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "Reset All Chapters to Unlearned State",
                        style = MaterialTheme.typography.bodyMedium,
                        color = BerserkerCrimson,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(28.dp))
        }
    }

    if (showAddDialog) {
        AddMemoryDialog(
            onDismiss = { showAddDialog = false },
            onSave = { subjectId, subjectName, chapterTitle, conceptTitle, content, mnemonic ->
                viewModel.saveMemory(subjectId, subjectName, chapterTitle, conceptTitle, content, mnemonic)
                showAddDialog = false
            }
        )
    }

    if (showResetConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showResetConfirmDialog = false },
            title = {
                Text(
                    text = "Reset All Chapters?",
                    color = TextHighContrastWhite,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "This will reset all 139 CBSE chapters and subtopic checkboxes across all 7 subjects to UNLEARNED. Permanent Room DB records will be updated.",
                    color = TextMutedGrey
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.resetAllChaptersToUnlearned()
                        showResetConfirmDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = BerserkerCrimson)
                ) {
                    Text("Confirm Reset", color = TextHighContrastWhite, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetConfirmDialog = false }) {
                    Text("Cancel", color = TextMutedGrey)
                }
            },
            containerColor = GunmetalCard,
            shape = RoundedCornerShape(14.dp)
        )
    }
}

@Composable
private fun DiagnosticNode(
    label: String,
    value: String,
    tint: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(GunmetalCardHigh)
            .border(1.dp, GunmetalBorder, RoundedCornerShape(6.dp))
            .padding(8.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = TextMutedGrey,
                fontSize = 9.sp
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                color = tint,
                fontWeight = FontWeight.Black
            )
        }
    }
}

@Composable
private fun MemoryCard(
    memory: SavedMemoryEntity,
    onTogglePin: () -> Unit,
    onDelete: () -> Unit
) {
    HudCornerBox(
        cornerColor = if (memory.isPinned) StarkGold else ArcReactorCyan,
        fillColor = GunmetalCard,
        borderColor = if (memory.isPinned) StarkGold.copy(alpha = 0.5f) else GunmetalBorder,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = memory.subjectName.uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            color = ArcReactorCyan,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = "•", color = TextMutedGrey)
                        Text(
                            text = memory.chapterTitle,
                            style = MaterialTheme.typography.labelSmall,
                            color = TextMutedGrey
                        )
                    }

                    Text(
                        text = memory.conceptTitle,
                        style = MaterialTheme.typography.titleMedium,
                        color = TextHighContrastWhite,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconButton(onClick = onTogglePin, modifier = Modifier.size(32.dp)) {
                        Icon(
                            imageVector = if (memory.isPinned) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                            contentDescription = "Pin",
                            tint = if (memory.isPinned) StarkGold else TextMutedGrey,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Delete",
                            tint = BerserkerCrimson.copy(alpha = 0.8f),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Text(
                text = memory.memoryContent,
                style = MaterialTheme.typography.bodyMedium,
                color = TextHighContrastWhite.copy(alpha = 0.9f)
            )

            if (memory.mnemonic.isNotBlank()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(6.dp))
                        .background(StarkGold.copy(alpha = 0.15f))
                        .border(1.dp, StarkGold.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(text = "🧠 Mnemonic:", color = StarkGold, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                        Text(text = memory.mnemonic, color = TextHighContrastWhite, fontSize = 11.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun AddMemoryDialog(
    onDismiss: () -> Unit,
    onSave: (subjectId: String, subjectName: String, chapterTitle: String, conceptTitle: String, content: String, mnemonic: String) -> Unit
) {
    var conceptTitle by remember { mutableStateOf("") }
    var chapterTitle by remember { mutableStateOf("Chapter 8 Trigonometry") }
    var content by remember { mutableStateOf("") }
    var mnemonic by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Synthesize to Mind Stone Vault",
                color = TextHighContrastWhite,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = conceptTitle,
                    onValueChange = { conceptTitle = it },
                    label = { Text("Concept / Formula Name") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextHighContrastWhite,
                        unfocusedTextColor = TextHighContrastWhite,
                        focusedContainerColor = GunmetalCardHigh,
                        unfocusedContainerColor = GunmetalCardHigh,
                        focusedBorderColor = StarkGold,
                        unfocusedBorderColor = GunmetalBorder
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Formulation / Core Truth") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextHighContrastWhite,
                        unfocusedTextColor = TextHighContrastWhite,
                        focusedContainerColor = GunmetalCardHigh,
                        unfocusedContainerColor = GunmetalCardHigh,
                        focusedBorderColor = StarkGold,
                        unfocusedBorderColor = GunmetalBorder
                    ),
                    minLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = mnemonic,
                    onValueChange = { mnemonic = it },
                    label = { Text("Recall Mnemonic (Optional)") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextHighContrastWhite,
                        unfocusedTextColor = TextHighContrastWhite,
                        focusedContainerColor = GunmetalCardHigh,
                        unfocusedContainerColor = GunmetalCardHigh,
                        focusedBorderColor = StarkGold,
                        unfocusedBorderColor = GunmetalBorder
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (conceptTitle.isNotBlank() && content.isNotBlank()) {
                        onSave("math", "Mathematics", chapterTitle, conceptTitle, content, mnemonic)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = StarkGold)
            ) {
                Text("Synthesize Node", color = ObsidianBlack, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = TextMutedGrey)
            }
        },
        containerColor = GunmetalCard,
        shape = RoundedCornerShape(14.dp)
    )
}
