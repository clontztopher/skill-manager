package com.example.skillmanager.Data.DAOs;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.skillmanager.Data.Entities.Mentee;

import java.util.List;
import java.util.concurrent.Future;

@Dao
public interface MenteeDAO extends BaseDAO<Mentee> {
    @Query("SELECT * FROM mentees")
    public LiveData<List<Mentee>> getMentees();

    @Query("SELECT * FROM mentees")
    public List<Mentee> getMenteesSync();

    @Query(("SELECT * FROM mentees WHERE mentee_id = :menteeId"))
    public LiveData<Mentee> findMenteeById(long menteeId);
}
