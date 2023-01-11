package com.playMatch.ui.activity.commonActivity.signUpActivity.signUpAdapters

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.playMatch.controller.`interface`.Tasklistner
import com.playMatch.databinding.ListItemLightBgRvChildBinding
import com.playMatch.databinding.RvChildSelectSportListItemBinding
import com.playMatch.ui.activity.commonActivity.signUpActivity.signupModel.SelectChildSPortModel
import com.saetae.controller.sharedPrefrence.PrefData

class SelectLightBgChildSportAdapter(var list: ArrayList<SelectChildSPortModel>, var activity: Activity) : RecyclerView.Adapter<SelectLightBgChildSportAdapter.ViewHolder>() {


    private  var selectedPosition=-1
    inner class ViewHolder(val binding: ListItemLightBgRvChildBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectLightBgChildSportAdapter.ViewHolder {
        val binding = ListItemLightBgRvChildBinding
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



    @SuppressLint("NotifyDataSetChanged")
    fun updateCommentList(Data: List<SelectChildSPortModel>, mRecyclerview: RecyclerView?) {
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