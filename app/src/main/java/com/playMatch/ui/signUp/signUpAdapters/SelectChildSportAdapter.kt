package com.playMatch.ui.signUp.signUpAdapters

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.playMatch.controller.`interface`.RecyclerviewListener
import com.playMatch.databinding.RvChildSelectSportListItemBinding
import com.playMatch.ui.signUp.signupModel.SelectChildSPortModel
import com.playMatch.controller.sharedPrefrence.PrefData

class SelectChildSportAdapter(var list: ArrayList<SelectChildSPortModel>, var activity: Activity,private var recyclerviewListener: RecyclerviewListener) : RecyclerView.Adapter<SelectChildSportAdapter.ViewHolder>() {


private  var selectedPosition=-1
    inner class ViewHolder(val binding: RvChildSelectSportListItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectChildSportAdapter.ViewHolder {
        val binding = RvChildSelectSportListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("ResourceAsColor", "SuspiciousIndentation")
    override fun onBindViewHolder(holder: SelectChildSportAdapter.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.apply {
            val ItemsviewModel = list[position]
            val id= PrefData.getStringPrefs(activity, PrefData.CHECK_BOX,"")
            val selectedLevel= PrefData.getStringPrefs(activity, PrefData.SELECTED_LEVEL,"").toInt()-1
                holder.binding.fitnessLevel.text = ItemsviewModel.fitnessLevel

            if (selectedLevel==position && selectedLevel!=null){
                binding.cardView.setCardBackgroundColor(Color.parseColor("#F95047"))
                binding.fitnessLevel.setTextColor(Color.WHITE)
            }else{
                binding.cardView.setCardBackgroundColor(Color.parseColor("#ffffff"))
                binding.fitnessLevel.setTextColor(Color.parseColor("#F95047"))
            }

            if (selectedPosition==position && id=="1") {
                binding.cardView.setCardBackgroundColor(Color.parseColor("#F95047"))
                binding.fitnessLevel.setTextColor(Color.WHITE)
            } else {
                binding.cardView.setCardBackgroundColor(Color.parseColor("#ffffff"))
                binding.fitnessLevel.setTextColor(Color.parseColor("#F95047"))
            }
            binding.cardView.setOnClickListener {
                selectedPosition=position
                notifyDataSetChanged()
                recyclerviewListener.onItemClick(position + 1, binding.fitnessLevel.text.toString().trim(),true)
                ItemsviewModel.status
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun getItemCount(): Int {
        return list.size
    }


}