package com.playMatch.ui.signUp.signupModel

data class SportListResponse(
    val `data`: List<SportsList>,
    val message: String,
    val success: String
)

data class SportsList(
    val created_at: String,
    val id: Int,
    val sportName: String,
    val updated_at: String
)