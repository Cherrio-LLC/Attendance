package com.cherrio.attendance.presentation.home.attendance_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cherrio.attendance.databinding.ItemAttendanceBinding
import com.cherrio.attendance.domain.model.ClassAndAttendance

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

class AttendanceAdapter:
    RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder>() {

    private lateinit var attendanceClicked: (ClassAndAttendance, Boolean) -> Unit
    private var list = mutableListOf<ClassAndAttendance>()

    inner class AttendanceViewHolder(private val binding: ItemAttendanceBinding):
            RecyclerView.ViewHolder(binding.root){

                fun bind(classAndAttendance: ClassAndAttendance){
                    binding.apply {
                        classTitle.text = classAndAttendance.name

                        btnExport.setOnClickListener {
                            attendanceClicked(classAndAttendance, true)
                        }
                        root.setOnClickListener {
                            attendanceClicked(classAndAttendance, false)
                        }
                    }
                }

            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendanceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AttendanceViewHolder(ItemAttendanceBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: AttendanceViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount() = list.size

    fun addItemClicked(attendanceClicked: (ClassAndAttendance, Boolean) -> Unit){
        this.attendanceClicked = attendanceClicked
    }
    fun submitList(list: MutableList<ClassAndAttendance>){
        this.list = list
        notifyDataSetChanged()
    }

}