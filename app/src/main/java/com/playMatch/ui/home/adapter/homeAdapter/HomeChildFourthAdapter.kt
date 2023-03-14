package com.playMatch.ui.home.adapter.homeAdapter

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.playMatch.R
import com.playMatch.controller.constant.IntentConstant
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.RvChildHomeFourthPositionListItemBinding
import com.playMatch.ui.home.activity.HomeActivity
import com.playMatch.ui.home.model.HomeChildfourthModel
import com.playMatch.controller.sharedPrefrence.PrefData
import com.playMatch.ui.home.model.homeResponse.TeamList
import com.playMatch.ui.home.model.homeResponse.UpcomingMatchList

class HomeChildFourthAdapter(var list: ArrayList<TeamList>, var activity: Activity) : RecyclerView.Adapter<HomeChildFourthAdapter.ViewHolder>() {


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


            Glide.with(activity)
                .load(ItemsviewModel.image)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .skipMemoryCache(true)
                .priority(Priority.IMMEDIATE)
                .placeholder(R.drawable.new_dummy_profile)
                .listener(object : RequestListener<Drawable> {
                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        dataSource: com.bumptech.glide.load.DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding.progressBar.visibility = View.GONE
                        return false
                    }
                }).into(binding.profileImage)



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
    fun updateList(Data: List<TeamList>) {
        if (list.size > 0) {
            list.clear()
            notifyDataSetChanged()
        }
        list.addAll(Data)
        notifyDataSetChanged()

    }
}