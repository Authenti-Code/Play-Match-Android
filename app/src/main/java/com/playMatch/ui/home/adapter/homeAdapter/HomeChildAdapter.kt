package com.playMatch.ui.home.adapter.homeAdapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.playMatch.controller.`interface`.ButtonClickListener
import com.playMatch.databinding.RvChildHomeListItemBinding
import com.playMatch.ui.home.dialogs.invitesDialog.InvitesDialog
import com.playMatch.ui.home.model.HomeChildModel
import com.playMatch.controller.sharedPrefrence.PrefData

class HomeChildAdapter(var list: ArrayList<HomeChildModel>, var activity: Activity) : RecyclerView.Adapter<HomeChildAdapter.ViewHolder>() {


    private val USER = 0
    private val IMAGE = 1

    private  var selectedPosition=-1
    inner class ViewHolder(val binding: RvChildHomeListItemBinding) : RecyclerView.ViewHolder(binding.root)




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = RvChildHomeListItemBinding
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
            holder.binding.name.text = ItemsviewModel.name
            binding.logo.setImageResource(ItemsviewModel.logo)




            binding.cardView.setOnClickListener {
                selectedPosition=position
                notifyDataSetChanged()

                InvitesDialog (activity).invitesDialog(object :
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




    @SuppressLint("NotifyDataSetChanged")
    fun updateList(Data: List<HomeChildModel>) {
        if (list.size > 0) {
            list.clear()
            notifyDataSetChanged()
        }
        list.addAll(Data)
        notifyDataSetChanged()

    }
}