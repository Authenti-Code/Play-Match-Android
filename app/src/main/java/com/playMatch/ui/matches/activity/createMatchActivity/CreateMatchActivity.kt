package com.playMatch.ui.matches.activity.createMatchActivity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.playMatch.R
import com.playMatch.controller.constant.IntentConstant
import com.playMatch.controller.`interface`.BottomSheetListner
import com.playMatch.controller.playMatchAPi.ResultResponse
import com.playMatch.controller.playMatchAPi.apiClasses.UserApi
import com.playMatch.controller.playMatchAPi.postPojoModel.user.Match.EditMatchPost
import com.playMatch.controller.playMatchAPi.postPojoModel.user.createMatch.CreateMatchPost
import com.playMatch.controller.playMatchAPi.postPojoModel.user.selectSportSearchPost.SelectSportSearchPost
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivityCreateMatchBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.matches.adapter.selectSportAdapter.SelectMatchSportAdapter
import com.playMatch.ui.matches.adapter.selectTeamAdapter.SelectTeamAdapter
import com.playMatch.controller.sharedPrefrence.PrefData
import com.playMatch.ui.home.activity.HomeActivity
import com.playMatch.ui.matches.model.createMatch.CreateMatchResponse
import com.playMatch.ui.matches.model.editMatch.EditMatchResponse
import com.playMatch.ui.matches.model.upcomingMatches.UpComingMatchList
import com.playMatch.ui.signUp.signupModel.SportListResponse
import com.playMatch.ui.signUp.signupModel.SportsList
import com.playMatch.ui.teams.model.teamList.TeamList
import com.playMatch.ui.teams.model.teamList.TeamListResponse
import java.util.*
import kotlin.collections.ArrayList

class CreateMatchActivity : BaseActivity(), View.OnClickListener,BottomSheetListner {
    private lateinit var binding: ActivityCreateMatchBinding
    private var adapter: SelectTeamAdapter? = null
    private var selectSportAdapter: SelectMatchSportAdapter? = null
    private var seletedBottomSheet: BottomSheetDialog? = null
    private var progressbar: ProgressBar? = null
    private var recyclerView: RecyclerView? = null
    private var cal: Calendar = Calendar.getInstance()
    private var list = ArrayList<TeamList>()
    private var sportList = ArrayList<SportsList>()
    private var sportId:String?=null
    private var teamId:String?=null

    private var searchText:String?=""
    private var level:String?="3"
    private var matchDetails: UpComingMatchList?=null
    private var fitnessLevel:String?="Intermediate"
    private var userType:Boolean?=false

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
            binding.matchName.setText(matchDetails?.name)
            binding.calendar.text=matchDetails?.matchDate
            binding.startTime.text=matchDetails?.startTime
            binding.finishTime.text=matchDetails?.finishTime
            binding.locationTv.setText(matchDetails?.location)
            binding.genderTv.setText(matchDetails?.gender)
            binding.description.setText(matchDetails?.description)

            if (matchDetails?.standard=="Beginner"){
                binding.beginner.setCardBackgroundColor(Color.parseColor("#F95047"))
                binding.beginnerTv.setTextColor(Color.WHITE)
                binding.intermediate.setCardBackgroundColor(Color.WHITE)
                binding.intermediateTv.setTextColor(Color.parseColor("#F95047"))
                binding.experienced.setCardBackgroundColor(Color.WHITE)
                binding.experiencedTv.setTextColor(Color.parseColor("#F95047"))
                fitnessLevel=binding.beginnerTv.text.toString().trim()
            }

            if (matchDetails?.standard=="Intermediate"){
                binding.intermediate.setCardBackgroundColor(Color.parseColor("#F95047"))
                binding.intermediateTv.setTextColor(Color.WHITE)
                binding.beginner.setCardBackgroundColor(Color.WHITE)
                binding.beginnerTv.setTextColor(Color.parseColor("#F95047"))
                binding.experienced.setCardBackgroundColor(Color.WHITE)
                binding.experiencedTv.setTextColor(Color.parseColor("#F95047"))
                fitnessLevel=binding.intermediateTv.text.toString().trim()
            }

