package com.playMatch.ui.signUp.signUpAdapters

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.playMatch.controller.`interface`.RecyclerviewListener
import com.playMatch.databinding.RvSelectSportItemBinding
import com.playMatch.ui.signUp.signupModel.SelectChildSPortLightModel
import com.playMatch.ui.signUp.signupModel.SelectChildSPortModel
import com.playMatch.ui.signUp.signupModel.SelectSportModel
import com.playMatch.controller.sharedPrefrence.PrefData
import com.playMatch.ui.signUp.signupModel.SportsList

class SelectSportAdapter(var list: ArrayList<SportsList>, var activity: Activity ) : RecyclerView.Adapter<SelectSportAdapter.ViewHolder>() {

    private var selectedPosition = -1
    private var adapter:SelectChildSportAdapter?=null
    private var lightAdapter:SelectLightBgChildSportAdapter?=null
    private  var mlist = ArrayList<SelectChildSPortModel>()
    private  var mlightlist = ArrayList<SelectChildSPortLightModel>()
    private var type:String?=null
    private var nlist=ArrayList<String>()


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

                override fun onItemClick(position: Int, viewType: String) {
                    nlist.add("${ItemsviewModel.id} : $viewType")

//                    if (nlist.isEmpty()) {
//                        nlist.add("${ItemsviewModel.id} : $viewType")
//                    }else if (nlist.isNotEmpty() && nlist[position] == "${ItemsviewModel.id} : $viewType"){
////                             nlist.remove("${ItemsviewModel.id} : $viewType")
//                    }else if (nlist.isNotEmpty()&& !nlist.equals("${ItemsviewModel.id} : $viewType")){
//                    }
                        type = viewType
                        val param = nlist.joinToString()
                        PrefData.setStringPrefs(activity, PrefData.New_ARRAYLIST, param)
                }
            })

            holder.binding.rvChildSports.adapter = adapter
            mlist.clear()
            mlist.add(
                SelectChildSPortModel(
                    "Beginner"
                )
            )

            mlist.add(
                SelectChildSPortModel(
                    "Novice"
                )
            )

            mlist.add(
                SelectChildSPortModel(
                    "Intermediate"
                )
            )

            mlist.add(
                SelectChildSPortModel(
                    "Experienced"
                )
            )
            mlist.add(
                SelectChildSPortModel(
                    "Superstar"
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