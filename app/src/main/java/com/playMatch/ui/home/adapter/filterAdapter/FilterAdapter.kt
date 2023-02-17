package com.playMatch.ui.home.adapter.filterAdapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.playMatch.R
import com.playMatch.databinding.RvFilterListItemBinding
import com.playMatch.ui.home.model.HomeChildModel
import com.playMatch.controller.sharedPrefrence.PrefData

class FilterAdapter(var list: ArrayList<HomeChildModel>, var activity: Activity) : RecyclerView.Adapter<FilterAdapter.ViewHolder>() {


    private val USER = 0
    private val IMAGE = 1

    private  var selectedPosition=0
    inner class ViewHolder(val binding: RvFilterListItemBinding) : RecyclerView.ViewHolder(binding.root)




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = RvFilterListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }


    @SuppressLint("ResourceAsColor", "SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.apply {
            val ItemsviewModel = list[position]
            val id= PrefData.getStringPrefs(activity, PrefData.CHECK_BOX,"")

            if (position==0 && selectedPosition==position){
                binding.cardViewTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_metro_cross, 0);
            }else if (selectedPosition==position){
                binding.cardViewTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_metro_cross, 0);
            }else{
                binding.cardViewTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            }


            binding.cardView.setOnClickListener {
                selectedPosition=position
                notifyDataSetChanged()
                binding.cardViewTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_metro_cross, 0);
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