package com.example.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.core.designsystem.CinematicIcons
import com.example.core.designsystem.GoldDark
import com.example.core.designsystem.GoldPrimary
import com.example.core.designsystem.LocalCinematicColors
import com.example.core.designsystem.ObsidianBlack
import com.example.core.designsystem.ObsidianBorder
import com.example.core.designsystem.PillShape
import com.example.core.designsystem.TagShape
import com.example.core.designsystem.VideoFrameShape
import com.example.core.ui.BadgeStyle
import com.example.core.ui.CinematicBadge
import com.example.core.ui.CinematicButton
import com.example.core.ui.CinematicSecondaryButton
import com.example.core.ui.CinematicTopBar
import com.example.core.ui.GlassmorphicCard
import com.example.core.ui.SectionHeader
import com.example.domain.project.CinematicProject
import com.example.domain.project.ProjectStatus

@Composable
fun HomeScreen(
    onCreateClick: () -> Unit,
    onProjectClick: (CinematicProject) -> Unit = {},
    onTemplateClick: (CinemaTemplate) -> Unit = {},
    onUpgradeClick: () -> Unit = {},
    viewModel: HomeViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val colors = LocalCinematicColors.current
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ObsidianBlack)
            .testTag("home_screen")
    ) {
        // App Header
        CinematicTopBar(
            title = "DILIGENCE SHINE FILMS",
            subtitle = "Turn Your Vision Into Cinema."
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // 1. Hero Creation Banner
            item(key = "hero_banner") {
                HeroCreationBanner(
                    onCreateClick = onCreateClick
                )
            }

            // 2. Subscription Status & Monthly Usage
            item(key = "subscription_usage") {
                SubscriptionStatusSection(
                    uiState = uiState,
                    onUpgradeClick = onUpgradeClick
                )
            }

            // 3. Continue Editing Active Draft (if present)
            if (uiState.activeDraft != null) {
                item(key = "continue_editing") {
                    ContinueEditingSection(
                        draft = uiState.activeDraft!!,
                        onResumeClick = { onProjectClick(uiState.activeDraft!!) }
                    )
                }
            }

            // 4. Recent Projects Carousel
            item(key = "recent_projects") {
                RecentProjectsSection(
                    projects = uiState.recentProjects,
                    onProjectClick = onProjectClick
                )
            }

            // 5. Director Templates
            item(key = "curated_templates") {
                TemplatesSection(
                    templates = uiState.templates,
                    onTemplateClick = onTemplateClick
                )
            }

            // 6. AI Cinematic Tools Grid
            item(key = "ai_tools") {
                AiToolsSection(
                    tools = uiState.aiTools
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun HeroCreationBanner(
    onCreateClick: () -> Unit
) {
    val colors = LocalCinematicColors.current

    GlassmorphicCard(
        borderGlow = true,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CinematicBadge(
                    text = "AI CINEMATIC ENGINE",
                    style = BadgeStyle.GOLD
                )
                CinematicBadge(
                    text = "FREE 30s TIER",
                    style = BadgeStyle.CYAN
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Turn Your Vision Into Cinema.",
                color = colors.textPrimary,
                fontSize = 22.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Upload a single reference photo. Lock 100% facial features. Direct anamorphic lenses, dynamic tracking, and emotive multi-lingual dialogue.",
                color = colors.textSecondary,
                fontSize = 13.sp,
                lineHeight = 19.sp
            )

            Spacer(modifier = Modifier.height(18.dp))

            CinematicButton(
                text = "CREATE CINEMATIC VIDEO",
                icon = CinematicIcons.CreateFilled,
                onClick = onCreateClick,
                modifier = Modifier.fillMaxWidth(),
                testTag = "home_create_video_button"
            )
        }
    }
}

@Composable
private fun SubscriptionStatusSection(
    uiState: HomeUiState,
    onUpgradeClick: () -> Unit
) {
    val colors = LocalCinematicColors.current
    val progress = (uiState.usedGenerationsThisMonth.toFloat() / uiState.totalMonthlyGenerationsAllowed.toFloat())
        .coerceIn(0f, 1f)

    // PLACEHOLDER: Billing and remote entitlement sync will connect in Phase 20-21
    GlassmorphicCard(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = 14.dp
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "STATUS: ",
                            color = colors.textSecondary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = uiState.currentPlanTier.title.uppercase(),
                            color = GoldPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                    Text(
                        text = "Max ${uiState.maxDurationSeconds}s generation length",
                        color = colors.textTertiary,
                        fontSize = 11.sp
                    )
                }

                CinematicSecondaryButton(
                    text = "UPGRADE",
                    icon = CinematicIcons.Star,
                    onClick = onUpgradeClick,
                    testTag = "home_upgrade_button"
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Usage progress bar
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(PillShape),
                color = GoldPrimary,
                trackColor = colors.surfaceElevated
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${uiState.usedGenerationsThisMonth} of ${uiState.totalMonthlyGenerationsAllowed} Monthly Generations Used",
                    color = colors.textSecondary,
                    fontSize = 11.sp
                )
                Text(
                    text = "${uiState.totalMonthlyGenerationsAllowed - uiState.usedGenerationsThisMonth} Remaining",
                    color = GoldPrimary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun ContinueEditingSection(
    draft: CinematicProject,
    onResumeClick: () -> Unit
) {
    val colors = LocalCinematicColors.current

    Column {
        SectionHeader(
            title = "Continue Editing",
            actionText = "Resume",
            onActionClick = onResumeClick
        )

        GlassmorphicCard(
            modifier = Modifier.fillMaxWidth(),
            onClick = onResumeClick
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Draft Frame Thumbnail Placeholder
                Box(
                    modifier = Modifier
                        .size(68.dp)
                        .clip(VideoFrameShape)
                        .background(colors.surfaceElevated)
                        .border(1.dp, GoldPrimary.copy(alpha = 0.5f), VideoFrameShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = CinematicIcons.Cut,
                        contentDescription = null,
                        tint = GoldPrimary,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CinematicBadge(
                            text = draft.aspectRatio.displayLabel.split(" ")[0],
                            style = BadgeStyle.CYAN
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        CinematicBadge(
                            text = "${draft.durationSeconds}s",
                            style = BadgeStyle.DEFAULT
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = draft.title,
                        color = colors.textPrimary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = draft.promptSummary,
                        color = colors.textSecondary,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
private fun RecentProjectsSection(
    projects: List<CinematicProject>,
    onProjectClick: (CinematicProject) -> Unit
) {
    val colors = LocalCinematicColors.current

    Column {
        SectionHeader(
            title = "Recent Projects",
            actionText = if (projects.isNotEmpty()) "See All" else null
        )

        if (projects.isEmpty()) {
            GlassmorphicCard(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "No projects yet. Tap Create to direct your first cinematic shot.",
                    color = colors.textTertiary,
                    fontSize = 13.sp
                )
            }
        } else {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(projects) { project ->
                    ProjectCardItem(
                        project = project,
                        onClick = { onProjectClick(project) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ProjectCardItem(
    project: CinematicProject,
    onClick: () -> Unit
) {
    val colors = LocalCinematicColors.current

    GlassmorphicCard(
        modifier = Modifier
            .width(168.dp)
            .clickable(onClick = onClick),
        contentPadding = 10.dp
    ) {
        Column {
            // Video Thumbnail Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
                    .clip(VideoFrameShape)
                    .background(colors.surfaceElevated)
                    .border(1.dp, colors.border, VideoFrameShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = CinematicIcons.Play,
                    contentDescription = "Play Project",
                    tint = GoldPrimary,
                    modifier = Modifier.size(32.dp)
                )

                // Aspect & Duration overlay
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(4.dp)
                ) {
                    CinematicBadge(
                        text = "${project.durationSeconds}s",
                        style = BadgeStyle.DEFAULT
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = project.title,
                color = colors.textPrimary,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = if (project.status == ProjectStatus.COMPLETED) "Ready" else "Draft",
                color = if (project.status == ProjectStatus.COMPLETED) colors.accentGreen else GoldPrimary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun TemplatesSection(
    templates: List<CinemaTemplate>,
    onTemplateClick: (CinemaTemplate) -> Unit
) {
    val colors = LocalCinematicColors.current

    Column {
        SectionHeader(
            title = "Cinematic Templates",
            actionText = "Explore"
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(templates) { template ->
                TemplateCardItem(
                    template = template,
                    onClick = { onTemplateClick(template) }
                )
            }
        }
    }
}

@Composable
private fun TemplateCardItem(
    template: CinemaTemplate,
    onClick: () -> Unit
) {
    val colors = LocalCinematicColors.current

    GlassmorphicCard(
        modifier = Modifier
            .width(220.dp)
            .clickable(onClick = onClick),
        contentPadding = 14.dp
    ) {
        Column {
            CinematicBadge(
                text = template.style.label.uppercase(),
                style = BadgeStyle.GOLD
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = template.title,
                color = colors.textPrimary,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = template.description,
                color = colors.textSecondary,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Apply Preset →",
                color = GoldPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun AiToolsSection(
    tools: List<AiToolItem>
) {
    val colors = LocalCinematicColors.current

    Column {
        SectionHeader(
            title = "AI Tools & Capabilities"
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            tools.chunked(2).forEach { rowTools ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    rowTools.forEach { tool ->
                        GlassmorphicCard(
                            modifier = Modifier.weight(1f),
                            contentPadding = 12.dp
                        ) {
                            Column {
                                CinematicBadge(
                                    text = tool.badge,
                                    style = BadgeStyle.CYAN
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = tool.title,
                                    color = colors.textPrimary,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = tool.subtitle,
                                    color = colors.textSecondary,
                                    fontSize = 11.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
