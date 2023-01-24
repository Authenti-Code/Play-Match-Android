package com.playMatch.ui.inbox.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.RvChildHomeListItemBinding
import com.playMatch.databinding.RvInboxListBinding
import com.playMatch.databinding.RvNotificationListItemBinding
import com.playMatch.ui.home.model.HomeChildModel
import com.playMatch.ui.inbox.activity.chatActivity.ChatActivity
import com.saetae.controller.sharedPrefrence.PrefData

class InboxAdapter(var list: ArrayList<HomeChildModel>, var activity: Activity) : RecyclerView.Adapter<InboxAdapter.ViewHolder>() {


    private val USER = 0
    private val IMAGE = 1

    private  var selectedPosition=-1
    inner class ViewHolder(val binding: RvInboxListBinding) : RecyclerView.ViewHolder(binding.root)




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = RvInboxListBinding
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
            holder.binding.username.text = ItemsviewModel.name




            binding.chatLay.setOnClickListener {
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