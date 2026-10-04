package com.example.domain.project

import com.example.domain.video.AspectRatio
import com.example.domain.video.GenerationRequest

enum class ProjectStatus {
    DRAFT,
    GENERATING,
    READY_FOR_EDIT,
    COMPLETED
}

data class CinematicProject(
    val id: String,
    val title: String,
    val promptSummary: String,
    val request: GenerationRequest,
    val status: ProjectStatus = ProjectStatus.DRAFT,
    val durationSeconds: Int = 10,
    val aspectRatio: AspectRatio = AspectRatio.WIDESCREEN_16_9,
    val videoUrl: String? = null,
    val thumbnailUrl: String? = null,
    val isFavorite: Boolean = false,
    val updatedAtMillis: Long = System.currentTimeMillis()
)
