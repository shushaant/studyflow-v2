package com.example.data.db

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.Delete
import com.example.data.model.ChapterEntity
import com.example.data.model.FlashcardEntity
import com.example.data.model.FocusSessionEntity
import com.example.data.model.SavedMemoryEntity
import com.example.data.model.StreakRecordEntity
import com.example.data.model.SubjectEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Dao
interface SubjectDao {
    @Query("SELECT * FROM subjects ORDER BY orderIndex ASC")
    fun getAllSubjects(): Flow<List<SubjectEntity>>

    @Query("SELECT COUNT(*) FROM subjects")
    suspend fun getSubjectsCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubjects(subjects: List<SubjectEntity>)

    @Update
    suspend fun updateSubject(subject: SubjectEntity)

    @Query("UPDATE subjects SET completedChapters = :completed, totalChapters = :total WHERE id = :subjectId")
    suspend fun updateSubjectProgress(subjectId: String, completed: Int, total: Int)

    @Query("UPDATE subjects SET completedChapters = 0")
    suspend fun resetAllSubjectProgress()
}

@Dao
interface ChapterDao {
    @Query("SELECT * FROM chapters ORDER BY chapterNumber ASC")
    fun getAllChapters(): Flow<List<ChapterEntity>>

    @Query("SELECT COUNT(*) FROM chapters")
    suspend fun getChaptersCount(): Int

    @Query("SELECT * FROM chapters WHERE subjectId = :subjectId ORDER BY chapterNumber ASC")
    fun getChaptersForSubject(subjectId: String): Flow<List<ChapterEntity>>

