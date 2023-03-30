package com.example.skillmanager.Activities.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.skillmanager.Data.Entities.Cycle;
import com.example.skillmanager.Data.Repositories.CycleRepository;

import java.util.List;

public class CycleListViewModel extends AndroidViewModel {
    private LiveData<List<Cycle>> mCycles;
    public CycleListViewModel(Application app) {
        super(app);
        CycleRepository cycleRepo = new CycleRepository(app);
        mCycles = cycleRepo.getAllCycles();
    }
    public LiveData<List<Cycle>> getAllCycles() {
        return mCycles;
    }
}
