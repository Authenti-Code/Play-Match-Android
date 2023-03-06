package com.playMatch.ui.matches.activity.matchDetails

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.playMatch.R
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.ActivityMatchDetailsBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.home.model.HomeChildModel
import com.playMatch.ui.matches.activity.createMatchActivity.CreateMatchActivity
import com.playMatch.ui.matches.adapter.acceptedPlayerAdapters.AcceptedPlayersAdapter
import com.playMatch.ui.matches.adapter.invitedPlayersAdapter.InvitedPlayersAdapter
import com.playMatch.ui.matches.adapter.searchPlayersAdapter.SearchPlayersAdapter
import com.playMatch.controller.sharedPrefrence.PrefData
import com.playMatch.ui.matches.model.upcomingMatches.UpComingMatchList

@Suppress("DEPRECATION")
class MatchDetailsActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMatchDetailsBinding
    private var searchAdapter: SearchPlayersAdapter?=null
    private var invitedAdapter: InvitedPlayersAdapter?=null
    private var acceptedAdapter: AcceptedPlayersAdapter?=null
    private var matchDetails: UpComingMatchList?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        removeStatusBarFullyBlackIcon()
        super.onCreate(savedInstanceState)
        binding = ActivityMatchDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getIntentData()
        initView()
        adapterView()
    }

    private fun getIntentData() {
        matchDetails = intent.extras?.getParcelable(PrefData.MATCH_DETAILS)
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        binding.back.setOnClickListener(this)
        binding.searchPlayers.setOnClickListener(this)
        binding.invitedPlayers.setOnClickListener(this)
        binding.accepted.setOnClickListener(this)
        binding.edit.setOnClickListener(this)

        binding.name.text=matchDetails?.name
        binding.date.text=matchDetails?.matchDate
        binding.time.text= "${matchDetails?.startTime} - ${matchDetails?.finishTime}"
        binding.locationTv.text=matchDetails?.location
        binding.fitnessLevel.text=matchDetails?.standard
        binding.sportsName.text=matchDetails?.sportName
        binding.gender.text=matchDetails?.gender

        Glide.with(this)
            .load(matchDetails?.image)
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
                    binding.progressBar.visibility = View.GONE
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressBar.visibility = View.GONE
                    return false
                }
            }).into(binding.logo)
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
                bundle.putParcelable(PrefData.MATCH_DETAILS,matchDetails)
                CommonUtils.performIntentWithBundle(this,CreateMatchActivity::class.java,bundle)
            }
        }
    }
}