package com.example.skillmanager.Data.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.skillmanager.Data.DAOs.MenteeDAO;
import com.example.skillmanager.Data.Database;
import com.example.skillmanager.Data.Entities.Mentee;

import java.util.List;
import java.util.concurrent.Future;

public class MenteeRepository {
    private MenteeDAO menteeDAO;
    private LiveData<List<Mentee>> mMentees;
    public MenteeRepository(Application application) {
        Database db = Database.getInstance(application);
        menteeDAO = db.menteeDAO();
        mMentees = menteeDAO.getMentees();
    }
    public LiveData<List<Mentee>> getAllMentees() {
        return mMentees;
    }
    public LiveData<Mentee> findMenteeById(long menteeId) {
        return menteeDAO.findMenteeById(menteeId);
    }
    public void addNewMentee(Mentee mentee) {
        Database.databaseWriteExecutor.execute(() -> menteeDAO.insertMentee(mentee));
    }
    public void updateMentee(Mentee mentee) {
        Database.databaseWriteExecutor.execute(() -> menteeDAO.updateMentee(mentee));
    }
    public void deleteMentee(Mentee mentee) {
        Database.databaseWriteExecutor.execute(() -> menteeDAO.deleteMentee(mentee));
    }
}

