package com.playMatch.ui.home.model.homeResponse

data class HomeResponse(
    val `data`: List<HomeList>,
    val message: String,
    val success: String
)

data class HomeList(
    val interests: List<Any>,
    val invites: List<Any>,
    val matchCount: Int,
    val name: String,
    val teams: List<TeamList>,
    val teamsCount: Int,
    val upcomingMatch: List<UpcomingMatchList>
)

data class TeamList(
    val image: String,
    val name: String,
    val sportName: String,
    val teamId: Int,
    val teamStandard: String,
    val userId: Int
)

data class UpcomingMatchList(
    val compareTime: String,
    val created_at: String,
    val description: String,
    val finishTime: String,
    val gender: String,
    val id: Int,
    val latitude: String,
    val level: String,
    val location: String,
    val longitude: String,
    val matchDate: String,
    val name: String,
    val sportId: Int,
    val sportName: String,
    val standard: String,
    val startTime: String,
    val teamId: Int,
    val teamImage: String,
    val teamName: String,
    val updated_at: String,
    val userId: Int
)