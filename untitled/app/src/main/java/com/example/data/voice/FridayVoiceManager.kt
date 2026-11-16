package com.example.data.voice

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class FridayVoiceManager(context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var isInitialized = false

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    private val _lastSpeechText = MutableStateFlow(
        "F.R.I.D.A.Y. online. All 7 CBSE subjects and Arc Reactor HUD diagnostics are operational, boss."
    )
    val lastSpeechText: StateFlow<String> = _lastSpeechText.asStateFlow()

    private var pendingSpeech: String? = null

    init {
        try {
            tts = TextToSpeech(context.applicationContext, this)
        } catch (_: Exception) {
            isInitialized = false
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts?.let { engine ->
                val result = engine.setLanguage(Locale.US)
                if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {
                    engine.setPitch(1.1f) // Slightly higher, crisp tactical AI voice
                    engine.setSpeechRate(1.02f)
                    isInitialized = true

                    engine.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                        override fun onStart(utteranceId: String?) {
                            _isSpeaking.value = true
                        }

                        override fun onDone(utteranceId: String?) {
                            _isSpeaking.value = false
                        }

                        override fun onError(utteranceId: String?) {
                            _isSpeaking.value = false
                        }
                    })

                    pendingSpeech?.let { speech ->
                        speak(speech)
                        pendingSpeech = null
                    }
                }
            }
        }
    }

    fun speak(text: String) {
        _lastSpeechText.value = text
        if (isInitialized && tts != null) {
            try {
                _isSpeaking.value = true
                tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, "friday_speech_${System.currentTimeMillis()}")
            } catch (_: Exception) {
                _isSpeaking.value = false
            }
        } else {
            pendingSpeech = text
        }
    }

    fun replaySpeech() {
        speak(_lastSpeechText.value)
    }

    fun stop() {
        try {
            tts?.stop()
        } catch (_: Exception) {}
        _isSpeaking.value = false
    }

    fun shutdown() {
        try {
            tts?.stop()
            tts?.shutdown()
        } catch (_: Exception) {}
    }
}
