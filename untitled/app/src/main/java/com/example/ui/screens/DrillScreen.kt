package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Draw
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SquareFoot
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.DrawnLine
import com.example.ui.components.ScratchpadCanvas
import com.example.ui.components.ScreenTab
import com.example.ui.theme.AmberBadge
import com.example.ui.theme.CyanPrimaryContainer
import com.example.ui.theme.ElectricCyanPrimary
import com.example.ui.theme.EmeraldTertiary
import com.example.ui.theme.SecondaryContainer
import com.example.ui.theme.SoftBlueSecondary
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerHighest
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.TertiaryContainer
import com.example.ui.theme.TextHighContrastWhite
import com.example.ui.theme.TextMutedGrey
import com.example.ui.viewmodel.StudyFlowViewModel

@Composable
fun DrillScreen(
    viewModel: StudyFlowViewModel,
    onNavigateToTab: (ScreenTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val quizSeconds by viewModel.quizSecondsRemaining.collectAsState()
    val selectedOption by viewModel.selectedQuizOption.collectAsState()
    val confidence by viewModel.confidenceLevel.collectAsState()
    val isFormulaPeekOpen by viewModel.isFormulaPeekOpen.collectAsState()
    val isDiagramOpen by viewModel.isDiagramOpen.collectAsState()
    val isFlagged by viewModel.isFlagged.collectAsState()

    var isScratchpadOpen by remember { mutableStateOf(false) }
    val drawnLines = remember { mutableStateListOf<DrawnLine>() }

    val mins = quizSeconds / 60
    val secs = quizSeconds % 60
    val formattedQuizTimer = String.format("%02d:%02d", mins, secs)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            // Top Test Utility Header (Exit button & Live countdown timer)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainer)
                        .clickable { onNavigateToTab(ScreenTab.DASHBOARD) }
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Exit",
                        tint = TextMutedGrey,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(text = "Exit", style = MaterialTheme.typography.labelSmall, color = TextMutedGrey)
                }

                Row(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(SurfaceContainer)
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(ElectricCyanPrimary)
                    )
                    Icon(
                        imageVector = Icons.Filled.Timer,
                        contentDescription = null,
                        tint = ElectricCyanPrimary,
                        modifier = Modifier.size(15.dp)
                    )
                    Text(
                        text = formattedQuizTimer,
                        style = MaterialTheme.typography.labelMedium,
                        color = ElectricCyanPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "left", style = MaterialTheme.typography.labelSmall, color = TextMutedGrey)
                }
            }
        }

        // Breadcrumb & Subject Hierarchy
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(SecondaryContainer.copy(alpha = 0.25f))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "CLASS 10 CBSE",
                        style = MaterialTheme.typography.labelSmall,
                        color = SoftBlueSecondary
                    )
                }
                Text(text = "•", color = TextMutedGrey, style = MaterialTheme.typography.labelSmall)
                Text(
                    text = "Ch 8: Introduction to Trigonometry",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMutedGrey,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // Question Progress Indicator & Segmented Track
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceContainerLow)
                    .padding(12.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "Question 4",
                                style = MaterialTheme.typography.headlineSmall,
                                color = TextHighContrastWhite,
                                fontWeight = FontWeight.Bold
                            )
                            Text(text = "of 10", style = MaterialTheme.typography.labelSmall, color = TextMutedGrey)
                        }

                        Row(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(EmeraldTertiary.copy(alpha = 0.15f))
                                .padding(horizontal = 8.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Verified,
                                contentDescription = null,
                                tint = EmeraldTertiary,
                                modifier = Modifier.size(13.dp)
                            )
                            Text(
                                text = "3 answered",
                                style = MaterialTheme.typography.labelSmall,
                                color = EmeraldTertiary
                            )
                        }
                    }

                    // 10-Question Segmented Track
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        repeat(10) { index ->
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(6.dp)
                                    .clip(CircleShape)
                                    .background(
                                        when {
                                            index < 3 -> EmeraldTertiary
                                            index == 3 -> ElectricCyanPrimary
                                            else -> SurfaceContainerHighest
                                        }
                                    )
                            )
                        }
                    }
                }
            }
        }

        // Meta Info Bar & Formula Peek Action
        item {
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
                            .clip(RoundedCornerShape(6.dp))
                            .background(SurfaceContainerHigh)
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "Board 2023 • 3M",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextMutedGrey
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(AmberBadge.copy(alpha = 0.15f))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "Medium",
                            style = MaterialTheme.typography.labelSmall,
                            color = AmberBadge
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainer)
                        .clickable { viewModel.toggleFormulaPeek() }
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.SquareFoot,
                        contentDescription = null,
                        tint = ElectricCyanPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = if (isFormulaPeekOpen) "Hide Formulas" else "Formula Peek",
                        style = MaterialTheme.typography.labelSmall,
                        color = ElectricCyanPrimary
                    )
                }
            }
        }

        // Formula Quick Card Drawer
        if (isFormulaPeekOpen) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(SurfaceContainerHigh)
                        .border(1.dp, ElectricCyanPrimary.copy(alpha = 0.3f), RoundedCornerShape(14.dp))
                        .padding(14.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Trigonometric Identities",
                                style = MaterialTheme.typography.titleMedium,
                                color = TextHighContrastWhite,
                                fontWeight = FontWeight.Bold
                            )
                            IconButton(onClick = { viewModel.toggleFormulaPeek() }, modifier = Modifier.size(24.dp)) {
                                Icon(imageVector = Icons.Filled.Close, contentDescription = "Close", tint = TextMutedGrey)
                            }
                        }

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(SurfaceContainerLowest)
                                    .padding(8.dp)
                            ) {
                                Column {
                                    Text(text = "Quotient:", style = MaterialTheme.typography.labelSmall, color = TextMutedGrey)
                                    Text(
                                        text = "tan(θ) = sin(θ) / cos(θ)",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = ElectricCyanPrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(SurfaceContainerLowest)
                                    .padding(8.dp)
                            ) {
                                Column {
                                    Text(text = "Pythagorean:", style = MaterialTheme.typography.labelSmall, color = TextMutedGrey)
                                    Text(
                                        text = "sin²θ + cos²θ = 1",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = EmeraldTertiary,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Hero Question Card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceContainer)
                    .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "If tan(A) = 4/3 and angle A is an acute angle, evaluate the precise value of the expression:",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextHighContrastWhite
                    )

                    // Prominent LaTeX-style Math Equation Block
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceContainerLowest)
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "2 · sin(A) · cos(A)",
                                style = MaterialTheme.typography.headlineSmall,
                                color = TextHighContrastWhite,
                                fontWeight = FontWeight.Bold
                            )
                            Box(
                                modifier = Modifier
                                    .padding(vertical = 6.dp)
                                    .width(180.dp)
                                    .height(2.dp)
                                    .background(TextMutedGrey)
                            )
                            Text(
                                text = "cos²(A) − sin²(A)",
                                style = MaterialTheme.typography.headlineSmall,
                                color = TextHighContrastWhite,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Reference Triangle Diagram Toggle Button
                    Row(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(SurfaceContainerHigh)
                            .clickable { viewModel.toggleDiagram() }
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Visibility,
                            contentDescription = null,
                            tint = ElectricCyanPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = if (isDiagramOpen) "Hide Reference Triangle" else "Show Reference Triangle",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextHighContrastWhite
                        )
                    }

                    // Inline Vector Diagram Canvas
                    if (isDiagramOpen) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(SurfaceContainerLow)
                                .padding(12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Canvas(modifier = Modifier.size(width = 200.dp, height = 120.dp)) {
                                    val p1 = Offset(30f, 100f) // A
                                    val p2 = Offset(170f, 100f) // B (90°)
                                    val p3 = Offset(170f, 20f) // C

                                    val trianglePath = Path().apply {
                                        moveTo(p1.x, p1.y)
                                        lineTo(p2.x, p2.y)
                                        lineTo(p3.x, p3.y)
                                        close()
                                    }
                                    drawPath(
                                        path = trianglePath,
                                        color = ElectricCyanPrimary,
                                        style = Stroke(width = 4f)
                                    )
                                    // Right angle square at B
                                    drawRect(
                                        color = Color.White.copy(alpha = 0.5f),
                                        topLeft = Offset(155f, 85f),
                                        size = androidx.compose.ui.geometry.Size(15f, 15f),
                                        style = Stroke(width = 2f)
                                    )
                                }
                                Text(
                                    text = "tan(A) = opp / adj = 4/3 ⇒ sin(A) = 4/5, cos(A) = 3/5",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = EmeraldTertiary,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }

        // Answer Options (A, B, C, D)
        val options = listOf(
            "A" to ("− 24 / 7" to "Negative ratio"),
            "B" to ("− 24 / 7" to "Selected Answer"),
            "C" to ("12 / 25" to "Numerator evaluation only"),
            "D" to ("7 / 25" to "Denominator evaluation only")
        )

        items(options.size) { index ->
            val (letter, data) = options[index]
            val isSelected = selectedOption == letter

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(if (isSelected) SurfaceContainerHigh else SurfaceContainer)
                    .border(
                        1.dp,
                        if (isSelected) ElectricCyanPrimary else Color.Transparent,
                        RoundedCornerShape(14.dp)
                    )
                    .clickable { viewModel.selectQuizOption(letter) }
                    .padding(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) ElectricCyanPrimary else SurfaceContainerHigh),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = letter,
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isSelected) SurfaceContainerLowest else TextMutedGrey,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Column {
                            Text(
                                text = data.first,
                                style = MaterialTheme.typography.headlineSmall,
                                color = if (isSelected) ElectricCyanPrimary else TextHighContrastWhite,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = data.second,
                                style = MaterialTheme.typography.labelSmall,
                                color = TextMutedGrey
                            )
                        }
                    }

                    if (isSelected) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = "Selected",
                            tint = ElectricCyanPrimary,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }

        // Rough Work & Scratchpad Drawer Toggle
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceContainer)
                        .clickable { isScratchpadOpen = !isScratchpadOpen }
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Draw,
                            contentDescription = null,
                            tint = SoftBlueSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Rough Work & Scratchpad",
                            style = MaterialTheme.typography.titleSmall,
                            color = TextHighContrastWhite,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(text = "Pen / Eraser", style = MaterialTheme.typography.labelSmall, color = TextMutedGrey)
                        Icon(
                            imageVector = if (isScratchpadOpen) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                            contentDescription = null,
                            tint = TextMutedGrey,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                if (isScratchpadOpen) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceContainerLow)
                            .padding(12.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Digital Rough Canvas:",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = TextMutedGrey
                                )
                                Row(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(SurfaceContainer)
                                        .clickable { drawnLines.clear() }
                                        .padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Delete,
                                        contentDescription = "Clear",
                                        tint = TextMutedGrey,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(text = "Clear", style = MaterialTheme.typography.labelSmall, color = TextMutedGrey)
                                }
                            }

                            ScratchpadCanvas(
                                lines = drawnLines,
                                onStartNewLine = { offset ->
                                    drawnLines.add(DrawnLine(listOf(offset)))
                                },
                                onAddPointToCurrentLine = { offset ->
                                    if (drawnLines.isNotEmpty()) {
                                        val last = drawnLines.last()
                                        drawnLines[drawnLines.size - 1] = last.copy(path = last.path + offset)
                                    }
                                }
                            )

                            Text(
                                text = "Draw steps or calculate fractions with finger/stylus",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextMutedGrey,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }

        // Confidence Selector
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(SurfaceContainer)
                    .padding(14.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Confidence Level for AI Analytics",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextMutedGrey
                        )
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = null,
                            tint = TextMutedGrey,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(
                            Triple("sure", "Certain", Icons.Filled.Verified),
                            Triple("half", "50 / 50", Icons.Filled.Help),
                            Triple("doubt", "Guessing", Icons.Filled.SentimentDissatisfied)
                        ).forEach { (confKey, confLabel, confIcon) ->
                            val isChosen = confidence == confKey
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        if (isChosen) EmeraldTertiary.copy(alpha = 0.2f) else SurfaceContainerHigh
                                    )
                                    .clickable { viewModel.setConfidence(confKey) }
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = confIcon,
                                        contentDescription = null,
                                        tint = if (isChosen) EmeraldTertiary else TextMutedGrey,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = confLabel,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (isChosen) EmeraldTertiary else TextMutedGrey,
                                        fontWeight = if (isChosen) FontWeight.Bold else FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Bottom Action Progression Dock
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceContainer)
                        .clickable { viewModel.toggleFlag() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isFlagged) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                        contentDescription = "Bookmark",
                        tint = if (isFlagged) AmberBadge else TextMutedGrey,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Button(
                    onClick = {
                        viewModel.showToast("Question 4 skipped")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = SurfaceContainer),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.height(48.dp)
                ) {
                    Text("Skip", color = TextHighContrastWhite)
                }

                Button(
                    onClick = {
                        viewModel.showToast("Answer confirmed! Mastered +12 XP")
                        onNavigateToTab(ScreenTab.DASHBOARD)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CyanPrimaryContainer),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("confirm_next_question_btn")
                ) {
                    Text(
                        text = "Confirm & Next →",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
