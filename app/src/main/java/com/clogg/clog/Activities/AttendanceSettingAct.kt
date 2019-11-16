package com.clogg.clog.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.CheckBox
import androidx.core.view.isVisible
import com.clogg.clog.R
import kotlinx.android.synthetic.main.activity_attendance_setting.*
import kotlinx.android.synthetic.main.content_view.view.*
import kotlinx.android.synthetic.main.header_view.view.*

class AttendanceSettingAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance_setting)

        setSupportActionBar(toolbarAttendanceSettings)
        supportActionBar!!.title = "Attendance Database"
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        expandable1.dayName.text = "Monday"
        expandable2.dayName.text = "Tuesday"
        expandable3.dayName.text = "Wednesday"
        expandable4.dayName.text = "Thursday"
        expandable5.dayName.text = "Friday"
        expandable6.dayName.text = "Saturday"

        addSubjectP.setOnClickListener {
            if(subjectName.text.toString() == "") {
                return@setOnClickListener
            }

            expandable1.emptySubjectList.isVisible = false
            expandable2.emptySubjectList.isVisible = false
            expandable3.emptySubjectList.isVisible = false
            expandable4.emptySubjectList.isVisible = false
            expandable5.emptySubjectList.isVisible = false
            expandable6.emptySubjectList.isVisible = false

            for(i in 1..6) {
                val checkBox = CheckBox(this)
                checkBox.text = subjectName.text.toString()
                when(i) {
                    1 -> expandable1.fragContent.addView(checkBox)
                    2 -> expandable2.fragContent.addView(checkBox)
                    3 -> expandable3.fragContent.addView(checkBox)
                    4 -> expandable4.fragContent.addView(checkBox)
                    5 -> expandable5.fragContent.addView(checkBox)
                    6 -> expandable6.fragContent.addView(checkBox)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