    @Update
    suspend fun updateChapter(chapter: ChapterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapter(chapter: ChapterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapters(chapters: List<ChapterEntity>)

    @Query("UPDATE chapters SET status = 'NOT_STARTED', completedDate = null, completedSubtopics = 0, isNcertDone = 0, isQuestionBankDone = 0, isSamplePaperDone = 0, isPyqDone = 0")
    suspend fun resetAllChaptersToUnlearned()

    @Query("UPDATE chapters SET isNcertDone = :isDone WHERE id = :chapterId")
    suspend fun setNcertDone(chapterId: String, isDone: Boolean)

    @Query("UPDATE chapters SET isQuestionBankDone = :isDone WHERE id = :chapterId")
    suspend fun setQuestionBankDone(chapterId: String, isDone: Boolean)

    @Query("UPDATE chapters SET isSamplePaperDone = :isDone WHERE id = :chapterId")
    suspend fun setSamplePaperDone(chapterId: String, isDone: Boolean)

    @Query("UPDATE chapters SET isPyqDone = :isDone WHERE id = :chapterId")
    suspend fun setPyqDone(chapterId: String, isDone: Boolean)
}

@Dao
interface FocusSessionDao {
    @Query("SELECT * FROM focus_sessions ORDER BY timestamp DESC")
    fun getAllSessions(): Flow<List<FocusSessionEntity>>

    @Insert
    suspend fun insertSession(session: FocusSessionEntity)
}

@Dao
interface StreakDao {
    @Query("SELECT * FROM streak_records WHERE id = 1 LIMIT 1")
    fun getStreakRecord(): Flow<StreakRecordEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(record: StreakRecordEntity)
}

@Dao
interface FlashcardDao {
    @Query("SELECT * FROM flashcards ORDER BY id ASC")
    fun getAllFlashcards(): Flow<List<FlashcardEntity>>

    @Update
    suspend fun updateFlashcard(card: FlashcardEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFlashcards(cards: List<FlashcardEntity>)
}

@Dao
interface SavedMemoryDao {
    @Query("SELECT * FROM saved_memories ORDER BY isPinned DESC, timestamp DESC")
    fun getAllMemories(): Flow<List<SavedMemoryEntity>>

    @Query("SELECT * FROM saved_memories WHERE subjectId = :subjectId ORDER BY isPinned DESC, timestamp DESC")
    fun getMemoriesForSubject(subjectId: String): Flow<List<SavedMemoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemory(memory: SavedMemoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemories(memories: List<SavedMemoryEntity>)

    @Delete
    suspend fun deleteMemory(memory: SavedMemoryEntity)

    @Query("UPDATE saved_memories SET isPinned = NOT isPinned WHERE id = :id")
    suspend fun togglePin(id: Long)

    @Query("DELETE FROM saved_memories")
    suspend fun deleteAllMemories()
}

@Dao
interface SubtopicDao {
    @Query("SELECT * FROM subtopics ORDER BY orderIndex ASC")
    fun getAllSubtopics(): Flow<List<com.example.data.model.SubtopicEntity>>

    @Query("SELECT * FROM subtopics WHERE chapterId = :chapterId ORDER BY orderIndex ASC")
    fun getSubtopicsForChapter(chapterId: String): Flow<List<com.example.data.model.SubtopicEntity>>

    @Query("SELECT * FROM subtopics WHERE chapterId = :chapterId ORDER BY orderIndex ASC")
    suspend fun getSubtopicsForChapterDirect(chapterId: String): List<com.example.data.model.SubtopicEntity>

    @Query("SELECT COUNT(*) FROM subtopics")
    suspend fun getSubtopicsCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubtopics(subtopics: List<com.example.data.model.SubtopicEntity>)

    @Update
    suspend fun updateSubtopic(subtopic: com.example.data.model.SubtopicEntity)

    @Query("UPDATE subtopics SET isCompleted = :isCompleted WHERE id = :subtopicId")
    suspend fun setSubtopicCompletion(subtopicId: String, isCompleted: Boolean)

    @Query("UPDATE subtopics SET isCompleted = :isCompleted WHERE chapterId = :chapterId")
    suspend fun setAllSubtopicsForChapter(chapterId: String, isCompleted: Boolean)

    @Query("UPDATE subtopics SET isCompleted = 0")
    suspend fun resetAllSubtopics()
}

@Database(
    entities = [
        SubjectEntity::class,
        ChapterEntity::class,
        com.example.data.model.SubtopicEntity::class,
        FocusSessionEntity::class,
        StreakRecordEntity::class,
        FlashcardEntity::class,
        SavedMemoryEntity::class
    ],
    version = 5,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun subjectDao(): SubjectDao
    abstract fun chapterDao(): ChapterDao
    abstract fun subtopicDao(): SubtopicDao
    abstract fun focusSessionDao(): FocusSessionDao
    abstract fun streakDao(): StreakDao
    abstract fun flashcardDao(): FlashcardDao
    abstract fun savedMemoryDao(): SavedMemoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "studyflow_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                // Always ensure database is seeded with complete CBSE subjects, chapters & subtopics
                CoroutineScope(Dispatchers.IO).launch {
                    ensureSeeded(instance)
                }

                instance
            }
        }

        suspend fun ensureSeeded(db: AppDatabase) {
            try {
                val subCount = db.subjectDao().getSubjectsCount()
                val chapCount = db.chapterDao().getChaptersCount()
                val subtopicCount = db.subtopicDao().getSubtopicsCount()

                if (subCount == 0 || chapCount == 0) {
                    populateInitialData(db)
                } else if (subtopicCount == 0) {
                    db.subtopicDao().insertSubtopics(DefaultSubtopicsData.defaultSubtopics)
                }
            } catch (e: Exception) {
                try {
                    populateInitialData(db)
                } catch (_: Exception) {}
            }
        }

        suspend fun populateInitialData(db: AppDatabase) {
            db.subjectDao().insertSubjects(DefaultSyllabusData.subjects)
            db.chapterDao().insertChapters(DefaultSyllabusData.chapters)
            db.subtopicDao().insertSubtopics(DefaultSubtopicsData.defaultSubtopics)

            db.streakDao().insertOrUpdate(StreakRecordEntity())

            val flashcards = listOf(
                FlashcardEntity(
                    subjectId = "math",
                    chapterTitle = "Ch 8: Intro to Trigonometry",
                    cardTitle = "Pythagorean Identity #1",
                    formulaFront = "sin²(θ) + cos²(θ) = 1",
                    explanationBack = "In right ΔABC: AB² + BC² = AC². Divide by AC² → (AB/AC)² + (BC/AC)² = 1",
                    mnemonic = "Some People Have / Curly Brown Hair / Through Proper Brushing (sin=P/H, cos=B/H, tan=P/B)",
                    rating = "MASTERED"
                ),
                FlashcardEntity(
                    subjectId = "math",
                    chapterTitle = "Ch 8: Intro to Trigonometry",
                    cardTitle = "Pythagorean Identity #2",
                    formulaFront = "1 + tan²(θ) = sec²(θ)",
                    explanationBack = "Divide sin²θ + cos²θ = 1 by cos²θ → (sin²θ/cos²θ) + 1 = 1/cos²θ → tan²θ + 1 = sec²θ",
                    mnemonic = "Secant is always '1 step ahead of Tan'",
                    rating = "GOOD"
                ),
                FlashcardEntity(
                    subjectId = "math",
                    chapterTitle = "Ch 8: Intro to Trigonometry",
                    cardTitle = "Pythagorean Identity #3",
                    formulaFront = "1 + cot²(θ) = cosec²(θ)",
                    explanationBack = "Divide sin²θ + cos²θ = 1 by sin²θ → 1 + (cos²θ/sin²θ) = 1/sin²θ → 1 + cot²θ = cosec²θ",
                    mnemonic = "'Co's stick together: Cot and Cosec",
                    rating = "HARD"
                ),
                FlashcardEntity(
                    subjectId = "math",
                    chapterTitle = "Ch 8: Intro to Trigonometry",
                    cardTitle = "Double Angle Identity",
                    formulaFront = "tan(2A) = 2 tan A / (1 - tan² A)",
                    explanationBack = "Derived from sin(2A)/cos(2A) = (2 sin A cos A) / (cos² A - sin² A)",
                    mnemonic = "Shortcut for Board 3M questions!",
                    rating = "GOOD"
                )
            )
            db.flashcardDao().insertFlashcards(flashcards)
            db.savedMemoryDao().insertMemories(DefaultSyllabusData.defaultMemories)
        }
    }
}
