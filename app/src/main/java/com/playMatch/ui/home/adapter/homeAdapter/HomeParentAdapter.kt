package com.playMatch.ui.home.adapter.homeAdapter

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.playMatch.R
import com.playMatch.controller.`interface`.ButtonClickListener
import com.playMatch.controller.constant.IntentConstant
import com.playMatch.controller.utils.CommonUtils
import com.playMatch.databinding.RvHomeListItemBinding
import com.playMatch.ui.home.activity.HomeActivity
import com.playMatch.ui.home.dialogs.invitesDialog.InvitesDialog
import com.playMatch.ui.home.model.*
import com.playMatch.ui.home.model.homeResponse.HomeList
import com.playMatch.ui.home.model.homeResponse.TeamList
import com.playMatch.ui.home.model.homeResponse.UpcomingMatchList
import com.playMatch.ui.signUp.signUpAdapters.SelectLightBgChildSportAdapter

class HomeParentAdapter(var list: ArrayList<HomeList>, var activity: Activity) : RecyclerView.Adapter<HomeParentAdapter.ViewHolder>() {

    private var selectedPosition = -1
    private var adapter: HomeChildAdapter?=null
    private var secondAdapter: HomeChildSecondPositionAdapterclass?=null
    private var thirdAdapter: HomeChildThirdPositionAdapter?=null
    private var fourthAdapter: HomeChildFourthAdapter?=null
    private var lightAdapter: SelectLightBgChildSportAdapter?=null
    private  var mlist = ArrayList<HomeChildModel>()
    private  var msecondlist = ArrayList<HomeChilseconddModel>()
    private  var mthirdlist = ArrayList<UpcomingMatchList>()
    private  var mfourthlist = ArrayList<TeamList>()

    inner class ViewHolder(val binding: RvHomeListItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvHomeListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.apply {
            val ItemsviewModel = list[position]

            if (position==0) {
                adapter = HomeChildAdapter(mlist, activity)
                holder.binding.rvChildHome.adapter = adapter
                holder.binding.heading.text = "New Invites"

                if (ItemsviewModel.invites.isEmpty()){
                     list.count()
                    list.drop(0)
                    binding.layout.visibility=View.GONE

                }else{
                    mlist.clear()
                    for (i in 1..5) {
                        mlist.add(
                            HomeChildModel(
                                R.drawable.match, "League Match"
                            )
                        )
                    }
                    binding.layout.visibility=View.VISIBLE
                }
            }
            else if (position==1){
                secondAdapter = HomeChildSecondPositionAdapterclass(msecondlist, activity)
                holder.binding.rvChildHome.adapter = secondAdapter
                holder.binding.heading.text = "New Interests"

                if (ItemsviewModel.invites.isEmpty()){
                    list.count()
                    list.drop(1)
                    binding.layout.visibility=View.GONE

                }else{
                    msecondlist.clear()
                    for (i in 1..5) {
                        msecondlist.add(
                            HomeChilseconddModel(
                                R.drawable.profile_pic, "Rossy Alan"
                            )
                        )
                    }
                    binding.layout.visibility=View.VISIBLE
                }

            } else if (position==2){
                thirdAdapter = HomeChildThirdPositionAdapter(mthirdlist, activity)
                holder.binding.rvChildHome.adapter = thirdAdapter
                holder.binding.heading.text = "Upcoming Matches"
                mthirdlist.clear()
                    thirdAdapter?.updateList(ItemsviewModel.upcomingMatch)



                binding.viewAll.setOnClickListener() {
                    val bundle = Bundle()
                    bundle.putString(IntentConstant.TYPE, IntentConstant.FRAGMENT_Matches)
                    CommonUtils.performIntentWithBundleFinish(
                        activity,
                        HomeActivity::class.java,
                        bundle
                    )
                }
            } else if (position==3){
                fourthAdapter = HomeChildFourthAdapter(mfourthlist, activity)
                holder.binding.rvChildHome.adapter = fourthAdapter
                holder.binding.heading.text = "Teams"
                mfourthlist.clear()

                    fourthAdapter?.updateList(ItemsviewModel.teams)


               binding.viewAll.setOnClickListener() {
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
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(Data: List<HomeList>) {
        if (list.size > 0) {
            list.clear()
            notifyDataSetChanged()
        }
        for (i in 0..3) {
            list.addAll(Data)
        }
        list.size
        notifyDataSetChanged()
    }
}