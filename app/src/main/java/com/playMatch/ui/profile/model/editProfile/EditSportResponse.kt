package com.playMatch.ui.profile.model.editProfile

data class EditSportResponse(
    val `data`: List<EditSportList>,
    val message: String,
    val success: String
)

data class EditSportList(
    val created_at: String,
    val id: Int,
    val isSelected: Int,
    val sportLevel: String,
    val levelName: String,
    val sportName: String,
    val updated_at: String
)