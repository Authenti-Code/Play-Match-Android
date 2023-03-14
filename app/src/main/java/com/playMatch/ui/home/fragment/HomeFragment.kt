package com.playMatch.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.lifecycleScope
import com.playMatch.R
import com.playMatch.controller.playMatchAPi.ResultResponse
import com.playMatch.controller.playMatchAPi.apiClasses.UserApi
import com.playMatch.controller.playMatchAPi.postPojoModel.user.Match.MatchInvitePost
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.FragmentHomeBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.home.activity.NearByMatches
import com.playMatch.ui.home.activity.NotificationActivity
import com.playMatch.ui.home.adapter.homeAdapter.HomeParentAdapter
import com.playMatch.ui.home.model.HomeParentModel
import com.playMatch.ui.home.model.homeResponse.HomeList
import com.playMatch.ui.home.model.homeResponse.HomeResponse
import com.playMatch.ui.matches.model.invitePlayer.InvitePlayerResponse


class HomeFragment : Fragment(),View.OnClickListener {
    private var binding: FragmentHomeBinding? = null
    private var HomeParentAdapter: HomeParentAdapter?=null
    private var data = ArrayList<HomeList>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        try {
            homeApi()
            initViews(binding?.root!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return binding?.root!!

    }

    private fun initViews(root: CoordinatorLayout) {
        setAdapters()
        binding?.notification?.setOnClickListener(this)
        binding?.globe?.setOnClickListener(this)

    }

    private fun setAdapters() {
        HomeParentAdapter = HomeParentAdapter(data, requireActivity())
        binding?.rvParentHome?.adapter = HomeParentAdapter

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.notification -> {
                CommonUtils.performIntent(requireActivity(), NotificationActivity::class.java)
            }
            R.id.globe -> {
                CommonUtils.performIntent(requireActivity(), NearByMatches::class.java)
            }
        }
    }

    private fun homeApi(){
        CommonUtils.showProgressDialog(requireActivity())
        if ((activity as BaseActivity).isNetworkAvailable()) {
            (activity as BaseActivity).lifecycleScope.launchWhenStarted {
                val resultResponse = UserApi(requireActivity()).home()
                apiHomeResult(resultResponse)
            }
        } else {
            (activity as BaseActivity).showNetworkSpeedError()
        }
    }
    private fun apiHomeResult(resultResponse: ResultResponse) {
        CommonUtils.hideProgressDialog()
        return when (resultResponse) {
            is ResultResponse.Success<*> -> {
                val response = resultResponse.response as HomeResponse
                //get data and convert string to json and save data
                if (response.success == "true") {

                    HomeParentAdapter?.updateList(response.data)
                    binding?.name?.text= "Welcome,"+response.data[0].name
                    binding?.teamCount?.text= response.data[0].teamsCount.toString()
                    binding?.matcheCount?.text= response.data[0].matchCount.toString()
                } else {
                    (activity as BaseActivity).showSnackBar(requireView().findViewById(R.id.rootView), response.message)
                }
            }
            else -> {
                (activity as BaseActivity).showError(resultResponse)
            }
        } as Unit
    }
}