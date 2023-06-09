package com.example.roomdbandfingerprintsdemo1.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    private static UserDatabase instance;
    public abstract UserDao userDao();

    public static synchronized UserDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            UserDatabase.class,"user_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    //---------> call on create method the first time app is created <---------------
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
//            new populateDbAsyncTask(instance).execute();
        }
    };


    //--------------> asyncTask for roomCallback <---------------
    private static class populateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDao userDao;
        private  populateDbAsyncTask(UserDatabase db){
            userDao = db.userDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            userDao.insert(new User(0,"admin",null,null));
            return null;
        }
    }
}
