package com.example.skillmanager.Data.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.skillmanager.Data.Entities.Mentee;
import com.example.skillmanager.Data.Entities.MenteeAssignmentCrossRef;
import com.example.skillmanager.Data.Entities.MenteeCycleCrossRef;

import java.util.List;

@Dao
public interface MenteeCycleDAO {
    @Query("SELECT * FROM mentee_cycles " +
            "INNER JOIN mentees ON mentees.menteeId = mentee_cycles.menteeId " +
            "WHERE mentee_cycles.cycleId = :cycleId")
    public LiveData<List<Mentee>> findMenteesForCycle(long cycleId);

//    @Query("SELECT * FROM mentee_assignments " +
//            "INNER JOIN mentees ON mentees.menteeId = mentee_assignments.menteeId " +
//            "WHERE mentee_assignments.assignmentId != :assignmentId")
//    public List<Mentee> getMenteesNotAssigned(long assignmentId);

    @Insert(onConflict = OnConflictStrategy.FAIL)
    public void insert(MenteeCycleCrossRef menteeCycleCrossRef);

    @Update
    public void update(MenteeCycleCrossRef menteeCycleCrossRef);

    @Delete
    public void delete(MenteeCycleCrossRef menteeCycleCrossRef);

    @Query("DELETE FROM mentee_cycles WHERE menteeId = :menteeId")
    public void removeFromCycles(long menteeId);
}
