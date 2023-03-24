package com.example.skillmanager.Data;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.skillmanager.Data.DAOs.AssessmentDAO;
import com.example.skillmanager.Data.DAOs.AssignmentDAO;
import com.example.skillmanager.Data.DAOs.MenteeAssignmentDAO;
import com.example.skillmanager.Data.DAOs.CycleDAO;
import com.example.skillmanager.Data.DAOs.MenteeCycleDAO;
import com.example.skillmanager.Data.DAOs.MenteeDAO;
import com.example.skillmanager.Data.Entities.Assessment;
import com.example.skillmanager.Data.Entities.Assignment;
import com.example.skillmanager.Data.Entities.Cycle;
import com.example.skillmanager.Data.Entities.MenteeAssignmentCrossRef;
import com.example.skillmanager.Data.Entities.Mentee;
import com.example.skillmanager.Data.Entities.MenteeCycleCrossRef;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@androidx.room.Database(entities = {
        Cycle.class,
        Assignment.class,
        Mentee.class,
        MenteeAssignmentCrossRef.class,
        MenteeCycleCrossRef.class
}, version = 1)
public abstract class Database extends RoomDatabase {
    private static final String DATABASE_NAME = "skill_manager.db";
    private static volatile Database mSkillManagerDB;
    private static final int NUM_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUM_THREADS);

    public static Database getInstance(final Context context) {
        if (mSkillManagerDB == null) {
            synchronized (Database.class) {
                if (mSkillManagerDB == null) {
                    mSkillManagerDB = Room.databaseBuilder(context, Database.class, DATABASE_NAME).build();
                }
            }
        }
        return mSkillManagerDB;
    }

    public abstract CycleDAO cycleDAO();
    public abstract AssignmentDAO assignmentDAO();
    public abstract MenteeDAO menteeDAO();
    public abstract MenteeAssignmentDAO menteeAssignmentDAO();

    public abstract MenteeCycleDAO menteeCycleDAO();
}