            if (matchDetails?.standard=="Experienced"){
                binding.experienced.setCardBackgroundColor(Color.parseColor("#F95047"))
                binding.experiencedTv.setTextColor(Color.WHITE)
                binding.beginner.setCardBackgroundColor(Color.WHITE)
                binding.beginnerTv.setTextColor(Color.parseColor("#F95047"))
                binding.intermediate.setCardBackgroundColor(Color.WHITE)
                binding.intermediateTv.setTextColor(Color.parseColor("#F95047"))
                fitnessLevel=binding.experiencedTv.text.toString().trim()
            }

            binding.selectSport.setCardBackgroundColor(Color.parseColor("#F95047"))
            binding.selectSportTv.setTextColor(Color.WHITE)
            binding.selectSportTv.text=matchDetails?.sportName
            sportId=matchDetails?.sportId.toString()
            binding.selectSportTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_circle, 0)

            binding.selectTeam.setCardBackgroundColor(Color.parseColor("#F95047"))
            binding.selectTeamTV.setTextColor(Color.WHITE)
            binding.selectTeamTV.text = matchDetails?.teamName
            teamId=matchDetails?.teamId.toString()
            binding.selectTeamTV.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.edit, 0);
            }
    }

    private fun getIntentData() {
        if (intent?.extras != null) {
            type = intent.extras?.getString(PrefData.EDIT, "")
            matchDetails = intent.extras?.getParcelable(PrefData.MATCH_DETAILS)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back -> {
                onBackPressed()
            }
            R.id.Continue -> {
                if (type=="edit"){
                    userType=true
                    editMatchApi()
                }else {
                    showProgressBar()
                    if (binding.matchName.text.toString().trim().isEmpty()) {
                        Toast.makeText(this, R.string.error_match_name, Toast.LENGTH_LONG).show()
                        hideProgressBar()
                    }else if (binding.calendar.text.toString().trim().isEmpty()) {
                        Toast.makeText(this, R.string.error_match_date, Toast.LENGTH_LONG).show()
                        hideProgressBar()
                    }else if (binding.startTime.text.toString().trim().isEmpty()) {
                        Toast.makeText(this, R.string.error_start_time, Toast.LENGTH_LONG).show()
                        hideProgressBar()
                    }else if (binding.finishTime.text.toString().trim().isEmpty()) {
                        Toast.makeText(this, R.string.error_finish_time, Toast.LENGTH_LONG).show()
                        hideProgressBar()
                    }   else if (binding.locationTv.text.toString().trim().isEmpty()) {
                        Toast.makeText(this, R.string.error_location, Toast.LENGTH_LONG).show()
                        hideProgressBar()
                    }else if (binding.genderTv.text.toString().trim().isEmpty()) {
                        Toast.makeText(this, R.string.error_gender, Toast.LENGTH_LONG).show()
                        hideProgressBar()
                    }else if (sportId==null) {
                        Toast.makeText(this, "Select your Sport", Toast.LENGTH_LONG).show()
                        hideProgressBar()
                    }else if (teamId==null) {
                        Toast.makeText(this, "Select your Team", Toast.LENGTH_LONG).show()
                        hideProgressBar()
                    }else if (binding.description.text.toString().trim().isEmpty()) {
                        Toast.makeText(this, R.string.error_location, Toast.LENGTH_LONG).show()
                        hideProgressBar()
                    }
                    else{
                        createMatchApi()
                    }
                }
            }
            R.id.selectTeam -> {
                teamsListApi()
            }

            R.id.selectSport -> {
                list.clear()
                searchText=""
                sportListApi()
                selectSportBottomSheet()
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
                fitnessLevel=binding.beginnerTv.text.toString().trim()
                level="1"
            }
            R.id.intermediate -> {
            binding.intermediate.setCardBackgroundColor(Color.parseColor("#F95047"))
            binding.intermediateTv.setTextColor(Color.WHITE)
            binding.beginner.setCardBackgroundColor(Color.WHITE)
            binding.beginnerTv.setTextColor(Color.parseColor("#F95047"))
            binding.experienced.setCardBackgroundColor(Color.WHITE)
            binding.experiencedTv.setTextColor(Color.parseColor("#F95047"))
                fitnessLevel=binding.intermediateTv.text.toString().trim()
              level="3"
            }

            R.id.experienced -> {
            binding.experienced.setCardBackgroundColor(Color.parseColor("#F95047"))
            binding.experiencedTv.setTextColor(Color.WHITE)
            binding.beginner.setCardBackgroundColor(Color.WHITE)
            binding.beginnerTv.setTextColor(Color.parseColor("#F95047"))
            binding.intermediate.setCardBackgroundColor(Color.WHITE)
            binding.intermediateTv.setTextColor(Color.parseColor("#F95047"))
                fitnessLevel=binding.experiencedTv.text.toString().trim()
                level="4"
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
        recyclerView = view.findViewById(R.id.rvSelectSport)
        progressbar = view.findViewById(R.id.progressBar)
        val close = view.findViewById<ImageView>(R.id.close)
        val search = view.findViewById<AppCompatEditText>(R.id.searchEt)

        selectSportAdapter = SelectMatchSportAdapter(sportList, this@CreateMatchActivity,String(),this)
        recyclerView?.adapter = selectSportAdapter

        search.doAfterTextChanged {
            list.clear()
            searchText=search.text.toString().trim()
            selectSportAdapter?.updateList(java.util.ArrayList())
            sportListApi()
        }

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
            lifecycleScope.launchWhenStarted {
                val resultResponse = UserApi(this@CreateMatchActivity).sportsList(
                    SelectSportSearchPost(searchText!!)
                )
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
         showProgressBar()
        if (isNetworkAvailable()) {
            lifecycleScope.launchWhenStarted {
                val resultResponse = UserApi(this@CreateMatchActivity).createMatch(CreateMatchPost(binding.matchName.text.toString().trim(),binding.calendar.text.toString().trim(),binding.startTime.text.toString().trim(),binding.finishTime.text.toString().trim(),binding.locationTv.text.toString().trim(),binding.genderTv.text.toString().trim(),level,fitnessLevel,sportId,teamId,binding.description.text.toString().trim(),"31.606142","74.885596",))
                apiCreateMatchResult(resultResponse)
            }
        } else {
            showNetworkSpeedError()
        }
    }

    private fun apiCreateMatchResult(resultResponse: ResultResponse) {
       hideProgressBar()
        return when (resultResponse) {
            is ResultResponse.Success<*> -> {
                val response = resultResponse.response as CreateMatchResponse
                //get data and convert string to json and save data
                if (response.success == "true") {
//                    CommonUtils.performIntent(this, PaymentActivity::class.java)
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


    private fun editMatchApi(){
        showProgressBar()
        if (isNetworkAvailable()) {
            lifecycleScope.launchWhenStarted {
                val resultResponse = UserApi(this@CreateMatchActivity).editMatch(EditMatchPost(binding.matchName.text.toString().trim(),binding.calendar.text.toString().trim(),binding.startTime.text.toString().trim(),binding.finishTime.text.toString().trim(),binding.locationTv.text.toString().trim(),binding.genderTv.text.toString().trim(),level,fitnessLevel,sportId,teamId,binding.description.text.toString().trim(),"31.606142","74.885596",matchDetails?.id.toString()))
                apiEditMatchResult(resultResponse)
            }
        } else {
            showNetworkSpeedError()
        }
    }

    private fun apiEditMatchResult(resultResponse: ResultResponse) {
        hideProgressBar()
        return when (resultResponse) {
            is ResultResponse.Success<*> -> {
                val response = resultResponse.response as EditMatchResponse
                //get data and convert string to json and save data
                if (response.success == "true") {
//                    CommonUtils.performIntent(this, PaymentActivity::class.java)
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
    private fun showProgressBar(){
        binding.continueProgressBar.visibility=View.VISIBLE
        binding.ContinueTv.visibility=View.GONE
    }
    private fun hideProgressBar(){
        binding.continueProgressBar.visibility=View.GONE
        binding.ContinueTv.visibility=View.VISIBLE
    }

    override fun onBackPressed() {
//        val user = PrefData.getStringPrefs(this, PrefData.USER_TYPE, "")
        val bundle = Bundle()
        bundle.putString(IntentConstant.TYPE, IntentConstant.EDIT_MATCH)

        if (userType == true && PrefData.getBooleanPrefs(
                this,
                PrefData.KEY_MATCH_TYPE,
                true
            )
        ) {
            CommonUtils.performIntentWithBundleFinish(this@CreateMatchActivity, HomeActivity::class.java,bundle)
        } else {
            CommonUtils.backPress(this)
        }
    }
}