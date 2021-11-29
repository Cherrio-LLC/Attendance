package com.attendance.lecturer.presentation.attendee_list


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.attendance.lecturer.data.entity.AttendeeEntity
import com.attendance.lecturer.databinding.ItemAttendeeBinding


/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

class AttendeeAdapter:
    RecyclerView.Adapter<AttendeeAdapter.AttendeeViewHolder>() {

    private lateinit var attendeeDelete: (AttendeeEntity) -> Unit
    private var list = mutableListOf<AttendeeEntity>()

    inner class AttendeeViewHolder(private val binding: ItemAttendeeBinding):
            RecyclerView.ViewHolder(binding.root){

                fun bind(attendeeEntity: AttendeeEntity, position: Int){
                    binding.apply {
                        txtSerialNo.text = "${position + 1}."
                        txtMatricNo.text = attendeeEntity.matricNo
                        btnDelete.setOnClickListener {
                            attendeeDelete(attendeeEntity)
                        }

                    }
                }

            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttendeeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AttendeeViewHolder(ItemAttendeeBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: AttendeeViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item, position)
    }

    override fun getItemCount() = list.size

    fun addItemClicked(attendeeDelete: (AttendeeEntity) -> Unit){
        this.attendeeDelete = attendeeDelete
    }
    fun submitList(attendeeEntity: AttendeeEntity){
        list.add(0,attendeeEntity)
        notifyDataSetChanged()
    }
    fun getList() = list

}