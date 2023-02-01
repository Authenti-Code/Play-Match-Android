package com.playMatch.ui.matches.activity.matchDetails

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.playMatch.R
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivityMatchDetailsBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.home.model.HomeChildModel
import com.playMatch.ui.matches.activity.createMatchActivity.CreateMatchActivity
import com.playMatch.ui.matches.adapter.acceptedPlayerAdapters.AcceptedPlayersAdapter
import com.playMatch.ui.matches.adapter.invitedPlayersAdapter.InvitedPlayersAdapter
import com.playMatch.ui.matches.adapter.searchPlayersAdapter.SearchPlayersAdapter
import com.saetae.controller.sharedPrefrence.PrefData

@Suppress("DEPRECATION")
class MatchDetailsActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMatchDetailsBinding
    private var searchAdapter: SearchPlayersAdapter?=null
    private var invitedAdapter: InvitedPlayersAdapter?=null
    private var acceptedAdapter: AcceptedPlayersAdapter?=null

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
        //searchAdapter
        val list = ArrayList<HomeChildModel>()
        searchAdapter = SearchPlayersAdapter(list, this)
        binding.rvSearchPlayers.adapter = searchAdapter
        for (i in 1..5) {
            list.add(
                HomeChildModel(
                    R.drawable.ic_league_match,"Hockey Match"
                )
            )
        }

        //Accepted Adapter
        list.clear()
        acceptedAdapter = AcceptedPlayersAdapter(list, this)
        binding.rvAccepted.adapter = acceptedAdapter
        for (i in 1..5) {
            list.add(
                HomeChildModel(
                    R.drawable.ic_league_match,"Hockey Match"
                )
            )
        }

        //InvitedAdapter
        list.clear()
        invitedAdapter = InvitedPlayersAdapter(list, this)
        binding.rvInvitedPlayers.adapter = invitedAdapter
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
                binding.searchPlayers.setCardBackgroundColor(Color.parseColor("#66B7B7B7"))
                binding.searchPlayers.strokeColor=(Color.parseColor("#66B7B7B7"))
                binding.searchPlayersTv.setTextColor(Color.BLACK)
                binding.invitedPlayers.setCardBackgroundColor(Color.WHITE)
                binding.invitedPlayersTv.setTextColor(Color.parseColor("#707070"))
                binding.accepted.setCardBackgroundColor(Color.WHITE)
                binding.acceptedTv.setTextColor(Color.parseColor("#707070"))
                binding.rvSearchPlayers.visibility=View.VISIBLE
                binding.rvInvitedPlayers.visibility=View.GONE
                binding.rvAccepted.visibility=View.GONE
            }

            R.id.invitedPlayers -> {
                binding.invitedPlayers.setCardBackgroundColor(Color.parseColor("#66B7B7B7"))
                binding.invitedPlayers.strokeColor=(Color.parseColor("#66B7B7B7"))
                binding.invitedPlayersTv.setTextColor(Color.BLACK)
                binding.searchPlayers.setCardBackgroundColor(Color.WHITE)
                binding.searchPlayersTv.setTextColor(Color.parseColor("#707070"))
                binding.accepted.setCardBackgroundColor(Color.WHITE)
                binding.acceptedTv.setTextColor(Color.parseColor("#707070"))
                binding.rvInvitedPlayers.visibility=View.VISIBLE
                binding.rvSearchPlayers.visibility=View.GONE
                binding.rvAccepted.visibility=View.GONE
            }

            R.id.accepted -> {
                binding.accepted.setCardBackgroundColor(Color.parseColor("#66B7B7B7"))
                binding.accepted.strokeColor=(Color.parseColor("#66B7B7B7"))
                binding.acceptedTv.setTextColor(Color.BLACK)
                binding.invitedPlayers.setCardBackgroundColor(Color.WHITE)
                binding.invitedPlayersTv.setTextColor(Color.parseColor("#707070"))
                binding.searchPlayers.setCardBackgroundColor(Color.WHITE)
                binding.searchPlayersTv.setTextColor(Color.parseColor("#707070"))
                binding.rvAccepted.visibility=View.VISIBLE
                binding.rvSearchPlayers.visibility=View.GONE
                binding.rvInvitedPlayers.visibility=View.GONE
            }

            R.id.edit -> {
                val bundle=Bundle()
                bundle.putString(PrefData.EDIT,"edit")
                CommonUtils.performIntentWithBundle(this,CreateMatchActivity::class.java,bundle)
            }
        }
    }
}