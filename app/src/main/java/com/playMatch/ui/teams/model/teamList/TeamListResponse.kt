package com.playMatch.ui.teams.model.teamList

data class TeamListResponse(
    val `data`: List<TeamList>,
    val message: String,
    val success: String
)

data class TeamList(
    val image: String,
    val name: String,
    val sportName: String,
    val teamId: Int,
    val teamStandard: String,
    val userId: Int
)