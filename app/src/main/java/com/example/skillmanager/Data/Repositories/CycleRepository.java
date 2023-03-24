package com.example.skillmanager.Data.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.skillmanager.Data.DAOs.CycleDAO;
import com.example.skillmanager.Data.Database;
import com.example.skillmanager.Data.Entities.Cycle;

import java.util.List;
import java.util.concurrent.Future;

public class CycleRepository {
    private CycleDAO cycleDAO;
    private LiveData<List<Cycle>> mCycles;

    public CycleRepository(Application application) {
        Database db = Database.getInstance(application);
        cycleDAO = db.cycleDAO();
        mCycles = cycleDAO.getCycles();
    }

    public LiveData<List<Cycle>> getAllCycles() {
        return mCycles;
    }

    public LiveData<Cycle> getCycleById(long id) {
        return cycleDAO.findCycleById(id);
    }

    public void insert(Cycle cycle) {
        Database.databaseWriteExecutor.execute(() -> cycleDAO.insertCycle(cycle));
    }

    public void update(Cycle cycle) {
        Database.databaseWriteExecutor.execute(() -> cycleDAO.updateCycle(cycle));
    }

    public void delete(Cycle cycle) {
        Database.databaseWriteExecutor.execute(() -> cycleDAO.deleteCycle(cycle));
    }
}
