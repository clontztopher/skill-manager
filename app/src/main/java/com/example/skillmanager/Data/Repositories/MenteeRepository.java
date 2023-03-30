package com.example.skillmanager.Data.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.skillmanager.Data.DAOs.MenteeDAO;
import com.example.skillmanager.Data.Entities.MenteeWithAssignments;
import com.example.skillmanager.Data.SkillManagerDatabase;
import com.example.skillmanager.Data.Entities.Mentee;

import java.util.List;
import java.util.concurrent.Future;

public class MenteeRepository {
    private MenteeDAO menteeDAO;
    private LiveData<List<Mentee>> mMentees;
    public MenteeRepository(Application application) {
        SkillManagerDatabase db = SkillManagerDatabase.getInstance(application);
        menteeDAO = db.menteeDAO();
        mMentees = menteeDAO.getMentees();
    }
    public LiveData<List<Mentee>> getAllMentees() {
        return mMentees;
    }

    public Future<List<Mentee>> getAllMenteesSync() {
        return SkillManagerDatabase.databaseWriteExecutor.submit(() -> menteeDAO.getMenteesSync());
    }
    public LiveData<Mentee> findMenteeById(long menteeId) {
        return menteeDAO.findMenteeById(menteeId);
    }

    public LiveData<List<MenteeWithAssignments>> getMenteesForCycle(long cycleId) {
        return menteeDAO.getMenteesForCycle(cycleId);
    }
    public void addNewMentee(Mentee mentee) {
        SkillManagerDatabase.databaseWriteExecutor.execute(() -> menteeDAO.insert(mentee));
    }
    public void updateMentee(Mentee mentee) {
        SkillManagerDatabase.databaseWriteExecutor.execute(() -> menteeDAO.update(mentee));
    }
    public void deleteMentee(Mentee mentee) {
        SkillManagerDatabase.databaseWriteExecutor.execute(() -> menteeDAO.delete(mentee));
    }
}

