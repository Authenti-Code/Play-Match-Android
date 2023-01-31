package com.playMatch.ui.home.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.playMatch.R
import com.playMatch.databinding.ActivityFilterBinding
import com.playMatch.databinding.ActivityNotificationBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.home.adapter.filterAdapter.FilterAdapter
import com.playMatch.ui.home.adapter.notificationAdapter.NotificationAdapter
import com.playMatch.ui.home.model.HomeChildModel

class FilterActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityFilterBinding
    private var adapter: FilterAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        removeStatusBarFullyBlackIcon()
        super.onCreate(savedInstanceState)
        binding = ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        adapterView()
    }

    private fun initView() {
        binding.back.setOnClickListener(this)
        binding.Apply.setOnClickListener(this)
        binding.sportsTv.setOnClickListener(this)
        binding.abilityTv.setOnClickListener(this)
        binding.distanceTV.setOnClickListener(this)
        binding.genderTv.setOnClickListener(this)
    }

    private fun adapterView() {
        val list = ArrayList<HomeChildModel>()
        adapter = FilterAdapter(list, this)
        binding.rvFilter.adapter = adapter
        for (i in 1..5) {
            list.add(
                HomeChildModel(
                    R.drawable.ic_league_match,"Hockey Match"
                )
            )

        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back -> {
                onBackPressed()
            }
            R.id.Apply -> {
                onBackPressed()
            }

            R.id.sportsTv -> {
            binding.sportsTv.setBackgroundColor(Color.WHITE)
            binding.abilityTv.setBackgroundColor(Color.parseColor("#F1F1F1"))
            binding.distanceTV.setBackgroundColor(Color.parseColor("#F1F1F1"))
            binding.genderTv.setBackgroundColor(Color.parseColor("#F1F1F1"))
            }
            R.id.abilityTv -> {
            binding.abilityTv.setBackgroundColor(Color.WHITE)
            binding.sportsTv.setBackgroundColor(Color.parseColor("#F1F1F1"))
            binding.distanceTV.setBackgroundColor(Color.parseColor("#F1F1F1"))
            binding.genderTv.setBackgroundColor(Color.parseColor("#F1F1F1"))
            }
            R.id.distanceTV -> {
            binding.distanceTV.setBackgroundColor(Color.WHITE)
            binding.sportsTv.setBackgroundColor(Color.parseColor("#F1F1F1"))
            binding.abilityTv.setBackgroundColor(Color.parseColor("#F1F1F1"))
            binding.genderTv.setBackgroundColor(Color.parseColor("#F1F1F1"))
            }
            R.id.genderTv -> {
            binding.genderTv.setBackgroundColor(Color.WHITE)
            binding.sportsTv.setBackgroundColor(Color.parseColor("#F1F1F1"))
            binding.abilityTv.setBackgroundColor(Color.parseColor("#F1F1F1"))
            binding.distanceTV.setBackgroundColor(Color.parseColor("#F1F1F1"))
            }
        }
    }
}