package com.playMatch.ui.matches.activity.matchDetails

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.playMatch.R
import com.playMatch.controller.constant.IntentConstant
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivityMatchDetailsBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.home.model.HomeChildModel
import com.playMatch.ui.matches.activity.createMatchActivity.CreateMatchActivity
import com.playMatch.ui.matches.adapter.searchPlayersAdapter.SearchPlayersAdapter
import com.saetae.controller.sharedPrefrence.PrefData

class MatchDetailsActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMatchDetailsBinding
    private var adapter: SearchPlayersAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        removeStatusBarFullyBlackIcon()
        super.onCreate(savedInstanceState)
        binding = ActivityMatchDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        adapterView()
    }

    private fun initView() {
        binding.back.setOnClickListener(this)
        binding.searchPlayers.setOnClickListener(this)
        binding.invitedPlayers.setOnClickListener(this)
        binding.accepted.setOnClickListener(this)
        binding.edit.setOnClickListener(this)
    }

    private fun adapterView() {
        val list = ArrayList<HomeChildModel>()
        adapter = SearchPlayersAdapter(list, this)
        binding.rvSearchPlayers.adapter = adapter
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
            R.id.searchPlayers -> {
                binding.searchPlayers.setCardBackgroundColor(Color.parseColor("#B7B7B7"))
                binding.searchPlayersTv.setTextColor(Color.BLACK)
                binding.invitedPlayers.setCardBackgroundColor(Color.WHITE)
                binding.invitedPlayersTv.setTextColor(Color.parseColor("#707070"))
                binding.accepted.setCardBackgroundColor(Color.WHITE)
                binding.acceptedTv.setTextColor(Color.parseColor("#707070"))
            }
            R.id.invitedPlayers -> {
                binding.invitedPlayers.setCardBackgroundColor(Color.parseColor("#B7B7B7"))
                binding.invitedPlayersTv.setTextColor(Color.BLACK)
                binding.searchPlayers.setCardBackgroundColor(Color.WHITE)
                binding.searchPlayersTv.setTextColor(Color.parseColor("#707070"))
                binding.accepted.setCardBackgroundColor(Color.WHITE)
                binding.acceptedTv.setTextColor(Color.parseColor("#707070"))
            }

            R.id.accepted -> {
                binding.accepted.setCardBackgroundColor(Color.parseColor("#B7B7B7"))
                binding.acceptedTv.setTextColor(Color.BLACK)
                binding.invitedPlayers.setCardBackgroundColor(Color.WHITE)
                binding.invitedPlayersTv.setTextColor(Color.parseColor("#707070"))
                binding.searchPlayers.setCardBackgroundColor(Color.WHITE)
                binding.searchPlayersTv.setTextColor(Color.parseColor("#707070"))
            }

            R.id.edit -> {
                val bundle=Bundle()
                bundle.putString(PrefData.EDIT,"edit")
                CommonUtils.performIntentWithBundle(this,CreateMatchActivity::class.java,bundle)
            }
        }

    }
}