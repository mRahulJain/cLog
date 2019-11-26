package com.chatter.chatter.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.clogg.clog.Database.Status
import com.clogg.clog.Database.StatusDao
import com.clogg.clog.Database.Subject

@Database(entities = arrayOf(SubjectName::class), version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun SubjectNameDao() : SubjectNameDao
}

@Database(entities = arrayOf(Subject::class), version = 2)
abstract class SubjectDatabase : RoomDatabase() {
    abstract fun SubjectDao() : SubjectDao
}

@Database(entities = arrayOf(Status::class), version = 2)
abstract class StatusDatabase : RoomDatabase() {
    abstract fun StatusDao() : StatusDao
}