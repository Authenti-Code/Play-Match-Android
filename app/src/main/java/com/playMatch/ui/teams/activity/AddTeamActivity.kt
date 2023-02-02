package com.playMatch.ui.teams.activity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.playMatch.R
import com.playMatch.controller.`interface`.BottomSheetListner
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivityAddTeamBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.home.model.HomeChildModel
import com.playMatch.ui.location.activity.LocationActivity
import com.playMatch.ui.matches.adapter.selectSportAdapter.SelectMatchSportAdapter
import com.saetae.controller.sharedPrefrence.PrefData

class AddTeamActivity : BaseActivity(), View.OnClickListener, BottomSheetListner {
    private lateinit var binding: ActivityAddTeamBinding
    private var CvColor:Boolean=true
    private var selectSportAdapter: SelectMatchSportAdapter? = null
    private var seletedBottomSheet: BottomSheetDialog? = null
    private var list = ArrayList<HomeChildModel>()


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
        binding.Stv.setOnClickListener(this)
        binding.Scv.setOnClickListener(this)
        binding.Mcv.setOnClickListener(this)
        binding.Tcv.setOnClickListener(this)
        binding.Wcv.setOnClickListener(this)
        binding.Thcv.setOnClickListener(this)
        binding.Fcv.setOnClickListener(this)
        binding.Scv.setOnClickListener(this)
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
        binding.mondaySlider.setValues(0.0f,5.0f)
        binding.tuesdaySlider.setValues(0.0f,5.0f)

        if (type=="edit"){
            binding.title.text="Edit Team"
        }
    }

    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.Scv -> {
                if (CvColor==true){
                    binding.Scv.setCardBackgroundColor(Color.parseColor("#F95047"))
                    binding.Stv.setTextColor(Color.WHITE)
                    CvColor=false

                }else{
                    binding.Scv.setCardBackgroundColor(Color.WHITE)
                    binding.Stv.setTextColor(Color.parseColor("#F95047"))
                    CvColor=true
                }
            }
            R.id.back -> {
                onBackPressed()
            }

            R.id.selectSport -> {
                selectSportBottomSheet()
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

        for (i in 1..6) {
            list.add(
                HomeChildModel(
                    R.drawable.ic_league_match,"Team ABC"
                )
            )

        }
        close.setOnClickListener{
            seletedBottomSheet?.dismiss()
        }
        seletedBottomSheet?.setCanceledOnTouchOutside(false)
        seletedBottomSheet?.setContentView(view)
        seletedBottomSheet?.show()
    }

    override fun bottomSheetListner(ViewType: String) {

        if (ViewType=="sports"){
            binding.selectSport.setCardBackgroundColor(Color.parseColor("#F95047"))
            binding.selectSportTv.setTextColor(Color.WHITE)
            binding.selectSportTv.text="Cricket"
            binding.selectSportTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_circle, 0);
            seletedBottomSheet?.dismiss()
        }
    }
}