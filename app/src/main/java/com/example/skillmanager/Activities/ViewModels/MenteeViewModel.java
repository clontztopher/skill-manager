package com.example.skillmanager.Activities.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.skillmanager.Data.Entities.Mentee;
import com.example.skillmanager.Data.Repositories.MenteeRepository;

public class MenteeViewModel extends AndroidViewModel {
    private MenteeRepository menteeRepository;

    public MenteeViewModel(Application app) {
        super(app);
        menteeRepository = new MenteeRepository(app);
    }

    public LiveData<Mentee> findById(long id) {
        return menteeRepository.findMenteeById(id);
    }

    public void insert(Mentee mentee) {
        menteeRepository.addNewMentee(mentee);
    }

    public void update(Mentee mentee) {
        menteeRepository.updateMentee(mentee);
    }

    public void delete(Mentee mentee) {
        menteeRepository.deleteMentee(mentee);
    }
}
