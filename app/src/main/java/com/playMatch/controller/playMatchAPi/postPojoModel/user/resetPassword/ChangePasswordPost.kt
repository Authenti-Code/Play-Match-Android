package com.playMatch.controller.playMatchAPi.postPojoModel.user.resetPassword

data class ChangePasswordPost( val oldpass: String? = null,
                               val newpass: String? = null,
                               val confirmpass:String?=null)
