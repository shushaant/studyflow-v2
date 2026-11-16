package com.example

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.testTag
import com.example.ui.theme.ArcReactorCyan
import com.example.ui.theme.ArcReactorGlow
import com.example.ui.theme.GunmetalCard
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.StarkGold
import com.example.ui.components.AppTopHeader
import com.example.ui.components.ScreenTab
import com.example.ui.components.SettingsModal
import com.example.ui.components.StudyFlowBottomBar
import com.example.ui.components.ToastNotification
import com.example.ui.screens.AiTutorScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.DrillScreen
import com.example.ui.screens.FocusTimerScreen
import com.example.ui.screens.SavedMemoryScreen
import com.example.ui.screens.SyllabusScreen
import com.example.ui.theme.DeepMatteBackground
import com.example.ui.theme.StudyFlowTheme
import com.example.ui.viewmodel.GeminiAiViewModel
import com.example.ui.viewmodel.StudyFlowViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.appcheck.recaptcha.RecaptchaAppCheckProviderFactory

private const val TAG = "MainActivity"
private const val DEBUG_TOKEN_EXTRA = "FIREBASE_APPCHECK_DEBUG_TOKEN"
private const val DEBUG_STORE_PREFIX = "com.google.firebase.appcheck.debug.store."
private const val DEBUG_SECRET_KEY = "com.google.firebase.appcheck.debug.DEBUG_SECRET"
private const val RECAPTCHA_SITE_KEY_RES = "recaptcha_site_key"

class MainActivity : ComponentActivity() {

    private val studyFlowViewModel: StudyFlowViewModel by viewModels()
    private val geminiAiViewModel: GeminiAiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureAppCheck(this, intent)
        enableEdgeToEdge()

        setContent {
            val isLightMode by studyFlowViewModel.isLightMode.collectAsState()
            StudyFlowTheme(isLightMode = isLightMode) {
                MainAppContent(
                    studyFlowViewModel = studyFlowViewModel,
                    geminiAiViewModel = geminiAiViewModel,
                    isLightMode = isLightMode
                )
            }
        }
    }

    private fun configureAppCheck(context: Context, intent: Intent?) {
        try {
            if (FirebaseApp.getApps(context).isEmpty()) {
                FirebaseApp.initializeApp(context)
            }
            if (FirebaseApp.getApps(context).isEmpty()) {
                Log.i(TAG, "FirebaseApp is not configured; skipping Firebase App Check.")
                return
            }

            if (BuildConfig.DEBUG) {
                val debugToken = intent?.getStringExtra(DEBUG_TOKEN_EXTRA)
                if (!debugToken.isNullOrEmpty()) {
                    val persistenceKey = FirebaseApp.getInstance().persistenceKey
                    val prefs = context.getSharedPreferences(
                        "$DEBUG_STORE_PREFIX$persistenceKey",
                        Context.MODE_PRIVATE
                    )
                    prefs.edit()
                        .putString(DEBUG_SECRET_KEY, debugToken)
                        .commit()
                    Log.d(TAG, "App Check debug token stored successfully.")
                }

                FirebaseAppCheck.getInstance().installAppCheckProviderFactory(
                    DebugAppCheckProviderFactory.getInstance()
                )
                Log.d(TAG, "DebugAppCheckProviderFactory installed.")
            } else {
                val resId = context.resources.getIdentifier(RECAPTCHA_SITE_KEY_RES, "string", context.packageName)
                if (resId != 0) {
                    val siteKey = context.getString(resId)
                    if (siteKey.isNotBlank()) {
                        FirebaseAppCheck.getInstance().installAppCheckProviderFactory(
                            RecaptchaAppCheckProviderFactory.getInstance(siteKey)
                        )
                    }
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, "Firebase App Check initialization bypassed: ${e.message}")
        }
    }
}

