package com.clogg.clog.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Status(
    @PrimaryKey(autoGenerate = true)
    val id : Long?=null,
    val date : String,
    val month : String,
    val year : String,
    val status : Int
)