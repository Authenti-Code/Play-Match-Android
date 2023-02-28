package com.playMatch.controller.playMatchAPi


import com.playMatch.controller.playMatchAPi.postPojoModel.user.editProfile.EditProfilePost
import com.playMatch.ui.login.model.LoginResponse
import com.playMatch.controller.playMatchAPi.postPojoModel.user.login.LoginPost
import com.playMatch.controller.playMatchAPi.postPojoModel.user.logout.LogoutPost
import com.playMatch.controller.playMatchAPi.postPojoModel.user.matchAvailability.MatchAvailabilityPost
import com.playMatch.controller.playMatchAPi.postPojoModel.user.register.RegisterPost
import com.playMatch.ui.signUp.userSports.UserSportsPost
import com.playMatch.ui.profile.activity.settingActivity.model.LogoutResponse
import com.playMatch.ui.profile.model.editProfile.EditProfileResponse
import com.playMatch.ui.profile.model.profile.ProfileResponse
import com.playMatch.ui.signUp.signupModel.*
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
    @POST(ApiConstant.SPORTS_LIST)
    suspend fun sportsList(
    ):Response<SportListResponse>


    @Headers("Content-Type: application/json")
    @POST(ApiConstant.LOGOUT)
    suspend fun LogoutApi(
        @Header(ApiConstant.AUTH) token: String,
        @Body eventDetail: LogoutPost?
    ):Response<LogoutResponse>


    @Headers("Content-Type: application/json")
    @POST(ApiConstant.SPORTS_LEVELS)
    suspend fun sportLevels(
        @Header(ApiConstant.AUTH) token: String,
        @Body eventDetail: UserSportsPost?
    ):Response<SportsLevelsResponse>

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.MATCH_AVAILABILITY)
    suspend fun matchAbility(
        @Header(ApiConstant.AUTH) token: String,
        @Body eventDetail: MatchAvailabilityPost?
    ):Response<MatchAvailabilityResponse>

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.PROFILE)
    suspend fun profile(
        @Header(ApiConstant.AUTH) token: String,
    ):Response<ProfileResponse>

    @Multipart
    @POST(ApiConstant.EDIT_PROFILE)
    suspend fun editProfile(
        @Header(ApiConstant.AUTHORIZATION) token: String,
        @Part image: MultipartBody.Part,
        @Part eventDetail: EditProfilePost?
    ): Response<EditProfileResponse>
}