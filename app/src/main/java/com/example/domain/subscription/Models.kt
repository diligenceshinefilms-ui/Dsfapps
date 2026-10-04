package com.example.domain.subscription

enum class PlanTier(val title: String, val maxSeconds: Int) {
    FREE("Free Plan", 30),
    CREATOR("Creator Plan", 60),
    PRO("Pro Plan", 180),
    STUDIO("Studio Plan", 600)
}

data class PlanLimits(
    val tier: PlanTier,
    val maxDurationSeconds: Int,
    val monthlyGenerations: Int,
    val hasWatermark: Boolean,
    val supports4k: Boolean,
    val priorityQueue: Boolean
)

data class SubscriptionPlan(
    val id: String,
    val tier: PlanTier,
    val priceFormatted: String,
    val billingPeriod: String,
    val features: List<String>
)
