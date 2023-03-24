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
public interface MenteeAssignmentDAO {
    @Query("SELECT * FROM mentee_assignments " +
            "INNER JOIN mentees ON mentees.menteeId = mentee_assignments.menteeId " +
            "WHERE mentee_assignments.assignmentId = :assignmentId")
    public List<Mentee> findMenteesForAssignment(long assignmentId);

    @Query("SELECT * FROM mentee_assignments " +
            "INNER JOIN mentees ON mentees.menteeId = mentee_assignments.menteeId " +
            "WHERE mentee_assignments.assignmentId != :assignmentId")
    public List<Mentee> getMenteesNotAssigned(long assignmentId);

    @Insert(onConflict = OnConflictStrategy.FAIL)
    public void insert(MenteeAssignmentCrossRef menteeAssignmentCrossRef);

    @Update
    public void update(MenteeAssignmentCrossRef menteeAssignmentCrossRef);

    @Delete
    public void delete(MenteeAssignmentCrossRef menteeAssignmentCrossRef);

    @Query("DELETE FROM mentee_assignments WHERE menteeId = :menteeId")
    public void removeFromAssignments(long menteeId);
}
