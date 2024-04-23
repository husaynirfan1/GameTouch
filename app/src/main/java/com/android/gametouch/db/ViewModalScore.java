package com.android.gametouch.db;/*
 * *
 *  * Created by Husayn on 22/10/2021, 5:04 PM
 *  * Copyright (c) 2021 . All rights reserved.
 *  * Last modified 22/10/2021, 2:29 PM
 *
 */

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModalScore extends AndroidViewModel {
    private ScoreRepository repository;
    private LiveData<List<ScoreModal1>> allScores;

    public ViewModalScore(@NonNull Application application){
        super(application);
        repository = new ScoreRepository(application);
        allScores = repository.getAllScore();
    }

    public void insert(ScoreModal1 model) {
        repository.insert(model);
    }

    public void insertALL(ScoreModal1 modals) {
        repository.insertAllCourses();
    }

    // below line is to update data in our repository.
    public void update(ScoreModal1 model) {
        repository.update(model);
    }

    // below line is to delete the data in our repository.
    public void delete(ScoreModal1 model) {
        repository.delete(model);
    }

    // below method is to delete all the courses in our list.
    public void deleteAllDoa() {
        repository.deleteAllCourses();
    }
    public LiveData<List<ScoreModal1>> getAllScores() {
        return allScores;
    }

    public LiveData<List<ScoreModal1>> findbyLeveL(String level) {
        return repository.findbyLevel(level);
    }

}
