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
        binding.addAwardButton.setOnClickListener(this)

        binding.homeeventoff.setOnClickListener(this)
        binding.homeeventon.setOnClickListener(this)

        binding.groupoff.setOnClickListener(this)
        binding.groupOn.setOnClickListener(this)

        binding.messageoff.setOnClickListener(this)
        binding.messageOn.setOnClickListener(this)

        binding.profileoff.setOnClickListener(this)
        binding.profileOn.setOnClickListener(this)

//        binding.addEventButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.addAwardButton -> {
//                CommonUtils.performIntent(this, CreateEventActivity::class.java)
            }
            R.id.homeeventoff -> {
                binding.homeeventon.visibility = View.VISIBLE
                binding.homeeventoff.visibility = View.GONE
                binding.messageoff.visibility = View.VISIBLE
                binding.messageOn.visibility = View.GONE
                binding.groupoff.visibility = View.VISIBLE
                binding.groupOn.visibility = View.GONE
                binding.profileoff.visibility = View.VISIBLE
                binding.profileOn.visibility = View.GONE

                performBottomNavigation(position = 1)
            }
            R.id.messageoff -> {
                binding.homeeventoff.visibility = View.VISIBLE
                binding.homeeventon.visibility = View.GONE
                binding.messageOn.visibility = View.VISIBLE
                binding.messageoff.visibility = View.GONE
                binding.groupoff.visibility = View.VISIBLE
                binding.groupOn.visibility = View.GONE
                binding.profileoff.visibility = View.VISIBLE
                binding.profileOn.visibility = View.GONE

                performBottomNavigation(position = 2)
            }
            R.id.profileoff -> {

                binding.homeeventoff.visibility = View.VISIBLE
                binding.homeeventon.visibility = View.GONE
                binding.messageoff.visibility = View.VISIBLE
                binding.messageOn.visibility = View.GONE
                binding.groupoff.visibility = View.VISIBLE
                binding.groupOn.visibility = View.GONE
                binding.profileOn.visibility = View.VISIBLE
                binding.profileoff.visibility = View.GONE

                performBottomNavigation(position = 4)

            }
            R.id.groupoff -> {
                binding.homeeventoff.visibility = View.VISIBLE
                binding.homeeventon.visibility = View.GONE
                binding.messageoff.visibility = View.VISIBLE
                binding.messageOn.visibility = View.GONE
                binding.groupOn.visibility = View.VISIBLE
                binding.groupoff.visibility = View.GONE
                binding.profileoff.visibility = View.VISIBLE
                binding.profileOn.visibility = View.GONE
                PrefData.setBooleanPrefs(this@HomeActivity, PrefData.KEY_NOTIFICATION_IS_CHAT, false)
//                binding.mChatNotificationView.visibility = View.GONE
                performBottomNavigation(position = 3)
            }
        }
    }
}