package com.clogg.clog.Activities

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.DatePicker
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.room.Room
import com.chatter.chatter.Database.AppDatabase
import com.chatter.chatter.Database.SubjectName
import com.clogg.clog.Fragment.DatePickerFragment
import com.clogg.clog.R
import kotlinx.android.synthetic.main.activity_attendance_setting.*
import kotlinx.android.synthetic.main.content_view.view.*
import kotlinx.android.synthetic.main.header_view.view.*
import java.util.*
import kotlin.collections.ArrayList

class AttendanceSettingAct : AppCompatActivity(),
    DatePickerDialog.OnDateSetListener {

    var dateType : String = ""

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, date: Int) {
        if(dateType == "start") {
            startDate.text = "$date/${month + 1}/$year"
            startDate.isVisible = true
            subjects = db.SubjectNameDao().getSubjects()
            for(subject in subjects) {
                db.SubjectNameDao().updateStartDate(date.toString(), subject.name)
                db.SubjectNameDao().updateStartMonth(month.toString(), subject.name)
                db.SubjectNameDao().updateStartYear(year.toString(), subject.name)
            }
        } else {
            endDate.text = "$date/${month + 1}/$year"
            endDate.isVisible = true
            subjects = db.SubjectNameDao().getSubjects()
            for(subject in subjects) {
                db.SubjectNameDao().updateEndDate(date.toString(), subject.name)
                db.SubjectNameDao().updateEndtMonth(month.toString(), subject.name)
                db.SubjectNameDao().updateEndYear(year.toString(), subject.name)
            }
        }
    }

    val db: AppDatabase by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "SubjectName.db"
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    var subjects : List<SubjectName> = listOf()

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
        subjects = db.SubjectNameDao().getSubjects()
        for(subject in subjects) {
            if(subject.startDate != "") {
                startDate.isVisible = true
                startDate.text = "${subject.startDate}/${subject.startMonth.toInt() + 1}/${subject.startYear}"
            }

            if(subject.endDate != "") {
                endDate.isVisible = true
                endDate.text = "${subject.endDate}/${subject.endMonth.toInt() + 1}/${subject.endYear}"
            }
            expandable1.emptySubjectList.isVisible = false
            expandable2.emptySubjectList.isVisible = false
            expandable3.emptySubjectList.isVisible = false
            expandable4.emptySubjectList.isVisible = false
            expandable5.emptySubjectList.isVisible = false
            expandable6.emptySubjectList.isVisible = false
            for(i in 1..6) {
                val checkBox = CheckBox(this)
                checkBox.text = subject.name
                when(i) {
                    1 -> {
                        checkBox.isChecked = subject.monday
                        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                            db.SubjectNameDao().updateMonday(isChecked, subject.name)
                        }
                        expandable1.fragContent.addView(checkBox)
                    }
                    2 -> {
                        checkBox.isChecked = subject.tuesday
                        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                            db.SubjectNameDao().updateTuesday(isChecked, subject.name)
                        }
                        expandable2.fragContent.addView(checkBox)
                    }
                    3 -> {
                        checkBox.isChecked = subject.wednesday
                        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                            db.SubjectNameDao().updateWednesday(isChecked, subject.name)
                        }
                        expandable3.fragContent.addView(checkBox)
                    }
                    4 -> {
                        checkBox.isChecked = subject.thursday
                        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                            db.SubjectNameDao().updateThursday(isChecked, subject.name)
                        }
                        expandable4.fragContent.addView(checkBox)
                    }
                    5 -> {
                        checkBox.isChecked = subject.friday
                        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                            db.SubjectNameDao().updateFriday(isChecked, subject.name)
                        }
                        expandable5.fragContent.addView(checkBox)
                    }
                    6 -> {
                        checkBox.isChecked = subject.saturday
                        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                            db.SubjectNameDao().updateSaturday(isChecked, subject.name)
                        }
                        expandable6.fragContent.addView(checkBox)
                    }
                }
            }
        }

        swipeRefreshAttendanceDatabase.setOnRefreshListener {
            Handler().postDelayed({

            }, 3000)
        }

        startTimeSession.setOnClickListener {
            dateType = "start"
            val datePicker = DatePickerFragment(this)
            datePicker.show(supportFragmentManager, "Date picker")
        }
        endTimeSession.setOnClickListener {
            dateType = "end"
            val datePicker = DatePickerFragment(this)
            datePicker.show(supportFragmentManager, "Date picker")
        }

        addSubjectP.setOnClickListener {
            if(subName.text.toString() == "") {
                return@setOnClickListener
            }

            val subjectName = SubjectName(
                name = subName.text.toString(),
                monday = false,
                tuesday = false,
                wednesday = false,
                thursday = false,
                friday = false,
                saturday = false,
                startDate = "",
                startMonth = "",
                startYear = "",
                endDate = "",
                endMonth = "",
                endYear = ""
            )
            for(subject in subjects) {
                if(subject.name == subName.text.toString()) {
                    Toast.makeText(this,
                        "Already present",
                        Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            db.SubjectNameDao().insertRow(subjectName)
            expandable1.emptySubjectList.isVisible = false
            expandable2.emptySubjectList.isVisible = false
            expandable3.emptySubjectList.isVisible = false
            expandable4.emptySubjectList.isVisible = false
            expandable5.emptySubjectList.isVisible = false
            expandable6.emptySubjectList.isVisible = false
            for(i in 1..6) {
                val checkBox = CheckBox(this)
                checkBox.text = subName.text.toString()
                val subNameHere = subName.text.toString()
                when(i) {
                    1 -> {
                        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                            db.SubjectNameDao().updateMonday(isChecked, subNameHere)
                        }
                        expandable1.fragContent.addView(checkBox)
                    }
                    2 -> {
                        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                            db.SubjectNameDao().updateTuesday(isChecked, subNameHere)
                        }
                        expandable2.fragContent.addView(checkBox)
                    }
                    3 -> {
                        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                            db.SubjectNameDao().updateWednesday(isChecked, subNameHere)
                        }
                        expandable3.fragContent.addView(checkBox)
                    }
                    4 -> {
                        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                            db.SubjectNameDao().updateThursday(isChecked, subNameHere)
                        }
                        expandable4.fragContent.addView(checkBox)
                    }
                    5 -> {
                        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                            db.SubjectNameDao().updateFriday(isChecked, subNameHere)
                        }
                        expandable5.fragContent.addView(checkBox)
                    }
                    6 -> {
                        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                            db.SubjectNameDao().updateSaturday(isChecked, subNameHere)
                        }
                        expandable6.fragContent.addView(checkBox)
                    }
                }
            }
            subName.setText("")
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
