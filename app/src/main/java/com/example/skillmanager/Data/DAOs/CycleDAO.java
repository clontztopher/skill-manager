package com.example.skillmanager.Data.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.skillmanager.Data.Entities.Cycle;

import java.util.List;

@Dao
public interface CycleDAO {

    @Query("SELECT * FROM cycles")
    public LiveData<List<Cycle>> getCycles();

    @Query("SELECT * FROM cycles WHERE id = :id")
    public LiveData<Cycle> findCycleById(long id);

    @Insert(onConflict =  OnConflictStrategy.FAIL)
    public void insertCycle(Cycle cycle);

    @Update
    public void updateCycle(Cycle cycle);

    @Delete
    public void deleteCycle(Cycle cycle);
}
