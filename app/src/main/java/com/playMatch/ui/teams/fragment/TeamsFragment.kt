package com.playMatch.ui.teams.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.playMatch.R
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.FragmentInboxBinding
import com.playMatch.databinding.FragmentTeamsBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.home.model.HomeChildModel
import com.playMatch.ui.inbox.adapter.InboxAdapter
import com.playMatch.ui.teams.activity.AddTeamActivity
import com.playMatch.ui.teams.adapter.TeamsAdapter
import com.saetae.controller.sharedPrefrence.PrefData

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TeamsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TeamsFragment : Fragment(),View.OnClickListener {
    private var binding: FragmentTeamsBinding? = null
    private var adapter: TeamsAdapter? = null
    private var list = ArrayList<HomeChildModel>()
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
       CommonUtils?.performIntent(requireActivity(),AddTeamActivity::class.java)
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

        for (i in 1..6) {
            list.add(
                HomeChildModel(
                    R.drawable.ic_league_match,"Team ABC"
                )
            )

        }
    }
}