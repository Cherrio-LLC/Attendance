package com.attendance.lecturer.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

fun <T> StateFlow<T>.collectInView(lifecycleOwner: LifecycleOwner, action: (T) -> Unit){
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
            this@collectInView.collectLatest {
                action.invoke(it)
            }
        }
    }
}
fun doIt(lifecycleOwner: LifecycleOwner, action: suspend () -> Unit){
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
            action()
        }
    }
}