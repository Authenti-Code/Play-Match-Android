package com.playMatch.ui.home.adapter.homeAdapter

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.playMatch.controller.constant.IntentConstant
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.RvChildHomeFourthPositionListItemBinding
import com.playMatch.ui.home.activity.HomeActivity
import com.playMatch.ui.home.model.HomeChildModel
import com.saetae.controller.sharedPrefrence.PrefData

class HomeChildFourthAdapter(var list: ArrayList<HomeChildModel>, var activity: Activity) : RecyclerView.Adapter<HomeChildFourthAdapter.ViewHolder>() {


    private  var selectedPosition=-1
    inner class ViewHolder(val binding: RvChildHomeFourthPositionListItemBinding) : RecyclerView.ViewHolder(binding.root)




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = RvChildHomeFourthPositionListItemBinding
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




            binding.profileImage.setOnClickListener {
                selectedPosition=position
                notifyDataSetChanged()
                val bundle = Bundle()
                bundle.putString(IntentConstant.TYPE, IntentConstant.FRAGMENT_Teams)
                CommonUtils.performIntentWithBundleFinish(
                    activity,
                    HomeActivity::class.java,
                    bundle
                )
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