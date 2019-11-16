package com.chatter.chatter.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface SubjectDao {
    @Insert
    fun insertRow(user: Subject)

    @Query("Select * from Subject")
    fun getUser() : Subject

    @Query("Delete from Subject")
    fun deleteUser()
}