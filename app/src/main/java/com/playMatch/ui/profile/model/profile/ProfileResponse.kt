package com.playMatch.ui.profile.model.profile

data class ProfileResponse(
    val `data`: Profile,
    val message: String,
    val success: String
)

data class Profile(
    val DOB: String,
    val distance: String,
    val email: String,
    val fitnessLevel: String,
    val fri: String,
    val gender: String,
    val image: String,
    val invitesReceived: Any,
    val location: String,
    val matchesCreated: Any,
    val matchesPlayed: Any,
    val mon: String,
    val name: String,
    val sat: String,
    val sportLevel: List<SportLevel>,
    val sun: String,
    val thu: String,
    val totalTeams: Any,
    val tue: String,
    val wed: String
)
data class SportLevel(
    val sportId: Any,
    val sportLevel: String,
    val sportName: Any,
)