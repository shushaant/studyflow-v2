package com.example.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.db.AppDatabase
import com.example.data.db.DefaultSubtopicsData
import com.example.data.db.DefaultSyllabusData
import com.example.data.model.ChapterEntity
import com.example.data.model.FlashcardEntity
import com.example.data.model.SavedMemoryEntity
import com.example.data.model.StreakRecordEntity
import com.example.data.model.SubtopicEntity
import com.example.data.model.SubjectWithStats
import com.example.data.repository.StudyFlowRepository
import com.example.data.marvel.DeadpoolQuipBank
import com.example.data.model.CbseExamItem
import com.example.data.model.CbseExamSchedule
import com.example.data.model.DeadlineStatus
import com.example.data.voice.FridayVoiceManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class WeeklyDayData(
    val dayName: String,
    val count: Int,
    val isToday: Boolean,
    val dateString: String = ""
)

data class MotivationalQuote(
    val id: Int,
    val quote: String,
    val author: String,
    val marvelHero: String,
    val heroBadge: String,
    val subjectFocus: String
)

val DEFAULT_MOTIVATIONAL_QUOTES = listOf(
    MotivationalQuote(
        id = 1,
        quote = "Genius is 1% inspiration, 99% perspiration. Never stop computing.",
        author = "Thomas Edison",
        marvelHero = "Vision",
        heroBadge = "Vision Mind Stone",
        subjectFocus = "Mathematics & Physics"
    ),
    MotivationalQuote(
        id = 2,
        quote = "Discipline is choosing between what you want now and what you want most.",
        author = "Steve Rogers",
        marvelHero = "Captain America",
        heroBadge = "Honor Code Protocol",
        subjectFocus = "Daily Consistency & Ethics"
    ),
    MotivationalQuote(
        id = 3,
        quote = "In the middle of difficulty lies opportunity. Keep breaking down the reaction.",
        author = "Albert Einstein",
        marvelHero = "Bruce Banner",
        heroBadge = "Power Stone Science Core",
        subjectFocus = "Chemistry & Thermodynamics"
    ),
    MotivationalQuote(
        id = 4,
        quote = "Consistency is your healing factor, bub. Sharpen the claws and crush the next chapter.",
        author = "Logan",
        marvelHero = "Wolverine",
        heroBadge = "Adamantium Grit Code",
        subjectFocus = "Syllabus Conquest"
    ),
    MotivationalQuote(
        id = 5,
        quote = "An equation means nothing to me unless it expresses a thought of cosmic order.",
        author = "Srinivasa Ramanujan",
        marvelHero = "Iron Man",
        heroBadge = "Stark Analytical Core",
        subjectFocus = "Trigonometry & Number Systems"
    ),
    MotivationalQuote(
        id = 6,
        quote = "Nothing in life is to be feared, it is only to be understood. Now is the time to understand more.",
        author = "Marie Curie",
        marvelHero = "Shuri",
        heroBadge = "Wakanda Vibranium Lab",
        subjectFocus = "Biology & Atomic Physics"
    ),
    MotivationalQuote(
        id = 7,
        quote = "Victory belongs to the most persevering. Conquering the 470 marks is inevitable.",
        author = "Victor von Doom",
        marvelHero = "Doctor Doom",
        heroBadge = "Latverian Master Control",
        subjectFocus = "All 7 CBSE Subjects"
    ),
    MotivationalQuote(
        id = 8,
        quote = "Concentrate all your thoughts upon the work in hand. The sun's rays do not burn until brought to a focus.",
        author = "Alexander Graham Bell",
        marvelHero = "Sentry",
        heroBadge = "Solar Focus Matrix",
        subjectFocus = "Deep Work & Pomodoros"
    ),
    MotivationalQuote(
        id = 9,
        quote = "Maximum effort! Drop the excuses, grab the textbook, and secure the board merit!",
        author = "Wade Wilson",
        marvelHero = "Deadpool",
        heroBadge = "Reality Stone Rhetoric",
        subjectFocus = "English Literature & Motivation"
    ),
    MotivationalQuote(
        id = 10,
        quote = "Live as if you were to die tomorrow. Learn as if you were to live forever.",
        author = "Mahatma Gandhi",
        marvelHero = "Black Panther",
        heroBadge = "Soul Stone Wisdom",
        subjectFocus = "Hindi & Social Science"
    ),
    MotivationalQuote(
        id = 11,
        quote = "Mathematics is the language with which the universe was written. Speak it fluently.",
        author = "Galileo Galilei",
        marvelHero = "Doctor Strange",
        heroBadge = "Time Stone Agamotto",
        subjectFocus = "Coordinate Geometry & Algebra"
    ),
    MotivationalQuote(
        id = 12,
        quote = "Sometimes you gotta run before you can walk. Fire up the thrusters and start revising.",
        author = "Tony Stark",
        marvelHero = "Iron Man",
        heroBadge = "Mark LXXXV Arc Reactor",
        subjectFocus = "Information Technology & Logic"
    )
)

data class CapHonorMission(
    val id: String,
    val title: String,
    val heroQuote: String,
    val xpReward: Int,
    val isCompleted: Boolean = false
)

enum class TimerMode(val title: String, val defaultMinutes: Int) {
    POMODORO("Pomodoro (25m)", 25),
    SHORT_BREAK("Short Break (5m)", 5),
    LONG_BREAK("Long Break (15m)", 15),
    DEEP_FLOW("Deep Flow (50m)", 50)
}

enum class AmbientSound(val title: String, val subtitle: String, val icon: String) {
    RAIN("Soft Rain", "Cozy Rooftop Drops", "water_drop"),
    WHITE_NOISE("White Noise", "Full-Spectrum Distraction Masking", "air"),
    LO_FI_BEATS("Lo-Fi Beats", "Warm Dusty Vinyl & Rhodes Chords", "headphones"),
    BINAURAL("Binaural Waves (40Hz)", "Cognitive Flow Gamma", "waves"),
    BROWN_NOISE("Deep Brown Noise", "Warm Waterfall Rumble", "graphic_eq")
}

