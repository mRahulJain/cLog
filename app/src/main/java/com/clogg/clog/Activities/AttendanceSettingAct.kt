package com.clogg.clog.Activities

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    val calendar = Calendar.getInstance()

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, date: Int) {
        calendar.set(Calendar.YEAR , year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, date)
        startDate.text = "$date/$month/$year"
        startDate.isVisible = true
        subjects = db.SubjectNameDao().getSubjects()
        for(subject in subjects) {
            db.SubjectNameDao().updateStartDate(date.toString(), subject.name)
            db.SubjectNameDao().updateStartMonth(month.toString(), subject.name)
            db.SubjectNameDao().updateStartYear(year.toString(), subject.name)
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
            startDate.isVisible = true
            startDate.text = "${subject.startDate}/${subject.startMonth}/${subject.startYear}"
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

        startTimeSession.setOnClickListener {
            val datePicker = DatePickerFragment(this)
            datePicker.show(supportFragmentManager, "Date picker")
        }

        addSubjectP.setOnClickListener {
            if(subName.text.toString() == "") {
                return@setOnClickListener
            }

            val days : ArrayList<String> = arrayListOf()
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
            if(subjects.contains(subjectName)) {
                Toast.makeText(this,
                    "Already present",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
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
                when(i) {
                    1 -> {
                        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                            db.SubjectNameDao().updateMonday(isChecked, subName.text.toString())
                        }
                        expandable1.fragContent.addView(checkBox)
                    }
                    2 -> {
                        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                            db.SubjectNameDao().updateTuesday(isChecked, subName.text.toString())
                        }
                        expandable2.fragContent.addView(checkBox)
                    }
                    3 -> {
                        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                            db.SubjectNameDao().updateWednesday(isChecked, subName.text.toString())
                        }
                        expandable3.fragContent.addView(checkBox)
                    }
                    4 -> {
                        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                            db.SubjectNameDao().updateThursday(isChecked, subName.text.toString())
                        }
                        expandable4.fragContent.addView(checkBox)
                    }
                    5 -> {
                        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                            db.SubjectNameDao().updateFriday(isChecked, subName.text.toString())
                        }
                        expandable5.fragContent.addView(checkBox)
                    }
                    6 -> {
                        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                            db.SubjectNameDao().updateSaturday(isChecked, subName.text.toString())
                        }
                        expandable6.fragContent.addView(checkBox)
                    }
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
