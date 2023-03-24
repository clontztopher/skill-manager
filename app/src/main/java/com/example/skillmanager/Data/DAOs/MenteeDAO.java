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

@Dao
public interface MenteeDAO {
    @Query("SELECT * FROM mentees")
    public LiveData<List<Mentee>> getMentees();

    @Query(("SELECT * FROM mentees WHERE menteeId = :menteeId"))
    public LiveData<Mentee> findMenteeById(long menteeId);

    @Insert(onConflict = OnConflictStrategy.FAIL)
    public void insertMentee(Mentee mentee);

    @Update
    public void updateMentee(Mentee mentee);

    @Delete
    public void deleteMentee(Mentee mentee);
}
