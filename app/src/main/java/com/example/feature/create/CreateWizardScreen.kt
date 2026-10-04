package com.example.feature.create

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.core.designsystem.CinematicIcons
import com.example.core.designsystem.ElectricCyan
import com.example.core.designsystem.GoldDark
import com.example.core.designsystem.GoldPrimary
import com.example.core.designsystem.LocalCinematicColors
import com.example.core.designsystem.ObsidianBlack
import com.example.core.designsystem.ObsidianBorder
import com.example.core.designsystem.PillShape
import com.example.core.designsystem.VideoFrameShape
import com.example.core.ui.BadgeStyle
import com.example.core.ui.CinematicBadge
import com.example.core.ui.CinematicButton
import com.example.core.ui.CinematicSecondaryButton
import com.example.core.ui.CinematicTopBar
import com.example.core.ui.GlassmorphicCard
import com.example.core.ui.SectionHeader
import com.example.domain.video.AspectRatio
import com.example.domain.video.CameraAngle
import com.example.domain.video.CinematicStyle
import com.example.domain.video.LensType
import com.example.domain.video.LightingStyle
import com.example.domain.video.VideoDuration
import com.example.domain.voice.SupportedLanguage

@Composable
fun CreateWizardScreen(
    onCloseClick: () -> Unit,
    onSuccessGenerated: () -> Unit = {},
    viewModel: CreateWizardViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val colors = LocalCinematicColors.current
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack)
            .testTag("create_wizard_screen")
    ) {
        // Wizard Header
        CinematicTopBar(
            title = "Cinematic Director",
            subtitle = "Step ${uiState.currentStep} of ${uiState.totalSteps}: ${uiState.stepTitle}",
            showBack = true,
            onBackClick = {
                if (uiState.currentStep > 1) {
                    viewModel.previousStep()
                } else {
                    onCloseClick()
                }
            },
            trailingIcon = CinematicIcons.Close,
            onTrailingClick = onCloseClick
        )

        // Step Progress Bar
        WizardProgressBar(
            currentStep = uiState.currentStep,
            totalSteps = uiState.totalSteps
        )

        // Error message banner (if validation failed)
        AnimatedVisibility(visible = uiState.errorMessage != null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colors.accentRed.copy(alpha = 0.15f))
                    .border(0.5.dp, colors.accentRed, RoundedCornerShape(0.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = CinematicIcons.Error,
                        contentDescription = null,
                        tint = colors.accentRed,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = uiState.errorMessage ?: "",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Scrollable Step Content Container
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Crossfade(
                targetState = uiState.currentStep,
                animationSpec = tween(250),
                label = "step_crossfade"
            ) { step ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    when (step) {
                        1 -> item { Step1ReferenceImage(uiState, viewModel) }
                        2 -> item { Step2CharacterConsistency(uiState, viewModel) }
                        3 -> item { Step3PromptInput(uiState, viewModel) }
                        4 -> item { Step4CharacterAction(uiState, viewModel) }
                        5 -> item { Step5CameraLens(uiState, viewModel) }
                        6 -> item { Step6LightingMood(uiState, viewModel) }
                        7 -> item { Step7DialogueInput(uiState, viewModel) }
                        8 -> item { Step8VoiceSelection(uiState, viewModel) }
                        9 -> item { Step9DurationAspectRatio(uiState, viewModel) }
                        10 -> item { Step10ReviewGenerate(uiState, viewModel, onSuccessGenerated) }
                    }
                }
            }
        }

        // Wizard Bottom Navigation Bar (Prev / Next)
        WizardBottomActions(
            uiState = uiState,
            onPrevClick = { viewModel.previousStep() },
            onNextClick = {
                if (uiState.currentStep == uiState.totalSteps) {
                    viewModel.saveDraftAndQueue {
                        onSuccessGenerated()
                    }
                } else {
                    viewModel.nextStep()
                }
            }
        )
    }
}

