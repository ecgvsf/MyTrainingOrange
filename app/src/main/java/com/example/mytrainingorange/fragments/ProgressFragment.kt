package com.example.mytrainingorange.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.mytrainingorange.MainActivity
import com.example.mytrainingorange.R
import com.example.mytrainingorange.listener.OnSwipeTouchListener

class ProgressFragment : Fragment() {
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        view?.findViewById<FrameLayout>(R.id.layout)
            ?.setOnTouchListener(object : OnSwipeTouchListener(this@ProgressFragment.context) {
                override fun onSwipeRight() {
                    super.onSwipeRight()
                    (activity as MainActivity?)!!.openMenu()
                }
            })

        return inflater.inflate(R.layout.fragment_progress, container, false)
    }
}