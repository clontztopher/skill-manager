package com.example.skillmanager.Data.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.skillmanager.Data.DAOs.CycleDAO;
import com.example.skillmanager.Data.Entities.CycleWithMentees;
import com.example.skillmanager.Data.SkillManagerDatabase;
import com.example.skillmanager.Data.Entities.Cycle;

import java.util.List;

public class CycleRepository {
    private CycleDAO cycleDAO;
    private LiveData<List<Cycle>> mCycles;

    public CycleRepository(Application application) {
        SkillManagerDatabase db = SkillManagerDatabase.getInstance(application);
        cycleDAO = db.cycleDAO();
        mCycles = cycleDAO.getCycles();
    }

    public LiveData<List<Cycle>> getAllCycles() {
        return mCycles;
    }

    public LiveData<Cycle> getCycleById(long id) {
        return cycleDAO.findCycleById(id);
    }

    public LiveData<CycleWithMentees> getCycleWithMentees(long cycleId) {
        return cycleDAO.getCycleWithMentees(cycleId);
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
