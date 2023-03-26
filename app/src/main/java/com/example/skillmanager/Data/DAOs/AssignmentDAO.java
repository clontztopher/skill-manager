package com.example.skillmanager.Data.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.skillmanager.Data.Entities.Assignment;

import java.util.List;

@Dao
public interface AssignmentDAO extends BaseDAO<Assignment> {

    @Query("SELECT * FROM assignment")
    public LiveData<List<Assignment>> getAllAssignments();

    @Query("SELECT * FROM assignment")
    public List<Assignment> getAllAssignmentsSync();

    @Query("SELECT * FROM assignment WHERE assignment_id = :id")
    public LiveData<Assignment> findById(long id);
}
