package com.playMatch.controller.playMatchAPi


import com.playMatch.controller.playMatchAPi.apiClasses.ApiParameters
import com.playMatch.controller.playMatchAPi.postPojoModel.user.Match.EditMatchPost
import com.playMatch.controller.playMatchAPi.postPojoModel.user.createMatch.CreateMatchPost
import com.playMatch.ui.login.model.LoginResponse
import com.playMatch.controller.playMatchAPi.postPojoModel.user.login.LoginPost
import com.playMatch.controller.playMatchAPi.postPojoModel.user.logout.LogoutPost
import com.playMatch.controller.playMatchAPi.postPojoModel.user.matchAvailability.MatchAvailabilityPost
import com.playMatch.controller.playMatchAPi.postPojoModel.user.register.RegisterPost
import com.playMatch.controller.playMatchAPi.postPojoModel.user.showTeam.ShowTeamPost
import com.playMatch.controller.playMatchAPi.postPojoModel.user.Match.UpcomingMatchPost
import com.playMatch.ui.matches.model.createMatch.CreateMatchResponse
import com.playMatch.ui.matches.model.editMatch.EditMatchResponse
import com.playMatch.ui.matches.model.upcomingMatches.UpcomingMatchResponse
import com.playMatch.ui.signUp.userSports.UserSportsPost
import com.playMatch.ui.profile.activity.settingActivity.model.LogoutResponse
import com.playMatch.ui.profile.model.editProfile.EditProfileResponse
import com.playMatch.ui.profile.model.profile.ProfileResponse
import com.playMatch.ui.signUp.signupModel.*
import com.playMatch.ui.teams.model.addTeam.AddTeamResponse
import com.playMatch.ui.teams.model.editTeam.EditTeamResponse
import com.playMatch.ui.teams.model.showTeamDetails.TeamDetailResponse
import com.playMatch.ui.teams.model.teamList.TeamListResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
        @Part(ApiParameters.Name)name: RequestBody? = null,
        @Part(ApiParameters.GENDER) gender: RequestBody? = null,
        @Part (ApiParameters.DOB) dob:RequestBody,
        @Part (ApiParameters.LOCATION) location: RequestBody?= null,
        @Part (ApiParameters.FitnessLevel) fitnessLevel: RequestBody?= null,
        @Part (ApiParameters.Sunday) sun: RequestBody?= null,
        @Part (ApiParameters.Monday) mon: RequestBody?= null,
        @Part (ApiParameters.Tuesday) tue: RequestBody?= null,
        @Part (ApiParameters.Wednesday) wed: RequestBody?= null,
        @Part (ApiParameters.Thursday) thu: RequestBody?= null,
        @Part (ApiParameters.Friday) fri: RequestBody?= null,
        @Part (ApiParameters.Saturday) sat: RequestBody?= null,
        ): Response<EditProfileResponse>

    @Multipart
    @POST(ApiConstant.ADD_TEAM)
    suspend fun addTeam(
        @Header(ApiConstant.AUTHORIZATION) token: String,
        @Part image: MultipartBody.Part,
        @Part(ApiParameters.Name)name: RequestBody? = null,
        @Part(ApiParameters.GENDER) gender: RequestBody? = null,
        @Part (ApiParameters.SPORT_ID) sportId:RequestBody,
        @Part (ApiParameters.LOCATION) location: RequestBody?= null,
        @Part (ApiParameters.TeamStandard) teamStandard: RequestBody?= null,
        @Part (ApiParameters.IsKitProvided) isKitProvided: RequestBody?= null,
        @Part (ApiParameters.IsAwayMatches) isAwayMatches: RequestBody?= null,
        @Part (ApiParameters.Sunday) sun: RequestBody?= null,
        @Part (ApiParameters.Monday) mon: RequestBody?= null,
        @Part (ApiParameters.Tuesday) tue: RequestBody?= null,
        @Part (ApiParameters.Wednesday) wed: RequestBody?= null,
        @Part (ApiParameters.Thursday) thu: RequestBody?= null,
        @Part (ApiParameters.Friday) fri: RequestBody?= null,
        @Part (ApiParameters.Saturday) sat: RequestBody?= null,
        @Part (ApiParameters.OtherDetails) otherDetails: RequestBody?= null,
    ): Response<AddTeamResponse>

    @Multipart
    @POST(ApiConstant.EDIT_TEAM)
    suspend fun editTeam(
        @Header(ApiConstant.AUTHORIZATION) token: String,
        @Part image: MultipartBody.Part,
        @Part(ApiParameters.TEAM_ID)teamId: RequestBody? = null,
        @Part(ApiParameters.Name)name: RequestBody? = null,
        @Part(ApiParameters.GENDER) gender: RequestBody? = null,
        @Part (ApiParameters.SPORT_ID) sportId:RequestBody,
        @Part (ApiParameters.LOCATION) location: RequestBody?= null,
        @Part (ApiParameters.TeamStandard) teamStandard: RequestBody?= null,
        @Part (ApiParameters.IsKitProvided) isKitProvided: RequestBody?= null,
        @Part (ApiParameters.IsAwayMatches) isAwayMatches: RequestBody?= null,
        @Part (ApiParameters.Sunday) sun: RequestBody?= null,
        @Part (ApiParameters.Monday) mon: RequestBody?= null,
        @Part (ApiParameters.Tuesday) tue: RequestBody?= null,
        @Part (ApiParameters.Wednesday) wed: RequestBody?= null,
        @Part (ApiParameters.Thursday) thu: RequestBody?= null,
        @Part (ApiParameters.Friday) fri: RequestBody?= null,
        @Part (ApiParameters.Saturday) sat: RequestBody?= null,
        @Part (ApiParameters.OtherDetails) otherDetails: RequestBody?= null,
    ): Response<EditTeamResponse>


    @Headers("Content-Type: application/json")
    @POST(ApiConstant.TEAM_LIST)
    suspend fun teams(
        @Header(ApiConstant.AUTH) token: String,
    ):Response<TeamListResponse>

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.SHOW_TEAM)
    suspend fun showTeam(
        @Header(ApiConstant.AUTH) token: String,
        @Body eventDetail: ShowTeamPost?
    ):Response<TeamDetailResponse>

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.LIST_MATCH)
    suspend fun listMatch(
        @Header(ApiConstant.AUTH) token: String,
        @Body eventDetail: UpcomingMatchPost?
    ):Response<UpcomingMatchResponse>

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.CREATE_MATCH)
    suspend fun createMatch(
        @Header(ApiConstant.AUTH) token: String,
        @Body eventDetail: CreateMatchPost?
    ):Response<CreateMatchResponse>

    @Headers("Content-Type: application/json")
    @POST(ApiConstant.EDIT_MATCH)
    suspend fun editMatch(
        @Header(ApiConstant.AUTH) token: String,
        @Body eventDetail: EditMatchPost?
    ):Response<EditMatchResponse>
}