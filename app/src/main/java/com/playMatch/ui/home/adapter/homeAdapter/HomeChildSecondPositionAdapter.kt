package com.playMatch.ui.home.adapter.homeAdapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.playMatch.controller.`interface`.ButtonClickListener
import com.playMatch.databinding.RvChildSecondPositionListItemBinding
import com.playMatch.ui.home.dialogs.interestDialog.InterestsDialog
import com.playMatch.ui.home.dialogs.invitesDialog.InvitesDialog
import com.playMatch.ui.home.model.HomeChildModel
import com.playMatch.ui.home.model.HomeChilseconddModel
import com.saetae.controller.sharedPrefrence.PrefData

class HomeChildSecondPositionAdapterclass(var list: ArrayList<HomeChilseconddModel>, var activity: Activity) : RecyclerView.Adapter<HomeChildSecondPositionAdapterclass.ViewHolder>() {


    private val USER = 0
    private val IMAGE = 1

    private  var selectedPosition=-1
    inner class ViewHolder(val binding: RvChildSecondPositionListItemBinding) : RecyclerView.ViewHolder(binding.root)




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = RvChildSecondPositionListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }


    @SuppressLint("ResourceAsColor", "SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.apply {
            val ItemsviewModel = list[position]
            val id= PrefData.getStringPrefs(activity, PrefData.CHECK_BOX,"")

            holder.binding.name.text = ItemsviewModel.name
            binding.profileImage.setImageResource(ItemsviewModel.logo)



            binding.cardView.setOnClickListener {
                selectedPosition=position
                notifyDataSetChanged()
                InterestsDialog (activity).interestDialog(object :
                    ButtonClickListener {
                    override fun Onclick() {}
                })
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    override fun getItemCount(): Int {
        return list.size
    }

}