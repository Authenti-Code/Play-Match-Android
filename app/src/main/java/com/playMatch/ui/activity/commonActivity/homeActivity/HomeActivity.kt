package com.playMatch.ui.activity.commonActivity.homeActivity

import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.playMatch.R
import com.playMatch.databinding.ActivityHomeBinding
import com.playMatch.ui.activity.baseActivity.BaseActivity
import com.playMatch.ui.fragments.homeFragment.HomeFragment
import com.saetae.controller.sharedPrefrence.PrefData

class HomeActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityHomeBinding
    private var currentPositionDrawer = -1


    @RequiresApi(33)
    override fun onCreate(savedInstanceState: Bundle?) {
        removeStatusBarFullyBlackIcon()
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        PrefData.setBooleanPrefs(this@HomeActivity, PrefData.IS_USER_LOGIN, true)
    }

    override fun onResume() {
        super.onResume()
        getDot()
    }



    private fun getDot(){
        if (PrefData.getBooleanPrefs(this@HomeActivity, PrefData.KEY_NOTIFICATION_IS_CHAT)) {
//            binding.mChatNotificationView.visibility = View.VISIBLE
        } else {
//            binding.mChatNotificationView.visibility = View.GONE
        }
    }


    private fun performBottomNavigation(position: Int) {
        var fragment: Fragment? = null
        var tagFragment: String? = null


        if (position == 1) {
            if (currentPositionDrawer != position) {
                val HomeFragment = HomeFragment()
                fragment = HomeFragment
                tagFragment = HomeFragment.javaClass.simpleName
            }
        } else if (position == 2) {
//            if (currentPositionDrawer != position) {
//                val calenderFragment = CalenderFragment()
//                fragment = calenderFragment
//                tagFragment = calenderFragment.javaClass.simpleName
//            }

        } else if (position == 3) {
//            if (currentPositionDrawer != position) {
//                val chatFragment = InboxFragment()
//                fragment = chatFragment
//                tagFragment = chatFragment.javaClass.simpleName
//
//            }
        } else if (position == 4) {
//            if (currentPositionDrawer != position) {
//                val farmerProfileFragment = ProfileFragment()
//                fragment = farmerProfileFragment
//                tagFragment = farmerProfileFragment.javaClass.simpleName
//            }
        }

        if (fragment != null) {
            val finalFragment: Fragment = fragment
            val finalTagFragment: String = tagFragment!!
            runOnUiThread {
                try {
                    addFragment(finalFragment, finalTagFragment)
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
        }
        currentPositionDrawer = position
    }

    fun getData() {
        runOnUiThread {
//            if (currentPositionDrawer == 1) {
//                val fragment: HomeFragment? = supportFragmentManager
//                    .findFragmentById(R.id.frameLayoutHomeMain) as HomeFragment?
//                fragment?.createShowApi()
//            }
        }
    }
    private fun addFragment(fragment: Fragment, TagFragment: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayoutHomeMain, fragment, TagFragment)
        fragmentTransaction.commitAllowingStateLoss()
        fragmentManager.executePendingTransactions()
    }
    private fun initViews() {
        performBottomNavigation(position = 1)
        binding.award.setOnClickListener(this)

        binding.homeOff.setOnClickListener(this)
        binding.homeOn.setOnClickListener(this)

        binding.groupOff.setOnClickListener(this)
        binding.groupOn.setOnClickListener(this)

        binding.chatOff.setOnClickListener(this)
        binding.chatOn.setOnClickListener(this)

        binding.profileOff.setOnClickListener(this)
        binding.profileOn.setOnClickListener(this)

//        binding.addEventButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.award -> {
//                CommonUtils.performIntent(this, CreateEventActivity::class.java)
            }
            R.id.homeOff -> {
                binding.homeOn.visibility = View.VISIBLE
                binding.homeOff.visibility = View.GONE
                binding.chatOff.visibility = View.VISIBLE
                binding.chatOn.visibility = View.GONE
                binding.groupOff.visibility = View.VISIBLE
                binding.groupOn.visibility = View.GONE
                binding.profileOff.visibility = View.VISIBLE
                binding.profileOn.visibility = View.GONE

                performBottomNavigation(position = 1)
            }
            R.id.chatOff -> {
                binding.homeOff.visibility = View.VISIBLE
                binding.homeOn.visibility = View.GONE
                binding.chatOn.visibility = View.VISIBLE
                binding.chatOff.visibility = View.GONE
                binding.groupOff.visibility = View.VISIBLE
                binding.groupOn.visibility = View.GONE
                binding.profileOff.visibility = View.VISIBLE
                binding.profileOn.visibility = View.GONE

                performBottomNavigation(position = 2)
            }
            R.id.profileOff -> {

                binding.homeOff.visibility = View.VISIBLE
                binding.homeOn.visibility = View.GONE
                binding.chatOff.visibility = View.VISIBLE
                binding.chatOn.visibility = View.GONE
                binding.groupOff.visibility = View.VISIBLE
                binding.groupOn.visibility = View.GONE
                binding.profileOn.visibility = View.VISIBLE
                binding.profileOff.visibility = View.GONE

                performBottomNavigation(position = 4)

            }
            R.id.groupOff -> {
                binding.homeOff.visibility = View.VISIBLE
                binding.homeOn.visibility = View.GONE
                binding.chatOff.visibility = View.VISIBLE
                binding.chatOn.visibility = View.GONE
                binding.groupOn.visibility = View.VISIBLE
                binding.groupOff.visibility = View.GONE
                binding.profileOff.visibility = View.VISIBLE
                binding.profileOn.visibility = View.GONE
                PrefData.setBooleanPrefs(this@HomeActivity, PrefData.KEY_NOTIFICATION_IS_CHAT, false)
//                binding.mChatNotificationView.visibility = View.GONE
                performBottomNavigation(position = 3)
            }
        }
    }
}