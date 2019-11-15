package com.clogg.clog.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.clogg.clog.Activities.AttendanceSettingAct

import com.clogg.clog.R
import kotlinx.android.synthetic.main.fragment_setting.view.*

class SettingFrag : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        view!!.attendance.setOnClickListener {
            val intent = Intent(view!!.context, AttendanceSettingAct::class.java)
            startActivity(intent)
        }

        return view
    }


}
