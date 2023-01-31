package com.playMatch.ui.home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.playMatch.R
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.FragmentHomeBinding
import com.playMatch.ui.home.activity.NearByMatches
import com.playMatch.ui.home.activity.NotificationActivity
import com.playMatch.ui.home.adapter.homeAdapter.HomeParentAdapter
import com.playMatch.ui.home.model.HomeParentModel


class HomeFragment : Fragment(),View.OnClickListener {
    private var binding: FragmentHomeBinding? = null
    private var HomeParentAdapter: HomeParentAdapter?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        try {
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
        val data = ArrayList<HomeParentModel>()
        HomeParentAdapter = HomeParentAdapter(data, requireActivity())
        binding?.rvParentHome?.adapter = HomeParentAdapter

        for (i in 1..4) {
            data.add(
                HomeParentModel(
                    "New Invites"
                )
            )

        }
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
}