package com.playMatch.ui.profile.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.playMatch.databinding.RvProfileListItemSportsAbilityBinding
import com.playMatch.ui.home.model.HomeChildModel
import com.playMatch.controller.sharedPrefrence.PrefData
import com.playMatch.ui.profile.model.profile.SportLevel

class ProfileSportsAdapter(var list: ArrayList<SportLevel>, var activity: Activity) : RecyclerView.Adapter<ProfileSportsAdapter.ViewHolder>() {


    private val USER = 0
    private val IMAGE = 1

    private  var selectedPosition=-1
    inner class ViewHolder(val binding: RvProfileListItemSportsAbilityBinding) : RecyclerView.ViewHolder(binding.root)




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = RvProfileListItemSportsAbilityBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }


    @SuppressLint("ResourceAsColor", "SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.apply {
            val ItemsviewModel = list[position]
            val id= PrefData.getStringPrefs(activity, PrefData.CHECK_BOX,"")

//            if (id=="1"){
//                binding.cardView.setCardBackgroundColor(Color.parseColor("#F95047"))
//            }else{
//                binding.cardView.setCardBackgroundColor(Color.parseColor("#80F95047"))
//            }

            if (list!=null) {
                holder.binding.sportName.text = ItemsviewModel.sportName.toString()
                holder.binding.sportLevel.text = ItemsviewModel.sportLevel
            }




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
    fun updateSportsList(Data: List<SportLevel>, mRecyclerview: RecyclerView?) {
        if (list.size > 0) {
            list.clear()
            notifyDataSetChanged()
        }
        list.addAll(Data)
        notifyDataSetChanged()

//        mRecyclerview?.postDelayed({
//            mRecyclerview.scrollToPosition(itemCount - 1)

//        }, 100)
    }



}