package com.playMatch.controller.playMatchAPi.postPojoModel.user.Match

data class EditMatchPost(
    val description: String,
    val finishTime: String,
    val gender: String,
    val latitude: String,
    val location: String,
    val longitude: String,
    val matchDate: String,
    val matchId: String,
    val name: String,
    val sportId: String,
    val standard: String,
    val startTime: String,
    val teamId: String
)