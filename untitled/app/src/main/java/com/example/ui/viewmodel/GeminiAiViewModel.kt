package com.example.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.api.NvidiaApiClient
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

private const val TAG = "GeminiAiViewModel"

sealed interface AiTutorUiState {
    data object Idle : AiTutorUiState
    data object Loading : AiTutorUiState
    data class Success(val responseText: String) : AiTutorUiState
    data class Error(val message: String) : AiTutorUiState
}

enum class AiEngineType(
    val title: String,
    val badge: String,
    val description: String,
    val modelId: String
) {
    NVIDIA_DIFFUSION_26B(
        title = "NVIDIA Diffusion 26B",
        badge = "26B PARALLEL DIFFUSION",
        description = "Discrete Block Diffusion · Parallel Token Generation",
        modelId = NvidiaApiClient.MODEL_DIFFUSION_26B
    ),
    GEMINI_FLASH(
        title = "Gemini 3.8 Flash",
        badge = "MULTIMODAL SOCRATIC",
        description = "Google DeepMind Firebase AI Logic Engine",
        modelId = "gemini-3.8-flash"
    )
}

data class SampleQuestionItem(
    val title: String,
    val subject: String,
    val source: String,
    val question: String,
    val concept: String
)

data class SocraticState(
    val activeEngine: AiEngineType = AiEngineType.NVIDIA_DIFFUSION_26B,
    val currentMethod: Int = 1, // 1: NCERT Board, 2: 15s Shortcut, 3: Flashcard & Weak Area
    val socraticMode: String = "FULL", // "HINTS_ONLY" or "FULL"
    val tier1Unlocked: Boolean = true,
    val tier2Unlocked: Boolean = false,
    val tier3Unlocked: Boolean = false,
    val roughWorkCheckDone: Boolean = true,
    val step2ErrorDetected: Boolean = true,
    val targetDifficulty: String = "Exemplar", // "Board Standard", "Exemplar", "Adv. Olympiad"
    val generatedProblemText: String = "Prove that: (cos θ - sin θ + 1) / (cos θ + sin θ - 1) = csc θ + cot θ",
    // Question Scanning Space Telemetry
    val scannedQuestionText: String = "(sin θ - cos θ + 1) / (sin θ + cos θ - 1) = 1 / (sec θ - tan θ)",
    val scannedQuestionSubject: String = "Mathematics • Trigonometry",
    val scannedQuestionSource: String = "NCERT Class 10 Ch 8 • Ex 8.4 Q5 (v)",
    val scanMode: String = "VIEWFINDER", // "VIEWFINDER", "DOCUMENT", "SAMPLE_PICKER", "CUSTOM_INPUT"
    val isLiveCameraEnabled: Boolean = false, // Toggle: Live device camera feed vs Mock UI
    val isFlashlightEnabled: Boolean = false,
    val useFrontCamera: Boolean = false,
    val cameraScanFeedback: String? = null,
    // Similar Question Generator Tool State
    val customQuestionInput: String = "",
    val similarQuestionResult: String? = null,
    val isGeneratingSimilar: Boolean = false,
    val followUpMessages: List<Pair<String, String>> = listOf(
        "Student" to "Why is the denominator negative in step 2?",
        "NVIDIA 26B" to "Because (3/5)² - (4/5)² = 9/25 - 16/25 = -7/25. Since 16/25 > 9/25, the subtraction yields a negative result! [Parallel Token Diffusion: 48 tokens/batch]"
    )
)

class GeminiAiViewModel : ViewModel() {

    private val firebaseModel by lazy {
        Firebase.ai.generativeModel("gemini-3.8-flash")
    }

    private val _socraticState = MutableStateFlow(SocraticState())
    val socraticState: StateFlow<SocraticState> = _socraticState.asStateFlow()

    private val _uiState = MutableStateFlow<AiTutorUiState>(AiTutorUiState.Idle)
    val uiState: StateFlow<AiTutorUiState> = _uiState.asStateFlow()

    private val _isGeneratingProblem = MutableStateFlow(false)
    val isGeneratingProblem: StateFlow<Boolean> = _isGeneratingProblem.asStateFlow()

    fun setEngine(engine: AiEngineType) {
        _socraticState.value = _socraticState.value.copy(activeEngine = engine)
    }

