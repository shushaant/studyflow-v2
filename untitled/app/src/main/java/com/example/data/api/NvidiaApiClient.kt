package com.example.data.api

import android.util.Log
import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

/**
 * Client for NVIDIA NIM Cloud Inference:
 * Diffusion-based 26B parameter LLM (google/diffusiongemma-26b-a4b-it)
 * enabling parallel token generation via discrete block diffusion.
 */
object NvidiaApiClient {
    private const val TAG = "NvidiaApiClient"
    private const val ENDPOINT = "https://integrate.api.nvidia.com/v1/chat/completions"
    const val MODEL_DIFFUSION_26B = "google/diffusiongemma-26b-a4b-it"

    // Default fallback to user-provided key if BuildConfig is not yet recompiled
    private val apiKey: String
        get() {
            return try {
                val key = BuildConfig.NVIDIA_API_KEY
                if (!key.isNullOrBlank() && !key.contains("MY_NVIDIA_API_KEY")) key
                else "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"
            } catch (e: Throwable) {
                "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"
            }
        }

    /**
     * Executes parallel token generation chat completion.
     */
    suspend fun chatCompletion(
        messages: List<Pair<String, String>>,
        maxTokens: Int = 1024,
        temperature: Double = 0.7
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            val url = URL(ENDPOINT)
            val connection = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                doOutput = true
                doInput = true
                connectTimeout = 30_000
                readTimeout = 45_000
                setRequestProperty("Content-Type", "application/json")
                setRequestProperty("Accept", "application/json")
                setRequestProperty("Authorization", "Bearer $apiKey")
            }

            // Build JSON payload
            val root = JSONObject().apply {
                put("model", MODEL_DIFFUSION_26B)
                put("max_tokens", maxTokens)
                put("temperature", temperature)
                put("top_p", 0.95)

                val msgArray = JSONArray()
                for ((role, content) in messages) {
                    val m = JSONObject().apply {
                        put("role", role)
                        put("content", content)
                    }
                    msgArray.put(m)
                }
                put("messages", msgArray)
            }

            // Write body
            OutputStreamWriter(connection.outputStream, "UTF-8").use { writer ->
                writer.write(root.toString())
                writer.flush()
            }

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val responseText = BufferedReader(InputStreamReader(connection.inputStream, "UTF-8")).use { reader ->
                    reader.readText()
                }
                val json = JSONObject(responseText)
                val choices = json.getJSONArray("choices")
                if (choices.length() > 0) {
                    val choice = choices.getJSONObject(0)
                    val message = choice.getJSONObject("message")
                    val content = message.getString("content")
                    Result.success(content.trim())
                } else {
                    Result.failure(Exception("NVIDIA API returned empty choices"))
                }
            } else {
                val errorText = BufferedReader(InputStreamReader(connection.errorStream ?: connection.inputStream, "UTF-8")).use {
                    it.readText()
                }
                Log.e(TAG, "NVIDIA API HTTP $responseCode: $errorText")
                Result.failure(Exception("NVIDIA API Error $responseCode: $errorText"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "NVIDIA API network exception", e)
            Result.failure(e)
        }
    }

    /**
     * Parallel Problem Generator using 26B Diffusion model.
     */
    suspend fun generateSyllabusProblem(
        subject: String,
        difficulty: String
    ): Result<String> {
        val messages = listOf(
            "system" to "You are Shuri and F.R.I.D.A.Y., Wakanda Tactical AI and Marvel Study Assistant. " +
                    "Generate high-yield CBSE Class 10 board exam practice problems using parallel text generation.",
            "user" to "Generate a single Class 10 CBSE $subject practice problem of $difficulty level. " +
                    "Focus on high-weightage concepts. Return the complete problem statement clearly in plain text."
        )
        return chatCompletion(messages, maxTokens = 512, temperature = 0.6)
    }

    /**
     * Socratic hint progression using 26B Diffusion model.
     */
    suspend fun generateSocraticTutorResponse(
        studentQuery: String,
        subject: String,
        history: List<Pair<String, String>>
    ): Result<String> {
        val messages = mutableListOf(
            "system" to "You are a world-class Socratic CBSE Class 10 Academic Tutor powered by NVIDIA Diffusion-based 26B LLM parallel token generation. " +
                    "Guide the student through $subject step-by-step. Encourage critical thinking, provide actionable hints, and explain clearly without spoiling the entire answer at once."
        )
        for ((speaker, text) in history.takeLast(4)) {
            val role = if (speaker.equals("Student", ignoreCase = true)) "user" else "assistant"
            messages.add(role to text)
        }
        messages.add("user" to studentQuery)
        return chatCompletion(messages, maxTokens = 768, temperature = 0.7)
    }

    /**
     * Similar Question Generator using NVIDIA Diffusion 26B model:
     * Analyzes any question provided by the student, identifies the core mathematical / scientific
     * concept, and generates an isomorphic 'Twin Problem' with fresh variables and a full marking scheme.
     */
    suspend fun generateSimilarQuestion(
        originalQuestion: String,
        subject: String = "Mathematics"
    ): Result<String> {
        val messages = listOf(
            "system" to "You are an elite CBSE Class 10 Exam Specialist and Problem Architect powered by NVIDIA Diffusion 26B parallel token generation. " +
                    "When given any Class 10 question, generate a high-yield isomorphic 'Twin Problem' that tests the exact same concept/formula but with fresh parameters, accompanied by step-by-step marking breakdown.",
            "user" to """
                Here is a Class 10 CBSE question:
                "$originalQuestion"

                Generate a complete Similar Practice Twin Problem with:
                1. 🎯 TWIN QUESTION: The new practice problem (same concept, fresh numbers/angles/compounds).
                2. 🧬 CORE CONCEPT: Key identity, theorem, or law applied.
                3. 📝 STEP-BY-STEP SOLUTION & MARKING SCHEME:
                   - Step 1 (+1 Mark)
                   - Step 2 (+1.5 Marks)
                   - Final Answer (+0.5 Mark)
                4. 💡 EXAM PRO-TIP: Common student error to avoid on board exams.

                Keep formatting clean with clear headers.
            """.trimIndent()
        )
        return chatCompletion(messages, maxTokens = 950, temperature = 0.65)
    }
}
