package com.playMatch.ui.fragments.homeFragment.homeAdapter

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build.VERSION_CODES.P
import android.service.autofill.FieldClassification
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.playMatch.R
import com.playMatch.databinding.RvHomeListItemBinding
import com.playMatch.ui.activity.commonActivity.signUpActivity.signUpAdapters.SelectChildSportAdapter
import com.playMatch.ui.activity.commonActivity.signUpActivity.signUpAdapters.SelectLightBgChildSportAdapter
import com.playMatch.ui.activity.commonActivity.signUpActivity.signupModel.SelectChildSPortModel
import com.playMatch.ui.fragments.homeFragment.homeModel.HomeChildModel
import com.playMatch.ui.fragments.homeFragment.homeModel.HomeParentModel

class HomeParentAdapter(var list: ArrayList<HomeParentModel>, var activity: Activity) : RecyclerView.Adapter<HomeParentAdapter.ViewHolder>() {

    private var selectedPosition = -1
    private var adapter: HomeChildAdapter?=null
    private var secondAdapter: HomeChildSecondPositionAdapterclass?=null
    private var thirdAdapter: HomeChildThirdPositionAdapter?=null
    private var fourthAdapter: HomeChildFourthAdapter?=null
    private var lightAdapter: SelectLightBgChildSportAdapter?=null
    private  var mlist = ArrayList<HomeChildModel>()

    inner class ViewHolder(val binding: RvHomeListItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeParentAdapter.ViewHolder {
        val binding = RvHomeListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeParentAdapter.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
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
                            R.drawable.ic_league_match,"League Match"
                        )
                    )

                }
            }
            else if (position==1){
                secondAdapter = HomeChildSecondPositionAdapterclass(mlist, activity)
                holder.binding.rvChildHome.adapter = secondAdapter
                holder.binding.heading.text = "New Interests"
                mlist.clear()
                for (i in 1..5) {
                    mlist.add(
                        HomeChildModel(
                            R.drawable.new_dummy_profile,"Rossy Alan"
                        )
                    )

                }
            } else if (position==2){
                thirdAdapter = HomeChildThirdPositionAdapter(mlist, activity)
                holder.binding.rvChildHome.adapter = thirdAdapter
                holder.binding.heading.text = "Upcoming Matches"
                mlist.clear()
                for (i in 1..5) {
                    mlist.add(
                        HomeChildModel(
                            R.drawable.new_dummy_profile,"T20 League"
                        )
                    )

                }
            } else if (position==3){
                fourthAdapter = HomeChildFourthAdapter(mlist, activity)
                holder.binding.rvChildHome.adapter = fourthAdapter
                holder.binding.heading.text = "Teams"
                mlist.clear()
                for (i in 1..5) {
                    mlist.add(
                        HomeChildModel(
                            R.drawable.juventus,"T20 League"
                        )
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