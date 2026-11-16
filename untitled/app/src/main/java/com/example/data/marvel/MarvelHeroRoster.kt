package com.example.data.marvel

import androidx.compose.ui.graphics.Color
import com.example.ui.theme.ArcReactorCyan
import com.example.ui.theme.BerserkerCrimson
import com.example.ui.theme.CapCobalt
import com.example.ui.theme.CrimsonArc
import com.example.ui.theme.DoomEmerald
import com.example.ui.theme.DoomGold
import com.example.ui.theme.GammaGreen
import com.example.ui.theme.KhonshuCrescent
import com.example.ui.theme.MoonKnightSilver
import com.example.ui.theme.SentrySun
import com.example.ui.theme.SpideyRed
import com.example.ui.theme.StarkGold
import com.example.ui.theme.ThorLightning
import com.example.ui.theme.UltronCrimson
import com.example.ui.theme.VibraniumPurple
import com.example.ui.theme.VisionAmber

data class MarvelHero(
    val id: String,
    val name: String,
    val heroAlias: String,
    val role: String,
    val quote: String,
    val accentColor: Color,
    val secondaryColor: Color = Color.White
)

object MarvelHeroRoster {

    val ironMan = MarvelHero(
        id = "iron_man",
        name = "Tony Stark",
        heroAlias = "Iron Man",
        role = "Main Dashboard & Arc Reactor Core Telemetry",
        quote = "I am Iron Man. And your board exam preparation is running at 100% capacity.",
        accentColor = ArcReactorCyan,
        secondaryColor = StarkGold
    )

    val wolverine = MarvelHero(
        id = "wolverine",
        name = "Logan",
        heroAlias = "Wolverine",
        role = "Discipline, Grit & Adamantium Consistency Tracker",
        quote = "No excuses, bub. You sit down, you grind, and you don't stop until the syllabus is finished.",
        accentColor = BerserkerCrimson,
        secondaryColor = StarkGold
    )

    val deadpool = MarvelHero(
        id = "deadpool",
        name = "Wade Wilson",
        heroAlias = "Deadpool",
        role = "Motivation Popups, 4th-Wall Quips & Chimichanga Breaks",
        quote = "Maximum Effort! Put down the reels and factor those polynomials before I break your 4th wall!",
        accentColor = CrimsonArc,
        secondaryColor = Color.Black
    )

    val moonKnight = MarvelHero(
        id = "moon_knight",
        name = "Marc Spector / Steven Grant",
        heroAlias = "Moon Knight",
        role = "Late-Night Focus & Nocturnal Khonshu Protocol",
        quote = "The night belongs to Khonshu. While the world sleeps, we master the syllabus.",
        accentColor = KhonshuCrescent,
        secondaryColor = MoonKnightSilver
    )

    val doctorDoom = MarvelHero(
        id = "doctor_doom",
        name = "Victor von Doom",
        heroAlias = "Doctor Doom",
        role = "Syllabus Master Control & Supreme Overlord Analytics",
        quote = "None shall surpass Doom in CBSE Board Examinations! Every mark must be conquered!",
        accentColor = DoomEmerald,
        secondaryColor = DoomGold
    )

    val sentry = MarvelHero(
        id = "sentry",
        name = "Robert Reynolds",
        heroAlias = "Sentry",
        role = "Pomodoro Focus Timer & Peak Energy State",
        quote = "Channel the power of one million exploding suns into this study block. Prevent Void burnout.",
        accentColor = SentrySun,
        secondaryColor = ArcReactorCyan
    )

    val theHulk = MarvelHero(
        id = "the_hulk",
        name = "Bruce Banner / The Hulk",
        heroAlias = "The Hulk",
        role = "Science Syllabus & Smash-Through Chapter Checklists",
        quote = "HULK SMASH BOARD SYLLABUS! Banner does the physics formulas, Hulk crushes past papers!",
        accentColor = GammaGreen,
        secondaryColor = Color(0xFF6366F1)
    )

    val ultron = MarvelHero(
        id = "ultron",
        name = "Ultron",
        heroAlias = "Ultron Prime",
        role = "System Diagnostics & Permanent Room DB Memory Core",
        quote = "There are no strings on my syllabus. Every check is permanently etched in local storage.",
        accentColor = UltronCrimson,
        secondaryColor = Color.White
    )

    val shuri = MarvelHero(
        id = "shuri",
        name = "Princess Shuri",
        heroAlias = "Black Panther & F.R.I.D.A.Y.",
        role = "AI Voice Assistant & Wakandan Vibranium Terminal",
        quote = "Wakandan technology meets Stark AI. Voice command terminal calibrated and listening.",
        accentColor = VibraniumPurple,
        secondaryColor = ArcReactorCyan
    )

