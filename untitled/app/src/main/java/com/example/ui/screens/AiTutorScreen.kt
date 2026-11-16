package com.example.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Cameraswitch
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.CropFree
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FlashOff
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.VideocamOff
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.HudCornerBox
import com.example.ui.components.ScreenTab
import com.example.ui.theme.ArcReactorCyan
import com.example.ui.theme.GunmetalBorder
import com.example.ui.theme.GunmetalCard
import com.example.ui.theme.GunmetalCardHigh
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.StarkGold
import com.example.ui.theme.AmberBadge
import com.example.ui.theme.CyanPrimaryContainer
import com.example.ui.theme.ElectricCyanPrimary
import com.example.ui.theme.EmeraldTertiary
import com.example.ui.theme.ErrorContainer
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.SecondaryContainer
import com.example.ui.theme.SoftBlueSecondary
import com.example.ui.theme.SurfaceContainer
import com.example.ui.theme.SurfaceContainerHigh
import com.example.ui.theme.SurfaceContainerHighest
import com.example.ui.theme.SurfaceContainerLow
import com.example.ui.theme.SurfaceContainerLowest
import com.example.ui.theme.TextHighContrastWhite
import com.example.ui.theme.TextMutedGrey
import com.example.ui.viewmodel.AiEngineType
import com.example.ui.viewmodel.GeminiAiViewModel

