package com.example

import com.example.domain.subscription.PlanTier
import com.example.feature.home.HomeViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class HomeViewModelTest {

    @Test
    fun `verify initial home state has free tier limits and templates`() {
        val viewModel = HomeViewModel()
        val state = viewModel.uiState.value

        assertEquals(PlanTier.FREE, state.currentPlanTier)
        assertEquals(30, state.maxDurationSeconds)
        assertEquals(5, state.totalMonthlyGenerationsAllowed)
        assertEquals(2, state.usedGenerationsThisMonth)

        // Templates present
        assertTrue(state.templates.isNotEmpty())
        val hollywoodTemplate = state.templates.find { it.id == "template_hollywood_blockbuster" }
        assertNotNull(hollywoodTemplate)
        assertEquals("Hollywood Sci-Fi Blockbuster", hollywoodTemplate?.title)

        // AI Tools present
        assertTrue(state.aiTools.isNotEmpty())
        val facialLock = state.aiTools.find { it.id == "tool_facial_lock" }
        assertNotNull(facialLock)
        assertEquals("Facial Geometry Lock", facialLock?.title)

        // Active Draft
        assertNotNull(state.activeDraft)
        assertEquals("Cyber City Awakening", state.activeDraft?.title)
    }
}
