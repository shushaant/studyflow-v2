package com.example.data.db

import com.example.data.model.ChapterEntity
import com.example.data.model.SavedMemoryEntity
import com.example.data.model.SubjectEntity

object DefaultSyllabusData {

    val subjects = listOf(
        SubjectEntity(
            id = "math",
            name = "Mathematics Standard / Basic",
            category = "Core Quantitative",
            code = "CBSE 041 / 241",
            iconName = "calculate",
            colorHex = "#4CD7F6",
            totalChapters = 14,
            completedChapters = 0,
            orderIndex = 1
        ),
        SubjectEntity(
            id = "science",
            name = "Science",
            category = "Phy / Chem / Bio",
            code = "CBSE 086",
            iconName = "science",
            colorHex = "#4EDEA3",
            totalChapters = 13,
            completedChapters = 0,
            orderIndex = 2
        ),
        SubjectEntity(
            id = "sst",
            name = "Social Science",
            category = "Hist / Geo / Pol / Eco",
            code = "CBSE 087",
            iconName = "public",
            colorHex = "#ADC6FF",
            totalChapters = 21,
            completedChapters = 0,
            orderIndex = 3
        ),
        SubjectEntity(
            id = "english",
            name = "English Language & Literature",
            category = "First Flight & Footprints",
            code = "CBSE 184",
            iconName = "menu_book",
            colorHex = "#F59E0B",
            totalChapters = 35,
            completedChapters = 0,
            orderIndex = 4
        ),
        SubjectEntity(
            id = "sanskrit",
            name = "Sanskrit",
            category = "Shemushi & Vyakaran",
            code = "CBSE 122",
            iconName = "auto_stories",
            colorHex = "#D8E2FF",
            totalChapters = 17,
            completedChapters = 0,
            orderIndex = 5
        ),
        SubjectEntity(
            id = "hindi",
            name = "Hindi Course-B",
            category = "Sparsh & Sanchayan",
            code = "CBSE 085",
            iconName = "translate",
            colorHex = "#FF9E66",
            totalChapters = 30,
            completedChapters = 0,
            orderIndex = 6
        ),
        SubjectEntity(
            id = "it",
            name = "Information Technology",
            category = "Employability & Skills",
            code = "CBSE 402",
            iconName = "computer",
            colorHex = "#1BBD85",
            totalChapters = 9,
            completedChapters = 0,
            orderIndex = 7
        )
    )

