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
//        binding.sundaySlider.setLabelFormatter(LabelFormatter { "hours" })
        binding.sundaySlider.setLabelFormatter { value: Float ->
            return@setLabelFormatter if (value>12.00){"${value.roundToInt()}pm"}else{
                "${value.roundToInt()}am"
            }
        }
        binding.sundaySlider.setValueFrom(0f).toString()
        binding.sundaySlider.setValues(0f,24f).toString()
        binding.sundaySlider.setValueTo(24f).toString()

//        binding.sundaySlider.addOnChangeListener { slider, value, fromUser ->
//            value = SimpleDateFormat("hh:mm", Locale.ENGLISH)
//            // Do something with the selected hours
//        }

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
                if (values[0].toFloat()>12.00){ id="${values[0].roundToInt()}pm"}else {
                  id="${values[0].roundToInt()}am"
                }
                if (values[1].toFloat()>12.00){ newId="${values[1].roundToInt()}pm"}else {
                  newId="${values[1].roundToInt()}am"
                }

                binding.Sstv.setText(id+"-"+ newId)
            }
        })


        //If you only want the slider start and end value and don't care about the previous values
        binding.sundaySlider.addOnChangeListener { slider, value, fromUser ->
            val values =  binding.sundaySlider.values
            val id:String
            val newId:String

            if (values[0].toFloat()>12.00){ id="${values[0].roundToInt()}pm"}else {
                id="${values[0].roundToInt()}am"
            }
            if (values[1].toFloat()>12.00){ newId="${values[1].roundToInt()}pm"}else {
                newId="${values[1].roundToInt()}am"
            }
            binding.Sstv.text =id+"-"+ newId
        }



        binding.mondaySlider.setValues(0.0f,10.0f)
        binding.tuesdaySlider.setValues(0.0f,10.0f)
        binding.wednesdaySlider.setValues(0.0f,10.0f)
        binding.thursdaySlider.setValues(0.0f,10.0f)
        binding.fridaySlider.setValues(0.0f,10.0f)
        binding.saturdaySlider.setValues(0.0f,10.0f)


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
        CommonUtils.performIntentFinish(this@MatchSignUpActivity, com.playMatch.ui.home.activity.HomeActivity::class.java)            }

        }
    }
}