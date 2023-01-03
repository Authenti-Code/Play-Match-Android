package com.playMatch.controller.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.LocationManager
import android.os.Bundle
import android.view.Window
import android.widget.LinearLayout
import androidx.activity.result.ActivityResultLauncher
import com.playMatch.R

class CommonUtils {

    companion object {
        //intent action---------------

        var progressbarDialog: Dialog? = null

        fun performIntent(context: Activity, className: Class<*>) {
            val intent = Intent(context, className)
            context.startActivity(intent)
            context.overridePendingTransition(R.anim.enter_activity, R.anim.exit_activity)
        }

        fun performIntentActivityResult(
            context: Activity,
            className: Class<*>,
            intentConstant: Int
        ) {
            val intent = Intent(context, className)
            context.startActivityForResult(intent, intentConstant)
            context.overridePendingTransition(R.anim.enter_activity, R.anim.exit_activity)
        }


        fun performIntentActivityLauncher(
            context: Activity,
            className: Class<*>,
            intentConstant: Int,
            resultLauncher: ActivityResultLauncher<Intent>
        ) {
            val intent = Intent(context, className)
            resultLauncher.launch(intent)
            context.overridePendingTransition(R.anim.enter_activity, R.anim.exit_activity)
        }

        fun performIntentActivityLauncherWithBundle(
            context: Activity,
            className: Class<*>,
            intentConstant: Int,
            resultLauncher: ActivityResultLauncher<Intent>,
            bundle: Bundle?
        ) {
            val intent = Intent(context, className)
            if (bundle != null) {
                intent.putExtras(bundle)
            }
            resultLauncher.launch(intent)
            context.overridePendingTransition(R.anim.enter_activity, R.anim.exit_activity)
        }

        fun performIntentFinish(context: Activity, className: Class<*>) {
            val intent = Intent(context, className)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            context.overridePendingTransition(R.anim.enter_activity, R.anim.exit_activity)
            context.finish()
        }

        fun performIntentWithBundle(context: Activity, className: Class<*>, bundle: Bundle?) {

            val intent = Intent(context, className)
            if (bundle != null) {
                intent.putExtras(bundle)
            }
            context.startActivity(intent)
            context.overridePendingTransition(R.anim.enter_activity, R.anim.exit_activity)
        }

        fun performIntentWithBundleFinish(context: Activity, className: Class<*>, bundle: Bundle?) {

            val intent = Intent(context, className)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            if (bundle != null) {
                intent.putExtras(bundle)
            }
            context.startActivity(intent)
            context.overridePendingTransition(R.anim.enter_activity, R.anim.exit_activity)
            context.finish()
        }

        fun performIntentActivityResultBundle(
            context: Activity,
            className: Class<*>,
            intentConstant: Int,
            bundle: Bundle?
        ) {
            try {
                val intent = Intent(context, className)
                if (bundle != null) {
                    intent.putExtras(bundle)
                }
                context.startActivityForResult(intent, intentConstant)
                context.overridePendingTransition(R.anim.enter_activity, R.anim.exit_activity)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }



        fun showProgressDialog(activity: Activity) {
            progressbarDialog = Dialog(activity,R.style.DialogTheme)
            progressbarDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)

            progressbarDialog!!.setContentView(R.layout.custom_progressbar)

            progressbarDialog!!.window!!.setBackgroundDrawable(
                ColorDrawable(Color.TRANSPARENT)
            )
            progressbarDialog!!.window!!.setLayout(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
            )
            progressbarDialog!!.setCancelable(false)
            progressbarDialog?.show()
        }

        fun hideProgressDialog() {
            if (progressbarDialog != null && progressbarDialog!!.isShowing) {
                progressbarDialog!!.dismiss()
            }
        }

        fun performIntentActivityResultBundleFinish(
            context: Activity,
            className: Class<*>,
            intentConstant: Int,
            bundle: Bundle?
        ) {
            try {
                val intent = Intent(context, className)
                if (bundle != null) {
                    intent.putExtras(bundle)
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                context.startActivityForResult(intent, intentConstant)
                context.overridePendingTransition(R.anim.enter_activity, R.anim.exit_activity)
                context.finish()

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        fun performIntentFinishAffinity(context: Activity, className: Class<*>) {
            val intent = Intent(context, className)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            context.overridePendingTransition(R.anim.enter_activity, R.anim.exit_activity)
            context.finishAffinity()
        }

        //backpress
        fun backPress(context: Activity) {
            context.overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
            context.finish()
        }

        fun isGpsEnable(activity: Activity): Boolean {
            val mLocationManager =
                activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }
    }

}