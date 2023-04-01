package com.example.skillmanager.Data.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.skillmanager.Data.DAOs.CycleDAO;
import com.example.skillmanager.Data.DAOs.MenteeAssignmentDAO;
import com.example.skillmanager.Data.Entities.MenteeAssignmentCrossRef;
import com.example.skillmanager.Data.SkillManagerDatabase;
import com.example.skillmanager.Data.Entities.Cycle;

import java.util.List;

public class CycleRepository {
    private CycleDAO cycleDAO;
    private MenteeAssignmentDAO menteeAssignmentDAO;
    private LiveData<List<Cycle>> mCycles;

    public CycleRepository(SkillManagerDatabase db) {
        cycleDAO = db.cycleDAO();
        mCycles = cycleDAO.getCycles();

        menteeAssignmentDAO = db.menteeAssignmentDAO();
    }

    public LiveData<List<Cycle>> getAllCycles() {
        return mCycles;
    }

    public LiveData<Cycle> getCycleById(long id) {
        return cycleDAO.findCycleById(id);
    }

    public void addAssignment(MenteeAssignmentCrossRef menteeAssignmentCrossRef) {
        SkillManagerDatabase.databaseWriteExecutor.execute(() -> {
            try {
                menteeAssignmentDAO.insert(menteeAssignmentCrossRef);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void removeAssignment(MenteeAssignmentCrossRef menteeAssignmentCrossRef) {
        SkillManagerDatabase.databaseWriteExecutor.execute(() -> menteeAssignmentDAO.delete(menteeAssignmentCrossRef));
    }

    public void insert(Cycle cycle) {
        SkillManagerDatabase.databaseWriteExecutor.execute(() -> cycleDAO.insert(cycle));
    }

    public void update(Cycle cycle) {
        SkillManagerDatabase.databaseWriteExecutor.execute(() -> cycleDAO.update(cycle));
    }

    public void delete(Cycle cycle) {
        SkillManagerDatabase.databaseWriteExecutor.execute(() -> cycleDAO.delete(cycle));
    }
}
