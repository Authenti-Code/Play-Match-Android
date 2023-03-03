package com.playMatch.ui.profile.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.playMatch.databinding.RvListItemProfileStatisticsBinding
import com.playMatch.ui.home.model.HomeChildModel
import com.playMatch.ui.home.model.ProfileModel

class ProfileStatisticsAdapter(var list: ArrayList<ProfileModel>, var activity: Activity) : RecyclerView.Adapter<ProfileStatisticsAdapter.ViewHolder>() {


    private val USER = 0
    private val IMAGE = 1
    private  var selectedPosition=-1
    inner class ViewHolder(val binding: RvListItemProfileStatisticsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = RvListItemProfileStatisticsBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    @SuppressLint("ResourceAsColor", "SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.apply {
            val ItemsviewModel = list[position]
                holder.binding.name.text=ItemsviewModel.name
                holder.binding.image.setImageResource(ItemsviewModel.logo)
                holder.binding.number.text=ItemsviewModel.value.toString()
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(Data: List<ProfileModel>) {
        if (list.size > 0) {
            list.clear()
            notifyDataSetChanged()
        }
        list.addAll(Data)
        notifyDataSetChanged()
    }
}