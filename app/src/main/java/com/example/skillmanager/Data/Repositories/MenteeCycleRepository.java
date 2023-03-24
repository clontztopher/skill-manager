package com.example.skillmanager.Data.Repositories;

import android.app.Application;

import com.example.skillmanager.Data.DAOs.MenteeCycleDAO;
import com.example.skillmanager.Data.DAOs.MenteeCycleDAO;
import com.example.skillmanager.Data.Database;
import com.example.skillmanager.Data.Entities.Mentee;
import com.example.skillmanager.Data.Entities.MenteeCycleCrossRef;

import java.util.List;
import androidx.lifecycle.LiveData;

public class MenteeCycleRepository {
    private MenteeCycleDAO menteeCycleDAO;

    public MenteeCycleRepository(Application application) {
        Database db = Database.getInstance(application);
        menteeCycleDAO = db.menteeCycleDAO();
    }

    public LiveData<List<Mentee>> getMenteesForCycle(long cycleId) {
        return menteeCycleDAO.findMenteesForCycle(cycleId);
    }

    public void removeFromCycles(long menteeId) {
        Database.databaseWriteExecutor.execute(() -> menteeCycleDAO.removeFromCycles(menteeId));
    }

    public void insert(MenteeCycleCrossRef menteeCycleCrossRef) {
        Database.databaseWriteExecutor.execute(() -> menteeCycleDAO.insert(menteeCycleCrossRef));
    }

    public void update(MenteeCycleCrossRef menteeCycleCrossRef) {
        Database.databaseWriteExecutor.execute(() -> menteeCycleDAO.update(menteeCycleCrossRef));
    }

    public void delete(MenteeCycleCrossRef menteeCycleCrossRef) {
        Database.databaseWriteExecutor.execute(() -> menteeCycleDAO.delete(menteeCycleCrossRef));
    }
}

