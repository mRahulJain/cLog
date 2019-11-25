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
import com.clogg.clog.Activities.AttendanceSettingAct
import com.clogg.clog.Adapter.CardStackAdapter
import com.clogg.clog.Adapter.SpotDiffCallback
import com.clogg.clog.R
import com.yuyakaido.android.cardstackview.*
import com.yuyakaido.android.cardstackview.internal.CardStackDataObserver
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_attendance.view.*
import kotlinx.android.synthetic.main.item.view.*
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
    val subjectList : ArrayList<String> = arrayListOf()
    lateinit var cardStackView : CardStackView
    lateinit var manager : CardStackLayoutManager
    lateinit var v : View

    override fun onCardDragging(direction: Direction, ratio: Float) {
        Log.d("myCHECK", "${ratio}")
        if(direction == Direction.Left) {
            view!!.notEmptyDatabase.setBackgroundColor(Color.RED)
        } else {
            view!!.notEmptyDatabase.setBackgroundColor(Color.GREEN)
        }
    }

    override fun onCardSwiped(direction: Direction) {
        view!!.notEmptyDatabase.setBackgroundColor(Color.WHITE)
        Log.d("CardStackView", "onCardSwiped: p = ${manager.topPosition}, d = $direction")
    }

    override fun onCardRewound() {
        Log.d("CardStackView", "onCardRewound: ${manager.topPosition}")
    }

    override fun onCardCanceled() {
        view!!.notEmptyDatabase.setBackgroundColor(Color.WHITE)
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
            subjectList.add(subject.name)
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
