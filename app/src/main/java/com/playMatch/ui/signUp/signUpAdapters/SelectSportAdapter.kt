package com.playMatch.ui.signUp.signUpAdapters

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
import com.playMatch.ui.signUp.signupModel.*


class SelectSportAdapter(var list: ArrayList<SportsList>, var activity: Activity,private var selecSportListener: SelectSportsListener ) : RecyclerView.Adapter<SelectSportAdapter.ViewHolder>() {

    private var selectedPosition = -1
    private var adapter:SelectChildSportAdapter?=null
    private var lightAdapter:SelectLightBgChildSportAdapter?=null
    private  var mlist = ArrayList<SelectChildSPortModel>()
    private  var mlightlist = ArrayList<SelectChildSPortLightModel>()
    private var type:String?=null
    private var param:String?=null
    private var nlist=ArrayList<selectedSportModel>()


    inner class ViewHolder(val binding:RvSelectSportItemBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectSportAdapter.ViewHolder {
        val binding = RvSelectSportItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: SelectSportAdapter.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.apply {
            val ItemsviewModel = list[position]
            holder.binding.sportName.text = ItemsviewModel.sportName

            adapter = SelectChildSportAdapter(mlist, activity,object : RecyclerviewListener {

                override fun onItemClick(position: Int, viewType: String,status:Boolean) {
//                    nlist.add("${ItemsviewModel.id} : $viewType")

                    if (nlist.isEmpty()) {
                        nlist.add(selectedSportModel(ItemsviewModel.id,viewType))
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
    fun updateList(data: List<SportsList>) {
        if (list.size > 0) {
            list.clear()
        }
        list.addAll(data)
        notifyDataSetChanged()
    }
}