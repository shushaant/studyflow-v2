package com.example.data.repository

import com.example.data.db.AppDatabase
import com.example.data.model.ChapterEntity
import com.example.data.model.FlashcardEntity
import com.example.data.model.FocusSessionEntity
import com.example.data.model.StreakRecordEntity
import com.example.data.model.SubjectEntity
import com.example.data.model.SubjectWithStats
import com.example.data.model.SavedMemoryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class StudyFlowRepository(private val db: AppDatabase) {

    val allSubjects: Flow<List<SubjectEntity>> = db.subjectDao().getAllSubjects()
    val allChapters: Flow<List<ChapterEntity>> = db.chapterDao().getAllChapters()
    val allSubtopics: Flow<List<com.example.data.model.SubtopicEntity>> = db.subtopicDao().getAllSubtopics()
    val streakRecord: Flow<StreakRecordEntity?> = db.streakDao().getStreakRecord()
    val allFlashcards: Flow<List<FlashcardEntity>> = db.flashcardDao().getAllFlashcards()
    val focusSessions: Flow<List<FocusSessionEntity>> = db.focusSessionDao().getAllSessions()
    val allSavedMemories: Flow<List<SavedMemoryEntity>> = db.savedMemoryDao().getAllMemories()

    val subjectsWithStats: Flow<List<SubjectWithStats>> = combine(
        allSubjects,
        allChapters
    ) { subjects, chapters ->
        val safeSubjects = if (subjects.isNotEmpty()) subjects else com.example.data.db.DefaultSyllabusData.subjects
        val safeChapters = if (chapters.isNotEmpty()) chapters else com.example.data.db.DefaultSyllabusData.chapters
        safeSubjects.map { subject ->
            val subjectChapters = safeChapters.filter { it.subjectId == subject.id }
            val completed = if (subjectChapters.isNotEmpty()) subjectChapters.count { it.status == "MASTERED" } else subject.completedChapters
            val total = if (subjectChapters.isNotEmpty()) subjectChapters.size else subject.totalChapters
            val percentage = if (total > 0) (completed * 100) / total else 0
            SubjectWithStats(
                subject = subject.copy(completedChapters = completed, totalChapters = total),
                chapters = subjectChapters,
                completionPercentage = percentage,
                completedCount = completed,
                totalCount = total
            )
        }
    }

    suspend fun ensureDataPopulated() {
        AppDatabase.ensureSeeded(db)
    }

    suspend fun toggleSubtopic(subtopic: com.example.data.model.SubtopicEntity, chapter: ChapterEntity): Boolean {
        val newCompleted = !subtopic.isCompleted
        db.subtopicDao().setSubtopicCompletion(subtopic.id, newCompleted)
        val chapterSubtopics = db.subtopicDao().getSubtopicsForChapterDirect(chapter.id)
        val completedCount = chapterSubtopics.count { if (it.id == subtopic.id) newCompleted else it.isCompleted }
        val totalCount = chapterSubtopics.size

        val newStatus = when {
            totalCount > 0 && completedCount >= totalCount -> "MASTERED"
            completedCount > 0 -> "IN_REVIEW"
            else -> "NOT_STARTED"
        }
        val dateString = if (newStatus == "MASTERED") {
            SimpleDateFormat("MMM dd", Locale.getDefault()).format(Date())
        } else {
            null
        }
        val updated = chapter.copy(
            status = newStatus,
            completedSubtopics = completedCount,
            subtopicCount = if (totalCount > 0) totalCount else chapter.subtopicCount,
            completedDate = dateString
        )
        db.chapterDao().updateChapter(updated)
        return newCompleted
    }

    suspend fun toggleChapterStatus(chapter: ChapterEntity): ChapterEntity {
        val nextStatus = if (chapter.status == "MASTERED") "NOT_STARTED" else "MASTERED"
        val isMastered = nextStatus == "MASTERED"
        val dateString = if (isMastered) {
            SimpleDateFormat("MMM dd", Locale.getDefault()).format(Date())
        } else {
            null
        }

        db.subtopicDao().setAllSubtopicsForChapter(chapter.id, isMastered)
        val chapterSubtopics = db.subtopicDao().getSubtopicsForChapterDirect(chapter.id)
        val totalSub = if (chapterSubtopics.isNotEmpty()) chapterSubtopics.size else chapter.subtopicCount

        val updated = chapter.copy(
            status = nextStatus,
            completedDate = dateString,
            completedSubtopics = if (isMastered) totalSub else 0,
            subtopicCount = totalSub
        )
        db.chapterDao().updateChapter(updated)
        return updated
    }

    suspend fun updateChapter(chapter: ChapterEntity) {
        db.chapterDao().updateChapter(chapter)
    }

    suspend fun toggleChapterNcert(chapter: ChapterEntity): Boolean {
        val newVal = !chapter.isNcertDone
        db.chapterDao().setNcertDone(chapter.id, newVal)
        return newVal
    }

    suspend fun toggleChapterQuestionBank(chapter: ChapterEntity): Boolean {
        val newVal = !chapter.isQuestionBankDone
        db.chapterDao().setQuestionBankDone(chapter.id, newVal)
        return newVal
    }

    suspend fun toggleChapterSamplePaper(chapter: ChapterEntity): Boolean {
        val newVal = !chapter.isSamplePaperDone
        db.chapterDao().setSamplePaperDone(chapter.id, newVal)
        return newVal
    }

    suspend fun toggleChapterPyq(chapter: ChapterEntity): Boolean {
        val newVal = !chapter.isPyqDone
        db.chapterDao().setPyqDone(chapter.id, newVal)
        return newVal
    }

    suspend fun resetAllChaptersToUnlearned() {
        db.chapterDao().resetAllChaptersToUnlearned()
        db.subtopicDao().resetAllSubtopics()
        db.subjectDao().resetAllSubjectProgress()
    }

    suspend fun addCustomChapter(subjectId: String, title: String, marks: Int) {
        val id = "${subjectId}_custom_${System.currentTimeMillis()}"
        val newChapter = ChapterEntity(
            id = id,
            subjectId = subjectId,
            chapterNumber = 99,
            title = title,
            status = "NOT_STARTED",
            weightageMarks = marks,
            subtopicCount = 3,
            completedSubtopics = 0
        )
        db.chapterDao().insertChapter(newChapter)
    }

    suspend fun insertSavedMemory(memory: SavedMemoryEntity) {
        db.savedMemoryDao().insertMemory(memory)
    }

    suspend fun deleteSavedMemory(memory: SavedMemoryEntity) {
        db.savedMemoryDao().deleteMemory(memory)
    }

    suspend fun togglePinSavedMemory(id: Long) {
        db.savedMemoryDao().togglePin(id)
    }

    suspend fun logFocusSession(subject: String, chapter: String, durationMinutes: Int, mode: String) {
        val session = FocusSessionEntity(
            subjectName = subject,
            chapterTitle = chapter,
            durationMinutes = durationMinutes,
            mode = mode
        )
        db.focusSessionDao().insertSession(session)
    }

    suspend fun updateFlashcardRating(flashcard: FlashcardEntity, newRating: String) {
        db.flashcardDao().updateFlashcard(flashcard.copy(rating = newRating))
    }

    suspend fun updateStreakRecord(record: StreakRecordEntity) {
        db.streakDao().insertOrUpdate(record)
    }
}

