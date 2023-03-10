package com.playMatch.ui.signUp.signUpAdapters

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.playMatch.controller.*
import com.playMatch.controller.`interface`.RecyclerviewListener
import com.playMatch.controller.`interface`.SelectSportsListener
import com.playMatch.controller.sharedPrefrence.PrefData
import com.playMatch.databinding.RvSelectSportItemBinding
import com.playMatch.ui.profile.model.profile.SportLevel
import com.playMatch.ui.signUp.signupModel.*


class SelectSportAdapter(var list: ArrayList<SportsList>, var activity: Activity,private var selecSportListener: SelectSportsListener ) : RecyclerView.Adapter<SelectSportAdapter.ViewHolder>() {

    private var selectedPosition = -1
    private var adapter:SelectChildSportAdapter?=null
    private var lightAdapter:SelectLightBgChildSportAdapter?=null
    private  var mlist = ArrayList<SelectChildSPortModel>()
    private  var mlightlist = ArrayList<SelectChildSPortLightModel>()
    private var level:String?=null
    private var sportsLevelName:String?=null
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
            mlightlist.add(
                    SelectChildSPortLightModel(
                        "Superstar"
                    )
                )

            binding.checkbox.setOnClickListener {

                if (binding.checkbox.isChecked) {
                    PrefData.setStringPrefs(activity, PrefData.CHECK_BOX, "1")
                    binding.beginner.strokeColor = Color.parseColor("#F95047")
                    binding.beginnerTv.setTextColor(Color.parseColor("#F95047"))
                    binding.novice.strokeColor = Color.parseColor("#F95047")
                    binding.noviceTv.setTextColor(Color.parseColor("#F95047"))
                    binding.intermediate.strokeColor = Color.parseColor("#F95047")
                    binding.intermediateTv.setTextColor(Color.parseColor("#F95047"))
                    binding.experienced.strokeColor = Color.parseColor("#F95047")
                    binding.experiencedTv.setTextColor(Color.parseColor("#F95047"))
                    binding.superstar.strokeColor = Color.parseColor("#F95047")
                    binding.superstarTv.setTextColor(Color.parseColor("#F95047"))
                    selectedPosition = position

                    binding.beginner.setOnClickListener {
                        binding.beginner.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding.beginnerTv.setTextColor(Color.WHITE)

                        level = "1"
                        sportsLevelName = binding.beginnerTv.text.toString().trim()

                        binding.novice.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.noviceTv.setTextColor(Color.parseColor("#F95047"))

                        binding.intermediate.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.intermediateTv.setTextColor(Color.parseColor("#F95047"))

                        binding.experienced.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.experiencedTv.setTextColor(Color.parseColor("#F95047"))

                        binding.superstar.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.superstarTv.setTextColor(Color.parseColor("#F95047"))

                        if (position in 0 until list.size) {
                            if (binding.checkbox.isChecked == true) {
                                nlist.add(
                                    selectedSportModel(
                                        ItemsviewModel.id,
                                        level.toString(),
                                        sportsLevelName.toString()
                                    )
                                )
                            } else {
                                nlist.remove(
                                    selectedSportModel(
                                        ItemsviewModel.id,
                                        level.toString(),
                                        sportsLevelName.toString()
                                    )
                                )
                            }
                        }




//                                    nlist.remove(
//                                        selectedSportModel(
//                                            ItemsviewModel.id,
//                                            level.toString(),
//                                            sportsLevelName.toString()
//                                        )
//                                    )
//                            }
//                        }
//                                if (nlist.isEmpty() && selectedPosition == position) {
//                                    nlist.add(
//                                        selectedSportModel(
//                                            ItemsviewModel.id,
//                                            level.toString(),
//                                            sportsLevelName.toString()
//                                        )
//                                    )
//                                } else {
//                                    for (i in 0 until nlist.size!!) {
//                                        val modelNew = nlist[i]
//                                        if (modelNew.sportId == ItemsviewModel.id) {
//                                            nlist[i] = selectedSportModel(
//                                                ItemsviewModel.id,
//                                                level.toString(),
//                                            sportsLevelName.toString()
//                                            )
//                                        } else {
//                                            break
//                                        }
//                                    }
//                                }
                    }

                    binding.novice.setOnClickListener {
                        binding.novice.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding.noviceTv.setTextColor(Color.WHITE)

                        level = "2"
                        sportsLevelName = binding.noviceTv.text.toString().trim()

                        binding.beginner.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.beginnerTv.setTextColor(Color.parseColor("#F95047"))

                        binding.intermediate.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.intermediateTv.setTextColor(Color.parseColor("#F95047"))

                        binding.experienced.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.experiencedTv.setTextColor(Color.parseColor("#F95047"))

                        binding.superstar.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.superstarTv.setTextColor(Color.parseColor("#F95047"))

                        if (position in 0 until list.size) {
                            if (binding.checkbox.isChecked == true) {
                                nlist.add(
                                    selectedSportModel(
                                        ItemsviewModel.id,
                                        level.toString(),
                                        sportsLevelName.toString()
                                    )
                                )
                            } else {
                                nlist.remove(
                                    selectedSportModel(
                                        ItemsviewModel.id,
                                        level.toString(),
                                        sportsLevelName.toString()
                                    )
                                )
                            }
                        }

//                            if (position in 0 until list.size) {
//
//                                if (nlist.isEmpty() && selectedPosition == position) {
//                                    nlist.add(
//                                        selectedSportModel(
//                                            ItemsviewModel.id,
//                                            level.toString(),
//                                            sportsLevelName.toString()
//                                        )
//                                    )
//                                } else {
//                                    if (nlist.isNotEmpty() && selectedPosition == position) {
//                                        for (i in 0 until nlist.size!!) {
//                                            val modelNew = nlist[i]
//                                            if (modelNew.sportId == ItemsviewModel.id) {
//                                                nlist[i] = selectedSportModel(
//                                                    ItemsviewModel.id,
//                                                    level.toString(),
//                                                    sportsLevelName.toString()
//                                                )
//                                            }
//                                        }
//                                    } else {
//                                        nlist.add(
//                                            selectedSportModel(
//                                                ItemsviewModel.id,
//                                                level.toString(),
//                                                sportsLevelName.toString()
//                                            )
//                                        )
//                                    }
//                                }
//                            }

//                                if (nlist.isEmpty() && selectedPosition == position) {
//                                    nlist.add(
//                                        selectedSportModel(
//                                            ItemsviewModel.id,
//                                            level.toString(),
//                                            levelName.toString()
//                                        )
//                                    )
//                                } else {
//                                    for (i in 0 until nlist.size!!) {
//                                        val modelNew = nlist[i]
//                                        if (modelNew.sportId == ItemsviewModel.id) {
//                                            nlist[i] = selectedSportModel(
//                                                ItemsviewModel.id,
//                                                level.toString(),
//                                                levelName.toString()
//                                            )
//                                        } else {
//                                            if (nlist.isNotEmpty() && selectedPosition == position) {
//                                                nlist.add(
//                                                    selectedSportModel(
//                                                        ItemsviewModel.id,
//                                                        level.toString(),
//                                                        levelName.toString()
//                                                    )
//                                                )
//                                            }
//                                        }
//                                    }
//                                }
                    }

                    binding.intermediate.setOnClickListener {
                        binding.intermediate.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding.intermediateTv.setTextColor(Color.WHITE)

                        level = "3"
                        sportsLevelName = binding.intermediateTv.text.toString().trim()

                        binding.beginner.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.beginnerTv.setTextColor(Color.parseColor("#F95047"))

                        binding.novice.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.noviceTv.setTextColor(Color.parseColor("#F95047"))

                        binding.experienced.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.experiencedTv.setTextColor(Color.parseColor("#F95047"))

                        binding.superstar.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.superstarTv.setTextColor(Color.parseColor("#F95047"))

                        if (binding.checkbox.isChecked == true) {
                            nlist.add(
                                selectedSportModel(
                                    ItemsviewModel.id,
                                    level.toString(),
                                    sportsLevelName.toString()
                                )
                            )
                        } else {
                            nlist.remove(
                                selectedSportModel(
                                    ItemsviewModel.id,
                                    level.toString(),
                                    sportsLevelName.toString()
                                )
                            )
                        }
//                            if (position in 0 until list.size) {
//
//                                if (nlist.isEmpty() && selectedPosition == position) {
//                                    nlist.add(
//                                        selectedSportModel(
//                                            ItemsviewModel.id,
//                                            level.toString(),
//                                            sportsLevelName.toString()
//                                        )
//                                    )
//                                } else {
//                                    if (nlist.isNotEmpty() && selectedPosition == position) {
//                                        for (i in 0 until nlist.size!!) {
//                                            val modelNew = nlist[i]
//                                            if (modelNew.sportId == ItemsviewModel.id) {
//                                                nlist[i] = selectedSportModel(
//                                                    ItemsviewModel.id,
//                                                    level.toString(),
//                                                    sportsLevelName.toString()
//                                                )
//                                            }
//                                        }
//                                    } else {
//                                        nlist.add(
//                                            selectedSportModel(
//                                                ItemsviewModel.id,
//                                                level.toString(),
//                                                sportsLevelName.toString()
//                                            )
//                                        )
//                                    }
//                                }
//                            }


                    }

                    binding.experienced.setOnClickListener {
                        binding.experienced.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding.experiencedTv.setTextColor(Color.WHITE)

                        level = "4"
                        sportsLevelName = binding.experiencedTv.text.toString().trim()

                        binding.beginner.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.beginnerTv.setTextColor(Color.parseColor("#F95047"))

                        binding.novice.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.noviceTv.setTextColor(Color.parseColor("#F95047"))

                        binding.intermediate.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.intermediateTv.setTextColor(Color.parseColor("#F95047"))

                        binding.superstar.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.superstarTv.setTextColor(Color.parseColor("#F95047"))

                        if (position in 0 until list.size) {
                            if (binding.checkbox.isChecked == true) {
                                nlist.add(
                                    selectedSportModel(
                                        ItemsviewModel.id,
                                        level.toString(),
                                        sportsLevelName.toString()
                                    )
                                )
                            } else {
                                nlist.remove(
                                    selectedSportModel(
                                        ItemsviewModel.id,
                                        level.toString(),
                                        sportsLevelName.toString()
                                    )
                                )
                            }
                        }
//                            if (position in 0 until list.size) {
//
//                                if (nlist.isEmpty() && selectedPosition == position) {
//                                    nlist.add(
//                                        selectedSportModel(
//                                            ItemsviewModel.id,
//                                            level.toString(),
//                                            sportsLevelName.toString()
//                                        )
//                                    )
//                                } else {
//                                    if (nlist.isNotEmpty() && selectedPosition == position) {
//                                        for (i in 0 until nlist.size!!) {
//                                            val modelNew = nlist[i]
//                                            if (modelNew.sportId == ItemsviewModel.id) {
//                                                nlist[i] = selectedSportModel(
//                                                    ItemsviewModel.id,
//                                                    level.toString(),
//                                                    sportsLevelName.toString()
//                                                )
//                                            }
//                                        }
//                                    } else {
//                                        nlist.add(
//                                            selectedSportModel(
//                                                ItemsviewModel.id,
//                                                level.toString(),
//                                                sportsLevelName.toString()
//                                            )
//                                        )
//                                    }
//                                }
//                            }

                    }

                    binding.superstar.setOnClickListener {
                        binding.superstar.isEnabled = false
                        binding.superstar.setCardBackgroundColor(Color.parseColor("#F95047"))
                        binding.superstarTv.setTextColor(Color.WHITE)

                        level = "5"
                        sportsLevelName = binding.superstarTv.text.toString().trim()

                        binding.beginner.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.beginnerTv.setTextColor(Color.parseColor("#F95047"))

                        binding.novice.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.noviceTv.setTextColor(Color.parseColor("#F95047"))

                        binding.intermediate.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.intermediateTv.setTextColor(Color.parseColor("#F95047"))

                        binding.experienced.setCardBackgroundColor(Color.parseColor("#ffffff"))
                        binding.experiencedTv.setTextColor(Color.parseColor("#F95047"))

//                                if (position in 0 until list.size) {
//                                    if (binding.checkbox.isChecked == true) {
//                                        nlist.add(
//                                            selectedSportModel(
//                                                ItemsviewModel.id,
//                                                level.toString(),
//                                                sportsLevelName.toString()
//                                            )
//                                        )
//                                    } else {
//                                        nlist.remove(
//                                            selectedSportModel(
//                                                ItemsviewModel.id,
//                                                level.toString(),
//                                                sportsLevelName.toString()
//                                            )
//                                        )
//                                    }
//                                }
                        if (position in 0 until list.size) {

                            if (nlist.isEmpty() && selectedPosition == position) {
                                nlist.add(
                                    selectedSportModel(
                                        ItemsviewModel.id,
                                        level.toString(),
                                        sportsLevelName.toString()
                                    )
                                )
                            } else {
                                if (nlist.isNotEmpty() && selectedPosition == position) {

                                    for (i in 0 until nlist.size!!) {
                                        val modelNew = nlist[i]
                                        if (modelNew.sportId == ItemsviewModel.id) {
                                            nlist[i] = selectedSportModel(
                                                ItemsviewModel.id,
                                                level.toString(),
                                                sportsLevelName.toString()
                                            )
                                        }
                                    }
                                } else {
                                    nlist.add(
                                        selectedSportModel(
                                            ItemsviewModel.id,
                                            level.toString(),
                                            sportsLevelName.toString()
                                        )
                                    )
                                }
                            }
                        }

                    }
                }
                else {
                    binding.beginner.strokeColor = Color.parseColor("#80F95047")
                    binding.beginner.setCardBackgroundColor(Color.parseColor("#ffffff"))
                    binding.beginnerTv.setTextColor(Color.parseColor("#80F95047"))
                    binding.noviceTv.setTextColor(Color.parseColor("#80F95047"))
                    binding.novice.strokeColor = Color.parseColor("#80F95047")
                    binding.novice.setCardBackgroundColor(Color.parseColor("#ffffff"))
                    binding.intermediateTv.setTextColor(Color.parseColor("#80F95047"))
                    binding.intermediate.setCardBackgroundColor(Color.parseColor("#ffffff"))
                    binding.intermediate.strokeColor = Color.parseColor("#80F95047")
                    binding.experienced.setCardBackgroundColor(Color.parseColor("#ffffff"))
                    binding.experiencedTv.setTextColor(Color.parseColor("#80F95047"))
                    binding.experienced.strokeColor = Color.parseColor("#80F95047")
                    binding.superstar.setCardBackgroundColor(Color.parseColor("#ffffff"))
                    binding.superstarTv.setTextColor(Color.parseColor("#80F95047"))
                    binding.superstar.strokeColor = Color.parseColor("#80F95047")

//           adapter!!.unselect(position)

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