package com.playMatch.controller.playMatchAPi.postPojoModel.user.login

data class LoginPost(
    val email: String? = null,
    val password: String? = null,
    val deviceType: String?= null,
    val deviceToken: String?= null,
    val deviceID: String?= null
)
