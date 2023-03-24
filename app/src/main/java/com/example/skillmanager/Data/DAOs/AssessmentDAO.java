package com.example.skillmanager.Data.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.skillmanager.Data.Entities.Assessment;

import java.util.List;

@Dao
public interface AssessmentDAO {

    @Query("SELECT * from assessments")
    public List<Assessment> getAllAssessments();

    @Query("SELECT * from assessments WHERE assessmentId = :id")
    public Assessment findAssessmentById(long id);

    @Query("SELECT * from assessments WHERE assignment_id = :assignmentId")
    public List<Assessment> getAssessmentsForAssignment(long assignmentId);

    @Insert(onConflict = OnConflictStrategy.FAIL)
    public void insertAssessment(Assessment assessment);

    @Update
    public void updateAssessment(Assessment assessment);

    @Delete
    public void deleteAssessment(Assessment assessment);

    @Query("DELETE FROM assessments WHERE assignment_id = :assignmentId")
    public void deleteAssessmentsForAssignment(long assignmentId);
}
