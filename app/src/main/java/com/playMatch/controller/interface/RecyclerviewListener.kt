package com.playMatch.controller.`interface`

import com.playMatch.ui.signUp.signupModel.selectedSportModel

interface RecyclerviewListener {
    fun onItemClick(position: Int, viewType: String,levelName:Boolean)
}

interface SelectSportsListener {
    fun onItemClick(position: Int, list: ArrayList<selectedSportModel>)
}

interface ApiUpdateListener {
    fun onItemClick(status:String)
}