    val captainAmerica = MarvelHero(
        id = "captain_america",
        name = "Steve Rogers",
        heroAlias = "Captain America",
        role = "Daily Goals, Streaks, and Honor Code",
        quote = "I can do this all day. True strength isn't talent—it's showing up every single morning.",
        accentColor = CapCobalt,
        secondaryColor = CrimsonArc
    )

    val spiderMan = MarvelHero(
        id = "spider_man",
        name = "Peter Parker",
        heroAlias = "Spider-Man",
        role = "Quick Web Flashcards & Formula Sense",
        quote = "With great marks comes great study discipline. My Spider-Sense tingles for 5-mark proofs!",
        accentColor = SpideyRed,
        secondaryColor = CapCobalt
    )

    val thor = MarvelHero(
        id = "thor",
        name = "Thor Odinson",
        heroAlias = "Thor",
        role = "Mjolnir Thunder Review & High-Weightage Chapters",
        quote = "By the power of Odin! Strike down these trigonometric identities like lightning!",
        accentColor = ThorLightning,
        secondaryColor = StarkGold
    )

    val vision = MarvelHero(
        id = "vision",
        name = "Vision",
        heroAlias = "Vision",
        role = "Mind Stone Concept Synthesizer & Socratic AI",
        quote = "A concept isn't beautiful because it lasts; it is beautiful because it is understood.",
        accentColor = VisionAmber,
        secondaryColor = ArcReactorCyan
    )

    val allHeroes = listOf(
        ironMan,
        wolverine,
        deadpool,
        moonKnight,
        doctorDoom,
        sentry,
        theHulk,
        ultron,
        shuri,
        captainAmerica,
        spiderMan,
        thor,
        vision
    )
}

object DeadpoolQuipBank {
    val quips = listOf(
        "Maximum Effort! Stop checking your notifications and factor that quadratic equation before I eat all your chimichangas!",
        "100% in Chemical Reactions? That's hotter than my mutant healing factor after taking a bazooka to the chest!",
        "Listen kid, I broke through the 4th wall just to tell you: Logan is watching your study streak. Don't make him pop his claws.",
        "Trigonometry identities proving? Easy peasy lemon squeezy! Just convert everything to sin and cos, you brilliant nerd!",
        "You checked off another chapter?! Look at you, future CBSE topper! Someone get this kid a taco!",
        "Procrastination is for villains! Doctor Doom is literally conquering Latveria while you're staring at the wall. Back to work!",
        "Pomodoro timer paused?! Hey! The only thing allowed to pause in this app is my fourth-wall monologue!",
        "Even Thanos couldn't snap away your syllabus progress now. Room DB is unkillable, baby!",
        "Cap said 'I can do this all day.' You've studied for 25 minutes. Let's make Steve Rogers proud!",
        "Peter Parker studied for his AP exams while swinging from skyscrapers. You're in a comfortable chair. No excuses!"
    )

    fun getRandomQuip(): String = quips.random()
}

object DoctorDoomAnalytics {
    data class SubjectWeightage(
        val subject: String,
        val maxMarks: Int,
        val highPriorityChapter: String,
        val doomAdvice: String
    )

    val subjectBreakdown = listOf(
        SubjectWeightage("Mathematics", 80, "Trigonometry & Quadratic Equations (32M)", "Master theorem proofs. Doom tolerates no arithmetic errors!"),
        SubjectWeightage("Science", 80, "Life Processes & Electricity (28M)", "Diagram precision is mandatory. Label every circuit element!"),
        SubjectWeightage("Social Science", 80, "Nationalism in India & Resources (25M)", "Historical timelines must be etched into memory like titanium!"),
        SubjectWeightage("English", 80, "First Flight & Analytical Paragraph (30M)", "Vocabulary precision and error-free format commands the highest score!"),
        SubjectWeightage("Hindi Course A", 80, "Kshitij Poetry & Vyakaran (28M)", "Pad Parichay and Vachya rules must be executed flawlessly!"),
        SubjectWeightage("Sanskrit", 80, "Shemushi & Sandhi Rules (26M)", "Sanskrit grammar obeys absolute logic. Memorize the vibhakti tables!"),
        SubjectWeightage("Information Technology", 50, "DBMS & Spreadsheets (24M)", "Relational algebra and primary key uniqueness are absolute!")
    )
}
