package com.playMatch.ui.home.adapter.nearbyMatchesAdapter

import android.annotation.SuppressLint
import android.app.Activity
import android.telecom.Call
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.card.MaterialCardView
import com.playMatch.R
import com.playMatch.databinding.RvChildHomeListItemBinding
import com.playMatch.databinding.RvNearByMatchListBinding
import com.playMatch.databinding.RvNotificationListItemBinding
import com.playMatch.ui.home.model.HomeChildModel
import com.saetae.controller.sharedPrefrence.PrefData

class NearByMatchesAdapter(var list: ArrayList<HomeChildModel>, var activity: Activity) : RecyclerView.Adapter<NearByMatchesAdapter.ViewHolder>() {


    private val USER = 0
    private val IMAGE = 1

    private  var selectedPosition=-1
    inner class ViewHolder(val binding: RvNearByMatchListBinding) : RecyclerView.ViewHolder(binding.root)




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = RvNearByMatchListBinding
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


            binding.cardView.setOnClickListener {
                selectedPosition=position
                showBottomSheetShowInterest()
                notifyDataSetChanged()
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
    fun updateCommentList(Data: List<HomeChildModel>, mRecyclerview: RecyclerView?) {
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

    private fun showBottomSheetShowInterest() {
        val view: View = activity.layoutInflater.inflate(R.layout.bottom_sheet_show_interest, null)
        val dialog = BottomSheetDialog(activity, R.style.AppBottomSheetDialogTheme)
        val showInterest = view.findViewById<CardView>(R.id.showInterest)

        showInterest.setOnClickListener {
            dialog.dismiss()
        }
        val cancelBtn = view.findViewById<ImageView>(R.id.cancel)
        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(view)
        dialog.show()
    }

}