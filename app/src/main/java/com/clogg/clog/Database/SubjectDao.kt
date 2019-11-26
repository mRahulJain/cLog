package com.chatter.chatter.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.clogg.clog.Database.Subject


@Dao
interface SubjectDao {
    @Insert
    fun insertRow(subject: Subject)

    @Query("Select * from Subject")
    fun getSubjects() : Subject

    @Query("Select attendedClasses from Subject where subject = :subject")
    fun getClasses(subject : String) : Int

    @Query("Delete from Subject")
    fun deleteUser()
}