package com.clogg.clog

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.clogg.clog.Fragment.AttendanceFrag
import com.clogg.clog.Fragment.ExpenseFrag
import com.clogg.clog.Fragment.SettingFrag

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    override fun onNavigationItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.navigation_settings -> {
            supportFragmentManager.beginTransaction().replace(R.id.frag, SettingFrag()).commit()
            true
        }
        R.id.navigation_attendance -> {
            supportFragmentManager.beginTransaction().replace(R.id.frag, AttendanceFrag()).commit()
            true
        }
        R.id.navigation_expense -> {
            supportFragmentManager.beginTransaction().replace(R.id.frag, ExpenseFrag()).commit()
            true
        }
        else -> false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        supportFragmentManager.beginTransaction().replace(R.id.frag, AttendanceFrag()).commit()
        navView.selectedItemId = R.id.navigation_attendance
        navView.setOnNavigationItemSelectedListener(this)
    }
}