@Composable
fun MainAppContent(
    studyFlowViewModel: StudyFlowViewModel,
    geminiAiViewModel: GeminiAiViewModel,
    isLightMode: Boolean = false
) {
    var currentTab by remember { mutableStateOf(ScreenTab.DASHBOARD) }
    val toastMessage by studyFlowViewModel.toastMessage.collectAsState()
    val streakRecord by studyFlowViewModel.streakRecord.collectAsState()

    val isSettingsModalOpen by studyFlowViewModel.isSettingsModalOpen.collectAsState()
    val subjectsWithStats by studyFlowViewModel.subjectsWithStats.collectAsState()
    val dailyPomodoroTarget by studyFlowViewModel.dailyPomodoroTarget.collectAsState()

    val isFridayModalOpen by studyFlowViewModel.isFridayModalOpen.collectAsState()
    val fridaySpeechText by studyFlowViewModel.fridayVoiceManager.lastSpeechText.collectAsState()
    val isSpeaking by studyFlowViewModel.fridayVoiceManager.isSpeaking.collectAsState()

    val isDeadpoolPopupVisible by studyFlowViewModel.isDeadpoolPopupVisible.collectAsState()
    val deadpoolQuip by studyFlowViewModel.deadpoolQuip.collectAsState()
    val timerState by studyFlowViewModel.timerState.collectAsState()

    // Screen-Wake Lock: Keep mobile screen awake while Pomodoro focus timer is actively running across all tabs
    val currentView = LocalView.current
    DisposableEffect(timerState.isRunning) {
        currentView.keepScreenOn = timerState.isRunning
        onDispose {
            currentView.keepScreenOn = false
        }
    }

    // BackHandler: Return to Dashboard if on a sub-tab
    BackHandler(enabled = currentTab != ScreenTab.DASHBOARD) {
        currentTab = ScreenTab.DASHBOARD
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            AppTopHeader(
                currentTab = currentTab,
                streakDays = streakRecord?.currentStreakDays ?: 14,
                onOpenSettings = { studyFlowViewModel.openSettingsModal() },
                onToggleTheme = { studyFlowViewModel.toggleThemeMode() },
                onOpenFriday = { studyFlowViewModel.openFridayModal() },
                onTriggerDeadpool = { studyFlowViewModel.triggerDeadpoolQuip() }
            )
        },
        bottomBar = {
            StudyFlowBottomBar(
                currentTab = currentTab,
                onTabSelected = { newTab -> currentTab = newTab }
            )
        },
        floatingActionButton = {
            FloatingArcReactorVoiceButton(
                onOpenFriday = { studyFlowViewModel.openFridayModal() }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (currentTab) {
                ScreenTab.DASHBOARD -> DashboardScreen(
                    viewModel = studyFlowViewModel,
                    onNavigateToTab = { currentTab = it }
                )
                ScreenTab.SYLLABUS -> SyllabusScreen(
                    viewModel = studyFlowViewModel,
                    onNavigateToTab = { currentTab = it }
                )
                ScreenTab.MEMORY -> SavedMemoryScreen(
                    viewModel = studyFlowViewModel,
                    onNavigateToTab = { currentTab = it }
                )
                ScreenTab.FOCUS -> FocusTimerScreen(
                    viewModel = studyFlowViewModel
                )
                ScreenTab.AI_TUTOR -> AiTutorScreen(
                    aiViewModel = geminiAiViewModel,
                    onNavigateToTab = { currentTab = it }
                )
            }

            // Settings & Appearance Modal (User-controlled theme toggle switch)
            if (isSettingsModalOpen) {
                SettingsModal(
                    isOpen = isSettingsModalOpen,
                    isLightMode = isLightMode,
                    onToggleTheme = { studyFlowViewModel.setThemeMode(it) },
                    onClose = { studyFlowViewModel.closeSettingsModal() },
                    totalChaptersCompleted = subjectsWithStats.sumOf { it.completedCount },
                    totalChapters = subjectsWithStats.sumOf { it.totalCount },
                    dailyPomodoroTarget = dailyPomodoroTarget,
                    onSetDailyPomodoroTarget = { studyFlowViewModel.setDailyPomodoroTarget(it) },
                    streakDays = streakRecord?.currentStreakDays ?: 14
                )
            }

            // F.R.I.D.A.Y. & Shuri Voice Assistant Modal
            if (isFridayModalOpen) {
                com.example.ui.components.FridayAssistantModal(
                    speechText = fridaySpeechText,
                    isSpeaking = isSpeaking,
                    onClose = { studyFlowViewModel.closeFridayModal() },
                    onSendCommand = { command ->
                        studyFlowViewModel.executeFridayCommand(command)
                    },
                    onReplayVoice = {
                        studyFlowViewModel.replayFridaySpeech()
                    }
                )
            }

            // Deadpool 4th-Wall Quip Popup
            if (isDeadpoolPopupVisible) {
                com.example.ui.components.DeadpoolQuipOverlay(
                    quip = deadpoolQuip,
                    onDismiss = { studyFlowViewModel.dismissDeadpoolPopup() }
                )
            }

            // Toast overlay
            ToastNotification(
                message = toastMessage,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            )
        }
    }
}

/**
 * Floating Arc Reactor Voice Assistant Button
 * Styled as a pulsing Stark Arc Reactor core that summons F.R.I.D.A.Y. from any tab.
 */
@Composable
fun FloatingArcReactorVoiceButton(
    onOpenFriday: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "arc_glow")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.94f,
        targetValue = 1.06f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    FloatingActionButton(
        onClick = onOpenFriday,
        shape = CircleShape,
        containerColor = ObsidianBlack,
        contentColor = ArcReactorCyan,
        modifier = modifier
            .size(56.dp)
            .scale(pulseScale)
            .border(2.dp, ArcReactorCyan, CircleShape)
            .shadow(12.dp, CircleShape, spotColor = ArcReactorCyan)
            .testTag("floating_arc_reactor_voice_btn")
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(ArcReactorCyan.copy(alpha = 0.2f))
                .border(1.dp, StarkGold.copy(alpha = 0.6f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Mic,
                contentDescription = "Summon F.R.I.D.A.Y.",
                tint = ArcReactorCyan,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
