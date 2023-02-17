package com.saetae.controller.saetaeApi.postPojoModel.user.register

import android.app.Notification

data class RegisterPost(
    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
    val confirmpass: String? = null,
    val DOB: String? = null,
    val location: String? = null,
    val gender: String? = null,
    val fitnessLevel: String? = null,
    val deviceType: String?= null,
    val deviceToken: String?= null,
    val deviceID: String?= null
)

