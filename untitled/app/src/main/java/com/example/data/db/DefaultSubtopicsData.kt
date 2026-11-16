package com.example.data.db

import com.example.data.model.ChapterEntity
import com.example.data.model.SubtopicEntity

object DefaultSubtopicsData {

    val chapterTopicsMap: Map<String, List<String>> = mapOf(
        // ==================== 1. MATHEMATICS (CBSE 041) ====================
        "math_1" to listOf(
            "The Fundamental Theorem of Arithmetic",
            "Revisiting Irrational Numbers (Proofs for √2, √3, √5)",
            "Revisiting Rational Numbers & Decimal Expansions",
            "Applications of HCF and LCM in Real-World Problems"
        ),
        "math_2" to listOf(
            "Geometrical Meaning of the Zeroes of a Polynomial",
            "Relationship between Zeroes and Coefficients of Quadratic Polynomials",
            "Factorisation & Verification of Zeroes",
            "Division Algorithm & Polynomial Identities"
        ),
        "math_3" to listOf(
            "Graphical Method of Finding Solution of Linear Equations",
            "Consistency and Inconsistency Conditions (a1/a2, b1/b2, c1/c2)",
            "Algebraic Methods: Substitution Method",
            "Algebraic Methods: Elimination Method",
            "Word Problems (Ages, Speeds, Fractions, Costs)"
        ),
        "math_4" to listOf(
            "Standard Form of Quadratic Equations (ax² + bx + c = 0)",
            "Solution of Quadratic Equations by Factorisation",
            "Solution using Quadratic Formula (Shridharacharya Method)",
            "Nature of Roots & Discriminant (D = b² - 4ac)"
        ),
        "math_5" to listOf(
            "Introduction to Arithmetic Progressions (AP), First Term & Common Diff",
            "Finding the nth Term of an AP (an = a + (n-1)d)",
            "Sum of First n Terms of an AP (Sn = n/2 [2a + (n-1)d])",
            "Applications of AP in Solving Daily Life Problems"
        ),
        "math_6" to listOf(
            "Basic Proportionality Theorem (Thales Theorem) & Its Converse",
            "Criteria for Similarity of Triangles (AAA, SSS, SAS)",
            "Properties & Ratio of Areas of Similar Triangles",
            "Pythagoras Theorem Proof & Proving Geometric Relations"
        ),
        "math_7" to listOf(
            "Distance Formula & Collinearity of Three Points",
            "Section Formula (Internal Division) & Coordinates",
            "Midpoint Formula & Centroid of a Triangle",
            "Applications to Geometric Figures (Parallelograms, Rhombuses)"
        ),
        "math_8" to listOf(
            "Trigonometric Ratios (sin, cos, tan, cosec, sec, cot)",
            "Trigonometric Ratios of Specific Angles (0°, 30°, 45°, 60°, 90°)",
            "Fundamental Pythagorean Identities (sin²θ + cos²θ = 1, 1+tan²θ=sec²θ)",
            "Proving Complex Trigonometric Identities"
        ),
        "math_9" to listOf(
            "Horizontal Line, Line of Sight, Angle of Elevation & Depression",
            "Single Right-Triangle Height and Distance Problems",
            "Two-Triangle Problems (Ships, Balloons, Towers, Buildings)",
            "Speed, Distance & Shadow Variation Problems"
        ),
        "math_10" to listOf(
            "Tangent to a Circle at Point of Contact & Secant",
            "Theorem 10.1: Tangent is Perpendicular to Radius at Point of Contact",
            "Theorem 10.2: Lengths of Tangents from an External Point are Equal",
            "Circumscribing and Inscribing Circle Proof Problems"
        ),
        "math_11" to listOf(
            "Perimeter and Area of a Circle Review",
            "Area of Sector of a Circle (Major and Minor Sectors)",
            "Area of Segment of a Circle (Major and Minor Segments)",
            "Areas of Combinations of Plane Figures (Designs, Rings, Shaded Regions)"
        ),
        "math_12" to listOf(
            "Surface Area of Combinations of Solids (Cylinders, Cones, Hemispheres)",
            "Volume of Combinations of Solids",
            "Conversion of Solids from One Shape to Another",
            "Frustum of a Cone Formulas & Applications"
        ),
        "math_13" to listOf(
            "Mean of Grouped Data: Direct Method",
            "Mean of Grouped Data: Assumed Mean Method",
            "Mode of Grouped Data & Modal Class",
            "Median of Grouped Data & Cumulative Frequency",
            "Empirical Relationship: 3 Median = Mode + 2 Mean"
        ),
        "math_14" to listOf(
            "Classical Definition of Probability: P(E) = n(E) / n(S)",
            "Elementary Events & Impossible vs Certain Events",
            "Complementary Events (P(E) + P(not E) = 1)",
            "Problems on Tossing Coins, Rolling Dice & Deck of 52 Cards"
        ),

        // ==================== 2. SCIENCE (CBSE 086) ====================
        "sci_1" to listOf(
            "Chemical Equations & Writing Balanced Chemical Equations",
            "Combination and Decomposition Reactions (Thermal, Electrolytic, Photolytic)",
            "Displacement and Double Displacement Reactions (Precipitation)",
            "Oxidation and Reduction (Redox Reactions)",
            "Effects of Oxidation in Everyday Life (Corrosion & Rancidity)"
        ),
        "sci_2" to listOf(
            "Chemical Properties of Acids and Bases (Reaction with Metals & Carbonates)",
            "Neutralisation Reaction & What All Acids/Bases Have in Common",
            "How Strong Are Acid or Base Solutions? The pH Scale in Daily Life",
            "Salts: Common Salt, Bleaching Powder & Baking Soda",
            "Washing Soda, Water of Crystallisation & Plaster of Paris"
        ),
        "sci_3" to listOf(
            "Physical & Chemical Properties of Metals and Non-Metals",
            "Reactivity Series of Metals & Reaction with Water/Acids",
            "Formation & Properties of Ionic Compounds",
            "Metallurgy: Extraction of Metals (Roasting & Calcination)",
            "Corrosion of Metals and Methods of Prevention"
        ),
        "sci_4" to listOf(
            "Covalent Bonding in Carbon Compounds & Electron Dot Structures",
            "Versatile Nature of Carbon: Catenation & Tetravalency",
            "Saturated and Unsaturated Hydrocarbons (Alkanes, Alkenes, Alkynes)",
            "Homologous Series & Nomenclature of Functional Groups",
            "Chemical Properties: Combustion, Oxidation, Addition & Substitution",
            "Important Carbon Compounds: Ethanol, Ethanoic Acid & Soaps/Detergents"
        ),
        "sci_5" to listOf(
            "Autotrophic Nutrition: Photosynthesis & Stomata Regulation",
            "Heterotrophic Nutrition: Human Alimentary Canal & Digestive Enzymes",
            "Respiration: Aerobic vs Anaerobic Breakdown of Glucose & Alveoli",
            "Transportation in Humans: Heart Structure, Blood Vessels & Lymph",
            "Transportation in Plants: Xylem (Water/Minerals) & Phloem (Food Translocation)",
            "Excretion: Human Excretory System, Structure of Nephron & Plant Excretion"
        ),
        "sci_6" to listOf(
            "Nervous System: Structure of a Neuron & Neuromuscular Junction",
            "Reflex Actions and Reflex Arc Pathway",
            "Human Brain: Forebrain, Midbrain, Hindbrain & Spinal Cord Protection",
            "Coordination in Plants: Nastic Movements and Tropic Movements (Phototropism, Geotropism)",
            "Plant Hormones (Auxin, Gibberellin, Cytokinin, Abscisic Acid)",
            "Hormones in Animals: Endocrine Glands, Thyroid, Adrenal, Pancreas & Pituitary"
        ),
        "sci_7" to listOf(
            "Asexual Reproduction: Fission, Fragmentation, Regeneration & Budding",
            "Vegetative Propagation & Spore Formation",
            "Sexual Reproduction in Flowering Plants: Stamen, Carpel, Pollination & Fertilisation",
            "Reproduction in Human Beings: Male and Female Reproductive Systems",
            "Fertilisation, Menstrual Cycle, Embryo Development & Implantation",
            "Reproductive Health: Contraceptive Methods & Prevention of STDs"
        ),
        "sci_8" to listOf(
            "Heredity and Accumulation of Variation during Reproduction",
            "Mendel's Experiments: Monohybrid Cross and Law of Segregation",
            "Dihybrid Cross and Law of Independent Assortment",
            "Expression of Traits through Cellular DNA and Proteins",
            "Sex Determination in Human Beings: Role of X and Y Chromosomes"
        ),
        "sci_9" to listOf(
            "Reflection of Light & Laws of Reflection",
            "Spherical Mirrors (Concave & Convex): Ray Diagrams and Image Formation",
            "New Cartesian Sign Convention & Mirror Formula (1/v + 1/u = 1/f)",
            "Linear Magnification Produced by Mirrors",
            "Refraction of Light, Snell's Law & Refractive Index",
            "Refraction by Spherical Lenses: Lens Formula, Magnification & Power of a Lens"
        ),
        "sci_10" to listOf(
            "Structure of the Human Eye & Power of Accommodation",
            "Defects of Vision: Myopia, Hypermetropia, Presbyopia and Their Corrections",
            "Refraction of Light through a Triangular Glass Prism",
            "Dispersion of White Light & Rainbow Formation",
            "Atmospheric Refraction: Twinkling of Stars & Advanced Sunrise/Delayed Sunset",
            "Scattering of Light: Tyndall Effect & Why the Clear Sky is Blue"
        ),
        "sci_11" to listOf(
            "Electric Current, Circuit Diagram, Ampere & Electric Charge (Q = It)",
            "Electric Potential and Potential Difference (V = W/Q)",
            "Ohm's Law: Mathematical Formulation and V-I Graph",
            "Factors Affecting Resistance & Electrical Resistivity of Conductors",
            "Resistors in Series Combination: Equivalent Resistance Formulation",
            "Resistors in Parallel Combination & Advantages of Parallel Circuits",
            "Heating Effect of Electric Current (Joule's Law of Heating: H = I²Rt)",
            "Electric Power (P = VI = I²R = V²/R) & Commercial Unit of Electrical Energy"
        ),
        "sci_12" to listOf(
            "Magnetic Field and Magnetic Field Lines around a Bar Magnet",
            "Magnetic Field due to a Straight Current-Carrying Conductor & Right-Hand Thumb Rule",
            "Magnetic Field due to a Circular Loop and Solenoid (Electromagnet)",
            "Force on a Current-Carrying Conductor in a Magnetic Field",
            "Fleming's Left-Hand Rule and Electric Motor Principle",
            "Electromagnetic Induction & Fleming's Right-Hand Rule",
            "Domestic Electric Circuits: Earth Wire, Live Wire, Neutral Wire, Fuse & Short-Circuit"
        ),
        "sci_13" to listOf(
            "Ecosystem: Biotic and Abiotic Components & Ecosystem Types",
            "Food Chains and Food Webs: Producers, Consumers and Decomposers",
            "Trophic Levels and 10% Energy Flow Law in Ecosystems",
            "Biological Magnification of Harmful Chemicals (Pesticides)",
            "Ozone Layer Depletion, Chlorofluorocarbons (CFCs) & UNEP Protocol",
            "Managing Garbage: Biodegradable vs Non-Biodegradable Waste Disposal"
        ),

        // ==================== 3. SOCIAL SCIENCE (CBSE 087) ====================
        // History
        "sst_1" to listOf(
            "The French Revolution and the Idea of the Nation (Civil Code of 1804)",
            "The Making of Nationalism in Europe & Liberal Nationalism",
            "A New Conservatism after 1815 & The Treaty of Vienna",
            "The Age of Revolutions: 1830–1848 & The Romantic Imagination",
            "The Making of Germany and Italy (Unification Processes)",
            "Visualising the Nation: Allegories (Marianne and Germania)",
            "Nationalism and Imperialism: The Balkan Powder Keg Crisis"
        ),
        "sst_2" to listOf(
            "The First World War, Khilafat and Non-Cooperation Movement",
            "Differing Strands within the Movement: Towns, Countryside (Awadh), and Tribals",
            "Swaraj in the Plantations (Inland Emigration Act 1859)",
            "Towards Civil Disobedience: Simon Commission & Purna Swaraj",
            "The Salt March to Dandi & Civil Disobedience Movement Launch",
            "How Participants Saw the Movement: Rich Peasants, Poor Peasants, Business Class",
            "The Limits of Civil Disobedience: Dalits, Poona Pact 1932 & Muslim Leagues",
            "The Sense of Collective Belonging: Bharat Mata, Folklore, and Tricolour Flag"
        ),
        "sst_3" to listOf(
            "The Pre-modern World: Ancient Silk Routes & Food Travels (Spaghetti & Potato)",
            "Conquest, Disease and Trade: Smallpox in the Americas",
            "The 19th Century (1815–1914): Flow of Trade, Labour, and Capital",
            "Late 19th Century Colonialism & Rinderpest Cattle Plague in Africa",
            "Indentured Labour Migration from India to Caribbean & Fiji",
            "The Inter-War Economy: Mass Production (Henry Ford) & The Great Depression",
            "Post-War Settlement and Bretton Woods Institutions (IMF and World Bank)"
        ),
        "sst_4" to listOf(
            "Before the Industrial Revolution: Proto-Industrialisation",
            "Hand Labour and Steam Power in Victorian Britain",
            "Life of the Workers and Seasonal Employment in Factories",
            "Industrialisation in the Colonies: The Age of Indian Textiles",
            "What Happened to Weavers? Gomasthas and Clashing Interests",
            "Factories Come Up in India: Bombay Cotton Mills & Calcutta Jute Mills",
            "The Peculiarities of Industrial Growth & Market for Goods (Advertising & Labels)"
        ),
        "sst_5" to listOf(
            "The First Printed Books in China, Japan (Diamond Sutra) and Korea",
            "Print Comes to Europe: Marco Polo & Gutenberg's Printing Press",
            "The Print Revolution and Its Impact on Religious Debates (Martin Luther)",
            "The Reading Mania, Penny Chapbooks, and Enlightenment Thinkers",
            "Print Culture and the French Revolution: Spreading Critical Thinking",
            "The 19th Century: Children, Women and Workers as Readers",
            "India and the World of Print: Religious Reforms, Women's Lives, and Vernacular Press"
        ),
        // Geography
        "sst_6" to listOf(
            "Classification of Resources: Origin, Exhaustibility, Ownership & Status",
            "Development of Resources and Sustainable Development (Rio Summit 1992, Agenda 21)",
            "Resource Planning in India: Need, Steps and Conservation of Resources",
            "Land Resources and Land Use Pattern in India",
            "Land Degradation and Conservation Measures",
            "Soil as a Resource: Classification (Alluvial, Black, Red, Laterite, Arid, Forest)",
            "Soil Erosion, Causes and Soil Conservation Techniques (Contour Ploughing, Shelter Belts)"
        ),
        "sst_7" to listOf(
            "Flora and Fauna in India & IUCN Classification of Species",
            "Factors Causing Depletion of Flora and Fauna",
            "Conservation of Forest and Wildlife in India: Wildlife Protection Act 1972",
            "Project Tiger, National Parks, and Biosphere Reserves",
            "Types and Distribution of Forests (Reserved, Protected, Unclassed Forests)",
            "Community and Conservation: Chipko Movement, Beej Bachao Andolan, Joint Forest Management"
        ),
        "sst_8" to listOf(
            "Water Scarcity and the Need for Water Conservation and Management",
            "Multi-Purpose River Valley Projects: Benefits and Environmental Criticisms",
            "Inter-State Water Disputes and Movement against Big Dams (Narmada Bachao Andolan)",
            "Rainwater Harvesting Systems: Guls/Kuls, Johads, Rooftop Harvesting & Bamboo Drip Irrigation"
        ),
        "sst_9" to listOf(
            "Types of Farming: Primitive Subsistence, Intensive Subsistence, and Commercial Farming",
            "Cropping Pattern: Rabi, Kharif, and Zaid Seasons",
            "Major Food Crops: Rice, Wheat, Millets (Jowar, Bajra, Ragi), and Maize",
            "Food Crops Other than Grains: Sugarcane, Oilseeds, Tea, Coffee, and Horticulture",
            "Non-Food Crops: Rubber, Fibre Crops (Cotton, Jute / Golden Fibre)",
            "Technological and Institutional Reforms: Land Reforms, Green Revolution & Bhoodan-Gramdan",
            "Contribution of Agriculture to the National Economy & Food Security"
        ),
        "sst_10" to listOf(
            "What is a Mineral? Modes of Occurrence of Minerals",
            "Ferrous Minerals: Iron Ore (Hematite, Magnetite) and Manganese",
            "Non-Ferrous Minerals: Copper and Bauxite",
            "Non-Metallic Minerals: Mica and Limestone (Rock Minerals)",
            "Conservation of Minerals: Need and Recycling Measures",
            "Conventional Sources of Energy: Coal, Petroleum, Natural Gas, Electricity (Thermal & Hydel)",
            "Non-Conventional Sources of Energy: Solar, Wind, Biogas, Tidal, and Geo-Thermal Energy"
        ),
        "sst_11" to listOf(
            "Importance of Manufacturing and Contribution to National Economy",
            "Industrial Location Factors and Agglomeration Economies",
            "Classification of Industries: Raw Materials, Role, Capital Investment, Ownership",
            "Agro-Based Industries: Textile Industry (Cotton, Jute) and Sugar Industry",
            "Mineral-Based Industries: Iron and Steel Industry, Aluminium Smelting, Chemical Industries",
            "Fertiliser, Cement, and Automobile Industries & Information Technology / Electronics",
            "Industrial Pollution and Environmental Degradation: Air, Water, Thermal, and Noise Pollution",
            "Control of Environmental Degradation: Effluent Treatment, Rainwater Harvesting, NTPC Practices"
        ),
        "sst_12" to listOf(
            "Transport Systems: Roadways & Golden Quadrilateral Super Highways",
            "National Highways, State Highways, District Roads, Rural Roads & Border Roads",
            "Railways: Distribution Factors and Significance in Indian Economy",
            "Pipelines: Key Networks (Hazira-Vijaipur-Jagdishpur, Barauni, Salaya)",
            "Waterways: Inland Waterways & Major Sea Ports along Indian Coastline",
            "Airways: Domestic, International, Pawan Hans Helicopters",
            "Communication Networks: Personal, Mass Communication (Radio, Doordarshan, Press)",
            "International Trade: Balance of Trade (Export vs Import) and Tourism as a Trade"
        ),
        // Political Science (Civics)
        "sst_13" to listOf(
            "Case Study of Belgium: Ethnic Composition and Regional Diversity",
            "Case Study of Sri Lanka: Sinhala Majority and Tamil Grievances",
            "Majoritarianism in Sri Lanka and Rise of Civil War",
            "Accommodation in Belgium: Unique Constitutional Arrangement",
            "Why Power Sharing is Desirable: Prudential Reasons vs Moral Reasons",
            "Forms of Power Sharing: Horizontal (Legislature, Executive, Judiciary)",
            "Vertical Power Sharing (Federal), Social Groups, and Coalition Governments"
        ),
        "sst_14" to listOf(
            "What is Federalism? Key Features of Federal Systems",
            "Coming Together Federations vs Holding Together Federations",
            "What Makes India a Federal Country? Three Lists (Union, State, Concurrent)",
            "Special Provisions for Certain States & Union Territories",
            "How is Federalism Practised? Linguistic States and Language Policy",
            "Centre-State Relations and the Era of Coalition Governments",
            "Decentralisation in India: 73rd and 74th Constitutional Amendments (Panchayati Raj System)"
        ),
        "sst_15" to listOf(
            "Gender and Politics: Public / Private Division of Labour",
            "Feminist Movements and Demands for Women's Political Representation",
            "Religion, Communalism and Politics: Concepts and Prejudices",
            "Communalism in Politics: Mob Violence, Voting Blocs & Secular State Features",
            "Caste and Politics: Caste Inequalities and Untouchability Prohibition",
            "Caste in Politics: Mobilisation and Vote-Bank Realities",
            "Politics in Caste: Assimilation, Coalition, and Backward Classes Empowerment"
        ),
        "sst_16" to listOf(
            "Why Do We Need Political Parties? Meaning and Three Components",
            "Functions of Political Parties in Democratic Systems",
            "The Necessity of Political Parties & Why Non-Party Democracy Fails",
            "How Many Parties Should We Have? One-Party, Two-Party, and Multi-Party Systems",
            "National Political Parties: Criteria, BJP, INC, AAP, BSP, CPI-M",
            "State Parties (Regional Parties) and Their Rising National Influence",
            "Challenges to Political Parties: Lack of Internal Democracy, Dynastic Succession, Money & Muscle Power",
            "How Can Parties Be Reformed? Anti-Defection Law, Affidavit Mandates, and Election Funding"
        ),
        "sst_17" to listOf(
            "How Do We Assess Democracy's Outcomes? Comparing with Autocracy",
            "Accountable, Responsive, and Legitimate Government",
            "Economic Growth and Development: Dictatorships vs Democracies",
            "Reduction of Inequality and Poverty in Democratic Nations",
            "Accommodation of Social Diversity: Harmony without Majoritarian Imposition",
            "Dignity and Freedom of the Citizens: Women's Rights and Eradication of Caste Discrimination"
        ),
        // Economics
        "sst_18" to listOf(
            "What Development Promises: Different People, Different Goals",
            "Income and Other Goals: Freedom, Security, Respect, and Equal Treatment",
            "National Development: Divergent Conceptions of Growth",
            "How to Compare Different Countries: Per Capita Income & World Bank Criteria",
            "Income and Other Criteria: Infant Mortality Rate, Literacy Rate, Net Attendance Ratio",
            "Public Facilities: PDS, Healthcare, Education Infrastructure",
            "Human Development Report (HDR) & Human Development Index (HDI)",
            "Sustainability of Development: Groundwater Depletion & Exhaustion of Natural Resources"
        ),
        "sst_19" to listOf(
            "Sectors of Economic Activities: Primary, Secondary, and Tertiary Sectors",
            "Comparing the Three Sectors: Gross Domestic Product (GDP) Calculation",
            "Historical Changes in Sectors: From Agriculture to Industrial and Service Hegemony",
            "Rising Importance of the Tertiary Sector in India: Basic Services & ICT Boom",
            "Where are Most of the People Employed? Disguised Unemployment / Underemployment",
            "How to Create More Employment: Irrigation, Credit, Infrastructure, and MGNREGA 2005",
            "Division of Sectors as Organised and Unorganised: Working Conditions & Job Security",
            "Sectors in Terms of Ownership: Public Sector vs Private Sector Responsibilities"
        ),
        "sst_20" to listOf(
            "Money as a Medium of Exchange: Elimination of Double Coincidence of Wants",
            "Modern Forms of Money: Currency Notes, Coins, Demand Deposits, and Cheques",
            "Loan Activities of Banks: Credit Creation and Interest Differential",
            "Two Different Credit Situations: Festive Season vs Debt-Trap (Swapna's Case)",
            "Terms of Credit: Interest Rate, Collateral, Documentation, and Mode of Repayment",
            "Formal Sector Credit in India: Commercial Banks, Cooperative Societies & RBI Supervision",
            "Informal Sector Credit: Moneylenders, Traders, Relatives & Exploitative Practices",
            "Self-Help Groups (SHGs) for the Poor: Empowerment of Rural Women and Collateral-Free Loans"
        ),
        "sst_21" to listOf(
            "Production Across Countries: Rise of Multinational Corporations (MNCs)",
            "Interlinking Production Across Countries: FDI, Joint Ventures, and Local Sourcing",
            "Foreign Trade and Integration of Markets: Chinese Toys in India Case Study",
            "What is Globalisation? Rapid Integration of Countries through Flows of Goods and Capital",
            "Factors that Have Enabled Globalisation: Information Technology & Liberalisation of Trade",
            "World Trade Organisation (WTO): Aims, Trade Rules, and Developed Nation Subsidies",
            "Impact of Globalisation in India: Benefits for Consumers and Top Companies, Challenges for Small Producers",
            "The Struggle for a Fair Globalisation: Role of Government Policies and Citizen Alliances"
        ),

        // ==================== 4. ENGLISH (CBSE 184) ====================
        // First Flight Prose
        "eng_1" to listOf(
            "Lencho's Faith and the Devastating Hailstorm",
            "The Letter to God Demanding 100 Pesos",
            "The Postmaster's Act of Charity and Compassion",
            "Irony of the Ending: 'A Bunch of Crooks' & Thematic Character Analysis"
        ),
        "eng_2" to listOf(
            "The Historic Inauguration Ceremony at Union Buildings Amphitheatre",
            "Apartheid System: Structure of Racial Dominance and Oppression",
            "Mandela's Twin Obligations: Family vs Nation",
            "Meaning of Courage and the Resilient Spirit of Freedom Fighters"
        ),
        "eng_3" to listOf(
            "His First Flight: The Young Seagull's Fear of Flying & Family Prodding",
            "The Mother's Trick: Bait of Fish and Taking the Leap of Faith",
            "The Black Aeroplane: The Dakota Pilot Caught in Storm Clouds",
            "The Mystery of the Unknown Pilot and the Guiding Runway Light"
        ),
        "eng_4" to listOf(
            "Anne Frank's Solitude and Personification of Kitty the Diary",
            "Anne's Relationship with Family and School Teachers",
            "Mr Keesing's Essays on 'A Chatterbox' and 'An Incorrigible Chatterbox'",
            "The Satirical Poem on Father Swan and Quacking Ducklings"
        ),
        "eng_5" to listOf(
            "A Baker from Goa: The Pader, Traditional Jingling Thud, and Bol/Bolinhas",
            "Coorg: The Land of Rolling Hills, Kodavu Warriors, Coffee Plantations, and Wildlife",
            "Greek or Arabic Descent Theory of the Kodavus and General Cariappa",
            "Tea from Assam: Pranjol and Rajvir's Train Journey & Legends of Tea Discovery"
        ),
        "eng_6" to listOf(
            "Gavin Maxwell's Life in Camusfearna and Desire for an Otter Pet",
            "Bringing Mijbil from Basra to London: The Airplane Box Ordeal",
            "Mijbil's Playful Habits: Ping-Pong Balls, Marbles, and Tub Taps",
            "The Curious Reactions of Londoners: 'A Baby Seal, Walrus, or Hippo?'"
        ),
        "eng_7" to listOf(
            "Valli's 8-Year-Old Curiosity and Fascination with the Town Bus",
            "Meticulous Planning, Coin Saving, and Calculation of Bus Fares",
            "The Bus Journey: The Galloping Cow, Scenery, and Disdain for Condescension",
            "The Dead Cow on the Return Trip: First Encounters with Death and Epiphany"
        ),
        "eng_8" to listOf(
            "Gautama Buddha's Early Life as Prince Siddhartha and the Four Sights",
            "Attaining Enlightenment under the Bodhi Tree at Bodh Gaya",
            "Kisa Gotami and the Loss of Her Only Son",
            "The Mustard Seed Condition: The Universal Inevitability of Death and Grief"
        ),
        "eng_9" to listOf(
            "Anton Chekhov's Farce: Ivan Vassilevitch Lomov's Marriage Proposal",
            "The Quarrel over Oxen Meadows Property Ownership",
            "The Second Quarrel over Hunting Dogs: Guess vs Squeezer",
            "Chubukov's Meddling, Hypocrisy, and Comic Reconciliation"
        ),
        // First Flight Poems
        "eng_10" to listOf(
            "The Crow, Hemlock Tree, and Fall of Fine Snow Dust",
            "Transformation of Mood: From Gloom to Joyous Renewal",
            "Poetic Devices: Alliteration, Inversion, Enjambment & Rhyme Scheme (abab)"
        ),
        "eng_11" to listOf(
            "Fire as a Metaphor for Uncontrolled Passion, Greed, and Lust",
            "Ice as a Symbol of Cold Hatred, Indifference, and Rigid Rigidity",
            "The Apocalyptic Theme: The Dual Potential for Human Self-Destruction"
        ),
        "eng_12" to listOf(
            "The Majestic Stalking Tiger Confined within Concrete Cell Bars",
            "Contrast with the Natural Wild Jungle: Lurking in Shadows and Stalking Deer",
            "The Tragic Loss of Freedom: Ignoring Visitors & Staring at Brilliant Stars"
        ),
        "eng_13" to listOf(
            "Humorous Identification: The Roaring Asian Lion & Striped Bengal Tiger",
            "The Peppering Leopard, Hugging Bear, and Crocodile's Weeping Tears",
            "The Hyena's Grin, Chameleon's Camouflage & Carolyn Wells' Comic Rhymes"
        ),
        "eng_14" to listOf(
            "The Boy's Grief over the Lost Ball Bouncing into the Deep Harbor",
            "Why the Poet Refuses to Intrude or Offer Money for Another Ball",
            "Epistemology of Loss: Learning Responsibility in a World of Possessions"
        ),
        "eng_15" to listOf(
            "The Parent's Continuous Nagging: Biting Nails, Slouching, Homework, and Acne",
            "Amanda's Escapist Fantasies: Emerald Sea Mermaid, Carefree Orphan, Rapunzel",
            "The Struggle for Autonomy and Personal Creative Space in Adolescence"
        ),
        "eng_16" to listOf(
            "The Trees Moving from the Confined Domestic Interior into the Empty Forest",
            "Struggle of Roots, Leaves, and Twigs against Glass Panes",
            "Feminist Allegory: Women Breaking Free from Domestic Confinement into Society"
        ),
        "eng_17" to listOf(
            "The Silent Arrival of Fog on Little Cat Feet",
            "Looking over Harbour and City on Silent Haunches",
            "The Fleeting and Transient Nature of Atmospheric Phenomena"
        ),
        "eng_18" to listOf(
            "Belinda and Her Pets: Ink the Kitten, Blink the Mouse, Mustard the Dog",
            "Mocking Custard the Cowardly Dragon for His Cry for a Safe Cage",
            "The Sudden Invasion of the Fierce Pirate with Cutlass and Pistol",
            "Custard's Valor: Gobbling the Pirate & The Return of Domestic Pretentiousness"
        ),
        "eng_19" to listOf(
            "Physical Beauty vs True Spiritual Identity: The Great Honey-Coloured Hair",
            "The Young Man's Despair over Outer Appearance Rather than Inner Soul",
            "The Old Religious Text: Only God Can Love Anne Gregory for Herself Alone"
        ),
        // Footprints Without Feet
        "eng_20" to listOf(
            "Tricki the Pampered Dog and Mrs Pumphrey's Overfeeding Indulgence",
            "Tricki's Bloated Sickness: Rheumy Eyes and Complete Lethargy",
            "Dr Herriot's Practical Surgery Treatment: Controlled Diet and Dogpack Play",
            "The Hilarious Spoils: Sherry, Brandy, Fresh Eggs, and 'Triumph of Surgery'"
        ),
        "eng_21" to listOf(
            "Hari Singh's Encounters with Anil: Flattery, Cooking, and Companionship",
            "Anil's Trust: Teaching Hari to Read, Write Sentences, and Add Numbers",
            "The Theft of 600 Rupees and Hesitation at the Railway Platform",
            "Conscience and Transformation: Returning the Damp Notes under the Mattress"
        ),
        "eng_22" to listOf(
            "Fowler's Disappointment with the Sloppy, Fat Secret Agent Ausable",
            "Max the Dangerous Intruder Holding an Automatic Pistol in Room 6",
            "Ausable's Cool Wit: Fabricating the Story of the Non-Existent Balcony",
            "The Knock at the Door, Waiter Henry's Drinks, and Max's Plunge to the Ground"
        ),
        "eng_23" to listOf(
            "Horace Danby's Respectable Persona and Obsession with Rare Collector Books",
            "Careful Planning of the Shotover Grange Country House Robbery",
            "Encounter with the Charming Lady in Red Pretending to be the Mistress",
            "The Trickery: Horace Opening the Safe Barehanded and His Ensuing Arrest"
        ),
        "eng_24" to listOf(
            "Griffin's Discovery of Invisibility through Rare Drugs",
            "Setting Fire to the Landlord's House and Wandering Naked in London Cold",
            "Mischief at the Drury Lane Theatrical Store and Iping Village Coach and Horses",
            "The Clergyman's Robbery, Headless Scientist Stun, and Escape from Constable Jaffers"
        ),
        "eng_25" to listOf(
            "Richard Ebright's Childhood Butterfly Collecting Passion and Mother's Support",
            "Dr Urquhart's Guidance and Tagging Monarch Butterflies",
            "The Breakthrough at the County Science Fair: Real Experiments vs Neat Displays",
            "Discovery of the Monarch Hormone in Gold Spots and DNA Blueprint Research"
        ),
        "eng_26" to listOf(
            "Mathilde Loisel's Dissatisfaction with Her Modest Middle-Class Life",
            "The Grand Ministerial Ball: Borrowing Madame Forestier's Diamond Necklace",
            "The Loss of the Necklace and Purchasing a Replacement for 36,000 Francs",
            "Ten Years of Gruelling Poverty, Hard Physical Labour, and The Shocking Revelation"
        ),
        "eng_27" to listOf(
            "Sulekha (Bholi): Childhood Brain Injury, Pockmarks, and Stuttering Insecurity",
            "Ramalal's Pragmatism in Sending Bholi to the Village Primary School",
            "The Compassionate Teacher's Transformative Love and Encouragement",
            "Bishamber Nath's Dowry Demands: Bholi's Defiant Voice and Rejection of the Greedy Groom"
        ),
        "eng_28" to listOf(
            "The 25th Century Retrospective: How a Nursery Rhyme Book Saved Earth",
            "Think-Tank's Arrogance: The Great and Mighty Ruler of Mars",
            "Captain Omega, Lieutenant Iota, and Sergeant Oop in the Centerville Library",
            "Misinterpreting Books as Sandwiches, Communication Devices, and Mother Goose Rhymes"
        ),
        // Grammar & Writing
        "eng_29" to listOf(
            "Simple Present, Present Continuous, Present Perfect, and Perfect Continuous",
            "Past Tenses: Simple Past, Past Continuous, and Past Perfect Rules",
            "Future Tenses: Expressing Intentions, Schedules, and Predictions",
            "Board Exam Error Correction and Gap Filling Exercises on Tenses"
        ),
        "eng_30" to listOf(
            "Expressing Ability and Permission: Can, Could, May",
            "Expressing Obligation, Necessity, and Prohibition: Must, Have to, Should, Ought to",
            "Expressing Possibility and Probability: Might, May, Could",
            "Expressing Willingness and Habits: Will, Would, Shall"
        ),
        "eng_31" to listOf(
            "Subject-Verb Agreement Rules: Singular vs Plural Subjects",
            "Compound Subjects with 'And', 'Either... Or', 'Neither... Nor'",
            "Collective Nouns and Indefinite Pronouns (Everyone, None, Each)",
            "Nouns Plural in Form but Singular in Meaning (Mathematics, News, Measles)"
        ),
        "eng_32" to listOf(
            "Rules for Changing Direct Speech to Indirect Speech: Tense Shifts",
            "Reporting Commands and Requests: Using 'Ordered', 'Advised', 'Requested'",
            "Reporting Statements and Affirmative Sentences with 'That'",
            "Changes in Time, Place, and Pronouns (Today -> That day, Now -> Then)"
        ),
        "eng_33" to listOf(
            "Reporting Interrogative Sentences: Wh-Questions (What, Where, Why)",
            "Reporting Yes/No Questions with 'If' or 'Whether'",
            "Reporting Exclamations and Wishes: 'Exclaimed with joy/sorrow'",
            "Dialogue Completion and Passage Conversion in Board Examination Formats"
        ),
        "eng_34" to listOf(
            "Format of Formal Letter: Sender's Address, Date, Receiver's Designation, Subject, Salutation",
            "Letter to the Editor: Highlighting Social Issues, Civic Problems, and Civic Solutions",
            "Official Complaint Letters: Faulty Electronics, Deficient Public Services",
            "Letters of Inquiry and Placing Orders: Product Specifications, Discounts, Terms"
        ),
        "eng_35" to listOf(
            "Structure of an Analytical Paragraph: Introduction, Body Paragraphs, and Conclusion",
            "Interpreting Data: Bar Graphs, Pie Charts, Line Graphs, and Tables",
            "Comparative Vocabulary: 'Surged', 'Dipped', 'Plateaued', 'Outnumbered', 'In contrast'",
            "Synthesizing Arguments: Comparing Advantages and Disadvantages Objectively"
        ),

        // ==================== 5. HINDI COURSE A (CBSE 002) ====================
        // Kshitij Prose
        "hin_1" to listOf(
            "नेताजी का चश्मा: कस्बे का चौराहा और नेताजी सुभाष चंद्र बोस की संगमरमर की मूर्ति",
            "कैप्टन चश्मेवाले का देशभक्तिपूर्ण व्यक्तित्व और मूर्तिकार की भूल",
            "हालदार साहब का आश्चर्य, पानवाले का व्यंग्य और देशभक्ति का सम्मान",
            "कैप्टन की मृत्यु, बच्चों द्वारा सरकंडे का चश्मा लगाना और नई पीढ़ी की आशा"
        ),
        "hin_2" to listOf(
            "बालगोबिन भगत का रूप-रंग, पहनावा और कबीरपंथी गृहस्थ संन्यासी आचरण",
            "प्रभातियाँ और खेतों में धान की रोपनी के समय संगीत साधना का जादू",
            "एकलौते बेटे की मृत्यु पर विलाप के बजाय आत्मा-परमात्मा मिलन का उत्सव",
            "पतोहू के पुनर्विवाह का साहसिक निर्णय और सामाजिक रूढ़ियों पर कड़ा प्रहार"
        ),
        "hin_3" to listOf(
            "यशपाल का पतनशील सामंती वर्ग पर व्यंग्य: लखनवी नवाब साहब का सेकंड क्लास डिब्बा",
            "सफेदपोश नवाब साहब की बनावटी जीवनशैली और खीरे की तैयारी की नवाबी रईसी",
            "खीरे को सूंघकर खिड़की से बाहर फेंकना और डकार लेकर तृप्ति का दिखावा",
            "बिना विचार, घटना और पात्रों के 'नई कहानी' लिखने पर लेखक का कटाक्ष"
        ),
        "hin_4" to listOf(
            "फादर कामिल बुल्के का व्यक्तित्व: बेल्जियम के रैम्सचैपेल से भारत की पुण्यभूमि आगमन",
            "हिंदी भाषा के प्रति अगाध प्रेम, प्रामाणिक 'अंग्रेजी-हिंदी कोश' का निर्माण",
            "इलाहाबाद की 'परिमल' साहित्यिक संस्था में सक्रियता और पारिवारिक आत्मीयता",
            "मानवीय करुणा की दिव्य चमक, बाहों का स्नेह और कफ़न की पवित्र शांति"
        ),
        "hin_5" to listOf(
            "मन्नू भंडारी का बचपन: अजमेर का माहौल और पिता का शक्की व अंतर्विरोधी स्वभाव",
            "कॉलेज की प्राध्यापिका शीला अग्रवाल का प्रभाव और साहित्यिक चेतना का जागरण",
            "सन् 1942 के स्वतंत्रता आंदोलन में सक्रिय भागीदारी और पिता का क्रोध व गर्व",
            "स्त्री अस्मिता का संघर्ष और स्वतंत्र व्यक्तित्व निर्माण की प्रेरणा"
        ),
        "hin_6" to listOf(
            "महावीर प्रसाद द्विवेदी का स्त्री-शिक्षा विरोधी कुतर्कों का ऐतिहासिक खंडन",
            "प्राचीन भारत में विदुषी स्त्रियाँ: गार्गी, मैत्रेयी, शकुंतला और अत्रि की पत्नी अनसूया",
            "संस्कृत नाटकों में प्राकृत भाषा बोलने का वास्तविक कारण और तत्कालीन शिक्षा व्यवस्था",
            "शिक्षा को अनर्थ का कारण मानने वाली संकीर्ण मानसिकता पर तर्कसंगत प्रहार"
        ),
        "hin_7" to listOf(
            "उस्ताद बिस्मिल्ला खाँ की शहनाई साधना, बचपन (अमीरुद्दीन) और डुमराँव की यादें",
            "बालाजी मंदिर की नौबतखाना में रियाज़ और रसूलनबाई-बतूलनबाई के गायन का प्रभाव",
            "काशी की गंगा-जमुनी तहज़ीब, हिंदू-मुस्लिम सौहार्द और काशी विश्वनाथ के प्रति श्रद्धा",
            "भारत रत्न से सम्मानित होने पर भी सादगी, फटा तहमद और सुर की सच्ची इबादत"
        ),
        "hin_8" to listOf(
            "भदंत आनंद कौसल्यायन का विचार: 'सभ्यता' और 'संस्कृति' शब्दों की सूक्ष्म व्याख्या",
            "आग और सुई-धागे के प्रथम आविष्कारकों की प्रज्ञा: संस्कृति का मूल स्रोत",
            "भौतिक सुख-सुविधाओं का संग्रह सभ्यता है, जबकि मानवता का कल्याण संस्कृति है",
            "कल्याण की भावना से विमुख होने पर संस्कृति का विकृत होकर 'असंस्कृति' बन जाना"
        ),
        // Kshitij Poetry
        "hin_9" to listOf(
            "सूरदास के पद: गोपियों का उद्धव पर व्यंग्य और 'बड़भागी' कहकर उपालंभ देना",
            "उद्धव के ज्ञान मार्ग (योग संदेश) को कड़वी ककड़ी के समान निरर्थक बताना",
            "कमल के पत्ते और तेल की गगरी के दृष्टांत द्वारा उद्धव की विरक्ति पर कटाक्ष",
            "गोपियों का श्रीकृष्ण के प्रति अनन्य प्रेम, हारिल की लकड़ी का प्रतीक और राजधर्म की सीख"
        ),
        "hin_10" to listOf(
            "रामचरितमानस के बालकांड से सीता स्वयंवर में शिवधनुष टूटने का प्रसंग",
            "परशुराम का क्रोध, विश्वामित्र से संवाद और लक्ष्मण का व्यंग्यपूर्ण प्रत्युत्तर",
            "लक्ष्मण द्वारा धनुष को पुराना व छूते ही टूटने वाला बताकर व्यंग्य करना",
            "परशुराम का फरसा दिखाना, क्षत्रिय-विनाशक गर्जना और श्रीराम का शीतल जल समान शांत स्वर"
        ),
        "hin_11" to listOf(
            "देव के सवैये और कवित्त: श्रीकृष्ण के राजसी व नटवर रूप का मनोहारी सौंदर्य",
            "पाँयनि नूपुर मंजु बजैं, कटि किंकिनी में धुनि की मधुराई का सजीव चित्रण",
            "वसंत ऋतु का कामदेव के बालक रूप में मानवीकरण और प्रकृति का वात्सल्य",
            "चांदनी रात की उज्ज्वलता, आकाश में दूध के झाग जैसी निर्मलता का कवित्त"
        ),
        "hin_12" to listOf(
            "जयशंकर प्रसाद का आत्मकथ्य: मित्रों द्वारा आत्मकथा लिखने के आग्रह पर असमर्थता",
            "जीवन की रिक्तता, खाली गागर और अतीत के छिपे दुखों को न कुरेदने की विनती",
            "प्रेयसी के साथ बिताए सुखद पलों (पाथेय) की स्मृति और वर्तमान की एकाकी व्यथा",
            "कवि की विनम्रता: 'छोटे से जीवन की कैसे बड़ी कथाएँ आज कहूँ'"
        ),
        "hin_13" to listOf(
            "उत्साह (निराला): बादलों का गर्जन, नवजीवन देने वाले क्रांतिकारी रूप का आह्वान",
            "तप्त धरा को शीतल करने, नई कल्पना व नए अंकुर के लिए क्रांति का संदेश",
            "अट नहीं रही है: फागुन मास की प्राकृतिक सुंदरता, मादकता और सुगंध का प्रसार",
            "पेड़ों पर हरी-लाल नई पत्तियों का लदना और प्रकृति का सौंदर्य हृदय में न समाना"
        ),
        "hin_14" to listOf(
            "यह दंतुरित मुसकान (नागार्जुन): नन्हे शिशु की दंतुरित मुसकान का जादू",
            "कठोर पाषाण पिघलकर जल बन जाना और बांस-बबूल से शेफालिका के फूलों का झड़ना",
            "प्रवासी पिता के प्रति शिशु का अपरिचित भाव और मां की मधुपर्क की साधना",
            "फसल: नदियों के पानी का जादू, हाथों के स्पर्श की गरिमा और सूरज की किरणों का रूपांतरण"
        ),
        "hin_15" to listOf(
            "छाया मत छूना (गिरिजा कुमार माथुर): पुरानी सुखद स्मृतियों को याद करने से वर्तमान दुख दूना होना",
            "यश, वैभव, मान और धन की मृगतृष्णा के पीछे व्यर्थ भटकने की चेतावनी",
            "हर चांदनी रात के पीछे एक अंधेरी काली रात (अमावस्या) का छिपा होना",
            "जो न मिला उसे भूलकर वर्तमान का यथार्थ स्वीकार करने और भविष्य वरण का संदेश"
        ),
        "hin_16" to listOf(
            "कन्यादान (ऋतुराज): मां द्वारा अपनी बेटी को विदाई के समय दी गई व्यावहारिक सीख",
            "वस्त्र और आभूषणों को स्त्री जीवन का बंधन (शाब्दिक भ्रम) न समझने की चेतावनी",
            "पानी में झांककर अपने सौंदर्य पर रीझने के बजाय यथार्थ से सामना करने की सीख",
            "आग रोटियां सेंकने के लिए है, जलने के लिए नहीं: लड़की होना पर लड़की जैसी दुर्बल न दिखाई देना"
        ),
        "hin_17" to listOf(
            "संगतकार (मंगलेश डबराल): मुख्य गायक के भारी सुर को सहारा देने वाले संगतकार की भूमिका",
            "जब मुख्य गायक तारसप्तक में बिखरने लगता है, तब उसके स्वर को ढांढस बंधाना",
            "संगतकार द्वारा अपने स्वर को मुख्य गायक से नीचा रखना: अक्षमता नहीं बल्कि मनुष्यता",
            "परदे के पीछे रहकर मुख्य कलाकार को सफल बनाने वाले सहायकों की गरिमा"
        ),
        // Kritika Part 2
        "hin_18" to listOf(
            "माता का अँचल (शिवपूजन सहाय): ग्रामीण परिवेश, पिता का स्नेह और बचपन के खेल",
            "रामायण पाठ, बबूत का तिलक, आटे की गोलियां मछलियों को खिलाना और कुश्ती लड़ना",
            "बारात, दुकानदारी, खेती के खेल और दोस्तों के साथ अमोलों व बटोही के संग मज़ाक",
            "सांप के बिल में पानी डालने पर विपदा: रोते हुए मां के आंचल में शरण लेना और रक्षा पाना"
        ),
        "hin_19" to listOf(
            "जॉर्ज पंचम की नाक (कमलेश्वर): इंग्लैंड की महारानी एलिजाबेथ द्वितीय के भारत आगमन की तैयारी",
            "इंडिया गेट पर स्थित जॉर्ज पंचम की लाट की नाक का अचानक गायब हो जाना",
            "सरकारी महकमे में हड़कंप, मूर्तिकार को बुलाना और पूरे देश के पहाड़ों की खाक छानना",
            "शहीद बच्चों और अंततः किसी जीवित भारतीय नागरिक की नाक लगाने का व्यंग्यात्मक व लज्जाजनक निर्णय"
        ),
        "hin_20" to listOf(
            "साना-साना हाथ जोड़ि... (मधु कांकरिया): सिक्किम की राजधानी गंगतोक का रात का सौंदर्य",
            "कंचनजंघा पर्वत श्रृंखला की ओर यात्रा, बौद्ध पताकाएं और जितेन नार्गे का मार्गदर्शन",
            "युमथांग और कटाओ (भारत का स्विट्जरलैंड) में बर्फ का प्राकृतिक जल-संचय तंत्र",
            "पहाड़ी रास्तों को चौड़ा करती पीठ पर बच्चे बांधे पत्थर तोड़ती श्रमजीवी महिलाओं का जीवन-संघर्ष"
        ),
        "hin_21" to listOf(
            "एही ठैयाँ झुलनी हेरानी हो रामा! (शिवप्रसाद सिंह): लोक गायिका दुलारी और टुन्नू का प्रेम प्रसंग",
            "कजली गायन प्रतियोगिता में दुलारी और सोलह वर्षीय टुन्नू के बीच काव्यमयी नोकझोंक",
            "टुन्नू द्वारा खद्दर का धोती-कुर्ता भेंट करना और देशभक्ति आंदोलन में कूदना",
            "विदेशी वस्त्रों के बहिष्कार में टुन्नू की शहादत और दुलारी द्वारा अमर प्रेम व देशप्रेम का निर्वाह"
        ),
        "hin_22" to listOf(
            "मैं क्यों लिखता हूँ? (सच्चिदानंद हीरानंद वात्स्यायन 'अज्ञेय'): लेखक की आंतरिक विवशता की खोज",
            "बाहरी दबाव (संपादक का आग्रह, आर्थिक आवश्यकता) बनाम आंतरिक अनुभूति की अभिव्यक्ति",
            "जापान के हिरोशिमा यात्रा का संस्मरण: परमाणु बम विस्फोट का प्रत्यक्ष प्रमाण",
            "पत्थर पर उकेरी गई मानव की जली हुई छाया को देखकर विस्फोट का साक्षात् अनुभव और कविता का प्रस्फुटन"
        ),
        // Vyakaran & Rachna
        "hin_23" to listOf(
            "पद परिचय की परिभाषा: शब्द और पद में अंतर",
            "संज्ञा और सर्वनाम पदों का व्याकरणिक परिचय (भेद, लिंग, वचन, कारक)",
            "विशेषण और क्रिया पदों का व्याकरणिक परिचय (कर्मक/सकर्मक, काल, वाच्य)",
            "अव्यय पदों (क्रियाविशेषण, समुच्चयबोधक, संबंधबोधक, विस्मयादिबोधक) का परिचय"
        ),
        "hin_24" to listOf(
            "वाच्य की परिभाषा और भेद: कर्तृवाच्य, कर्मवाच्य, भाववाच्य",
            "कर्तृवाच्य से कर्मवाच्य में रूपांतरण के नियम (द्वारा/से का प्रयोग)",
            "कर्तृवाच्य से भाववाच्य में रूपांतरण और असमर्थता सूचक वाक्य",
            "बोर्ड परीक्षा के आधार पर वाच्य पहचान और बहुविकल्पीय अभ्यास"
        ),
        "hin_25" to listOf(
            "रस का स्वरूप और भरतमुनि का रस-सूत्र (विभावानुभावव्यभिचारिसंयोगाद्रसनिष्पत्तिः)",
            "रस के चार अंग: स्थायी भाव, विभाव (आलंबन, उद्दीपन), अनुभाव, संचारी भाव",
            "प्रमुख 9+2 रस: श्रृंगार, हास्य, करुण, रौद्र, वीर, भयानक, बीभत्स, अद्भुत, शांत, वात्सल्य, भक्ति",
            "काव्य पंक्तियों में रस, स्थायी भाव और आलंबन-उद्दीपन की सटीक पहचान"
        ),
        "hin_26" to listOf(
            "रचना के आधार पर वाक्य के तीन भेद: सरल वाक्य, संयुक्त वाक्य, मिश्र वाक्य",
            "सरल वाक्य की संरचना: एक उद्देश्य और एक विधेय",
            "संयुक्त वाक्य: समानाधिकरण समुच्चयबोधक (और, एवं, तथा, या, परंतु, किंतु, इसलिए)",
            "मिश्र वाक्य: प्रधान उपवाक्य और आश्रित उपवाक्य (संज्ञा, विशेषण, क्रियाविशेषण उपवाक्य)",
            "वाक्य रूपांतरण: सरल से संयुक्त, संयुक्त से मिश्र और मिश्र से सरल वाक्य बनाना"
        ),
        "hin_27" to listOf(
            "अनुच्छेद लेखन की रूपरेखा: भूमिका, विषय-विस्तार और निष्कर्ष",
            "संकेत बिंदुओं (हंट्स) का प्रभावी उपयोग और शब्द-सीमा (100-120 शब्द) का पालन",
            "समसामयिक विषय: आत्मनिर्भर भारत, सोशल मीडिया के प्रभाव, पर्यावरण संरक्षण",
            "विचारप्रधान विषय: समय का सदुपयोग, अनुशासन का महत्व, नारी सशक्तिकरण"
        ),
        "hin_28" to listOf(
            "औपचारिक पत्र का प्रारूप: प्रेषक का पता, दिनांक, सेवा में, विषय, महोदय, मुख्य विषय, धन्यवाद, भवदीय",
            "शिकायती पत्र: नगर निगम अधिकारी को गंदगी/सड़क मरम्मत हेतु",
            "संपादकीय पत्र: दैनिक समाचार पत्र के संपादक को जनसमस्या निवारण हेतु",
            "अनौपचारिक पत्र का प्रारूप: बड़ों को आदर, छोटों को स्नेह, कुशलता समाचार और संदेश"
        ),
        "hin_29" to listOf(
            "विज्ञापन लेखन के आवश्यक तत्व: आकर्षक शीर्षक, स्लोगन, चित्र, मुख्य विशेषताएं",
            "रियायत (छूट), मुफ्त उपहार, सीमित समय का उल्लेख और संपर्क सूत्र (फोन/ईमेल)",
            "वाणिज्यिक विज्ञापन: पुस्तक मेला, दंतमंजन, पर्यावरण-अनुकूल वाहन, योग कक्षा",
            "सामाजिक जागरूकता विज्ञापन: जल संरक्षण, मतदान जागरूकता, रक्तदान शिविर"
        ),
        "hin_30" to listOf(
            "संदेश लेखन का प्रारूप: बॉक्स, शीर्षक (संदेश), दिनांक, समय, संबोधन, विषय-वस्तु, प्रेषक",
            "शुभकामना संदेश: दीपावली, ईद, स्वतंत्रता दिवस, जन्मदिन व परीक्षा में सफलता",
            "विशेष अवसरों पर संदेश: शिक्षक दिवस, पर्यावरण दिवस, खेल दिवस",
            "शोक व सांत्वना संदेश और संक्षिप्त व सारगर्भित भाषा शैली"
        ),

        // ==================== 6. SANSKRIT (CBSE 122) ====================
        // Shemushi Part 2
        "san_1" to listOf(
            "शुचिपर्यावरणम्: महानगरमध्येषु प्रदूषणसमस्यायाः भयावहता",
            "प्रकृतिरेव शरणम्: शुद्धपर्यावरणस्य अनिवार्यता",
            "ललितलतानां रमणीयं काननं गन्तुं कवेः अभिलाषा",
            "श्लोकानाम् अन्वयः, भावार्थः, शब्दार्थाः च"
        ),
        "san_2" to listOf(
            "बुद्धिर्बलवती सदा: देउलाख्ये ग्रामे राजसिंहस्य भार्या बुद्धिमती",
            "व्याघ्रं दृष्ट्वा पुत्राभ्यां सह बुद्धिमत्याः चातुर्यम्",
            "जम्बुक-व्याघ्रयोः संवादः गलबद्धशृगालकस्य गतिः",
            "आपत्काले बुद्धेः प्रयोगेन प्राणरक्षणस्य पाठः"
        ),
        "san_3" to listOf(
            "शिशुलालनम्: कुन्दमाला नाटकात् संकलितः प्रसंगः",
            "भगवान् रामः लव-कुशयोः राजसिंहासनारोहणाय आग्रहः",
            "बालभावं दृष्ट्वा रामस्य वात्सल्यस्नेहः",
            "विदूषकस्य हास्यप्रयोगाः लव-कुशयोः वंशपरिचयः च"
        ),
        "san_4" to listOf(
            "जननी तुल्यवत्सला: महाभारतस्य वनपर्वात् संकलिता कथा",
            "सुरभिः कामधेनोः भूमौ पतितं दुर्बलं पुत्रं दृष्ट्वा रोदनम्",
            "इन्द्र-सुरभ्योः संवादः: दुर्बले सुते मातुः अधिका कृपा",
            "सर्वेषु अपत्येषु मातुः समदृष्टिः इति नीतिबोधः"
        ),
        "san_5" to listOf(
            "सुभाषितानि: आलस्यं हि मनुष्याणां शरीरस्थो महान् रिपुः",
            "उद्यमसमो बन्धुः नास्ति यं कृत्वा नावसीदति",
            "गुणी गुणं वेत्ति न वेत्ति निर्गुणः (पण्डितः जनः)",
            "सम्पत्तौ च विपत्तौ च महतामेकरूपता (उदय-अस्त सवितुः रूपम्)"
        ),
        "san_6" to listOf(
            "सौहार्दं प्रकृतेः शोभा: वनस्य जन्तूनां राजपदार्थं कलहः",
            "सिंहः, काकः, पिकः, मयूरः, गजः, वानराणां च वाक्-युद्धम्",
            "प्रकृतिमातुः आगमनम् सर्वेषां परस्परं पूरकत्वम्",
            "अन्योन्याश्रिताः वयं सर्वे इति संदेशः"
        ),
        "san_7" to listOf(
            "विचित्रः साक्षी: निर्धनस्य जनस्य रुग्णं पुत्रं द्रष्टुं यात्रा",
            "रात्रौ ग्रामे कस्यचित् गृहस्थस्य गृहे आश्रयः",
            "तस्मिन् एव गृहे चौरस्य प्रवेशः आरक्षिणा अभियोगारोपणम्",
            "न्यायाधीशः बंकिमचन्द्रस्य चातुर्येण मृतशरीरस्य साक्ष्यम्"
        ),
        "san_8" to listOf(
            "सूक्तयः: तिरुक्कुरल् ग्रन्थात् संस्कृतरूपान्तरिताः श्लोकाः",
            "पिता यच्छति पुत्राय बाल्ये विद्याधनं महत्",
            "अवक्रता यथा चित्ते तथा वाचि भवेद् यदि (तदेव सामञ्जस्यम्)",
            "सत्या वाणी, विदुषां लक्षणं, धर्मप्रदा वाक् च"
        ),
        "san_9" to listOf(
            "प्राणेभ्योऽपि प्रियः सुहृद्: मुद्राराक्षस नाटकात् संकलितः",
            "चाणक्यस्य नीतिकौशलम् अमात्यराक्षसस्य अन्वेषणम्",
            "चन्दनदासस्य स्वमित्राय प्राणारोपणस्य निष्ठा",
            "मैत्रीधर्मस्य महत्ता चाणक्येन चन्दनदासस्य सत्कारः"
        ),
        // Vyakaran
        "san_10" to listOf(
            "व्यञ्जनसन्धिः: वर्गीयप्रथमवर्णस्य तृतीयवर्णे परिवर्तनम् (जश्त्व सन्धिः)",
            "वर्गीयप्रथमवर्णस्य पञ्चमवर्णे परिवर्तनम् (अनुनासिक सन्धिः)",
            "विसर्गसन्धिः: विसर्गस्य उत्वम् (अतो रोरप्लुतादप्लुते)",
            "विसर्गस्य रत्वम्, लोपः, सत्वं, षत्वं, शत्वं च"
        ),
        "san_11" to listOf(
            "तत्पुरुषसमासः: द्वितीया तः सप्तमी विभक्तिपर्यन्तम्",
            "बहुव्रीहिसमासः: अन्यपदप्रधानः (पीताम्बरः, लम्बोधरः)",
            "अव्ययीभावसमासः: अनु, उप, सह, निर्, प्रति, यथा उपसर्गाः",
            "द्वन्द्वसमासः: इतरेतरद्वन्द्वः च-अर्थे द्वन्द्वः"
        ),
        "san_12" to listOf(
            "तद्धितप्रत्ययाः: मतुप् (गुणवान्, धनवान्, श्रीमान्)",
            "त्व (महत्त्वम्, लघुत्वम्) एवं तल् (जनता, लघुता)",
            "स्त्रीप्रत्ययः: टाप् (अजा, बाला, चटका)",
            "ङीप् प्रत्ययः (नदी, भवती, श्रीमती)"
        ),
        "san_13" to listOf(
            "अव्ययपदानां वाक्येषु सार्थकप्रयोगः",
            "कालवाचकाः: उच्चैः, च, श्वः, ह्यः, अद्य, इदानीम्, अधुना",
            "स्थानवाचकाः: अत्र-तत्र, यत्र-कुत्र, इतस्ततः, सर्वत्र",
            "रीतिवाचकाः: यदा-तदा, यथा-तथा, सहसा, वृथा, कदापि"
        ),
        "san_14" to listOf(
            "उपपदविभक्तिनियमाः: द्वितीया (उभयतः, परितः, धिक्, विना, प्रति)",
            "तृतीया विभक्तिः (सह, साकम्, समम्, अलम्, काणः अक्ष्णा)",
            "चतुर्थी विभक्तिः (रुच्, दा, नमः, स्वस्ति, क्रुध्)",
            "पञ्चमी (बहिः, विना, भी), षष्ठी (उपरि, अधः, पुरतः), सप्तमी (स्निह्, निपुणः, विश्वस्)"
        ),
        "san_15" to listOf(
            "वाच्यपरिवर्तनम्: केवलं लट्लकारे (वर्तमानकाले)",
            "कर्तृवाच्यस्य लक्षणम्: कर्ता प्रथमायां, कर्म द्वितीयायां, क्रिया कर्त्रनुसारिणी",
            "कर्मवाच्यस्य लक्षणम्: कर्ता तृतीयायां, कर्म प्रथमायां, क्रिया यक्+आत्मनेपदम्",
            "भाववाच्यस्य नियमाः: अकर्मकधातुभिः सह एकवचनप्रयोगः"
        ),
        "san_16" to listOf(
            "घटिकादर्शनम्: संस्कृतभाषायां समयलेखनम्",
            "सामान्यसमयः: १:०० (एकवादनम्) तः १२:०० (द्वादशवादनम्)",
            "सपादसमयः: सपाद-एकवादनम् (१:१५), सपाद-द्विवादनम् (२:१५)",
            "सार्धसमयः (३:३० - सार्धत्रिवादनम्) एवं पादोनसमयः (४:४५ - पादोनपञ्चवादनम्)"
        ),
        "san_17" to listOf(
            "अशुद्धिसंशोधनम्: वचन-लिङ्ग-पुरुष-लकार-संशोधनम्",
            "कर्तृ-क्रिया-अन्वयस्य दोषाणां निराकरणम्",
            "विशेष्य-विशेषण-सम्बन्धस्य शुद्धप्रयोगः",
            "कारकविभक्ति-दोषसंशोधनस्य अभ्यासः"
        ),

        // ==================== 7. INFORMATION TECHNOLOGY (CODE 402) ====================
        // Part A: Employability Skills
        "it_1" to listOf(
            "Communication Cycle: Sender, Ideas, Encoding, Channel, Receiver, Decoding, Feedback",
            "Verbal Communication (Oral & Written) vs Non-Verbal (Body Language, Posture, Gestures)",
            "Barriers to Effective Communication: Physical, Linguistic, Cultural, Interpersonal",
            "Principles of Effective Communication: The 7 Cs of Communication"
        ),
        "it_2" to listOf(
            "Stress Management: Meaning, Sources of Stress, and Harmful Effects",
            "Stress Management Techniques: Physical Exercise, Yoga, Meditation, Nature Walks, Time Management",
            "Ability to Work Independently: Self-Awareness, Self-Motivation, and Self-Regulation",
            "Goal Setting: SMART Goals (Specific, Measurable, Achievable, Realistic, Time-bound)"
        ),
        "it_3" to listOf(
            "Operating Systems: Functions and Types (Windows, Linux, Android, iOS)",
            "File and Folder Management: Creating, Renaming, Moving, Searching, and Deleting",
            "Basic Computer Maintenance: Disk Cleanup, Defragmentation, and Software Updates",
            "Computer Security & Cyber Threats: Viruses, Worms, Trojan Horses, Phishing, Antivirus Software"
        ),
        "it_4" to listOf(
            "Entrepreneurship and Society: Role of Entrepreneurs in Economic Growth and Job Creation",
            "Qualities and Characteristics of a Successful Entrepreneur",
            "Common Myths about Entrepreneurship Debunked",
            "Entrepreneurship as a Career Option and Sustainable Business Practices"
        ),
        "it_5" to listOf(
            "Definition of Green Economy and Green Skills",
            "The 17 United Nations Sustainable Development Goals (SDGs)",
            "Role of Individuals in Conserving Energy, Water, and Natural Resources",
            "E-Waste Management and Reduction of Carbon Footprint"
        ),
        // Part B: Subject Specific Skills
        "it_6" to listOf(
            "Applying Styles in a Document (Page, Paragraph, Character, Frame, Numbering Styles)",
            "Inserting and Modifying Images: Grouping, Positioning, Wrapping, and Cropping Objects",
            "Creating and Using Custom Templates in Word Processors",
            "Creating and Customizing Table of Contents (TOC) with Heading Levels",
            "Using Mail Merge to Create Mass Letters, Envelopes, and Mailing Labels"
        ),
        "it_7" to listOf(
            "Consolidating Data and Grouping Worksheets",
            "Creating Subtotals and What-If Analysis Tools (Scenarios, Goal Seek, Solver)",
            "Linking Multiple Sheets: Relative vs Absolute Hyperlinks and External Cell Referencing",
            "Sharing and Reviewing Spreadsheets: Tracking Changes, Comments, and Merging Workbooks",
            "Recording, Running, and Managing Basic Macros in Spreadsheets"
        ),
        "it_8" to listOf(
            "Database Management Concepts: Flat File vs Relational Database Management Systems (RDBMS)",
            "RDBMS Terminology: Entities, Attributes, Tuples, Tables, Primary Key, Composite Key, Foreign Key",
            "Creating Tables using Design View and Table Wizard; Setting Data Types and Field Properties",
            "Form Creation and Design; Modifying Controls and Layout",
            "Designing Queries using Design View and SQL Statements (SELECT, WHERE, ORDER BY, INSERT, UPDATE, DELETE)",
            "Creating Professional Reports from Tables and Queries"
        ),
        "it_9" to listOf(
            "Working with Accessibility Options in Operating Systems (StickyKeys, FilterKeys, High Contrast)",
            "Networking Fundamentals: Types of Networks (LAN, WAN, MAN), Internet Architectures, ISP, Modem",
            "Introduction to Instant Messaging and Creating Blogs using Online Portals",
            "Using Offline Blog Editors (Qumana, Windows Live Writer)",
            "Online Transactions, E-Commerce, and Internet Security Best Practices (Strong Passwords, Firewalls)",
            "Workplace Safety, Ergonomics, Hazard Prevention, and Basic First Aid Rules"
        )
    )

