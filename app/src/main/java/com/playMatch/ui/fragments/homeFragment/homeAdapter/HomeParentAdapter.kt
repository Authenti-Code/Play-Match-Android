package com.playMatch.ui.fragments.homeFragment.homeAdapter

import android.annotation.SuppressLint
import android.app.Activity
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
            holder.binding.heading.text = ItemsviewModel.name


            adapter = HomeChildAdapter(mlist, activity)
            holder.binding.rvChildHome.adapter = adapter
            mlist.clear()
            for (i in 1..5) {
                mlist.add(
                    HomeChildModel(
                        R.drawable.ic_league_match,"League Match"
                    )
                )

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

//    private fun getSampleArrayList(): ArrayList<Any> {
//        val items: ArrayList<Any> = ArrayList()
//        items.add(User("Dany Targaryen", "Valyria"))
//        items.add(User("Rob Stark", "Winterfell"))
//        items.add("image")
//        items.add(User("Jon Snow", "Castle Black"))
//        items.add("image")
//        items.add(User("Tyrion Lanister", "King's Landing"))
//        return items
//    }

}