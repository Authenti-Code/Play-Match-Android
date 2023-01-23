package com.playMatch.ui.home.dialogs.invitesDialog

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import com.playMatch.R
import com.playMatch.controller.`interface`.ButtonClickListener

class InvitesDialog (private val activity: Activity) {
    fun invitesDialog(buttonClickListener: ButtonClickListener) {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        // set custom layout
        dialog.setContentView(R.layout.dialog_invites)
        // set height and width
        val width = WindowManager.LayoutParams.MATCH_PARENT
        val height = WindowManager.LayoutParams.MATCH_PARENT
        // set to custom layout
        dialog.window?.setLayout(width, height)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val params: WindowManager.LayoutParams = dialog.window!!.attributes
        params.gravity = Gravity.CENTER
        // find TextView and set Error
        val cancel: ImageView = dialog.findViewById(R.id.cancel)
//        val sure: AppCompatButton = dialog.findViewById(R.id.sure)

//        sure .setOnClickListener {
//            CommonUtils.backPress(this.activity)
//
//        }
        cancel.setOnClickListener{
            dialog.dismiss()
        }
        dialog.show()
    }

}