@Composable
fun AiTutorScreen(
    aiViewModel: GeminiAiViewModel,
    onNavigateToTab: (ScreenTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val socraticState by aiViewModel.socraticState.collectAsState()
    val isGenerating by aiViewModel.isGeneratingProblem.collectAsState()
    var followUpInput by remember { mutableStateOf("") }
    val context = LocalContext.current
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        )
    }
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasCameraPermission = isGranted
        if (isGranted) {
            aiViewModel.toggleLiveCamera(true)
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(4.dp))
            // NVIDIA NIM 26B Diffusion Engine Active Status & Toggle
            NvidiaEngineBanner(
                activeEngine = socraticState.activeEngine,
                onToggleEngine = { aiViewModel.toggleEngine() }
            )
        }

        // 1. QUESTION SCANNER & TARGET VIEWPORT SPACE
        item {
            val clipboardManager = LocalClipboardManager.current
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceContainerLowest)
                    .border(1.dp, ElectricCyanPrimary.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                    .padding(14.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    // Header Bar with Telemetry
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
                                    .background(EmeraldTertiary)
                            )
                            Text(
                                text = "QUESTION SCANNER VIEWPORT",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextHighContrastWhite,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(EmeraldTertiary.copy(alpha = 0.15f))
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "99.4% Optical Confidence",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = EmeraldTertiary
                                )
                            }
                        }
                    }

                    // Scanner Mode Tabs
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf(
                            "VIEWFINDER" to "📷 Live Camera",
                            "SAMPLE_PICKER" to "📚 Board Samples",
                            "CUSTOM_INPUT" to "✍️ Custom Input"
                        ).forEach { (modeKey, label) ->
                            val isSelected = socraticState.scanMode == modeKey
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) CyanPrimaryContainer else SurfaceContainerHigh)
                                    .clickable { aiViewModel.setScanMode(modeKey) }
                                    .padding(vertical = 7.dp, horizontal = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (isSelected) Color.White else TextMutedGrey,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }

                    // QUESTION SCANNER VIEWPORT SPACE (The area to clearly see the question being scanned)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceContainerLow)
                            .border(1.5.dp, ElectricCyanPrimary.copy(alpha = 0.7f), RoundedCornerShape(12.dp))
                            .padding(14.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(SurfaceContainerHigh)
                                        .padding(horizontal = 8.dp, vertical = 3.dp),
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = socraticState.scannedQuestionSubject,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = ElectricCyanPrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                // Camera Scan Mode Toggle: Live Camera Feed vs Mock UI
                                Row(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(SurfaceContainerHigh)
                                        .padding(horizontal = 8.dp, vertical = 3.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        imageVector = if (socraticState.isLiveCameraEnabled) Icons.Filled.Videocam else Icons.Filled.CameraAlt,
                                        contentDescription = null,
                                        tint = if (socraticState.isLiveCameraEnabled) EmeraldTertiary else ElectricCyanPrimary,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Text(
                                        text = if (socraticState.isLiveCameraEnabled) "Live Feed" else "Mock UI",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = if (socraticState.isLiveCameraEnabled) EmeraldTertiary else TextMutedGrey,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    )
                                    Switch(
                                        checked = socraticState.isLiveCameraEnabled,
                                        onCheckedChange = { isEnabled ->
                                            if (isEnabled) {
                                                if (hasCameraPermission) {
                                                    aiViewModel.toggleLiveCamera(true)
                                                } else {
                                                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                                                }
                                            } else {
                                                aiViewModel.toggleLiveCamera(false)
                                            }
                                        },
                                        colors = SwitchDefaults.colors(
                                            checkedThumbColor = Color.White,
                                            checkedTrackColor = EmeraldTertiary,
                                            uncheckedThumbColor = TextMutedGrey,
                                            uncheckedTrackColor = SurfaceContainerLowest
                                        )
                                    )
                                }
                            }

                            // Camera Scanner Viewport Feed (Switches between Live Camera Feed and Mock Viewfinder)
                            AnimatedVisibility(visible = socraticState.scanMode == "VIEWFINDER") {
                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    if (socraticState.isLiveCameraEnabled) {
                                        if (!hasCameraPermission) {
                                            CameraPermissionPromptCard(
                                                onRequestPermission = { cameraPermissionLauncher.launch(Manifest.permission.CAMERA) },
                                                onFallbackToMock = { aiViewModel.toggleLiveCamera(false) }
                                            )
                                        } else {
                                            LiveCameraScannerViewport(
                                                aiViewModel = aiViewModel,
                                                socraticState = socraticState
                                            )
                                        }
                                    } else {
                                        MockScannerViewport(
                                            aiViewModel = aiViewModel,
                                            onEnableLiveCamera = {
                                                if (hasCameraPermission) {
                                                    aiViewModel.toggleLiveCamera(true)
                                                } else {
                                                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                                                }
                                            }
                                        )
                                    }
                                }
                            }

                            // OCR Scan Extraction Status Feedback Banner
                            socraticState.cameraScanFeedback?.let { feedbackText ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(EmeraldTertiary.copy(alpha = 0.15f))
                                        .border(1.dp, EmeraldTertiary.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                                        .padding(horizontal = 8.dp, vertical = 4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Filled.Check, contentDescription = null, tint = EmeraldTertiary, modifier = Modifier.size(13.dp))
                                        Text(
                                            text = feedbackText,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = EmeraldTertiary,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 11.sp
                                        )
                                    }
                                    IconButton(
                                        onClick = { aiViewModel.clearCameraScanFeedback() },
                                        modifier = Modifier.size(20.dp)
                                    ) {
                                        Icon(Icons.Filled.Close, contentDescription = "Dismiss", tint = EmeraldTertiary, modifier = Modifier.size(12.dp))
                                    }
                                }
                            }

                            // The prominent question text display viewport
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(SurfaceContainerLowest)
                                    .border(1.dp, Color.White.copy(alpha = 0.06f), RoundedCornerShape(8.dp))
                                    .padding(12.dp)
                            ) {
                                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text(
                                        text = "TARGET SCANNED QUESTION:",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = EmeraldTertiary,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = socraticState.scannedQuestionText,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = TextHighContrastWhite,
                                        fontWeight = FontWeight.Bold,
                                        lineHeight = 22.sp
                                    )
                                }
                            }

                            // Bottom telemetry & action buttons
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Button(
                                        onClick = {
                                            clipboardManager.setText(AnnotatedString(socraticState.scannedQuestionText))
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = SurfaceContainerHigh),
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                                    ) {
                                        Icon(imageVector = Icons.Filled.ContentCopy, contentDescription = null, tint = TextMutedGrey, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Copy", color = TextMutedGrey, style = MaterialTheme.typography.labelSmall)
                                    }

                                    Button(
                                        onClick = {
                                            aiViewModel.setCustomQuestionInput(socraticState.scannedQuestionText)
                                            aiViewModel.generateSimilarQuestion(socraticState.scannedQuestionText)
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = CyanPrimaryContainer),
                                        shape = RoundedCornerShape(8.dp),
                                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                                    ) {
                                        Icon(imageVector = Icons.Filled.AutoAwesome, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Make Similar Question", color = Color.White, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                                    }
                                }

                                Button(
                                    onClick = { aiViewModel.generateAnotherProblem() },
                                    colors = ButtonDefaults.buttonColors(containerColor = SurfaceContainerHigh),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Icon(imageVector = Icons.Filled.Refresh, contentDescription = null, tint = ElectricCyanPrimary, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Re-scan", color = ElectricCyanPrimary, style = MaterialTheme.typography.labelSmall)
                                }
                            }
                        }
                    }

                    // Mode 2: Sample Board Questions Quick Picker
                    AnimatedVisibility(visible = socraticState.scanMode == "SAMPLE_PICKER") {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(SurfaceContainerLow)
                                .padding(10.dp)
                        ) {
                            Text(
                                text = "SELECT HIGH-YIELD CBSE QUESTION TO SCAN:",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextMutedGrey,
                                fontWeight = FontWeight.Bold
                            )
                            aiViewModel.sampleQuestionsList.forEach { sample ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(SurfaceContainerHigh)
                                        .clickable {
                                            aiViewModel.setScannedQuestion(
                                                text = sample.question,
                                                subject = sample.subject,
                                                source = sample.source
                                            )
                                        }
                                        .padding(8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                                                Text(text = sample.title, style = MaterialTheme.typography.labelSmall, color = ElectricCyanPrimary, fontWeight = FontWeight.Bold)
                                                Text(text = "• ${sample.source}", style = MaterialTheme.typography.labelSmall, color = TextMutedGrey)
                                            }
                                            Text(text = sample.question, style = MaterialTheme.typography.bodySmall, color = TextHighContrastWhite, maxLines = 1)
                                        }
                                        Text(text = "Scan →", style = MaterialTheme.typography.labelSmall, color = EmeraldTertiary, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }

                    // Mode 3: Custom Input Box
                    AnimatedVisibility(visible = socraticState.scanMode == "CUSTOM_INPUT") {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(SurfaceContainerLow)
                                .padding(10.dp)
                        ) {
                            Text(
                                text = "TYPE OR PASTE QUESTION TO SCAN & DUPLICATE:",
                                style = MaterialTheme.typography.labelSmall,
                                color = TextMutedGrey,
                                fontWeight = FontWeight.Bold
                            )
                            var customText by remember { mutableStateOf(socraticState.customQuestionInput) }
                            OutlinedTextField(
                                value = customText,
                                onValueChange = {
                                    customText = it
                                    aiViewModel.setCustomQuestionInput(it)
                                },
                                placeholder = { Text("e.g. Prove that √5 is an irrational number...", color = TextMutedGrey) },
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = ElectricCyanPrimary,
                                    unfocusedBorderColor = GunmetalBorder,
                                    focusedTextColor = TextHighContrastWhite,
                                    unfocusedTextColor = TextHighContrastWhite
                                ),
                                maxLines = 3
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Button(
                                    onClick = {
                                        if (customText.isNotBlank()) {
                                            aiViewModel.setScannedQuestion(
                                                text = customText.trim(),
                                                subject = "Custom Student Problem",
                                                source = "User Scratchpad"
                                            )
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = CyanPrimaryContainer),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text("Apply to Viewport", color = Color.White, style = MaterialTheme.typography.labelSmall)
                                }
                            }
                        }
                    }
                }
            }
        }

        // 2. SIMILAR QUESTION GENERATOR TOOL (NVIDIA DIFFUSION 26B PARALLEL PROBLEM MAKER)
        item {
            val clipboardManager = LocalClipboardManager.current
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceContainer)
                    .border(1.dp, EmeraldTertiary.copy(alpha = 0.35f), RoundedCornerShape(16.dp))
                    .padding(14.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(imageVector = Icons.Filled.AutoAwesome, contentDescription = null, tint = EmeraldTertiary, modifier = Modifier.size(18.dp))
                            Text(
                                text = "Similar Question Generator",
                                style = MaterialTheme.typography.titleMedium,
                                color = TextHighContrastWhite,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(EmeraldTertiary.copy(alpha = 0.15f))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(text = "NVIDIA 26B Diffusion Tool", style = MaterialTheme.typography.labelSmall, color = EmeraldTertiary, fontWeight = FontWeight.Bold)
                        }
                    }

                    Text(
                        text = "Give this AI tool any question to instantly generate an isomorphic 'Twin Problem' with identical concept/blueprint, altered values, and full CBSE marking scheme.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMutedGrey
                    )

                    // Question Input / Preview for Generation
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceContainerLowest)
                            .padding(10.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "QUESTION GIVEN TO AI:",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = ElectricCyanPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                                TextButton(
                                    onClick = {
                                        aiViewModel.setCustomQuestionInput(socraticState.scannedQuestionText)
                                    },
                                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text("Use Scanned Question", color = EmeraldTertiary, style = MaterialTheme.typography.labelSmall)
                                }
                            }
                            Text(
                                text = if (socraticState.customQuestionInput.isNotBlank()) socraticState.customQuestionInput else socraticState.scannedQuestionText,
                                style = MaterialTheme.typography.bodySmall,
                                color = TextHighContrastWhite,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    // Generator Action Button
                    Button(
                        onClick = { aiViewModel.generateSimilarQuestion() },
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldTertiary),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth().height(44.dp)
                    ) {
                        if (socraticState.isGeneratingSimilar) {
                            CircularProgressIndicator(color = ObsidianBlack, modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Generating Similar Twin Problem...", color = ObsidianBlack, fontWeight = FontWeight.Bold)
                        } else {
                            Icon(imageVector = Icons.Filled.AutoAwesome, contentDescription = null, tint = ObsidianBlack, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Generate Similar Question (NVIDIA 26B)", color = ObsidianBlack, fontWeight = FontWeight.Bold)
                        }
                    }

                    // Display Result
                    socraticState.similarQuestionResult?.let { resultText ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(SurfaceContainerLowest)
                                .border(1.dp, EmeraldTertiary.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                                .padding(12.dp)
                        ) {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "GENERATED TWIN PROBLEM & MARKING SCHEME",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = EmeraldTertiary,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                        IconButton(
                                            onClick = { clipboardManager.setText(AnnotatedString(resultText)) },
                                            modifier = Modifier.size(28.dp)
                                        ) {
                                            Icon(imageVector = Icons.Filled.ContentCopy, contentDescription = "Copy", tint = TextMutedGrey, modifier = Modifier.size(16.dp))
                                        }
                                        IconButton(
                                            onClick = { onNavigateToTab(ScreenTab.FOCUS) },
                                            modifier = Modifier.size(28.dp)
                                        ) {
                                            Icon(imageVector = Icons.Filled.Timer, contentDescription = "Practice in Timer", tint = ElectricCyanPrimary, modifier = Modifier.size(16.dp))
                                        }
                                    }
                                }

                                Text(
                                    text = resultText,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextHighContrastWhite,
                                    lineHeight = 19.sp
                                )

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    Button(
                                        onClick = { aiViewModel.generateSimilarQuestion() },
                                        colors = ButtonDefaults.buttonColors(containerColor = SurfaceContainerHigh),
                                        shape = RoundedCornerShape(8.dp)
                                    ) {
                                        Icon(imageVector = Icons.Filled.Refresh, contentDescription = null, tint = EmeraldTertiary, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("Generate Another Twin", color = EmeraldTertiary, style = MaterialTheme.typography.labelSmall)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Student Rough Work Verification Alert
        if (socraticState.step2ErrorDetected) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(SurfaceContainerHigh)
                        .border(1.dp, ErrorRed.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
                        .padding(14.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(imageVector = Icons.Filled.Warning, contentDescription = null, tint = ErrorRed, modifier = Modifier.size(18.dp))
                            Text(
                                text = "Gemini Handwriting Check: Error in Step 2",
                                style = MaterialTheme.typography.titleSmall,
                                color = ErrorRed,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = "\"I looked at line 02 of your scratchpad: you wrote cos A · (1 - sin A) in the denominator. Notice the original problem had (1 + sin A). You accidentally inverted the addition sign when finding the LCM!\"",
                            style = MaterialTheme.typography.bodySmall,
                            color = TextHighContrastWhite,
                            lineHeight = 18.sp
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextButton(onClick = { aiViewModel.dismissErrorAlert() }) {
                                Text("Dismiss Alert", color = TextMutedGrey)
                            }
                            Button(
                                onClick = { aiViewModel.dismissErrorAlert() },
                                colors = ButtonDefaults.buttonColors(containerColor = CyanPrimaryContainer),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text("Recalculate Step 2", color = Color.White, style = MaterialTheme.typography.labelSmall)
                            }
                        }
                    }
                }
            }
        }

        // Socratic Learning Mode & Multi-Path Solver
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceContainer)
                    .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(16.dp))
                    .padding(14.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(imageVector = Icons.Filled.Psychology, contentDescription = null, tint = ElectricCyanPrimary, modifier = Modifier.size(18.dp))
                            Text(
                                text = "Gemini Multi-Path Solver",
                                style = MaterialTheme.typography.titleMedium,
                                color = TextHighContrastWhite,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(SecondaryContainer.copy(alpha = 0.2f))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(text = "3 Perspectives", style = MaterialTheme.typography.labelSmall, color = SoftBlueSecondary)
                        }
                    }

                    // Method Tabs
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf(1 to "Method 1: NCERT Board", 2 to "Method 2: 15s Shortcut", 3 to "Method 3: Flashcard").forEach { (methodNum, methodTitle) ->
                            val isSelected = socraticState.currentMethod == methodNum
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) CyanPrimaryContainer else SurfaceContainerHigh)
                                    .clickable { aiViewModel.setMethod(methodNum) }
                                    .padding(vertical = 8.dp, horizontal = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = methodTitle,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (isSelected) Color.White else TextMutedGrey,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }

                    // Step by step breakdown for Method 1
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        StepItem(
                            stepNum = 1,
                            title = "Divide Numerator & Denominator by cos θ",
                            marks = "+1.0 Mark",
                            content = "LHS = (tan θ - 1 + sec θ) / (tan θ + 1 - sec θ) = (tan θ + sec θ - 1) / (tan θ - sec θ + 1)"
                        )
                        StepItem(
                            stepNum = 2,
                            title = "Substitute Identity 1 = sec²θ - tan²θ",
                            marks = "+1.5 Marks",
                            content = "Replace '1': [(tan θ + sec θ) - (sec²θ - tan²θ)] / (tan θ - sec θ + 1)"
                        )
                        StepItem(
                            stepNum = 3,
                            title = "Factorize & Cancel Identical Term",
                            marks = "+1.0 Mark",
                            content = "= (sec θ + tan θ)[1 - (sec θ - tan θ)] / [1 - sec θ + tan θ] = sec θ + tan θ"
                        )
                        StepItem(
                            stepNum = 4,
                            title = "Conjugate Rationalization • RHS",
                            marks = "+0.5 Mark",
                            content = "= (sec θ + tan θ)(sec θ - tan θ) / (sec θ - tan θ) = 1 / (sec θ - tan θ) = RHS (Hence Proved)"
                        )
                    }
                }
            }
        }

        // Progressive Socratic Hints
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceContainer)
                    .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(16.dp))
                    .padding(14.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Progressive Socratic Hints",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextHighContrastWhite,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = if (socraticState.tier2Unlocked) "2 of 3 Revealed" else "1 of 3 Revealed",
                            style = MaterialTheme.typography.labelSmall,
                            color = EmeraldTertiary
                        )
                    }

                    // Tier 1
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceContainerLow)
                            .padding(10.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "Tier 1: Core Concept & Formula Recall", style = MaterialTheme.typography.titleSmall, color = EmeraldTertiary)
                                Text(text = "Unlocked", style = MaterialTheme.typography.labelSmall, color = EmeraldTertiary)
                            }
                            Text(
                                text = "To simplify compound fractions containing both cos A and (1 + sin A), combine into a common denominator first, then apply fundamental Pythagorean identity sin²A + cos²A = 1.",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextHighContrastWhite
                            )
                        }
                    }

                    // Tier 2
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceContainerLow)
                            .padding(10.dp)
                    ) {
                        if (socraticState.tier2Unlocked) {
                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(text = "Tier 2: Next Logical Step", style = MaterialTheme.typography.titleSmall, color = ElectricCyanPrimary)
                                Text(
                                    text = "Expand (1 + sin A)² into 1 + 2 sin A + sin²A. Group cos²A + sin²A = 1 to get 2 + 2 sin A in the numerator, then factor 2(1 + sin A).",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextHighContrastWhite
                                )
                            }
                        } else {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(text = "Tier 2: Next Logical Step", style = MaterialTheme.typography.titleSmall, color = TextMutedGrey)
                                    Text(text = "-0.5 confidence deduction if revealed", style = MaterialTheme.typography.labelSmall, color = AmberBadge)
                                }
                                Button(
                                    onClick = { aiViewModel.unlockTier2() },
                                    colors = ButtonDefaults.buttonColors(containerColor = SurfaceContainerHigh)
                                ) {
                                    Icon(imageVector = Icons.Filled.Visibility, contentDescription = null, tint = ElectricCyanPrimary, modifier = Modifier.size(14.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Reveal Hint 2", color = ElectricCyanPrimary, style = MaterialTheme.typography.labelSmall)
                                }
                            }
                        }
                    }
                }
            }
        }

        // Gemini Practice Generator
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceContainer)
                    .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(16.dp))
                    .padding(14.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(imageVector = Icons.Filled.AutoAwesome, contentDescription = null, tint = ElectricCyanPrimary, modifier = Modifier.size(18.dp))
                            Text(
                                text = "Gemini Practice Generator",
                                style = MaterialTheme.typography.titleMedium,
                                color = TextHighContrastWhite,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(EmeraldTertiary.copy(alpha = 0.15f))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(text = "Instant AI Variation", style = MaterialTheme.typography.labelSmall, color = EmeraldTertiary)
                        }
                    }

                    // Difficulty selector
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf("Board Standard", "Exemplar", "Adv. Olympiad").forEach { diff ->
                            val isSelected = socraticState.targetDifficulty == diff
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) CyanPrimaryContainer else SurfaceContainerHigh)
                                    .clickable { aiViewModel.setDifficulty(diff) }
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = diff,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (isSelected) Color.White else TextMutedGrey,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }

                    // Generated Problem Box
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceContainerLowest)
                            .padding(12.dp)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = "AI CLONE #1 • TRIG SYMMETRY", style = MaterialTheme.typography.labelSmall, color = ElectricCyanPrimary)
                                Text(text = "Generated just now", style = MaterialTheme.typography.labelSmall, color = TextMutedGrey)
                            }
                            Text(
                                text = socraticState.generatedProblemText,
                                style = MaterialTheme.typography.titleSmall,
                                color = TextHighContrastWhite,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Button(
                        onClick = { aiViewModel.generateAnotherProblem() },
                        colors = ButtonDefaults.buttonColors(containerColor = SurfaceContainerHigh),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth().height(44.dp)
                    ) {
                        if (isGenerating) {
                            CircularProgressIndicator(color = ElectricCyanPrimary, modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Generating Variation...", color = TextHighContrastWhite)
                        } else {
                            Icon(imageVector = Icons.Filled.Refresh, contentDescription = null, tint = ElectricCyanPrimary, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Generate Another Similar Problem", color = TextHighContrastWhite)
                        }
                    }
                }
            }
        }

        // Ask Gemini Follow-up input & chat history
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceContainer)
                    .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(16.dp))
                    .padding(14.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Ask Gemini Follow-up",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextHighContrastWhite,
                        fontWeight = FontWeight.SemiBold
                    )

                    // History messages
                    socraticState.followUpMessages.forEach { (sender, text) ->
                        val isGemini = sender == "Gemini"
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isGemini) SurfaceContainerLow else SurfaceContainerHigh)
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = if (isGemini) "🤖 Gemini:" else "👤 You:",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isGemini) ElectricCyanPrimary else SoftBlueSecondary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = text,
                                style = MaterialTheme.typography.bodySmall,
                                color = TextHighContrastWhite,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    // Input field
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = followUpInput,
                            onValueChange = { followUpInput = it },
                            placeholder = { Text("Ask follow-up or 'Explain step 2 simpler'") },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = TextHighContrastWhite,
                                unfocusedTextColor = TextHighContrastWhite,
                                focusedBorderColor = ElectricCyanPrimary,
                                unfocusedBorderColor = SurfaceContainerHighest,
                                focusedContainerColor = SurfaceContainerLowest,
                                unfocusedContainerColor = SurfaceContainerLowest
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.weight(1f)
                        )

                        Button(
                            onClick = {
                                if (followUpInput.isNotBlank()) {
                                    aiViewModel.askFollowUp(followUpInput)
                                    followUpInput = ""
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = CyanPrimaryContainer),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 14.dp)
                        ) {
                            Icon(imageVector = Icons.Filled.Send, contentDescription = "Send", tint = Color.White)
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

@Composable
private fun StepItem(
    stepNum: Int,
    title: String,
    marks: String,
    content: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(SurfaceContainerLow)
            .padding(10.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Step $stepNum: $title",
                    style = MaterialTheme.typography.titleSmall,
                    color = ElectricCyanPrimary,
                    fontWeight = FontWeight.SemiBold
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(EmeraldTertiary.copy(alpha = 0.15f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(text = marks, style = MaterialTheme.typography.labelSmall, color = EmeraldTertiary)
                }
            }
            Text(
                text = content,
                style = MaterialTheme.typography.labelSmall,
                color = TextHighContrastWhite
            )
        }
    }
}

