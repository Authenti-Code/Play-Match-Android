package com.playMatch.ui.profile.model.profile

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProfileResponse(
    @field:SerializedName("data")
    val `data`: Profile,
    @field:SerializedName("message")
    val message: String,
    @field:SerializedName("success")
    val success: String
): Parcelable
@Parcelize
data class Profile(
    @field:SerializedName("DOB")
    val DOB: String,
    @field:SerializedName("distance")
    val distance: String,
    @field:SerializedName("email")
    val email: String,
    @field:SerializedName("fitnessLevel")
    val fitnessLevel: String,
    @field:SerializedName("fri")
    val fri: String,
    @field:SerializedName("gender")
    val gender: String,
    @field:SerializedName("image")
    val image: String,
    @field:SerializedName("invitesReceived")
    val invitesReceived: String,
    @field:SerializedName("location")
    val location: String,
    @field:SerializedName("matchesCreated")
    val matchesCreated: String,
    @field:SerializedName("matchesPlayed")
    val matchesPlayed:String,
    @field:SerializedName("mon")
    val mon: String,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("sat")
    val sat: String,
    @field:SerializedName("sportLevel")
    val sportLevel: List<SportLevel>,
    @field:SerializedName("sun")
    val sun: String,
    @field:SerializedName("thu")
    val thu: String,
    @field:SerializedName("totalTeams")
    val totalTeams: String,
    @field:SerializedName("tue")
    val tue: String,
    @field:SerializedName("wed")
    val wed: String
): Parcelable

@Parcelize
data class SportLevel(
    @field:SerializedName("sportId")
    val sportId: Int,
    @field:SerializedName("sportLevel")
    val sportLevel: String,
    @field:SerializedName("levelName")
    val levelName: String,
    @field:SerializedName("sportName")
    val sportName: String,
): Parcelable