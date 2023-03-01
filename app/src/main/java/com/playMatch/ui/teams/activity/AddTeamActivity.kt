package com.playMatch.ui.teams.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.slider.RangeSlider
import com.playMatch.R
import com.playMatch.controller.`interface`.BottomSheetListner
import com.playMatch.controller.playMatchAPi.ApiConstant
import com.playMatch.controller.playMatchAPi.ResultResponse
import com.playMatch.controller.playMatchAPi.apiClasses.UserApi
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivityAddTeamBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.home.model.HomeChildModel
import com.playMatch.ui.location.activity.LocationActivity
import com.playMatch.ui.matches.adapter.selectSportAdapter.SelectMatchSportAdapter
import com.playMatch.controller.sharedPrefrence.PrefData
import com.playMatch.ui.signUp.signupModel.SportListResponse
import com.playMatch.ui.signUp.signupModel.SportsList
import com.soundcloud.android.crop.Crop
import kotlin.math.roundToInt

class AddTeamActivity : BaseActivity(), View.OnClickListener, BottomSheetListner {
    private lateinit var binding: ActivityAddTeamBinding
    private var CvColor:Boolean=true
    private var selectSportAdapter: SelectMatchSportAdapter? = null
    private var seletedBottomSheet: BottomSheetDialog? = null
    private var list = ArrayList<SportsList>()

    //String
    private var sun:String?=""
    private var mon:String?=""
    private var tue:String?=""
    private var wed:String?=""
    private var thu:String?=""
    private var fri:String?=""
    private var sat:String?=""
    private var fitnessLevel:String?="Intermediate"

