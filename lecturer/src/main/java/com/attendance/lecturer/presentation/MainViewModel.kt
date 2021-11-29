package com.attendance.lecturer.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 *Created by Ayodele on 11/28/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableLiveData<LatAndLon>()
    val state: LiveData<LatAndLon> = _state


    fun setLatAndLon(latAndLon: LatAndLon){
        _state.value = latAndLon
    }


}