    fun getDefaultTopicsForChapter(chapter: ChapterEntity): List<String> {
        val titleLower = chapter.title.lowercase()
        return when {
            titleLower.contains("nationalism") -> listOf(
                "Core Causes & Historical Context",
                "Key Leaders, Movements & Mass Mobilisation",
                "Events, Treaties & Landmark Milestones",
                "Ideological Legacy & National Significance"
            )
            titleLower.contains("grammar") || titleLower.contains("vyakaran") -> listOf(
                "Definitions, Fundamental Rules & Classifications",
                "Standard Formulaic Usage & Concord Conditions",
                "Common Syntactic Errors & Rectifications",
                "Board Examination Application Exercises"
            )
            titleLower.contains("skills") || titleLower.contains("part a") -> listOf(
                "Core Definition, Principles & Importance",
                "Practical Techniques & Daily Applications",
                "Case Studies, Scenarios & Role Analysis",
                "Self-Assessment & Workplace Guidelines"
            )
            else -> listOf(
                "1. Fundamental Concepts & Key Definitions",
                "2. Standard Formulas, Principles & Properties",
                "3. Solved NCERT Textbook Examples & Methodologies",
                "4. Board Examination High-Yield Practice Questions"
            )
        }
    }

    val defaultSubtopics: List<SubtopicEntity> by lazy {
        DefaultSyllabusData.chapters.flatMap { chapter ->
            val topics = chapterTopicsMap[chapter.id] ?: getDefaultTopicsForChapter(chapter)
            topics.mapIndexed { index, title ->
                SubtopicEntity(
                    id = "${chapter.id}_sub_${index + 1}",
                    chapterId = chapter.id,
                    title = title,
                    isCompleted = false,
                    orderIndex = index + 1
                )
            }
        }
    }
}
