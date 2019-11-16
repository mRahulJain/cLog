package com.chatter.chatter.Database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Month
import java.time.Year

@Entity
data class SubjectName(
    @PrimaryKey(autoGenerate = true)
    val id : Long?=null,
    val name : String,
    val monday : Boolean,
    val tuesday : Boolean,
    val wednesday : Boolean,
    val thursday : Boolean,
    val friday : Boolean,
    val saturday : Boolean,
    val startDate : String,
    val startMonth : String,
    val startYear : String,
    val endDate : String,
    val endMonth: String,
    val endYear: String
)

