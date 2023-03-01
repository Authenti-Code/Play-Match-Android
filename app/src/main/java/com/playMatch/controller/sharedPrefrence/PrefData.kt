package com.playMatch.controller.sharedPrefrence

import android.content.Context

object PrefData {
    private const val PREFERENCE = "SaetaePref"
    const val KEY_BEARER_TOKEN = "key_bearer_token"
    const val NAME = "Name"
    const val MY_ID = "my_id"
    const val TEAM_ID = "Team_Id"
    const val CHAT_ROOM_ID = "chat_room_id"
    const val OTHER_USER_ID = "other_user_id"
    const val OTHER_USER_Receiver_ID = "other_user_receiver_id"
    const val EMAIL = "email"
    const val PASSWORD = "password"
    const val CONFIRM_PASSWORD = "confirm_password"
    const val CHECK_BOX="Check_Box"
    const val IS_USER_LOGIN="is_user_login"
    const val SPORT_IMAGE="sport_image"
    const val SPORT_ID="sport_id"
    const val DISTANCE="distance_km"
    const val SOCIAL_LOGIN="social_login"
    const val LOCATION="location_name"
    const val LONGITUDE="longitude"
    const val EDIT="edit"

    const val CURRENT_USER_SCREEN_TYPE="current_screen_type"
    const val DEVICE_TOKEN = "device_token"
    const val IS_LOCATION_PERMISSION_FIRST_TIME_DIALOG = "is_location_permission_first_time_dialog"


    const val KEY_NOTIFICATION_IS_MESSAGE="key_is_message"
    const val KEY_NOTIFICATION_IS_CHAT="key_is_notification"
    const val KEY_NOTIFICATION_TYPE="key_notification_type"
    const val New_ARRAYLIST="New_ArrayList"





    fun setBooleanPrefs(context: Context, prefKey: String, value: Boolean) {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).edit()
            .putBoolean(prefKey, value).apply()
    }

//    fun setUserDetail(context: Context, prefKey: String, model: String?) {
//        val gson = Gson()
//        val json = gson.toJson(model)
//        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(prefKey, json)
//            .apply()
//    }

    fun getBooleanPrefs(context: Context, prefKey: String): Boolean {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
            .getBoolean(prefKey, false)
    }


    fun getBooleanPrefs(context: Context, prefKey: String, defaultValue: Boolean): Boolean {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
            .getBoolean(prefKey, defaultValue)
    }

//    fun getUserDetail(context: Context, prefKey: String): UserLoginResponse? {
//        val gson = Gson()
//        val json =
//            PreferenceManager.getDefaultSharedPreferences(context).getString(prefKey, null)
//        val type = object : TypeToken<UserLoginResponse>() {
//
//        }.type
//        return gson.fromJson<UserLoginResponse>(json, type)
//    }

    fun setStringPrefs(context: Context, prefKey: String, Value: String?) {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).edit()
            .putString(prefKey, Value).apply()
    }

    fun getStringPrefs(context: Context, prefKey: String): String? {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
            .getString(prefKey, null)
    }

    fun getStringPrefs(context: Context, prefKey: String, defaultValue: String): String {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
            .getString(prefKey, defaultValue)
            .toString()
    }

    fun setIntPrefs(context: Context, prefKey: String, value: Int) {
        return context.getSharedPreferences(PREFERENCE, 0).edit().putInt(prefKey, value)
            .apply()
    }

    fun getIntPrefs(context: Context, prefKey: String): Int {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).getInt(prefKey, 0)
    }

    fun getIntPrefs(context: Context, prefKey: String, defaultValue: Int): Int {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
            .getInt(prefKey, defaultValue)
    }

    fun setLongPrefs(context: Context, prefKey: String, value: Long) {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).edit()
            .putLong(prefKey, value).apply()
    }

    fun getLongPrefs(context: Context, prefKey: String): Long {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
            .getLong(prefKey, 0)
    }

    fun getLongPrefs(context: Context, prefKey: String, defaultValue: Long): Long {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
            .getLong(prefKey, defaultValue)
    }

    fun setFloatPrefs(context: Context, prefKey: String, value: Float) {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).edit()
            .putFloat(prefKey, value).apply()
    }

    fun getFloatPrefs(context: Context, prefKey: String): Float {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
            .getFloat(prefKey, 0f)
    }

    fun getFloatPrefs(context: Context, prefKey: String, defaultValue: Long): Float {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
            .getFloat(prefKey, defaultValue.toFloat())
    }


    /**
     * Clear all data in SharedPreference
     *
     * @param context
     */
    fun clearWholePreference(context: Context): Boolean {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).edit().clear()
            .commit()
    }

    /**
     * Clear single key value
     *
     * @param prefKey
     * @param context
     */
    fun remove(context: Context, prefKey: String) {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).edit()
            .remove(prefKey).apply()
    }

    //getter and setter arraylist
//    inline fun <reified T> setArraylist(
//        context: Context?,
//        prefKey: String?,
//        arraylist: ArrayList<T>
//    ) {
//        val gson = Gson()
//        val json = gson.toJson(arraylist)
//        if (context != null) {
//            PreferenceManager.getDefaultSharedPreferences(context).edit()
//                .putString(prefKey, json).apply()
//        }
//    }


//    inline fun <reified T> getArraylist(
//        context: Context?,
//        prefKey: String?
//    ): ArrayList<T>? {
//        val gson = Gson()
//        val json =
//            context?.let {
//                PreferenceManager.getDefaultSharedPreferences(it)
//                    .getString(prefKey, ",")
//            }
//        val type: Type = object :
//            TypeToken<ArrayList<T>?>() {}.type
//        return gson.fromJson(json, type)
//    }
}