    // ALL CHAPTERS UNLEARNED ("NOT_STARTED", completedDate = null, completedSubtopics = 0)
    val chapters = listOf(
        // 1. Mathematics (14 chapters)
        ChapterEntity("math_1", "math", 1, "Real Numbers", "NOT_STARTED", 6, null, 4, 0),
        ChapterEntity("math_2", "math", 2, "Polynomials", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("math_3", "math", 3, "Pair of Linear Equations in Two Variables", "NOT_STARTED", 5, null, 4, 0),
        ChapterEntity("math_4", "math", 4, "Quadratic Equations", "NOT_STARTED", 6, null, 4, 0),
        ChapterEntity("math_5", "math", 5, "Arithmetic Progressions", "NOT_STARTED", 5, null, 4, 0),
        ChapterEntity("math_6", "math", 6, "Triangles", "NOT_STARTED", 8, null, 5, 0),
        ChapterEntity("math_7", "math", 7, "Coordinate Geometry", "NOT_STARTED", 6, null, 3, 0),
        ChapterEntity("math_8", "math", 8, "Introduction to Trigonometry", "NOT_STARTED", 8, null, 6, 0),
        ChapterEntity("math_9", "math", 9, "Some Applications of Trigonometry", "NOT_STARTED", 5, null, 3, 0),
        ChapterEntity("math_10", "math", 10, "Circles", "NOT_STARTED", 6, null, 4, 0),
        ChapterEntity("math_11", "math", 11, "Areas Related to Circles", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("math_12", "math", 12, "Surface Areas and Volumes", "NOT_STARTED", 7, null, 4, 0),
        ChapterEntity("math_13", "math", 13, "Statistics", "NOT_STARTED", 8, null, 5, 0),
        ChapterEntity("math_14", "math", 14, "Probability", "NOT_STARTED", 4, null, 3, 0),

        // 2. Science (13 chapters)
        ChapterEntity("sci_1", "science", 1, "Chemical Reactions and Equations", "NOT_STARTED", 5, null, 4, 0),
        ChapterEntity("sci_2", "science", 2, "Acids, Bases and Salts", "NOT_STARTED", 6, null, 4, 0),
        ChapterEntity("sci_3", "science", 3, "Metals and Non-metals", "NOT_STARTED", 7, null, 5, 0),
        ChapterEntity("sci_4", "science", 4, "Carbon and its Compounds", "NOT_STARTED", 6, null, 4, 0),
        ChapterEntity("sci_5", "science", 5, "Life Processes", "NOT_STARTED", 8, null, 5, 0),
        ChapterEntity("sci_6", "science", 6, "Control and Coordination", "NOT_STARTED", 6, null, 4, 0),
        ChapterEntity("sci_7", "science", 7, "How do Organisms Reproduce?", "NOT_STARTED", 6, null, 4, 0),
        ChapterEntity("sci_8", "science", 8, "Heredity", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("sci_9", "science", 9, "Light - Reflection and Refraction", "NOT_STARTED", 8, null, 5, 0),
        ChapterEntity("sci_10", "science", 10, "The Human Eye and the Colourful World", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("sci_11", "science", 11, "Electricity", "NOT_STARTED", 7, null, 5, 0),
        ChapterEntity("sci_12", "science", 12, "Magnetic Effects of Electric Current", "NOT_STARTED", 6, null, 4, 0),
        ChapterEntity("sci_13", "science", 13, "Our Environment", "NOT_STARTED", 4, null, 3, 0),

        // 3. Social Science (21 chapters)
        // History (5)
        ChapterEntity("sst_1", "sst", 1, "History: The Rise of Nationalism in Europe", "NOT_STARTED", 6, null, 4, 0),
        ChapterEntity("sst_2", "sst", 2, "History: Nationalism in India", "NOT_STARTED", 6, null, 4, 0),
        ChapterEntity("sst_3", "sst", 3, "History: The Making of a Global World", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("sst_4", "sst", 4, "History: The Age of Industrialisation", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("sst_5", "sst", 5, "History: Print Culture and the Modern World", "NOT_STARTED", 4, null, 3, 0),
        // Geography (7)
        ChapterEntity("sst_6", "sst", 6, "Geography: Resources and Development", "NOT_STARTED", 5, null, 3, 0),
        ChapterEntity("sst_7", "sst", 7, "Geography: Forest and Wildlife Resources", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("sst_8", "sst", 8, "Geography: Water Resources", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("sst_9", "sst", 9, "Geography: Agriculture", "NOT_STARTED", 5, null, 4, 0),
        ChapterEntity("sst_10", "sst", 10, "Geography: Minerals and Energy Resources", "NOT_STARTED", 5, null, 4, 0),
        ChapterEntity("sst_11", "sst", 11, "Geography: Manufacturing Industries", "NOT_STARTED", 5, null, 4, 0),
        ChapterEntity("sst_12", "sst", 12, "Geography: Lifelines of National Economy", "NOT_STARTED", 4, null, 3, 0),
        // Political Science (5)
        ChapterEntity("sst_13", "sst", 13, "Political Science: Power Sharing", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("sst_14", "sst", 14, "Political Science: Federalism", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("sst_15", "sst", 15, "Political Science: Gender Religion and Caste", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("sst_16", "sst", 16, "Political Science: Political Parties", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("sst_17", "sst", 17, "Political Science: Outcomes of Democracy", "NOT_STARTED", 4, null, 3, 0),
        // Economics (4)
        ChapterEntity("sst_18", "sst", 18, "Economics: Development", "NOT_STARTED", 5, null, 3, 0),
        ChapterEntity("sst_19", "sst", 19, "Economics: Sectors of the Indian Economy", "NOT_STARTED", 6, null, 4, 0),
        ChapterEntity("sst_20", "sst", 20, "Economics: Money and Credit", "NOT_STARTED", 5, null, 4, 0),
        ChapterEntity("sst_21", "sst", 21, "Economics: Globalisation and the Indian Economy", "NOT_STARTED", 5, null, 3, 0),

        // 4. English Language and Literature (35 chapters)
        // First Flight Prose (9)
        ChapterEntity("eng_1", "english", 1, "Prose: A Letter to God", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("eng_2", "english", 2, "Prose: Nelson Mandela: Long Walk to Freedom", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("eng_3", "english", 3, "Prose: Two Stories about Flying", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("eng_4", "english", 4, "Prose: From the Diary of Anne Frank", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("eng_5", "english", 5, "Prose: Glimpses of India", "NOT_STARTED", 5, null, 4, 0),
        ChapterEntity("eng_6", "english", 6, "Prose: Mijbil the Otter", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("eng_7", "english", 7, "Prose: Madam Rides the Bus", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("eng_8", "english", 8, "Prose: The Sermon at Benares", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("eng_9", "english", 9, "Prose: The Proposal", "NOT_STARTED", 5, null, 3, 0),
        // First Flight Poems (10)
        ChapterEntity("eng_10", "english", 10, "Poem: Dust of Snow", "NOT_STARTED", 3, null, 2, 0),
        ChapterEntity("eng_11", "english", 11, "Poem: Fire and Ice", "NOT_STARTED", 3, null, 2, 0),
        ChapterEntity("eng_12", "english", 12, "Poem: A Tiger in the Zoo", "NOT_STARTED", 3, null, 2, 0),
        ChapterEntity("eng_13", "english", 13, "Poem: How to Tell Wild Animals", "NOT_STARTED", 3, null, 2, 0),
        ChapterEntity("eng_14", "english", 14, "Poem: The Ball Poem", "NOT_STARTED", 3, null, 2, 0),
        ChapterEntity("eng_15", "english", 15, "Poem: Amanda!", "NOT_STARTED", 3, null, 2, 0),
        ChapterEntity("eng_16", "english", 16, "Poem: The Trees", "NOT_STARTED", 3, null, 2, 0),
        ChapterEntity("eng_17", "english", 17, "Poem: Fog", "NOT_STARTED", 3, null, 2, 0),
        ChapterEntity("eng_18", "english", 18, "Poem: The Tale of Custard the Dragon", "NOT_STARTED", 3, null, 2, 0),
        ChapterEntity("eng_19", "english", 19, "Poem: For Anne Gregory", "NOT_STARTED", 3, null, 2, 0),
        // Footprints Without Feet (9)
        ChapterEntity("eng_20", "english", 20, "Footprints: A Triumph of Surgery", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("eng_21", "english", 21, "Footprints: The Thief's Story", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("eng_22", "english", 22, "Footprints: The Midnight Visitor", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("eng_23", "english", 23, "Footprints: A Question of Trust", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("eng_24", "english", 24, "Footprints: Footprints Without Feet", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("eng_25", "english", 25, "Footprints: The Making of a Scientist", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("eng_26", "english", 26, "Footprints: The Necklace", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("eng_27", "english", 27, "Footprints: Bholi", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("eng_28", "english", 28, "Footprints: The Book That Saved the Earth", "NOT_STARTED", 4, null, 3, 0),
        // Grammar & Writing (7)
        ChapterEntity("eng_29", "english", 29, "Grammar: Tenses", "NOT_STARTED", 3, null, 3, 0),
        ChapterEntity("eng_30", "english", 30, "Grammar: Modals", "NOT_STARTED", 3, null, 3, 0),
        ChapterEntity("eng_31", "english", 31, "Grammar: Subject-Verb Concord", "NOT_STARTED", 3, null, 3, 0),
        ChapterEntity("eng_32", "english", 32, "Grammar: Reported Speech (Commands, Requests)", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("eng_33", "english", 33, "Grammar: Statements & Questions", "NOT_STARTED", 3, null, 3, 0),
        ChapterEntity("eng_34", "english", 34, "Writing: Formal Letter Writing", "NOT_STARTED", 5, null, 3, 0),
        ChapterEntity("eng_35", "english", 35, "Writing: Analytical Paragraph", "NOT_STARTED", 5, null, 3, 0),

        // 5. Hindi Course A (30 chapters)
        // Kshitij Prose (8)
        ChapterEntity("hin_1", "hindi", 1, "Kshitij: Netaji ka Chashma", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("hin_2", "hindi", 2, "Kshitij: Balgobin Bhagat", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("hin_3", "hindi", 3, "Kshitij: Lakhvi Andaz", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("hin_4", "hindi", 4, "Kshitij: Manviya Karuna ki Divya Chamak", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("hin_5", "hindi", 5, "Kshitij: Ek Kahani Yeh Bhi", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("hin_6", "hindi", 6, "Kshitij: Stree-shiksha ke Virodhi Kutarko ka Khandan", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("hin_7", "hindi", 7, "Kshitij: Naubatkhane mein Ibadat", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("hin_8", "hindi", 8, "Kshitij: Sanskriti", "NOT_STARTED", 4, null, 3, 0),
        // Kshitij Poetry (9)
        ChapterEntity("hin_9", "hindi", 9, "Poetry: Surdas ke Pad", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("hin_10", "hindi", 10, "Poetry: Ram-Lakshman-Parshuram Samvad", "NOT_STARTED", 5, null, 3, 0),
        ChapterEntity("hin_11", "hindi", 11, "Poetry: Swayamprabha / Dev", "NOT_STARTED", 3, null, 2, 0),
        ChapterEntity("hin_12", "hindi", 12, "Poetry: Atmakathya", "NOT_STARTED", 3, null, 2, 0),
        ChapterEntity("hin_13", "hindi", 13, "Poetry: Utsah aur At Nahi Rahi Hai", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("hin_14", "hindi", 14, "Poetry: Yah Danturit Muskan aur Fasal", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("hin_15", "hindi", 15, "Poetry: Chhaya Mat Chhoo", "NOT_STARTED", 3, null, 2, 0),
        ChapterEntity("hin_16", "hindi", 16, "Poetry: Kanya Dan", "NOT_STARTED", 3, null, 2, 0),
        ChapterEntity("hin_17", "hindi", 17, "Poetry: Sangatkar", "NOT_STARTED", 3, null, 2, 0),
        // Kritika Part 2 (5)
        ChapterEntity("hin_18", "hindi", 18, "Kritika: Mata ka Anchal", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("hin_19", "hindi", 19, "Kritika: George Pancham ki Naak", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("hin_20", "hindi", 20, "Kritika: Sana-Sana Hath Jodi...", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("hin_21", "hindi", 21, "Kritika: Ahiaiya Jhulan Heri Ma Harni!", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("hin_22", "hindi", 22, "Kritika: Kahiyan Thula Jhule Rahni Aisi!", "NOT_STARTED", 4, null, 3, 0),
        // Vyakaran & Rachna (8)
        ChapterEntity("hin_23", "hindi", 23, "Vyakaran: Pad Parichay", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("hin_24", "hindi", 24, "Vyakaran: Vachya", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("hin_25", "hindi", 25, "Vyakaran: Ras", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("hin_26", "hindi", 26, "Vyakaran: Vakya Bhed", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("hin_27", "hindi", 27, "Rachna: Anuchhed Lekhan", "NOT_STARTED", 5, null, 3, 0),
        ChapterEntity("hin_28", "hindi", 28, "Rachna: Patra Lekhan", "NOT_STARTED", 5, null, 3, 0),
        ChapterEntity("hin_29", "hindi", 29, "Rachna: Vigyapan Lekhan", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("hin_30", "hindi", 30, "Rachna: Sandesh Lekhan", "NOT_STARTED", 4, null, 3, 0),

        // 6. Sanskrit (17 chapters)
        // Shemushi Part 2 (9)
        ChapterEntity("san_1", "sanskrit", 1, "Shemushi: Suchi Paryavaran", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("san_2", "sanskrit", 2, "Shemushi: Buddhirbalvati Sadi", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("san_3", "sanskrit", 3, "Shemushi: Shishulalanam", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("san_4", "sanskrit", 4, "Shemushi: Janani Tutsa Vatsala", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("san_5", "sanskrit", 5, "Shemushi: Subhashitani", "NOT_STARTED", 5, null, 4, 0),
        ChapterEntity("san_6", "sanskrit", 6, "Shemushi: Sauhridam Praktershobha", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("san_7", "sanskrit", 7, "Shemushi: Vichitra Sakshi", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("san_8", "sanskrit", 8, "Shemushi: Suktayah", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("san_9", "sanskrit", 9, "Shemushi: Anuktayah", "NOT_STARTED", 4, null, 3, 0),
        // Vyakaran (8)
        ChapterEntity("san_10", "sanskrit", 10, "Vyakaran: Sandhi (Vyanjan & Visarga)", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("san_11", "sanskrit", 11, "Vyakaran: Samas (Tatpurush, Bahuvrihi)", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("san_12", "sanskrit", 12, "Vyakaran: Pratyay (Matup, Tva, Tal)", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("san_13", "sanskrit", 13, "Vyakaran: Avyaya Padani", "NOT_STARTED", 3, null, 2, 0),
        ChapterEntity("san_14", "sanskrit", 14, "Vyakaran: Karak-Upapad Vibhakti", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("san_15", "sanskrit", 15, "Vyakaran: Vachya Parivartan", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("san_16", "sanskrit", 16, "Vyakaran: Samay Lekhan", "NOT_STARTED", 3, null, 2, 0),
        ChapterEntity("san_17", "sanskrit", 17, "Vyakaran: Shuddhi-Sanshodhan", "NOT_STARTED", 3, null, 2, 0),

        // 7. Information Technology (IT - Code 402) (9 chapters)
        // Part A Employability Skills (5)
        ChapterEntity("it_1", "it", 1, "Part A: Communication Skills", "NOT_STARTED", 5, null, 3, 0),
        ChapterEntity("it_2", "it", 2, "Part A: Self-Management Skills", "NOT_STARTED", 5, null, 3, 0),
        ChapterEntity("it_3", "it", 3, "Part A: Information & Comm. Tech Skills", "NOT_STARTED", 5, null, 3, 0),
        ChapterEntity("it_4", "it", 4, "Part A: Entrepreneurial Skills", "NOT_STARTED", 4, null, 3, 0),
        ChapterEntity("it_5", "it", 5, "Part A: Green Skills", "NOT_STARTED", 4, null, 3, 0),
        // Part B Subject Specific Skills (4)
        ChapterEntity("it_6", "it", 6, "Part B: Digital Documentation (Advanced)", "NOT_STARTED", 8, null, 4, 0),
        ChapterEntity("it_7", "it", 7, "Part B: Electronic Spreadsheet (Advanced)", "NOT_STARTED", 8, null, 4, 0),
        ChapterEntity("it_8", "it", 8, "Part B: Database Management System", "NOT_STARTED", 8, null, 4, 0),
        ChapterEntity("it_9", "it", 9, "Part B: Web Applications and Security", "NOT_STARTED", 8, null, 4, 0)
    )

    // Initial pre-configured Saved Memories
    val defaultMemories = listOf(
        SavedMemoryEntity(
            subjectId = "math",
            subjectName = "Mathematics",
            chapterTitle = "Ch 8: Introduction to Trigonometry",
            conceptTitle = "Pythagorean Identity #1",
            memoryContent = "sin²(θ) + cos²(θ) = 1",
            mnemonic = "Fundamental identity in right triangle: (P/H)² + (B/H)² = (P²+B²)/H² = 1",
            isPinned = true
        ),
        SavedMemoryEntity(
            subjectId = "math",
            subjectName = "Mathematics",
            chapterTitle = "Ch 4: Quadratic Equations",
            conceptTitle = "Quadratic Roots Formula (Shridharacharya)",
            memoryContent = "x = (-b ± √(b² - 4ac)) / (2a)",
            mnemonic = "Discriminant D = b² - 4ac: D>0 (2 real distinct), D=0 (equal), D<0 (no real)",
            isPinned = true
        ),
        SavedMemoryEntity(
            subjectId = "science",
            subjectName = "Science",
            chapterTitle = "Ch 11: Electricity",
            conceptTitle = "Ohm's Law & Resistance",
            memoryContent = "V = I × R (Potential Diff = Current × Resistance). Resistance: R = ρ(L/A)",
            mnemonic = "VIR triangle: Cover V to find I×R, cover I to find V/R",
            isPinned = true
        ),
        SavedMemoryEntity(
            subjectId = "science",
            subjectName = "Science",
            chapterTitle = "Ch 5: Life Processes",
            conceptTitle = "Photosynthesis Balanced Equation",
            memoryContent = "6CO₂ + 12H₂O + Sunlight + Chlorophyll → C₆H₁₂O₆ + 6O₂ + 6H₂O",
            mnemonic = "6-12-Sunlight yields 1 Glucose + 6 Oxygen + 6 Water",
            isPinned = false
        ),
        SavedMemoryEntity(
            subjectId = "sst",
            subjectName = "Social Science",
            chapterTitle = "History: Rise of Nationalism",
            conceptTitle = "Treaty of Vienna (1815)",
            memoryContent = "Representatives of Britain, Russia, Prussia & Austria met to undo changes of Napoleonic wars and restore Bourbon monarchy.",
            mnemonic = "B-R-P-A four major powers at Vienna hosted by Duke Metternich",
            isPinned = false
        ),
        SavedMemoryEntity(
            subjectId = "it",
            subjectName = "Information Technology",
            chapterTitle = "Part B: Database Management",
            conceptTitle = "Primary Key vs Foreign Key",
            memoryContent = "Primary Key uniquely identifies each record in table (cannot be NULL). Foreign Key references Primary Key in another table to establish relation.",
            mnemonic = "PK = Unique ID; FK = Relationship bridge",
            isPinned = false
        )
    )
}
