package com.playMatch.controller.playMatchAPi


import com.playMatch.ui.signUp.signupModel.RegistrationResponse
import com.saetae.controller.saetaeApi.postPojoModel.user.register.RegisterPost
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    //register phone number
    @Headers("Content-Type: application/json")
    @POST(ApiConstant.REGISTER)
    suspend fun registerUser(@Body signUp: RegisterPost?): Response<RegistrationResponse?>?

    //verify otp phone number
}