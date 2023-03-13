package com.playMatch.ui.matches.model.players

data class PlayersResponse(
    val `data`: List<PlayersListing>,
    val message: String,
    val success: String
)

data class PlayersListing(
    val gender: String,
    val image: String,
    val inviteId: Int,
    val levelName: String,
    val name: String,
    val sportLevel: String,
    val id: Int
)