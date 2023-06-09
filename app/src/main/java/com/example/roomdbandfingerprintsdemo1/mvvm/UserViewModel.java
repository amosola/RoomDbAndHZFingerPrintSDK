package com.example.roomdbandfingerprintsdemo1.mvvm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.roomdbandfingerprintsdemo1.room.User;

import java.util.Base64;
import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepository repository;
    private List<User> users;
    private Base64 base64;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        users = repository.getUsers(base64);
    }
    public void insert(User user){
        repository.insert(user);
    }
    public void update(User user){
        repository.update(user);
    }
    public void delete(User user){
        repository.delete(user);
    }

    public List<User> getUsers(Base64 base64){
        return users;
    }
}