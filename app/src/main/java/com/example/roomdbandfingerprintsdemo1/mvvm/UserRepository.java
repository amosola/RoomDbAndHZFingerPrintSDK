package com.example.roomdbandfingerprintsdemo1.mvvm;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.roomdbandfingerprintsdemo1.room.User;
import com.example.roomdbandfingerprintsdemo1.room.UserDao;
import com.example.roomdbandfingerprintsdemo1.room.UserDatabase;

import java.util.Base64;
import java.util.List;

public class UserRepository {
    private UserDao userDao;
    private List<User> users;
    private Base64 base64;

    public UserRepository(Application application) {
        UserDatabase database = UserDatabase.getInstance(application);
        userDao = database.userDao();
        users = userDao.getUser(base64);//get base 64
    }

    public void insert(User user){
        new insertUserAsyncTask(userDao).execute(user);
    }
    public void update(User user){
        new updateUserAsyncTask(userDao).execute(user);
    }
    public void delete(User user){
        new deleteUserAsyncTask(userDao).execute(user);
    }
    public List<User> getUsers(Base64 args){return users;}


    //---------------------> async tasks for accessing db <-------------
    private static class insertUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        private insertUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }
    }

    private static class updateUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        private updateUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.update(users[0]);
            return null;
        }
    }

    private static class deleteUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        private deleteUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.delete(users[0]);
            return null;
        }
    }

}
