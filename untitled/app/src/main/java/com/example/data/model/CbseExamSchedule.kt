package com.example.data.model

import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

data class CbseExamItem(
    val id: String,
    val subjectId: String,
    val subjectName: String,
    val subjectCode: String,
    val examDateString: String,      // "17 February 2027"
    val dayOfWeek: String,            // "Wednesday"
    val examTiming: String,           // "10:30 AM – 01:30 PM"
    val durationHours: Double,        // 3.0 or 2.0
    val targetDateTime: LocalDateTime, // e.g. 2027-02-17T10:30:00
    val infinityStone: String,        // "Mind Stone", "Power Stone", etc.
    val quantumReadinessHero: String, // "Iron Man", "Bruce Banner", etc.
    val tacticalDirective: String
)

data class DeadlineStatus(
    val daysRemaining: Long,
    val hoursRemaining: Long,
    val minutesRemaining: Long,
    val secondsRemaining: Long,
    val formattedCountdown: String,
    val isPastDeadline: Boolean,
    val requiredDailyVelocity: Double,
    val isPacingBehind: Boolean,
    val starkHudWarning: String
)

object CbseExamSchedule {

    // IST Timezone for CBSE Board Exams
    val IST_ZONE: ZoneId = ZoneId.of("Asia/Kolkata")

    // Master completion deadline: January 15, 2027, 23:59:59 IST
    val MASTER_DEADLINE: LocalDateTime = LocalDateTime.of(2027, 1, 15, 23, 59, 59)

    val officialExams: List<CbseExamItem> = listOf(
        CbseExamItem(
            id = "cbse_math",
            subjectId = "math",
            subjectName = "Mathematics Standard / Basic",
            subjectCode = "041 / 241",
            examDateString = "17 February 2027",
            dayOfWeek = "Wednesday",
            examTiming = "10:30 AM – 01:30 PM",
            durationHours = 3.0,
            targetDateTime = LocalDateTime.of(2027, 2, 17, 10, 30, 0),
            infinityStone = "Mind Stone",
            quantumReadinessHero = "Iron Man & Vision",
            tacticalDirective = "Master Trigonometric identities, Quadratic roots, and Surface Areas & Volumes."
        ),
        CbseExamItem(
            id = "cbse_science",
            subjectId = "science",
            subjectName = "Science",
            subjectCode = "086",
            examDateString = "22 February 2027",
            dayOfWeek = "Monday",
            examTiming = "10:30 AM – 01:30 PM",
            durationHours = 3.0,
            targetDateTime = LocalDateTime.of(2027, 2, 22, 10, 30, 0),
            infinityStone = "Power Stone",
            quantumReadinessHero = "The Hulk & Bruce Banner",
            tacticalDirective = "Crush Ohm's law numericals, ray diagrams, and Chemical equation balancing."
        ),
        CbseExamItem(
            id = "cbse_it",
            subjectId = "it",
            subjectName = "Information Technology",
            subjectCode = "402",
            examDateString = "24 February 2027",
            dayOfWeek = "Wednesday",
            examTiming = "10:30 AM – 12:30 PM",
            durationHours = 2.0,
            targetDateTime = LocalDateTime.of(2027, 2, 24, 10, 30, 0),
            infinityStone = "Vibranium Core",
            quantumReadinessHero = "Shuri & Ultron",
            tacticalDirective = "Database Management SQL queries, Spreadsheets, and Digital Documentation."
        ),
        CbseExamItem(
            id = "cbse_english",
            subjectId = "english",
            subjectName = "English Language & Literature",
            subjectCode = "184",
            examDateString = "26 February 2027",
            dayOfWeek = "Friday",
            examTiming = "10:30 AM – 01:30 PM",
            durationHours = 3.0,
            targetDateTime = LocalDateTime.of(2027, 2, 26, 10, 30, 0),
            infinityStone = "Reality Stone",
            quantumReadinessHero = "Captain America & Spider-Man",
            tacticalDirective = "Format precision for Analytical Paragraphs, Formal Letters, and Literature extracts."
        ),
        CbseExamItem(
            id = "cbse_sanskrit",
            subjectId = "sanskrit",
            subjectName = "Sanskrit",
            subjectCode = "122",
            examDateString = "01 March 2027",
            dayOfWeek = "Monday",
            examTiming = "10:30 AM – 01:30 PM",
            durationHours = 3.0,
            targetDateTime = LocalDateTime.of(2027, 3, 1, 10, 30, 0),
            infinityStone = "Time Stone",
            quantumReadinessHero = "Doctor Strange & Moon Knight",
            tacticalDirective = "Sandhi, Samas, and Shemushi Bhag-2 shlokas memorization."
        ),
        CbseExamItem(
            id = "cbse_hindi",
            subjectId = "hindi",
            subjectName = "Hindi Course-B",
            subjectCode = "085",
            examDateString = "03 March 2027",
            dayOfWeek = "Wednesday",
            examTiming = "10:30 AM – 01:30 PM",
            durationHours = 3.0,
            targetDateTime = LocalDateTime.of(2027, 3, 3, 10, 30, 0),
            infinityStone = "Soul Stone",
            quantumReadinessHero = "Thor & Wolverine",
            tacticalDirective = "Sparsh & Sanchayan chapters, Muhavare, Samas, and Patra Lekhan."
        ),
        CbseExamItem(
            id = "cbse_sst",
            subjectId = "sst",
            subjectName = "Social Science",
            subjectCode = "087",
            examDateString = "08 March 2027",
            dayOfWeek = "Monday",
            examTiming = "10:30 AM – 01:30 PM",
            durationHours = 3.0,
            targetDateTime = LocalDateTime.of(2027, 3, 8, 10, 30, 0),
            infinityStone = "Space Stone",
            quantumReadinessHero = "Doctor Doom & Wolverine",
            tacticalDirective = "Nationalism in India timeline, Map pointing, and Federalism concepts."
        )
    )

