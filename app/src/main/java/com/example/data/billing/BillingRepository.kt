package com.example.data.billing

import com.example.core.common.Resource
import com.example.domain.subscription.PlanLimits
import com.example.domain.subscription.PlanTier
import com.example.domain.subscription.SubscriptionPlan
import kotlinx.coroutines.flow.Flow

interface BillingRepository {
    fun observeCurrentTier(): Flow<PlanTier>
    suspend fun getAvailablePlans(): Resource<List<SubscriptionPlan>>
    suspend fun getPlanLimits(tier: PlanTier): PlanLimits
}
