package com.example.skillmanager.Data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.skillmanager.Data.DAOs.AssignmentDAO;
import com.example.skillmanager.Data.DAOs.CycleDAO;
import com.example.skillmanager.Data.DAOs.MenteeAssignmentDAO;
import com.example.skillmanager.Data.DAOs.MenteeDAO;
import com.example.skillmanager.Data.Entities.Assignment;
import com.example.skillmanager.Data.Entities.Cycle;
import com.example.skillmanager.Data.Entities.MenteeAssignmentCrossRef;
import com.example.skillmanager.Data.Entities.Mentee;
import com.example.skillmanager.Data.Entities.Project;
import com.example.skillmanager.Data.Entities.Study;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@androidx.room.Database(entities = {
            Cycle.class,
            Assignment.class,
            Mentee.class,
            MenteeAssignmentCrossRef.class,
        },
        version = 1,
        exportSchema = false)
public abstract class SkillManagerDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "skill_manager.db";
    private static volatile SkillManagerDatabase mSkillManagerDB;
    private static final int NUM_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUM_THREADS);

    public static SkillManagerDatabase getInstance(final Context context) {
        if (mSkillManagerDB == null) {
            synchronized (SkillManagerDatabase.class) {
                if (mSkillManagerDB == null) {
                    mSkillManagerDB = Room.databaseBuilder(context, SkillManagerDatabase.class, DATABASE_NAME)
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    databaseWriteExecutor.execute(() -> {
                                        SkillManagerDatabase database = SkillManagerDatabase.getInstance(context);
                                        Cycle cycle1 = new Cycle();
                                        cycle1.setDisplayName("Spring 2023");
                                        cycle1.setStartDateString("04/01/2023");
                                        cycle1.setEndDateString("06/30/2023");

                                        Cycle cycle2 = new Cycle();
                                        cycle2.setDisplayName("Summer 2023");
                                        cycle2.setStartDateString("07/01/2023");
                                        cycle2.setEndDateString("09/30/2023");

                                        database.cycleDAO().insert(cycle1);
                                        database.cycleDAO().insert(cycle2);

                                        Mentee mentee1 = new Mentee();
                                        mentee1.setName("Topher");
                                        mentee1.setEmail("topher@example.com");
                                        mentee1.setPhone("234-432-2342");

                                        Mentee mentee2 = new Mentee();
                                        mentee2.setName("Jenny");
                                        mentee2.setEmail("jenny@example.com");
                                        mentee2.setPhone("534-345-2342");

                                        database.menteeDAO().insert(mentee1);
                                        database.menteeDAO().insert(mentee2);

                                        Assignment assignment1 = new Assignment();
                                        assignment1.setType(Assignment.AssignmentType.PROJECT);
                                        assignment1.setTitle("Web Site Home Page");
                                        assignment1.setTopic("Web Development");
                                        assignment1.setProject(new Project("Build a home page for your website."));

                                        Assignment assignment2 = new Assignment();
                                        assignment2.setType(Assignment.AssignmentType.STUDY);
                                        assignment2.setTitle("Learn Polymorphism");
                                        assignment2.setTopic("Design Patterns");
                                        assignment2.setStudy(new Study(
                                                "https://docs.oracle.com/javase/tutorial/java/IandI/polymorphism.html",
                                                "1. How is polymorphism expressed in subclassing systems?\r\n" +
                                                        "2. Do methods need to be explicitly overridden for the pattern to be considered as polymorphism?"
                                        ));

                                        database.assignmentDAO().insert(assignment1);
                                        database.assignmentDAO().insert(assignment2);
                                    });
                                }
                            })
                            .build();
                }
            }
        }
        return mSkillManagerDB;
    }

    public abstract CycleDAO cycleDAO();
    public abstract AssignmentDAO assignmentDAO();
    public abstract MenteeDAO menteeDAO();
    public abstract MenteeAssignmentDAO menteeAssignmentDAO();
}
