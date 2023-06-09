package com.example.skillmanager.Activities.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.skillmanager.Data.Entities.Mentee;
import com.example.skillmanager.Data.Repositories.MenteeRepository;
import com.example.skillmanager.Data.SkillManagerDatabase;

import java.util.List;
import java.util.concurrent.Future;

public class MenteeListViewModel extends AndroidViewModel {
    private LiveData<List<Mentee>> mMentees;
    private MenteeRepository menteeRepo;

    public MenteeListViewModel(Application app) {
        super(app);
        SkillManagerDatabase db = SkillManagerDatabase.getInstance(app);
        menteeRepo = new MenteeRepository(db);
        mMentees = menteeRepo.getAllMentees();
    }

    public LiveData<List<Mentee>> getAllMentees() {
        return mMentees;
    }

    public Future<List<Mentee>> getAllMenteesSync() {
        return menteeRepo.getAllMenteesSync();
    }
}
