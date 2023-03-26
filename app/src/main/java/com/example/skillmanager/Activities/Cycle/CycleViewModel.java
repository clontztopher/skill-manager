package com.example.skillmanager.Activities.Cycle;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.skillmanager.Data.Entities.Cycle;
import com.example.skillmanager.Data.Entities.CycleWithMentees;
import com.example.skillmanager.Data.Entities.MenteeCycleCrossRef;
import com.example.skillmanager.Data.Repositories.CycleRepository;
import com.example.skillmanager.Data.Repositories.MenteeCycleRepository;

import java.util.List;

public class CycleViewModel extends AndroidViewModel {
    private CycleRepository cycleRepository;
    private MenteeCycleRepository menteeCycleRepository;

    public CycleViewModel(Application app) {
        super(app);
        cycleRepository = new CycleRepository(app);
        menteeCycleRepository = new MenteeCycleRepository(app);
    }
    public LiveData<Cycle> getCycle(long id) {
        return cycleRepository.getCycleById(id);
    }

    public LiveData<CycleWithMentees> getCycleWithMentees(long id) { return cycleRepository.getCycleWithMentees(id); }

    public void insert(Cycle cycle) {
        cycleRepository.insert(cycle);
    }

    public void update(Cycle cycle) {
        cycleRepository.update(cycle);
    }

    public void delete(Cycle cycle) {
        cycleRepository.delete(cycle);
    }

    public void addMentee(long menteeId, long cycleId) {
        menteeCycleRepository.insert(new MenteeCycleCrossRef(menteeId, cycleId));
    }

    public void removeMentee(long menteeId, long cycleId) {
        menteeCycleRepository.delete(new MenteeCycleCrossRef(menteeId, cycleId));
    }

    @Override
    protected void onCleared() {
        cycleRepository = null;
    }
}
