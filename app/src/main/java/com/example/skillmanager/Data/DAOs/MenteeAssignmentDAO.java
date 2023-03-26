package com.example.skillmanager.Data.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.skillmanager.Data.Entities.Mentee;
import com.example.skillmanager.Data.Entities.MenteeAssignmentCrossRef;

import java.util.List;

@Dao
public interface MenteeAssignmentDAO extends BaseDAO<MenteeAssignmentCrossRef> {
    @Query("SELECT * FROM mentee_assignments " +
            "INNER JOIN mentees ON mentees.mentee_id = mentee_assignments.mentee_id " +
            "WHERE mentee_assignments.assignment_id = :assignmentId")
    public List<Mentee> findMenteesForAssignment(long assignmentId);
}
