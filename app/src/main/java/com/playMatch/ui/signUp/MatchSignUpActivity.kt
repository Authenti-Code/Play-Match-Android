package com.playMatch.ui.signUp

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.google.android.material.slider.RangeSlider
import com.playMatch.R
import com.playMatch.controller.playMatchAPi.ResultResponse
import com.playMatch.controller.playMatchAPi.apiClasses.UserApi
import com.playMatch.controller.playMatchAPi.postPojoModel.user.matchAvailability.MatchAvailabilityPost
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivityMatchSignUpBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.home.activity.HomeActivity
import com.playMatch.ui.signUp.signupModel.MatchAvailabilityResponse
import com.playMatch.ui.signUp.signupModel.SportsLevelsResponse
import com.playMatch.ui.signUp.userSports.UserSportsPost
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


class MatchSignUpActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMatchSignUpBinding
    private var SCvColor:Boolean=true
    private var MCvColor:Boolean=true
    private var TCvColor:Boolean=true
    private var WCvColor:Boolean=true
    private var ThCvColor:Boolean=true
    private var FCvColor:Boolean=true
    private var SACvColor:Boolean=true
    private var availaible:String="0"
    private var toNotify:String="0"
    private var sun:String?=null
    private var mon:String?=null
    private var tue:String?=null
    private var wed:String?=null
    private var thu:String?=null
    private var fri:String?=null
    private var sat:String?=null

    @RequiresApi(33)
    override fun onCreate(savedInstanceState: Bundle?) {
        removeStatusBarFullyBlackIcon()
        super.onCreate(savedInstanceState)
        binding = ActivityMatchSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun initView() {
        binding.back.setOnClickListener(this)

        binding.Continue.setOnClickListener(this)
        binding.skip.setOnClickListener(this)
        binding.checkbox.setOnClickListener(this)
        binding.notify.setOnClickListener(this)
        binding.Stv.setOnClickListener(this)
        binding.Mtv.setOnClickListener(this)
        binding.Ttv.setOnClickListener(this)
        binding.Wtv.setOnClickListener(this)
        binding.Thtv.setOnClickListener(this)
        binding.Ftv.setOnClickListener(this)
        binding.Satv.setOnClickListener(this)

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
            R.id.checkbox -> {
                if (binding.checkbox.isChecked){
                    availaible="1"
                }else{
                    availaible="0"
                }

                if (binding.checkbox.isChecked){
                    binding.Stv.isClickable=false
                    binding.Mtv.isClickable=false
                    binding.Ttv.isClickable=false
                    binding.Wtv.isClickable=false
                    binding.Thtv.isClickable=false
                    binding.Ftv.isClickable=false
                    binding.Satv.isClickable=false
                }else{
                    binding.Stv.isClickable=true
                    binding.Mtv.isClickable=true
                    binding.Ttv.isClickable=true
                    binding.Wtv.isClickable=true
                    binding.Thtv.isClickable=true
                    binding.Ftv.isClickable=true
                    binding.Satv.isClickable=true
                }
            }
            R.id.notify -> {
                if (binding.notify.isChecked){
                    toNotify="1"
                }else{
                    toNotify="0"
                }
            }

            R.id.Continue -> {
                if (binding.distanceTv.text.toString().trim()!=""){
                    matchAvailApi()
                }else{
                    Toast.makeText(this@MatchSignUpActivity, "Please Enter Distance", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.skip -> {
        CommonUtils.performIntentFinish(this@MatchSignUpActivity, com.playMatch.ui.home.activity.HomeActivity::class.java)
            }
        }
    }

    private fun matchAvailApi(){

        if (isNetworkAvailable()) {
            showProgressBar()
            lifecycleScope.launchWhenStarted {
                val resultResponse = UserApi(this@MatchSignUpActivity).matchAvailability(
                    MatchAvailabilityPost( binding.distanceTv.text.toString().trim(),availaible,sun,mon,tue,wed,thu,fri,sat,toNotify)
                )
                apiResult(resultResponse)
            }
        } else {
            showNetworkSpeedError()
        }
    }


    private fun apiResult(resultResponse: ResultResponse) {
        hideProgressBar()
        return when (resultResponse) {
            is ResultResponse.Success<*> -> {
                val response = resultResponse.response as MatchAvailabilityResponse
                //get data and convert string to json and save data
                if (response.success == "true") {
                    CommonUtils.performIntentFinish(this, HomeActivity::class.java)
                } else {
                    showSnackBar(findViewById(R.id.rootView), response.message)
                }
            }
            else -> {
                showError(resultResponse)
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