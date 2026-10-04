package com.example.domain.video

enum class AspectRatio(val displayLabel: String, val ratioFloat: Float) {
    WIDESCREEN_16_9("16:9 Cinematic", 16f / 9f),
    PORTRAIT_9_16("9:16 Shorts/Reel", 9f / 16f),
    SQUARE_1_1("1:1 Feed Post", 1f)
}

enum class VideoDuration(val seconds: Int, val isFreeTier: Boolean) {
    SEC_5(5, true),
    SEC_8(8, true),
    SEC_10(10, true),
    SEC_15(15, true),
    SEC_20(20, true),
    SEC_30(30, true),
    SEC_45(45, false),
    SEC_60(60, false)
}

enum class CameraAngle(val label: String, val cinematicPromptSnippet: String) {
    DYNAMIC_TRACKING("Dynamic Tracking", "smooth cinematic tracking shot following character movement"),
    SLOW_PUSH_IN("Slow Push-In", "slow dramatic push-in shot intensifying emotional depth"),
    LOW_ANGLE_HERO("Low-Angle Hero", "powerful low-angle hero shot emphasizing authority and grandeur"),
    EYE_LEVEL_CLOSEUP("Eye-Level Close-up", "intimate eye-level cinematic close-up with shallow depth of field"),
    WIDE_ESTABLISHING("Wide Establishing", "majestic anamorphic wide shot establishing atmospheric environment"),
    DUTCH_ANGLE("Dutch Angle", "subtle dutch angle evoking suspense, psychological tension, and drama")
}

enum class LensType(val label: String, val focalLength: String) {
    ANAMORPHIC_35MM("35mm Anamorphic", "35mm anamorphic prime lens with soft oval bokeh and subtle lens flare"),
    PRIME_50MM("50mm Cinema Prime", "50mm f/1.2 cinema prime lens producing natural perspective and hyper-sharp subject separation"),
    PORTRAIT_85MM("85mm Telephoto", "85mm cinema lens with cream-smooth background blur and creamy facial compression")
}

enum class LightingStyle(val label: String, val promptSnippet: String) {
    GOLDEN_HOUR("Golden Hour", "warm sunset golden hour key light with warm amber rim lighting"),
    NOIR_DRAMATIC("Cinematic Noir", "high-contrast chiaroscuro lighting, deep shadows, moody rim light"),
    CYBERPUNK_NEON("Neon Atmospheric", "vibrant cyan and magenta atmospheric neon lighting with wet reflective surfaces"),
    STUDIO_DIFFUSED("Studio Master", "soft diffused 3-point studio lighting with pristine skin tone reproduction"),
    MOONLIT_NIGHT("Moonlit Ambient", "cool 4500K moonlit cinematic illumination with soft volumetric fog")
}

enum class CinematicStyle(val label: String) {
    HOLLYWOOD_BLOCKBUSTER("Hollywood Blockbuster"),
    BOLLYWOOD_DRAMA("Bollywood Emotional Drama"),
    INDIE_REALISM("Indie Ultra-Realism"),
    VINTAGE_FILM_35MM("35mm Vintage Film"),
    FUTURISTIC_SCI_FI("Futuristic Sci-Fi")
}

enum class GenerationState {
    CREATED,
    VALIDATING,
    QUEUED,
    PROCESSING,
    GENERATING,
    COMPLETED,
    FAILED,
    CANCELLED
}

data class GenerationSegment(
    val segmentIndex: Int,
    val startSecond: Int,
    val endSecond: Int,
    val videoUrl: String? = null,
    val state: GenerationState = GenerationState.QUEUED
)

data class GenerationRequest(
    val requestId: String,
    val referenceImageUri: String?,
    val rightsConfirmed: Boolean,
    val userPrompt: String,
    val structuredPrompt: String,
    val dialogueText: String,
    val voiceProfileId: String,
    val characterAction: String,
    val cameraAngle: CameraAngle = CameraAngle.SLOW_PUSH_IN,
    val lensType: LensType = LensType.PRIME_50MM,
    val lightingStyle: LightingStyle = LightingStyle.GOLDEN_HOUR,
    val cinematicStyle: CinematicStyle = CinematicStyle.HOLLYWOOD_BLOCKBUSTER,
    val duration: VideoDuration = VideoDuration.SEC_10,
    val aspectRatio: AspectRatio = AspectRatio.WIDESCREEN_16_9,
    val negativePrompt: String = "deformed features, changing face, face warping, double mouth, blur, low quality, artifacts"
)

data class GenerationJob(
    val jobId: String,
    val request: GenerationRequest,
    val state: GenerationState = GenerationState.CREATED,
    val statusMessage: String = "Initializing generation pipeline...",
    val segments: List<GenerationSegment> = emptyList(),
    val finalVideoUri: String? = null,
    val audioUri: String? = null,
    val createdAtMillis: Long = System.currentTimeMillis()
)
