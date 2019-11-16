package com.chatter.chatter.Database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface SubjectNameDao {
    @Insert
    fun insertRow(subjectName: SubjectName)

    @Query("Select * from SubjectName")
    fun getSubjects() : List<SubjectName>

    @Query("Delete from SubjectName")
    fun deleteSubject()

    @Query("Update SubjectName SET monday = :bool WHERE name = :name")
    fun updateMonday(bool : Boolean, name : String)
    @Query("Update SubjectName SET tuesday = :bool WHERE name = :name")
    fun updateTuesday(bool : Boolean, name : String)
    @Query("Update SubjectName SET wednesday = :bool WHERE name = :name")
    fun updateWednesday(bool : Boolean, name : String)
    @Query("Update SubjectName SET thursday = :bool WHERE name = :name")
    fun updateThursday(bool : Boolean, name : String)
    @Query("Update SubjectName SET friday = :bool WHERE name = :name")
    fun updateFriday(bool : Boolean, name : String)
    @Query("Update SubjectName SET saturday = :bool WHERE name = :name")
    fun updateSaturday(bool : Boolean, name : String)

    @Query("Update SubjectName SET startDate = :date WHERE name = :name")
    fun updateStartDate(date : String, name : String)
    @Query("Update SubjectName SET startMonth = :month WHERE name = :name")
    fun updateStartMonth(month : String, name : String)
    @Query("Update SubjectName SET startYear = :year WHERE name = :name")
    fun updateStartYear(year : String, name : String)

    @Query("Update SubjectName SET endDate = :date WHERE name = :name")
    fun updateEndDate(date : String, name : String)
    @Query("Update SubjectName SET endMonth = :month WHERE name = :name")
    fun updateEndtMonth(month : String, name : String)
    @Query("Update SubjectName SET endYear = :year WHERE name = :name")
    fun updateEndYear(year : String, name : String)
}