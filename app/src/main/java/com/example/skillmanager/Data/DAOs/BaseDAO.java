package com.example.skillmanager.Data.DAOs;

import android.database.sqlite.SQLiteConstraintException;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;


public interface BaseDAO<T> {
    @Insert()
    void insert(T ent) throws SQLiteConstraintException;

    @Update
    void update(T ent);

    @Delete
    void delete(T ent);
}
