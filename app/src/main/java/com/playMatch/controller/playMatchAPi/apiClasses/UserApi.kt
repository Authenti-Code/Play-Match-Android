package com.playMatch.controller.playMatchAPi.apiClasses

import android.app.Activity
import com.playMatch.controller.playMatchAPi.*
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.controller.sharedPrefrence.PrefData
import com.playMatch.controller.playMatchAPi.postPojoModel.user.login.LoginPost
import com.playMatch.controller.playMatchAPi.postPojoModel.user.logout.LogoutPost
import com.playMatch.controller.playMatchAPi.postPojoModel.user.register.RegisterPost
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
}