package com.playMatch.ui.activity.commonActivity.signUpActivity.signUpAdapters

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.RvSelectSportItemBinding

//class SelectSportAdapter(var list: ArrayList<EventComment>, var activity: Activity, ) : RecyclerView.Adapter<SelectSportAdapter.ViewHolder>() {
//
//    private var selectedPosition = -1
//
//    inner class ViewHolder(val binding:RvSelectSportItemBinding) : RecyclerView.ViewHolder(binding.root)
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsAdapter.ViewHolder {
//        val binding = RvListItemCommentsBinding
//            .inflate(LayoutInflater.from(parent.context), parent, false)
//        return ViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: CommentsAdapter.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
//        holder.apply {
//            val ItemsviewModel = list[position]
//            holder.binding.name.text = ItemsviewModel.user.name
//            holder.binding.time.text = ItemsviewModel.createdAt
//            holder.binding.comment.text = ItemsviewModel.comment
//
//
//            binding.name.setOnClickListener {
//                val bundle= Bundle()
//                bundle.putString(PrefData.OTHER_USER_ID, ItemsviewModel.user_id)
//                selectedPosition = position
//                CommonUtils.performIntentWithBundle(activity,
//                    OtherUserProfileActivity::class.java,bundle)
//            }
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return list.size
//    }
//
//    @SuppressLint("NotifyDataSetChanged")
//    fun updateCommentList(Data: List<EventComment>,mRecyclerview: RecyclerView?) {
//        if (list.size > 0) {
//            list.clear()
//            notifyDataSetChanged()
//        }
//        list.addAll(Data)
//        notifyDataSetChanged()
//
//        mRecyclerview?.postDelayed({
//            mRecyclerview.scrollToPosition(itemCount - 1)
//
//        }, 100)
//    }
//}