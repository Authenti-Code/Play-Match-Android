package com.playMatch.ui.fragments.homeFragment.homeAdapter

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.playMatch.R
import com.playMatch.databinding.RvChildHomeListItemBinding
import com.playMatch.ui.fragments.homeFragment.homeModel.HomeChildModel
import com.saetae.controller.sharedPrefrence.PrefData

class HomeChildAdapter(var list: ArrayList<HomeChildModel>, var activity: Activity) : RecyclerView.Adapter<HomeChildAdapter.ViewHolder>() {


    private val USER = 0
    private val IMAGE = 1

    private  var selectedPosition=-1
    inner class ViewHolder(val binding: RvChildHomeListItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun getItemViewType(position: Int): Int {
//        if (list[position] is User) {
//            return USER
//        } else if (list[position] is String) {
//            return IMAGE
//        }
        return -1    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeChildAdapter.ViewHolder {

        val binding = RvChildHomeListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        when (viewType) {
            USER -> {

            }
            IMAGE -> {
//                val v2: View = inflater.inflate(R.layout.layout_viewholder2, viewGroup, false)
//                viewHolder = ViewHolder2(v2)
            }
            else -> {
//                val v: View = inflater.inflate(R.layout.simple_list_item_1, viewGroup, false)
//                viewHolder = RecyclerViewSimpleTextViewHolder(v)
            }
        }
        return ViewHolder(binding)
    }

    @SuppressLint("ResourceAsColor", "SuspiciousIndentation")
    override fun onBindViewHolder(holder: HomeChildAdapter.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.apply {
            val ItemsviewModel = list[position]
            val id= PrefData.getStringPrefs(activity, PrefData.CHECK_BOX,"")

//            if (id=="1"){
//                binding.cardView.setCardBackgroundColor(Color.parseColor("#F95047"))
//            }else{
//                binding.cardView.setCardBackgroundColor(Color.parseColor("#80F95047"))
//            }
            holder.binding.name.text = ItemsviewModel.name




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