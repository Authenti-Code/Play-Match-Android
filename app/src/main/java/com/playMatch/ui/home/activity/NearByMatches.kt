package com.playMatch.ui.home.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.playMatch.R
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivityNotificationBinding
import com.playMatch.databinding.NearByMatchesBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.home.adapter.nearbyMatchesAdapter.NearByMatchesAdapter
import com.playMatch.ui.home.adapter.notificationAdapter.NotificationAdapter
import com.playMatch.ui.home.model.HomeChildModel

class NearByMatches : BaseActivity(), View.OnClickListener {
    private lateinit var binding: NearByMatchesBinding
    private var adapter: NearByMatchesAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        removeStatusBarFullyBlackIcon()
        super.onCreate(savedInstanceState)
        binding = NearByMatchesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        adapterView()
    }

    private fun initView() {
        binding.back.setOnClickListener(this)
        binding.streetMap.setOnClickListener(this)
        binding.filter.setOnClickListener(this)
    }

    private fun adapterView() {
        val list = ArrayList<HomeChildModel>()
        adapter = NearByMatchesAdapter(list, this)
        binding.rvNearbyMatches.adapter = adapter
        for (i in 1..5) {
            list.add(
                HomeChildModel(
                    R.drawable.ic_league_match,"T20 League Match"
                )
            )

        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back -> {
                onBackPressed()
            }
            R.id.streetMap -> {
                CommonUtils.performIntent(this,OnMapNearbyMatchesActivity::class.java)
            }
            R.id.filter -> {
                CommonUtils.performIntent(this,FilterActivity::class.java)
            }
        }

    }
}