@Composable
private fun WizardProgressBar(
    currentStep: Int,
    totalSteps: Int
) {
    val colors = LocalCinematicColors.current
    val progress = currentStep.toFloat() / totalSteps.toFloat()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.surface)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "DIRECTOR PIPELINE",
                color = colors.textSecondary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Text(
                text = "$currentStep of $totalSteps",
                color = GoldPrimary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(PillShape),
            color = GoldPrimary,
            trackColor = colors.surfaceElevated
        )
    }
}

@Composable
private fun WizardBottomActions(
    uiState: CreateWizardUiState,
    onPrevClick: () -> Unit,
    onNextClick: () -> Unit
) {
    val colors = LocalCinematicColors.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.surface.copy(alpha = 0.98f))
            .border(0.5.dp, colors.border)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (uiState.currentStep > 1) {
            CinematicSecondaryButton(
                text = "Back",
                icon = CinematicIcons.Back,
                onClick = onPrevClick,
                testTag = "wizard_back_button"
            )
        } else {
            Spacer(modifier = Modifier.width(1.dp))
        }

        CinematicButton(
            text = if (uiState.currentStep == uiState.totalSteps) "QUEUE GENERATION" else "Next Step",
            icon = if (uiState.currentStep == uiState.totalSteps) CinematicIcons.AI else CinematicIcons.Forward,
            isLoading = uiState.isSavingDraft,
            onClick = onNextClick,
            testTag = "wizard_next_button"
        )
    }
}

