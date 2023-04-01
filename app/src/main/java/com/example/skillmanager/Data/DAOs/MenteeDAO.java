package com.example.skillmanager.Data.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.skillmanager.Data.Entities.Assignment;
import com.example.skillmanager.Data.Entities.Mentee;

import java.util.List;
import java.util.Map;

@Dao
public interface MenteeDAO extends BaseDAO<Mentee> {
    @Query("SELECT * FROM mentees")
    public LiveData<List<Mentee>> getMentees();

    @Query("SELECT * FROM mentees")
    public List<Mentee> getMenteesSync();

    @Query("SELECT * FROM mentees WHERE mentee_id = :menteeId")
    public LiveData<Mentee> findMenteeById(long menteeId);

    @Query("SELECT * FROM mentee_assignments " +
            "INNER JOIN mentees, assignment " +
            "ON mentees.mentee_id = mentee_assignments.mentee_id " +
            "AND assignment.assignment_id = mentee_assignments.assignment_id " +
            "WHERE mentee_assignments.cycle_id = :cycleId")
    public LiveData<Map<Mentee, List<Assignment>>> getMenteesForCycle(long cycleId);
}
