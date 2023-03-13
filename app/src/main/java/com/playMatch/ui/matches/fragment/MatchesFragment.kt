package com.playMatch.ui.matches.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.playMatch.R
import com.playMatch.R.color
import com.playMatch.controller.playMatchAPi.ResultResponse
import com.playMatch.controller.playMatchAPi.apiClasses.UserApi
import com.playMatch.controller.playMatchAPi.postPojoModel.user.Match.UpcomingMatchPost
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.FragmentMatchesBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.home.model.HomeChildModel
import com.playMatch.ui.matches.activity.createMatchActivity.CreateMatchActivity
import com.playMatch.ui.matches.adapter.pastAdapter.PastAdapter
import com.playMatch.ui.matches.adapter.upcomingAdapter.UpcomingAdapter
import com.playMatch.ui.matches.model.upcomingMatches.UpComingMatchList
import com.playMatch.ui.matches.model.upcomingMatches.UpcomingMatchResponse


/**
 * A simple [Fragment] subclass.
 * Use the [MatchesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MatchesFragment : Fragment(),View.OnClickListener {
    private var binding: FragmentMatchesBinding? = null
    private var adapter: UpcomingAdapter? = null
    private var pastAdapter: PastAdapter? = null
    private var list = ArrayList<HomeChildModel>()
    private var upcomingList = ArrayList<UpComingMatchList>()
    private var pageNo: String = "1"
    private var matchType: String = "u"
    private var totalPages: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMatchesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            (activity as BaseActivity).notifications = true
            initViews()
            setAdapter()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun initViews() {
        binding?.upcomingMatch?.setOnClickListener(this)
        binding?.pastMatch?.setOnClickListener(this)
        binding?.createMatch?.setOnClickListener(this)
    }


    @SuppressLint("ResourceAsColor")
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.upcoming_match -> {
                binding?.pastMatch?.setBackgroundResource(color.white)
                binding?.upcomingMatch?.setBackgroundResource(R.drawable.matches_tab_bg)
                binding?.upcomingMatch?.setTextColor(Color.WHITE)
                binding?.pastMatch?.setTextColor(Color.parseColor("#E65D50"))
                binding?.rvUpcoming?.visibility=View.VISIBLE
                binding?.rvPast?.visibility=View.GONE
                binding?.noMatch?.visibility=View.GONE
                matchType="u"
                upcomingMatchListApi()
            }
            R.id.past_match -> {
                binding?.upcomingMatch?.setBackgroundResource(color.white)
                binding?.pastMatch?.setBackgroundResource(R.drawable.matches_tab_bg)
                binding?.pastMatch?.setTextColor(Color.WHITE)
                binding?.upcomingMatch?.setTextColor(Color.parseColor("#E65D50"))
                binding?.rvUpcoming?.visibility=View.VISIBLE
                binding?.rvPast?.visibility=View.GONE
                binding?.noMatch?.visibility=View.GONE
                matchType="p"
                upcomingMatchListApi()
            }
            R.id.createMatch -> {
                CommonUtils.performIntent(requireActivity(),CreateMatchActivity::class.java)
            }
        }
        }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun setAdapter() {
        adapter = UpcomingAdapter(upcomingList, requireActivity())
        binding?.rvUpcoming?.adapter = adapter

        pastAdapter = PastAdapter(list, requireActivity())
        binding?.rvPast?.adapter = pastAdapter
        list.clear()
        for (i in 1..5) {
            list.add(
                HomeChildModel(
                    R.drawable.match,"T20 League Match"
                )
            )
        }
    }

    private fun upcomingMatchListApi(){

        if ((activity as BaseActivity).isNetworkAvailable()) {
            CommonUtils.showProgressDialog(requireActivity())
            lifecycleScope.launchWhenStarted {
                val resultResponse = UserApi(requireActivity()).upcomingMatch(UpcomingMatchPost(matchType))
                apiUpcomingMatchResult(resultResponse)
            }
        } else {
            (activity as BaseActivity).showNetworkSpeedError()
        }
    }
    private fun apiUpcomingMatchResult(resultResponse: ResultResponse) {
        CommonUtils.hideProgressDialog()
        return when (resultResponse) {
            is ResultResponse.Success<*> -> {
                val response = resultResponse.response as UpcomingMatchResponse
                //get data and convert string to json and save data
                if (response.success == "true") {
                    adapter?.updateList(response.data)
                } else {
                    (activity as BaseActivity).showSnackBar(view?.findViewById(R.id.rootView), response.message)
                }
            }
            else -> {
                (activity as BaseActivity).showError(resultResponse)
            }
        } as Unit
    }

    override fun onResume() {
        super.onResume()
        upcomingMatchListApi()
    }
}