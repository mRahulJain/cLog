package com.clogg.clog.Fragment

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.clogg.clog.Activities.AttendanceSettingAct
import java.util.*

class DatePickerFragment(context: AttendanceSettingAct) : DialogFragment() {
    val req = context

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val date = calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(req , activity as DatePickerDialog.OnDateSetListener, year, month, date)
    }
}