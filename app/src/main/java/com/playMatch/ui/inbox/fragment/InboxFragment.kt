package com.playMatch.ui.inbox.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.playMatch.R
import com.playMatch.databinding.FragmentInboxBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.home.model.HomeChildModel
import com.playMatch.ui.inbox.adapter.InboxAdapter
import com.playMatch.controller.sharedPrefrence.PrefData


class InboxFragment : Fragment(),View.OnClickListener {
    private var binding: FragmentInboxBinding? = null
    private var adapter: InboxAdapter? = null
    private var list = ArrayList<HomeChildModel>()
    private var pageNo: String = "1"
    private var totalPages: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInboxBinding.inflate(inflater, container, false)
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
        adapter = InboxAdapter(list, requireActivity())
        binding?.rvInbox?.adapter = adapter

        for (i in 1..5) {
            list.add(
                HomeChildModel(
                    R.drawable.ic_league_match,"Rossy Alan"
                )
            )

        }
    }
}