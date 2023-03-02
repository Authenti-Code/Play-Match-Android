package com.playMatch.ui.matches.activity.createMatchActivity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.playMatch.R
import com.playMatch.controller.`interface`.BottomSheetListner
import com.playMatch.controller.playMatchAPi.ResultResponse
import com.playMatch.controller.playMatchAPi.apiClasses.UserApi
import com.playMatch.controller.playMatchAPi.postPojoModel.user.createMatch.CreateMatchPost
import com.playMatch.controller.playMatchAPi.postPojoModel.user.showTeam.ShowTeamPost
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivityCreateMatchBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.home.model.HomeChildModel
import com.playMatch.ui.matches.activity.payment.PaymentActivity
import com.playMatch.ui.matches.adapter.selectSportAdapter.SelectMatchSportAdapter
import com.playMatch.ui.matches.adapter.selectTeamAdapter.SelectTeamAdapter
import com.playMatch.controller.sharedPrefrence.PrefData
import com.playMatch.ui.matches.model.createMatch.CreateMatchResponse
import com.playMatch.ui.signUp.signupModel.SportListResponse
import com.playMatch.ui.signUp.signupModel.SportsList
import com.playMatch.ui.teams.model.showTeamDetails.TeamDetailResponse
import com.playMatch.ui.teams.model.teamList.TeamList
import com.playMatch.ui.teams.model.teamList.TeamListResponse
import java.util.*
import kotlin.collections.ArrayList

class CreateMatchActivity : BaseActivity(), View.OnClickListener,BottomSheetListner {
    private lateinit var binding: ActivityCreateMatchBinding
    private var adapter: SelectTeamAdapter? = null
    private var selectSportAdapter: SelectMatchSportAdapter? = null
    private var seletedBottomSheet: BottomSheetDialog? = null

    private var cal: Calendar = Calendar.getInstance()


    private var list = ArrayList<TeamList>()
    private var sportList = ArrayList<SportsList>()

    private var sportId:String?=null
    private var teamId:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        removeStatusBarFullyBlackIcon()
        super.onCreate(savedInstanceState)
        binding = ActivityCreateMatchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentData()
        initView()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initView() {
        binding.back.setOnClickListener(this)
        binding.Continue.setOnClickListener(this)
        binding.selectTeam.setOnClickListener(this)
        binding.selectSport.setOnClickListener(this)
        binding.calendar.setOnClickListener(this)
        binding.startTime.setOnClickListener(this)
        binding.finishTime.setOnClickListener(this)
        binding.intermediate.setOnClickListener(this)
        binding.beginner.setOnClickListener(this)
        binding.experienced.setOnClickListener(this)
        binding.description.setOnTouchListener { view, event ->
            view.parent.requestDisallowInterceptTouchEvent(true)
            if ((event.action and MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                view.parent.requestDisallowInterceptTouchEvent(false)
            }
            return@setOnTouchListener false
        }
        if (type=="edit"){
            binding.title.text="Edit Match"
        }
    }

    private fun getIntentData() {
        if (intent?.extras != null) {
            type = intent.extras?.getString(PrefData.EDIT, "")
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back -> {
                onBackPressed()
            }
            R.id.Continue -> {
                if (type=="edit"){
                    onBackPressed()
                }else {
                    CommonUtils.performIntent(this, PaymentActivity::class.java)
                }
            }
            R.id.selectTeam -> {
                teamsListApi()
            }

            R.id.selectSport -> {
            sportListApi()
            }

            R.id.calendar -> {
                val dateSetListener =
                    DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                        cal.set(Calendar.YEAR, year)
                        cal.set(Calendar.MONTH, monthOfYear)
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        updateDateInView()
                    }

                DatePickerDialog(
                    this,R.style.dialogTheme,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

            R.id.startTime -> {
                val cal = Calendar.getInstance()
                val timeSetListener =
                    TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                        cal.set(Calendar.HOUR_OF_DAY, hour)
                        cal.set(Calendar.MINUTE, minute)
                        binding.startTime.text = SimpleDateFormat("HH:mm").format(cal.time)
                    }
                TimePickerDialog(
                    this,R.style.dialogTheme,
                    timeSetListener,
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    true
                ).show()
            }

            R.id.finishTime -> {
                val cal = Calendar.getInstance()
                val timeSetListener =
                    TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                        cal.set(Calendar.HOUR_OF_DAY, hour)
                        cal.set(Calendar.MINUTE, minute)
                        binding.finishTime.text = SimpleDateFormat("HH:mm").format(cal.time)
                    }
                TimePickerDialog(
                    this,R.style.dialogTheme,
                    timeSetListener,
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    true
                ).show()
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

        }

    }
    private fun selectTeamBottomSheet() {
        val view: View = layoutInflater.inflate(R.layout.bottom_sheet_select_team, null)
        seletedBottomSheet = BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvSelectTeams)

        adapter = SelectTeamAdapter(list, this@CreateMatchActivity,String(),this)
        recyclerView?.adapter = adapter


        seletedBottomSheet?.setCanceledOnTouchOutside(false)
        seletedBottomSheet?.setContentView(view)
        seletedBottomSheet?.show()
    }

