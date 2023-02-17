package com.playMatch.ui.signUp.signupModel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UploadImageResponse(
    @field:SerializedName("message")
    val message: String,
    @field:SerializedName("success")
    val success: String
): Parcelable