package com.playMatch.ui.profile.adapter
import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.playMatch.controller.*
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
    private var level:String?=null
    private var levelName:String?=null
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
                binding.beginner.strokeColor=Color.parseColor("#F95047")
                binding.beginnerTv.setTextColor(Color.parseColor("#F95047"))
                binding.novice.strokeColor=Color.parseColor("#F95047")
                binding.noviceTv.setTextColor(Color.parseColor("#F95047"))
                binding.intermediate.strokeColor=Color.parseColor("#F95047")
                binding.intermediateTv.setTextColor(Color.parseColor("#F95047"))
                binding.experienced.strokeColor=Color.parseColor("#F95047")
                binding.experiencedTv.setTextColor(Color.parseColor("#F95047"))
                binding.superstar.strokeColor=Color.parseColor("#F95047")
                binding.superstarTv.setTextColor(Color.parseColor("#F95047"))
                nlist.clear()
                nlist.add(selectedSportModel(ItemsviewModel.id,ItemsviewModel.sportLevel,ItemsviewModel.sportName))

                if (ItemsviewModel.sportLevel == "1") {
                        binding.beginner.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding.beginnerTv.setTextColor(Color.WHITE)

                        binding.novice.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.noviceTv.setTextColor(Color.parseColor("#F95047"))

                        binding.intermediate.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.intermediateTv.setTextColor(Color.parseColor("#F95047"))

                        binding.experienced.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.experiencedTv.setTextColor(Color.parseColor("#F95047"))

                        binding.superstar.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.superstarTv.setTextColor(Color.parseColor("#F95047"))
                    }
                if (ItemsviewModel.sportLevel=="2") {
                    binding.novice.setCardBackgroundColor(Color.parseColor("#F95047"))
                    binding.noviceTv.setTextColor(Color.WHITE)

                    binding.beginner.setCardBackgroundColor(Color.parseColor("#ffffff"))
                    binding.beginnerTv.setTextColor(Color.parseColor("#F95047"))

                    binding.intermediate.setCardBackgroundColor(Color.parseColor("#ffffff"))
                    binding.intermediateTv.setTextColor(Color.parseColor("#F95047"))

                    binding.experienced.setCardBackgroundColor(Color.parseColor("#ffffff"))
                    binding.experiencedTv.setTextColor(Color.parseColor("#F95047"))

                    binding.superstar.setCardBackgroundColor(Color.parseColor("#ffffff"))
                    binding.superstarTv.setTextColor(Color.parseColor("#F95047"))
                }
                if (ItemsviewModel.sportLevel=="3") {
                    binding.intermediate.setCardBackgroundColor(Color.parseColor("#F95047"))
                    binding.intermediateTv.setTextColor(Color.WHITE)

                    binding.beginner.setCardBackgroundColor(Color.parseColor("#ffffff"))
                    binding.beginnerTv.setTextColor(Color.parseColor("#F95047"))

                    binding.novice.setCardBackgroundColor(Color.parseColor("#ffffff"))
                    binding.noviceTv.setTextColor(Color.parseColor("#F95047"))

                    binding.experienced.setCardBackgroundColor(Color.parseColor("#ffffff"))
                    binding.experiencedTv.setTextColor(Color.parseColor("#F95047"))

                    binding.superstar.setCardBackgroundColor(Color.parseColor("#ffffff"))
                    binding.superstarTv.setTextColor(Color.parseColor("#F95047"))
                }
                if (ItemsviewModel.sportLevel=="4") {
                    binding.experienced.setCardBackgroundColor(Color.parseColor("#F95047"))
                    binding.experiencedTv.setTextColor(Color.WHITE)

                    binding.beginner.setCardBackgroundColor(Color.parseColor("#ffffff"))
                    binding.beginnerTv.setTextColor(Color.parseColor("#F95047"))

                    binding.novice.setCardBackgroundColor(Color.parseColor("#ffffff"))
                    binding.noviceTv.setTextColor(Color.parseColor("#F95047"))

                    binding.intermediate.setCardBackgroundColor(Color.parseColor("#ffffff"))
                    binding.intermediateTv.setTextColor(Color.parseColor("#F95047"))

                    binding.superstar.setCardBackgroundColor(Color.parseColor("#ffffff"))
                    binding.superstarTv.setTextColor(Color.parseColor("#F95047"))
                }
                if (ItemsviewModel.sportLevel=="5") {
                    binding.superstar.setCardBackgroundColor(Color.parseColor("#F95047"))
                    binding.superstarTv.setTextColor(Color.WHITE)

                    binding.beginner.setCardBackgroundColor(Color.parseColor("#ffffff"))
                    binding.beginnerTv.setTextColor(Color.parseColor("#F95047"))

                    binding.novice.setCardBackgroundColor(Color.parseColor("#ffffff"))
                    binding.noviceTv.setTextColor(Color.parseColor("#F95047"))

                    binding.intermediate.setCardBackgroundColor(Color.parseColor("#ffffff"))
                    binding.intermediateTv.setTextColor(Color.parseColor("#F95047"))

                    binding.experienced.setCardBackgroundColor(Color.parseColor("#ffffff"))
                    binding.experiencedTv.setTextColor(Color.parseColor("#F95047"))
                }
                if (binding.checkbox.isChecked){
                    binding.beginner.setOnClickListener {
                        binding.beginner.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding.beginnerTv.setTextColor(Color.WHITE)

                        binding.novice.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.noviceTv.setTextColor(Color.parseColor("#F95047"))

                        binding.intermediate.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.intermediateTv.setTextColor(Color.parseColor("#F95047"))

                        binding.experienced.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.experiencedTv.setTextColor(Color.parseColor("#F95047"))

                        binding.superstar.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.superstarTv.setTextColor(Color.parseColor("#F95047"))
                    }

                    binding.novice.setOnClickListener {
                        binding.novice.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding.noviceTv.setTextColor(Color.WHITE)

                        binding.beginner.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.beginnerTv.setTextColor(Color.parseColor("#F95047"))

                        binding.intermediate.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.intermediateTv.setTextColor(Color.parseColor("#F95047"))

                        binding.experienced.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.experiencedTv.setTextColor(Color.parseColor("#F95047"))

                        binding.superstar.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.superstarTv.setTextColor(Color.parseColor("#F95047"))
                    }

                    binding.intermediate.setOnClickListener {
                        binding.intermediate.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding.intermediateTv.setTextColor(Color.WHITE)

                        binding.beginner.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.beginnerTv.setTextColor(Color.parseColor("#F95047"))

                        binding.novice.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.noviceTv.setTextColor(Color.parseColor("#F95047"))

                        binding.experienced.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.experiencedTv.setTextColor(Color.parseColor("#F95047"))

                        binding.superstar.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.superstarTv.setTextColor(Color.parseColor("#F95047"))
                    }

                    binding.experienced.setOnClickListener {
                        binding.experienced.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding.experiencedTv.setTextColor(Color.WHITE)

                        binding.beginner.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.beginnerTv.setTextColor(Color.parseColor("#F95047"))

                        binding.novice.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.noviceTv.setTextColor(Color.parseColor("#F95047"))

                        binding.intermediate.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.intermediateTv.setTextColor(Color.parseColor("#F95047"))

                        binding.superstar.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.superstarTv.setTextColor(Color.parseColor("#F95047"))
                    }

                    binding.superstar.setOnClickListener {
                        binding.superstar.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding.superstarTv.setTextColor(Color.WHITE)

                        binding.beginner.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.beginnerTv.setTextColor(Color.parseColor("#F95047"))

                        binding.novice.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.noviceTv.setTextColor(Color.parseColor("#F95047"))

                        binding.intermediate.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.intermediateTv.setTextColor(Color.parseColor("#F95047"))

                        binding.experienced.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.experiencedTv.setTextColor(Color.parseColor("#F95047"))
                    }
                }

            }else {
                binding.checkbox.isChecked= false
            }
                    if (nlist.isEmpty() && selectedPosition==position) {
                        nlist.add(selectedSportModel(ItemsviewModel.id,level.toString(),levelName.toString()))
                    } else {
                        for (i in 0 until nlist.size!!) {
                            val modelNew = nlist[i]
                            if (modelNew.sportId ==ItemsviewModel.id ) {
                                    nlist[i]=selectedSportModel(ItemsviewModel.id,level.toString(),levelName.toString())
                                }
                            else{
                                nlist.add(selectedSportModel(ItemsviewModel.id,level.toString(),levelName.toString()))
                            }
                        }
                    }
                    if (nlist!=null) {
                        selecSportListener.onItemClick(position, nlist)
                    }


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
            mlightlist.add(SelectChildSPortLightModel("Superstar"))

            binding.checkbox.setOnClickListener {

       if (binding.checkbox.isChecked){
           PrefData.setStringPrefs(activity, PrefData.CHECK_BOX,"1")
           binding.beginner.strokeColor=Color.parseColor("#F95047")
           binding.beginnerTv.setTextColor(Color.parseColor("#F95047"))
           binding.novice.strokeColor=Color.parseColor("#F95047")
           binding.noviceTv.setTextColor(Color.parseColor("#F95047"))
           binding.intermediate.strokeColor=Color.parseColor("#F95047")
           binding.intermediateTv.setTextColor(Color.parseColor("#F95047"))
           binding.experienced.strokeColor=Color.parseColor("#F95047")
           binding.experiencedTv.setTextColor(Color.parseColor("#F95047"))
           binding.superstar.strokeColor=Color.parseColor("#F95047")
           binding.superstarTv.setTextColor(Color.parseColor("#F95047"))
           selectedPosition=position

           binding.beginner.setOnClickListener {
               binding.beginner.setCardBackgroundColor(Color.parseColor("#F95047"))
               binding.beginnerTv.setTextColor(Color.WHITE)

               level="1"
               levelName=binding.beginnerTv.text.toString().trim()

               binding.novice.setCardBackgroundColor(Color.parseColor("#ffffff"))
               binding.noviceTv.setTextColor(Color.parseColor("#F95047"))

               binding.intermediate.setCardBackgroundColor(Color.parseColor("#ffffff"))
               binding.intermediateTv.setTextColor(Color.parseColor("#F95047"))

               binding.experienced.setCardBackgroundColor(Color.parseColor("#ffffff"))
               binding.experiencedTv.setTextColor(Color.parseColor("#F95047"))

               binding.superstar.setCardBackgroundColor(Color.parseColor("#ffffff"))
               binding.superstarTv.setTextColor(Color.parseColor("#F95047"))
           }

           binding.novice.setOnClickListener {
               binding.novice.setCardBackgroundColor(Color.parseColor("#F95047"))
               binding.noviceTv.setTextColor(Color.WHITE)

               level="2"
               levelName=binding.noviceTv.text.toString().trim()

               binding.beginner.setCardBackgroundColor(Color.parseColor("#ffffff"))
               binding.beginnerTv.setTextColor(Color.parseColor("#F95047"))

               binding.intermediate.setCardBackgroundColor(Color.parseColor("#ffffff"))
               binding.intermediateTv.setTextColor(Color.parseColor("#F95047"))

               binding.experienced.setCardBackgroundColor(Color.parseColor("#ffffff"))
               binding.experiencedTv.setTextColor(Color.parseColor("#F95047"))

               binding.superstar.setCardBackgroundColor(Color.parseColor("#ffffff"))
               binding.superstarTv.setTextColor(Color.parseColor("#F95047"))
           }

           binding.intermediate.setOnClickListener {
               binding.intermediate.setCardBackgroundColor(Color.parseColor("#F95047"))
               binding.intermediateTv.setTextColor(Color.WHITE)

               level="3"
               levelName=binding.intermediateTv.text.toString().trim()

               binding.beginner.setCardBackgroundColor(Color.parseColor("#ffffff"))
               binding.beginnerTv.setTextColor(Color.parseColor("#F95047"))

               binding.novice.setCardBackgroundColor(Color.parseColor("#ffffff"))
               binding.noviceTv.setTextColor(Color.parseColor("#F95047"))

               binding.experienced.setCardBackgroundColor(Color.parseColor("#ffffff"))
               binding.experiencedTv.setTextColor(Color.parseColor("#F95047"))

               binding.superstar.setCardBackgroundColor(Color.parseColor("#ffffff"))
               binding.superstarTv.setTextColor(Color.parseColor("#F95047"))
           }

           binding.experienced.setOnClickListener {
               binding.experienced.setCardBackgroundColor(Color.parseColor("#F95047"))
               binding.experiencedTv.setTextColor(Color.WHITE)

               level="4"
               levelName=binding.experiencedTv.text.toString().trim()

               binding.beginner.setCardBackgroundColor(Color.parseColor("#ffffff"))
               binding.beginnerTv.setTextColor(Color.parseColor("#F95047"))

               binding.novice.setCardBackgroundColor(Color.parseColor("#ffffff"))
               binding.noviceTv.setTextColor(Color.parseColor("#F95047"))

               binding.intermediate.setCardBackgroundColor(Color.parseColor("#ffffff"))
               binding.intermediateTv.setTextColor(Color.parseColor("#F95047"))

               binding.superstar.setCardBackgroundColor(Color.parseColor("#ffffff"))
               binding.superstarTv.setTextColor(Color.parseColor("#F95047"))
           }

           binding.superstar.setOnClickListener {
               binding.superstar.setCardBackgroundColor(Color.parseColor("#F95047"))
               binding.superstarTv.setTextColor(Color.WHITE)

               level="5"
               levelName=binding.superstarTv.text.toString().trim()

               binding.beginner.setCardBackgroundColor(Color.parseColor("#ffffff"))
               binding.beginnerTv.setTextColor(Color.parseColor("#F95047"))

               binding.novice.setCardBackgroundColor(Color.parseColor("#ffffff"))
               binding.noviceTv.setTextColor(Color.parseColor("#F95047"))

               binding.intermediate.setCardBackgroundColor(Color.parseColor("#ffffff"))
               binding.intermediateTv.setTextColor(Color.parseColor("#F95047"))

               binding.experienced.setCardBackgroundColor(Color.parseColor("#ffffff"))
               binding.experiencedTv.setTextColor(Color.parseColor("#F95047"))
           }
       }
       else{
    PrefData.setStringPrefs(activity, PrefData.CHECK_BOX,"0")

           binding.beginner.strokeColor=Color.parseColor("#80F95047")
           binding.beginnerTv.setTextColor(Color.parseColor("#80F95047"))
           binding.novice.strokeColor=Color.parseColor("#80F95047")
           binding.beginnerTv.setTextColor(Color.parseColor("#80F95047"))
           binding.intermediate.strokeColor=Color.parseColor("#80F95047")
           binding.beginnerTv.setTextColor(Color.parseColor("#80F95047"))
           binding.experienced.strokeColor=Color.parseColor("#80F95047")
           binding.beginnerTv.setTextColor(Color.parseColor("#80F95047"))
           binding.superstar.strokeColor=Color.parseColor("#80F95047")
           binding.beginnerTv.setTextColor(Color.parseColor("#80F95047"))

           adapter!!.unselect(position)

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