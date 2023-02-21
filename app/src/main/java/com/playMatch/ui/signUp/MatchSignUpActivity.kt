package com.playMatch.ui.signUp

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import com.google.android.material.slider.RangeSlider
import com.playMatch.R
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivityMatchSignUpBinding
import com.playMatch.ui.baseActivity.BaseActivity
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
        binding.Stv.setOnClickListener(this)
        binding.Mtv.setOnClickListener(this)
        binding.Ttv.setOnClickListener(this)
        binding.Wtv.setOnClickListener(this)
        binding.Thtv.setOnClickListener(this)
        binding.Ftv.setOnClickListener(this)
        binding.Satv.setOnClickListener(this)
        binding.Continue.setOnClickListener(this)
        binding.skip.setOnClickListener(this)

        binding.sundaySlider.setLabelFormatter { value: Float ->
            return@setLabelFormatter if (value>12.00){"${value.roundToInt()}:00pm"}else{
                "${value.roundToInt()}:00am"
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
                val newId:String
                //Those are the new updated values of sldier when user has finshed dragging
                Log.i("SliderNewValue From", values[0].toString())
                Log.i("SliderNewValue To", values[1].toString())
                if (values[0].toFloat()>12.00){ id="${values[0].roundToInt()}:00pm"}else {
                  id="${values[0].roundToInt()}:00am"
                }
                if (values[1].toFloat()>12.00){ newId="${values[1].roundToInt()}:00pm"}else {
                  newId="${values[1].roundToInt()}:00am"
                }

                binding.Sstv.text = "$id - $newId"
            }
        })
        binding.sundaySlider.addOnChangeListener { slider, value, fromUser ->
            val values =  binding.sundaySlider.values
            val id:String
            val newId:String

            if (values[0].toFloat()>12.00){ id="${values[0].roundToInt()}:00pm"}else {
                id="${values[0].roundToInt()}:00am"
            }
            if (values[1].toFloat()>12.00){ newId="${values[1].roundToInt()}:00pm"}else {
                newId="${values[1].roundToInt()}:00am"
            }
            binding.Sstv.text ="$id - $newId"
        }


        binding.mondaySlider.setLabelFormatter { value: Float ->
            return@setLabelFormatter if (value>12.00){"${value.roundToInt()}:00pm"}else{
                "${value.roundToInt()}:00am"
            }
        }
        binding.mondaySlider.setValueFrom(0f).toString()
        binding.mondaySlider.setValues(0f,23f).toString()
        binding.mondaySlider.setValueTo(23f).toString()

        binding.mondaySlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: RangeSlider) {
                val values = binding.mondaySlider.values
                //Those are the satrt and end values of sldier when user start dragging
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
                if (values[0].toFloat()>12.00){ id="${values[0].roundToInt()}:00pm"}else {
                    id="${values[0].roundToInt()}:00am"
                }
                if (values[1].toFloat()>12.00){ newId="${values[1].roundToInt()}:00pm"}else {
                    newId="${values[1].roundToInt()}:00am"
                }

                binding.Mstv.text = "$id - $newId"
            }
        })
        binding.mondaySlider.addOnChangeListener { slider, value, fromUser ->
            val values =  binding.mondaySlider.values
            val id:String
            val newId:String

            if (values[0].toFloat()>12.00){ id="${values[0].roundToInt()}:00pm"}else {
                id="${values[0].roundToInt()}:00am"
            }
            if (values[1].toFloat()>12.00){ newId="${values[1].roundToInt()}:00pm"}else {
                newId="${values[1].roundToInt()}:00am"
            }
            binding.Mstv.text ="$id - $newId"
        }


        binding.tuesdaySlider.setLabelFormatter { value: Float ->
            return@setLabelFormatter if (value>12.00){"${value.roundToInt()}:00pm"}else{
                "${value.roundToInt()}:00am"
            }
        }
        binding.tuesdaySlider.setValueFrom(0f).toString()
        binding.tuesdaySlider.setValues(0f,23f).toString()
        binding.tuesdaySlider.setValueTo(23f).toString()

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
                val newId:String
                //Those are the new updated values of sldier when user has finshed dragging
                Log.i("SliderNewValue From", values[0].toString())
                Log.i("SliderNewValue To", values[1].toString())
                if (values[0].toFloat()>12.00){ id="${values[0].roundToInt()}:00pm"}else {
                    id="${values[0].roundToInt()}:00am"
                }
                if (values[1].toFloat()>12.00){ newId="${values[1].roundToInt()}:00pm"}else {
                    newId="${values[1].roundToInt()}:00am"
                }

                binding.Tstv.text = "$id - $newId"
            }
        })
        binding.tuesdaySlider.addOnChangeListener { slider, value, fromUser ->
            val values =  binding.tuesdaySlider.values
            val id:String
            val newId:String

            if (values[0].toFloat()>12.00){ id="${values[0].roundToInt()}:00pm"}else {
                id="${values[0].roundToInt()}:00am"
            }
            if (values[1].toFloat()>12.00){ newId="${values[1].roundToInt()}:00pm"}else {
                newId="${values[1].roundToInt()}:00am"
            }
            binding.Tstv.text = "$id - $newId"
        }

        binding.wednesdaySlider.setLabelFormatter { value: Float ->
            return@setLabelFormatter if (value>12.00){"${value.roundToInt()}:00pm"}else{
                "${value.roundToInt()}:00am"
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
                if (values[0].toFloat()>12.00){ id="${values[0].roundToInt()}:00pm"}else {
                    id="${values[0].roundToInt()}:00am"
                }
                if (values[1].toFloat()>12.00){ newId="${values[1].roundToInt()}:00pm"}else {
                    newId="${values[1].roundToInt()}:00am"
                }

                binding.Wstv.text = "$id - $newId"
            }
        })
        binding.wednesdaySlider.addOnChangeListener { slider, value, fromUser ->
            val values =  binding.wednesdaySlider.values
            val id:String
            val newId:String

            if (values[0].toFloat()>12.00){ id="${values[0].roundToInt()}:00pm"}else {
                id="${values[0].roundToInt()}:00am"
            }
            if (values[1].toFloat()>12.00){ newId="${values[1].roundToInt()}:00pm"}else {
                newId="${values[1].roundToInt()}:00am"
            }
            binding.Wstv.text ="$id - $newId"
        }

        binding.thursdaySlider.setLabelFormatter { value: Float ->
            return@setLabelFormatter if (value>12.00){"${value.roundToInt()}:00pm"}else{
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
                if (values[0].toFloat()>12.00){ id="${values[0].roundToInt()}:00pm"}else {
                    id="${values[0].roundToInt()}:00am"
                }
                if (values[1].toFloat()>12.00){ newId="${values[1].roundToInt()}:00pm"}else {
                    newId="${values[1].roundToInt()}:00am"
                }

                binding.Thstv.text = "$id - $newId"
            }
        })
        binding.thursdaySlider.addOnChangeListener { slider, value, fromUser ->
            val values =  binding.thursdaySlider.values
            val id:String
            val newId:String

            if (values[0].toFloat()>12.00){ id="${values[0].roundToInt()}:00pm"}else {
                id="${values[0].roundToInt()}:00am"
            }
            if (values[1].toFloat()>12.00){ newId="${values[1].roundToInt()}:00pm"}else {
                newId="${values[1].roundToInt()}:00am"
            }
            binding.Thstv.text ="$id - $newId"
        }

        binding.fridaySlider.setLabelFormatter { value: Float ->
            return@setLabelFormatter if (value>12.00){"${value.roundToInt()}:00pm"}else{
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
                if (values[0].toFloat()>12.00){ id="${values[0].roundToInt()}:00pm"}else {
                    id="${values[0].roundToInt()}:00am"
                }
                if (values[1].toFloat()>12.00){ newId="${values[1].roundToInt()}:00pm"}else {
                    newId="${values[1].roundToInt()}:00am"
                }

                binding.Fstv.text = "$id - $newId"
            }
        })
        binding.fridaySlider.addOnChangeListener { slider, value, fromUser ->
            val values =  binding.fridaySlider.values
            val id:String
            val newId:String

            if (values[0].toFloat()>12.00){ id="${values[0].roundToInt()}:00pm"}else {
                id="${values[0].roundToInt()}:00am"
            }
            if (values[1].toFloat()>12.00){ newId="${values[1].roundToInt()}:00pm"}else {
                newId="${values[1].roundToInt()}:00am"
            }
            binding.Fstv.text ="$id - $newId"
        }

        binding.saturdaySlider.setLabelFormatter { value: Float ->
            return@setLabelFormatter if (value>12.00){"${value.roundToInt()}:00pm"}else{
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
                if (values[0].toFloat()>12.00){ id="${values[0].roundToInt()}:00pm"}else {
                    id="${values[0].roundToInt()}:00am"
                }
                if (values[1].toFloat()>12.00){ newId="${values[1].roundToInt()}:00pm"}else {
                    newId="${values[1].roundToInt()}:00am"
                }

                binding.Sastv.text = "$id - $newId"
            }
        })
        binding.saturdaySlider.addOnChangeListener { slider, value, fromUser ->
            val values =  binding.saturdaySlider.values
            val id:String
            val newId:String

            if (values[0].toFloat()>12.00){ id="${values[0].roundToInt()}:00pm"}else {
                id="${values[0].roundToInt()}:00am"
            }
            if (values[1].toFloat()>12.00){ newId="${values[1].roundToInt()}:00pm"}else {
                newId="${values[1].roundToInt()}:00am"
            }
            binding.Sastv.text ="$id - $newId"
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

                }else{
                    binding.Scv.setCardBackgroundColor(Color.WHITE)
                    binding.Stv.setTextColor(Color.parseColor("#F95047"))
                    SCvColor=true
                    binding.Slay.visibility=View.GONE
                }
            }
            R.id.Mtv -> {
                if (MCvColor==true){
                    binding.Mcv.setCardBackgroundColor(Color.parseColor("#F95047"))
                    binding.Mtv.setTextColor(Color.WHITE)
                    binding.Mlay.visibility=View.VISIBLE
                    MCvColor=false

                }else{
                    binding.Mcv.setCardBackgroundColor(Color.WHITE)
                    binding.Mtv.setTextColor(Color.parseColor("#F95047"))
                    binding.Mlay.visibility=View.GONE
                    MCvColor=true
                }
            }
            R.id.Ttv -> {
                if (TCvColor==true){
                    binding.Tcv.setCardBackgroundColor(Color.parseColor("#F95047"))
                    binding.Ttv.setTextColor(Color.WHITE)
                    binding.Tlay.visibility=View.VISIBLE
                    TCvColor=false

                }else{
                    binding.Tcv.setCardBackgroundColor(Color.WHITE)
                    binding.Ttv.setTextColor(Color.parseColor("#F95047"))
                    binding.Tlay.visibility=View.GONE
                    TCvColor=true
                }
            }
            R.id.Wtv -> {
                if (WCvColor==true){
                    binding.Wcv.setCardBackgroundColor(Color.parseColor("#F95047"))
                    binding.Wtv.setTextColor(Color.WHITE)
                    binding.Wlay.visibility=View.VISIBLE
                    WCvColor=false

                }else{
                    binding.Wcv.setCardBackgroundColor(Color.WHITE)
                    binding.Wtv.setTextColor(Color.parseColor("#F95047"))
                    binding.Wlay.visibility=View.GONE
                    WCvColor=true
                }
            }
            R.id.Thtv -> {
                if (ThCvColor==true){
                    binding.Thcv.setCardBackgroundColor(Color.parseColor("#F95047"))
                    binding.Thtv.setTextColor(Color.WHITE)
                    binding.Thlay.visibility=View.VISIBLE
                    ThCvColor=false

                }else{
                    binding.Thcv.setCardBackgroundColor(Color.WHITE)
                    binding.Thtv.setTextColor(Color.parseColor("#F95047"))
                    binding.Thlay.visibility=View.GONE
                    ThCvColor=true
                }
            }
            R.id.Ftv -> {
                if (FCvColor==true){
                    binding.Fcv.setCardBackgroundColor(Color.parseColor("#F95047"))
                    binding.Ftv.setTextColor(Color.WHITE)
                    binding.Flay.visibility=View.VISIBLE
                    FCvColor=false

                }else{
                    binding.Fcv.setCardBackgroundColor(Color.WHITE)
                    binding.Ftv.setTextColor(Color.parseColor("#F95047"))
                    binding.Flay.visibility=View.GONE
                    FCvColor=true
                }
            }
            R.id.Satv -> {
                if (SACvColor==true){
                    binding.Sacv.setCardBackgroundColor(Color.parseColor("#F95047"))
                    binding.Satv.setTextColor(Color.WHITE)
                    binding.SaLay.visibility=View.VISIBLE
                    SACvColor=false

                }else{
                    binding.Sacv.setCardBackgroundColor(Color.WHITE)
                    binding.Satv.setTextColor(Color.parseColor("#F95047"))
                    binding.SaLay.visibility=View.GONE
                    SACvColor=true
                }
            }
            R.id.back -> {
                onBackPressed()
            }

            R.id.Continue -> {
        CommonUtils.performIntentFinish(this@MatchSignUpActivity, com.playMatch.ui.home.activity.HomeActivity::class.java)
            }
            R.id.skip -> {
        CommonUtils.performIntentFinish(this@MatchSignUpActivity, com.playMatch.ui.home.activity.HomeActivity::class.java)
            }
        }
    }
}