    fun getDeadlineStatus(uncompletedChaptersCount: Int, now: LocalDateTime = LocalDateTime.now()): DeadlineStatus {
        val duration = Duration.between(now, MASTER_DEADLINE)
        val isPast = duration.isNegative

        val totalSeconds = if (isPast) 0 else duration.seconds
        val days = totalSeconds / 86400
        val hours = (totalSeconds % 86400) / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60

        val daysForVelocity = if (days < 1) 1.0 else days.toDouble()
        val requiredVelocity = uncompletedChaptersCount / daysForVelocity

        val isBehind = requiredVelocity > 2.5 && uncompletedChaptersCount > 10

        val warning = if (isBehind) {
            "⚠️ STARK HUD WARNING: Pacing deficit detected! Required velocity: ${String.format(Locale.US, "%.1f", requiredVelocity)} chapters/day to meet January 15, 2027 deadline."
        } else {
            "✓ STARK TACTICAL TELEMETRY: Trajectory nominal for 95%+ Time Heist. Velocity: ${String.format(Locale.US, "%.1f", requiredVelocity)} chapters/day."
        }

        val formatted = if (isPast) {
            "DEADLINE PASSED"
        } else {
            "${days}d ${hours}h ${minutes}m ${seconds}s"
        }

        return DeadlineStatus(
            daysRemaining = days,
            hoursRemaining = hours,
            minutesRemaining = minutes,
            secondsRemaining = seconds,
            formattedCountdown = formatted,
            isPastDeadline = isPast,
            requiredDailyVelocity = requiredVelocity,
            isPacingBehind = isBehind,
            starkHudWarning = warning
        )
    }

    fun getExamCountdown(exam: CbseExamItem, now: LocalDateTime = LocalDateTime.now()): String {
        val duration = Duration.between(now, exam.targetDateTime)
        if (duration.isNegative) return "EXAM CONQUERED"
        val days = duration.toDays()
        val hours = duration.toHours() % 24
        val minutes = duration.toMinutes() % 60
        return "${days}d ${hours}h ${minutes}m left"
    }

    fun getNextUpcomingExam(now: LocalDateTime = LocalDateTime.now()): CbseExamItem? {
        return officialExams.firstOrNull { it.targetDateTime.isAfter(now) }
            ?: officialExams.firstOrNull()
    }
}
