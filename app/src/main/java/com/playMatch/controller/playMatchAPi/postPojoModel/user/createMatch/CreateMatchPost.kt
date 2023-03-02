package com.playMatch.controller.playMatchAPi.postPojoModel.user.createMatch


data class CreateMatchPost(
    val name: String? = null,
    val matchDate: String? = null,
    val startTime: String? = null,
    val finishTime: String? = null,
    val location: String? = null,
    val gender: String? = null,
    val standard: String? = null,
    val sportId: String?= null,
    val teamId: String?= null,
    val description: String?= null
)

