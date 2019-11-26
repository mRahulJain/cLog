package com.clogg.clog.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.clogg.clog.Database.Subject


@Dao
interface StatusDao {
    @Insert
    fun insertRow(status: Status)

    @Query("Select * from Status")
    fun getStatus() : Status

    @Query("Delete from Status")
    fun deleteUser()
}