package com.attendance.student.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 *Created by Ayodele on 11/23/2021.
 *Copyright (c) 2021 NQB8 All rights reserved.
 */

abstract class BaseFragment<B: ViewBinding>: Fragment() {
    private var _binding: B? = null
    protected val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getBinding(inflater)
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        useViews()
    }

    abstract fun getBinding(layoutInflater: LayoutInflater): B
    abstract fun useViews()

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()

    }
}