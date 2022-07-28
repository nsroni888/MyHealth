package com.pialroni.myhealth;


import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * Creating Room Database
 */
@Database(entities = {UserData.class}, version = 3)
public abstract class UserDatabase extends RoomDatabase {

    // below line is to create instance
    // for our database class.
    private static UserDatabase instance;

    // below line is to create
    // abstract variable for dao.

    /**
     * Interface for getting data
     * @return
     */
    public abstract IDataBase Dao();

    // on below line we are getting instance for our database.
    public static synchronized UserDatabase getInstance(Context context) {
        // below line is to check if 
        // the instance is null or not.
        /**
         * Singleton Object
         */
        if (instance == null) {
            // if the instance is null we
            // are creating a new instance
            instance =
                    // for creating a instance for our database 
                    // we are creating a database builder and passing 
                    // our database class with our database name.
                    Room.databaseBuilder(context.getApplicationContext(),
                            UserDatabase.class, "User_Data")
                            // below line is use to add fall back to 
                            // destructive migration to our database.
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            // below line is to add callback 
                            // to our database.
                            .addCallback(roomCallback)
                            // below line is to 
                            // build our database.
                            .build();
        }
        // after creating an instance 
        // we are returning our instance
        return instance;
    }

    // below line is to create a callback for our room database.
    private static Callback roomCallback = new Callback() {
        @Override
        public void onCreate(SupportSQLiteDatabase db) {
            super.onCreate(db);
            // this method is called when database is created
            // and below line is to populate our data.
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    // we are creating an async task class to perform task in background.
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        PopulateDbAsyncTask(UserDatabase instance) {
            IDataBase dao = instance.Dao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}