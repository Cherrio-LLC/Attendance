package com.attendance.lecturer.presentation.classes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.attendance.lecturer.data.entity.AttendanceEntity
import com.attendance.lecturer.databinding.ItemClassBinding


/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

class AttendanceAdapter:
    RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder>() {

    private lateinit var attendanceClicked: (AttendanceEntity) -> Unit
    private var list = listOf<AttendanceEntity>()

    inner class AttendanceViewHolder(private val binding: ItemClassBinding):
            RecyclerView.ViewHolder(binding.root){

                fun bind(classAndAttendance: AttendanceEntity){
                    binding.apply {
                        classTitle.text = classAndAttendance.classTitle

                        btnExport.setOnClickListener {
                            attendanceClicked(classAndAttendance)
                        }
                    }
                }

            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AttendanceViewHolder(ItemClassBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: AttendanceViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount() = list.size

    fun addItemClicked(attendanceClicked: (AttendanceEntity) -> Unit){
        this.attendanceClicked = attendanceClicked
    }
    fun submitList(list: List<AttendanceEntity>){
        this.list = list
        notifyDataSetChanged()
    }

}