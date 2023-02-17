package com.playMatch.ui.matches.activity.createMatchActivity

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.playMatch.R
import com.playMatch.controller.`interface`.BottomSheetListner
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivityCreateMatchBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.home.model.HomeChildModel
import com.playMatch.ui.matches.activity.payment.PaymentActivity
import com.playMatch.ui.matches.adapter.selectSportAdapter.SelectMatchSportAdapter
import com.playMatch.ui.matches.adapter.selectTeamAdapter.SelectTeamAdapter
import com.playMatch.controller.sharedPrefrence.PrefData

class CreateMatchActivity : BaseActivity(), View.OnClickListener,BottomSheetListner {
    private lateinit var binding: ActivityCreateMatchBinding
    private var adapter: SelectTeamAdapter? = null
    private var selectSportAdapter: SelectMatchSportAdapter? = null
    private var seletedBottomSheet: BottomSheetDialog? = null

    private var list = ArrayList<HomeChildModel>()
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
                selectTeamBottomSheet()
            }  R.id.selectSport -> {
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

        }

    }
    private fun selectTeamBottomSheet() {
        val view: View = layoutInflater.inflate(R.layout.bottom_sheet_select_team, null)
        seletedBottomSheet = BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvSelectTeams)

        adapter = SelectTeamAdapter(list, this@CreateMatchActivity,String(),this)
        recyclerView?.adapter = adapter

        for (i in 1..6) {
            list.add(
                HomeChildModel(
                    R.drawable.ic_league_match,"Team ABC"
                )
            )

        }
        seletedBottomSheet?.setCanceledOnTouchOutside(false)
        seletedBottomSheet?.setContentView(view)
        seletedBottomSheet?.show()
    }

    private fun selectSportBottomSheet() {
        val view: View = layoutInflater.inflate(R.layout.bottom_sheet_select_sport, null)
        seletedBottomSheet = BottomSheetDialog(this, R.style.AppBottomSheetDialogTheme)
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvSelectSport)
        val close = view.findViewById<ImageView>(R.id.close)

        selectSportAdapter = SelectMatchSportAdapter(list, this@CreateMatchActivity,String(),this)
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
        }else {
            binding.selectTeam.setCardBackgroundColor(Color.parseColor("#F95047"))
            binding.selectTeamTV.setTextColor(Color.WHITE)
            binding.selectTeamTV.text = "Team ABC"
            binding.selectTeamTV.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.edit, 0);
            seletedBottomSheet?.dismiss()

        }
    }
}