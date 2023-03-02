package com.playMatch.ui.matches.adapter.selectSportAdapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.playMatch.controller.`interface`.BottomSheetListner
import com.playMatch.databinding.RvSelectSportListItemBinding
import com.playMatch.ui.home.model.HomeChildModel
import com.playMatch.controller.sharedPrefrence.PrefData
import com.playMatch.ui.signUp.signupModel.SportsList

class SelectMatchSportAdapter(var list: ArrayList<SportsList>, var activity: Activity, private var ViewType: String, val bottomSheetListner: BottomSheetListner) : RecyclerView.Adapter<SelectMatchSportAdapter.ViewHolder>() {


    private val USER = 0
    private val IMAGE = 1

    private  var selectedPosition=-1
    inner class ViewHolder(val binding: RvSelectSportListItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = RvSelectSportListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }


    @SuppressLint("ResourceAsColor", "SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.apply {
            val ItemsviewModel = list[position]
            val id= PrefData.getStringPrefs(activity, PrefData.CHECK_BOX,"")

            binding.sportName.text=ItemsviewModel.sportName

            binding.cardView.setOnClickListener {
                selectedPosition=position
                notifyDataSetChanged()
                bottomSheetListner.bottomSheetListner("sports",ItemsviewModel.id.toString(),ItemsviewModel.sportName)
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
    fun updateList(Data: List<SportsList>) {
        if (list.size > 0) {
            list.clear()
            notifyDataSetChanged()
        }
        list.addAll(Data)
        notifyDataSetChanged()

//        mRecyclerview?.postDelayed({
//            mRecyclerview.scrollToPosition(itemCount - 1)
//
//        }, 100)
    }



}