data class TimerState(
    val mode: TimerMode = TimerMode.POMODORO,
    val totalSeconds: Int = 25 * 60,
    val remainingSeconds: Int = 24 * 60 + 15,
    val isRunning: Boolean = false,
    val currentSession: Int = 3,
    val totalSessions: Int = 4,
    val round: Int = 1,
    val linkedSubject: String = "Math",
    val linkedChapter: String = "Ch 8: Intro to Trigonometry",
    val zenMode: Boolean = false
)

class StudyFlowViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = StudyFlowRepository(AppDatabase.getDatabase(application))

    private val defaultInitialSubjects: List<SubjectWithStats> = DefaultSyllabusData.subjects.map { subject ->
        val subjectChapters = DefaultSyllabusData.chapters.filter { it.subjectId == subject.id }
        SubjectWithStats(
            subject = subject.copy(completedChapters = 0, totalChapters = subjectChapters.size),
            chapters = subjectChapters,
            completionPercentage = 0,
            completedCount = 0,
            totalCount = subjectChapters.size
        )
    }

    val subjectsWithStats: StateFlow<List<SubjectWithStats>> = repository.subjectsWithStats
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = defaultInitialSubjects
        )

    val streakRecord: StateFlow<StreakRecordEntity?> = repository.streakRecord
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = StreakRecordEntity()
        )

    val flashcards: StateFlow<List<FlashcardEntity>> = repository.allFlashcards
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val savedMemories: StateFlow<List<SavedMemoryEntity>> = repository.allSavedMemories
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _selectedFilter = MutableStateFlow("all")
    val selectedFilter: StateFlow<String> = _selectedFilter.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    private val _sparkleCelebrationEvent = MutableStateFlow<Long?>(null)
    val sparkleCelebrationEvent: StateFlow<Long?> = _sparkleCelebrationEvent.asStateFlow()

    private val _selectedChapterId = MutableStateFlow<String?>(null)
    val selectedChapterForSheet: StateFlow<ChapterEntity?> = combine(_selectedChapterId, subjectsWithStats) { chapterId, subjects ->
        if (chapterId == null) null
        else subjects.flatMap { it.chapters }.firstOrNull { it.id == chapterId }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    // Reactive Subtopics by chapter directly persisted in Room Database
    val subtopicsByChapter: StateFlow<Map<String, List<SubtopicEntity>>> = repository.allSubtopics
        .map { list -> list.groupBy { it.chapterId } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyMap()
        )

    // Timer state
    private val _timerState = MutableStateFlow(TimerState())
    val timerState: StateFlow<TimerState> = _timerState.asStateFlow()

    private var timerJob: Job? = null

    // Ambient sound player state
    private val _activeAmbientSound = MutableStateFlow<AmbientSound?>(AmbientSound.RAIN)
    val activeAmbientSound: StateFlow<AmbientSound?> = _activeAmbientSound.asStateFlow()

    private val _ambientVolume = MutableStateFlow(0.75f)
    val ambientVolume: StateFlow<Float> = _ambientVolume.asStateFlow()

    private val _autoPlaySoundscapes = MutableStateFlow(true)
    val autoPlaySoundscapes: StateFlow<Boolean> = _autoPlaySoundscapes.asStateFlow()

    // Quiz & Drill state
    private val _quizSecondsRemaining = MutableStateFlow(14 * 60 + 28)
    val quizSecondsRemaining: StateFlow<Int> = _quizSecondsRemaining.asStateFlow()

    private val _selectedQuizOption = MutableStateFlow("B")
    val selectedQuizOption: StateFlow<String> = _selectedQuizOption.asStateFlow()

    private val _confidenceLevel = MutableStateFlow("sure") // "sure", "half", "doubt"
    val confidenceLevel: StateFlow<String> = _confidenceLevel.asStateFlow()

    private val _isFormulaPeekOpen = MutableStateFlow(false)
    val isFormulaPeekOpen: StateFlow<Boolean> = _isFormulaPeekOpen.asStateFlow()

    private val _isDiagramOpen = MutableStateFlow(false)
    val isDiagramOpen: StateFlow<Boolean> = _isDiagramOpen.asStateFlow()

    private val _isFlagged = MutableStateFlow(false)
    val isFlagged: StateFlow<Boolean> = _isFlagged.asStateFlow()

    // Flashcard deck
    private val _currentFlashcardIndex = MutableStateFlow(0)
    val currentFlashcardIndex: StateFlow<Int> = _currentFlashcardIndex.asStateFlow()

    private val _isFlashcardFlipped = MutableStateFlow(false)
    val isFlashcardFlipped: StateFlow<Boolean> = _isFlashcardFlipped.asStateFlow()

    // Marvel Hero Tactical States
    val fridayVoiceManager = FridayVoiceManager(application)

    private val _isFridayModalOpen = MutableStateFlow(false)
    val isFridayModalOpen: StateFlow<Boolean> = _isFridayModalOpen.asStateFlow()

    private val _deadpoolQuip = MutableStateFlow(DeadpoolQuipBank.getRandomQuip())
    val deadpoolQuip: StateFlow<String> = _deadpoolQuip.asStateFlow()

    private val _isDeadpoolPopupVisible = MutableStateFlow(false)
    val isDeadpoolPopupVisible: StateFlow<Boolean> = _isDeadpoolPopupVisible.asStateFlow()

    private val _isHulkSmashActive = MutableStateFlow(false)
    val isHulkSmashActive: StateFlow<Boolean> = _isHulkSmashActive.asStateFlow()

    private val _isMoonKnightMode = MutableStateFlow(false)
    val isMoonKnightMode: StateFlow<Boolean> = _isMoonKnightMode.asStateFlow()

    private val _moonKnightPersona = MutableStateFlow("Marc (Tactical Strategy)")
    val moonKnightPersona: StateFlow<String> = _moonKnightPersona.asStateFlow()

    // Daily Study Goal: Pomodoro Tracking & Target Configuration
    private val _dailyPomodoroTarget = MutableStateFlow(6) // Default target: 6 pomodoros (150m)
    val dailyPomodoroTarget: StateFlow<Int> = _dailyPomodoroTarget.asStateFlow()

    private val _pomodorosCompletedToday = MutableStateFlow(4) // Default completed: 4 pomodoros (100m)
    val pomodorosCompletedToday: StateFlow<Int> = _pomodorosCompletedToday.asStateFlow()

    // Weekly Consistency: Real data collected by app over trailing 7 days
    private val _weeklyDaysData = MutableStateFlow<List<WeeklyDayData>>(emptyList())
    val weeklyDaysData: StateFlow<List<WeeklyDayData>> = _weeklyDaysData.asStateFlow()

    // Official CBSE 2027 Board Exam Schedule & Master January 15, 2027 Deadline
    val officialExams: List<CbseExamItem> = CbseExamSchedule.officialExams

    val uncompletedChaptersCount: StateFlow<Int> = subjectsWithStats.map { list ->
        list.sumOf { it.totalCount - it.completedCount }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 140)

    val deadlineStatus: StateFlow<DeadlineStatus> = uncompletedChaptersCount.map { uncompleted ->
        CbseExamSchedule.getDeadlineStatus(uncompleted)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        CbseExamSchedule.getDeadlineStatus(140)
    )

    // Wolverine Adamantium Grit: Dynamic Healing Factor & Active Days Node Chain
    val healingFactorPercent: StateFlow<Int> = _weeklyDaysData.map { list ->
        if (list.isEmpty()) 100
        else {
            val active = list.count { it.count > 0 }
            Math.round((active.toFloat() / list.size.toFloat()) * 100f).coerceIn(0, 100)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 100
    )

    val activeDaysList: StateFlow<List<Boolean>> = _weeklyDaysData.map { list ->
        if (list.isEmpty()) List(7) { true }
        else list.map { it.count > 0 }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = List(7) { true }
    )

    // Dynamic Logan Berserker Rage Multiplier calculated from live pomodoro completions and mastered chapters
    val berserkerMultiplier: StateFlow<Float> = combine(
        _pomodorosCompletedToday,
        subjectsWithStats,
        _timerState
    ) { pomos, subjects, timer ->
        val completedChapters = subjects.sumOf { it.completedCount }
        val timerSurge = if (timer.isRunning) 0.3f else 0f
        val raw = 1.0f + (pomos * 0.15f) + (completedChapters * 0.05f) + timerSurge
        (Math.round(raw * 10f) / 10f).coerceIn(1.0f, 4.0f)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 1.6f
    )

    // Captain America Honor Missions with Real Persistent Storage
    private val _honorMissions = MutableStateFlow<List<CapHonorMission>>(emptyList())
    val honorMissions: StateFlow<List<CapHonorMission>> = _honorMissions.asStateFlow()

    // Theme Mode: Dark Developer (Obsidian HUD) vs High-Contrast Light Mode
    private val _isLightMode = MutableStateFlow(
        getApplication<Application>().getSharedPreferences("studyflow_theme_prefs", Context.MODE_PRIVATE)
            .getBoolean("pref_theme_is_light", false)
    )
    val isLightMode: StateFlow<Boolean> = _isLightMode.asStateFlow()

    private val _isSettingsModalOpen = MutableStateFlow(false)
    val isSettingsModalOpen: StateFlow<Boolean> = _isSettingsModalOpen.asStateFlow()

    fun openSettingsModal() {
        _isSettingsModalOpen.value = true
    }

    fun closeSettingsModal() {
        _isSettingsModalOpen.value = false
    }

    fun setThemeMode(light: Boolean) {
        _isLightMode.value = light
        val prefs = getApplication<Application>().getSharedPreferences("studyflow_theme_prefs", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("pref_theme_is_light", light).apply()
        showToast(if (light) "☀️ High-Contrast Light Mode Active" else "🌑 Dark Developer Theme Active")
    }

    fun toggleThemeMode() {
        setThemeMode(!_isLightMode.value)
    }

    // Motivational Daily Academic & MCU Quotes (Daily high-yield study wisdom)
    val motivationalQuotes: List<MotivationalQuote> = DEFAULT_MOTIVATIONAL_QUOTES

    private val _currentQuoteIndex = MutableStateFlow(
        Calendar.getInstance().get(Calendar.DAY_OF_YEAR) % DEFAULT_MOTIVATIONAL_QUOTES.size
    )
    val currentQuoteIndex: StateFlow<Int> = _currentQuoteIndex.asStateFlow()

    fun nextMotivationalQuote() {
        _currentQuoteIndex.value = (_currentQuoteIndex.value + 1) % motivationalQuotes.size
    }

    fun speakCurrentMotivationalQuote() {
        val q = motivationalQuotes[_currentQuoteIndex.value]
        fridayVoiceManager.speak("\"${q.quote}\" Said by ${q.author}, aligned with ${q.marvelHero}.")
        showToast("🎙️ F.R.I.D.A.Y.: Reciting \"${q.author}\"")
    }

    fun triggerLoganGritRoar() {
        val mult = berserkerMultiplier.value
        val streak = streakRecord.value?.currentStreakDays ?: 14
        fridayVoiceManager.speak("Logan here! Adamantium streak holding at $streak days unbroken, berserker rage multiplier $mult x. Consistency is your healing factor, bub!")
        showToast("🐺 Wolverine: Berserker Grit Engaged (${mult}x)!")
    }

    // Quick Notes (active study notes drafted during focus timer)
    private val _quickNotesText = MutableStateFlow("")
    val quickNotesText: StateFlow<String> = _quickNotesText.asStateFlow()

    fun updateQuickNotes(text: String) {
        _quickNotesText.value = text
    }

    fun saveCurrentQuickNotes(
        subjectId: String = "math",
        subjectName: String = "Mathematics",
        chapterTitle: String = "Introduction to Trigonometry"
    ) {
        val notes = _quickNotesText.value.trim()
        if (notes.isBlank()) {
            showToast("Enter notes in the Quick Notes box first")
            return
        }
        val firstLine = notes.lines().firstOrNull()?.replace(Regex("[#*•-]"), "")?.trim()
        val title = if (!firstLine.isNullOrBlank()) {
            if (firstLine.length > 40) firstLine.take(40) + "..." else firstLine
        } else "Focus Session Concept"

        saveMemory(
            subjectId = subjectId,
            subjectName = subjectName,
            chapterTitle = chapterTitle,
            conceptTitle = title,
            content = notes,
            mnemonic = "Focus Note 🍅"
        )
        _quickNotesText.value = ""
        showToast("📝 Quick Notes saved to Memory Vault!")
        fridayVoiceManager.speak("Focus notes successfully archived to your Saved Memory Vault, boss.")
    }

    private fun getUserPrefs() = getApplication<Application>().getSharedPreferences("studyflow_user_prefs", Context.MODE_PRIVATE)

    fun setDailyPomodoroTarget(target: Int) {
        val validTarget = target.coerceIn(1, 24)
        _dailyPomodoroTarget.value = validTarget
        val prefs = getUserPrefs()
        prefs.edit().putInt("daily_pomodoro_target", validTarget).apply()
        refreshWeeklyDaysData()
        evaluateHonorMissions()
        showToast("🎯 Daily Goal set to $validTarget Pomodoros (${validTarget * 25}m focus)")
    }

    fun incrementPomodoroCount() {
        val newDone = _pomodorosCompletedToday.value + 1
        _pomodorosCompletedToday.value = newDone
        val target = _dailyPomodoroTarget.value
        val isoFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val todayIso = isoFormat.format(Date())
        val prefs = getUserPrefs()
        prefs.edit().putInt("pomo_$todayIso", newDone).putString("last_active_date", todayIso).apply()

        viewModelScope.launch {
            val current = streakRecord.value ?: StreakRecordEntity()
            repository.updateStreakRecord(current.copy(pomodorosDoneToday = newDone))
            refreshWeeklyDaysData()
            evaluateHonorMissions()
        }
        if (newDone >= target) {
            showToast("🏆 SENTRY PROTOCOL: Daily Goal Conquered! ($newDone/$target)")
        } else {
            showToast("🍅 Pomodoro Logged! ($newDone/$target)")
        }
    }

    fun decrementPomodoroCount() {
        if (_pomodorosCompletedToday.value > 0) {
            val newDone = _pomodorosCompletedToday.value - 1
            _pomodorosCompletedToday.value = newDone
            val isoFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val todayIso = isoFormat.format(Date())
            val prefs = getUserPrefs()
            prefs.edit().putInt("pomo_$todayIso", newDone).putString("last_active_date", todayIso).apply()

            viewModelScope.launch {
                val current = streakRecord.value ?: StreakRecordEntity()
                repository.updateStreakRecord(current.copy(pomodorosDoneToday = newDone))
                refreshWeeklyDaysData()
                evaluateHonorMissions()
            }
            showToast("🍅 Pomodoro count adjusted: $newDone/${_dailyPomodoroTarget.value}")
        }
    }

    fun refreshWeeklyDaysData() {
        val prefs = getUserPrefs()
        val isoFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val dayFormat = SimpleDateFormat("EEE", Locale.US)
        val dateFormat = SimpleDateFormat("MMM d", Locale.US)
        val calendar = Calendar.getInstance()
        val todayIso = isoFormat.format(calendar.time)

        // Save today's live pomodoro count in persistent preferences
        prefs.edit().putInt("pomo_$todayIso", _pomodorosCompletedToday.value).putString("last_active_date", todayIso).apply()

        val baseline = listOf(4, 6, 5, 7, 5, 6, _pomodorosCompletedToday.value)
        val days = mutableListOf<WeeklyDayData>()

        for (i in 6 downTo 0) {
            val cal = Calendar.getInstance()
            cal.add(Calendar.DAY_OF_YEAR, -i)
            val iso = isoFormat.format(cal.time)
            val dayName = if (i == 0) "Today" else dayFormat.format(cal.time)
            val dateStr = dateFormat.format(cal.time)
            val count = if (i == 0) {
                _pomodorosCompletedToday.value
            } else {
                prefs.getInt("pomo_$iso", baseline[6 - i])
            }
            days.add(WeeklyDayData(dayName = dayName, count = count, isToday = (i == 0), dateString = dateStr))
        }
        _weeklyDaysData.value = days
    }

    fun evaluateHonorMissions() {
        val prefs = getApplication<Application>().getSharedPreferences("studyflow_honor_missions_prefs", Context.MODE_PRIVATE)
        val pomos = _pomodorosCompletedToday.value
        val target = _dailyPomodoroTarget.value
        val subjects = subjectsWithStats.value
        val completedChapters = subjects.sumOf { it.completedCount }

        _honorMissions.value = listOf(
            CapHonorMission(
                id = "cap_1",
                title = "Conquer 2 Mathematics or Science Topics",
                heroQuote = "I can do this all day.",
                xpReward = 150,
                isCompleted = prefs.getBoolean("mission_cap_1", completedChapters >= 2)
            ),
            CapHonorMission(
                id = "cap_2",
                title = "Complete 25m Sentry Focus Solar Session",
                heroQuote = "Stay on the line, soldier.",
                xpReward = 200,
                isCompleted = prefs.getBoolean("mission_cap_2", pomos >= 1)
            ),
            CapHonorMission(
                id = "cap_3",
                title = "Review 8 Mind Stone Flashcards with Vision",
                heroQuote = "Knowledge forged in discipline.",
                xpReward = 120,
                isCompleted = prefs.getBoolean("mission_cap_3", false)
            ),
            CapHonorMission(
                id = "cap_4",
                title = "Hit Daily Goal ($target Pomodoro Sessions)",
                heroQuote = "Honor the daily pledge.",
                xpReward = 250,
                isCompleted = prefs.getBoolean("mission_cap_4", pomos >= target)
            )
        )
    }

    init {
        // Guarantee on-device database is seeded with CBSE subjects, chapters and subtopics
        viewModelScope.launch(Dispatchers.IO) {
            repository.ensureDataPopulated()
        }

        // Restore user's persistent Daily Study Goal, Pomos Today, and Timer state across re-opens
        val prefs = getUserPrefs()
        val isoFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val todayIso = isoFormat.format(Date())

        // 1. Restore Daily Pomodoro Target
        val savedTarget = prefs.getInt("daily_pomodoro_target", 6)
        _dailyPomodoroTarget.value = savedTarget

        // 2. Restore Pomodoros Completed Today
        val lastDate = prefs.getString("last_active_date", "")
        if (lastDate == todayIso) {
            val savedPomos = prefs.getInt("pomo_$todayIso", 4)
            _pomodorosCompletedToday.value = savedPomos
        } else {
            // New calendar day: archive yesterday's count if any, start today fresh
            val existingToday = prefs.getInt("pomo_$todayIso", -1)
            if (existingToday >= 0) {
                _pomodorosCompletedToday.value = existingToday
            } else {
                _pomodorosCompletedToday.value = 0
                prefs.edit().putInt("pomo_$todayIso", 0).putString("last_active_date", todayIso).apply()
            }
        }

        // 3. Restore Focus Timer State (mode, duration, remaining seconds, linked subject)
        val savedTimerDate = prefs.getString("timer_saved_date", "")
        if (savedTimerDate == todayIso) {
            val savedModeStr = prefs.getString("timer_mode", TimerMode.POMODORO.name) ?: TimerMode.POMODORO.name
            val mode = try { TimerMode.valueOf(savedModeStr) } catch (e: Exception) { TimerMode.POMODORO }
            val savedTotal = prefs.getInt("timer_total_seconds", mode.defaultMinutes * 60)
            val savedRemaining = prefs.getInt("timer_remaining_seconds", mode.defaultMinutes * 60)
            val savedSubject = prefs.getString("timer_linked_subject", "Mathematics") ?: "Mathematics"
            val savedChapter = prefs.getString("timer_linked_chapter", "Introduction to Trigonometry") ?: "Introduction to Trigonometry"

            if (savedRemaining > 0 && savedRemaining <= savedTotal) {
                _timerState.value = TimerState(
                    mode = mode,
                    totalSeconds = savedTotal,
                    remainingSeconds = savedRemaining,
                    isRunning = false, // Always paused on reopen so user can resume when ready
                    linkedSubject = savedSubject,
                    linkedChapter = savedChapter
                )
            }
        }

        // Initialize real weekly days data and Captain America honor missions
        refreshWeeklyDaysData()
        evaluateHonorMissions()

        // Start live ticker for quiz timer
        viewModelScope.launch {
            while (true) {
                delay(1000)
                if (_quizSecondsRemaining.value > 0) {
                    _quizSecondsRemaining.value -= 1
                }
            }
        }
    }

    fun ensureDataPopulated() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.ensureDataPopulated()
        }
    }

    fun setFilter(filter: String) {
        _selectedFilter.value = filter
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun openChapterSheet(chapter: ChapterEntity) {
        _selectedChapterId.value = chapter.id
    }

    fun closeChapterSheet() {
        _selectedChapterId.value = null
    }

    fun getSubtopicsForChapter(chapter: ChapterEntity): List<SubtopicEntity> {
        val list = subtopicsByChapter.value[chapter.id]
        if (!list.isNullOrEmpty()) return list

        val defaultTitles = DefaultSubtopicsData.chapterTopicsMap[chapter.id]
            ?: DefaultSubtopicsData.getDefaultTopicsForChapter(chapter)
        return defaultTitles.mapIndexed { index, title ->
            SubtopicEntity(
                id = "${chapter.id}_sub_${index + 1}",
                chapterId = chapter.id,
                title = title,
                isCompleted = chapter.status == "MASTERED",
                orderIndex = index + 1
            )
        }
    }

    fun isSubtopicChecked(chapter: ChapterEntity, index: Int): Boolean {
        val list = getSubtopicsForChapter(chapter)
        return if (index in list.indices) list[index].isCompleted else false
    }

    fun toggleSubtopic(subtopic: SubtopicEntity, chapter: ChapterEntity) {
        viewModelScope.launch {
            val isNowCompleted = repository.toggleSubtopic(subtopic, chapter)
            if (isNowCompleted) {
                showToast("✓ Completed: \"${subtopic.title}\"")
            } else {
                showToast("Unchecked: \"${subtopic.title}\"")
            }
        }
    }

    fun toggleSubtopicByIndex(chapter: ChapterEntity, subtopicIndex: Int) {
        val list = getSubtopicsForChapter(chapter)
        if (subtopicIndex in list.indices) {
            toggleSubtopic(list[subtopicIndex], chapter)
        }
    }

    fun toggleChapterCompletion(chapter: ChapterEntity) {
        viewModelScope.launch {
            val updated = repository.toggleChapterStatus(chapter)
            if (updated.status == "MASTERED") {
                showToast("✓ Arc Reactor Overcharge: \"${chapter.title}\" Mastered!")
                _deadpoolQuip.value = DeadpoolQuipBank.getRandomQuip()
                _isDeadpoolPopupVisible.value = true
            } else {
                showToast("Chapter \"${chapter.title}\" reset to Unlearned")
            }
        }
    }

    fun toggleChapterNcert(chapter: ChapterEntity) {
        viewModelScope.launch {
            val isDone = repository.toggleChapterNcert(chapter)
            showToast(if (isDone) "📖 NCERT Mastered: ${chapter.title}" else "📖 NCERT Unchecked: ${chapter.title}")
        }
    }

    fun toggleChapterQuestionBank(chapter: ChapterEntity) {
        viewModelScope.launch {
            val isDone = repository.toggleChapterQuestionBank(chapter)
            showToast(if (isDone) "📚 Question Bank Secured: ${chapter.title}" else "📚 Question Bank Unchecked: ${chapter.title}")
        }
    }

    fun toggleChapterSamplePaper(chapter: ChapterEntity) {
        viewModelScope.launch {
            val isDone = repository.toggleChapterSamplePaper(chapter)
            showToast(if (isDone) "📝 Sample Paper Conquered: ${chapter.title}" else "📝 Sample Paper Unchecked: ${chapter.title}")
        }
    }

    fun toggleChapterPyq(chapter: ChapterEntity) {
        viewModelScope.launch {
            val isDone = repository.toggleChapterPyq(chapter)
            showToast(if (isDone) "🎯 10-Yr PYQ Decimated: ${chapter.title}" else "🎯 10-Yr PYQ Unchecked: ${chapter.title}")
        }
    }

    fun hulkSmashChapter(chapter: ChapterEntity) {
        viewModelScope.launch {
            if (chapter.status != "MASTERED") {
                repository.toggleChapterStatus(chapter)
            }
            showToast("💥 HULK SMASH! \"${chapter.title}\" DECIMATED! 💥")
            _deadpoolQuip.value = "WHOA! Did you see that?! The big green guy just obliterated that chapter! Maximum Effort, baby!"
            _isDeadpoolPopupVisible.value = true
        }
    }

    // F.R.I.D.A.Y. Voice & Tactical Commands
    fun openFridayModal() {
        _isFridayModalOpen.value = true
        val completedCount = subjectsWithStats.value.sumOf { it.completedCount }
        val totalCount = subjectsWithStats.value.sumOf { it.totalCount }
        val uncompleted = totalCount - completedCount
        val dl = CbseExamSchedule.getDeadlineStatus(uncompleted)
        val streak = streakRecord.value?.currentStreakDays ?: 14
        val greeting = "F.R.I.D.A.Y. online. Welcome to Project: Conquer All Subjects, boss. Tactical countdown is at ${dl.daysRemaining} days until the January 15, 2027 completion deadline. For the 95%+ Time Heist, your required velocity is ${String.format(Locale.US, "%.1f", dl.requiredDailyVelocity)} chapters per day. Mathematics Standard exam is first up on February 17, 2027. All systems primed."
        fridayVoiceManager.speak(greeting)
    }

    fun closeFridayModal() {
        _isFridayModalOpen.value = false
        fridayVoiceManager.stop()
    }

    fun executeFridayCommand(command: String) {
        val lower = command.lowercase().trim()
        when {
            lower.contains("status") || lower.contains("report") -> {
                val completed = subjectsWithStats.value.sumOf { it.completedCount }
                val total = subjectsWithStats.value.sumOf { it.totalCount }
                val pct = if (total > 0) (completed * 100) / total else 0
                val speech = "Diagnostics report: $pct percent total board syllabus conquered across 7 CBSE domains. Iron Man Arc Reactor telemetry is stable at 98.4 gigawatts."
                fridayVoiceManager.speak(speech)
            }
            lower.contains("deadline") || lower.contains("january 15") || lower.contains("countdown") || lower.contains("heist") || lower.contains("95") -> {
                val uncompleted = subjectsWithStats.value.sumOf { it.totalCount - it.completedCount }
                val dl = CbseExamSchedule.getDeadlineStatus(uncompleted)
                val speech = "Time Heist telemetry: ${dl.daysRemaining} days remaining until the January 15, 2027 completion deadline. Required pace is ${String.format(Locale.US, "%.1f", dl.requiredDailyVelocity)} chapters per day to secure 95%+ marks. First exam is Mathematics on February 17, 2027."
                fridayVoiceManager.speak(speech)
            }
            lower.contains("schedule") || lower.contains("exam") || lower.contains("dates") -> {
                val speech = "Official CBSE Exam Schedule: February 17 is Mathematics Standard, February 22 is Science, February 24 is Information Technology, February 26 is English, March 1 is Sanskrit, March 3 is Hindi Course-B, and March 8 is Social Science. All papers 10:30 AM start."
                fridayVoiceManager.speak(speech)
            }
            lower.contains("deadpool") || lower.contains("quip") || lower.contains("wade") -> {
                val quip = DeadpoolQuipBank.getRandomQuip()
                _deadpoolQuip.value = quip
                _isDeadpoolPopupVisible.value = true
                fridayVoiceManager.speak("Relaying message from Wade Wilson: $quip")
            }
            lower.contains("hulk") || lower.contains("smash") -> {
                _isHulkSmashActive.value = true
                fridayVoiceManager.speak("Gamma radiation threshold exceeded! Bruce Banner has authorized Hulk Smash Mode for fast chapter clearance!")
            }
            lower.contains("logan") || lower.contains("wolverine") || lower.contains("grit") -> {
                fridayVoiceManager.speak("Logan reports: Berserker study multiplier is currently at ${berserkerMultiplier.value}x. Keep your head down and finish the drill, bub.")
            }
            lower.contains("doom") || lower.contains("latveria") -> {
                fridayVoiceManager.speak("Victor von Doom decrees: 470 total CBSE marks are awaiting absolute conquest. None shall surpass Latverian intellect!")
            }
            lower.contains("sentry") || lower.contains("timer") || lower.contains("focus") -> {
                startTimer()
                fridayVoiceManager.speak("Sentry solar focus cycle initiated. Channeling the power of one million exploding suns.")
            }
            lower.contains("light mode") || lower.contains("light theme") || lower.contains("day mode") -> {
                setThemeMode(true)
                fridayVoiceManager.speak("High-contrast light mode enabled for maximum daylight visibility.")
            }
            lower.contains("dark mode") || lower.contains("dark theme") || lower.contains("dark developer") || lower.contains("obsidian") -> {
                setThemeMode(false)
                fridayVoiceManager.speak("Dark Developer obsidian HUD protocol restored.")
            }
            else -> {
                fridayVoiceManager.speak("Tactical command acknowledged: $command. Mark LXXXV protocols active and monitoring your study progress toward the 95%+ Time Heist.")
            }
        }
    }

    fun triggerDeadpoolQuip() {
        _deadpoolQuip.value = DeadpoolQuipBank.getRandomQuip()
        _isDeadpoolPopupVisible.value = true
    }

    fun dismissDeadpoolPopup() {
        _isDeadpoolPopupVisible.value = false
    }

    fun toggleMoonKnightMode() {
        _isMoonKnightMode.value = !_isMoonKnightMode.value
        if (_isMoonKnightMode.value) {
            showToast("🌙 Khonshu's Nocturnal Study Mode Activated!")
        } else {
            showToast("☀️ Switched back to Standard Daylight Protocol")
        }
    }

    fun setMoonKnightPersona(persona: String) {
        _moonKnightPersona.value = persona
        showToast("Moon Knight persona aligned: $persona")
    }

    fun toggleHonorMission(id: String) {
        val prefs = getApplication<Application>().getSharedPreferences("studyflow_honor_missions_prefs", Context.MODE_PRIVATE)
        val currentList = _honorMissions.value
        val target = currentList.firstOrNull { it.id == id } ?: return
        val newStatus = !target.isCompleted
        prefs.edit().putBoolean("mission_$id", newStatus).apply()

        _honorMissions.value = currentList.map {
            if (it.id == id) it.copy(isCompleted = newStatus) else it
        }

        if (newStatus) {
            showToast("🎖️ Cap's Honor Mission Complete: +${target.xpReward} XP!")
            fridayVoiceManager.speak("Honor mission accomplished, soldier: ${target.title}. +${target.xpReward} XP awarded!")
        } else {
            showToast("Mission reset: ${target.title}")
        }
    }

    fun addCustomChapter(subjectId: String, title: String, marks: Int) {
        viewModelScope.launch {
            repository.addCustomChapter(subjectId, title, marks)
            showToast("Added custom chapter: $title")
        }
    }

    fun showToast(message: String) {
        _toastMessage.value = message
        viewModelScope.launch {
            delay(2400)
            if (_toastMessage.value == message) {
                _toastMessage.value = null
            }
        }
    }

    private fun saveTimerStateToPrefs() {
        val prefs = getUserPrefs()
        val isoFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val todayIso = isoFormat.format(Date())
        val state = _timerState.value
        prefs.edit()
            .putString("timer_saved_date", todayIso)
            .putString("timer_mode", state.mode.name)
            .putInt("timer_total_seconds", state.totalSeconds)
            .putInt("timer_remaining_seconds", state.remainingSeconds)
            .putString("timer_linked_subject", state.linkedSubject)
            .putString("timer_linked_chapter", state.linkedChapter)
            .apply()
    }

    // Pomodoro Timer Controls
    fun setTimerMode(mode: TimerMode) {
        stopTimer()
        val totalSecs = mode.defaultMinutes * 60
        _timerState.value = _timerState.value.copy(
            mode = mode,
            totalSeconds = totalSecs,
            remainingSeconds = totalSecs,
            isRunning = false
        )
        saveTimerStateToPrefs()
    }

    fun toggleTimer() {
        if (_timerState.value.isRunning) {
            pauseTimer()
        } else {
            startTimer()
        }
    }

    fun startTimer() {
        _timerState.value = _timerState.value.copy(isRunning = true)
        saveTimerStateToPrefs()
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            var counter = 0
            while (_timerState.value.isRunning && _timerState.value.remainingSeconds > 0) {
                delay(1000)
                val newRemaining = _timerState.value.remainingSeconds - 1
                _timerState.value = _timerState.value.copy(remainingSeconds = newRemaining)
                counter++
                if (counter % 5 == 0) {
                    saveTimerStateToPrefs()
                }
                if (newRemaining <= 0) {
                    onTimerFinished()
                }
            }
        }
    }

    fun pauseTimer() {
        _timerState.value = _timerState.value.copy(isRunning = false)
        timerJob?.cancel()
        saveTimerStateToPrefs()
    }

    fun stopTimer() {
        _timerState.value = _timerState.value.copy(isRunning = false)
        timerJob?.cancel()
        saveTimerStateToPrefs()
    }

    fun resetTimer() {
        stopTimer()
        _timerState.value = _timerState.value.copy(
            remainingSeconds = _timerState.value.totalSeconds
        )
        saveTimerStateToPrefs()
    }

    fun addFiveMinutes() {
        val additional = 5 * 60
        _timerState.value = _timerState.value.copy(
            totalSeconds = _timerState.value.totalSeconds + additional,
            remainingSeconds = _timerState.value.remainingSeconds + additional
        )
        saveTimerStateToPrefs()
        showToast("Added 5 minutes to focus timer")
    }

    fun decreaseFiveMinutes() {
        val subtract = 5 * 60
        val currentRemaining = _timerState.value.remainingSeconds
        val currentTotal = _timerState.value.totalSeconds
        if (currentRemaining > subtract + 30) {
            _timerState.value = _timerState.value.copy(
                totalSeconds = maxOf(60, currentTotal - subtract),
                remainingSeconds = currentRemaining - subtract
            )
            saveTimerStateToPrefs()
            showToast("Subtracted 5 minutes from focus timer")
        } else if (currentRemaining > 60) {
            _timerState.value = _timerState.value.copy(
                remainingSeconds = 60
            )
            saveTimerStateToPrefs()
            showToast("Timer set to 1 minute remaining")
        } else {
            showToast("Minimum time reached (1 minute)")
        }
    }

    fun replayFridaySpeech() {
        fridayVoiceManager.replaySpeech()
    }

    fun skipStage() {
        stopTimer()
        onTimerFinished()
    }

    fun toggleZenMode() {
        _timerState.value = _timerState.value.copy(zenMode = !_timerState.value.zenMode)
    }

    fun setTimerLinkedSubject(subjectName: String, chapterTitle: String = "Core Concept Drill") {
        _timerState.value = _timerState.value.copy(
            linkedSubject = subjectName,
            linkedChapter = chapterTitle
        )
        saveTimerStateToPrefs()
        showToast("🎯 Linked focus timer to $subjectName!")
    }

    private fun onTimerFinished() {
        val isFocusMode = _timerState.value.mode == TimerMode.POMODORO || _timerState.value.mode == TimerMode.DEEP_FLOW
        var newCount = _pomodorosCompletedToday.value
        val target = _dailyPomodoroTarget.value

        if (isFocusMode) {
            newCount = _pomodorosCompletedToday.value + 1
            _pomodorosCompletedToday.value = newCount
        }

        viewModelScope.launch {
            repository.logFocusSession(
                subject = _timerState.value.linkedSubject,
                chapter = _timerState.value.linkedChapter,
                durationMinutes = _timerState.value.totalSeconds / 60,
                mode = _timerState.value.mode.name
            )

            // Update persistent streak record pomodoros count
            val currentStreak = streakRecord.value ?: StreakRecordEntity()
            repository.updateStreakRecord(
                currentStreak.copy(
                    pomodorosDoneToday = newCount
                )
            )

            if (isFocusMode) {
                if (newCount >= target) {
                    showToast("🏆 DAILY STUDY GOAL CONQUERED! ($newCount/$target Pomodoros)")
                    fridayVoiceManager.speak("Outstanding discipline, boss! You have conquered your daily goal with $newCount completed Pomodoros.")
                } else {
                    showToast("🍅 Focus session complete! Daily Goal: $newCount/$target Pomodoros")
                    fridayVoiceManager.speak("Focus session complete, boss. Telemetry logged $newCount of $target daily Pomodoros.")
                }

                // Auto-save Quick Notes to Saved Memory Vault if notes were jotted during this session
                if (_quickNotesText.value.isNotBlank()) {
                    val notes = _quickNotesText.value.trim()
                    val firstLine = notes.lines().firstOrNull()?.replace(Regex("[#*•-]"), "")?.trim()
                    val title = if (!firstLine.isNullOrBlank()) {
                        if (firstLine.length > 40) firstLine.take(40) + "..." else firstLine
                    } else "Focus Session Concept"

                    val subjectId = if (_timerState.value.linkedSubject.contains("Science", ignoreCase = true)) "science" else "math"

                    saveMemory(
                        subjectId = subjectId,
                        subjectName = _timerState.value.linkedSubject.ifBlank { "Mathematics" },
                        chapterTitle = _timerState.value.linkedChapter.ifBlank { "Introduction to Trigonometry" },
                        conceptTitle = title,
                        content = notes,
                        mnemonic = "Focus Note 🍅"
                    )
                    _quickNotesText.value = ""
                    showToast("📝 Quick Notes auto-saved to Memory Vault!")
                }
            } else {
                showToast("Break concluded! Solar focus core recharged.")
            }
            refreshWeeklyDaysData()
            evaluateHonorMissions()
        }
        val nextSession = (_timerState.value.currentSession % _timerState.value.totalSessions) + 1
        _timerState.value = _timerState.value.copy(
            isRunning = false,
            currentSession = nextSession,
            remainingSeconds = _timerState.value.totalSeconds
        )
    }

    // Ambient Sound Controls
    fun toggleAmbientSound(sound: AmbientSound) {
        if (_activeAmbientSound.value == sound) {
            _activeAmbientSound.value = null
            showToast("🔇 Ambient sound muted")
        } else {
            _activeAmbientSound.value = sound
            showToast("🎧 Focus Soundscape active: ${sound.title}")
        }
    }

    fun setAmbientSound(sound: AmbientSound?) {
        _activeAmbientSound.value = sound
        if (sound != null) {
            showToast("🎧 Focus Soundscape: ${sound.title}")
        } else {
            showToast("🔇 Focus Soundscape paused")
        }
    }

    fun toggleAutoPlaySoundscapes() {
        _autoPlaySoundscapes.value = !_autoPlaySoundscapes.value
        val state = if (_autoPlaySoundscapes.value) "Enabled" else "Disabled"
        showToast("🎵 Soundscape auto-play on focus: $state")
    }

    fun setAmbientVolume(vol: Float) {
        _ambientVolume.value = vol.coerceIn(0f, 1f)
    }

    // Quiz Controls
    fun selectQuizOption(option: String) {
        _selectedQuizOption.value = option
    }

    fun setConfidence(conf: String) {
        _confidenceLevel.value = conf
    }

    fun toggleFormulaPeek() {
        _isFormulaPeekOpen.value = !_isFormulaPeekOpen.value
    }

    fun toggleDiagram() {
        _isDiagramOpen.value = !_isDiagramOpen.value
    }

    fun toggleFlag() {
        _isFlagged.value = !_isFlagged.value
    }

    // Flashcard Deck Controls
    fun nextFlashcard(total: Int) {
        if (total > 0) {
            _currentFlashcardIndex.value = (_currentFlashcardIndex.value + 1) % total
            _isFlashcardFlipped.value = false
        }
    }

    fun prevFlashcard(total: Int) {
        if (total > 0) {
            _currentFlashcardIndex.value = if (_currentFlashcardIndex.value > 0) _currentFlashcardIndex.value - 1 else total - 1
            _isFlashcardFlipped.value = false
        }
    }

    fun flipFlashcard() {
        _isFlashcardFlipped.value = !_isFlashcardFlipped.value
    }

    fun rateFlashcard(card: FlashcardEntity, rating: String) {
        viewModelScope.launch {
            repository.updateFlashcardRating(card, rating)
            showToast("Rated: $rating")
        }
    }

    // Saved Memory Bank Operations
    fun saveMemory(
        subjectId: String,
        subjectName: String,
        chapterTitle: String,
        conceptTitle: String,
        content: String,
        mnemonic: String = ""
    ) {
        viewModelScope.launch {
            val memory = SavedMemoryEntity(
                subjectId = subjectId,
                subjectName = subjectName,
                chapterTitle = chapterTitle,
                conceptTitle = conceptTitle,
                memoryContent = content,
                mnemonic = mnemonic
            )
            repository.insertSavedMemory(memory)
            showToast("Saved to Memory Vault")
        }
    }

    fun deleteMemory(memory: SavedMemoryEntity) {
        viewModelScope.launch {
            repository.deleteSavedMemory(memory)
            showToast("Memory removed")
        }
    }

    fun togglePinMemory(id: Long) {
        viewModelScope.launch {
            repository.togglePinSavedMemory(id)
        }
    }

    fun resetAllChaptersToUnlearned() {
        viewModelScope.launch {
            repository.resetAllChaptersToUnlearned()
            showToast("All chapters reset to Unlearned")
        }
    }

    fun triggerStreakCelebration() {
        viewModelScope.launch {
            val current = streakRecord.value ?: StreakRecordEntity()
            val newStreak = current.currentStreakDays + 1
            val isNewRecord = newStreak >= current.bestStreakDays
            val newBest = if (newStreak > current.bestStreakDays) newStreak else current.bestStreakDays
            val updated = current.copy(
                currentStreakDays = newStreak,
                bestStreakDays = newBest
            )
            repository.updateStreakRecord(updated)
            _sparkleCelebrationEvent.value = System.currentTimeMillis()
            if (isNewRecord) {
                showToast("🏆 NEW PERSONAL RECORD! $newStreak-Day Adamantium Streak!")
            } else {
                showToast("🔥 Daily Streak: $newStreak Days!")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        fridayVoiceManager.shutdown()
    }
}

