package com.example.skillmanager.Data.Repositories;

import android.app.Application;

import com.example.skillmanager.Data.DAOs.MenteeCycleDAO;
import com.example.skillmanager.Data.Entities.CycleWithMentees;
import com.example.skillmanager.Data.SkillManagerDatabase;
import com.example.skillmanager.Data.Entities.Mentee;
import com.example.skillmanager.Data.Entities.MenteeCycleCrossRef;

import java.util.List;
import androidx.lifecycle.LiveData;

public class MenteeCycleRepository {
    private MenteeCycleDAO menteeCycleDAO;

    public MenteeCycleRepository(Application application) {
        SkillManagerDatabase db = SkillManagerDatabase.getInstance(application);
        menteeCycleDAO = db.menteeCycleDAO();
    }

    public void insert(MenteeCycleCrossRef menteeCycleCrossRef) {
        SkillManagerDatabase.databaseWriteExecutor.execute(() -> menteeCycleDAO.insert(menteeCycleCrossRef));
    }

    public void update(MenteeCycleCrossRef menteeCycleCrossRef) {
        SkillManagerDatabase.databaseWriteExecutor.execute(() -> menteeCycleDAO.update(menteeCycleCrossRef));
    }

    public void delete(MenteeCycleCrossRef menteeCycleCrossRef) {
        SkillManagerDatabase.databaseWriteExecutor.execute(() -> menteeCycleDAO.delete(menteeCycleCrossRef));
    }
}

