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
    val device_type: String?= null,
    val device_token: String?= null,
    val device_id: String?= null
)

