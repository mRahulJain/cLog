package com.clogg.clog.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Subject(
    @PrimaryKey(autoGenerate = true)
    val id : Long?=null,
    val day : String,
    val date : String,
    val status : String
)