package com.saetae.controller.saetaeApi.postPojoModel.user.login

data class LoginPost(
    val email: String? = null,
    val password: String? = null,
    val device_type: String?= null,
    val device_token: String?= null,
    val device_id: String?= null
)
