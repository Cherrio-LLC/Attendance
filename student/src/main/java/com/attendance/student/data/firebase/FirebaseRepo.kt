package com.attendance.student.data.firebase

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

    fun markAttendance(classId: String, matricNo: String) {
        FirebaseDatabase.getInstance().reference.child("ATTENDANCE")
            .child(classId)
            .child(matricNo.replace("/","*"))
            .setValue(true)
    }

    fun getClassDetails(classId: String) = callbackFlow {
        val callback = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val classDetails = snapshot.getValue(ClassDetails::class.java)
                    trySend(classDetails)
                    close()
                }else{
                  trySend(null)
                    close()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                close()
            }
        }
        val ref = FirebaseDatabase.getInstance().reference
            .child("CLASS_DETAILS")
            .child(classId)

            ref.addListenerForSingleValueEvent(callback)

        awaitClose {
            ref.removeEventListener(callback)
        }
    }
}

