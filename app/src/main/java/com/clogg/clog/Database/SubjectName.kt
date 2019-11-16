package com.chatter.chatter.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SubjectName(
    @PrimaryKey(autoGenerate = true)
    val id : Long?=null,
    val name : String,
    val startDate : String,
    val endDate : String
)

@Entity
data class Subject(
    @PrimaryKey(autoGenerate = true)
    val id : Long?=null,
    val day : String,
    val date : String,
    val status : String
)

