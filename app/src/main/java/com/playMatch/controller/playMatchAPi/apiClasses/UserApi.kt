package com.playMatch.controller.playMatchAPi.apiClasses

import android.app.Activity
import com.playMatch.controller.playMatchAPi.*
import com.playMatch.controller.playMatchAPi.postPojoModel.user.createMatch.CreateMatchPost
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.controller.sharedPrefrence.PrefData
import com.playMatch.controller.playMatchAPi.postPojoModel.user.login.LoginPost
import com.playMatch.controller.playMatchAPi.postPojoModel.user.logout.LogoutPost
import com.playMatch.controller.playMatchAPi.postPojoModel.user.matchAvailability.MatchAvailabilityPost
import com.playMatch.controller.playMatchAPi.postPojoModel.user.register.RegisterPost
import com.playMatch.controller.playMatchAPi.postPojoModel.user.showTeam.ShowTeamPost
import com.playMatch.controller.playMatchAPi.postPojoModel.user.Match.UpcomingMatchPost
import com.playMatch.ui.signUp.userSports.UserSportsPost
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class UserApi( val activity: Activity): BaseActivity()  {

    private var apiService: ApiService? = null

    init {
//        apiService = ApiClient().getClient().create(ApiService::class.java)
        apiService = OldApiClient().getClient().create(ApiService::class.java)
    }
    var token:String?=null
    /**
     * register user
     * */

    init {
        token = PrefData.getStringPrefs(activity, PrefData.KEY_BEARER_TOKEN, "")
    }


    suspend fun registerUser(registerPost: RegisterPost): ResultResponse {
        return try {
            val response = apiService?.registerUser(registerPost)
            if (response?.isSuccessful!!) {
                val model = response.body()
                ResultResponse.Success(model)
            } else {
                when (response.code()) {
                    403 -> ResultResponse.HttpErrors.ResourceForbidden(response.message())
                    404 -> ResultResponse.HttpErrors.ResourceNotFound(response.message())
                    500 -> ResultResponse.HttpErrors.InternalServerError(response.message())
                    502 -> ResultResponse.HttpErrors.BadGateWay(response.message())
                    301 -> ResultResponse.HttpErrors.ResourceRemoved(response.message())
                    302 -> ResultResponse.HttpErrors.RemovedResourceFound(response.message())
                    else -> ResultResponse.Error(response.message())
                }
            }

        } catch (error: IOException) {
            ResultResponse.NetworkException(error.message!!)
        }
    }

    suspend fun uploadImage(
        activity: Activity,
        imageBytes: ByteArray,
        imageName: String
    ): ResultResponse {
        return try {
            val requestFile =
                imageBytes.toRequestBody("image/*".toMediaTypeOrNull(), 0, imageBytes.size)

            val imageBody = MultipartBody.Part.createFormData("image", imageName, requestFile)
            val response = apiService?.uploadImageInChat(ApiConstant.BEARER_TOKEN + " " +token!!, imageBody)
            if (response?.isSuccessful!!) {
                val model = response.body()
                ResultResponse.Success(model)
            } else {
                when (response.code()) {
                    403 -> ResultResponse.HttpErrors.ResourceForbidden(response.message())
                    404 -> ResultResponse.HttpErrors.ResourceNotFound(response.message())
                    500 -> ResultResponse.HttpErrors.InternalServerError(response.message())
                    502 -> ResultResponse.HttpErrors.BadGateWay(response.message())
                    301 -> ResultResponse.HttpErrors.ResourceRemoved(response.message())
                    302 -> ResultResponse.HttpErrors.RemovedResourceFound(response.message())
                    else -> ResultResponse.Error(response.message())
                }
            }

        } catch (error: IOException) {
            ResultResponse.NetworkException(error.message!!)
        }
    }


    suspend fun loginUser(loginPost: LoginPost): ResultResponse {
        return try {
            val response = apiService?.userLogin(loginPost)
            if (response?.isSuccessful!!) {
                val model = response.body()
                ResultResponse.Success(model)
            } else {
                when (response.code()) {
                    403 -> ResultResponse.HttpErrors.ResourceForbidden(response.message())
                    404 -> ResultResponse.HttpErrors.ResourceNotFound(response.message())
                    500 -> ResultResponse.HttpErrors.InternalServerError(response.message())
                    502 -> ResultResponse.HttpErrors.BadGateWay(response.message())
                    301 -> ResultResponse.HttpErrors.ResourceRemoved(response.message())
                    302 -> ResultResponse.HttpErrors.RemovedResourceFound(response.message())
                    else -> ResultResponse.Error(response.message())
                }
            }

        } catch (error: IOException) {
            ResultResponse.NetworkException(error.message!!)
        }
    }


    suspend fun sportsList(): ResultResponse {
        return try {
            val response = apiService?.sportsList()
            if (response?.isSuccessful!!) {
                val model = response.body()
                ResultResponse.Success(model)
            } else {
                when (response.code()) {
                    403 -> ResultResponse.HttpErrors.ResourceForbidden(response.message())
                    404 -> ResultResponse.HttpErrors.ResourceNotFound(response.message())
                    500 -> ResultResponse.HttpErrors.InternalServerError(response.message())
                    502 -> ResultResponse.HttpErrors.BadGateWay(response.message())
                    301 -> ResultResponse.HttpErrors.ResourceRemoved(response.message())
                    302 -> ResultResponse.HttpErrors.RemovedResourceFound(response.message())
                    else -> ResultResponse.Error(response.message())
                }
            }

        } catch (error: IOException) {
            ResultResponse.NetworkException(error.message!!)
        }
    }


    suspend fun Logout(logoutPost: LogoutPost): ResultResponse {

        return try {
            val response = apiService?.LogoutApi(ApiConstant.BEARER_TOKEN + " " + token,logoutPost)
            if (response?.isSuccessful!!) {
                val model = response.body()
                ResultResponse.Success(model)
            } else {
                when (response.code()) {
                    403 -> ResultResponse.HttpErrors.ResourceForbidden(response.message())
                    404 -> ResultResponse.HttpErrors.ResourceNotFound(response.message())
                    500 -> ResultResponse.HttpErrors.InternalServerError(response.message())
                    502 -> ResultResponse.HttpErrors.BadGateWay(response.message())
                    301 -> ResultResponse.HttpErrors.ResourceRemoved(response.message())
                    302 -> ResultResponse.HttpErrors.RemovedResourceFound(response.message())
                    else -> ResultResponse.Error(response.message())
                }
            }

        } catch (error: IOException) {
            ResultResponse.NetworkException(error.message!!)
        }
    }


    suspend fun sportsLevels(userSportsPost: UserSportsPost): ResultResponse {
        return try {
            val response = apiService?.sportLevels(ApiConstant.BEARER_TOKEN + " " + token,userSportsPost)
            if (response?.isSuccessful!!) {
                val model = response.body()
                ResultResponse.Success(model)
            } else {
                when (response.code()) {
                    403 -> ResultResponse.HttpErrors.ResourceForbidden(response.message())
                    404 -> ResultResponse.HttpErrors.ResourceNotFound(response.message())
                    500 -> ResultResponse.HttpErrors.InternalServerError(response.message())
                    502 -> ResultResponse.HttpErrors.BadGateWay(response.message())
                    301 -> ResultResponse.HttpErrors.ResourceRemoved(response.message())
                    302 -> ResultResponse.HttpErrors.RemovedResourceFound(response.message())
                    else -> ResultResponse.Error(response.message())
                }
            }

        } catch (error: IOException) {
            ResultResponse.NetworkException(error.message!!)
        }
    }
    suspend fun matchAvailability(matchAvailabilityPost: MatchAvailabilityPost): ResultResponse {
        return try {
            val response = apiService?.matchAbility(ApiConstant.BEARER_TOKEN + " " + token,matchAvailabilityPost)
            if (response?.isSuccessful!!) {
                val model = response.body()
                ResultResponse.Success(model)
            } else {
                when (response.code()) {
                    403 -> ResultResponse.HttpErrors.ResourceForbidden(response.message())
                    404 -> ResultResponse.HttpErrors.ResourceNotFound(response.message())
                    500 -> ResultResponse.HttpErrors.InternalServerError(response.message())
                    502 -> ResultResponse.HttpErrors.BadGateWay(response.message())
                    301 -> ResultResponse.HttpErrors.ResourceRemoved(response.message())
                    302 -> ResultResponse.HttpErrors.RemovedResourceFound(response.message())
                    else -> ResultResponse.Error(response.message())
                }
            }

        } catch (error: IOException) {
            ResultResponse.NetworkException(error.message!!)
        }
    }

    suspend fun profile(): ResultResponse {
        return try {
            val response = apiService?.profile(ApiConstant.BEARER_TOKEN + " " + token)
            if (response?.isSuccessful!!) {
                val model = response.body()
                ResultResponse.Success(model)
            } else {
                when (response.code()) {
                    403 -> ResultResponse.HttpErrors.ResourceForbidden(response.message())
                    404 -> ResultResponse.HttpErrors.ResourceNotFound(response.message())
                    500 -> ResultResponse.HttpErrors.InternalServerError(response.message())
                    502 -> ResultResponse.HttpErrors.BadGateWay(response.message())
                    301 -> ResultResponse.HttpErrors.ResourceRemoved(response.message())
                    302 -> ResultResponse.HttpErrors.RemovedResourceFound(response.message())
                    else -> ResultResponse.Error(response.message())
                }
            }

        } catch (error: IOException) {
            ResultResponse.NetworkException(error.message!!)
        }
    }

    suspend fun EditProfile(
        activity: Activity,
        imageBytes: ByteArray,
        imageName: String,
        name: String,
        gender: String,
        dob: String,
        location: String,
        fitness: String,
        sun: String,
        mon: String,
        tue: String,
        wed: String,
        thu: String,
        fri: String,
        sat: String,
    ): ResultResponse {
        return try {
            val requestFile = imageBytes.toRequestBody("image/*".toMediaTypeOrNull(), 0, imageBytes.size)

            val imageBody = MultipartBody.Part.createFormData("image", imageName, requestFile)
            val response = apiService?.editProfile(ApiConstant.BEARER_TOKEN + " " +token!!,imageBody,name.toRequestBody(),gender.toRequestBody(),dob.toRequestBody(),location.toRequestBody(),fitness.toRequestBody(),sun.toRequestBody(),mon.toRequestBody(),tue.toRequestBody(),wed.toRequestBody(),thu.toRequestBody(),fri.toRequestBody(),sat.toRequestBody())
            if (response?.isSuccessful!!) {
                val model = response.body()
                ResultResponse.Success(model)
            } else {
                when (response.code()) {
                    403 -> ResultResponse.HttpErrors.ResourceForbidden(response.message())
                    404 -> ResultResponse.HttpErrors.ResourceNotFound(response.message())
                    500 -> ResultResponse.HttpErrors.InternalServerError(response.message())
                    502 -> ResultResponse.HttpErrors.BadGateWay(response.message())
                    301 -> ResultResponse.HttpErrors.ResourceRemoved(response.message())
                    302 -> ResultResponse.HttpErrors.RemovedResourceFound(response.message())
                    else -> ResultResponse.Error(response.message())
                }
            }

        } catch (error: IOException) {
            ResultResponse.NetworkException(error.message!!)
        }
    }


    suspend fun addTeam(
        activity: Activity,
        imageBytes: ByteArray,
        imageName: String,
        name: String,
        gender: String,
        sportId: String,
        location: String,
        teamStandard: String,
        isKitProvided: String,
        isAwayMatches: String,
        sun: String,
        mon: String,
        tue: String,
        wed: String,
        thu: String,
        fri: String,
        sat: String,
        otherDetails: String,
    ): ResultResponse {
        return try {
            val requestFile = imageBytes.toRequestBody("image/*".toMediaTypeOrNull(), 0, imageBytes.size)

            val imageBody = MultipartBody.Part.createFormData("image", imageName, requestFile)
            val response = apiService?.addTeam(ApiConstant.BEARER_TOKEN + " " +token!!,imageBody,name.toRequestBody(),gender.toRequestBody(),sportId.toRequestBody(),location.toRequestBody(),teamStandard.toRequestBody(),isKitProvided.toRequestBody(),isAwayMatches.toRequestBody(),sun.toRequestBody(),mon.toRequestBody(),tue.toRequestBody(),wed.toRequestBody(),thu.toRequestBody(),fri.toRequestBody(),sat.toRequestBody(),otherDetails.toRequestBody())
            if (response?.isSuccessful!!) {
                val model = response.body()
                ResultResponse.Success(model)
            } else {
                when (response.code()) {
                    403 -> ResultResponse.HttpErrors.ResourceForbidden(response.message())
                    404 -> ResultResponse.HttpErrors.ResourceNotFound(response.message())
                    500 -> ResultResponse.HttpErrors.InternalServerError(response.message())
                    502 -> ResultResponse.HttpErrors.BadGateWay(response.message())
                    301 -> ResultResponse.HttpErrors.ResourceRemoved(response.message())
                    302 -> ResultResponse.HttpErrors.RemovedResourceFound(response.message())
                    else -> ResultResponse.Error(response.message())
                }
            }

        } catch (error: IOException) {
            ResultResponse.NetworkException(error.message!!)
        }
    }

    suspend fun teams(): ResultResponse {
        return try {
            val response = apiService?.teams(ApiConstant.BEARER_TOKEN + " " + token)
            if (response?.isSuccessful!!) {
                val model = response.body()
                ResultResponse.Success(model)
            } else {
                when (response.code()) {
                    403 -> ResultResponse.HttpErrors.ResourceForbidden(response.message())
                    404 -> ResultResponse.HttpErrors.ResourceNotFound(response.message())
                    500 -> ResultResponse.HttpErrors.InternalServerError(response.message())
                    502 -> ResultResponse.HttpErrors.BadGateWay(response.message())
                    301 -> ResultResponse.HttpErrors.ResourceRemoved(response.message())
                    302 -> ResultResponse.HttpErrors.RemovedResourceFound(response.message())
                    else -> ResultResponse.Error(response.message())
                }
            }

        } catch (error: IOException) {
            ResultResponse.NetworkException(error.message!!)
        }
    }

    suspend fun teamDetail(showTeamPost: ShowTeamPost): ResultResponse {
        return try {
            val response = apiService?.showTeam(ApiConstant.BEARER_TOKEN + " " + token,showTeamPost)
            if (response?.isSuccessful!!) {
                val model = response.body()
                ResultResponse.Success(model)
            } else {
                when (response.code()) {
                    403 -> ResultResponse.HttpErrors.ResourceForbidden(response.message())
                    404 -> ResultResponse.HttpErrors.ResourceNotFound(response.message())
                    500 -> ResultResponse.HttpErrors.InternalServerError(response.message())
                    502 -> ResultResponse.HttpErrors.BadGateWay(response.message())
                    301 -> ResultResponse.HttpErrors.ResourceRemoved(response.message())
                    302 -> ResultResponse.HttpErrors.RemovedResourceFound(response.message())
                    else -> ResultResponse.Error(response.message())
                }
            }

        } catch (error: IOException) {
            ResultResponse.NetworkException(error.message!!)
        }
    }

    suspend fun editTeam(
        activity: Activity,
        imageBytes: ByteArray,
        imageName: String,
        teamId: String,
        name: String,
        gender: String,
        sportId: String,
        location: String,
        teamStandard: String,
        isKitProvided: String,
        isAwayMatches: String,
        sun: String,
        mon: String,
        tue: String,
        wed: String,
        thu: String,
        fri: String,
        sat: String,
        otherDetails: String,
    ): ResultResponse {
        return try {
            val requestFile = imageBytes.toRequestBody("image/*".toMediaTypeOrNull(), 0, imageBytes.size)

            val imageBody = MultipartBody.Part.createFormData("image", imageName, requestFile)
            val response = apiService?.editTeam(ApiConstant.BEARER_TOKEN + " " +token!!,imageBody,teamId.toRequestBody(),name.toRequestBody(),gender.toRequestBody(),sportId.toRequestBody(),location.toRequestBody(),teamStandard.toRequestBody(),isKitProvided.toRequestBody(),isAwayMatches.toRequestBody(),sun.toRequestBody(),mon.toRequestBody(),tue.toRequestBody(),wed.toRequestBody(),thu.toRequestBody(),fri.toRequestBody(),sat.toRequestBody(),otherDetails.toRequestBody())
            if (response?.isSuccessful!!) {
                val model = response.body()
                ResultResponse.Success(model)
            } else {
                when (response.code()) {
                    403 -> ResultResponse.HttpErrors.ResourceForbidden(response.message())
                    404 -> ResultResponse.HttpErrors.ResourceNotFound(response.message())
                    500 -> ResultResponse.HttpErrors.InternalServerError(response.message())
                    502 -> ResultResponse.HttpErrors.BadGateWay(response.message())
                    301 -> ResultResponse.HttpErrors.ResourceRemoved(response.message())
                    302 -> ResultResponse.HttpErrors.RemovedResourceFound(response.message())
                    else -> ResultResponse.Error(response.message())
                }
            }

        } catch (error: IOException) {
            ResultResponse.NetworkException(error.message!!)
        }
    }

    suspend fun createMatch(createMatchPost: CreateMatchPost): ResultResponse {
        return try {
            val response = apiService?.createMatch(ApiConstant.BEARER_TOKEN + " " + token,createMatchPost)
            if (response?.isSuccessful!!) {
                val model = response.body()
                ResultResponse.Success(model)
            } else {
                when (response.code()) {
                    403 -> ResultResponse.HttpErrors.ResourceForbidden(response.message())
                    404 -> ResultResponse.HttpErrors.ResourceNotFound(response.message())
                    500 -> ResultResponse.HttpErrors.InternalServerError(response.message())
                    502 -> ResultResponse.HttpErrors.BadGateWay(response.message())
                    301 -> ResultResponse.HttpErrors.ResourceRemoved(response.message())
                    302 -> ResultResponse.HttpErrors.RemovedResourceFound(response.message())
                    else -> ResultResponse.Error(response.message())
                }
            }

        } catch (error: IOException) {
            ResultResponse.NetworkException(error.message!!)
        }
    }

    suspend fun upcomingMatch(upcomingMatchPost: UpcomingMatchPost): ResultResponse {
        return try {
            val response = apiService?.listMatch(ApiConstant.BEARER_TOKEN + " " + token,upcomingMatchPost)
            if (response?.isSuccessful!!) {
                val model = response.body()
                ResultResponse.Success(model)
            } else {
                when (response.code()) {
                    403 -> ResultResponse.HttpErrors.ResourceForbidden(response.message())
                    404 -> ResultResponse.HttpErrors.ResourceNotFound(response.message())
                    500 -> ResultResponse.HttpErrors.InternalServerError(response.message())
                    502 -> ResultResponse.HttpErrors.BadGateWay(response.message())
                    301 -> ResultResponse.HttpErrors.ResourceRemoved(response.message())
                    302 -> ResultResponse.HttpErrors.RemovedResourceFound(response.message())
                    else -> ResultResponse.Error(response.message())
                }
            }

        } catch (error: IOException) {
            ResultResponse.NetworkException(error.message!!)
        }
    }

}