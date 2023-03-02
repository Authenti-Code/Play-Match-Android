package com.playMatch.ui.matches.adapter.selectTeamAdapter

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.drawable.Drawable
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
import com.playMatch.controller.`interface`.BottomSheetListner
import com.playMatch.databinding.RvListItemSelectTeamBinding
import com.playMatch.ui.home.model.HomeChildModel
import com.playMatch.controller.sharedPrefrence.PrefData
import com.playMatch.ui.teams.model.teamList.TeamList

class SelectTeamAdapter(var list: ArrayList<TeamList>, var activity: Activity,private var ViewType: String, val bottomSheetListner: BottomSheetListner) : RecyclerView.Adapter<SelectTeamAdapter.ViewHolder>() {


    private val USER = 0
    private val IMAGE = 1

    private  var selectedPosition=-1
    inner class ViewHolder(val binding: RvListItemSelectTeamBinding) : RecyclerView.ViewHolder(binding.root)




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = RvListItemSelectTeamBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }


    @SuppressLint("ResourceAsColor", "SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.apply {
            val ItemsviewModel = list[position]
            val id= PrefData.getStringPrefs(activity, PrefData.CHECK_BOX,"")

            holder.binding.name.text = ItemsviewModel.name
            holder.binding.sportLevel.text = ItemsviewModel.teamStandard
            holder.binding.sportName.text = ItemsviewModel.sportName


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

            binding.cardView.setOnClickListener {
                selectedPosition=position
                notifyDataSetChanged()
                bottomSheetListner.bottomSheetListner("team",ItemsviewModel.teamId.toString(),ItemsviewModel.name)
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