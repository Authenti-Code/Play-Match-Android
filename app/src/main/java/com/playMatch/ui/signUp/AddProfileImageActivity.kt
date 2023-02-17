package com.playMatch.ui.signUp

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.playMatch.R
import com.playMatch.controller.playMatchAPi.ApiConstant
import com.playMatch.controller.playMatchAPi.ResultResponse
import com.playMatch.controller.playMatchAPi.apiClasses.UserApi
import com.playMatch.controller.sharedPrefrence.PrefData
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivityAddProfileImageBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.signUp.signupModel.UploadImageResponse
import com.soundcloud.android.crop.Crop
import java.io.ByteArrayOutputStream

class AddProfileImageActivity :  BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityAddProfileImageBinding
    private var name:String?=null

    private val PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        removeStatusBarFullyBlackIcon()
        super.onCreate(savedInstanceState)
        binding = ActivityAddProfileImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentData()
        initView()
    }
    private fun getIntentData() {

        if (intent?.extras != null) {
            name = intent.extras?.getString(PrefData.NAME,"")
        }

    }
    private fun initView() {
        binding.back.setOnClickListener(this)
        binding.Continue.setOnClickListener(this)
        binding.skip.setOnClickListener(this)
        binding.editProfile.setOnClickListener(this)
        binding.name.text=name
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back -> {
                onBackPressed()
            }
            R.id.Continue -> {
              uploadProfileImgApi()
            }
            R.id.skip -> {
                CommonUtils.performIntent(this, SelectSportActivity::class.java)
            }R.id.editProfile -> {
            selectImage()
            }
        }
    }

    private fun selectImage() {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@AddProfileImageActivity)
        builder.setTitle("Add Photo!")
        builder.setItems(options, DialogInterface.OnClickListener { dialog, item ->
            if (options[item] == "Take Photo") {

                try {
                    if (!hasPermissions(this@AddProfileImageActivity, *PERMISSIONS)) {
                        ActivityCompat.requestPermissions(
                            this@AddProfileImageActivity,
                            PERMISSIONS,
                            PERMISSION_ALL

                        )
                    } else {
                        openCamera()

                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else if (options[item] == "Choose from Gallery") {
                openGallery()
            } else if (options[item] == "Cancel") {
                dialog.dismiss()
            }
        })
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ApiConstant.REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            try {
                uri = Uri.fromFile(file)
                beginCrop(uri)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else if (requestCode == ApiConstant.REQUEST_CODE_GALLERY) {
            if (data != null) {
                uri = data.data
                beginCrop(uri)
            }
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data)
        }
    }


    @SuppressLint("ObsoleteSdkInt")
    private fun hasPermissions(context: Context?, vararg permissions: String): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }

    @SuppressLint("SetTextI18n")
    @Throws(java.lang.Exception::class)
    private fun handleCrop(resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            cropImage(Uri.fromFile(file).toString())

            if (file?.exists()!!) {
                file?.delete()
            }
            val idCurrentTime = System.currentTimeMillis()
            imageName = "$idCurrentTime.jpg"
            Glide.with(this@AddProfileImageActivity).load(mBitmapImage).into(binding.profileImage)
//            binding.profileImage.setImageBitmap(mBitmapImage)

        } else if (resultCode == Crop.RESULT_ERROR) {
            CommonUtils.hideProgressDialog()
            Toast.makeText(this, Crop.getError(data!!).message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadProfileImgApi() {
        showProgressBar()
        val bos = ByteArrayOutputStream()
        mBitmapImage?.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        val imageBytes = bos.toByteArray()
        lifecycleScope.launchWhenStarted {
            val resultResponse = UserApi(this@AddProfileImageActivity).uploadImage(
                this@AddProfileImageActivity, imageBytes, imageName.toString()
            )
            uploadImageResult(resultResponse)
        }
    }

    private fun uploadImageResult(resultResponse: ResultResponse) {
        return when (resultResponse) {
            is ResultResponse.Success<*> -> {
                val response = resultResponse.response as UploadImageResponse
                if (response.success == "true") {
                    CommonUtils.performIntent(this, SelectSportActivity::class.java)
                    hideProgressBar()
                } else {
                    showSnackBar(
                        binding.rootView, response.message
                    )
                    hideProgressBar()

                }
            }
            else -> {
                showError(resultResponse)
                hideProgressBar()

            }
        }
    }

    private fun showProgressBar(){
        binding.progressBar.visibility=View.VISIBLE
        binding.continueTv.visibility=View.GONE
    }

    private fun hideProgressBar(){
        binding.progressBar.visibility=View.GONE
        binding.continueTv.visibility=View.VISIBLE
    }
}