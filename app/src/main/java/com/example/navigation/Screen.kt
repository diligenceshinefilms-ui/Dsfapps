package com.example.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.core.designsystem.CinematicIcons

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Onboarding : Screen("onboarding")
    data object MainShell : Screen("main_shell")
    data object CreateFlow : Screen("create_flow")
    data object Editor : Screen("editor")
    data object Settings : Screen("settings")
    data object Report : Screen("report")
}

enum class MainDestination(
    val route: String,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
) {
    HOME(
        route = "tab_home",
        title = "Home",
        selectedIcon = CinematicIcons.HomeFilled,
        unselectedIcon = CinematicIcons.HomeOutlined,
        testTag = "nav_item_home"
    ),
    CREATE(
        route = "tab_create",
        title = "Create",
        selectedIcon = CinematicIcons.CreateFilled,
        unselectedIcon = CinematicIcons.CreateOutlined,
        testTag = "nav_item_create"
    ),
    PROJECTS(
        route = "tab_projects",
        title = "Projects",
        selectedIcon = CinematicIcons.ProjectsFilled,
        unselectedIcon = CinematicIcons.ProjectsOutlined,
        testTag = "nav_item_projects"
    ),
    TEMPLATES(
        route = "tab_templates",
        title = "Templates",
        selectedIcon = CinematicIcons.TemplatesFilled,
        unselectedIcon = CinematicIcons.TemplatesOutlined,
        testTag = "nav_item_templates"
    ),
    PROFILE(
        route = "tab_profile",
        title = "Profile",
        selectedIcon = CinematicIcons.ProfileFilled,
        unselectedIcon = CinematicIcons.ProfileOutlined,
        testTag = "nav_item_profile"
    )
}
