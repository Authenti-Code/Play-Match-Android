package com.playMatch.ui.home.adapter.homeAdapter

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
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
import com.playMatch.ui.signUp.signUpAdapters.SelectLightBgChildSportAdapter

class HomeParentAdapter(var list: ArrayList<HomeParentModel>, var activity: Activity) : RecyclerView.Adapter<HomeParentAdapter.ViewHolder>() {

    private var selectedPosition = -1
    private var adapter: HomeChildAdapter?=null
    private var secondAdapter: HomeChildSecondPositionAdapterclass?=null
    private var thirdAdapter: HomeChildThirdPositionAdapter?=null
    private var fourthAdapter: HomeChildFourthAdapter?=null
    private var lightAdapter: SelectLightBgChildSportAdapter?=null
    private  var mlist = ArrayList<HomeChildModel>()
    private  var msecondlist = ArrayList<HomeChilseconddModel>()
    private  var mthirdlist = ArrayList<HomeChildthirdModel>()
    private  var mfourthlist = ArrayList<HomeChildfourthModel>()

    inner class ViewHolder(val binding: RvHomeListItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvHomeListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.apply {
            val ItemsviewModel = list[position]

            if (position==0) {
                adapter = HomeChildAdapter(mlist, activity)
                holder.binding.rvChildHome.adapter = adapter
                holder.binding.heading.text = "New Invites"
                mlist.clear()
                for (i in 1..5) {
                    mlist.add(
                        HomeChildModel(
                            R.drawable.match,"League Match"
                        )
                    )

                }
            }
            else if (position==1){
                secondAdapter = HomeChildSecondPositionAdapterclass(msecondlist, activity)
                holder.binding.rvChildHome.adapter = secondAdapter
                holder.binding.heading.text = "New Interests"
                msecondlist.clear()
                for (i in 1..5) {
                    msecondlist.add(
                        HomeChilseconddModel(
                            R.drawable.profile_pic,"Rossy Alan"
                        )
                    )

                }
            } else if (position==2){
                thirdAdapter = HomeChildThirdPositionAdapter(mthirdlist, activity)
                holder.binding.rvChildHome.adapter = thirdAdapter
                holder.binding.heading.text = "Upcoming Matches"
                mthirdlist.clear()
                for (i in 1..5) {
                    mthirdlist.add(
                        HomeChildthirdModel(
                            R.drawable.your_team,"T20 League"
                        )
                    )

                }

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
                for (i in 1..5) {
                    mfourthlist.add(
                        HomeChildfourthModel(
                            R.drawable.juventus,"League Match"
                        )
                    )

                }
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
    fun updateCommentList(Data: List<HomeParentModel>, mRecyclerview: RecyclerView?) {
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