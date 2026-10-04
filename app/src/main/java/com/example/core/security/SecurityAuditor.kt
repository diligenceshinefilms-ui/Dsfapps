package com.example.core.security

object SecurityAuditor {
    /**
     * Checks whether an entered prompt contains overt prohibited content
     * before delegating to backend content safety services.
     */
    fun isPreflightPromptSafe(prompt: String): Boolean {
        if (prompt.isBlank()) return false
        val lower = prompt.lowercase()
        val hardProhibitedTerms = listOf(
            "child sexual",
            "csam",
            "non-consensual",
            "deepfake revenge",
            "suicide instructions",
            "bomb making"
        )
        return hardProhibitedTerms.none { term -> lower.contains(term) }
    }
}
