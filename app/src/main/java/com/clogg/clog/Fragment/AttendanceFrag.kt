package com.clogg.clog.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.clogg.clog.Activities.AttendanceSettingAct

import com.clogg.clog.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_attendance.view.*

class AttendanceFrag : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_attendance, container, false)

        view!!.addSubject.setOnClickListener {
            val intent = Intent(view!!.context, AttendanceSettingAct::class.java)
            startActivity(intent)
        }

        return view
    }


}
