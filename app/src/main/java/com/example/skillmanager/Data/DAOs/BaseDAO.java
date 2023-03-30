package com.example.skillmanager.Data.DAOs;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;


public interface BaseDAO<T> {
    @Insert(onConflict = OnConflictStrategy.FAIL)
    void insert(T ent);

    @Update
    void update(T ent);

    @Delete
    void delete(T ent);
}
