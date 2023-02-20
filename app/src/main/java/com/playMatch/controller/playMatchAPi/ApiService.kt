package com.playMatch.controller.playMatchAPi


import com.playMatch.ui.login.model.LoginResponse
import com.playMatch.ui.signUp.signupModel.RegistrationResponse
import com.playMatch.ui.signUp.signupModel.UploadImageResponse
import com.playMatch.controller.playMatchAPi.postPojoModel.user.login.LoginPost
import com.playMatch.controller.playMatchAPi.postPojoModel.user.logout.LogoutPost
import com.playMatch.controller.playMatchAPi.postPojoModel.user.register.RegisterPost
import com.playMatch.ui.profile.activity.settingActivity.model.LogoutResponse
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

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.LOGIN)
    suspend fun userLogin(
        @Body userLogin: LoginPost?): Response<LoginResponse?>?


    @Headers("Content-Type: application/json")
    @POST(ApiConstant.LOGOUT)
    suspend fun LogoutApi(
        @Header(ApiConstant.AUTH) token: String,
        @Body eventDetail: LogoutPost?
    ):Response<LogoutResponse>

    //verify otp phone number
}