package com.example.skillmanager.Data.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.skillmanager.Data.Entities.Assignment;

import java.util.List;

@Dao
public interface AssignmentDAO {
    @Query("SELECT * FROM assignments")
    public LiveData<List<Assignment>> getAllAssignments();

    @Query("SELECT * FROM assignments WHERE assignmentId = :id")
    public LiveData<Assignment> findAssignmentById(long id);

    @Query("SELECT * FROM assignments WHERE cycle_id = :cycleId")
    public LiveData<List<Assignment>> getAssignmentsByCycle(long cycleId);

    @Insert(onConflict = OnConflictStrategy.FAIL)
    public void insertAssignment(Assignment assignment);

    @Update
    public void updateAssignment(Assignment assignment);

    @Delete
    public void deleteAssignment(Assignment assignment);
}
