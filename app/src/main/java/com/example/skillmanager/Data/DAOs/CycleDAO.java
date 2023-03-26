package com.example.skillmanager.Data.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.skillmanager.Data.Entities.Cycle;
import com.example.skillmanager.Data.Entities.CycleWithMentees;

import java.util.List;

@Dao
public interface CycleDAO extends BaseDAO<Cycle> {
    @Query("SELECT * FROM cycles")
    public abstract LiveData<List<Cycle>> getCycles();

    @Query("SELECT * FROM cycles WHERE cycle_id = :id")
    public abstract LiveData<Cycle> findCycleById(long id);

    @Transaction
    @Query("SELECT * FROM cycles WHERE cycle_id = :cycleId")
    public abstract LiveData<CycleWithMentees> getCycleWithMentees(long cycleId);
}
