package com.playMatch.ui.profile.activity.editProfile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.playMatch.R
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivityEditProfileBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.home.model.HomeChildModel
import com.playMatch.ui.profile.activity.editSportsAbility.EditSportsActivity
import com.playMatch.ui.profile.adapter.ProfileSportsAdapter

class EditProfileActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityEditProfileBinding
    private var profileSportsAdapter: ProfileSportsAdapter? = null
    private var list = ArrayList<HomeChildModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        removeStatusBarFullyBlackIcon()
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        setAdapter()
    }

    private fun initView() {
        binding.back.setOnClickListener(this)
        binding.update.setOnClickListener(this)
        binding.beginner.setOnClickListener(this)
        binding.intermediate.setOnClickListener(this)
        binding.experienced.setOnClickListener(this)
        binding.editSports.setOnClickListener(this)
        binding.mondaySlider.setValues(1.0f,5.0f)
        binding.tuesdaySlider.setValues(1.0f,5.0f)

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
}