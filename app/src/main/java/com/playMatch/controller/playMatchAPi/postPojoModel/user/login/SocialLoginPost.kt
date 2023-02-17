package com.saetae.controller.saetaeApi.postPojoModel.user.login

data class SocialLoginPost( val name: String? = null,
                            val email: String? = null,
                            val social_id: String? = null,
                            val signup_type: String? = null,
                            val device_type: String?= null,
                            val device_token: String?= null,
                            val device_id: String?= null)
