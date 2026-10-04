package com.example.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.common.AppConstants
import com.example.domain.project.CinematicProject
import com.example.domain.project.ProjectStatus
import com.example.domain.subscription.PlanTier
import com.example.domain.video.AspectRatio
import com.example.domain.video.CameraAngle
import com.example.domain.video.CinematicStyle
import com.example.domain.video.GenerationRequest
import com.example.domain.video.LensType
import com.example.domain.video.LightingStyle
import com.example.domain.video.VideoDuration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CinemaTemplate(
    val id: String,
    val title: String,
    val style: CinematicStyle,
    val cameraAngle: CameraAngle,
    val lens: LensType,
    val lighting: LightingStyle,
    val description: String,
    val samplePrompt: String
)

data class AiToolItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val badge: String
)

data class HomeUiState(
    val currentPlanTier: PlanTier = PlanTier.FREE,
    val usedGenerationsThisMonth: Int = 2,
    val totalMonthlyGenerationsAllowed: Int = AppConstants.FREE_TIER_MONTHLY_GENERATIONS,
    val maxDurationSeconds: Int = AppConstants.MAX_FREE_DURATION_SECONDS,
    val activeDraft: CinematicProject? = null,
    val recentProjects: List<CinematicProject> = emptyList(),
    val templates: List<CinemaTemplate> = emptyList(),
    val aiTools: List<AiToolItem> = emptyList(),
    val isLoading: Boolean = false
)

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            // Curated Director Templates
            val templates = listOf(
                CinemaTemplate(
                    id = "template_hollywood_blockbuster",
                    title = "Hollywood Sci-Fi Blockbuster",
                    style = CinematicStyle.HOLLYWOOD_BLOCKBUSTER,
                    cameraAngle = CameraAngle.DYNAMIC_TRACKING,
                    lens = LensType.ANAMORPHIC_35MM,
                    lighting = LightingStyle.CYBERPUNK_NEON,
                    description = "Anamorphic 35mm glass, cybernetic volumetric neon, and dynamic tracking.",
                    samplePrompt = "Hero walks decisively through neon-lit rain-slicked city streets while looking toward the camera."
                ),
                CinemaTemplate(
                    id = "template_bollywood_drama",
                    title = "Bollywood Emotional Drama",
                    style = CinematicStyle.BOLLYWOOD_DRAMA,
                    cameraAngle = CameraAngle.SLOW_PUSH_IN,
                    lens = LensType.PRIME_50MM,
                    lighting = LightingStyle.GOLDEN_HOUR,
                    description = "Warm golden hour rims, intense 50mm focus on emotional facial delivery.",
                    samplePrompt = "Character delivers a powerful, heartfelt speech bathed in sunset golden light."
                ),
                CinemaTemplate(
                    id = "template_vintage_noir",
                    title = "Vintage Noir Mystery",
                    style = CinematicStyle.VINTAGE_FILM_35MM,
                    cameraAngle = CameraAngle.DUTCH_ANGLE,
                    lens = LensType.PORTRAIT_85MM,
                    lighting = LightingStyle.NOIR_DRAMATIC,
                    description = "High-contrast chiaroscuro, deep shadows, and subtle psychological dutch tilt.",
                    samplePrompt = "Detective steps out from deep shadows under streetlight with intense gaze."
                )
            )

            // AI Quick Tools
            val aiTools = listOf(
                AiToolItem(
                    id = "tool_facial_lock",
                    title = "Facial Geometry Lock",
                    subtitle = "100% identity preservation",
                    badge = "ACTIVE"
                ),
                AiToolItem(
                    id = "tool_voice_studio",
                    title = "Voice & Dialogue Studio",
                    subtitle = "Hindi • Marathi • English",
                    badge = "TTS ENGINE"
                ),
                AiToolItem(
                    id = "tool_camera_matrix",
                    title = "Camera & Lens Matrix",
                    subtitle = "Anamorphic & Prime shots",
                    badge = "DIRECTOR"
                ),
                AiToolItem(
                    id = "tool_lip_sync",
                    title = "Aperture Lip-Sync",
                    subtitle = "Natural jaw & mouth tracking",
                    badge = "SYNC V2"
                )
            )

            // Sample Recent Project & Active Draft (Initial showcase state)
            val draft = CinematicProject(
                id = "draft_001",
                title = "Cyber City Awakening",
                promptSummary = "Character enters futuristic neon atrium",
                request = GenerationRequest(
                    requestId = "req_draft_01",
                    referenceImageUri = null,
                    rightsConfirmed = true,
                    userPrompt = "Character enters futuristic neon atrium",
                    structuredPrompt = "Cinematic slow push-in, anamorphic 35mm, cyan neon lighting",
                    dialogueText = "The journey has only just begun.",
                    voiceProfileId = "voice_en_01",
                    characterAction = "Walking toward camera",
                    cameraAngle = CameraAngle.SLOW_PUSH_IN,
                    lensType = LensType.ANAMORPHIC_35MM,
                    lightingStyle = LightingStyle.CYBERPUNK_NEON,
                    cinematicStyle = CinematicStyle.HOLLYWOOD_BLOCKBUSTER,
                    duration = VideoDuration.SEC_10,
                    aspectRatio = AspectRatio.WIDESCREEN_16_9
                ),
                status = ProjectStatus.READY_FOR_EDIT,
                durationSeconds = 10,
                aspectRatio = AspectRatio.WIDESCREEN_16_9
            )

            val recentProjects = listOf(
                draft,
                CinematicProject(
                    id = "project_002",
                    title = "Sunset Monologue",
                    promptSummary = "Emotional speech during golden hour",
                    request = GenerationRequest(
                        requestId = "req_proj_02",
                        referenceImageUri = null,
                        rightsConfirmed = true,
                        userPrompt = "Emotional speech during golden hour",
                        structuredPrompt = "Golden hour rim, 50mm prime, eye-level close-up",
                        dialogueText = "We will never surrender our vision.",
                        voiceProfileId = "voice_hi_01",
                        characterAction = "Speaking with conviction",
                        cameraAngle = CameraAngle.EYE_LEVEL_CLOSEUP,
                        lensType = LensType.PRIME_50MM,
                        lightingStyle = LightingStyle.GOLDEN_HOUR,
                        cinematicStyle = CinematicStyle.BOLLYWOOD_DRAMA,
                        duration = VideoDuration.SEC_15,
                        aspectRatio = AspectRatio.PORTRAIT_9_16
                    ),
                    status = ProjectStatus.COMPLETED,
                    durationSeconds = 15,
                    aspectRatio = AspectRatio.PORTRAIT_9_16,
                    isFavorite = true
                )
            )

            _uiState.value = _uiState.value.copy(
                templates = templates,
                aiTools = aiTools,
                activeDraft = draft,
                recentProjects = recentProjects
            )
        }
    }
}
