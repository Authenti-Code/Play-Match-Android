package com.playMatch.ui.teams.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.app.ActivityCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.slider.RangeSlider
import com.playMatch.R
import com.playMatch.controller.`interface`.BottomSheetListner
import com.playMatch.controller.playMatchAPi.ApiConstant
import com.playMatch.controller.playMatchAPi.ResultResponse
import com.playMatch.controller.playMatchAPi.apiClasses.UserApi
import com.playMatch.controller.playMatchAPi.postPojoModel.user.selectSportSearchPost.SelectSportSearchPost
import com.playMatch.controller.playMatchAPi.postPojoModel.user.showTeam.ShowTeamPost
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivityAddTeamBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.location.activity.LocationActivity
import com.playMatch.ui.matches.adapter.selectSportAdapter.SelectMatchSportAdapter
import com.playMatch.controller.sharedPrefrence.PrefData
import com.playMatch.ui.signUp.signupModel.SportListResponse
import com.playMatch.ui.signUp.signupModel.SportsList
import com.playMatch.ui.teams.model.addTeam.AddTeamResponse
import com.playMatch.ui.teams.model.editTeam.EditTeamResponse
import com.playMatch.ui.teams.model.showTeamDetails.TeamDetailResponse
import com.soundcloud.android.crop.Crop
import java.io.ByteArrayOutputStream
import kotlin.math.roundToInt

class AddTeamActivity : BaseActivity(), View.OnClickListener, BottomSheetListner {
    private lateinit var binding: ActivityAddTeamBinding
    private var selectSportAdapter: SelectMatchSportAdapter? = null
    private var selectedBottomSheet: BottomSheetDialog? = null
    private var progressbar: ProgressBar? = null
    private var recyclerView: RecyclerView? = null
    private var list = ArrayList<SportsList>()

