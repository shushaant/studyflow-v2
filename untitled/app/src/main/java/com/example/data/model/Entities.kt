package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subjects")
data class SubjectEntity(
    @PrimaryKey val id: String,
    val name: String,
    val category: String, // e.g. "CBSE X", "Phy/Chem/Bio", "Hist/Civ/Geo"
    val code: String = "", // e.g. "CBSE 041", "CBSE 086", "CBSE 087"
    val iconName: String,
    val colorHex: String,
    val totalChapters: Int = 0,
    val completedChapters: Int = 0,
    val orderIndex: Int
)

@Entity(tableName = "chapters")
data class ChapterEntity(
    @PrimaryKey val id: String,
    val subjectId: String,
    val chapterNumber: Int,
    val title: String,
    val status: String = "NOT_STARTED", // "MASTERED", "IN_REVIEW", "UP_NEXT", "NOT_STARTED"
    val weightageMarks: Int = 4,
    val completedDate: String? = null,
    val subtopicCount: Int = 4,
    val completedSubtopics: Int = 0,
    val notes: String = "",
    val isNcertDone: Boolean = false,
    val isQuestionBankDone: Boolean = false,
    val isSamplePaperDone: Boolean = false,
    val isPyqDone: Boolean = false
)

@Entity(tableName = "subtopics")
data class SubtopicEntity(
    @PrimaryKey val id: String,
    val chapterId: String,
    val title: String,
    val isCompleted: Boolean = false,
    val orderIndex: Int = 0
)

@Entity(tableName = "focus_sessions")
data class FocusSessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val subjectName: String,
    val chapterTitle: String,
    val durationMinutes: Int,
    val mode: String, // "POMODORO", "SHORT_BREAK", "LONG_BREAK", "DEEP_FLOW"
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "streak_records")
data class StreakRecordEntity(
    @PrimaryKey val id: Int = 1,
    val currentStreakDays: Int = 14,
    val bestStreakDays: Int = 18,
    val todayFocusedMinutes: Int = 195, // 3h 15m
    val dailyTargetMinutes: Int = 270, // 4h 30m
    val activeDaysBitmask: Int = 127, // 7 days active (1111111 in binary)
    val pomodorosDoneToday: Int = 4,
    val totalPomosThisWeek: Int = 42,
    val adherencePercent: Int = 98
)

@Entity(tableName = "flashcards")
data class FlashcardEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val subjectId: String,
    val chapterTitle: String,
    val cardTitle: String,
    val formulaFront: String,
    val explanationBack: String,
    val mnemonic: String,
    val rating: String = "GOOD" // "HARD", "GOOD", "MASTERED"
)

@Entity(tableName = "saved_memories")
data class SavedMemoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val subjectId: String,
    val subjectName: String,
    val chapterTitle: String,
    val conceptTitle: String,
    val memoryContent: String,
    val mnemonic: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val isPinned: Boolean = false
)

data class SubjectWithStats(
    val subject: SubjectEntity,
    val chapters: List<ChapterEntity>,
    val completionPercentage: Int,
    val completedCount: Int,
    val totalCount: Int
)
