package com.playMatch.ui.teams.model.showTeamDetails

data class TeamDetailResponse(
    val `data`: TeamDetail,
    val message: String,
    val success: String
)

data class TeamDetail(
    val created_at: String,
    val fri: String,
    val gender: String,
    val id: Int,
    val image: String,
    val isAwayMatches: String,
    val isKitProvided: String,
    val location: String,
    val mon: String,
    val name: String,
    val otherDetails: String,
    val sat: String,
    val sportId: Int,
    val sportName: String,
    val sun: String,
    val teamStandard: String,
    val thu: String,
    val tue: String,
    val updated_at: String,
    val userId: Int,
    val wed: String
)