    //String
    private var sun:String?=""
    private var mon:String?=""
    private var tue:String?=""
    private var wed:String?=""
    private var thu:String?=""
    private var fri:String?=""
    private var sat:String?=""
    private var teamId:String?=""
    private var sportId:String?=""
    private var searchText:String?=""
    private var kitProvided:String?="0"
    private var awayMatches:String?="0"
    private var teamStandard:String?="Intermediate"

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
            teamId = intent.extras?.getString(PrefData.TEAM_ID, "")
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
            binding.createTeamTV.text="Save"
            teamDetails()
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
//                CommonUtils.showProgressDialog(this)
                list.clear()
                searchText=""
                sportListApi()
                selectSportBottomSheet()
            }

            R.id.beginner -> {
                binding.beginner.setCardBackgroundColor(Color.parseColor("#F95047"))
                binding.beginnerTv.setTextColor(Color.WHITE)
                binding.intermediate.setCardBackgroundColor(Color.WHITE)
                binding.intermediateTv.setTextColor(Color.parseColor("#F95047"))
                binding.experienced.setCardBackgroundColor(Color.WHITE)
                binding.experiencedTv.setTextColor(Color.parseColor("#F95047"))
                teamStandard="Beginner"
            }
            R.id.intermediate -> {
                binding.intermediate.setCardBackgroundColor(Color.parseColor("#F95047"))
                binding.intermediateTv.setTextColor(Color.WHITE)
                binding.beginner.setCardBackgroundColor(Color.WHITE)
                binding.beginnerTv.setTextColor(Color.parseColor("#F95047"))
                binding.experienced.setCardBackgroundColor(Color.WHITE)
                binding.experiencedTv.setTextColor(Color.parseColor("#F95047"))
                teamStandard="Intermediate"
            }

            R.id.experienced -> {
                binding.experienced.setCardBackgroundColor(Color.parseColor("#F95047"))
                binding.experiencedTv.setTextColor(Color.WHITE)
                binding.beginner.setCardBackgroundColor(Color.WHITE)
                binding.beginnerTv.setTextColor(Color.parseColor("#F95047"))
                binding.intermediate.setCardBackgroundColor(Color.WHITE)
                binding.intermediateTv.setTextColor(Color.parseColor("#F95047"))
                teamStandard="Experienced"
            }

            R.id.kitYes -> {
                binding.kitYes.setCardBackgroundColor(Color.parseColor("#F95047"))
                binding.kitYesTv.setTextColor(Color.WHITE)
                binding.kitNo.setCardBackgroundColor(Color.WHITE)
                binding.kitNoTv.setTextColor(Color.parseColor("#F95047"))
                kitProvided="1"
            }

            R.id.kitNo -> {
                binding.kitNo.setCardBackgroundColor(Color.parseColor("#F95047"))
                binding.kitNoTv.setTextColor(Color.WHITE)
                binding.kitYes.setCardBackgroundColor(Color.WHITE)
                binding.kitYesTv.setTextColor(Color.parseColor("#F95047"))
                kitProvided="0"
            }

            R.id.awayYes -> {
                binding.awayYes.setCardBackgroundColor(Color.parseColor("#F95047"))
                binding.awayYesTv.setTextColor(Color.WHITE)
                binding.awayNo.setCardBackgroundColor(Color.WHITE)
                binding.awayNoTv.setTextColor(Color.parseColor("#F95047"))
                awayMatches="1"
            }

            R.id.awayNo -> {
                binding.awayNo.setCardBackgroundColor(Color.parseColor("#F95047"))
                binding.awayNoTv.setTextColor(Color.WHITE)
                binding.awayYes.setCardBackgroundColor(Color.WHITE)
                binding.awayYesTv.setTextColor(Color.parseColor("#F95047"))
                awayMatches="0"
            }
            R.id.createTeam -> {
                showProgressBar()
                if (binding.name.text.toString().trim().isEmpty()) {
                    Toast.makeText(this, R.string.error_name, Toast.LENGTH_LONG).show()
                    hideProgressBar()
                }else if(sportId==""){
                    Toast.makeText(this, "Please Select Sport", Toast.LENGTH_LONG).show()

                } else if (binding.location.text.toString().trim().isEmpty()) {
                    Toast.makeText(this, R.string.error_location, Toast.LENGTH_LONG).show()
                    hideProgressBar()
                }else if (binding.genderTv.text.toString().trim().isEmpty()) {
                    Toast.makeText(this, R.string.error_location, Toast.LENGTH_LONG).show()
                    hideProgressBar()
                }else{
                    if (type=="edit"){
                        editTeamApi()
                    }else {
                        addTeamApi()
                    }
                }
            } R.id.location -> {
            CommonUtils.performIntent(this@AddTeamActivity,LocationActivity::class.java)
            }
            }
    }
    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    private fun selectSportBottomSheet() {
        val view: View = layoutInflater.inflate(R.layout.bottom_sheet_select_sport, null)
        selectedBottomSheet = BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme)
         recyclerView = view.findViewById(R.id.rvSelectSport)
        progressbar = view.findViewById(R.id.progressBar)
        val close = view.findViewById<ImageView>(R.id.close)
        val search = view.findViewById<AppCompatEditText>(R.id.searchEt)


        selectSportAdapter = SelectMatchSportAdapter(list, this@AddTeamActivity,String(),this)
        recyclerView?.adapter = selectSportAdapter




            search.doAfterTextChanged {
                    list.clear()
                    searchText=search.text.toString().trim()
                    selectSportAdapter?.updateList(java.util.ArrayList())
                    sportListApi()
            }

        close.setOnClickListener{
            selectedBottomSheet?.dismiss()
        }
        selectedBottomSheet?.setCanceledOnTouchOutside(false)
        selectedBottomSheet?.setContentView(view)
        selectedBottomSheet?.show()
    }

    override fun bottomSheetListner(ViewType: String,id:String,sportsName:String) {

        if (ViewType=="sports"){
            binding.selectSport.setCardBackgroundColor(Color.parseColor("#F95047"))
            binding.selectSportTv.setTextColor(Color.WHITE)
            binding.selectSportTv.text=sportsName
            if (id!=null) {
                sportId = id
            }
            binding.selectSportTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_circle, 0);
            selectedBottomSheet?.dismiss()
        }
    }

    private fun sportListApi(){
        if (isNetworkAvailable()) {
            lifecycleScope.launchWhenStarted {
                val resultResponse = UserApi(this@AddTeamActivity).sportsList(SelectSportSearchPost(searchText!!))
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
                    progressbar?.visibility=View.GONE
                    recyclerView?.visibility=View.VISIBLE
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

    private fun showProgressBar(){
        binding.continueProgressBar.visibility=View.VISIBLE
        binding.createTeamTV.visibility=View.GONE
    }
    private fun hideProgressBar(){
        binding.continueProgressBar.visibility=View.GONE
        binding.createTeamTV.visibility=View.VISIBLE

    }

    override fun onResume() {
        super.onResume()
        val location = PrefData.getStringPrefs(this, PrefData.LOCATION, "")
        if (location.isNotEmpty()) {
            binding.location.text = location
        }
    }

    private fun addTeamApi(){
        showProgressBar()
        val bos = ByteArrayOutputStream()
        mBitmapImage?.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        val imageBytes = bos.toByteArray()
        lifecycleScope.launchWhenStarted {
            val resultResponse = UserApi(this@AddTeamActivity).addTeam(
                this@AddTeamActivity, imageBytes, imageName.toString(),binding.name.text.toString().trim(),binding.genderTv.text.toString().trim(),sportId.toString(),binding.location.text.toString().trim(),teamStandard.toString(),kitProvided.toString(),awayMatches.toString(),sun!!,mon!!,tue!!,wed!!,thu!!,fri!!,sat!!,binding.description.text.toString().trim())
            addTeamapiResult(resultResponse)
        }
    }

    private fun addTeamapiResult(resultResponse: ResultResponse) {
        return when (resultResponse) {
            is ResultResponse.Success<*> -> {
                val response = resultResponse.response as AddTeamResponse
                if (response.success == "true") {
                    hideProgressBar()
                    onBackPressed()
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

    private fun teamDetails(){

        if (isNetworkAvailable()) {
            CommonUtils.showProgressDialog(this)
            lifecycleScope.launchWhenStarted {
                val resultResponse = UserApi(this@AddTeamActivity).teamDetail(ShowTeamPost(teamId.toString()))
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
                val response = resultResponse.response as TeamDetailResponse
                //get data and convert string to json and save data
                if (response.success == "true") {
                    binding.progressBar.visibility=View.VISIBLE
                    Glide.with(this@AddTeamActivity)
                        .load(response.data.image)
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
                    binding.genderTv.setText(response.data.gender)
                    binding.location.setText(response.data.location)
                    binding.description.setText(response.data.otherDetails)

                    if (response.data.sportName!=null){
                        binding.selectSport.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding.selectSportTv.setTextColor(Color.WHITE)
                        binding.selectSportTv.text=response.data.sportName
                        if (response.data.sportId!=null) {
                            sportId = response.data.sportId.toString()
                        }
                        binding.selectSportTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_circle, 0)
                    }

                    if (response.data.teamStandard=="Beginner"){
                        binding.beginner.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding.beginnerTv.setTextColor(Color.WHITE)
                        binding.intermediate.setCardBackgroundColor(Color.WHITE)
                        binding.intermediateTv.setTextColor(Color.parseColor("#F95047"))
                        binding.experienced.setCardBackgroundColor(Color.WHITE)
                        binding.experiencedTv.setTextColor(Color.parseColor("#F95047"))
                        teamStandard="Beginner"
                    }else if (response.data.teamStandard=="Intermediate"){
                        binding.intermediate.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding.intermediateTv.setTextColor(Color.WHITE)
                        binding.beginner.setCardBackgroundColor(Color.WHITE)
                        binding.beginnerTv.setTextColor(Color.parseColor("#F95047"))
                        binding.experienced.setCardBackgroundColor(Color.WHITE)
                        binding.experiencedTv.setTextColor(Color.parseColor("#F95047"))
                        teamStandard="Intermediate"
                    }else if (response.data.teamStandard=="Experienced"){
                        binding.experienced.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding.experiencedTv.setTextColor(Color.WHITE)
                        binding.beginner.setCardBackgroundColor(Color.WHITE)
                        binding.beginnerTv.setTextColor(Color.parseColor("#F95047"))
                        binding.intermediate.setCardBackgroundColor(Color.WHITE)
                        binding.intermediateTv.setTextColor(Color.parseColor("#F95047"))
                        teamStandard="Experienced"
                    }

                    if (response.data.isAwayMatches=="0"){
                        binding.awayNo.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding.awayNoTv.setTextColor(Color.WHITE)
                        binding.awayYes.setCardBackgroundColor(Color.WHITE)
                        binding.awayYesTv.setTextColor(Color.parseColor("#F95047"))
                        awayMatches="0"
                    }else{
                        binding.awayYes.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding.awayYesTv.setTextColor(Color.WHITE)
                        binding.awayNo.setCardBackgroundColor(Color.WHITE)
                        binding.awayNoTv.setTextColor(Color.parseColor("#F95047"))
                        awayMatches="1"
                    }

                    if (response.data.isKitProvided=="0"){
                        binding.kitNo.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding.kitNoTv.setTextColor(Color.WHITE)
                        binding.kitYes.setCardBackgroundColor(Color.WHITE)
                        binding.kitYesTv.setTextColor(Color.parseColor("#F95047"))
                        kitProvided="0"
                    }else{
                        binding.kitYes.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding.kitYesTv.setTextColor(Color.WHITE)
                        binding.kitNo.setCardBackgroundColor(Color.WHITE)
                        binding.kitNoTv.setTextColor(Color.parseColor("#F95047"))
                        kitProvided="1"
                    }

                    if (response.data.sun!= ""){
                        binding.Scv.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding.Stv.setTextColor(Color.WHITE)
                        binding.Slay.visibility=View.VISIBLE
                        binding.Sstv.text=response.data.sun
                        sun=binding.Sstv.text.toString()
                        SCvColor=false
                        val strs = response.data.sun.split("-").toTypedArray()
                        val t1=strs[0]
                        val t2=strs[1]
                        val timePartsStart = t1.split(":")
                        val timePartsEnd = t2.split(":")
                        val start = timePartsStart[0].toFloat()
                        var end = timePartsEnd[0].toFloat()

                        if (timePartsEnd[1]=="59 pm"){
                            end=24.toFloat()
                        }

                        binding.sundaySlider.setValues(start,end)
                    }
                    else{
                        binding.Slay.visibility=View.GONE
                    }
                    if (response.data.mon!= ""){
                        binding.Mcv.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding.Mtv.setTextColor(Color.WHITE)
                        binding.Mlay.visibility=View.VISIBLE
                        MCvColor=false
                        binding.Mstv.text=response.data.mon
                        mon=binding.Mstv.text.toString()
                        val strs = response.data.mon.split("-").toTypedArray()
                        val t1=strs[0]
                        val t2=strs[1]
                        val timePartsStart = t1.split(":")
                        val timePartsEnd = t2.split(":")
                        val start = timePartsStart[0].toFloat()
                        var end = timePartsEnd[0].toFloat()

                        if (timePartsEnd[1]=="59 pm"){
                            end=24.toFloat()
                        }

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
                        tue=binding.Tstv.text.toString()
                        TCvColor=false

                        val strs = response.data.tue.split("-").toTypedArray()
                        val t1=strs[0]
                        val t2=strs[1]
                        val timePartsStart = t1.split(":")
                        val timePartsEnd = t2.split(":")
                        val start = timePartsStart[0].toFloat()
                        var end = timePartsEnd[0].toFloat()

                        if (timePartsEnd[1]=="59 pm"){
                            end=24.toFloat()
                        }

                        binding.tuesdaySlider.setValues(start,end)
                    } else{
                        binding.Tlay.visibility=View.GONE
                    }
                    if (response.data.wed!= ""){
                        binding.Wcv.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding.Wtv.setTextColor(Color.WHITE)
                        binding.Wlay.visibility=View.VISIBLE
                        binding.Wstv.text=response.data.wed
                        wed=binding.Wstv.text.toString()
                        WCvColor=false
                        val strs = response.data.wed.split("-").toTypedArray()
                        val t1=strs[0]
                        val t2=strs[1]
                        val timePartsStart = t1.split(":")
                        val timePartsEnd = t2.split(":")
                        val start = timePartsStart[0].toFloat()
                        var end = timePartsEnd[0].toFloat()

                        if (timePartsEnd[1]=="59 pm"){
                            end=24.toFloat()
                        }
                        binding.wednesdaySlider.setValues(start,end)
                    } else{
                        binding.Wlay.visibility=View.GONE
                    }
                    if (response.data.thu!= ""){
                        binding.Thcv.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding.Thtv.setTextColor(Color.WHITE)
                        binding.Thlay.visibility=View.VISIBLE
                        binding.Thstv.text=response.data.thu
                        thu=binding.Thstv.text.toString()
                        ThCvColor=false
                        val strs = response.data.thu.split("-").toTypedArray()
                        val t1=strs[0]
                        val t2=strs[1]
                        val timePartsStart = t1.split(":")
                        val timePartsEnd = t2.split(":")
                        val start = timePartsStart[0].toFloat()
                        var end = timePartsEnd[0].toFloat()

                        if (timePartsEnd[1]=="59 pm"){
                            end=24.toFloat()
                        }
                        binding.thursdaySlider.setValues(start,end)
                    } else{
                        binding.Thlay.visibility=View.GONE
                    }
                    if (response.data.fri != ""){
                        binding.Fcv.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding.Ftv.setTextColor(Color.WHITE)
                        binding.Flay.visibility=View.VISIBLE
                        binding.Fstv.text=response.data.fri
                        fri=binding.Fstv.text.toString()
                        FCvColor=false

                        val strs = response.data.fri.split("-").toTypedArray()
                        val t1=strs[0]
                        val t2=strs[1]
                        val timePartsStart = t1.split(":")
                        val timePartsEnd = t2.split(":")
                        val start = timePartsStart[0].toFloat()
                        var end = timePartsEnd[0].toFloat()
                        if (timePartsEnd[1]=="59 pm"){
                            end=24.toFloat()
                        }
                        binding.fridaySlider.setValues(start,end)
                    } else{
                        binding.Flay.visibility=View.GONE
                    }
                    if (response.data.sat!= ""){
                        binding.Sacv.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding.Satv.setTextColor(Color.WHITE)
                        binding.SaLay.visibility=View.VISIBLE
                        binding.Sastv.text=response.data.sat
                        sat=binding.Sastv.text.toString()
                        SACvColor=false

                        val strs = response.data.sat.split("-").toTypedArray()
                        val t1=strs[0]
                        val t2=strs[1]
                        val timePartsStart = t1.split(":")
                        val timePartsEnd = t2.split(":")
                        val start = timePartsStart[0].toFloat()
                        var end = timePartsEnd[0].toFloat()
                        if (timePartsEnd[1]=="59 pm"){
                            end=24.toFloat()
                        }

                        binding.saturdaySlider.setValues(start,end)
                    } else{
                        binding.SaLay.visibility=View.GONE
                    }

//                    if(response.data.sportLevel!=null){
//                         sportList = response.data.sportLevel
//                        intent.putExtra("mylist", myList)
//                    }

                } else {
                    showSnackBar(findViewById(R.id.rootView), response.message)
                }
            }
            else -> {
                showError(resultResponse)
            }
        } as Unit
    }

    private fun editTeamApi(){
        showProgressBar()
        val bos = ByteArrayOutputStream()
        mBitmapImage?.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        val imageBytes = bos.toByteArray()
        lifecycleScope.launchWhenStarted {
            val resultResponse = UserApi(this@AddTeamActivity).editTeam(
                this@AddTeamActivity, imageBytes, imageName.toString(),teamId.toString(),binding.name.text.toString().trim(),binding.genderTv.text.toString().trim(),sportId.toString(),binding.location.text.toString().trim(),teamStandard.toString(),kitProvided.toString(),awayMatches.toString(),sun!!,mon!!,tue!!,wed!!,thu!!,fri!!,sat!!,binding.description.text.toString().trim())
            editTeamApiResult(resultResponse)
        }
    }

    private fun editTeamApiResult(resultResponse: ResultResponse) {
        return when (resultResponse) {
            is ResultResponse.Success<*> -> {
                val response = resultResponse.response as EditTeamResponse
                if (response.success == "true") {
                    hideProgressBar()
                    onBackPressed()
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



}