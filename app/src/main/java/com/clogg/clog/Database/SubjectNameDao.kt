package com.chatter.chatter.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface SubjectNameDao {
    @Insert
    fun insertRow(user: SubjectName)

    @Query("Select * from SubjectName")
    fun getUser() : SubjectName

    @Query("Delete from SubjectName")
    fun deleteUser()
}