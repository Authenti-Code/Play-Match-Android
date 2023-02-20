package com.playMatch.ui.login.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginResponse(
    @field:SerializedName("message")
    val message: String,
    @field:SerializedName("success")
    val success: String,
    @field:SerializedName("token")
    val token: String
): Parcelable