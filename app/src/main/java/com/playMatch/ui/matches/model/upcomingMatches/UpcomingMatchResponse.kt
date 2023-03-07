package com.playMatch.ui.matches.model.upcomingMatches

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class UpcomingMatchResponse(
    @field:SerializedName("data")
    val `data`: List<UpComingMatchList>,
    @field:SerializedName("message")
    val message: String,
    @field:SerializedName("success")
    val success: String
):Parcelable
@Parcelize
data class UpComingMatchList(
    @field:SerializedName("compareTime")
    val compareTime: String,
    @field:SerializedName("created_at")
    val created_at: String,
    @field:SerializedName("description")
    val description: String,
    @field:SerializedName("finishTime")
    val finishTime: String,
    @field:SerializedName("gender")
    val gender: String,
    @field:SerializedName("id")
    val id: Int,
    @field:SerializedName("latitude")
    val latitude: String,
    @field:SerializedName("location")
    val location: String,
    @field:SerializedName("longitude")
    val longitude: String,
    @field:SerializedName("matchDate")
    val matchDate: String,
    @field:SerializedName("name")
    val name: String,
    @field:SerializedName("sportId")
    val sportId: Int,
    @field:SerializedName("sportName")
    val sportName: String,
    @field:SerializedName("standard")
    val standard: String,
    @field:SerializedName("startTime")
    val startTime: String,
    @field:SerializedName("teamId")
    val teamId: Int,
    @field:SerializedName("teamName")
    val teamName: String,
    @field:SerializedName("updated_at")
    val updated_at: String,
    @field:SerializedName("teamImage")
    val teamImage: String,
    @field:SerializedName("userId")
    val userId: Int
):Parcelable