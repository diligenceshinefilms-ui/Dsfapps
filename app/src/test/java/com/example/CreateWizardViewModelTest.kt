package com.example

import com.example.domain.video.AspectRatio
import com.example.domain.video.CameraAngle
import com.example.domain.video.LensType
import com.example.domain.video.LightingStyle
import com.example.domain.video.VideoDuration
import com.example.domain.voice.SupportedLanguage
import com.example.feature.create.CreateWizardViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class CreateWizardViewModelTest {

    @Test
    fun `verify step 1 validation requires reference image and rights checkbox`() {
        val viewModel = CreateWizardViewModel()
        val initial = viewModel.uiState.value

        assertEquals(1, initial.currentStep)
        assertFalse("Step 1 must be invalid without image and rights", initial.isCurrentStepValid())

        // Try next step - should fail and set error
        viewModel.nextStep()
        assertEquals(1, viewModel.uiState.value.currentStep)
        assertNotNull(viewModel.uiState.value.errorMessage)

        // Set image only
        viewModel.setReferenceImageUri("content://media/external/images/media/42")
        assertFalse("Step 1 must still be invalid without rights confirmed", viewModel.uiState.value.isCurrentStepValid())

        // Confirm rights
        viewModel.setRightsConfirmed(true)
        assertTrue("Step 1 must be valid with image and rights", viewModel.uiState.value.isCurrentStepValid())

        // Now nextStep succeeds
        viewModel.nextStep()
        assertEquals(2, viewModel.uiState.value.currentStep)
    }

    @Test
    fun `verify prompt validation enforces minimum length`() {
        val viewModel = CreateWizardViewModel()
        viewModel.setStep(3)

        viewModel.setPromptText("hi")
        assertFalse(viewModel.uiState.value.isCurrentStepValid())

        viewModel.setPromptText("Hero enters atmospheric neon corridor")
        assertTrue(viewModel.uiState.value.isCurrentStepValid())
    }

    @Test
    fun `verify dialogue validation when voice is enabled`() {
        val viewModel = CreateWizardViewModel()
        viewModel.setStep(7)

        viewModel.setVoiceEnabled(true)
        viewModel.setDialogueText("")
        assertFalse(viewModel.uiState.value.isCurrentStepValid())

        viewModel.setDialogueText("We will triumph.")
        assertTrue(viewModel.uiState.value.isCurrentStepValid())

        // Disabling voice makes step valid even if dialogue is blank
        viewModel.setDialogueText("")
        viewModel.setVoiceEnabled(false)
        assertTrue(viewModel.uiState.value.isCurrentStepValid())
    }

    @Test
    fun `verify parser matrix structured instructions output`() {
        val viewModel = CreateWizardViewModel()
        viewModel.setReferenceImageUri("content://test.jpg")
        viewModel.setRightsConfirmed(true)
        viewModel.setPromptText("Hero walks toward camera in heavy rain")
        viewModel.setCharacterAction("Walks forward decisively")
        viewModel.setCameraAngle(CameraAngle.DYNAMIC_TRACKING)
        viewModel.setLensType(LensType.ANAMORPHIC_35MM)
        viewModel.setLightingStyle(LightingStyle.GOLDEN_HOUR)
        viewModel.setDuration(VideoDuration.SEC_10)
        viewModel.setAspectRatio(AspectRatio.WIDESCREEN_16_9)
        viewModel.setSelectedLanguage(SupportedLanguage.ENGLISH)
        viewModel.setDialogueText("Cinema is born.")

        val matrix = viewModel.uiState.value.buildStructuredCinematicInstructions()

        assertTrue(matrix.contains("CINEMATIC VIDEO GENERATION PARSER MATRIX"))
        assertTrue(matrix.contains("Character: Reference image facial geometry locked"))
        assertTrue(matrix.contains("Action: Walks forward decisively"))
        assertTrue(matrix.contains("Camera: Dynamic Tracking"))
        assertTrue(matrix.contains("Lens: 35mm Anamorphic"))
        assertTrue(matrix.contains("Lighting: Golden Hour"))
        assertTrue(matrix.contains("Duration: 10s"))
        assertTrue(matrix.contains("AspectRatio: 16:9 Cinematic"))
        assertTrue(matrix.contains("Dialogue: \"Cinema is born.\" [Language: English]"))
    }

    @Test
    fun `verify save draft flow creates cinematic project in state`() {
        val viewModel = CreateWizardViewModel()
        viewModel.setReferenceImageUri("content://portrait.jpg")
        viewModel.setRightsConfirmed(true)
        viewModel.setPromptText("Epic sci-fi hero arrival scene")
        viewModel.setDialogueText("Stand ready.")

        var savedProjectTitle: String? = null
        viewModel.saveDraftAndQueue { project ->
            savedProjectTitle = project.title
        }

        assertTrue(viewModel.uiState.value.isDraftSaved)
        assertNotNull(viewModel.uiState.value.savedProject)
        assertTrue(savedProjectTitle?.startsWith("Epic sci-fi") == true)
    }
}
