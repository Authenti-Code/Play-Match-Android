package com.playMatch.controller.playMatchAPi.postPojoModel.user.matchAvailability

import android.app.Notification

data class MatchAvailabilityPost(
    val distance: String? = null,
    val isAlwaysAvailable: String? = null,
    val sun: String? = null,
    val mon: String? = null,
    val tue: String? = null,
    val wed: String? = null,
    val thu: String? = null,
    val fri: String? = null,
    val sat: String?= null,
    val toNotify: String?= null,
)

