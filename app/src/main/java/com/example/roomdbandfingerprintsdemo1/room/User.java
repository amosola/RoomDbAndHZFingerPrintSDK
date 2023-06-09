package com.example.roomdbandfingerprintsdemo1.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Base64;

@Entity(tableName = "user_table")
public class User {
    @PrimaryKey
    private int id;
    private String username;
    private Base64 profilePhoto;
    private Base64 fingerPrint;

    //--------------> constructor <-------------

    public User(int id, String username, Base64 profilePhoto, Base64 fingerPrint) {
        this.id = id;
        this.username = username;
        this.profilePhoto = profilePhoto;
        this.fingerPrint = fingerPrint;
    }

    //---------------> getters and setters <-----------------

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Base64 getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(Base64 profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public Base64 getFingerPrint() {
        return fingerPrint;
    }

    public void setFingerPrint(Base64 fingerPrint) {
        this.fingerPrint = fingerPrint;
    }
}

