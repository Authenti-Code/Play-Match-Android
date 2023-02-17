package com.playMatch.controller.playMatchAPi


import com.playMatch.ui.signUp.signupModel.RegistrationResponse
import com.playMatch.ui.signUp.signupModel.UploadImageResponse
import com.saetae.controller.saetaeApi.postPojoModel.user.login.LoginPost
import com.saetae.controller.saetaeApi.postPojoModel.user.register.RegisterPost
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*


interface ApiService {

    //register phone number
    @Headers("Content-Type: application/json")
    @POST(ApiConstant.REGISTER)
    suspend fun registerUser(@Body signUp: RegisterPost?): Response<RegistrationResponse?>?

    @Multipart
    @POST(ApiConstant.UPLOAD_IMAGE)
    suspend fun uploadImageInChat(
        @Header(ApiConstant.AUTHORIZATION) token: String,
        @Part image: MultipartBody.Part
    ): Response<UploadImageResponse>

//    @Headers("Content-Type: application/json")
//    @POST(ApiConstant.LOGIN)
//    suspend fun userLogin(
//        @Body userLogin: LoginPost?): Response<UserLoginResponse?>?

    //verify otp phone number
}