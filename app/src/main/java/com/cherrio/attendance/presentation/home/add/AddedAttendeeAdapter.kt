package com.cherrio.attendance.presentation.home.add

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cherrio.attendance.data.entity.AttendeeEntity
import com.cherrio.attendance.databinding.ItemAddAttendeeBinding
import com.cherrio.attendance.databinding.ItemAttendanceBinding
import com.cherrio.attendance.databinding.ItemAttendeeBinding
import com.cherrio.attendance.domain.model.ClassAndAttendance

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

class AddedAttendeeAdapter:
    RecyclerView.Adapter<AddedAttendeeAdapter.AddAttendeeViewHolder>() {

    private var list = mutableListOf<AttendeeEntity>()

    inner class AddAttendeeViewHolder(private val binding: ItemAddAttendeeBinding):
            RecyclerView.ViewHolder(binding.root){

                fun bind(attendeeEntity: AttendeeEntity, position: Int){
                    binding.apply {
                        txtSerialNo.text = "${attendeeEntity.id + 1}."
                        txtMatricNo.text = attendeeEntity.matricNo
                    }
                }

            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddAttendeeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AddAttendeeViewHolder(ItemAddAttendeeBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: AddAttendeeViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item, position)
    }

    override fun getItemCount() = list.size

    fun submitList(attendeeEntity: AttendeeEntity){
        list.add(0,attendeeEntity)
        notifyDataSetChanged()
    }

}