@Composable
private fun NvidiaEngineBanner(
    activeEngine: AiEngineType,
    onToggleEngine: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isNvidia = activeEngine == AiEngineType.NVIDIA_DIFFUSION_26B
    val engineColor = if (isNvidia) EmeraldTertiary else ArcReactorCyan

    HudCornerBox(
        cornerColor = engineColor,
        fillColor = GunmetalCard,
        borderColor = engineColor.copy(alpha = 0.45f),
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
                            .size(32.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(engineColor.copy(alpha = 0.2f))
                            .border(1.dp, engineColor.copy(alpha = 0.6f), RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.AutoAwesome,
                            contentDescription = null,
                            tint = engineColor,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = if (isNvidia) "NVIDIA DIFFUSION 26B" else "GEMINI 3.8 FLASH",
                                style = MaterialTheme.typography.titleSmall,
                                color = TextHighContrastWhite,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(engineColor.copy(alpha = 0.2f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = if (isNvidia) "PARALLEL TOKENS" else "MULTIMODAL",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = engineColor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 9.sp
                                )
                            }
                        }
                        Text(
                            text = if (isNvidia)
                                "Diffusion-based 26B parameter LLM via NVIDIA NIM API"
                            else
                                "Google DeepMind Multimodal Socratic Engine",
                            style = MaterialTheme.typography.labelSmall,
                            color = TextMutedGrey,
                            fontSize = 10.sp
                        )
                    }
                }

                // Switch Engine Button
                Button(
                    onClick = onToggleEngine,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GunmetalCardHigh,
                        contentColor = engineColor
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .height(34.dp)
                        .border(1.dp, engineColor.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                        .testTag("toggle_ai_engine_btn")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Sync,
                            contentDescription = "Switch Engine",
                            tint = engineColor,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = if (isNvidia) "GEMINI" else "NVIDIA 26B",
                            style = MaterialTheme.typography.labelSmall,
                            color = engineColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    }
                }
            }

            // Specs bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .background(ObsidianBlack.copy(alpha = 0.6f))
                    .border(0.5.dp, GunmetalBorder, RoundedCornerShape(6.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isNvidia)
                        "⚡ Discrete Block Diffusion (256-token canvas blocks)"
                    else
                        "✨ Socratic 3-Tier Progressive Disclosure Mode",
                    style = MaterialTheme.typography.labelSmall,
                    color = TextMutedGrey,
                    fontSize = 10.sp
                )
                Text(
                    text = if (isNvidia) "vLLM-0.21.0" else "Firebase AI",
                    style = MaterialTheme.typography.labelSmall,
                    color = engineColor,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 10.sp
                )
            }
        }
    }
}

