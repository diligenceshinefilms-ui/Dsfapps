package com.example.feature.shell

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.core.designsystem.ObsidianBlack
import com.example.feature.create.CreateWizardScreen
import com.example.feature.home.HomeScreen
import com.example.feature.profile.ProfileFoundationView
import com.example.feature.projects.ProjectsFoundationView
import com.example.feature.templates.TemplatesFoundationView
import com.example.navigation.MainDestination

@Composable
fun MainShellScreen(
    initialDestination: MainDestination = MainDestination.HOME,
    modifier: Modifier = Modifier
) {
    var currentDestination by rememberSaveable { mutableStateOf(initialDestination) }

    // Android back button handling: return to HOME tab if on a secondary tab
    BackHandler(enabled = currentDestination != MainDestination.HOME) {
        currentDestination = MainDestination.HOME
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        bottomBar = {
            CinematicBottomBar(
                currentDestination = currentDestination,
                onDestinationSelected = { destination ->
                    currentDestination = destination
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Crossfade(
                targetState = currentDestination,
                animationSpec = tween(durationMillis = 250),
                label = "shell_tab_crossfade"
            ) { destination ->
                when (destination) {
                    MainDestination.HOME -> {
                        HomeScreen(
                            onCreateClick = {
                                currentDestination = MainDestination.CREATE
                            }
                        )
                    }
                    MainDestination.CREATE -> {
                        CreateWizardScreen(
                            onCloseClick = {
                                currentDestination = MainDestination.HOME
                            },
                            onSuccessGenerated = {
                                currentDestination = MainDestination.PROJECTS
                            }
                        )
                    }
                    MainDestination.PROJECTS -> {
                        ProjectsFoundationView()
                    }
                    MainDestination.TEMPLATES -> {
                        TemplatesFoundationView()
                    }
                    MainDestination.PROFILE -> {
                        ProfileFoundationView()
                    }
                }
            }
        }
    }
}
