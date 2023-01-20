package com.playMatch.ui.matches.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.playMatch.R
import com.playMatch.R.color
import com.playMatch.databinding.FragmentInboxBinding
import com.playMatch.databinding.FragmentMatchesBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.home.model.HomeChildModel
import com.playMatch.ui.inbox.adapter.InboxAdapter
import com.playMatch.ui.matches.adapter.pastAdapter.PastAdapter
import com.playMatch.ui.matches.adapter.upcomingAdapter.UpcomingAdapter
import com.saetae.controller.sharedPrefrence.PrefData


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
    private var pageNo: String = "1"
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
            }
            R.id.past_match -> {
                binding?.upcomingMatch?.setBackgroundResource(color.white)
                binding?.pastMatch?.setBackgroundResource(R.drawable.matches_tab_bg)
                binding?.pastMatch?.setTextColor(Color.WHITE)
                binding?.upcomingMatch?.setTextColor(Color.parseColor("#E65D50"))
                binding?.rvUpcoming?.visibility=View.GONE
                binding?.rvPast?.visibility=View.VISIBLE
            }

        }
        }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun setAdapter() {
        adapter = UpcomingAdapter(list, requireActivity())
        binding?.rvUpcoming?.adapter = adapter
        list.clear()
        for (i in 1..5) {
            list.add(
                HomeChildModel(
                    R.drawable.ic_upcoming_matches,"Rossy Alan"
                )
            )

        }

        pastAdapter = PastAdapter(list, requireActivity())
        binding?.rvPast?.adapter = pastAdapter
        list.clear()
        for (i in 1..5) {
            list.add(
                HomeChildModel(
                    R.drawable.ic_league_match,"Rossy Alan"
                )
            )

        }
    }
}