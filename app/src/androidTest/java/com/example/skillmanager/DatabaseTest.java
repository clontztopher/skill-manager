package com.example.skillmanager;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.example.skillmanager.Data.DAOs.CycleDAO;
import com.example.skillmanager.Data.Entities.Cycle;
import com.example.skillmanager.Data.SkillManagerDatabase;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    private CycleDAO cycleDAO;
    private SkillManagerDatabase db;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, SkillManagerDatabase.class).build();
        cycleDAO = db.cycleDAO();
    }
    @After
    public void tearDown() {
        db.close();
    }

    @Test
    public void duplicateInsertThrowsError() {
        Cycle cycle1 = new Cycle();
        cycle1.setDisplayName("Spring 2023");
        cycle1.setStartDateString("04/01/2023");
        cycle1.setEndDateString("06/30/2023");
        cycle1.setCycleId(50);
        cycleDAO.insert(cycle1);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SQLiteConstraintException e = assertThrows(SQLiteConstraintException.class, () -> cycleDAO.insert(cycle1));
        assertTrue(e.getMessage().contains("UNIQUE constraint failed"));
    }
}