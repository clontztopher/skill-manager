package com.example.skillmanager.Data.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.skillmanager.Data.Entities.Assignment;
import com.example.skillmanager.Data.Entities.MenteeWithAssignment;

import java.util.List;

@Dao
public interface AssignmentDAO extends BaseDAO<Assignment> {

    @Query("SELECT * FROM assignment")
    public LiveData<List<Assignment>> getAllAssignments();

    @Query("SELECT * FROM assignment")
    public List<Assignment> getAllAssignmentsSync();

    @Query("SELECT * FROM assignment WHERE assignment_id = :id")
    public LiveData<Assignment> findById(long id);

    @Query("SELECT * FROM mentee_assignments " +
            "INNER JOIN cycles, mentees, assignment " +
            "ON mentee_assignments.mentee_id = :menteeId " +
            "AND mentee_assignments.assignment_id = :assignmentId " +
            "AND cycles.cycle_id = :cycleId")
    public LiveData<MenteeWithAssignment> getMenteeWithAssignment(long cycleId, long menteeId, long assignmentId);
}
