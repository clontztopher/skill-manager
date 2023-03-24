package com.example.skillmanager.Activities.Mentee;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.skillmanager.Data.Entities.Mentee;
import com.example.skillmanager.Data.Repositories.MenteeRepository;

import java.util.List;

public class MenteeListViewModel extends AndroidViewModel {
    private LiveData<List<Mentee>> mMentees;

    public MenteeListViewModel(Application app) {
        super(app);

        MenteeRepository menteeRepo = new MenteeRepository(app);
        mMentees = menteeRepo.getAllMentees();
    }

    public LiveData<List<Mentee>> getAllMentees() {
        return mMentees;
    }
}
