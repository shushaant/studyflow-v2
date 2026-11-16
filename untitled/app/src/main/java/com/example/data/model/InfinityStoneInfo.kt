package com.example.data.model

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.example.R
import com.example.ui.theme.InfinityGauntletCore
import com.example.ui.theme.InfinityMindStone
import com.example.ui.theme.InfinityPowerStone
import com.example.ui.theme.InfinityRealityStone
import com.example.ui.theme.InfinitySoulStone
import com.example.ui.theme.InfinitySpaceStone
import com.example.ui.theme.InfinityTimeStone

/**
 * Classification of CBSE Class 10 Main Subjects as the Six MCU Infinity Stones.
 */
enum class InfinityStoneType(
    val stoneName: String,
    val cosmicTitle: String,
    val emblem: String,
    val color: Color,
    val guardianHero: String,
    val domain: String,
    val cosmicPower: String,
    @DrawableRes val drawableResId: Int? = null
) {
    MIND(
        stoneName = "Mind Stone",
        cosmicTitle = "THE MIND STONE // YELLOW COGNITION",
        emblem = "🟡",
        color = InfinityMindStone,
        guardianHero = "Vision & Iron Man",
        domain = "Mathematics (Algebra, Trigonometry & Proofs)",
        cosmicPower = "Infinite computational logic, synthetic reasoning, and analytical theorem proofs.",
        drawableResId = R.drawable.stone_mind
    ),
    POWER(
        stoneName = "Power Stone",
        cosmicTitle = "THE POWER STONE // PURPLE CATACLYSM",
        emblem = "🟣",
        color = InfinityPowerStone,
        guardianHero = "Bruce Banner (Hulk) & Thor",
        domain = "Science (Physics, Chemistry & Biology)",
        cosmicPower = "Raw atomic reactions, thermodynamics, electrical circuits, and cellular vitality.",
        drawableResId = R.drawable.stone_power
    ),
    SPACE(
        stoneName = "Space Stone",
        cosmicTitle = "THE SPACE STONE // BLUE TESSERACT",
        emblem = "🔵",
        color = InfinitySpaceStone,
        guardianHero = "Captain America & Captain Marvel",
        domain = "Social Science (Geography, History & Civics)",
        cosmicPower = "Spatial mastery across global trade routes, continents, resources, and civilizations.",
        drawableResId = R.drawable.stone_space
    ),
    TIME(
        stoneName = "Time Stone",
        cosmicTitle = "THE TIME STONE // GREEN AGAMOTTO",
        emblem = "🟢",
        color = InfinityTimeStone,
        guardianHero = "Doctor Strange & Doctor Doom",
        domain = "Sanskrit (Shemushi, Vyakaran & Timeless Epics)",
        cosmicPower = "Chrono-linguistic antiquity, eternal Vedic shlokas, and timeless grammar rules.",
        drawableResId = R.drawable.stone_time
    ),
    REALITY(
        stoneName = "Reality Stone",
        cosmicTitle = "THE REALITY STONE // RED AETHER",
        emblem = "🔴",
        color = InfinityRealityStone,
        guardianHero = "Scarlet Witch & Deadpool",
        domain = "English (Language & Literature — First Flight)",
        cosmicPower = "Bending narrative reality through creative prose, literary rhetoric, and poetic verse.",
        drawableResId = R.drawable.stone_reality
    ),
    SOUL(
        stoneName = "Soul Stone",
        cosmicTitle = "THE SOUL STONE // ORANGE VORMIR",
        emblem = "🟠",
        color = InfinitySoulStone,
        guardianHero = "Gamora & Black Panther",
        domain = "Hindi (Course A — Kshitij, Kritika & Kavya)",
        cosmicPower = "Spiritual essence of moral humanity, cultural conscience, and poetic motherland wisdom.",
        drawableResId = R.drawable.stone_soul
    ),
    GAUNTLET(
        stoneName = "Gauntlet Core",
        cosmicTitle = "THE INFINITY GAUNTLET // CYBER MATRIX",
        emblem = "⚡",
        color = InfinityGauntletCore,
        guardianHero = "Shuri & Ultron",
        domain = "Information Technology (Code 402 Skills)",
        cosmicPower = "Nanotech cybernetic conduit interconnecting and synchronizing all six cosmic stones.",
        drawableResId = null
    );

    companion object {
        fun fromSubjectId(subjectId: String): InfinityStoneType {
            return when (subjectId.lowercase()) {
                "math" -> MIND
                "science" -> POWER
                "sst", "social", "social_science" -> SPACE
                "sanskrit", "sans" -> TIME
                "english", "eng" -> REALITY
                "hindi", "hin" -> SOUL
                "it", "information_technology" -> GAUNTLET
                else -> MIND
            }
        }
    }
}