// -------------------------------------------------------------------------
// STEP 1: REFERENCE IMAGE & RIGHTS CONFIRMATION
// -------------------------------------------------------------------------
@Composable
private fun Step1ReferenceImage(
    uiState: CreateWizardUiState,
    viewModel: CreateWizardViewModel
) {
    val colors = LocalCinematicColors.current

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            viewModel.setReferenceImageUri(uri?.toString())
        }
    )

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        CinematicBadge(
            text = "STEP 1 • SINGLE SOURCE OF TRUTH",
            style = BadgeStyle.GOLD
        )

        Text(
            text = "Upload Character Reference Photo",
            color = colors.textPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Upload a clear portrait of your subject. This image serves as the single source of truth for 100% facial geometry lock throughout the cinematic sequence.",
            color = colors.textSecondary,
            fontSize = 13.sp,
            lineHeight = 18.sp
        )

        // Photo Upload Box
        GlassmorphicCard(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                photoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }
        ) {
            if (uiState.referenceImageUri != null) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(160.dp)
                            .clip(VideoFrameShape)
                            .border(2.dp, GoldPrimary, VideoFrameShape)
                    ) {
                        AsyncImage(
                            model = uiState.referenceImageUri,
                            contentDescription = "Uploaded Reference Character",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Change Reference Photo",
                        color = GoldPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(colors.surfaceElevated, PillShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = CinematicIcons.ReferenceFace,
                            contentDescription = null,
                            tint = GoldPrimary,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Select Reference Portrait (Photo Picker)",
                        color = colors.textPrimary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Tap to choose JPEG / PNG from your device gallery",
                        color = colors.textSecondary,
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Rights Confirmation Mandatory Checkbox
        GlassmorphicCard(
            modifier = Modifier.fillMaxWidth(),
            borderGlow = uiState.rightsConfirmed
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Checkbox(
                    checked = uiState.rightsConfirmed,
                    onCheckedChange = { viewModel.setRightsConfirmed(it) },
                    colors = CheckboxDefaults.colors(
                        checkedColor = GoldPrimary,
                        uncheckedColor = colors.border,
                        checkmarkColor = ObsidianBlack
                    ),
                    modifier = Modifier.testTag("rights_confirmation_checkbox")
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Mandatory Rights & Consent Confirmation",
                        color = colors.textPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "I confirm that I possess full legal rights, ownership, or explicit consent to use this reference image for facial identity locking and cinematic generation in compliance with Google Play Developer policies.",
                        color = colors.textSecondary,
                        fontSize = 11.sp,
                        lineHeight = 16.sp
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------------------
// STEP 2: CHARACTER CONSISTENCY SETTINGS
// -------------------------------------------------------------------------
@Composable
private fun Step2CharacterConsistency(
    uiState: CreateWizardUiState,
    viewModel: CreateWizardViewModel
) {
    val colors = LocalCinematicColors.current

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        CinematicBadge(
            text = "STEP 2 • STRICT FACIAL FEATURE LOCK",
            style = BadgeStyle.GOLD
        )

        Text(
            text = "Facial Geometry Lock: 100% Identity",
            color = colors.textPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Priority Directive: The engine will prioritize facial identity preservation over all stylistic instructions. Identity, age, ethnicity, and facial geometry remain strictly locked.",
            color = colors.textSecondary,
            fontSize = 13.sp,
            lineHeight = 18.sp
        )

        GlassmorphicCard(modifier = Modifier.fillMaxWidth()) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Strictness Level",
                        color = colors.textPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    CinematicBadge(
                        text = "${(uiState.facialLockStrictness * 100).toInt()}% MAXIMUM LOCK",
                        style = BadgeStyle.CYAN
                    )
                }

                Slider(
                    value = uiState.facialLockStrictness,
                    onValueChange = { /* Locked at 100% per strict directive */ },
                    valueRange = 0.8f..1.0f,
                    colors = SliderDefaults.colors(
                        thumbColor = GoldPrimary,
                        activeTrackColor = GoldPrimary,
                        inactiveTrackColor = colors.surfaceElevated
                    )
                )

                Text(
                    text = "No lookalikes or similar faces allowed. The original face is preserved exactly across varying camera angles, lighting, and expressions.",
                    color = colors.textTertiary,
                    fontSize = 11.sp
                )
            }
        }

        SectionHeader(title = "Locked Anatomical Features")

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            uiState.lockedFeatures.forEach { feature ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colors.surfaceElevated, PillShape)
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = CinematicIcons.Check,
                        contentDescription = null,
                        tint = colors.accentGreen,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = feature,
                        color = colors.textPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------------------
// STEP 3: CINEMATIC PROMPT INPUT
// -------------------------------------------------------------------------
@Composable
private fun Step3PromptInput(
    uiState: CreateWizardUiState,
    viewModel: CreateWizardViewModel
) {
    val colors = LocalCinematicColors.current

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        CinematicBadge(
            text = "STEP 3 • CINEMATIC SCENE PROMPT",
            style = BadgeStyle.GOLD
        )

        Text(
            text = "Describe Your Cinematic Scene",
            color = colors.textPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Provide your natural language vision. The engine parser matrix will decompose this into camera, lens, lighting, motion, and environmental vectors.",
            color = colors.textSecondary,
            fontSize = 13.sp
        )

        OutlinedTextField(
            value = uiState.promptText,
            onValueChange = { viewModel.setPromptText(it) },
            placeholder = {
                Text(
                    text = "e.g., Hero steps forward under heavy rain into glowing neon square, turning toward the camera with resolute courage...",
                    color = colors.textTertiary,
                    fontSize = 14.sp
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .testTag("prompt_input_field"),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = GoldPrimary,
                unfocusedBorderColor = ObsidianBorder,
                focusedContainerColor = colors.surfaceElevated,
                unfocusedContainerColor = colors.surfaceCard
            ),
            shape = VideoFrameShape
        )

        SectionHeader(title = "Negative Prompt Presets (Quality Lock)")

        OutlinedTextField(
            value = uiState.negativePrompt,
            onValueChange = { viewModel.setNegativePrompt(it) },
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = colors.textSecondary,
                unfocusedTextColor = colors.textTertiary,
                focusedBorderColor = GoldDark,
                unfocusedBorderColor = ObsidianBorder,
                focusedContainerColor = colors.surfaceElevated,
                unfocusedContainerColor = colors.surfaceCard
            ),
            shape = VideoFrameShape
        )
    }
}

// -------------------------------------------------------------------------
// STEP 4: CHARACTER ACTION & MOTION
// -------------------------------------------------------------------------
@Composable
private fun Step4CharacterAction(
    uiState: CreateWizardUiState,
    viewModel: CreateWizardViewModel
) {
    val colors = LocalCinematicColors.current

    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        CinematicBadge(
            text = "STEP 4 • ACTION & MOTION PATH",
            style = BadgeStyle.GOLD
        )

        Text(
            text = "Motion, Expression & Environment",
            color = colors.textPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = uiState.characterAction,
            onValueChange = { viewModel.setCharacterAction(it) },
            label = { Text("Character Action & Path") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = GoldPrimary,
                unfocusedBorderColor = ObsidianBorder
            )
        )

        OutlinedTextField(
            value = uiState.facialExpression,
            onValueChange = { viewModel.setFacialExpression(it) },
            label = { Text("Facial Emotion & Expression") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = GoldPrimary,
                unfocusedBorderColor = ObsidianBorder
            )
        )

        OutlinedTextField(
            value = uiState.bodyGesture,
            onValueChange = { viewModel.setBodyGesture(it) },
            label = { Text("Hand & Body Gestures") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = GoldPrimary,
                unfocusedBorderColor = ObsidianBorder
            )
        )

        OutlinedTextField(
            value = uiState.environmentDetails,
            onValueChange = { viewModel.setEnvironmentDetails(it) },
            label = { Text("Environment & Atmospheric Depth") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = GoldPrimary,
                unfocusedBorderColor = ObsidianBorder
            )
        )
    }
}

// -------------------------------------------------------------------------
// STEP 5: CAMERA ANGLE & LENS
// -------------------------------------------------------------------------
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun Step5CameraLens(
    uiState: CreateWizardUiState,
    viewModel: CreateWizardViewModel
) {
    val colors = LocalCinematicColors.current

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        CinematicBadge(
            text = "STEP 5 • CINEMA CAMERA & GLASS",
            style = BadgeStyle.GOLD
        )

        Text(
            text = "Camera Angle & Framing",
            color = colors.textPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            CameraAngle.entries.forEach { angle ->
                val isSelected = uiState.cameraAngle == angle
                GlassmorphicCard(
                    modifier = Modifier.fillMaxWidth(),
                    borderGlow = isSelected,
                    onClick = { viewModel.setCameraAngle(angle) }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = angle.label,
                                color = if (isSelected) GoldPrimary else colors.textPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Text(
                                text = angle.cinematicPromptSnippet,
                                color = colors.textSecondary,
                                fontSize = 11.sp
                            )
                        }
                        if (isSelected) {
                            Icon(
                                imageVector = CinematicIcons.Check,
                                contentDescription = null,
                                tint = GoldPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }

        SectionHeader(title = "Cinema Lens Selection")

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            LensType.entries.forEach { lens ->
                val isSelected = uiState.lensType == lens
                GlassmorphicCard(
                    modifier = Modifier.fillMaxWidth(),
                    borderGlow = isSelected,
                    onClick = { viewModel.setLensType(lens) }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = lens.label,
                                color = if (isSelected) GoldPrimary else colors.textPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Text(
                                text = lens.focalLength,
                                color = colors.textSecondary,
                                fontSize = 11.sp
                            )
                        }
                        if (isSelected) {
                            Icon(
                                imageVector = CinematicIcons.Check,
                                contentDescription = null,
                                tint = GoldPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------------------
// STEP 6: LIGHTING & MOOD
// -------------------------------------------------------------------------
@Composable
private fun Step6LightingMood(
    uiState: CreateWizardUiState,
    viewModel: CreateWizardViewModel
) {
    val colors = LocalCinematicColors.current

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        CinematicBadge(
            text = "STEP 6 • ATMOSPHERIC LIGHTING & MOOD",
            style = BadgeStyle.GOLD
        )

        Text(
            text = "Director Lighting Setup",
            color = colors.textPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            LightingStyle.entries.forEach { style ->
                val isSelected = uiState.lightingStyle == style
                GlassmorphicCard(
                    modifier = Modifier.fillMaxWidth(),
                    borderGlow = isSelected,
                    onClick = { viewModel.setLightingStyle(style) }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = style.label,
                                color = if (isSelected) GoldPrimary else colors.textPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Text(
                                text = style.promptSnippet,
                                color = colors.textSecondary,
                                fontSize = 11.sp
                            )
                        }
                        if (isSelected) {
                            Icon(
                                imageVector = CinematicIcons.Check,
                                contentDescription = null,
                                tint = GoldPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }

        SectionHeader(title = "Cinematic Genre & Visual Style")

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            CinematicStyle.entries.forEach { genre ->
                val isSelected = uiState.cinematicStyle == genre
                GlassmorphicCard(
                    modifier = Modifier.fillMaxWidth(),
                    borderGlow = isSelected,
                    onClick = { viewModel.setCinematicStyle(genre) }
                ) {
                    Text(
                        text = genre.label,
                        color = if (isSelected) GoldPrimary else colors.textPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------------------
// STEP 7: DIALOGUE INPUT
// -------------------------------------------------------------------------
@Composable
private fun Step7DialogueInput(
    uiState: CreateWizardUiState,
    viewModel: CreateWizardViewModel
) {
    val colors = LocalCinematicColors.current

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        CinematicBadge(
            text = "STEP 7 • SCRIPT & DIALOGUE",
            style = BadgeStyle.GOLD
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Spoken Dialogue & Lip-Sync",
                    color = colors.textPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Synchronize character lips with speech",
                    color = colors.textSecondary,
                    fontSize = 12.sp
                )
            }
            Switch(
                checked = uiState.voiceEnabled,
                onCheckedChange = { viewModel.setVoiceEnabled(it) },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = GoldPrimary,
                    checkedTrackColor = GoldDark,
                    uncheckedTrackColor = colors.surfaceElevated
                )
            )
        }

        if (uiState.voiceEnabled) {
            OutlinedTextField(
                value = uiState.dialogueText,
                onValueChange = { viewModel.setDialogueText(it) },
                label = { Text("Spoken Script (English, Hindi, or Marathi)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .testTag("dialogue_input_field"),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = GoldPrimary,
                    unfocusedBorderColor = ObsidianBorder
                ),
                shape = VideoFrameShape
            )

            Text(
                text = "${uiState.dialogueText.trim().split("\\s+".toRegex()).size} words • Estimated speaking duration: ~${(uiState.dialogueText.length / 15).coerceAtLeast(2)}s",
                color = GoldPrimary,
                fontSize = 12.sp
            )
        } else {
            GlassmorphicCard(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Voice disabled. Generated video will render with atmospheric ambient soundtrack only.",
                    color = colors.textSecondary,
                    fontSize = 13.sp
                )
            }
        }
    }
}

// -------------------------------------------------------------------------
// STEP 8: VOICE SELECTION
// -------------------------------------------------------------------------
@Composable
private fun Step8VoiceSelection(
    uiState: CreateWizardUiState,
    viewModel: CreateWizardViewModel
) {
    val colors = LocalCinematicColors.current

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        CinematicBadge(
            text = "STEP 8 • VOICE PROFILE & ACTOR",
            style = BadgeStyle.GOLD
        )

        Text(
            text = "Language & Voice Profile",
            color = colors.textPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        SectionHeader(title = "Primary Language")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SupportedLanguage.entries.forEach { lang ->
                val isSelected = uiState.selectedLanguage == lang
                GlassmorphicCard(
                    modifier = Modifier.weight(1f),
                    borderGlow = isSelected,
                    onClick = { viewModel.setSelectedLanguage(lang) }
                ) {
                    Text(
                        text = lang.displayName,
                        color = if (isSelected) GoldPrimary else colors.textPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }

        SectionHeader(title = "Voice Actors")

        val voices = listOf(
            "voice_cinematic_male_01" to "Cinematic Deep Male (Resonant)",
            "voice_dramatic_female_01" to "Dramatic Emotive Female",
            "voice_narrator_02" to "Pro Blockbuster Narrator",
            "voice_calm_actor_03" to "Intimate Natural Actor"
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            voices.forEach { (id, name) ->
                val isSelected = uiState.selectedVoiceId == id
                GlassmorphicCard(
                    modifier = Modifier.fillMaxWidth(),
                    borderGlow = isSelected,
                    onClick = { viewModel.setSelectedVoiceId(id) }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = name,
                            color = if (isSelected) GoldPrimary else colors.textPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                        if (isSelected) {
                            Icon(
                                imageVector = CinematicIcons.Check,
                                contentDescription = null,
                                tint = GoldPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------------------
// STEP 9: DURATION & ASPECT RATIO
// -------------------------------------------------------------------------
@Composable
private fun Step9DurationAspectRatio(
    uiState: CreateWizardUiState,
    viewModel: CreateWizardViewModel
) {
    val colors = LocalCinematicColors.current

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        CinematicBadge(
            text = "STEP 9 • FORMAT & DURATION",
            style = BadgeStyle.GOLD
        )

        Text(
            text = "Duration (Max 30s Free Tier)",
            color = colors.textPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            VideoDuration.entries.filter { it.isFreeTier }.forEach { dur ->
                val isSelected = uiState.duration == dur
                GlassmorphicCard(
                    modifier = Modifier.fillMaxWidth(),
                    borderGlow = isSelected,
                    onClick = { viewModel.setDuration(dur) }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${dur.seconds} Seconds",
                            color = if (isSelected) GoldPrimary else colors.textPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        CinematicBadge(
                            text = if (dur.seconds == 30) "MAX FREE TIER" else "FREE",
                            style = if (dur.seconds == 30) BadgeStyle.GOLD else BadgeStyle.DEFAULT
                        )
                    }
                }
            }
        }

        SectionHeader(title = "Aspect Ratio")

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            AspectRatio.entries.forEach { ratio ->
                val isSelected = uiState.aspectRatio == ratio
                GlassmorphicCard(
                    modifier = Modifier.fillMaxWidth(),
                    borderGlow = isSelected,
                    onClick = { viewModel.setAspectRatio(ratio) }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = ratio.displayLabel,
                            color = if (isSelected) GoldPrimary else colors.textPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        if (isSelected) {
                            Icon(
                                imageVector = CinematicIcons.Check,
                                contentDescription = null,
                                tint = GoldPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------------------
// STEP 10: REVIEW & GENERATE
// -------------------------------------------------------------------------
@Composable
private fun Step10ReviewGenerate(
    uiState: CreateWizardUiState,
    viewModel: CreateWizardViewModel,
    onSuccessGenerated: () -> Unit
) {
    val colors = LocalCinematicColors.current

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        CinematicBadge(
            text = "STEP 10 • FINAL REVIEW & DIRECTIVE",
            style = BadgeStyle.GOLD
        )

        Text(
            text = "Director Review & Generation",
            color = colors.textPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        // Summary Badges
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CinematicBadge(text = "100% FACE LOCK", style = BadgeStyle.GOLD)
            CinematicBadge(text = "${uiState.duration.seconds}s CINEMA", style = BadgeStyle.CYAN)
            CinematicBadge(text = uiState.aspectRatio.displayLabel.split(" ")[0], style = BadgeStyle.DEFAULT)
        }

        // Full Parser Matrix Breakdown
        GlassmorphicCard(
            modifier = Modifier.fillMaxWidth(),
            borderGlow = true
        ) {
            Column {
                Text(
                    text = "CINEMATIC PARSER MATRIX SUMMARY",
                    color = GoldPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = uiState.buildStructuredCinematicInstructions(),
                    color = colors.textSecondary,
                    fontSize = 11.sp,
                    lineHeight = 16.sp
                )
            }
        }

        if (uiState.isDraftSaved) {
            GlassmorphicCard(
                modifier = Modifier.fillMaxWidth(),
                borderGlow = true
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = CinematicIcons.Check,
                        contentDescription = null,
                        tint = colors.accentGreen,
                        modifier = Modifier.size(36.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Project Draft Queued Successfully!",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Your cinematic shot instructions and reference image are locked in state.",
                        color = colors.textSecondary,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
