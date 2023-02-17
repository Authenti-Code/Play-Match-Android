package com.playMatch.controller.playMatchAPi.apiClasses

import android.app.Activity
import com.playMatch.controller.playMatchAPi.ApiClient
import com.playMatch.controller.playMatchAPi.ApiService
import com.playMatch.controller.playMatchAPi.ResultResponse
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.controller.sharedPrefrence.PrefData
import com.saetae.controller.saetaeApi.postPojoModel.user.register.RegisterPost
import java.io.IOException

class UserApi( val activity: Activity): BaseActivity()  {

    private var apiService: ApiService? = null

    init {
        apiService = ApiClient().getClient().create(ApiService::class.java)
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
}