    //boolean
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
        binding = ActivityAddTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentData()
        initView()
    }

    private fun getIntentData() {

        if (intent?.extras != null) {
            type = intent.extras?.getString(PrefData.CURRENT_USER_SCREEN_TYPE, "")
        }
    }

    private fun initView() {
        binding.back.setOnClickListener(this)
        binding.Stv.setOnClickListener(this)
        binding.Mtv.setOnClickListener(this)
        binding.Ttv.setOnClickListener(this)
        binding.Wtv.setOnClickListener(this)
        binding.Thtv.setOnClickListener(this)
        binding.Ftv.setOnClickListener(this)
        binding.Satv.setOnClickListener(this)
        binding.addImage.setOnClickListener(this)
        binding.intermediate.setOnClickListener(this)
        binding.beginner.setOnClickListener(this)
        binding.experienced.setOnClickListener(this)
        binding.selectSport.setOnClickListener(this)
        binding.kitYes.setOnClickListener(this)
        binding.kitNo.setOnClickListener(this)
        binding.awayYes.setOnClickListener(this)
        binding.awayNo.setOnClickListener(this)
        binding.createTeam.setOnClickListener(this)
        binding.location.setOnClickListener(this)
        binding.sundaySlider.setLabelFormatter { value: Float ->
            return@setLabelFormatter if (value.roundToInt() in 12..23) {
                "${value.roundToInt()}:00 pm"
            } else if (value.roundToInt() == 24) {
                "23:59 pm"
            } else {
                "${value.roundToInt()}:00 am"
            }
        }
        binding.sundaySlider.setValueFrom(0f).toString()
        binding.sundaySlider.setValues(0f,24f).toString()
        binding.sundaySlider.setValueTo(24f).toString()
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
                var id:String
                var newId:String
                //Those are the new updated values of sldier when user has finshed dragging
                Log.i("SliderNewValue From", values[0].toString())
                Log.i("SliderNewValue To", values[1].toString())
                if (values[0].toFloat().roundToInt()>=12){ id="${values[0].roundToInt()}:00 pm"}else {
                    id="${values[0].roundToInt()}:00am"
                }

                if (values[0].toFloat().roundToInt() == 24){ id="23:59 pm"}

                if (values[1].toFloat().roundToInt()>=12){ newId="${values[1].roundToInt()}:00 pm"}else {
                    newId="${values[1].roundToInt()}:00 am"
                }
                if (values[1].toFloat().roundToInt() >= 24){ newId="23:59 pm"}
                binding.Sstv.text = "$id - $newId"
                sun=binding.Sstv.text.toString().trim()

            }
        })
        binding.sundaySlider.addOnChangeListener { slider, value, fromUser ->
            val values =  binding.sundaySlider.values
            var id:String
            var newId:String

            if (values[0].toFloat().roundToInt()>=12){ id="${values[0].roundToInt()}:00 pm"}else {
                id="${values[0].roundToInt()}:00 am"
            }
            if (values[0].toFloat().roundToInt() == 24){ id="23:59 pm"}

            if (values[1].toFloat().roundToInt()>=12){ newId="${values[1].roundToInt()}:00 pm"}else {
                newId="${values[1].roundToInt()}:00 am"
            }
            if (values[1].toFloat().roundToInt() == 24){ newId="23:59 pm"}
            binding.Sstv.text ="$id - $newId"
            sun=binding.Sstv.text.toString().trim()
        }


        binding.mondaySlider.setLabelFormatter { value: Float ->
            return@setLabelFormatter if (value.roundToInt() in 12..23) {
                "${value.roundToInt()}:00 pm"
            } else if (value.roundToInt() == 24) {
                "23:59 pm"
            } else {
                "${value.roundToInt()}:00 am"
            }
        }
        binding.mondaySlider.setValueFrom(0f).toString()
        binding.mondaySlider.setValues(0f,24f).toString()
        binding.mondaySlider.setValueTo(24f).toString()

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
                var id:String
                var newId:String
                //Those are the new updated values of sldier when user has finshed dragging
                Log.i("SliderNewValue From", values[0].toString())
                Log.i("SliderNewValue To", values[1].toString())
                if (values[0].toFloat().roundToInt()>=12){ id="${values[0].roundToInt()}:00 pm"}else {
                    id="${values[0].roundToInt()}:00am"
                }

                if (values[0].toFloat().roundToInt() == 24){ id="23:59 pm"}

                if (values[1].toFloat().roundToInt()>=12){ newId="${values[1].roundToInt()}:00 pm"}else {
                    newId="${values[1].roundToInt()}:00 am"
                }
                if (values[1].toFloat().roundToInt() >= 24){ newId="23:59 pm"}
                binding.Mstv.text = "$id - $newId"
                mon= binding.Mstv.text.toString().trim()
            }
        })
        binding.mondaySlider.addOnChangeListener { slider, value, fromUser ->
            val values =  binding.mondaySlider.values
            var id:String
            var newId:String

            if (values[0].toFloat().roundToInt()>=12){ id="${values[0].roundToInt()}:00 pm"}else {
                id="${values[0].roundToInt()}:00 am"
            }
            if (values[0].toFloat().roundToInt() == 24){ id="23:59 pm"}

            if (values[1].toFloat().roundToInt()>=12){ newId="${values[1].roundToInt()}:00 pm"}else {
                newId="${values[1].roundToInt()}:00 am"
            }
            if (values[1].toFloat().roundToInt() == 24){ newId="23:59 pm"}
            binding.Mstv.text ="$id - $newId"
            mon= binding.Mstv.text.toString().trim()
        }

        binding.tuesdaySlider.setLabelFormatter { value: Float ->
            return@setLabelFormatter if (value.roundToInt() in 12..23) {
                "${value.roundToInt()}:00 pm"
            } else if (value.roundToInt() == 24) {
                "23:59 pm"
            } else {
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
                var id:String
                var newId:String
                //Those are the new updated values of sldier when user has finshed dragging
                Log.i("SliderNewValue From", values[0].toString())
                Log.i("SliderNewValue To", values[1].toString())
                if (values[0].toFloat().roundToInt()>=12){ id="${values[0].roundToInt()}:00 pm"}else {
                    id="${values[0].roundToInt()}:00am"
                }

                if (values[0].toFloat().roundToInt() == 24){ id="23:59 pm"}

                if (values[1].toFloat().roundToInt()>=12){ newId="${values[1].roundToInt()}:00 pm"}else {
                    newId="${values[1].roundToInt()}:00 am"
                }
                if (values[1].toFloat().roundToInt() >= 24){ newId="23:59 pm"}
                binding.Tstv.text = "$id - $newId"
                tue= binding.Tstv.text.toString().trim()
            }
        })
        binding.tuesdaySlider.addOnChangeListener { slider, value, fromUser ->
            val values =  binding.tuesdaySlider.values
            var id:String
            var newId:String

            if (values[0].toFloat().roundToInt()>=12){ id="${values[0].roundToInt()}:00 pm"}else {
                id="${values[0].roundToInt()}:00 am"
            }
            if (values[0].toFloat().roundToInt() == 24){ id="23:59 pm"}

            if (values[1].toFloat().roundToInt()>=12){ newId="${values[1].roundToInt()}:00 pm"}else {
                newId="${values[1].roundToInt()}:00 am"
            }
            if (values[1].toFloat().roundToInt() == 24){ newId="23:59 pm"}
            binding.Tstv.text = "$id - $newId"
            tue= binding.Tstv.text.toString().trim()
        }

        binding.wednesdaySlider.setLabelFormatter { value: Float ->
            return@setLabelFormatter if (value.roundToInt() in 12..23) {
                "${value.roundToInt()}:00 pm"
            } else if (value.roundToInt() == 24) {
                "23:59 pm"
            } else {
                "${value.roundToInt()}:00 am"
            }
        }
        binding.wednesdaySlider.setValueFrom(0f).toString()
        binding.wednesdaySlider.setValues(0f,24f).toString()
        binding.wednesdaySlider.setValueTo(24f).toString()

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
                var id:String
                var newId:String
                //Those are the new updated values of slider when user has finshed dragging
                Log.i("SliderNewValue From", values[0].toString())
                Log.i("SliderNewValue To", values[1].toString())
                if (values[0].toFloat().roundToInt()>=12){ id="${values[0].roundToInt()}:00 pm"}else {
                    id="${values[0].roundToInt()}:00am"
                }

                if (values[0].toFloat().roundToInt() == 24){ id="23:59 pm"}

                if (values[1].toFloat().roundToInt()>=12){ newId="${values[1].roundToInt()}:00 pm"}else {
                    newId="${values[1].roundToInt()}:00 am"
                }
                if (values[1].toFloat().roundToInt() >= 24){ newId="23:59 pm"}

                binding.Wstv.text = "$id - $newId"
                wed= binding.Wstv.text.toString().trim()
            }
        })
        binding.wednesdaySlider.addOnChangeListener { slider, value, fromUser ->
            val values =  binding.wednesdaySlider.values
            var id:String
            var newId:String

            if (values[0].toFloat().roundToInt()>=12){ id="${values[0].roundToInt()}:00 pm"}else {
                id="${values[0].roundToInt()}:00 am"
            }
            if (values[0].toFloat().roundToInt() == 24){ id="23:59 pm"}

            if (values[1].toFloat().roundToInt()>=12){ newId="${values[1].roundToInt()}:00 pm"}else {
                newId="${values[1].roundToInt()}:00 am"
            }
            if (values[1].toFloat().roundToInt() == 24){ newId="23:59 pm"}
            binding.Wstv.text ="$id - $newId"
            wed= binding.Wstv.text.toString().trim()
        }

        binding.thursdaySlider.setLabelFormatter { value: Float ->
            return@setLabelFormatter if (value.roundToInt() in 12..23) {
                "${value.roundToInt()}:00 pm"
            } else if (value.roundToInt() == 24) {
                "23:59 pm"
            } else {
                "${value.roundToInt()}:00 am"
            }
        }
        binding.thursdaySlider.setValueFrom(0f).toString()
        binding.thursdaySlider.setValues(0f,24f).toString()
        binding.thursdaySlider.setValueTo(24f).toString()

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
                var id:String
                var newId:String
                //Those are the new updated values of slider when user has finshed dragging
                Log.i("SliderNewValue From", values[0].toString())
                Log.i("SliderNewValue To", values[1].toString())
                if (values[0].toFloat().roundToInt()>=12){ id="${values[0].roundToInt()}:00 pm"}else {
                    id="${values[0].roundToInt()}:00am"
                }

                if (values[0].toFloat().roundToInt() == 24){ id="23:59 pm"}

                if (values[1].toFloat().roundToInt()>=12){ newId="${values[1].roundToInt()}:00 pm"}else {
                    newId="${values[1].roundToInt()}:00 am"
                }
                if (values[1].toFloat().roundToInt() >= 24){ newId="23:59 pm"}

                binding.Thstv.text = "$id - $newId"
                thu= binding.Thstv.text.toString().trim()

            }
        })
        binding.thursdaySlider.addOnChangeListener { slider, value, fromUser ->
            val values =  binding.thursdaySlider.values
            var id:String
            var newId:String

            if (values[0].toFloat().roundToInt()>=12){ id="${values[0].roundToInt()}:00 pm"}else {
                id="${values[0].roundToInt()}:00 am"
            }
            if (values[0].toFloat().roundToInt() == 24){ id="23:59 pm"}

            if (values[1].toFloat().roundToInt()>=12){ newId="${values[1].roundToInt()}:00 pm"}else {
                newId="${values[1].roundToInt()}:00 am"
            }
            if (values[1].toFloat().roundToInt() == 24){ newId="23:59 pm"}
            binding.Thstv.text ="$id - $newId"
            thu= binding.Thstv.text.toString().trim()

        }

        binding.fridaySlider.setLabelFormatter { value: Float ->
            return@setLabelFormatter if (value.roundToInt() in 12..23) {
                "${value.roundToInt()}:00 pm"
            } else if (value.roundToInt() == 24) {
                "23:59 pm"
            } else {
                "${value.roundToInt()}:00 am"
            }
        }
        binding.fridaySlider.setValueFrom(0f).toString()
        binding.fridaySlider.setValues(0f,24f).toString()
        binding.fridaySlider.setValueTo(24f).toString()

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
                var id:String
                var newId:String
                //Those are the new updated values of slider when user has finshed dragging
                Log.i("SliderNewValue From", values[0].toString())
                Log.i("SliderNewValue To", values[1].toString())
                if (values[0].toFloat().roundToInt()>=12){ id="${values[0].roundToInt()}:00 pm"}else {
                    id="${values[0].roundToInt()}:00am"
                }

                if (values[0].toFloat().roundToInt() == 24){ id="23:59 pm"}

                if (values[1].toFloat().roundToInt()>=12){ newId="${values[1].roundToInt()}:00 pm"}else {
                    newId="${values[1].roundToInt()}:00 am"
                }
                if (values[1].toFloat().roundToInt() >= 24){ newId="23:59 pm"}

                binding.Fstv.text = "$id - $newId"
                fri= binding.Fstv.text.toString().trim()

            }
        })
        binding.fridaySlider.addOnChangeListener { slider, value, fromUser ->
            val values =  binding.fridaySlider.values
            var id:String
            var newId:String

            if (values[0].toFloat().roundToInt()>=12){ id="${values[0].roundToInt()}:00 pm"}else {
                id="${values[0].roundToInt()}:00 am"
            }
            if (values[0].toFloat().roundToInt() == 24){ id="23:59 pm"}

            if (values[1].toFloat().roundToInt()>=12){ newId="${values[1].roundToInt()}:00 pm"}else {
                newId="${values[1].roundToInt()}:00 am"
            }
            if (values[1].toFloat().roundToInt() == 24){ newId="23:59 pm"}
            binding.Fstv.text ="$id - $newId"
            fri= binding.Fstv.text.toString().trim()
        }

        binding.saturdaySlider.setLabelFormatter { value: Float ->
            return@setLabelFormatter if (value.roundToInt() in 12..23) {
                "${value.roundToInt()}:00 pm"
            } else if (value.roundToInt() == 24) {
                "23:59 pm"
            } else {
                "${value.roundToInt()}:00 am"
            }
        }
        binding.saturdaySlider.setValueFrom(0f).toString()
        binding.saturdaySlider.setValues(0f,24f).toString()
        binding.saturdaySlider.setValueTo(24f).toString()

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
                var id:String
                var newId:String
                //Those are the new updated values of slider when user has finshed dragging
                Log.i("SliderNewValue From", values[0].toString())
                Log.i("SliderNewValue To", values[1].toString())
                if (values[0].toFloat().roundToInt()>=12){ id="${values[0].roundToInt()}:00 pm"}else {
                    id="${values[0].roundToInt()}:00am"
                }

                if (values[0].toFloat().roundToInt() == 24){ id="23:59 pm"}

                if (values[1].toFloat().roundToInt()>=12){ newId="${values[1].roundToInt()}:00 pm"}else {
                    newId="${values[1].roundToInt()}:00 am"
                }
                if (values[1].toFloat().roundToInt() >= 24){ newId="23:59 pm"}

                binding.Sastv.text = "$id - $newId"
                sat= binding.Sastv.text.toString().trim()

            }
        })
        binding.saturdaySlider.addOnChangeListener { slider, value, fromUser ->
            val values =  binding.saturdaySlider.values
            var id:String
            var newId:String

            if (values[0].toFloat().roundToInt()>=12){ id="${values[0].roundToInt()}:00 pm"}else {
                id="${values[0].roundToInt()}:00 am"
            }
            if (values[0].toFloat().roundToInt() == 24){ id="23:59 pm"}

            if (values[1].toFloat().roundToInt()>=12){ newId="${values[1].roundToInt()}:00 pm"}else {
                newId="${values[1].roundToInt()}:00 am"
            }
            if (values[1].toFloat().roundToInt() == 24){ newId="23:59 pm"}
            binding.Sastv.text ="$id - $newId"
            sat= binding.Sastv.text.toString().trim()

        }

        if (type=="edit"){
            binding.title.text="Edit Team"
            binding.save.text="Save"
        }
    }

    override fun onClick(v: View?) {

        when (v?.id) {
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
            R.id.back -> {
                onBackPressed()
            }

            R.id.addImage -> {
                selectImage()
            }

            R.id.selectSport -> {
                sportListApi()
            }

            R.id.beginner -> {
                binding.beginner.setCardBackgroundColor(Color.parseColor("#F95047"))
                binding.beginnerTv.setTextColor(Color.WHITE)
                binding.intermediate.setCardBackgroundColor(Color.WHITE)
                binding.intermediateTv.setTextColor(Color.parseColor("#F95047"))
                binding.experienced.setCardBackgroundColor(Color.WHITE)
                binding.experiencedTv.setTextColor(Color.parseColor("#F95047"))
            }
            R.id.intermediate -> {
                binding.intermediate.setCardBackgroundColor(Color.parseColor("#F95047"))
                binding.intermediateTv.setTextColor(Color.WHITE)
                binding.beginner.setCardBackgroundColor(Color.WHITE)
                binding.beginnerTv.setTextColor(Color.parseColor("#F95047"))
                binding.experienced.setCardBackgroundColor(Color.WHITE)
                binding.experiencedTv.setTextColor(Color.parseColor("#F95047"))
            }

            R.id.experienced -> {
                binding.experienced.setCardBackgroundColor(Color.parseColor("#F95047"))
                binding.experiencedTv.setTextColor(Color.WHITE)
                binding.beginner.setCardBackgroundColor(Color.WHITE)
                binding.beginnerTv.setTextColor(Color.parseColor("#F95047"))
                binding.intermediate.setCardBackgroundColor(Color.WHITE)
                binding.intermediateTv.setTextColor(Color.parseColor("#F95047"))
            }

            R.id.kitYes -> {
                binding.kitYes.setCardBackgroundColor(Color.parseColor("#F95047"))
                binding.kitYesTv.setTextColor(Color.WHITE)
                binding.kitNo.setCardBackgroundColor(Color.WHITE)
                binding.kitNoTv.setTextColor(Color.parseColor("#F95047"))
            }

            R.id.kitNo -> {
                binding.kitNo.setCardBackgroundColor(Color.parseColor("#F95047"))
                binding.kitNoTv.setTextColor(Color.WHITE)
                binding.kitYes.setCardBackgroundColor(Color.WHITE)
                binding.kitYesTv.setTextColor(Color.parseColor("#F95047"))
            }

            R.id.awayYes -> {
                binding.awayYes.setCardBackgroundColor(Color.parseColor("#F95047"))
                binding.awayYesTv.setTextColor(Color.WHITE)
                binding.awayNo.setCardBackgroundColor(Color.WHITE)
                binding.awayNoTv.setTextColor(Color.parseColor("#F95047"))
            }

            R.id.awayNo -> {
                binding.awayNo.setCardBackgroundColor(Color.parseColor("#F95047"))
                binding.awayNoTv.setTextColor(Color.WHITE)
                binding.awayYes.setCardBackgroundColor(Color.WHITE)
                binding.awayYesTv.setTextColor(Color.parseColor("#F95047"))
            }
            R.id.createTeam -> {
                onBackPressed()
            } R.id.location -> {
            CommonUtils.performIntent(this@AddTeamActivity,LocationActivity::class.java)
            }
            }

    }
    private fun selectSportBottomSheet() {
        val view: View = layoutInflater.inflate(R.layout.bottom_sheet_select_sport, null)
        seletedBottomSheet = BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvSelectSport)
        val close = view.findViewById<ImageView>(R.id.close)

        selectSportAdapter = SelectMatchSportAdapter(list, this@AddTeamActivity,String(),this)
        recyclerView?.adapter = selectSportAdapter


        close.setOnClickListener{
            seletedBottomSheet?.dismiss()
        }
        seletedBottomSheet?.setCanceledOnTouchOutside(false)
        seletedBottomSheet?.setContentView(view)
        seletedBottomSheet?.show()
    }

    override fun bottomSheetListner(ViewType: String,id:String) {

        if (ViewType!=null){
            binding.selectSport.setCardBackgroundColor(Color.parseColor("#F95047"))
            binding.selectSportTv.setTextColor(Color.WHITE)
            binding.selectSportTv.text=ViewType
            binding.selectSportTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_circle, 0);
            seletedBottomSheet?.dismiss()
        }
    }

    private fun sportListApi(){
        if (isNetworkAvailable()) {
            CommonUtils.showProgressDialog(this)
            lifecycleScope.launchWhenStarted {
                val resultResponse = UserApi(this@AddTeamActivity).sportsList()
                apiSportListResult(resultResponse)
            }
        } else {
            CommonUtils.hideProgressDialog()
            showNetworkSpeedError()
        }
    }

    private fun apiSportListResult(resultResponse: ResultResponse): Any {
        CommonUtils.hideProgressDialog()
        return when (resultResponse) {
            is ResultResponse.Success<*> -> {
                val response = resultResponse.response as SportListResponse
                //get data and convert string to json and save data
                if (response.success == "true") {
                    selectSportBottomSheet()

                    selectSportAdapter!!.updateList(response.data)


                } else {
                    CommonUtils.hideProgressDialog()
                    Toast.makeText(
                        this,
                        "Server Error",
                        Toast.LENGTH_SHORT
                    ).show()                }
            }
            else -> {
                CommonUtils.hideProgressDialog()
                showError(resultResponse)
            }
        }
    }

    private fun selectImage() {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@AddTeamActivity)
        builder.setTitle("Add Photo!")
        builder.setItems(options, DialogInterface.OnClickListener { dialog, item ->
            if (options[item] == "Take Photo") {

                try {
                    if (!hasPermissions(this@AddTeamActivity, *PERMISSIONS)) {
                        ActivityCompat.requestPermissions(
                            this@AddTeamActivity,
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
            Glide.with(this@AddTeamActivity).load(mBitmapImage).into(binding.profileImage)
//            binding.profileImage.setImageBitmap(mBitmapImage)

        } else if (resultCode == Crop.RESULT_ERROR) {
            CommonUtils.hideProgressDialog()
            Toast.makeText(this, Crop.getError(data!!).message, Toast.LENGTH_SHORT).show()
        }
    }
}