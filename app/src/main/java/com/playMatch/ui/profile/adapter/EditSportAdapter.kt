package com.playMatch.ui.profile.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.playMatch.controller.*
import com.playMatch.controller.`interface`.RecyclerviewListener
import com.playMatch.controller.`interface`.SelectSportsListener
import com.playMatch.controller.sharedPrefrence.PrefData
import com.playMatch.databinding.RvSelectSportItemBinding
import com.playMatch.ui.profile.model.editProfile.EditSportList
import com.playMatch.ui.profile.model.profile.SportLevel
import com.playMatch.ui.signUp.signUpAdapters.SelectChildSportAdapter
import com.playMatch.ui.signUp.signUpAdapters.SelectLightBgChildSportAdapter
import com.playMatch.ui.signUp.signupModel.*


class EditSportAdapter(var list: ArrayList<EditSportList>, var activity: Activity, private var selecSportListener: SelectSportsListener ) : RecyclerView.Adapter<EditSportAdapter.ViewHolder>() {

    private var selectedPosition = -1
    private var adapter: SelectChildSportAdapter?=null
    private var lightAdapter: SelectLightBgChildSportAdapter?=null
    private  var mlist = ArrayList<SelectChildSPortModel>()
    private  var mlightlist = ArrayList<SelectChildSPortLightModel>()
    private var type:String?=null
    private var param:String?=null
    private var nlist=ArrayList<selectedSportModel>()

    inner class ViewHolder(val binding:RvSelectSportItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditSportAdapter.ViewHolder {
        val binding = RvSelectSportItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: EditSportAdapter.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.apply {
            val ItemsviewModel = list[position]
            holder.binding.sportName.text = ItemsviewModel.sportName

            if (ItemsviewModel.isSelected == 1 ) {
                binding.checkbox.isChecked=true
                binding.rvChildSports.visibility=View.VISIBLE
                binding.rvLightChildSports.visibility=View.GONE
                selectedPosition=position
//                PrefData.setStringPrefs(activity,PrefData.SELECTED_LEVEL,ItemsviewModel.sportLevel)
                PrefData.setStringPrefs(activity, PrefData.CHECK_BOX,"1")
                adapter?.updateLevel(ItemsviewModel.sportLevel)
            }


            adapter = SelectChildSportAdapter(mlist, activity,object : RecyclerviewListener {

                override fun onItemClick(position: Int, viewType: String,status:Boolean) {

                    if (nlist.isEmpty()) {
                        nlist.add(selectedSportModel(ItemsviewModel.id,position.toString()))
                    } else {
                        for (i in 0 until nlist.size!!) {
                            val modelNew = nlist[i]
                            if (modelNew.sportId ==ItemsviewModel.id ) {
                                    nlist[i]=selectedSportModel(ItemsviewModel.id,viewType)
                                }
                            else{
                                nlist.add(selectedSportModel(ItemsviewModel.id,viewType))
                            }
                        }
                    }
                    if (nlist!=null) {
                        selecSportListener.onItemClick(position, nlist)
                    }
                }
            })

            holder.binding.rvChildSports.adapter = adapter
            mlist.clear()
            mlist.add(
                SelectChildSPortModel(
                    "Beginner",false
                )
            )

            mlist.add(
                SelectChildSPortModel(
                    "Novice",false
                )
            )

            mlist.add(
                SelectChildSPortModel(
                    "Intermediate",false
                )
            )

            mlist.add(
                SelectChildSPortModel(
                    "Experienced",false
                )
            )
            mlist.add(
                SelectChildSPortModel(
                    "Superstar",false
                )
            )

            lightAdapter = SelectLightBgChildSportAdapter(mlightlist, activity)
            binding.rvLightChildSports.adapter = lightAdapter
            mlightlist.clear()

                mlightlist.add(
                    SelectChildSPortLightModel(
                        "Beginner"
                    )
                )
            mlightlist.add(
                    SelectChildSPortLightModel(
                        "Novice"
                    )
                )
            mlightlist.add(
                    SelectChildSPortLightModel(
                        "Intermediate"
                    )
                )
            mlightlist.add(
                    SelectChildSPortLightModel(
                        "Experienced"
                    )
                )
            mlightlist.add(
                    SelectChildSPortLightModel(
                        "Superstar"
                    )
                )

            binding.checkbox.setOnClickListener {
       if (binding.checkbox.isChecked){
    PrefData.setStringPrefs(activity, PrefData.CHECK_BOX,"1")
    binding.rvChildSports.visibility=View.VISIBLE
    binding.rvLightChildSports.visibility=View.GONE
           selectedPosition=position
       }
       else{
    PrefData.setStringPrefs(activity, PrefData.CHECK_BOX,"0")
    binding.rvChildSports.visibility=View.GONE
    binding.rvLightChildSports.visibility=View.VISIBLE

           for (i in 0 until nlist.size!!) {
               val modelNew = nlist[i]
               when (modelNew.sportId) {
                   ItemsviewModel.id -> {
                       nlist.removeAt(i)
                       return@setOnClickListener
                   }
               }
               }
             }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(data: List<EditSportList>) {
        if (list.size > 0) {
            list.clear()
        }
        list.addAll(data)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSportList(data: List<SportLevel>) {
        val ItemsviewModel = list[selectedPosition]
        for (i in 0 until list.size!!) {
            val modelNew = data[i]
            if (modelNew.sportId ==ItemsviewModel.id) {
            }
            else{
//                nlist.add(selectedSportModel(ItemsviewModel.id,viewType))
            }
        }
        notifyDataSetChanged()
    }
}