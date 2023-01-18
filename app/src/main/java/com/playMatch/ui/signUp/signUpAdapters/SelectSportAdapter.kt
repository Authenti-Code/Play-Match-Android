package com.playMatch.ui.signUp.signUpAdapters

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.playMatch.databinding.RvSelectSportItemBinding
import com.playMatch.ui.signUp.signupModel.SelectChildSPortModel
import com.playMatch.ui.signUp.signupModel.SelectSportModel
import com.saetae.controller.sharedPrefrence.PrefData

class SelectSportAdapter(var list: ArrayList<SelectSportModel>, var activity: Activity ) : RecyclerView.Adapter<SelectSportAdapter.ViewHolder>() {

    private var selectedPosition = -1
    private var adapter:SelectChildSportAdapter?=null
    private var lightAdapter:SelectLightBgChildSportAdapter?=null
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


            adapter = SelectChildSportAdapter(mlist, activity)
            holder.binding.rvChildSports.adapter = adapter
            mlist.clear()
            for (i in 1..5) {
                mlist.add(
                    SelectChildSPortModel(
                        "Beginner"
                    )
                )

            }

            lightAdapter = SelectLightBgChildSportAdapter(mlist, activity)
            binding.rvLightChildSports.adapter = lightAdapter
            for (i in 1..5) {
                list.add(
                    SelectSportModel(
                        "Cricket"
                    )
                )

            }
            binding.checkbox.setOnClickListener {
if (binding.checkbox.isChecked){
    PrefData.setStringPrefs(activity,PrefData.CHECK_BOX,"1")
    binding.rvChildSports.visibility=View.VISIBLE
    binding.rvLightChildSports.visibility=View.GONE
}else{
    PrefData.setStringPrefs(activity,PrefData.CHECK_BOX,"0")
    binding.rvChildSports.visibility=View.GONE
    binding.rvLightChildSports.visibility=View.VISIBLE
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