@Composable
fun LiveCameraScannerViewport(
    aiViewModel: GeminiAiViewModel,
    socraticState: com.example.ui.viewmodel.SocraticState,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraControl = remember { mutableStateOf<CameraControl?>(null) }
    val previewView = remember {
        PreviewView(context).apply {
            implementationMode = PreviewView.ImplementationMode.COMPATIBLE
        }
    }

    DisposableEffect(lifecycleOwner, socraticState.useFrontCamera) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        var cameraProvider: ProcessCameraProvider? = null

        cameraProviderFuture.addListener({
            try {
                cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }
                val cameraSelector = if (socraticState.useFrontCamera) {
                    CameraSelector.DEFAULT_FRONT_CAMERA
                } else {
                    CameraSelector.DEFAULT_BACK_CAMERA
                }
                cameraProvider?.unbindAll()
                val camera = cameraProvider?.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview
                )
                cameraControl.value = camera?.cameraControl
                try {
                    camera?.cameraControl?.enableTorch(socraticState.isFlashlightEnabled)
                } catch (_: Exception) {}
            } catch (e: Exception) {
                Log.e("LiveCameraViewport", "Binding camera preview failed", e)
            }
        }, ContextCompat.getMainExecutor(context))

        onDispose {
            try {
                cameraProvider?.unbindAll()
            } catch (_: Exception) {}
        }
    }

    LaunchedEffect(socraticState.isFlashlightEnabled) {
        try {
            cameraControl.value?.enableTorch(socraticState.isFlashlightEnabled)
        } catch (_: Exception) {}
    }

    val infiniteTransition = rememberInfiniteTransition(label = "laserScan")
    val laserProgress by infiniteTransition.animateFloat(
        initialValue = 0.05f,
        targetValue = 0.95f,
        animationSpec = infiniteRepeatable(
            animation = tween(2200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "laserLine"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(230.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Black)
            .border(1.5.dp, EmeraldTertiary.copy(alpha = 0.8f), RoundedCornerShape(10.dp))
    ) {
        AndroidView(
            factory = { previewView },
            modifier = Modifier.fillMaxSize()
        )

        // Targeting reticle overlay with animated laser
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 3.dp.toPx()
            val bracketLength = 24.dp.toPx()
            val cyan = Color(0xFF00E5FF)
            val emerald = Color(0xFF00E676)

            // Top-left
            drawLine(emerald, Offset(14.dp.toPx(), 14.dp.toPx()), Offset(14.dp.toPx() + bracketLength, 14.dp.toPx()), strokeWidth)
            drawLine(emerald, Offset(14.dp.toPx(), 14.dp.toPx()), Offset(14.dp.toPx(), 14.dp.toPx() + bracketLength), strokeWidth)

            // Top-right
            drawLine(emerald, Offset(size.width - 14.dp.toPx(), 14.dp.toPx()), Offset(size.width - 14.dp.toPx() - bracketLength, 14.dp.toPx()), strokeWidth)
            drawLine(emerald, Offset(size.width - 14.dp.toPx(), 14.dp.toPx()), Offset(size.width - 14.dp.toPx(), 14.dp.toPx() + bracketLength), strokeWidth)

            // Bottom-left
            drawLine(emerald, Offset(14.dp.toPx(), size.height - 14.dp.toPx()), Offset(14.dp.toPx() + bracketLength, size.height - 14.dp.toPx()), strokeWidth)
            drawLine(emerald, Offset(14.dp.toPx(), size.height - 14.dp.toPx()), Offset(14.dp.toPx(), size.height - 14.dp.toPx() - bracketLength), strokeWidth)

            // Bottom-right
            drawLine(emerald, Offset(size.width - 14.dp.toPx(), size.height - 14.dp.toPx()), Offset(size.width - 14.dp.toPx() - bracketLength, size.height - 14.dp.toPx()), strokeWidth)
            drawLine(emerald, Offset(size.width - 14.dp.toPx(), size.height - 14.dp.toPx()), Offset(size.width - 14.dp.toPx(), size.height - 14.dp.toPx() - bracketLength), strokeWidth)

            // Animated Laser Scanner Line
            val laserY = size.height * laserProgress
            drawLine(
                color = cyan.copy(alpha = 0.85f),
                start = Offset(18.dp.toPx(), laserY),
                end = Offset(size.width - 18.dp.toPx(), laserY),
                strokeWidth = 2.5.dp.toPx()
            )
        }

        // Top telemetry & lens controls inside camera view
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.Black.copy(alpha = 0.65f))
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(EmeraldTertiary)
                    )
                    Text(
                        text = "LIVE CAMERA FEED • 1080p",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                // Torch toggle
                IconButton(
                    onClick = { aiViewModel.toggleFlashlight() },
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.65f))
                ) {
                    Icon(
                        imageVector = if (socraticState.isFlashlightEnabled) Icons.Filled.FlashOn else Icons.Filled.FlashOff,
                        contentDescription = "Toggle Torch",
                        tint = if (socraticState.isFlashlightEnabled) StarkGold else Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }

                // Lens switch
                IconButton(
                    onClick = { aiViewModel.toggleCameraLens() },
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.65f))
                ) {
                    Icon(
                        imageVector = Icons.Filled.Cameraswitch,
                        contentDescription = "Switch Lens",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        // Bottom Capture Shutter Button
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp)
        ) {
            Button(
                onClick = { aiViewModel.captureAndScanFromCamera() },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldTertiary),
                shape = RoundedCornerShape(20.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.CameraAlt,
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Scan Question (OCR)",
                    color = Color.Black,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun MockScannerViewport(
    aiViewModel: GeminiAiViewModel,
    onEnableLiveCamera: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "laserScanMock")
    val laserProgress by infiniteTransition.animateFloat(
        initialValue = 0.1f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(2600, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "laserLineMock"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(SurfaceContainerLowest)
            .border(1.dp, ElectricCyanPrimary.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 2.5.dp.toPx()
            val bracketLength = 20.dp.toPx()
            val cyan = Color(0xFF00E5FF)

            // Top-left
            drawLine(cyan, Offset(12.dp.toPx(), 12.dp.toPx()), Offset(12.dp.toPx() + bracketLength, 12.dp.toPx()), strokeWidth)
            drawLine(cyan, Offset(12.dp.toPx(), 12.dp.toPx()), Offset(12.dp.toPx(), 12.dp.toPx() + bracketLength), strokeWidth)

            // Top-right
            drawLine(cyan, Offset(size.width - 12.dp.toPx(), 12.dp.toPx()), Offset(size.width - 12.dp.toPx() - bracketLength, 12.dp.toPx()), strokeWidth)
            drawLine(cyan, Offset(size.width - 12.dp.toPx(), 12.dp.toPx()), Offset(size.width - 12.dp.toPx(), 12.dp.toPx() + bracketLength), strokeWidth)

            // Bottom-left
            drawLine(cyan, Offset(12.dp.toPx(), size.height - 12.dp.toPx()), Offset(12.dp.toPx() + bracketLength, size.height - 12.dp.toPx()), strokeWidth)
            drawLine(cyan, Offset(12.dp.toPx(), size.height - 12.dp.toPx()), Offset(12.dp.toPx(), size.height - 12.dp.toPx() - bracketLength), strokeWidth)

            // Bottom-right
            drawLine(cyan, Offset(size.width - 12.dp.toPx(), size.height - 12.dp.toPx()), Offset(size.width - 12.dp.toPx() - bracketLength, size.height - 12.dp.toPx()), strokeWidth)
            drawLine(cyan, Offset(size.width - 12.dp.toPx(), size.height - 12.dp.toPx()), Offset(size.width - 12.dp.toPx(), size.height - 12.dp.toPx() - bracketLength), strokeWidth)

            // Laser bar
            val laserY = size.height * laserProgress
            drawLine(
                color = cyan.copy(alpha = 0.6f),
                start = Offset(16.dp.toPx(), laserY),
                end = Offset(size.width - 16.dp.toPx(), laserY),
                strokeWidth = 2.dp.toPx()
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(SurfaceContainerHigh)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "MOCK SCANNER UI",
                        style = MaterialTheme.typography.labelSmall,
                        color = TextMutedGrey,
                        fontSize = 9.sp
                    )
                }

                TextButton(
                    onClick = onEnableLiveCamera,
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 8.dp, vertical = 0.dp)
                ) {
                    Icon(Icons.Filled.Videocam, contentDescription = null, tint = EmeraldTertiary, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Switch to Live Camera", color = EmeraldTertiary, style = MaterialTheme.typography.labelSmall)
                }
            }

            Text(
                text = "Align textbook question inside viewfinder reticle",
                style = MaterialTheme.typography.bodySmall,
                color = TextMutedGrey
            )

            Button(
                onClick = { aiViewModel.captureAndScanFromCamera() },
                colors = ButtonDefaults.buttonColors(containerColor = CyanPrimaryContainer),
                shape = RoundedCornerShape(8.dp),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 14.dp, vertical = 4.dp)
            ) {
                Icon(imageVector = Icons.Filled.CropFree, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Scan Frame (Mock)", color = Color.White, style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@Composable
fun CameraPermissionPromptCard(
    onRequestPermission: () -> Unit,
    onFallbackToMock: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(SurfaceContainerLowest)
            .border(1.dp, StarkGold.copy(alpha = 0.6f), RoundedCornerShape(10.dp))
            .padding(14.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(StarkGold.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.CameraAlt,
                    contentDescription = "Camera",
                    tint = StarkGold,
                    modifier = Modifier.size(24.dp)
                )
            }
            Text(
                text = "Camera Permission Required",
                style = MaterialTheme.typography.titleSmall,
                color = TextHighContrastWhite,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Grant camera access to stream live video and scan CBSE textbook questions directly into NVIDIA Diffusion 26B.",
                style = MaterialTheme.typography.bodySmall,
                color = TextMutedGrey,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = onRequestPermission,
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldTertiary),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text("Grant Permission", color = Color.Black, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                }
                Button(
                    onClick = onFallbackToMock,
                    colors = ButtonDefaults.buttonColors(containerColor = SurfaceContainerHigh),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text("Use Mock UI", color = TextMutedGrey, style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}
