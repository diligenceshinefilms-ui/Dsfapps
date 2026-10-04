package com.example.feature.onboarding

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.designsystem.CinematicIcons
import com.example.core.designsystem.GoldPrimary
import com.example.core.designsystem.LocalCinematicColors
import com.example.core.designsystem.ObsidianBlack
import com.example.core.designsystem.ObsidianBorder
import com.example.core.designsystem.PillShape
import com.example.core.ui.BadgeStyle
import com.example.core.ui.CinematicBadge
import com.example.core.ui.CinematicButton
import com.example.core.ui.GlassmorphicCard
import kotlinx.coroutines.launch

data class OnboardingPageData(
    val badge: String,
    val title: String,
    val subtitle: String,
    val description: String,
    val icon: ImageVector
)

private val OnboardingPages = listOf(
    OnboardingPageData(
        badge = "CHARACTER IDENTITY LOCK",
        title = "Strict Facial Consistency",
        subtitle = "100% Identity Preservation",
        description = "Upload a single reference photo. Our facial geometry lock preserves forehead, eyes, nose, lips, jawline, and skin tone across all generated shots.",
        icon = CinematicIcons.ReferenceFace
    ),
    OnboardingPageData(
        badge = "CINEMATIC CAMERA MATRIX",
        title = "Direct Every Shot",
        subtitle = "Anamorphic Lenses & Dynamic Angles",
        description = "Shape your story with 35mm Anamorphic glass, 50mm primes, low-angle hero framing, golden hour rims, and atmospheric depth.",
        icon = CinematicIcons.Camera
    ),
    OnboardingPageData(
        badge = "LIP-SYNC & VOICES",
        title = "Emotive Multi-Lingual Speech",
        subtitle = "Hindi • Marathi • English",
        description = "Empower characters with cinematic voices and synchronized lip movements calibrated for high-impact dramatic performance.",
        icon = CinematicIcons.Mic
    )
)

@Composable
fun OnboardingScreen(
    onFinishOnboarding: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalCinematicColors.current
    val pagerState = rememberPagerState(pageCount = { OnboardingPages.size })
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack)
            .windowInsetsPadding(WindowInsets.statusBars)
            .windowInsetsPadding(WindowInsets.navigationBars)
            .testTag("onboarding_screen")
    ) {
        // Top Bar with Skip CTA
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "DILIGENCE SHINE FILMS",
                color = colors.textPrimary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            if (pagerState.currentPage < OnboardingPages.size - 1) {
                Text(
                    text = "SKIP",
                    color = GoldPrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .clickable { onFinishOnboarding() }
                        .padding(8.dp)
                        .testTag("onboarding_skip_button")
                )
            }
        }

        // Pager Content
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) { page ->
            val pageData = OnboardingPages[page]
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Feature Icon Badge
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .background(colors.surfaceElevated, PillShape)
                        .border(2.dp, GoldPrimary.copy(alpha = 0.6f), PillShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = pageData.icon,
                        contentDescription = null,
                        tint = GoldPrimary,
                        modifier = Modifier.size(56.dp)
                    )
                }

                Spacer(modifier = Modifier.height(28.dp))

                CinematicBadge(
                    text = pageData.badge,
                    style = BadgeStyle.GOLD
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = pageData.title,
                    color = colors.textPrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = pageData.subtitle,
                    color = GoldPrimary,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                GlassmorphicCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = pageData.description,
                        color = colors.textSecondary,
                        fontSize = 14.sp,
                        lineHeight = 22.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        // Bottom Controls: Page Indicators & Action CTA
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Page Indicator Dots
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                repeat(OnboardingPages.size) { index ->
                    val isSelected = pagerState.currentPage == index
                    val width by animateDpAsState(
                        targetValue = if (isSelected) 28.dp else 8.dp,
                        animationSpec = tween(durationMillis = 300),
                        label = "indicator_width"
                    )
                    val color by animateColorAsState(
                        targetValue = if (isSelected) GoldPrimary else ObsidianBorder,
                        animationSpec = tween(durationMillis = 300),
                        label = "indicator_color"
                    )

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .height(8.dp)
                            .width(width)
                            .clip(PillShape)
                            .background(color)
                    )
                }
            }

            // Primary Navigation CTA
            val isLastPage = pagerState.currentPage == OnboardingPages.size - 1
            CinematicButton(
                text = if (isLastPage) "START CREATING CINEMA" else "CONTINUE",
                icon = if (isLastPage) CinematicIcons.AI else CinematicIcons.Forward,
                onClick = {
                    if (isLastPage) {
                        onFinishOnboarding()
                    } else {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                testTag = "onboarding_primary_button"
            )
        }
    }
}