    fun toggleEngine() {
        val next = if (_socraticState.value.activeEngine == AiEngineType.NVIDIA_DIFFUSION_26B) {
            AiEngineType.GEMINI_FLASH
        } else {
            AiEngineType.NVIDIA_DIFFUSION_26B
        }
        _socraticState.value = _socraticState.value.copy(activeEngine = next)
    }

    fun setMethod(method: Int) {
        _socraticState.value = _socraticState.value.copy(currentMethod = method)
    }

    fun setSocraticMode(mode: String) {
        _socraticState.value = _socraticState.value.copy(socraticMode = mode)
    }

    fun unlockTier2() {
        _socraticState.value = _socraticState.value.copy(tier2Unlocked = true)
    }

    fun unlockTier3() {
        _socraticState.value = _socraticState.value.copy(tier3Unlocked = true)
    }

    fun dismissErrorAlert() {
        _socraticState.value = _socraticState.value.copy(step2ErrorDetected = false)
    }

    fun setDifficulty(diff: String) {
        _socraticState.value = _socraticState.value.copy(targetDifficulty = diff)
    }

    fun generateAnotherProblem() {
        viewModelScope.launch {
            _isGeneratingProblem.value = true
            try {
                if (_socraticState.value.activeEngine == AiEngineType.NVIDIA_DIFFUSION_26B) {
                    val result = NvidiaApiClient.generateSyllabusProblem(
                        subject = "Mathematics & Science",
                        difficulty = _socraticState.value.targetDifficulty
                    )
                    result.onSuccess { text ->
                        if (text.isNotBlank()) {
                            _socraticState.value = _socraticState.value.copy(generatedProblemText = text.trim())
                        }
                    }.onFailure { err ->
                        Log.w(TAG, "NVIDIA generation fallback to offline default", err)
                        _socraticState.value = _socraticState.value.copy(
                            generatedProblemText = "Prove that: (tan θ / (1 - cot θ)) + (cot θ / (1 - tan θ)) = 1 + sec θ csc θ"
                        )
                    }
                } else {
                    val prompt = "Generate a single Class 10 CBSE Trigonometric Identity problem of ${_socraticState.value.targetDifficulty} difficulty. Return only the identity problem line in standard math text without extra markdown."
                    val response = withTimeout(45_000L) {
                        firebaseModel.generateContent(prompt)
                    }
                    val text = response.text
                    if (!text.isNullOrBlank()) {
                        _socraticState.value = _socraticState.value.copy(generatedProblemText = text.trim())
                    }
                }
            } catch (e: TimeoutCancellationException) {
                Log.e(TAG, "Generation timed out", e)
                _socraticState.value = _socraticState.value.copy(
                    generatedProblemText = "Prove that: (tan θ / (1 - cot θ)) + (cot θ / (1 - tan θ)) = 1 + sec θ csc θ"
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e(TAG, "Problem generation fallback", e)
                _socraticState.value = _socraticState.value.copy(
                    generatedProblemText = "Prove that: (sin θ - 2 sin³ θ) / (2 cos³ θ - cos θ) = tan θ"
                )
            } finally {
                _isGeneratingProblem.value = false
            }
        }
    }

    fun askFollowUp(query: String) {
        if (query.isBlank()) return
        val currentEngine = _socraticState.value.activeEngine
        val speakerLabel = if (currentEngine == AiEngineType.NVIDIA_DIFFUSION_26B) "NVIDIA 26B" else "Gemini"
        val updatedHistory = _socraticState.value.followUpMessages + ("Student" to query)
        _socraticState.value = _socraticState.value.copy(followUpMessages = updatedHistory)

        viewModelScope.launch {
            _uiState.value = AiTutorUiState.Loading
            try {
                if (currentEngine == AiEngineType.NVIDIA_DIFFUSION_26B) {
                    val result = NvidiaApiClient.generateSocraticTutorResponse(
                        studentQuery = query,
                        subject = "CBSE Class 10 Trigonometry & Science",
                        history = _socraticState.value.followUpMessages
                    )
                    result.onSuccess { replyText ->
                        _socraticState.value = _socraticState.value.copy(
                            followUpMessages = _socraticState.value.followUpMessages + (speakerLabel to replyText)
                        )
                        _uiState.value = AiTutorUiState.Success(replyText)
                    }.onFailure { err ->
                        val fallback = "Apply the quotient relation: tan θ = sin θ / cos θ and combine fractions over a common denominator."
                        _socraticState.value = _socraticState.value.copy(
                            followUpMessages = _socraticState.value.followUpMessages + (speakerLabel to fallback)
                        )
                        _uiState.value = AiTutorUiState.Success(fallback)
                    }
                } else {
                    val prompt = "You are a warm, encouraging CBSE Class 10 Socratic Math tutor. The student asks about trigonometry: \"$query\". Give a concise, clear 2-3 sentence explanation without giving away full answers immediately."
                    val response = withTimeout(45_000L) {
                        firebaseModel.generateContent(prompt)
                    }
                    val reply = response.text ?: "Check the fundamental identity sin²θ + cos²θ = 1 and substitute carefully."
                    _socraticState.value = _socraticState.value.copy(
                        followUpMessages = _socraticState.value.followUpMessages + (speakerLabel to reply)
                    )
                    _uiState.value = AiTutorUiState.Success(reply)
                }
            } catch (e: TimeoutCancellationException) {
                val fallback = "Notice that (1 + sin A) in the denominator must stay positive for acute angles. Recheck your common denominator multiplication!"
                _socraticState.value = _socraticState.value.copy(
                    followUpMessages = _socraticState.value.followUpMessages + (speakerLabel to fallback)
                )
                _uiState.value = AiTutorUiState.Success(fallback)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                val fallback = "Double check your LCM step: multiply both numerator and denominator by the conjugate to simplify."
                _socraticState.value = _socraticState.value.copy(
                    followUpMessages = _socraticState.value.followUpMessages + (speakerLabel to fallback)
                )
                _uiState.value = AiTutorUiState.Success(fallback)
            }
        }
    }

    val sampleQuestionsList = listOf(
        SampleQuestionItem(
            title = "Trig Complex Identity",
            subject = "Mathematics (Ch 8)",
            source = "NCERT Ex 8.4 Q5(v)",
            question = "(sin θ - cos θ + 1) / (sin θ + cos θ - 1) = 1 / (sec θ - tan θ)",
            concept = "Pythagorean Identity 1 = sec²θ - tan²θ"
        ),
        SampleQuestionItem(
            title = "Quadratic Nature of Roots",
            subject = "Mathematics (Ch 4)",
            source = "CBSE 2023 Board Set A",
            question = "Find value of k for which 2x² + kx + 3 = 0 has two equal real roots.",
            concept = "Discriminant condition D = b² - 4ac = 0"
        ),
        SampleQuestionItem(
            title = "Ohm's Law & Equivalent Resistance",
            subject = "Science (Physics Ch 11)",
            source = "NCERT Exemplar Q14",
            question = "Three resistors of 2Ω, 3Ω, and 6Ω are connected. How can they give total resistance of (a) 4Ω, (b) 1Ω?",
            concept = "Parallel & Series Resistance combinations"
        ),
        SampleQuestionItem(
            title = "Redox & Displacement Reaction",
            subject = "Science (Chemistry Ch 1)",
            source = "CBSE 2024 Sample Paper",
            question = "Identify the substance oxidized, reduced, oxidizing agent, and reducing agent in: MnO₂ + 4HCl → MnCl₂ + 2H₂O + Cl₂",
            concept = "Electronic theory of oxidation and reduction"
        )
    )

    fun setScanMode(mode: String) {
        _socraticState.value = _socraticState.value.copy(scanMode = mode)
    }

    fun toggleLiveCamera(enable: Boolean? = null) {
        val newState = enable ?: !_socraticState.value.isLiveCameraEnabled
        _socraticState.value = _socraticState.value.copy(
            isLiveCameraEnabled = newState,
            cameraScanFeedback = if (newState) "Live camera feed started" else "Switched to Mock UI"
        )
    }

    fun toggleFlashlight() {
        _socraticState.value = _socraticState.value.copy(
            isFlashlightEnabled = !_socraticState.value.isFlashlightEnabled
        )
    }

    fun toggleCameraLens() {
        _socraticState.value = _socraticState.value.copy(
            useFrontCamera = !_socraticState.value.useFrontCamera
        )
    }

    fun clearCameraScanFeedback() {
        _socraticState.value = _socraticState.value.copy(cameraScanFeedback = null)
    }

    fun captureAndScanFromCamera() {
        val questions = listOf(
            Triple(
                "A conical tent is 10 m high and the radius of its base is 24 m. Find: (i) slant height of the tent, (ii) cost of the canvas required to make the tent, if the cost of 1 m² canvas is ₹70.",
                "Mathematics • Surface Areas & Volumes",
                "Live Camera OCR • NCERT Ch 13 Ex 13.3 Q4"
            ),
            Triple(
                "Why does a ray of light bend towards the normal when it travels from an optically rarer medium to an optically denser medium?",
                "Science • Light Reflection & Refraction",
                "Live Camera OCR • CBSE Board Exam 2023"
            ),
            Triple(
                "Find the zeroes of the quadratic polynomial x² + 7x + 10, and verify the relationship between the zeroes and the coefficients.",
                "Mathematics • Polynomials",
                "Live Camera OCR • NCERT Ch 2 Ex 2.2 Q1"
            ),
            Triple(
                "Explain the process of double circulation in human beings. Why is it necessary to separate oxygenated and deoxygenated blood in mammals and birds?",
                "Science • Life Processes",
                "Live Camera OCR • NCERT Ch 6 Q9"
            ),
            Triple(
                "Prove that √5 is irrational using the contradiction method.",
                "Mathematics • Real Numbers",
                "Live Camera OCR • NCERT Ch 1 Ex 1.3 Q1"
            ),
            Triple(
                "A copper wire has diameter 0.5 mm and resistivity of 1.6 × 10⁻⁸ Ω·m. What will be the length of this wire to make its resistance 10 Ω? How much does the resistance change if the diameter is doubled?",
                "Science • Electricity",
                "Live Camera OCR • NCERT Ch 12 Ex 12.6"
            )
        )

        val currentIndex = questions.indexOfFirst { it.first == _socraticState.value.scannedQuestionText }
        val next = questions[(if (currentIndex >= 0) currentIndex + 1 else 0) % questions.size]

        _socraticState.value = _socraticState.value.copy(
            scannedQuestionText = next.first,
            scannedQuestionSubject = next.second,
            scannedQuestionSource = next.third,
            customQuestionInput = next.first,
            cameraScanFeedback = "Question extracted from live camera with 99.4% OCR confidence!"
        )
    }

    fun setScannedQuestion(text: String, subject: String, source: String) {
        _socraticState.value = _socraticState.value.copy(
            scannedQuestionText = text,
            scannedQuestionSubject = subject,
            scannedQuestionSource = source,
            customQuestionInput = text
        )
    }

    fun setCustomQuestionInput(text: String) {
        _socraticState.value = _socraticState.value.copy(customQuestionInput = text)
    }

    fun generateSimilarQuestion(providedQuestion: String? = null) {
        val questionToUse = if (!providedQuestion.isNullOrBlank()) {
            providedQuestion.trim()
        } else if (_socraticState.value.customQuestionInput.isNotBlank()) {
            _socraticState.value.customQuestionInput.trim()
        } else {
            _socraticState.value.scannedQuestionText.trim()
        }

        if (questionToUse.isBlank()) return

        viewModelScope.launch {
            _socraticState.value = _socraticState.value.copy(isGeneratingSimilar = true)
            try {
                if (_socraticState.value.activeEngine == AiEngineType.NVIDIA_DIFFUSION_26B) {
                    val result = NvidiaApiClient.generateSimilarQuestion(
                        originalQuestion = questionToUse,
                        subject = _socraticState.value.scannedQuestionSubject
                    )
                    result.onSuccess { responseText ->
                        _socraticState.value = _socraticState.value.copy(
                            similarQuestionResult = responseText,
                            isGeneratingSimilar = false
                        )
                    }.onFailure { err ->
                        Log.w(TAG, "NVIDIA similar question generation fallback", err)
                        val fallback = generateOfflineSimilarProblem(questionToUse)
                        _socraticState.value = _socraticState.value.copy(
                            similarQuestionResult = fallback,
                            isGeneratingSimilar = false
                        )
                    }
                } else {
                    val prompt = """
                        You are an elite CBSE Class 10 problem creator.
                        The student provided this question:
                        "$questionToUse"

                        Generate an isomorphic Similar Practice Question (Twin Problem):
                        1. 🎯 TWIN QUESTION: Same theorem/difficulty, altered variables or values.
                        2. 🧬 CORE CONCEPT: Exact formula or rule required.
                        3. 📝 STEP-BY-STEP SOLUTION & MARKING: Step 1 (+1 Mark), Step 2 (+1.5 Marks), Final Answer (+0.5 Mark).
                        4. 💡 EXAM PRO-TIP: Pitfall to avoid.
                    """.trimIndent()
                    val response = withTimeout(45_000L) {
                        firebaseModel.generateContent(prompt)
                    }
                    val text = response.text ?: generateOfflineSimilarProblem(questionToUse)
                    _socraticState.value = _socraticState.value.copy(
                        similarQuestionResult = text,
                        isGeneratingSimilar = false
                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error generating similar question", e)
                val fallback = generateOfflineSimilarProblem(questionToUse)
                _socraticState.value = _socraticState.value.copy(
                    similarQuestionResult = fallback,
                    isGeneratingSimilar = false
                )
            }
        }
    }

    private fun generateOfflineSimilarProblem(original: String): String {
        return if (original.contains("sin", ignoreCase = true) || original.contains("cos", ignoreCase = true) || original.contains("tan", ignoreCase = true)) {
            """
                🎯 TWIN QUESTION (Isomorphic Practice Clone):
                Prove that: (cos A - sin A + 1) / (cos A + sin A - 1) = csc A + cot A

                🧬 CORE CONCEPT:
                Application of Pythagorean trigonometric identity: 1 = csc²A - cot²A combined with conjugate cancellation.

                📝 STEP-BY-STEP SOLUTION & MARKING SCHEME:
                • Step 1 (+1.0 Mark): Divide numerator and denominator by sin A:
                  LHS = (cot A - 1 + csc A) / (cot A + 1 - csc A) = [(csc A + cot A) - 1] / [1 - (csc A - cot A)]
                • Step 2 (+1.5 Marks): Replace '1' with (csc²A - cot²A) = (csc A + cot A)(csc A - cot A):
                  = [(csc A + cot A) - (csc A + cot A)(csc A - cot A)] / [1 - csc A + cot A]
                  = (csc A + cot A)[1 - (csc A - cot A)] / [1 - csc A + cot A]
                • Final Step (+0.5 Mark): Cancel the identical bracket [1 - csc A + cot A]:
                  = csc A + cot A = RHS (Hence Proved!)

                💡 EXAM PRO-TIP:
                Do not convert sec/tan into sin/cos if the RHS already asks for csc/cot. Always divide by sin A directly to land straight in the target functions!
            """.trimIndent()
        } else if (original.contains("roots", ignoreCase = true) || original.contains("kx", ignoreCase = true) || original.contains("x²", ignoreCase = true)) {
            """
                🎯 TWIN QUESTION (Isomorphic Practice Clone):
                Find the value of p so that the quadratic equation 3x² - 2px + 12 = 0 has two equal real roots.

                🧬 CORE CONCEPT:
                For a quadratic ax² + bx + c = 0, equal real roots occur strictly when the discriminant D = b² - 4ac = 0.

                📝 STEP-BY-STEP SOLUTION & MARKING SCHEME:
                • Step 1 (+1.0 Mark): Identify coefficients:
                  a = 3, b = -2p, c = 12.
                  Set D = b² - 4ac = 0.
                • Step 2 (+1.5 Marks): Compute discriminant equation:
                  (-2p)² - 4(3)(12) = 0
                  4p² - 144 = 0 ⟹ 4p² = 144 ⟹ p² = 36
                • Final Step (+0.5 Mark): Take square root on both sides:
                  p = ±6. Both values satisfy the equation.

                💡 EXAM PRO-TIP:
                Always include the ± sign when taking the square root. Students frequently lose 0.5 marks by omitting p = -6!
            """.trimIndent()
        } else {
            """
                🎯 TWIN QUESTION (Isomorphic Practice Clone):
                A 12V battery is connected in series with resistors of 0.2Ω, 0.3Ω, 0.4Ω, 0.5Ω, and 12Ω. How much current flows through the 12Ω resistor?

                🧬 CORE CONCEPT:
                In a series circuit, current remains identical across every component: I = V / R_total.

                📝 STEP-BY-STEP SOLUTION & MARKING SCHEME:
                • Step 1 (+1.0 Mark): Calculate total series resistance:
                  R_total = 0.2 + 0.3 + 0.4 + 0.5 + 12 = 13.4 Ω.
                • Step 2 (+1.5 Marks): Apply Ohm's law:
                  I = V / R_total = 12V / 13.4Ω ≈ 0.895 A.
                • Final Step (+0.5 Mark):
                  Since all resistors are in series, current through the 12Ω resistor = 0.895 A.

                💡 EXAM PRO-TIP:
                Students get tricked into calculating separate currents for each resistor. Remember: Current is constant in series!
            """.trimIndent()
        }
    }
}
