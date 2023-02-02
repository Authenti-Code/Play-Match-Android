package com.playMatch.ui.matches.adapter.upcomingAdapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.RvMatchesListItemBinding
import com.playMatch.ui.home.model.HomeChildModel
import com.playMatch.ui.inbox.activity.chatActivity.ChatActivity
import com.playMatch.ui.matches.activity.matchDetails.MatchDetailsActivity

class UpcomingAdapter(var list: ArrayList<HomeChildModel>, var activity: Activity) : RecyclerView.Adapter<UpcomingAdapter.ViewHolder>() {


    private  var selectedPosition=-1
    inner class ViewHolder(val binding: RvMatchesListItemBinding) : RecyclerView.ViewHolder(binding.root)




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = RvMatchesListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }


    @SuppressLint("ResourceAsColor", "SuspiciousIndentation", "SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.apply {
            val itemsViewModel = list[position]

//            if (id=="1"){
//                binding.cardView.setCardBackgroundColor(Color.parseColor("#F95047"))
//            }else{
//                binding.cardView.setCardBackgroundColor(Color.parseColor("#80F95047"))
//            }
            holder.binding.name.text = itemsViewModel.name
            if (position==1){
                binding.genderTv.text="Female"
                binding.host.visibility=View.GONE
                binding.chat.visibility=View.VISIBLE
            }

            binding.cardView.setOnClickListener {
                selectedPosition=position
                notifyDataSetChanged()
                CommonUtils.performIntent(activity,MatchDetailsActivity::class.java)
            }

            binding.chat.setOnClickListener {
                selectedPosition=position
                notifyDataSetChanged()
                CommonUtils.performIntent(activity,ChatActivity::class.java)
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