package com.example.data.auth

import com.example.core.common.Resource
import kotlinx.coroutines.flow.Flow

data class UserProfile(
    val uid: String,
    val displayName: String?,
    val email: String?,
    val isAnonymous: Boolean = false
)

interface AuthRepository {
    fun observeCurrentUser(): Flow<UserProfile?>
    suspend fun signInWithGoogle(idToken: String): Resource<UserProfile>
    suspend fun signOut(): Resource<Unit>
    suspend fun deleteAccount(): Resource<Unit>
}