    private fun selectSportBottomSheet() {
        val view: View = layoutInflater.inflate(R.layout.bottom_sheet_select_sport, null)
        seletedBottomSheet = BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvSelectSport)
        val close = view.findViewById<ImageView>(R.id.close)

        selectSportAdapter = SelectMatchSportAdapter(sportList, this@CreateMatchActivity,String(),this)
        recyclerView?.adapter = selectSportAdapter

        close.setOnClickListener{
            seletedBottomSheet?.dismiss()
        }
        seletedBottomSheet?.setCanceledOnTouchOutside(false)
        seletedBottomSheet?.setContentView(view)
        seletedBottomSheet?.show()
    }

    override fun bottomSheetListner(ViewType: String,id:String,sportName:String) {

        if (ViewType=="sports"){
            binding.selectSport.setCardBackgroundColor(Color.parseColor("#F95047"))
            binding.selectSportTv.setTextColor(Color.WHITE)
            binding.selectSportTv.text=sportName
            sportId=id
            binding.selectSportTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_circle, 0);
            seletedBottomSheet?.dismiss()
        }else {
            binding.selectTeam.setCardBackgroundColor(Color.parseColor("#F95047"))
            binding.selectTeamTV.setTextColor(Color.WHITE)
            binding.selectTeamTV.text = sportName
            teamId=id
            binding.selectTeamTV.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.edit, 0);
            seletedBottomSheet?.dismiss()

        }
    }

    private fun sportListApi(){
        if (isNetworkAvailable()) {
            CommonUtils.showProgressDialog(this)
            lifecycleScope.launchWhenStarted {
                val resultResponse = UserApi(this@CreateMatchActivity).sportsList()
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

    private fun teamsListApi(){

        if (isNetworkAvailable()) {
            CommonUtils.showProgressDialog(this@CreateMatchActivity)
            lifecycleScope.launchWhenStarted {
                val resultResponse = UserApi(this@CreateMatchActivity).teams()
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
                val response = resultResponse.response as TeamListResponse
                //get data and convert string to json and save data
                if (response.success == "true") {
                    selectTeamBottomSheet()
                    adapter?.updateList(response.data)
                } else {
                    showSnackBar(findViewById(R.id.rootView), response.message)
                }
            }
            else -> {
                showError(resultResponse)
            }
        } as Unit
    }

    private fun updateDateInView() {
        val myFormat = "yyyy-MM-dd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.calendar.text = sdf.format(cal.time)
    }

    private fun createMatchApi(){

        if (isNetworkAvailable()) {
            CommonUtils.showProgressDialog(this)
            lifecycleScope.launchWhenStarted {
                val resultResponse = UserApi(this@CreateMatchActivity).createMatch(CreateMatchPost(teamId.toString()))
                apiCreateMatchResult(resultResponse)
            }
        } else {
            showNetworkSpeedError()
        }
    }

    private fun apiCreateMatchResult(resultResponse: ResultResponse) {
        CommonUtils.hideProgressDialog()
        return when (resultResponse) {
            is ResultResponse.Success<*> -> {
                val response = resultResponse.response as CreateMatchResponse
                //get data and convert string to json and save data
                if (response.success == "true") {
                    onBackPressed()
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