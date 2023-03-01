package com.playMatch.ui.teams.fragment

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.playMatch.R
import com.playMatch.controller.playMatchAPi.ResultResponse
import com.playMatch.controller.playMatchAPi.apiClasses.UserApi
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.FragmentTeamsBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.home.model.HomeChildModel
import com.playMatch.ui.teams.activity.AddTeamActivity
import com.playMatch.ui.teams.adapter.TeamsAdapter
import com.playMatch.controller.sharedPrefrence.PrefData
import com.playMatch.ui.profile.model.profile.ProfileResponse
import com.playMatch.ui.teams.model.teamList.TeamList
import com.playMatch.ui.teams.model.teamList.TeamListResponse


class TeamsFragment : Fragment(),View.OnClickListener {
    private var binding: FragmentTeamsBinding? = null
    private var adapter: TeamsAdapter? = null
    private var list = ArrayList<TeamList>()
    private var pageNo: String = "1"
    private var totalPages: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTeamsBinding.inflate(inflater, container, false)
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
        PrefData.setBooleanPrefs(requireActivity(), PrefData.KEY_NOTIFICATION_IS_CHAT, false)
        binding?.addTeam?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.addTeam -> {
       CommonUtils.performIntent(requireActivity(),AddTeamActivity::class.java)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun setAdapter() {
        adapter = TeamsAdapter(list, requireActivity())
        binding?.rvTeams?.adapter = adapter
    }

    private fun teamsListApi(){

        if ((activity as BaseActivity).isNetworkAvailable()) {
            CommonUtils.showProgressDialog(requireActivity())
            lifecycleScope.launchWhenStarted {
                val resultResponse = UserApi(requireActivity()).teams()
                apiResult(resultResponse)
            }
        } else {
            (activity as BaseActivity).showNetworkSpeedError()
        }
    }


    private fun apiResult(resultResponse: ResultResponse) {
        CommonUtils.hideProgressDialog()
        return when (resultResponse) {
            is ResultResponse.Success<*> -> {
                val response = resultResponse.response as TeamListResponse
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
        teamsListApi()
    }
}