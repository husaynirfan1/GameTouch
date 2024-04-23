package com.android.gametouch.db;/*
 * *
 *  * Created by Husayn on 22/10/2021, 5:04 PM
 *  * Copyright (c) 2021 . All rights reserved.
 *  * Last modified 22/10/2021, 2:29 PM
 *
 */

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

// adding annotation for our database entities and db version.

@androidx.room.Database(entities = {ScoreModal1.class}, version = 3)

public abstract class Database extends RoomDatabase {

    // below line is to create instance
    // for our database class.
    private static Database instance;
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //this method is called when database is created and below line is to populate our data.
            new PopulateDbAsyncTask(instance).execute();
            Log.d("ONCREATE", "DB CREATED.");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

        }
    };

    // on below line we are getting instance for our database.
    public static synchronized Database getInstance(Context context) {
        // below line is to check if
        // the instance is null or not.
        if (instance == null) {
            // if the instance is null we
            // are creating a new instance
            instance =
                    // for creating a instance for our database
                    // we are creating a database builder and passing
                    // our database class with our database name.
                    Room.databaseBuilder(context.getApplicationContext(),
                                    Database.class, "database")
                            .createFromAsset("tapDB.db")
                            // below line is use to add fall back to
                            // destructive migration to our database.

                            // below line is to add callback
                            // to our database
                            .allowMainThreadQueries()
                            .addMigrations(MIGRATION_2_3)
                            .addCallback(roomCallback)
                            // below line is to
                            // build our database.
                            .build();

        }
        // after creating an instance
        // we are returning our instance
        return instance;
    }

    // below line is to create
    // abstract variable for dao.
    public abstract ScoreDao1 ScoreDao1();

    //     we are creating an async task class to perform task in background.
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        PopulateDbAsyncTask(Database instance) {
            ScoreDao1 scoreDao1 = instance.ScoreDao1();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            ScoreDao1 scoreDao1 = instance.ScoreDao1();
            return null;
        }
    }
}

