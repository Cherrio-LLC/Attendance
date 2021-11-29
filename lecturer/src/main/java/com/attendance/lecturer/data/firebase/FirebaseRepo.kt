package com.attendance.student.data.firebase

import com.attendance.lecturer.data.firebase.Attendance
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 *Created by Ayodele on 11/28/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@Singleton
class FirebaseRepo @Inject constructor() {

    fun createClass(classDetails: ClassDetails) = callbackFlow {
        FirebaseDatabase.getInstance().reference.child("CLASS_DETAILS")
            .child(classDetails.classId.toString())
            .setValue(classDetails).addOnCompleteListener {
                if (it.isSuccessful){
                   trySend(true)
                }else{
                    trySend(false)
                }
            }
        awaitClose()
    }
    fun deleteClass(id: String) = callbackFlow{
        FirebaseDatabase.getInstance().reference.child("CLASS_DETAILS")
            .child(id)
            .removeValue().addOnCompleteListener {
                if (it.isSuccessful){
                    trySend(true)
                }else{
                    trySend(false)
                }
            }
        awaitClose()
    }
    fun deleteAttendance(classId: String, attendance: Attendance){
        val ref = FirebaseDatabase.getInstance().reference
            .child("ATTENDANCE")
            .child(classId)
            .child(attendance.matricNo.replace("/","*")).removeValue()
    }

    fun getClassAttendances(classId: String) = callbackFlow {
        val callback = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    snapshot.children.forEach {
                    val att = it.key!!.replace("*","/")
                    trySend(Attendance(att))
                    }
                }else{
                  trySend(null)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        }
        val ref = FirebaseDatabase.getInstance().reference
            .child("ATTENDANCE")
            .child(classId)

        ref.addValueEventListener(callback)

        awaitClose {
            ref.removeEventListener(callback)
        }
    }
}

