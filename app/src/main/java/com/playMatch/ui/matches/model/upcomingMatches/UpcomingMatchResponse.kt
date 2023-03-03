package com.playMatch.ui.matches.model.upcomingMatches

data class UpcomingMatchResponse(
    val `data`: List<UpComingMatchList>,
    val message: String,
    val success: String
)

data class UpComingMatchList(
    val compareTime: String,
    val created_at: String,
    val description: String,
    val finishTime: String,
    val gender: String,
    val id: Int,
    val latitude: String,
    val location: String,
    val longitude: String,
    val matchDate: String,
    val name: String,
    val sportId: Int,
    val sportName: String,
    val standard: String,
    val startTime: String,
    val teamId: Int,
    val teamName: String,
    val updated_at: String,
    val image: String,
    val userId: Int
)