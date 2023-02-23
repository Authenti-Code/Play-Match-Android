package com.playMatch.ui.profile.activity.editSportsAbility

import android.os.Bundle
import android.view.View
import com.playMatch.R
import com.playMatch.controller.`interface`.SelectSportsListener
import com.playMatch.databinding.ActivityEditSportsBinding
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.signUp.signUpAdapters.SelectSportAdapter
import com.playMatch.ui.signUp.signupModel.SelectSportModel
import com.playMatch.controller.sharedPrefrence.PrefData
import com.playMatch.ui.signUp.signupModel.SportsList

class EditSportsActivity : BaseActivity(), View.OnClickListener {
    private lateinit var binding: ActivityEditSportsBinding
    private var adapter: SelectSportAdapter?=null
    private  var list = ArrayList<SportsList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        removeStatusBarFullyBlackIcon()
        super.onCreate(savedInstanceState)
        binding = ActivityEditSportsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        setAdapter()
    }

    private fun setAdapter() {
        if (list.isEmpty()) {
            adapter = SelectSportAdapter(list, this,object:SelectSportsListener{
                override fun onItemClick(position: Int, list: String) {
                }
            })
            binding.rvEditSports.adapter = adapter
//            for (i in 1..5) {
//                list.add(
//                    SelectSportModel(
//                        "Cricket"
//                    )
//                )
//            }
        }else{
            list.clear()
            setAdapter()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        PrefData.remove(this, PrefData.CHECK_BOX)
    }

    private fun initView() {
        binding.update.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.update -> {
                onBackPressed()
            }
        }
    }
}