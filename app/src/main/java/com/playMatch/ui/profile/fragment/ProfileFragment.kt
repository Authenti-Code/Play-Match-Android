package com.playMatch.ui.profile.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.playMatch.R
import com.playMatch.databinding.FragmentInboxBinding
import com.playMatch.databinding.FragmentProfileBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.home.model.HomeChildModel
import com.playMatch.ui.inbox.adapter.InboxAdapter
import com.playMatch.ui.profile.adapter.ProfileSportsAdapter
import com.saetae.controller.sharedPrefrence.PrefData

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment(),View.OnClickListener {
    private var binding: FragmentProfileBinding? = null
    private var adapter: ProfileSportsAdapter? = null
    private var list = ArrayList<HomeChildModel>()
    private var pageNo: String = "1"
    private var totalPages: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
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
    }

    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun setAdapter() {
        adapter = ProfileSportsAdapter(list, requireActivity())
        binding?.rvSports?.adapter = adapter

        for (i in 1..5) {
            list.add(
                HomeChildModel(
                    R.drawable.ic_league_match,"Cricket"
                )
            )

        }
    }
}