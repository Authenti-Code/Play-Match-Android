package com.playMatch.controller.playMatchAPi

import org.w3c.dom.Comment

object ApiConstant {
    const val AUTHORIZATION = "Authorization"

    // user methods
    const val REGISTER = "register"
    const val SOCIAL_LOGIN = "social_registger"

    const val LOGIN = "login"

    const val BEARER_TOKEN = "Bearer"

    const val AUTH="Authorization"

    const val CHANGE_PASSWORD="userchngpass"

    const val EDIT_PROFILE="editUserProfile"
    const val UPLOAD_IMAGE="userImage"

    const val PROFILE="userprofile"

    const val SPORTS_LIST="sportsList"
    const val SPORTS_LEVELS="sportAbilityLevels"
    const val LOGOUT="logout"

    const val EVENT_SHOW="eventshow"
    const val EVENT_DETAIL="eventdetails"
    const val COMMENT="comment"
    const val INTERESTED_USERS="event_intrestedUsers"
    const val EVENT_PARTICIPATED_USERS="event_participatedUsers"
    const val UPCOMING_EVENT="upcomingEvents"
    const val HISTORY_EVENT="historyEvents"
    const val FEEDBACK="feedback"
    const val OTHER_USER_PROFILE="other_userprofile"
    const val CHAT_LIST="chatlist"
    const val FORGOPASS="forgotpass"
    const val VERIFYOTP="verifyotp"
    const val RESETPASS="resetpass"
    const val EVENT_SEARCH="eventsearch"
    const val LOC_FILTER="loc_filter"

    const val SPORTSICONSSHOW="sportsiconshow"
    const val NOTIFICATION="notification"
    const val INBOX="inbox"
    const val USER_HOSTING_EVENTS="userhosting_events"

    const val REQUEST_CODE_CAMERA=1
    const val REQUEST_CODE_GALLERY=2

    //api endpoint methods
    const val SIGNUP = "register"
    const val SOCIAL_SIGNUP = "SOCIAL_REGISTER"
    const val GLOGIN = "login"
    const val GUEST_LOGIN = "guest_login"
    const val FORGOT_PASSWORD = "forgot_password"
    const val RESET_PASSWORD = "reset_password"
    const val GLOGOUT = "logout"
    const val NOTIFICATION_ON_OFF = "notification_On_Off"
    const val GET_NOTIFICATION = "notifications"

    //api post params
    const val FIRST_NAME="first_name"
    const val LAST_NAME="last_name"
    const val EMAIL="email"
    const val PASSWORD="password"
    const val CONFIRM_PASSWORD="confirm_password"
    const val PHONE_NUMBER="phone_number"
    const val DEVICE_ID="device_id"
    const val DEVICE_TOKEN="device_token"
    const val DEVICE_TYPE="device_type"
    const val SOCIAL_ID="social_id"
    const val SIGNUP_TYPE="signup_type"
    const val PAGE_NO="page_no"
}