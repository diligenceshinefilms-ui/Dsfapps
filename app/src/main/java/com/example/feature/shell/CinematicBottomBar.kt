package com.example.feature.shell

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.designsystem.GoldGradient
import com.example.core.designsystem.GoldPrimary
import com.example.core.designsystem.LocalCinematicColors
import com.example.core.designsystem.ObsidianBlack
import com.example.core.designsystem.PillShape
import com.example.navigation.MainDestination

@Composable
fun CinematicBottomBar(
    currentDestination: MainDestination,
    onDestinationSelected: (MainDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalCinematicColors.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(colors.surface.copy(alpha = 0.96f))
            .border(
                width = 0.5.dp,
                color = colors.border.copy(alpha = 0.8f)
            )
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MainDestination.entries.forEach { destination ->
                val isSelected = currentDestination == destination

                if (destination == MainDestination.CREATE) {
                    // Center Floating Highlight for the CREATE action
                    CinematicCreateCenterButton(
                        isSelected = isSelected,
                        onClick = { onDestinationSelected(destination) }
                    )
                } else {
                    CinematicNavItem(
                        destination = destination,
                        isSelected = isSelected,
                        onClick = { onDestinationSelected(destination) }
                    )
                }
            }
        }
    }
}

@Composable
private fun CinematicNavItem(
    destination: MainDestination,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val colors = LocalCinematicColors.current
    val interactionSource = remember { MutableInteractionSource() }

    val iconColor by animateColorAsState(
        targetValue = if (isSelected) GoldPrimary else colors.textTertiary,
        animationSpec = tween(durationMillis = 200),
        label = "nav_icon_color"
    )

    val textColor by animateColorAsState(
        targetValue = if (isSelected) GoldPrimary else colors.textTertiary,
        animationSpec = tween(durationMillis = 200),
        label = "nav_text_color"
    )

    Column(
        modifier = Modifier
            .minimumInteractiveComponentSize()
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(bounded = false, radius = 24.dp, color = GoldPrimary),
                onClick = onClick
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .testTag(destination.testTag),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(PillShape)
                .background(
                    if (isSelected) GoldPrimary.copy(alpha = 0.15f) else Color.Transparent
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isSelected) destination.selectedIcon else destination.unselectedIcon,
                contentDescription = destination.title,
                tint = iconColor,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = destination.title,
            color = textColor,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
        )
    }
}

@Composable
private fun CinematicCreateCenterButton(
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = Modifier
            .minimumInteractiveComponentSize()
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(bounded = false, radius = 26.dp, color = GoldPrimary),
                onClick = onClick
            )
            .padding(horizontal = 6.dp)
            .testTag(MainDestination.CREATE.testTag),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .offset(y = (-4).dp)
                .clip(PillShape)
                .background(GoldGradient)
                .border(2.dp, ObsidianBlack, PillShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = MainDestination.CREATE.selectedIcon,
                contentDescription = "Create Video",
                tint = ObsidianBlack,
                modifier = Modifier.size(24.dp)
            )
        }

        Text(
            text = "Create",
            color = GoldPrimary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.offset(y = (-2).dp)
        )
    }
}
