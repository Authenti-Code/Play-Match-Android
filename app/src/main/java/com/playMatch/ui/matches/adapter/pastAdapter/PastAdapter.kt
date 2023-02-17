package com.playMatch.ui.matches.adapter.pastAdapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.RvMatchesListItemBinding
import com.playMatch.ui.home.model.HomeChildModel
import com.playMatch.ui.matches.activity.matchDetails.MatchDetailsActivity
import com.playMatch.controller.sharedPrefrence.PrefData

class PastAdapter(var list: ArrayList<HomeChildModel>, var activity: Activity) : RecyclerView.Adapter<PastAdapter.ViewHolder>() {


    private val USER = 0
    private val IMAGE = 1

    private  var selectedPosition=-1
    inner class ViewHolder(val binding: RvMatchesListItemBinding) : RecyclerView.ViewHolder(binding.root)




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = RvMatchesListItemBinding
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
            holder.binding.name.text = ItemsviewModel.name
            holder.binding.logo.setImageResource(ItemsviewModel.logo)




            binding.cardView.setOnClickListener {
                selectedPosition=position
                notifyDataSetChanged()
                CommonUtils.performIntent(activity, MatchDetailsActivity::class.java)
            }
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