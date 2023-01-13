package com.playMatch.ui.fragments.homeFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.playMatch.R
import com.playMatch.databinding.FragmentHomeBinding
import com.playMatch.ui.activity.commonActivity.signUpActivity.signupModel.SelectSportModel
import com.playMatch.ui.fragments.homeFragment.homeAdapter.HomeParentAdapter
import com.playMatch.ui.fragments.homeFragment.homeModel.HomeParentModel


class HomeFragment : Fragment() {
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

    private fun initViews(root: FrameLayout) {
        setAdapters()

    }

    private fun setAdapters() {
        val data = ArrayList<HomeParentModel>()
        HomeParentAdapter = HomeParentAdapter(data, requireActivity())
        binding?.rvParentHome?.adapter = HomeParentAdapter

        for (i in 1..5) {
            data.add(
                HomeParentModel(
                    "New Invites"
                )
            )

        }
    }
}