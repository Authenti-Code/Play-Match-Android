package com.playMatch.ui.signUp.signUpAdapters

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.playMatch.databinding.RvListItemLightBgRvChildBinding
import com.playMatch.ui.signUp.signupModel.SelectChildSPortLightModel
import com.playMatch.ui.signUp.signupModel.SelectChildSPortModel

class SelectLightBgChildSportAdapter(var list: ArrayList<SelectChildSPortLightModel>, var activity: Activity) : RecyclerView.Adapter<SelectLightBgChildSportAdapter.ViewHolder>() {


    private  var selectedPosition=-1
    inner class ViewHolder(val binding: RvListItemLightBgRvChildBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectLightBgChildSportAdapter.ViewHolder {
        val binding = RvListItemLightBgRvChildBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("ResourceAsColor", "SuspiciousIndentation")
    override fun onBindViewHolder(holder: SelectLightBgChildSportAdapter.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.apply {
            val ItemsviewModel = list[position]

            binding.cardView.setOnClickListener {
                selectedPosition=position
                notifyDataSetChanged()
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