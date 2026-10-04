package com.example.feature.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.common.AppConstants
import com.example.domain.project.CinematicProject
import com.example.domain.project.ProjectStatus
import com.example.domain.video.AspectRatio
import com.example.domain.video.CameraAngle
import com.example.domain.video.CinematicStyle
import com.example.domain.video.GenerationRequest
import com.example.domain.video.LensType
import com.example.domain.video.LightingStyle
import com.example.domain.video.VideoDuration
import com.example.domain.voice.SupportedLanguage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

data class CreateWizardUiState(
    val currentStep: Int = 1,
    val totalSteps: Int = 10,

    // Step 1: Reference Image & Rights
    val referenceImageUri: String? = null,
    val rightsConfirmed: Boolean = false,

    // Step 2: Facial Consistency Lock
    val facialLockStrictness: Float = 1.0f, // 100% strict identity preservation
    val lockedFeatures: Set<String> = setOf(
        "Core Facial Structure",
        "Facial Proportions",
        "Skin Tone",
        "Forehead & Hairline",
        "Eye Shape & Eyebrows",
        "Nose Bridge & Contours",
        "Lips & Mouth Shape",
        "Jawline, Chin & Ears"
    ),

    // Step 3: Natural Language Prompt
    val promptText: String = "",
    val negativePrompt: String = "deformed features, changing face, face warping, double mouth, blur, low quality, artifacts",

    // Step 4: Character Action & Motion
    val characterAction: String = "Walks forward with resolute determination",
    val facialExpression: String = "Intense, focused, unwavering",
    val bodyGesture: String = "Subtle cinematic head turn toward camera",
    val environmentDetails: String = "Atmospheric city street with wet pavement and volumetric mist",

    // Step 5: Camera Angle & Lens
    val cameraAngle: CameraAngle = CameraAngle.DYNAMIC_TRACKING,
    val lensType: LensType = LensType.ANAMORPHIC_35MM,

    // Step 6: Lighting & Mood
    val lightingStyle: LightingStyle = LightingStyle.GOLDEN_HOUR,
    val cinematicStyle: CinematicStyle = CinematicStyle.HOLLYWOOD_BLOCKBUSTER,
    val emotionMood: String = "Epic, triumphant, and profound",

    // Step 7: Dialogue Input
    val voiceEnabled: Boolean = true,
    val dialogueText: String = "The cinematic journey begins right here.",

    // Step 8: Voice Selection
    val selectedLanguage: SupportedLanguage = SupportedLanguage.ENGLISH,
    val selectedVoiceId: String = "voice_cinematic_male_01",
    val voiceSpeed: Float = 1.0f,
    val voicePitch: Float = 1.0f,

    // Step 9: Duration & Aspect Ratio
    val duration: VideoDuration = VideoDuration.SEC_10,
    val aspectRatio: AspectRatio = AspectRatio.WIDESCREEN_16_9,

    // Step 10: Review & Generation State
    val isSavingDraft: Boolean = false,
    val isDraftSaved: Boolean = false,
    val savedProject: CinematicProject? = null,
    val errorMessage: String? = null
) {
    val stepTitle: String
        get() = when (currentStep) {
            1 -> "Reference Image Upload"
            2 -> "Character Consistency Lock"
            3 -> "Cinematic Prompt Input"
            4 -> "Character Action & Motion"
            5 -> "Camera Angle & Lens"
            6 -> "Lighting & Visual Mood"
            7 -> "Dialogue Script"
            8 -> "Voice & Language Profile"
            9 -> "Duration & Aspect Ratio"
            10 -> "Review & Generate"
            else -> ""
        }

    fun isCurrentStepValid(): Boolean {
        return when (currentStep) {
            1 -> referenceImageUri != null && rightsConfirmed
            2 -> rightsConfirmed && lockedFeatures.isNotEmpty()
            3 -> promptText.trim().length >= 5
            4 -> characterAction.isNotBlank()
            5 -> true
            6 -> true
            7 -> !voiceEnabled || dialogueText.trim().isNotEmpty()
            8 -> selectedVoiceId.isNotBlank()
            9 -> duration.isFreeTier // Free tier max 30s validation
            10 -> isAllDataValid()
            else -> true
        }
    }

    fun isAllDataValid(): Boolean {
        return referenceImageUri != null &&
                rightsConfirmed &&
                promptText.trim().length >= 5 &&
                (!voiceEnabled || dialogueText.trim().isNotEmpty())
    }

    /**
     * Builds structured cinematic instruction block following the Parser Matrix
     */
    fun buildStructuredCinematicInstructions(): String {
        return buildString {
            appendLine("=== CINEMATIC VIDEO GENERATION PARSER MATRIX ===")
            appendLine("Character: Reference image facial geometry locked (100% identity preservation)")
            appendLine("Action: $characterAction")
            appendLine("Expression: $facialExpression")
            appendLine("Gesture: $bodyGesture")
            appendLine("Environment: $environmentDetails")
            appendLine("Camera: ${cameraAngle.label} - ${cameraAngle.cinematicPromptSnippet}")
            appendLine("Lens: ${lensType.label} - ${lensType.focalLength}")
            appendLine("Lighting: ${lightingStyle.label} - ${lightingStyle.promptSnippet}")
            appendLine("Emotion: $emotionMood")
            if (voiceEnabled) {
                appendLine("Dialogue: \"$dialogueText\" [Language: ${selectedLanguage.displayName}]")
                appendLine("Voice: Profile ID: $selectedVoiceId (Speed: ${voiceSpeed}x, Pitch: ${voicePitch}x)")
            } else {
                appendLine("Dialogue: [None / Instrumental]")
            }
            appendLine("Duration: ${duration.seconds}s [Free Tier compliant: ${duration.isFreeTier}]")
            appendLine("Style: ${cinematicStyle.label}")
            appendLine("AspectRatio: ${aspectRatio.displayLabel}")
            appendLine("NegativePrompt: $negativePrompt")
            appendLine("Continuity: Strict Reference Image Face Lock Active")
        }
    }
}

class CreateWizardViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CreateWizardUiState())
    val uiState: StateFlow<CreateWizardUiState> = _uiState.asStateFlow()

    fun nextStep() {
        val current = _uiState.value
        if (current.isCurrentStepValid() && current.currentStep < current.totalSteps) {
            _uiState.value = current.copy(
                currentStep = current.currentStep + 1,
                errorMessage = null
            )
        } else if (!current.isCurrentStepValid()) {
            _uiState.value = current.copy(
                errorMessage = getValidationErrorMessage(current.currentStep)
            )
        }
    }

    fun previousStep() {
        val current = _uiState.value
        if (current.currentStep > 1) {
            _uiState.value = current.copy(
                currentStep = current.currentStep - 1,
                errorMessage = null
            )
        }
    }

    fun setStep(step: Int) {
        if (step in 1.._uiState.value.totalSteps) {
            _uiState.value = _uiState.value.copy(currentStep = step, errorMessage = null)
        }
    }

    fun setReferenceImageUri(uriString: String?) {
        _uiState.value = _uiState.value.copy(referenceImageUri = uriString, errorMessage = null)
    }

    fun setRightsConfirmed(confirmed: Boolean) {
        _uiState.value = _uiState.value.copy(rightsConfirmed = confirmed, errorMessage = null)
    }

    fun setPromptText(text: String) {
        _uiState.value = _uiState.value.copy(promptText = text, errorMessage = null)
    }

    fun setNegativePrompt(text: String) {
        _uiState.value = _uiState.value.copy(negativePrompt = text)
    }

    fun setCharacterAction(action: String) {
        _uiState.value = _uiState.value.copy(characterAction = action)
    }

    fun setFacialExpression(expression: String) {
        _uiState.value = _uiState.value.copy(facialExpression = expression)
    }

    fun setBodyGesture(gesture: String) {
        _uiState.value = _uiState.value.copy(bodyGesture = gesture)
    }

    fun setEnvironmentDetails(env: String) {
        _uiState.value = _uiState.value.copy(environmentDetails = env)
    }

    fun setCameraAngle(angle: CameraAngle) {
        _uiState.value = _uiState.value.copy(cameraAngle = angle)
    }

    fun setLensType(lens: LensType) {
        _uiState.value = _uiState.value.copy(lensType = lens)
    }

    fun setLightingStyle(lighting: LightingStyle) {
        _uiState.value = _uiState.value.copy(lightingStyle = lighting)
    }

    fun setCinematicStyle(style: CinematicStyle) {
        _uiState.value = _uiState.value.copy(cinematicStyle = style)
    }

    fun setEmotionMood(mood: String) {
        _uiState.value = _uiState.value.copy(emotionMood = mood)
    }

    fun setVoiceEnabled(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(voiceEnabled = enabled, errorMessage = null)
    }

    fun setDialogueText(dialogue: String) {
        _uiState.value = _uiState.value.copy(dialogueText = dialogue, errorMessage = null)
    }

    fun setSelectedLanguage(language: SupportedLanguage) {
        _uiState.value = _uiState.value.copy(selectedLanguage = language)
    }

    fun setSelectedVoiceId(voiceId: String) {
        _uiState.value = _uiState.value.copy(selectedVoiceId = voiceId)
    }

    fun setVoiceSpeed(speed: Float) {
        _uiState.value = _uiState.value.copy(voiceSpeed = speed)
    }

    fun setVoicePitch(pitch: Float) {
        _uiState.value = _uiState.value.copy(voicePitch = pitch)
    }

    fun setDuration(duration: VideoDuration) {
        _uiState.value = _uiState.value.copy(duration = duration)
    }

    fun setAspectRatio(ratio: AspectRatio) {
        _uiState.value = _uiState.value.copy(aspectRatio = ratio)
    }

    fun saveDraftAndQueue(onSuccess: (CinematicProject) -> Unit) {
        val state = _uiState.value
        if (!state.isAllDataValid()) {
            _uiState.value = state.copy(errorMessage = "Please complete all required fields before generating.")
            return
        }

        viewModelScope.launch {
            _uiState.value = state.copy(isSavingDraft = true, errorMessage = null)

            // Construct Generation Request
            val request = GenerationRequest(
                requestId = UUID.randomUUID().toString(),
                referenceImageUri = state.referenceImageUri,
                rightsConfirmed = state.rightsConfirmed,
                userPrompt = state.promptText,
                structuredPrompt = state.buildStructuredCinematicInstructions(),
                dialogueText = if (state.voiceEnabled) state.dialogueText else "",
                voiceProfileId = state.selectedVoiceId,
                characterAction = state.characterAction,
                cameraAngle = state.cameraAngle,
                lensType = state.lensType,
                lightingStyle = state.lightingStyle,
                cinematicStyle = state.cinematicStyle,
                duration = state.duration,
                aspectRatio = state.aspectRatio,
                negativePrompt = state.negativePrompt
            )

            // Store inputs cleanly in state draft
            val project = CinematicProject(
                id = UUID.randomUUID().toString(),
                title = if (state.promptText.length > 25) state.promptText.take(25) + "..." else state.promptText,
                promptSummary = state.characterAction,
                request = request,
                status = ProjectStatus.DRAFT,
                durationSeconds = state.duration.seconds,
                aspectRatio = state.aspectRatio
            )

            _uiState.value = _uiState.value.copy(
                isSavingDraft = false,
                isDraftSaved = true,
                savedProject = project
            )

            onSuccess(project)
        }
    }

    private fun getValidationErrorMessage(step: Int): String {
        return when (step) {
            1 -> "Please select a reference image and confirm legal rights to continue."
            2 -> "Please verify facial feature lock checklist."
            3 -> "Please enter a descriptive cinematic prompt (at least 5 characters)."
            4 -> "Please specify character action and motion."
            7 -> "Dialogue text cannot be empty when voice is enabled."
            9 -> "Selected duration exceeds free tier allowance (max 30s)."
            else -> "Please complete all required settings."
        }
    }
}
