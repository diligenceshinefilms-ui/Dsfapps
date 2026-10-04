package com.example.data.video

import com.example.core.common.Resource
import com.example.domain.video.GenerationJob
import com.example.domain.video.GenerationRequest
import kotlinx.coroutines.flow.Flow

interface VideoRepository {
    suspend fun submitGeneration(request: GenerationRequest): Resource<GenerationJob>
    fun observeGenerationJob(jobId: String): Flow<GenerationJob?>
    suspend fun cancelGeneration(jobId: String): Resource<Unit>
}
