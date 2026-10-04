package com.example

import com.example.core.common.AppConstants
import com.example.core.designsystem.GoldPrimary
import com.example.core.designsystem.ObsidianBlack
import com.example.core.designsystem.Spacing
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class DesignSystemTest {

    @Test
    fun `verify brand constants`() {
        assertEquals("Diligence Shine Films", AppConstants.APP_NAME)
        assertEquals("com.dsf.apps", AppConstants.PACKAGE_NAME)
        assertEquals("Turn Your Vision Into Cinema.", AppConstants.TAGLINE)
        assertEquals(30, AppConstants.MAX_FREE_DURATION_SECONDS)
    }

    @Test
    fun `verify design system tokens`() {
        assertNotNull(GoldPrimary)
        assertNotNull(ObsidianBlack)
        val spacing = Spacing()
        assertTrue(spacing.touchTargetMin.value >= 48f)
        assertEquals(16f, spacing.screenPadding.value)
    }
}
