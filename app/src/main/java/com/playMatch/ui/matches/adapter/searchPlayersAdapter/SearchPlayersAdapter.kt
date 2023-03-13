package com.playMatch.ui.matches.adapter.searchPlayersAdapter

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.playMatch.R
import com.playMatch.controller.playMatchAPi.ResultResponse
import com.playMatch.controller.playMatchAPi.apiClasses.UserApi
import com.playMatch.controller.playMatchAPi.postPojoModel.user.Match.MatchInvitePost
import com.playMatch.controller.playMatchAPi.postPojoModel.user.players.PlayersPost
import com.playMatch.databinding.RvListItemSearchPlayersBinding
import com.playMatch.ui.home.model.HomeChildModel
import com.playMatch.controller.sharedPrefrence.PrefData
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.ui.baseActivity.BaseActivity
import com.playMatch.ui.matches.model.invitePlayer.InvitePlayerResponse
import com.playMatch.ui.matches.model.players.PlayersListing
import com.playMatch.ui.matches.model.players.PlayersResponse

class SearchPlayersAdapter(var list: ArrayList<PlayersListing>, var activity: Activity) : RecyclerView.Adapter<SearchPlayersAdapter.ViewHolder>() {


    private val USER = 0
    private val IMAGE = 1

    private var  matchId:String?=null
    private var  userId:Int?=null

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
            
            binding.name.text=ItemsviewModel.name
            binding.gender.text=ItemsviewModel.gender
            binding.level.text=ItemsviewModel.levelName
            userId=ItemsviewModel.id

            binding.cardView.setOnClickListener {
                selectedPosition=position
                notifyDataSetChanged()
                if (userId!=null && matchId!=null) {
                    invitePlayerApi()
                    binding.cardViewTv.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_grey_check,
                        0,
                        0,
                        0
                    );
                    binding.cardView.setCardBackgroundColor(Color.parseColor("#66B7B7B7"))
                    binding.cardViewTv.text = "Invited"
                    binding.cardView.strokeColor = (Color.parseColor("#66B7B7B7"))
                }
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
    fun updateList(Data: List<PlayersListing>,id:String) {
        if (list.size > 0) {
            list.clear()
            notifyDataSetChanged()
        }
        list.addAll(Data)
        matchId=id
        notifyDataSetChanged()
    }



    private fun invitePlayerApi(){
        CommonUtils.showProgressDialog(activity)
        if ((activity as BaseActivity).isNetworkAvailable()) {
            (activity as BaseActivity).lifecycleScope.launchWhenStarted {
                val resultResponse = UserApi(activity).invitePlayer(MatchInvitePost(matchId.toString(),userId!!.toInt()))
                apiInvitePlayerResult(resultResponse)
            }
        } else {
            (activity as BaseActivity).showNetworkSpeedError()
        }
    }
    private fun apiInvitePlayerResult(resultResponse: ResultResponse) {
        CommonUtils.hideProgressDialog()
        return when (resultResponse) {
            is ResultResponse.Success<*> -> {
                val response = resultResponse.response as InvitePlayerResponse
                //get data and convert string to json and save data
                if (response.success == "true") {
//                    searchAdapter?.updateList(response.data,matchDetails?.id.toString())
                } else {
                    (activity as BaseActivity).showSnackBar(activity.findViewById(R.id.rootView), response.message)
                }
            }
            else -> {
                (activity as BaseActivity).showError(resultResponse)
            }
        } as Unit
    }

}