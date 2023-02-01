package com.playMatch.ui.matches.adapter.acceptedPlayerAdapters

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.playMatch.R
import com.playMatch.databinding.RvListItemSearchPlayersBinding
import com.playMatch.databinding.RvNotificationListItemBinding
import com.playMatch.ui.home.model.HomeChildModel
import com.saetae.controller.sharedPrefrence.PrefData

class AcceptedPlayersAdapter(var list: ArrayList<HomeChildModel>, var activity: Activity) : RecyclerView.Adapter<AcceptedPlayersAdapter.ViewHolder>() {


    private val USER = 0
    private val IMAGE = 1

    private  var selectedPosition=-1
    inner class ViewHolder(val binding: RvListItemSearchPlayersBinding) : RecyclerView.ViewHolder(binding.root)




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = RvListItemSearchPlayersBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }


    @SuppressLint("ResourceAsColor", "SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.apply {
            val ItemsviewModel = list[position]
            val id= PrefData.getStringPrefs(activity, PrefData.CHECK_BOX,"")
            binding.cardViewTv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_user_check, 0, 0, 0);
            binding.cardView.setCardBackgroundColor(Color.parseColor("#66B7B7B7"))
            binding.cardView.strokeColor=(Color.parseColor("#66B7B7B7"))
            binding.cardViewTv.text="Accepted"
            binding.cardView.setOnClickListener {
                selectedPosition=position
                notifyDataSetChanged()
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