package com.android.gametouch.db;/*
 * *
 *  * Created by Husayn on 22/10/2021, 5:04 PM
 *  * Copyright (c) 2021 . All rights reserved.
 *  * Last modified 22/10/2021, 2:29 PM
 *
 */

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ScoreRepository {
    private ScoreDao1 dao;
    private LiveData<List<ScoreModal1>> allScore;

    public ScoreRepository(Application application){
        Database database = Database.getInstance(application);
        dao = database.ScoreDao1();
        allScore = dao.getAllScores();
    }
    public void insert(ScoreModal1 model) {
        new InsertCourseAsyncTask(dao).execute(model);
    }

    public void insertAllCourses() {
        new InsertAllCoursesAsyncTask(dao).execute();
    }

    public void update(ScoreModal1 model) {
        new UpdateCourseAsyncTask(dao).execute(model);
    }

    public void delete(ScoreModal1 model) {
        new DeleteCourseAsyncTask(dao).execute(model);
    }

    public void deleteAllCourses() {
        new DeleteAllCoursesAsyncTask(dao).execute();
    }

    public LiveData<List<ScoreModal1>> getAllScore() {
        return allScore;
    }

    public LiveData<List<ScoreModal1>> findbyLevel(String level){
        return dao.findbyLevel(level);
    }

    private static class InsertCourseAsyncTask extends AsyncTask<ScoreModal1, Void, Void> {
        private ScoreDao1 dao;

        private InsertCourseAsyncTask(ScoreDao1 dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ScoreModal1... model) {
            dao.insert(model[0]);
            return null;
        }
    }

    private static class UpdateCourseAsyncTask extends AsyncTask<ScoreModal1, Void, Void> {
        private ScoreDao1 dao;

        private UpdateCourseAsyncTask(ScoreDao1 dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ScoreModal1... models) {
            dao.update(models[0]);
            return null;
        }
    }

    private static class DeleteCourseAsyncTask extends AsyncTask<ScoreModal1, Void, Void> {
        private ScoreDao1 dao;

        private DeleteCourseAsyncTask(ScoreDao1 dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ScoreModal1... models) {

            dao.delete(models[0]);
            return null;
        }
    }

    private static class DeleteAllCoursesAsyncTask extends AsyncTask<Void, Void, Void> {
        private ScoreDao1 dao;

        private DeleteAllCoursesAsyncTask(ScoreDao1 dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {

            dao.deleteAllCourses();
            return null;
        }
    }

    private static class InsertAllCoursesAsyncTask extends AsyncTask<Void, Void, Void> {
        private ScoreDao1 dao;

        private InsertAllCoursesAsyncTask(ScoreDao1 dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.insertAll();
            return null;
        }
    }
}
