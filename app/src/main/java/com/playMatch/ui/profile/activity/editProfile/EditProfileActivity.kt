package com.playMatch.ui.profile.activity.editProfile

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.google.android.material.slider.RangeSlider
import com.playMatch.R
import com.playMatch.controller.playMatchAPi.ApiConstant
import com.playMatch.controller.playMatchAPi.ResultResponse
import com.playMatch.controller.playMatchAPi.apiClasses.UserApi
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivityEditProfileBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.home.model.HomeChildModel
import com.playMatch.ui.profile.activity.editSportsAbility.EditSportsActivity
import com.playMatch.ui.profile.adapter.ProfileSportsAdapter
import com.playMatch.ui.profile.model.profile.ProfileResponse
import com.soundcloud.android.crop.Crop
import kotlin.math.roundToInt

class EditProfileActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityEditProfileBinding
    private var profileSportsAdapter: ProfileSportsAdapter? = null
    private var list = ArrayList<HomeChildModel>()
    private var sun:String?=null
    private var mon:String?=null
    private var tue:String?=null
    private var wed:String?=null
    private var thu:String?=null
    private var fri:String?=null
    private var sat:String?=null
    private var SCvColor:Boolean=true
    private var MCvColor:Boolean=true
    private var TCvColor:Boolean=true
    private var WCvColor:Boolean=true
    private var ThCvColor:Boolean=true
    private var FCvColor:Boolean=true
    private var SACvColor:Boolean=true

    private val PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        removeStatusBarFullyBlackIcon()
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        setAdapter()
        profileApi()
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        binding.back.setOnClickListener(this)
        binding.update.setOnClickListener(this)
        binding.beginner.setOnClickListener(this)
        binding.intermediate.setOnClickListener(this)
        binding.experienced.setOnClickListener(this)
        binding.editSports.setOnClickListener(this)
        binding.editImage.setOnClickListener(this)
        binding.mondaySlider.setValues(0.0f,5.0f)
        binding.tuesdaySlider.setValues(0.0f,5.0f)
        binding.Stv.setOnClickListener(this)
        binding.Mtv.setOnClickListener(this)
        binding.Ttv.setOnClickListener(this)
        binding.Wtv.setOnClickListener(this)
        binding.Thtv.setOnClickListener(this)
        binding.Ftv.setOnClickListener(this)
        binding.Satv.setOnClickListener(this)

        binding.sundaySlider.setLabelFormatter { value: Float ->
            return@setLabelFormatter if (value.roundToInt() >= 12){"${value.roundToInt()}:00 pm"}else{
                "${value.roundToInt()}:00 am"
            }
        }
        binding.sundaySlider.setValueFrom(0f).toString()
        binding.sundaySlider.setValues(0f,23f).toString()
        binding.sundaySlider.setValueTo(23f).toString()

        binding.sundaySlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: RangeSlider) {
                val values = binding.sundaySlider.values
                //Those are the satrt and end values of sldier when user start dragging
                Log.i("SliderPreviousValue From", values[0].toString())
                Log.i("SliderPreviousValue To", values[1].toString())
            }

            @SuppressLint("SetTextI18n")
            override fun onStopTrackingTouch(slider: RangeSlider) {
                val values = binding.sundaySlider.values
                val id:String
                var newId:String
                //Those are the new updated values of sldier when user has finshed dragging
                Log.i("SliderNewValue From", values[0].toString())
                Log.i("SliderNewValue To", values[1].toString())
                if (values[0].toFloat().roundToInt() >= 12){ id="${values[0].roundToInt()}:00 pm"}else {
                    id="${values[0].roundToInt()}:00 am"
                }
                if (values[1].toFloat().roundToInt() >= 12){ newId="${values[1].roundToInt()}:00 pm"}else {
                    newId="${values[1].roundToInt()}:00 am"
                }

                if (values[1].toFloat().roundToInt() >= 23){ newId="${values[1].roundToInt()}:59 pm"}else {
                    newId="${values[1].roundToInt()}:00 am"
                }

                binding.Sstv.text = "$id - $newId"
                sun=binding.Sstv.text.toString().trim()

            }
        })
        binding.sundaySlider.addOnChangeListener { slider, value, fromUser ->
            val values =  binding.sundaySlider.values
            val id:String
            var newId:String

            if (values[0].toFloat().roundToInt() >= 12){ id="${values[0].roundToInt()}:00 pm"}else {
                id="${values[0].roundToInt()}:00 am"
            }
            if (values[1].toFloat().roundToInt() >= 12){ newId="${values[1].roundToInt()}:00 pm"}else {
                newId="${values[1].roundToInt()}:00 am"
            }
            if (values[1].toFloat().roundToInt() >= 23){ newId="${values[1].roundToInt()}:59 pm"}else {
                newId="${values[1].roundToInt()}:00 am"
            }
            binding.Sstv.text ="$id - $newId"
            sun=binding.Sstv.text.toString().trim()
        }


        binding.mondaySlider.setLabelFormatter { value: Float ->
            return@setLabelFormatter if (value.roundToInt() >= 12){"${value.roundToInt()}:00 pm"}else{
                "${value.roundToInt()}:00 am"
            }
        }
        binding.mondaySlider.setValueFrom(0f).toString()
        binding.mondaySlider.setValues(0f,23f).toString()
        binding.mondaySlider.setValueTo(23f).toString()

        binding.mondaySlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: RangeSlider) {
                val values = binding.mondaySlider.values
                //Those are the start and end values of slider when user start dragging
                Log.i("SliderPreviousValue From", values[0].toString())
                Log.i("SliderPreviousValue To", values[1].toString())
            }

            @SuppressLint("SetTextI18n")
            override fun onStopTrackingTouch(slider: RangeSlider) {
                val values = binding.mondaySlider.values
                val id:String
                val newId:String
                //Those are the new updated values of sldier when user has finshed dragging
                Log.i("SliderNewValue From", values[0].toString())
                Log.i("SliderNewValue To", values[1].toString())
                if (values[0].toFloat().roundToInt() >= 12){ id="${values[0].roundToInt()}:00 pm"}else {
                    id="${values[0].roundToInt()}:00 am"
                }
                if (values[1].toFloat().roundToInt() >= 12){ newId="${values[1].roundToInt()}:00 pm"}else {
                    newId="${values[1].roundToInt()}:00 am"
                }
                binding.Mstv.text = "$id - $newId"
                mon= binding.Mstv.text.toString().trim()
            }
        })
        binding.mondaySlider.addOnChangeListener { slider, value, fromUser ->
            val values =  binding.mondaySlider.values
            val id:String
            val newId:String

            if (values[0].toFloat().roundToInt() >= 12){ id="${values[0].roundToInt()}:00pm"}else {
                id="${values[0].roundToInt()}:00am"
            }
            if (values[1].toFloat().roundToInt() >= 12){ newId="${values[1].roundToInt()}:00 pm"}else {
                newId="${values[1].roundToInt()}:00 am"
            }
            binding.Mstv.text ="$id - $newId"
            mon= binding.Mstv.text.toString().trim()
        }


        binding.tuesdaySlider.setLabelFormatter { value: Float ->
            return@setLabelFormatter if (value.roundToInt() >= 12){"${value.roundToInt()}:00 pm"}else{
                "${value.roundToInt()}:00 am"
            }
        }
        binding.tuesdaySlider.setValueFrom(0f).toString()
        binding.tuesdaySlider.setValues(0f,24f).toString()
        binding.tuesdaySlider.setValueTo(24f).toString()

        binding.tuesdaySlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: RangeSlider) {
                val values = binding.tuesdaySlider.values
                //Those are the satrt and end values of sldier when user start dragging
                Log.i("SliderPreviousValue From", values[0].toString())
                Log.i("SliderPreviousValue To", values[1].toString())
            }

            @SuppressLint("SetTextI18n")
            override fun onStopTrackingTouch(slider: RangeSlider) {
                val values = binding.tuesdaySlider.values
                val id:String
                var newId:String
                //Those are the new updated values of sldier when user has finshed dragging
                Log.i("SliderNewValue From", values[0].toString())
                Log.i("SliderNewValue To", values[1].toString())
                if (values[0].toFloat().roundToInt()==12){ id="${values[0].roundToInt()}:00 pm"}else {
                    id="${values[0].roundToInt()}:00am"
                }
                if (values[1].toFloat().roundToInt()>=12){ newId="${values[1].roundToInt()}:00 pm"}else {
                    newId="${values[1].roundToInt()}:00 am"
                }
                if (values[1].toFloat().roundToInt() >= 23){ newId="${values[1].roundToInt()}:59 pm"}
                binding.Tstv.text = "$id - $newId"
                tue= binding.Tstv.text.toString().trim()

            }
        })
        binding.tuesdaySlider.addOnChangeListener { slider, value, fromUser ->
            val values =  binding.tuesdaySlider.values
            val id:String
            val newId:String

            if (values[0].toFloat().roundToInt()>=12){ id="${values[0].roundToInt()}:00 pm"}else {
                id="${values[0].roundToInt()}:00 am"
            }
            if (values[1].toFloat().roundToInt()>=12){ newId="${values[1].roundToInt()}:00 pm"}else {
                newId="${values[1].roundToInt()}:00 am"
            }
            binding.Tstv.text = "$id - $newId"
            tue= binding.Tstv.text.toString().trim()
        }

        binding.wednesdaySlider.setLabelFormatter { value: Float ->
            return@setLabelFormatter if (value.roundToInt()>=12){"${value.roundToInt()}:00 pm"}else{
                "${value.roundToInt()}:00 am"
            }
        }
        binding.wednesdaySlider.setValueFrom(0f).toString()
        binding.wednesdaySlider.setValues(0f,23f).toString()
        binding.wednesdaySlider.setValueTo(23f).toString()

        binding.wednesdaySlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: RangeSlider) {
                val values = binding.wednesdaySlider.values
                //Those are the satrt and end values of sldier when user start dragging
                Log.i("SliderPreviousValue From", values[0].toString())
                Log.i("SliderPreviousValue To", values[1].toString())
            }

            @SuppressLint("SetTextI18n")
            override fun onStopTrackingTouch(slider: RangeSlider) {
                val values = binding.wednesdaySlider.values
                val id:String
                val newId:String
                //Those are the new updated values of sldier when user has finshed dragging
                Log.i("SliderNewValue From", values[0].toString())
                Log.i("SliderNewValue To", values[1].toString())
                if (values[0].toFloat().roundToInt() >= 12){ id="${values[0].roundToInt()}:00pm"}else {
                    id="${values[0].roundToInt()}:00am"
                }
                if (values[1].toFloat().roundToInt() >= 12){ newId="${values[1].roundToInt()}:00pm"}else {
                    newId="${values[1].roundToInt()}:00am"
                }

                binding.Wstv.text = "$id - $newId"
                wed= binding.Wstv.text.toString().trim()
            }
        })
        binding.wednesdaySlider.addOnChangeListener { slider, value, fromUser ->
            val values =  binding.wednesdaySlider.values
            val id:String
            val newId:String

            if (values[0].toFloat().roundToInt() >= 12){ id="${values[0].roundToInt()}:00pm"}else {
                id="${values[0].roundToInt()}:00am"
            }
            if (values[1].toFloat().roundToInt() >= 12){ newId="${values[1].roundToInt()}:00pm"}else {
                newId="${values[1].roundToInt()}:00am"
            }
            binding.Wstv.text ="$id - $newId"
            wed= binding.Wstv.text.toString().trim()
        }

        binding.thursdaySlider.setLabelFormatter { value: Float ->
            return@setLabelFormatter if (value.roundToInt()>=12){"${value.roundToInt()}:00pm"}else{
                "${value.roundToInt()}:00am"
            }
        }
        binding.thursdaySlider.setValueFrom(0f).toString()
        binding.thursdaySlider.setValues(0f,23f).toString()
        binding.thursdaySlider.setValueTo(23f).toString()

        binding.thursdaySlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: RangeSlider) {
                val values = binding.thursdaySlider.values
                //Those are the satrt and end values of sldier when user start dragging
                Log.i("SliderPreviousValue From", values[0].toString())
                Log.i("SliderPreviousValue To", values[1].toString())
            }

            @SuppressLint("SetTextI18n")
            override fun onStopTrackingTouch(slider: RangeSlider) {
                val values = binding.thursdaySlider.values
                val id:String
                val newId:String
                //Those are the new updated values of sldier when user has finshed dragging
                Log.i("SliderNewValue From", values[0].toString())
                Log.i("SliderNewValue To", values[1].toString())
                if (values[0].toFloat().roundToInt() >= 12){ id="${values[0].roundToInt()}:00pm"}else {
                    id="${values[0].roundToInt()}:00am"
                }
                if (values[1].toFloat().roundToInt() >= 12){ newId="${values[1].roundToInt()}:00pm"}else {
                    newId="${values[1].roundToInt()}:00am"
                }

                binding.Thstv.text = "$id - $newId"
                thu= binding.Thstv.text.toString().trim()

            }
        })
        binding.thursdaySlider.addOnChangeListener { slider, value, fromUser ->
            val values =  binding.thursdaySlider.values
            val id:String
            val newId:String

            if (values[0].toFloat().roundToInt() >= 12){ id="${values[0].roundToInt()}:00pm"}else {
                id="${values[0].roundToInt()}:00am"
            }
            if (values[1].toFloat().roundToInt() >= 12){ newId="${values[1].roundToInt()}:00pm"}else {
                newId="${values[1].roundToInt()}:00am"
            }
            binding.Thstv.text ="$id - $newId"
            thu= binding.Thstv.text.toString().trim()

        }

        binding.fridaySlider.setLabelFormatter { value: Float ->
            return@setLabelFormatter if (value.roundToInt()>=12){"${value.roundToInt()}:00pm"}else{
                "${value.roundToInt()}:00am"
            }
        }
        binding.fridaySlider.setValueFrom(0f).toString()
        binding.fridaySlider.setValues(0f,23f).toString()
        binding.fridaySlider.setValueTo(23f).toString()

        binding.fridaySlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: RangeSlider) {
                val values = binding.fridaySlider.values
                //Those are the satrt and end values of sldier when user start dragging
                Log.i("SliderPreviousValue From", values[0].toString())
                Log.i("SliderPreviousValue To", values[1].toString())
            }

            @SuppressLint("SetTextI18n")
            override fun onStopTrackingTouch(slider: RangeSlider) {
                val values = binding.fridaySlider.values
                val id:String
                val newId:String
                //Those are the new updated values of sldier when user has finshed dragging
                Log.i("SliderNewValue From", values[0].toString())
                Log.i("SliderNewValue To", values[1].toString())
                if (values[0].toFloat().roundToInt() >= 12){ id="${values[0].roundToInt()}:00pm"}else {
                    id="${values[0].roundToInt()}:00am"
                }
                if (values[1].toFloat().roundToInt() >= 12){ newId="${values[1].roundToInt()}:00pm"}else {
                    newId="${values[1].roundToInt()}:00am"
                }

                binding.Fstv.text = "$id - $newId"
                fri= binding.Fstv.text.toString().trim()

            }
        })
        binding.fridaySlider.addOnChangeListener { slider, value, fromUser ->
            val values =  binding.fridaySlider.values
            val id:String
            val newId:String

            if (values[0].toFloat().roundToInt() >= 12){ id="${values[0].roundToInt()}:00pm"}else {
                id="${values[0].roundToInt()}:00am"
            }
            if (values[1].toFloat().roundToInt() >= 12){ newId="${values[1].roundToInt()}:00pm"}else {
                newId="${values[1].roundToInt()}:00am"
            }
            binding.Fstv.text ="$id - $newId"
            fri= binding.Fstv.text.toString().trim()
        }

        binding.saturdaySlider.setLabelFormatter { value: Float ->
            return@setLabelFormatter if (value.roundToInt()>=12){"${value.roundToInt()}:00pm"}else{
                "${value.roundToInt()}:00am"
            }
        }
        binding.saturdaySlider.setValueFrom(0f).toString()
        binding.saturdaySlider.setValues(0f,23f).toString()
        binding.saturdaySlider.setValueTo(23f).toString()

        binding.saturdaySlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: RangeSlider) {
                val values = binding.fridaySlider.values
                //Those are the satrt and end values of sldier when user start dragging
                Log.i("SliderPreviousValue From", values[0].toString())
                Log.i("SliderPreviousValue To", values[1].toString())
            }

            @SuppressLint("SetTextI18n")
            override fun onStopTrackingTouch(slider: RangeSlider) {
                val values = binding.saturdaySlider.values
                val id:String
                val newId:String
                //Those are the new updated values of sldier when user has finshed dragging
                Log.i("SliderNewValue From", values[0].toString())
                Log.i("SliderNewValue To", values[1].toString())
                if (values[0].toFloat().roundToInt() >= 12){ id="${values[0].roundToInt()}:00pm"}else {
                    id="${values[0].roundToInt()}:00am"
                }
                if (values[1].toFloat().roundToInt() >= 12){ newId="${values[1].roundToInt()}:00pm"}else {
                    newId="${values[1].roundToInt()}:00am"
                }

                binding.Sastv.text = "$id - $newId"
                sat= binding.Sastv.text.toString().trim()

            }
        })
        binding.saturdaySlider.addOnChangeListener { slider, value, fromUser ->
            val values =  binding.saturdaySlider.values
            val id:String
            val newId:String

            if (values[0].toFloat().roundToInt() >= 12){ id="${values[0].roundToInt()}:00pm"}else {
                id="${values[0].roundToInt()}:00am"
            }
            if (values[1].toFloat().roundToInt() >= 12){ newId="${values[1].roundToInt()}:00pm"}else {
                newId="${values[1].roundToInt()}:00am"
            }
            binding.Sastv.text ="$id - $newId"
            sat= binding.Sastv.text.toString().trim()

        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back -> {
                onBackPressed()
            }
            R.id.update -> {
                onBackPressed()
            }
            R.id.editSports -> {
                CommonUtils.performIntent(this,EditSportsActivity::class.java)
            }
            R.id.editImage -> {
                selectImage()
            }

            R.id.beginner -> {
                binding.beginnerCheck.visibility=View.VISIBLE
                binding.intermediateCheck.visibility=View.GONE
                binding.experiencedCheck.visibility=View.GONE
            }
            R.id.intermediate -> {
                binding.beginnerCheck.visibility=View.GONE
                binding.intermediateCheck.visibility=View.VISIBLE
                binding.experiencedCheck.visibility=View.GONE
            }
            R.id.experienced -> {
                binding.beginnerCheck.visibility=View.GONE
                binding.intermediateCheck.visibility=View.GONE
                binding.experiencedCheck.visibility=View.VISIBLE
            }
            R.id.Stv -> {
                if (SCvColor==true){
                    binding.Scv.setCardBackgroundColor(Color.parseColor("#F95047"))
                    binding.Stv.setTextColor(Color.WHITE)
                    binding.Slay.visibility=View.VISIBLE
                    SCvColor=false
                    sun=binding.Sstv.text.toString()

                }else{
                    binding.Scv.setCardBackgroundColor(Color.WHITE)
                    binding.Stv.setTextColor(Color.parseColor("#F95047"))
                    SCvColor=true
                    binding.Slay.visibility=View.GONE
                    sun=""
                }
            }
            R.id.Mtv -> {
                if (MCvColor==true){
                    binding.Mcv.setCardBackgroundColor(Color.parseColor("#F95047"))
                    binding.Mtv.setTextColor(Color.WHITE)
                    binding.Mlay.visibility=View.VISIBLE
                    MCvColor=false
                    mon=binding.Mstv.text.toString()


                }else{
                    binding.Mcv.setCardBackgroundColor(Color.WHITE)
                    binding.Mtv.setTextColor(Color.parseColor("#F95047"))
                    binding.Mlay.visibility=View.GONE
                    MCvColor=true
                    mon=""
                }
            }
            R.id.Ttv -> {
                if (TCvColor==true){
                    binding.Tcv.setCardBackgroundColor(Color.parseColor("#F95047"))
                    binding.Ttv.setTextColor(Color.WHITE)
                    binding.Tlay.visibility=View.VISIBLE
                    TCvColor=false
                    tue=binding.Tstv.text.toString()

                }else{
                    binding.Tcv.setCardBackgroundColor(Color.WHITE)
                    binding.Ttv.setTextColor(Color.parseColor("#F95047"))
                    binding.Tlay.visibility=View.GONE
                    TCvColor=true
                    tue=""
                }
            }
            R.id.Wtv -> {
                if (WCvColor==true){
                    binding.Wcv.setCardBackgroundColor(Color.parseColor("#F95047"))
                    binding.Wtv.setTextColor(Color.WHITE)
                    binding.Wlay.visibility=View.VISIBLE
                    WCvColor=false
                    wed=binding.Wstv.text.toString()


                }else{
                    binding.Wcv.setCardBackgroundColor(Color.WHITE)
                    binding.Wtv.setTextColor(Color.parseColor("#F95047"))
                    binding.Wlay.visibility=View.GONE
                    WCvColor=true
                    wed=""
                }
            }
            R.id.Thtv -> {
                if (ThCvColor==true){
                    binding.Thcv.setCardBackgroundColor(Color.parseColor("#F95047"))
                    binding.Thtv.setTextColor(Color.WHITE)
                    binding.Thlay.visibility=View.VISIBLE
                    ThCvColor=false
                    thu=binding.Thstv.text.toString()

                }else{
                    binding.Thcv.setCardBackgroundColor(Color.WHITE)
                    binding.Thtv.setTextColor(Color.parseColor("#F95047"))
                    binding.Thlay.visibility=View.GONE
                    ThCvColor=true
                    thu=""
                }
            }
            R.id.Ftv -> {
                if (FCvColor==true){
                    binding.Fcv.setCardBackgroundColor(Color.parseColor("#F95047"))
                    binding.Ftv.setTextColor(Color.WHITE)
                    binding.Flay.visibility=View.VISIBLE
                    FCvColor=false
                    fri=binding.Fstv.text.toString()

                }else{
                    binding.Fcv.setCardBackgroundColor(Color.WHITE)
                    binding.Ftv.setTextColor(Color.parseColor("#F95047"))
                    binding.Flay.visibility=View.GONE
                    FCvColor=true
                    fri=""
                }
            }
            R.id.Satv -> {
                if (SACvColor==true){
                    binding.Sacv.setCardBackgroundColor(Color.parseColor("#F95047"))
                    binding.Satv.setTextColor(Color.WHITE)
                    binding.SaLay.visibility=View.VISIBLE
                    SACvColor=false
                    sat=binding.Sastv.text.toString()

                }else{
                    binding.Sacv.setCardBackgroundColor(Color.WHITE)
                    binding.Satv.setTextColor(Color.parseColor("#F95047"))
                    binding.SaLay.visibility=View.GONE
                    SACvColor=true
                    sat=""
                }
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun setAdapter() {
        profileSportsAdapter = ProfileSportsAdapter(list, this)
        binding.rvSports.adapter = profileSportsAdapter

        list.clear()
        for (i in 1..5) {
            list.add(
                HomeChildModel(
                    R.drawable.ic_league_match, "Cricket"
                )
            )
        }
    }

    private fun selectImage() {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@EditProfileActivity)
        builder.setTitle("Add Photo!")
        builder.setItems(options, DialogInterface.OnClickListener { dialog, item ->
            if (options[item] == "Take Photo") {

                try {
                    if (!hasPermissions(this@EditProfileActivity, *PERMISSIONS)) {
                        ActivityCompat.requestPermissions(
                            this@EditProfileActivity,
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
            Glide.with(this@EditProfileActivity).load(mBitmapImage).into(binding.profileImage)
//            binding.profileImage.setImageBitmap(mBitmapImage)

        } else if (resultCode == Crop.RESULT_ERROR) {
            CommonUtils.hideProgressDialog()
            Toast.makeText(this, Crop.getError(data!!).message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun profileApi(){

        if (isNetworkAvailable()) {
            CommonUtils.showProgressDialog(this)
            lifecycleScope.launchWhenStarted {
                val resultResponse = UserApi(this@EditProfileActivity).profile()
                apiResult(resultResponse)
            }
        } else {
            showNetworkSpeedError()
        }
    }


    private fun apiResult(resultResponse: ResultResponse) {
        CommonUtils.hideProgressDialog()
        return when (resultResponse) {
            is ResultResponse.Success<*> -> {
                val response = resultResponse.response as ProfileResponse
                //get data and convert string to json and save data
                if (response.success == "true") {
                    Glide.with(this@EditProfileActivity)
                        .load("http://"+response.data.image)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .skipMemoryCache(true)
                        .priority(Priority.IMMEDIATE)
                        .placeholder(R.drawable.new_dummy_profile)
                        .listener(object : RequestListener<Drawable> {
                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: com.bumptech.glide.request.target.Target<Drawable>?,
                                dataSource: com.bumptech.glide.load.DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                binding?.progressBar?.visibility = View.GONE
                                return false
                            }

                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: com.bumptech.glide.request.target.Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                binding?.progressBar?.visibility = View.GONE
                                return false
                            }

                        }).into(binding!!.profileImage)

                    binding.name.setText(response.data.name)
                    binding.dob.setText(response.data.DOB)
                    binding.gender.setText(response.data.gender)
                    binding.address.setText(response.data.location)

                    if (response.data.fitnessLevel=="Beginner"){
                        binding.beginnerCheck.visibility=View.VISIBLE
                    }else{
                        binding.beginnerCheck.visibility=View.GONE
                    }
                    if (response.data.fitnessLevel=="Intermediate"){
                        binding.intermediateCheck.visibility=View.VISIBLE
                    }else{
                        binding.intermediateCheck.visibility=View.GONE
                    }
                    if (response.data.fitnessLevel=="Experienced"){
                        binding.experiencedCheck.visibility=View.VISIBLE
                    }else{
                        binding.experiencedCheck.visibility=View.GONE
                    }

                    if (response.data.sun!= ""){
                        binding.Scv.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding.Stv.setTextColor(Color.WHITE)
                        binding.Slay.visibility=View.VISIBLE
                        binding.Sstv.text=response.data.sun
                        val strs = response.data.sun.split("-").toTypedArray()
                        val t1=strs[0]
                        val t2=strs[1]
                        val timePartsStart = t1.split(":")
                        val timePartsEnd = t2.split(":")
                        val start = timePartsStart[0].toFloat()
                        val end = timePartsEnd[0].toFloat()

                        binding.tuesdaySlider.setValues(start,end)
                    }
                    else{
                        binding.Slay.visibility=View.GONE
                    }
                    if (response.data.mon!= ""){
                        binding.Mcv.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding.Mtv.setTextColor(Color.WHITE)
                        binding.Mlay.visibility=View.VISIBLE
                                binding.Mstv.text=response.data.sun
                        val strs = response.data.sun.split("-").toTypedArray()
                        val t1=strs[0]
                        val t2=strs[1]
                        val timePartsStart = t1.split(":")
                        val timePartsEnd = t2.split(":")
                        val start = timePartsStart[0].toFloat()
                        val end = timePartsEnd[0].toFloat()

                        binding.mondaySlider.setValues(start,end)
                    }
                    else{
                        binding.Mlay.visibility=View.GONE
                    }
                    if (response.data.tue!= ""){
                        binding.Tcv.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding.Ttv.setTextColor(Color.WHITE)
                        binding.Tlay.visibility=View.VISIBLE
                        binding.Tstv.text=response.data.tue
                        val strs = response.data.tue.split("-").toTypedArray()
                        val t1=strs[0]
                        val t2=strs[1]
                        val timePartsStart = t1.split(":")
                        val timePartsEnd = t2.split(":")
                        val start = timePartsStart[0].toFloat()
                        val end = timePartsEnd[0].toFloat()

                        binding.tuesdaySlider.setValues(start,end)
                    } else{
                        binding.Tlay.visibility=View.GONE
                    }
                    if (response.data.wed!= ""){
                        binding.Wcv.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding.Wtv.setTextColor(Color.WHITE)
                        binding.Wlay.visibility=View.VISIBLE
                        binding.Wstv.text=response.data.tue
                        val strs = response.data.tue.split("-").toTypedArray()
                        val t1=strs[0]
                        val t2=strs[1]
                        val timePartsStart = t1.split(":")
                        val timePartsEnd = t2.split(":")
                        val start = timePartsStart[0].toFloat()
                        val end = timePartsEnd[0].toFloat()

                        binding.wednesdaySlider.setValues(start,end)
                    } else{
                        binding.Wlay.visibility=View.GONE
                    }
                    if (response.data.thu!= ""){
                        binding.Thcv.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding.Thtv.setTextColor(Color.WHITE)
                        binding.Thlay.visibility=View.VISIBLE
                        binding.Thstv.text=response.data.tue
                        val strs = response.data.tue.split("-").toTypedArray()
                        val t1=strs[0]
                        val t2=strs[1]
                        val timePartsStart = t1.split(":")
                        val timePartsEnd = t2.split(":")
                        val start = timePartsStart[0].toFloat()
                        val end = timePartsEnd[0].toFloat()

                        binding.thursdaySlider.setValues(start,end)
                    } else{
                        binding.Thlay.visibility=View.GONE
                    }
                    if (response.data.fri != ""){
                        binding.Fcv.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding.Ftv.setTextColor(Color.WHITE)
                        binding.Flay.visibility=View.VISIBLE
                        binding.Fstv.text=response.data.tue
                        val strs = response.data.tue.split("-").toTypedArray()
                        val t1=strs[0]
                        val t2=strs[1]
                        val timePartsStart = t1.split(":")
                        val timePartsEnd = t2.split(":")
                        val start = timePartsStart[0].toFloat()
                        val end = timePartsEnd[0].toFloat()

                        binding.fridaySlider.setValues(start,end)
                    } else{
                        binding.Flay.visibility=View.GONE
                    }
                    if (response.data.sat!= ""){
                        binding.Sacv.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding.Satv.setTextColor(Color.WHITE)
                        binding.SaLay.visibility=View.VISIBLE
                        binding.Sastv.text=response.data.tue
                        val strs = response.data.tue.split("-").toTypedArray()
                        val t1=strs[0]
                        val t2=strs[1]
                        val timePartsStart = t1.split(":")
                        val timePartsEnd = t2.split(":")
                        val start = timePartsStart[0].toFloat()
                        val end = timePartsEnd[0].toFloat()

                        binding.saturdaySlider.setValues(start,end)
                    } else{
                        binding.SaLay.visibility=View.GONE
                    }

                } else {
                    showSnackBar(findViewById(R.id.rootView), response.message)
                }
            }
            else -> {
                showError(resultResponse)
            }
        } as Unit
    }
}