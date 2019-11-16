package com.chatter.chatter.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.clogg.clog.Database.Subject

@Database(entities = arrayOf(SubjectName::class), version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun SubjectNameDao() : SubjectNameDao
}

@Database(entities = arrayOf(Subject::class), version = 1)
abstract class SubjectDatabase : RoomDatabase() {
    abstract fun SubjectDao() : SubjectDao
}