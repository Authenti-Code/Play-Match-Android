package com.playMatch.ui.profile.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.playMatch.R
import com.playMatch.databinding.RvChildHomeListItemBinding
import com.playMatch.databinding.RvInboxListBinding
import com.playMatch.databinding.RvListItemProfileStatisticsBinding
import com.playMatch.databinding.RvNotificationListItemBinding
import com.playMatch.databinding.RvProfileListItemSportsAbilityBinding
import com.playMatch.ui.home.model.HomeChildModel
import com.saetae.controller.sharedPrefrence.PrefData

class ProfileStatisticsAdapter(var list: ArrayList<HomeChildModel>, var activity: Activity) : RecyclerView.Adapter<ProfileStatisticsAdapter.ViewHolder>() {


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

//            if (id=="1"){
//                binding.cardView.setCardBackgroundColor(Color.parseColor("#F95047"))
//            }else{
//                binding.cardView.setCardBackgroundColor(Color.parseColor("#80F95047"))
//            }

                holder.binding.name.text=ItemsviewModel.name
                holder.binding.image.setImageResource(ItemsviewModel.logo)





//            binding.cardView.setOnClickListener {
//                selectedPosition=position
//                notifyDataSetChanged()
//            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getItemCount(): Int {
        return list.size
    }




    @SuppressLint("NotifyDataSetChanged")
    fun updateCommentList(Data: List<HomeChildModel>, mRecyclerview: RecyclerView?) {
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