package com.example.skillmanager.Data.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "mentees")
public class Mentee {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "mentee_id")
    private long menteeId;

    private String name;

    private String email;

    private String phone;

    public long getMenteeId() {
        return menteeId;
    }

    public void setMenteeId(long id) {
        this.menteeId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return getName();
    }

}
