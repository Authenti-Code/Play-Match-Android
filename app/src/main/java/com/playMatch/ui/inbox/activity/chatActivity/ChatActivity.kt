package com.playMatch.ui.inbox.activity.chatActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.playMatch.R
import com.playMatch.databinding.ActivityChatBinding
import com.playMatch.databinding.ActivityNotificationBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.home.adapter.notificationAdapter.NotificationAdapter
import com.playMatch.ui.home.model.HomeChildModel

class ChatActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityChatBinding
    private var adapter: NotificationAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        removeStatusBarFullyBlackIcon()
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        adapterView()
    }

    private fun initView() {
        binding.back.setOnClickListener(this)
    }

    private fun adapterView() {
        val list = ArrayList<HomeChildModel>()
        adapter = NotificationAdapter(list, this)
//        binding.rvNotification.adapter = adapter
//        for (i in 1..5) {
//            list.add(
//                HomeChildModel(
//                    R.drawable.ic_league_match,"Hockey Match"
//                )
//            )
//
//        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back -> {
                onBackPressed()
            }
        }

    }
}