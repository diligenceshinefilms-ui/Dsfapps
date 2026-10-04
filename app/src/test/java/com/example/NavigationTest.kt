package com.example

import com.example.navigation.MainDestination
import com.example.navigation.Screen
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class NavigationTest {

    @Test
    fun `verify screen routes defined`() {
        assertEquals("splash", Screen.Splash.route)
        assertEquals("onboarding", Screen.Onboarding.route)
        assertEquals("main_shell", Screen.MainShell.route)
        assertEquals("create_flow", Screen.CreateFlow.route)
        assertEquals("editor", Screen.Editor.route)
        assertEquals("settings", Screen.Settings.route)
        assertEquals("report", Screen.Report.route)
    }

    @Test
    fun `verify bottom navigation destinations count and attributes`() {
        val destinations = MainDestination.entries
        assertEquals(5, destinations.size)

        val home = MainDestination.HOME
        assertEquals("tab_home", home.route)
        assertEquals("Home", home.title)
        assertNotNull(home.selectedIcon)
        assertNotNull(home.unselectedIcon)

        val create = MainDestination.CREATE
        assertEquals("tab_create", create.route)
        assertEquals("Create", create.title)

        val projects = MainDestination.PROJECTS
        assertEquals("tab_projects", projects.route)
        assertEquals("Projects", projects.title)

        val templates = MainDestination.TEMPLATES
        assertEquals("tab_templates", templates.route)
        assertEquals("Templates", templates.title)

        val profile = MainDestination.PROFILE
        assertEquals("tab_profile", profile.route)
        assertEquals("Profile", profile.title)
    }
}
