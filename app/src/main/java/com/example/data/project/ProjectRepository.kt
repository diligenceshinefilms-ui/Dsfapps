package com.example.data.project

import com.example.core.common.Resource
import com.example.domain.project.CinematicProject
import kotlinx.coroutines.flow.Flow

interface ProjectRepository {
    fun getRecentProjects(): Flow<List<CinematicProject>>
    suspend fun getProjectById(projectId: String): Resource<CinematicProject>
    suspend fun saveProject(project: CinematicProject): Resource<Unit>
    suspend fun deleteProject(projectId: String): Resource<Unit>
    suspend fun toggleFavorite(projectId: String): Resource<Unit>
}
