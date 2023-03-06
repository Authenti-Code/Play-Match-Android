package com.playMatch.ui.matches.model.editMatch

data class EditMatchResponse(
    val `data`: EditMatch,
    val message: String,
    val success: String
)

data class EditMatch(
    val created_at: String,
    val description: String,
    val finishTime: String,
    val gender: String,
    val id: Int,
    val location: String,
    val matchDate: String,
    val name: String,
    val sportId: Int,
    val standard: String,
    val startTime: String,
    val teamId: Int,
    val updated_at: String,
    val userId: Int
)