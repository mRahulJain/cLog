package com.clogg.clog.Fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.chatter.chatter.Database.AppDatabase
import com.clogg.clog.Activities.AttendanceSettingAct
import com.clogg.clog.R
import com.lorentzos.flingswipe.SwipeFlingAdapterView
import com.lorentzos.flingswipe.SwipeFlingAdapterView.onFlingListener
import kotlinx.android.synthetic.main.fragment_attendance.*
import kotlinx.android.synthetic.main.fragment_attendance.view.*
import java.util.*
import kotlin.collections.ArrayList


class AttendanceFrag : Fragment() {

    var i = 0
    val db: AppDatabase by lazy {
        Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java,
            "SubjectName.db"
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_attendance, container, false)

        view!!.addSubject.setOnClickListener {
            val intent = Intent(view!!.context, AttendanceSettingAct::class.java)
            startActivity(intent)
        }

        onCreatePerform(view!!)

        view!!.swipeRefreshHomeAttendance.setOnRefreshListener {
            Handler().postDelayed({
                onCreatePerform(view!!)
                view!!.swipeRefreshHomeAttendance.isRefreshing = false
            },2000)
        }

        view!!.yesPresent.setOnClickListener {
            
        }
        return view
    }

    fun onCreatePerform(view: View) {
        val subjects = db.SubjectNameDao().getSubjects()
        val al : ArrayList<String> = arrayListOf()
        for(subject in subjects) {
            al.add(subject.name)
        }

        if(al.size == 0) {
            view!!.emptyDatabase.isVisible = true
            view!!.notEmptyDatabase.isVisible = false
        } else {
            view!!.emptyDatabase.isVisible = false
            view!!.notEmptyDatabase.isVisible = true
        }

        val flingContainer =
            view!!.findViewById(R.id.frame) as SwipeFlingAdapterView


        val arrayAdapter = ArrayAdapter<String>(view!!.context, R.layout.item, R.id.helloText, al)
        flingContainer.adapter = arrayAdapter
        flingContainer.setFlingListener(object : onFlingListener {
            override fun removeFirstObjectInAdapter() { // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!")
                al.removeAt(0)
                arrayAdapter.notifyDataSetChanged()
            }

            override fun onLeftCardExit(dataObject: Any) {
                Toast.makeText(view!!.context, "Left!", Toast.LENGTH_SHORT).show()
            }

            override fun onRightCardExit(dataObject: Any) {
                Toast.makeText(view!!.context, "Right!", Toast.LENGTH_SHORT).show()
            }

            override fun onAdapterAboutToEmpty(itemsInAdapter: Int) {
                al.add("XML " + java.lang.String.valueOf(i))
                arrayAdapter.notifyDataSetChanged()
                Log.d("LIST", "notified")
                i++
            }

            override fun onScroll(scrollProgressPercent: Float) {
                val view = flingContainer.selectedView
                view.findViewById<LinearLayout>(R.id.item_swipe_right_indicator).alpha =
                    if (scrollProgressPercent < 0) -scrollProgressPercent else 0.toFloat()
                view.findViewById<LinearLayout>(R.id.item_swipe_left_indicator).alpha =
                    if (scrollProgressPercent > 0) scrollProgressPercent else 0.toFloat()
            }
        })
        flingContainer.setOnItemClickListener { itemPosition, dataObject ->
            Toast.makeText(
                view!!.context,
                "Clicked!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


}
