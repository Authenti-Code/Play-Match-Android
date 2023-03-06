package com.playMatch.ui.matches.adapter.upcomingAdapter

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
import com.playMatch.controller.sharedPrefrence.PrefData
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.RvMatchesListItemBinding
import com.playMatch.ui.home.model.HomeChildModel
import com.playMatch.ui.inbox.activity.chatActivity.ChatActivity
import com.playMatch.ui.matches.activity.matchDetails.MatchDetailsActivity
import com.playMatch.ui.matches.model.upcomingMatches.UpComingMatchList

class UpcomingAdapter(var list: ArrayList<UpComingMatchList>, var activity: Activity) : RecyclerView.Adapter<UpcomingAdapter.ViewHolder>() {


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

            holder.binding.name.text = itemsViewModel.name
            holder.binding.genderTv.text = itemsViewModel.gender
            holder.binding.date.text = itemsViewModel.matchDate
            holder.binding.address.text = itemsViewModel.location
            holder.binding.fitnessLevel.text = itemsViewModel.standard
            holder.binding.sportsName.text = itemsViewModel.sportName
            holder.binding.time.text = itemsViewModel.startTime +" - "+itemsViewModel.finishTime


            Glide.with(activity)
                .load(itemsViewModel.image)
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
                        binding?.progressBar?.visibility = View.GONE
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding?.progressBar?.visibility = View.GONE
                        return false
                    }

                }).into(binding!!.logo)
//            if (position==1){
//                binding.genderTv.text="Female"
//                binding.host.visibility=View.GONE
//                binding.chat.visibility=View.VISIBLE
//            }

            binding.cardView.setOnClickListener {
                val bundle=Bundle()
                bundle.putParcelable(PrefData.MATCH_DETAILS,itemsViewModel)
                selectedPosition=position
                notifyDataSetChanged()
                CommonUtils.performIntentWithBundle(activity,MatchDetailsActivity::class.java,bundle)
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
    fun updateList(Data: List<UpComingMatchList>) {
        if (list.size > 0) {
            list.clear()
            notifyDataSetChanged()
        }
        list.addAll(Data)
        notifyDataSetChanged()
    }
}