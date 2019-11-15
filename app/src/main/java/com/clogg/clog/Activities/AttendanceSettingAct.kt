package com.clogg.clog.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.clogg.clog.R
import kotlinx.android.synthetic.main.activity_attendance_setting.*
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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
