package com.playMatch.ui.activity.commonActivity.signUpActivity.signUpAdapters

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.playMatch.controller.`interface`.Tasklistner
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.RvChildSelectSportListItemBinding
import com.playMatch.databinding.RvSelectSportItemBinding
import com.playMatch.ui.activity.commonActivity.signUpActivity.signupModel.SelectChildSPortModel
import com.playMatch.ui.activity.commonActivity.signUpActivity.signupModel.SelectSportModel
import com.saetae.controller.sharedPrefrence.PrefData

class SelectSportAdapter(var list: ArrayList<SelectSportModel>, var activity: Activity ) : RecyclerView.Adapter<SelectSportAdapter.ViewHolder>() {

    private var selectedPosition = -1
    private var adapter:SelectChildSportAdapter?=null
    private  var mlist = ArrayList<SelectChildSPortModel>()

    inner class ViewHolder(val binding:RvSelectSportItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectSportAdapter.ViewHolder {
        val binding = RvSelectSportItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectSportAdapter.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.apply {
            val ItemsviewModel = list[position]
            holder.binding.sportName.text = ItemsviewModel.sport


            adapter = SelectChildSportAdapter(mlist, activity,object :Tasklistner {
                override fun onItemClick(position: Int) {

                }
            })
            holder.binding.rvChildSports.adapter = adapter
            mlist.clear()
            for (i in 1..5) {
                mlist.add(
                    SelectChildSPortModel(
                        "Beginner"
                    )
                )

            }
            binding.checkbox.setOnClickListener {
if (binding.checkbox.isChecked){
    PrefData.setStringPrefs(activity,PrefData.CHECK_BOX,"1")
}else{
    PrefData.setStringPrefs(activity,PrefData.CHECK_BOX,"0")
}
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateCommentList(Data: List<SelectSportModel>,mRecyclerview: RecyclerView?) {
        if (list.size > 0) {
            list.clear()
            notifyDataSetChanged()
        }
        list.addAll(Data)
        notifyDataSetChanged()

        mRecyclerview?.postDelayed({
            mRecyclerview.scrollToPosition(itemCount - 1)

        }, 100)
    }
}