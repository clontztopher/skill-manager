package com.example.skillmanager.Activities.Cycle;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.skillmanager.Data.Entities.Cycle;
import com.example.skillmanager.Data.Repositories.CycleRepository;

public class CycleViewModel extends AndroidViewModel {
    private CycleRepository cycleRepository;
    public CycleViewModel(Application app) {
        super(app);
        cycleRepository = new CycleRepository(app);
    }
    public LiveData<Cycle> getCycle(long id) {
        return cycleRepository.getCycleById(id);
    }

    public void insert(Cycle cycle) {
        cycleRepository.insert(cycle);
    }

    public void update(Cycle cycle) {
        cycleRepository.update(cycle);
    }

    public void delete(Cycle cycle) {
        cycleRepository.delete(cycle);
    }

    @Override
    protected void onCleared() {
        cycleRepository = null;
    }
}
