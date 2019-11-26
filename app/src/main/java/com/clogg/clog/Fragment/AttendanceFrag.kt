package com.clogg.clog.Fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Room
import com.chatter.chatter.Database.AppDatabase
import com.chatter.chatter.Database.StatusDatabase
import com.chatter.chatter.Database.SubjectDatabase
import com.clogg.clog.Activities.AttendanceSettingAct
import com.clogg.clog.Adapter.CardStackAdapter
import com.clogg.clog.Adapter.SpotDiffCallback
import com.clogg.clog.Database.Status
import com.clogg.clog.R
import com.yuyakaido.android.cardstackview.*
import com.yuyakaido.android.cardstackview.internal.CardStackDataObserver
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_attendance.view.*
import kotlinx.android.synthetic.main.item.view.*
import java.util.*
import kotlin.collections.ArrayList


class AttendanceFrag : Fragment(), CardStackListener {

    val db: AppDatabase by lazy {
        Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java,
            "SubjectName.db"
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    val dbStatus: StatusDatabase by lazy {
        Room.databaseBuilder(
            requireContext(),
            StatusDatabase::class.java,
            "Status.db"
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    val dbSubject: SubjectDatabase by lazy {
        Room.databaseBuilder(
            requireContext(),
            SubjectDatabase::class.java,
            "Subject.db"
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    val subjectList : ArrayList<String> = arrayListOf()
    lateinit var cardStackView : CardStackView
    lateinit var manager : CardStackLayoutManager
    lateinit var v : View

    override fun onCardDragging(direction: Direction, ratio: Float) {
        if(direction == Direction.Left) {
            view!!.notPresent.setImageResource(R.drawable.dislike_red)
            view!!.yesPresent.setImageResource(R.drawable.like)
        } else {
            view!!.yesPresent.setImageResource(R.drawable.like_green)
            view!!.notPresent.setImageResource(R.drawable.dislike)
        }
    }

    override fun onCardSwiped(direction: Direction) {
        view!!.yesPresent.setImageResource(R.drawable.like)
        view!!.notPresent.setImageResource(R.drawable.dislike)
//        Log.d("CardStackView", "onCardSwiped: p = ${manager.topPosition}, d = $direction")
//        Log.d("CardStackView", "onCardSwiped: subjectName : ${subjectList[manager.topPosition - 1]}")
        var classesAttended = 0
        classesAttended += dbSubject.SubjectDao().getClasses(subjectList[manager.topPosition - 1])

        if(manager.topPosition == subjectList.size) {
            val calendar = Calendar.getInstance()
            val status = Status(
                date = calendar.get(Calendar.DATE).toString(),
                month = calendar.get(Calendar.MONTH).toString(),
                year = calendar.get(Calendar.YEAR).toString(),
                status = 1
            )
            dbStatus.StatusDao().insertRow(status)
        }
    }

    override fun onCardRewound() {
        Log.d("CardStackView", "onCardRewound: ${manager.topPosition}")
    }

    override fun onCardCanceled() {
        view!!.yesPresent.setImageResource(R.drawable.like)
        view!!.notPresent.setImageResource(R.drawable.dislike)
        Log.d("CardStackView", "onCardCanceled: ${manager.topPosition}")
    }

    override fun onCardAppeared(view: View, position: Int) {
//        val textView = view.findViewById<TextView>(R.id.item_name)
        Log.d("CardStackView", "onCardAppeared: ($position)")
    }

    override fun onCardDisappeared(view: View, position: Int) {
//        val textView = view.findViewById<TextView>(R.id.item_name)
        Log.d("CardStackView", "onCardDisappeared: ($position)")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_attendance, container, false)
        v = view
        view!!.addSubject.setOnClickListener {
            val intent = Intent(view!!.context, AttendanceSettingAct::class.java)
            startActivity(intent)
        }

        val subjects = db.SubjectNameDao().getSubjects()
        for(subject in subjects) {
            val calendar = Calendar.getInstance()

            if(calendar.get(Calendar.DAY_OF_WEEK) == 2) {
                if(subject.monday) {
                    subjectList.add(subject.name)
                }
            } else if(calendar.get(Calendar.DAY_OF_WEEK) == 3) {
                if(subject.tuesday) {
                    subjectList.add(subject.name)
                }
            } else if(calendar.get(Calendar.DAY_OF_WEEK) == 4) {
                if(subject.wednesday) {
                    subjectList.add(subject.name)
                }
            } else if(calendar.get(Calendar.DAY_OF_WEEK) == 5) {
                if(subject.thursday) {
                    subjectList.add(subject.name)
                }
            } else if(calendar.get(Calendar.DAY_OF_WEEK) == 6) {
                if(subject.friday) {
                    subjectList.add(subject.name)
                }
            } else if(calendar.get(Calendar.DAY_OF_WEEK) == 7) {
                if(subject.saturday) {
                    subjectList.add(subject.name)
                }
            }
        }

        view!!.swipeRefreshHomeAttendance.setOnRefreshListener {
            Handler().postDelayed({
                view!!.swipeRefreshHomeAttendance.isRefreshing = false
            },2000)
        }

        cardStackView = view!!.findViewById(R.id.card_stack_view)
        manager = CardStackLayoutManager(activity, this)

        cardStackView.layoutManager = manager
        cardStackView.adapter = CardStackAdapter(subjectList)

        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(false)
        manager.setStackFrom(StackFrom.Top)
        manager.setSwipeThreshold(0.3F)
        manager.setMaxDegree(50.0f)

        view!!.undo.setOnClickListener {
            val setting = RewindAnimationSetting.Builder()
                .setDirection(Direction.Bottom)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(DecelerateInterpolator())
                .build()
            manager.setRewindAnimationSetting(setting)
            cardStackView.rewind()
        }

        view!!.yesPresent.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Right)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            cardStackView.swipe()
        }

        view!!.notPresent.setOnClickListener {
            val setting = SwipeAnimationSetting.Builder()
                .setDirection(Direction.Left)
                .setDuration(Duration.Normal.duration)
                .setInterpolator(AccelerateInterpolator())
                .build()
            manager.setSwipeAnimationSetting(setting)
            cardStackView.swipe()
        }
        return view
    }
}
